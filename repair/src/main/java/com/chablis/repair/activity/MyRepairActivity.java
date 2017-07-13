package com.chablis.repair.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.chablis.repair.R;
import com.chablis.repair.adapter.InformationAdapter;
import com.chablis.repair.base.BaseTitleActivity;
import com.chablis.repair.model.Equipment;
import com.chablis.repair.rx.Response;
import com.chablis.repair.rx.RxObserverableCallBack;
import com.chablis.repair.rx.SoapObservable;
import com.chablis.repair.rx.SoapObserver;
import com.chablis.repair.utils.CommonUtil;
import com.chablis.repair.utils.PrefUtils;
import com.chablis.repair.utils.SoapUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyRepairActivity extends BaseTitleActivity {
    private ArrayList data;

    @BindView(R.id.list)
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_repair);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getMyRepair();
        data = new ArrayList();
//        for (int i = 0; i < 8; i++) {
//            data.add("aaaaaaaa" + i);
//        }
//
//        list.setAdapter(new InformationAdapter(this, data));

        View header = getLayoutInflater().inflate(R.layout.my_repair_table1, null);
        list.addHeaderView(header);


        View footer = getLayoutInflater().inflate(R.layout.information_table2, null);
        list.addFooterView(footer);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //listview有头部，下标从1开始
                Log.d("InformationActivity", "position:" + position);
                Equipment.RepairInfo repairInfo = (Equipment.RepairInfo) data.get(position - 1);
                Intent intent = new Intent(mActivity, InformationDetailActivity.class);
                intent.putExtra("repairInfo", repairInfo);
                startActivity(intent);
            }
        });


    }

    public void getMyRepair() {
        Observable<Response> observable = SoapObservable.getAnyObservable(new RxObserverableCallBack() {
            @Override
            public String doWebRequest() {
                return SoapUtils.getMyRepair(appContext.user.getUser_ID());
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers
                        .mainThread()).subscribe(new SoapObserver<Response>() {

            @Override
            public void onSuccess(String s) {
                Log.d("MyRepairActivity", s);
                data = (ArrayList) JSONArray.parseArray(s, Equipment.RepairInfo.class);
                list.setAdapter(new InformationAdapter(mActivity, data));//没有数据
                if (data.size() == 0) {
                    View nodata = getLayoutInflater().inflate(R.layout.information_nodata, null);
                    list.addFooterView(nodata);
                }
            }

            @Override
            public void onFailure(String s) {
                Log.d("MyRepairActivity", s);
                CommonUtil.showToast(mActivity, s);
                View nodata = getLayoutInflater().inflate(R.layout.information_nodata, null);
                list.addFooterView(nodata);
            }
        });
    }

    @OnClick(R.id.tv_exit)
    public void onViewClicked() {
        PrefUtils.logout();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

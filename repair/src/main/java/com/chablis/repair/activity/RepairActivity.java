package com.chablis.repair.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
import com.chablis.repair.utils.SoapUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RepairActivity extends BaseTitleActivity {

    private ArrayList data;

    @BindView(R.id.list)
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_repair);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getRepairList();

        View header = getLayoutInflater().inflate(R.layout.repair_table1, null);
        list.addHeaderView(header);

        View footer = getLayoutInflater().inflate(R.layout.information_table2, null);
        list.addFooterView(footer, null, false);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //listview有头部，下标从1开始
                if (position == 0) {
                    return;
                }
                Log.d(TAG, "position:" + position);
                Equipment.RepairInfo repairInfo = (Equipment.RepairInfo) data.get(position - 1);
                Intent intent = new Intent(mActivity, InformationDetailActivity.class);
                intent.putExtra("repairInfo", repairInfo);
                startActivity(intent);
            }
        });


    }

    public void getRepairList() {
        Observable<Response> observable = SoapObservable.getAnyObservable(new RxObserverableCallBack() {
            @Override
            public String doWebRequest() {
                return SoapUtils.getRepairList();
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers
                        .mainThread()).subscribe(new SoapObserver<Response>() {

            @Override
            public void onSuccess(String s) {
                Log.d(TAG, s);
                data = (ArrayList) JSONArray.parseArray(s, Equipment.RepairInfo.class);
                list.setAdapter(new InformationAdapter(mActivity, data));//没有数据
                if (data.size() == 0) {
                    View nodata = getLayoutInflater().inflate(R.layout.information_nodata, null);
                    list.addFooterView(nodata, null, false);
                }
            }

            @Override
            public void onFailure(String s) {
                Log.d("MyRepairActivity", s);
                CommonUtil.showToast(mActivity, s);
                View nodata = getLayoutInflater().inflate(R.layout.information_nodata, null);
                list.addFooterView(nodata, null, false);
            }
        });
    }
}

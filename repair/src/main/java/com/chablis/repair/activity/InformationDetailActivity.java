package com.chablis.repair.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chablis.repair.R;
import com.chablis.repair.rx.Response;
import com.chablis.repair.rx.RxObserverableCallBack;
import com.chablis.repair.rx.SoapObservable;
import com.chablis.repair.rx.SoapObserver;
import com.chablis.repair.adapter.ImagesAdapter;
import com.chablis.repair.base.BaseActivity;
import com.chablis.repair.model.EquImage;
import com.chablis.repair.model.Equipment;
import com.chablis.repair.model.RepairDetail;
import com.chablis.repair.utils.SoapUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InformationDetailActivity extends BaseActivity {
    private ImagesAdapter mAdapter;

    @BindView(R.id.tv_title2)
    TextView tvTitle2;
    @BindView(R.id.tv_divice2)
    TextView tvDivice2;
    @BindView(R.id.tv_class2)
    TextView tvClass2;
    @BindView(R.id.tv_class4)
    TextView tvClass4;
    @BindView(R.id.tv_time2)
    TextView tvTime2;
    @BindView(R.id.tv_state2)
    TextView tvState2;
    @BindView(R.id.tv_answer2)
    TextView tvAnswer2;
    @BindView(R.id.rv_images1)
    RecyclerView rvImages1;
    @BindView(R.id.rv_images2)
    RecyclerView rvImages2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        final Equipment.RepairInfo repairInfo = (Equipment.RepairInfo) intent.getSerializableExtra("repairInfo");
//        Log.d("InformationDetailActivi", "ac66136a351a43829a3e4bfca5ec4d39");
//        getRepairData("ac66136a351a43829a3e4bfca5ec4d39");
    }

    /*public void getRepairData(final String repairId) {
        Observable<String> observable = SoapObservable.getObservable(new RxObserverableCallBack() {
            @Override
            public String doWebRequest() {
                return SoapUtils.repairDetail(repairId);
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers
                        .mainThread()).subscribe(new SoapObserver<Response>(){
            @Override
            public void onSuccess(String s) {
                String[] info = s.split("\\|");
                RepairDetail repairDetail = JSONObject.parseObject(info[0], RepairDetail.class);
                ArrayList<EquImage> equImages = (ArrayList<EquImage>) JSONObject.parseArray(info[1], EquImage.class);
                tvTitle2.setText(repairDetail.getTITLE());
                tvDivice2.setText(repairDetail.getPROBLEMDTION());
                tvClass2.setText(repairDetail.getBIGCLASS());
                tvClass4.setText(repairDetail.getSMALLCLASS());
                tvTime2.setText(repairDetail.getTIMEVIEW());
                //TODO 后台没有状态字段                tvState2
                tvAnswer2.setText(repairDetail.getSERVICERESULT());
                mAdapter = new ImagesAdapter(equImages);
                initRecylerView();
            }

            @Override
            public void onFailure(String s) {
                Log.d("InformationDetailActivi", s);
            }
        });

    }*/

    public void initRecylerView() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(mActivity, 4, GridLayoutManager.VERTICAL, false);//设置为一个4列的纵向网格布局
        rvImages1.setLayoutManager(mLayoutManager);
//        rvImages2.setLayoutManager(mLayoutManager);
        rvImages1.setAdapter(mAdapter);
    }
}


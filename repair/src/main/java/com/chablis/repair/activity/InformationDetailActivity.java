package com.chablis.repair.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chablis.repair.R;
import com.chablis.repair.adapter.ImagesAdapter;
import com.chablis.repair.adapter.SpaceItemDecoration;
import com.chablis.repair.base.BaseTitleActivity;
import com.chablis.repair.model.Equipment;
import com.chablis.repair.model.RepairDetail;
import com.chablis.repair.rx.Response;
import com.chablis.repair.rx.RxObserverableCallBack;
import com.chablis.repair.rx.SoapObservable;
import com.chablis.repair.rx.SoapObserver;
import com.chablis.repair.utils.CommonUtil;
import com.chablis.repair.utils.SoapUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class InformationDetailActivity extends BaseTitleActivity {
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
        setContentView(R.layout.activity_information_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final Equipment.RepairInfo repairInfo = (Equipment.RepairInfo) intent.getSerializableExtra("repairInfo");
        getRepairData(repairInfo.getMAINTAIN_ID());
    }

    public void getRepairData(final String repairId) {
        hud= KProgressHUD.create(mActivity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        Observable<Response> observable = SoapObservable.getAnyObservable(new RxObserverableCallBack() {
            @Override
            public String doWebRequest() {
                return SoapUtils.repairDetail(repairId);
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers
                        .mainThread()).subscribe(new SoapObserver<Response>() {
            @Override
            public void onSuccess(String s) {
                hud.dismiss();
                Log.d("InformationDetailActivi", s);
                final RepairDetail repairDetail = JSONObject.parseObject(s, RepairDetail.class);
                tvTitle2.setText(repairDetail.getClientInfo().getTITLE());
                tvDivice2.setText(repairDetail.getClientInfo().getPROBLEMDTION());
                tvClass2.setText(repairDetail.getClientInfo().getBIGCLASS());
                tvClass4.setText(repairDetail.getClientInfo().getSMALLCLASS());
                tvTime2.setText(repairDetail.getClientInfo().getTIMEVIEW());
                tvState2.setText(repairDetail.getClientInfo().getSTATUS().equals("0") ? "未维修" : "已维修");
                tvAnswer2.setText(repairDetail.getClientInfo().getSERVICERESULT());
                mAdapter = new ImagesAdapter(repairDetail.getDePiclist());
                ImagesAdapter answerAdapter = new ImagesAdapter(repairDetail.getResultPiclist());
                initRecylerView(rvImages1, mAdapter);
                initRecylerView(rvImages2, answerAdapter);
                mAdapter.setItemClickListener(new ImagesAdapter.onItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(mActivity, BigPictureActivity.class);
                        ArrayList<RepairDetail.RepairImage> images = repairDetail.getDePiclist();
                        intent.putExtra("url", images.get(position).getFILE_URL());
                        startActivity(intent);
                    }
                });
                answerAdapter.setItemClickListener(new ImagesAdapter.onItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(mActivity, BigPictureActivity.class);
                        ArrayList<RepairDetail.RepairImage> images = repairDetail.getResultPiclist();
                        intent.putExtra("url", images.get(position).getFILE_URL());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(String s) {
                hud.dismiss();
                Log.d("InformationDetailActivi", s);
                CommonUtil.showToast(mActivity, s);
            }
        });

    }

    public void initRecylerView(RecyclerView recyclerView, ImagesAdapter adapter) {
        GridLayoutManager mLayoutManager = new GridLayoutManager(mActivity, 4);//设置为一个4列的纵向网格布局
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.space);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

}


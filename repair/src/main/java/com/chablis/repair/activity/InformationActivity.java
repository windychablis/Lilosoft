package com.chablis.repair.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chablis.repair.R;
import com.chablis.repair.adapter.InformationAdapter;
import com.chablis.repair.base.BaseActivity;
import com.chablis.repair.model.Equipment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InformationActivity extends BaseActivity implements AbsListView.OnScrollListener {
    @BindView(R.id.linearLayout3)
    LinearLayout linearLayout3;
    private ArrayList data;
    private Equipment equipment;

    @BindView(R.id.list)
    ListView list;

    private View header;
    private View nodata;
    private View footer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        ButterKnife.bind(this);
        final Intent intent = getIntent();
        final String info = intent.getStringExtra("info");

        equipment = JSONObject.parseObject(info, Equipment.class);

        data = (ArrayList) equipment.getRepairList();


        list.setAdapter(new InformationAdapter(this, data));

        header = getLayoutInflater().inflate(R.layout.information_table1, null);
        TextView tvNumber = ButterKnife.findById(header, R.id.tv_describe);
        TextView tvType = ButterKnife.findById(header, R.id.tv_type);
        TextView tvArea1 = ButterKnife.findById(header, R.id.tv_area1);
        TextView tvDate = ButterKnife.findById(header, R.id.tv_date);
        TextView tvArea2 = ButterKnife.findById(header, R.id.tv_area2);
        TextView tvFloor = ButterKnife.findById(header, R.id.tv_floor);
        TextView tvIp = ButterKnife.findById(header, R.id.tv_ip);
        TextView tvRemarks = ButterKnife.findById(header, R.id.tv_remarks);
        tvNumber.setText(equipment.getClientinfo().getCLIENT_TYPE());
        tvType.setText(equipment.getClientinfo().getTERM_TYPE());
        tvArea1.setText(equipment.getClientinfo().getAREA_CODE());
        tvArea2.setText(equipment.getClientinfo().getREMARK());
        tvDate.setText(equipment.getClientinfo().getTIMEVIEW());
        tvFloor.setText(equipment.getClientinfo().getFLOORNUM());
        tvIp.setText(equipment.getClientinfo().getCLIENT_IP());
        tvRemarks.setText(equipment.getClientinfo().getREMARK());

        list.addHeaderView(header, null, false);
        View suspend = getLayoutInflater().inflate(R.layout.repair_table1, null);
        list.addHeaderView(suspend, null, false);

        //没有数据
        if (data.size() == 0) {
            nodata = getLayoutInflater().inflate(R.layout.information_nodata, null);
            list.addFooterView(nodata, null, false);

        }
        footer = getLayoutInflater().inflate(R.layout.information_table2, null);
        list.addFooterView(footer, null, false);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("InformationActivity", "position:" + position);
                //listview有头部，下标从1开始
                Equipment.RepairInfo repairInfo = equipment.getRepairList().get(position - 2);
                intent.putExtra("repairInfo", repairInfo);
                intent.setClass(mActivity, InformationDetailActivity.class);
                startActivity(intent);

            }
        });

        list.setOnScrollListener(this);


    }

    @OnClick({R.id.tv_back, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                mActivity.finish();
                break;
            case R.id.button:
                Intent intent = new Intent(mActivity, ReportActivity.class);
                intent.putExtra("clientinfo", equipment.getClientinfo());
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem >= 1) {
            linearLayout3.setVisibility(View.VISIBLE);
        } else {
            linearLayout3.setVisibility(View.GONE);
        }
    }
}

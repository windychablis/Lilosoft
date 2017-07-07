package com.chablis.repair.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class InformationActivity extends BaseActivity {
    private ArrayList data;
    private Equipment equipment;

    @BindView(R.id.list)
    ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        ButterKnife.bind(this);
        final Intent intent = getIntent();
        final String info = intent.getStringExtra("info");

        equipment = JSONObject.parseObject(info, Equipment.class);
        Log.d("InformationActivity", "equipment:" + equipment);

        data = (ArrayList) equipment.getRepairList();


        list.setAdapter(new InformationAdapter(this, data));

        View header = getLayoutInflater().inflate(R.layout.information_table1, null);
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

        list.addHeaderView(header);

        //没有数据
        if (data.size() == 0) {
            View nodata = getLayoutInflater().inflate(R.layout.information_nodata, null);
            list.addFooterView(nodata);
        }


        View footer = getLayoutInflater().inflate(R.layout.information_table2, null);
        list.addFooterView(footer);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //listview有头部，下标从1开始
                Log.d("InformationActivity", "position:" + position);
                Equipment.RepairInfo repairInfo=equipment.getRepairList().get(position-1);
                intent.putExtra("repairInfo",repairInfo);
                intent.setClass(mActivity,InformationDetailActivity.class);
                startActivity(intent);

            }
        });


    }

    @OnClick({R.id.tv_back, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                mActivity.finish();
                break;
            case R.id.button:
                break;
        }
    }
}

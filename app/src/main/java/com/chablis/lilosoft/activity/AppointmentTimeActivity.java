package com.chablis.lilosoft.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.adapter.AppointmentDateAdapter;
import com.chablis.lilosoft.adapter.AppointmentTimeAdapter;
import com.chablis.lilosoft.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppointmentTimeActivity extends BaseActivity {

    @BindView(R.id.rv_date)
    RecyclerView rvDate;
    @BindView(R.id.listview)
    ListView listview;
    private ArrayList<String> dates;
    private AppointmentDateAdapter dateAdapter;
    private AppointmentTimeAdapter timeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_time);
        ButterKnife.bind(this);
        getData();
        initView();
    }

    public void initView() {
        //设置上面的日期列表
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvDate.setLayoutManager(linearLayoutManager);
        dateAdapter = new AppointmentDateAdapter(mActivity, dates);
        rvDate.setAdapter(dateAdapter);
        dateAdapter.setmOnItemClickLitener(new AppointmentDateAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                //position是点击的位置
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

        //设置下面的时间列表
        timeAdapter=new AppointmentTimeAdapter(mActivity,null);
        listview.setAdapter(timeAdapter);

    }

    public void getData() {
        dates = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            dates.add("03-18");
        }
    }

    @OnClick(R.id.tv_close)
    public void onViewClicked() {
        this.finish();
    }
}

package com.chablis.lilosoft.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.adapter.AppointmentDateAdapter;
import com.chablis.lilosoft.adapter.AppointmentTimeAdapter;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.base.Global;
import com.chablis.lilosoft.model.AffairItem;
import com.chablis.lilosoft.model.AppointmentTime;
import com.chablis.lilosoft.utils.ToastUtils;
import com.chablis.lilosoft.utils.WebUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppointmentTimeActivity extends BaseActivity {

    @BindView(R.id.rv_date)
    RecyclerView rvDate;
    @BindView(R.id.listview)
    ListView listview;
    private AppointmentDateAdapter dateAdapter;
    private AppointmentTimeAdapter timeAdapter;

    private AffairItem affairItem;
    public String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_time);
        ButterKnife.bind(this);
        affairItem= (AffairItem) getIntent().getSerializableExtra("affairItem");
        getDate();
    }

    public void initView(final ArrayList<HashMap> dates) {
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
                date=dates.get(position).get("datas").toString();
                getTime(date);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }

    private void getDate() {
        new AsyncTask<String, Integer, String>() {

            @Override
            protected String doInBackground(String... params) {
                return WebUtil.getAppointmentTime(Global.areacode);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Type type = new TypeToken<ArrayList<HashMap>>() {
                }.getType();
                Gson gson = new Gson();
                ArrayList<HashMap> times = gson.fromJson(s, type);
                if (times!=null) {
                    date=times.get(0).get("datas").toString();
                    getTime(date);
                    initView(times);
                }else{
                    ToastUtils.showToast(mActivity,"暂无数据");
                }

            }
        }.execute();
    }

    public void getTime(final String date){
        new AsyncTask<String, Integer, String>() {

            @Override
            protected String doInBackground(String... params) {
                return WebUtil.getAppointmentData(date,affairItem.getProject_no(),Global.areacode);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s!=null) {
                    Log.d("AppointmentTimeActivity", s);
                    Type type = new TypeToken<ArrayList<AppointmentTime>>() {
                    }.getType();
                    Gson gson = new Gson();
                    ArrayList<AppointmentTime> times = gson.fromJson(s, type);
                    //设置下面的时间列表
                    timeAdapter=new AppointmentTimeAdapter(mActivity,times);
                    listview.setAdapter(timeAdapter);
                }

            }
        }.execute();
    }

    @OnClick(R.id.tv_close)
    public void onViewClicked() {
        this.finish();
    }
}

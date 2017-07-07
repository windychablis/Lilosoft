package com.chablis.repair.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.chablis.repair.R;
import com.chablis.repair.base.SoapAsyncTask;
import com.chablis.repair.base.TaskCallBack;
import com.chablis.repair.model.Equipment;
import com.chablis.repair.utils.SoapUtils;

public class InformationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_detail);
        Intent intent=getIntent();
        Equipment.RepairInfo repairInfo = (Equipment.RepairInfo) intent.getSerializableExtra("repairInfo");
        Log.d("InformationDetailActivi", "ac66136a351a43829a3e4bfca5ec4d39");
        getRepairDetail(repairInfo.getMAINTAIN_ID());
    }

    public void getRepairDetail(final String id){
        new SoapAsyncTask(new TaskCallBack() {
            @Override
            public String doInBackground() {
                return SoapUtils.repairDetail(id);
            }

            @Override
            public void onSuccess(String result) {
                Log.d("InformationDetailActivi", result);
            }

            @Override
            public void onFailure(String msg) {
                Log.d("InformationDetailActivi", msg);
            }
        }).execute();
    }
}

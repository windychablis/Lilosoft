package com.chablis.repair.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.chablis.repair.R;
import com.chablis.repair.model.Equipment;

public class InformationDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_detail);
        Intent intent=getIntent();
        Equipment.RepairInfo repairInfo = (Equipment.RepairInfo) intent.getSerializableExtra("repairInfo");
        Log.d("InformationDetailActivi", repairInfo.toString());
    }
}

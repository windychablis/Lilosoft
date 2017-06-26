package com.chablis.repair;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chablis.repair.adapter.InformationAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InformationActivity extends AppCompatActivity {
    private ArrayList data;

    @BindView(R.id.list)
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        ButterKnife.bind(this);
        data = new ArrayList();
        for (int i = 0; i < 8; i++) {
            data.add("aaaaaaaa" + i);
        }

        list.setAdapter(new InformationAdapter(this, data));

        View header=getLayoutInflater().inflate(R.layout.information_table1,null);
        list.addHeaderView(header);

        //没有数据
        if (data.size()==0) {
            View nodata = getLayoutInflater().inflate(R.layout.information_nodata, null);
            list.addFooterView(nodata);
        }



        View footer=getLayoutInflater().inflate(R.layout.information_table2,null);
        list.addFooterView(footer);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //listview有头部，下标从1开始
                Log.d("InformationActivity", "position:" + position);
            }
        });



    }
}

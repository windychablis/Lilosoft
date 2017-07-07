package com.chablis.repair.activity;

import android.os.Bundle;
import android.view.View;

import com.chablis.repair.R;
import com.chablis.repair.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_search, R.id.tv_repair, R.id.tv_me})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                nextActivity(QRCodeActivity.class);
                break;
            case R.id.tv_repair:
                break;
            case R.id.tv_me:
                break;
        }
    }
}

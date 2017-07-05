package com.chablis.repair.activity;

import android.os.Bundle;

import com.chablis.repair.R;
import com.chablis.repair.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.loginBtn)
    public void onViewClicked() {
        nextActivity(MainActivity.class);
    }
}

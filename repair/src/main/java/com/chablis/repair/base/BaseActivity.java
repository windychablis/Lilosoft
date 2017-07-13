package com.chablis.repair.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.io.Serializable;

public class BaseActivity extends AppCompatActivity {
    protected BaseActivity mActivity;
    protected FragmentManager mFragmentManager;
    protected String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mFragmentManager = getSupportFragmentManager();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onResume() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }


    public void nextActivity(Class<?> clazz, boolean isPlayAnim, String name, Serializable s) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        if (null != name && !name.trim().equals("")) {
            intent.putExtra(name, s);
        }
        startActivity(intent);
        if (isPlayAnim) {
//            overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        }
    }

    public void nextActivity(Class<?> clazz) {
        nextActivity(clazz, true);
    }

    public void nextActivity(Class<?> clazz, boolean isPlayAnim) {
        nextActivity(clazz, true, null, null);
    }

    public void nextActivity(Class<?> clazz, String name, Serializable s) {
        nextActivity(clazz, true, name, s);
    }


}

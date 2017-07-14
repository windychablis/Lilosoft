package com.chablis.repair.activity;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.chablis.repair.base.BaseActivity;
import com.chablis.repair.base.SoapAsyncTask;
import com.chablis.repair.base.TaskCallBack;
import com.chablis.repair.model.User;
import com.chablis.repair.utils.CommonUtil;
import com.chablis.repair.utils.PrefUtils;
import com.chablis.repair.utils.SoapUtils;

public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        autoLogin();
    }

    private void autoLogin() {
        final String username, password;
        if (PrefUtils.getCacheLoginState()) {
            username = PrefUtils.getUserName();
            password = PrefUtils.getUserPass();
            new SoapAsyncTask(new TaskCallBack() {
                @Override
                public String doInBackground() {
                    return SoapUtils.login(username, password);
                }

                @Override
                public void onSuccess(String result) {
                    Log.d("LoginActivity", result);
                    appContext.user = JSONObject.parseObject(result, User.class);
                    PrefUtils.putCacheLoginState(true);
                    PrefUtils.saveUserName(username);
                    PrefUtils.saveUserPass(password);
                    nextActivity(MainActivity.class);
                    mActivity.finish();
                    CommonUtil.showToast(mActivity, "登录成功");
                }

                @Override
                public void onFailure(String msg) {
                    Log.d("LoginActivity", msg);
                    nextActivity(LoginActivity.class);
                    CommonUtil.showToast(mActivity, msg);
                }


            }).execute();
        } else {
            nextActivity(LoginActivity.class);
            mActivity.finish();
        }
    }
}

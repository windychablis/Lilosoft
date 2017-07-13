package com.chablis.repair.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.chablis.repair.R;
import com.chablis.repair.base.BaseActivity;
import com.chablis.repair.base.JsonUtils;
import com.chablis.repair.base.SoapAsyncTask;
import com.chablis.repair.base.TaskCallBack;
import com.chablis.repair.model.User;
import com.chablis.repair.utils.CommonUtil;
import com.chablis.repair.utils.PermissionUtils;
import com.chablis.repair.utils.PrefUtils;
import com.chablis.repair.utils.SoapUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_pass)
    EditText etPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        PermissionUtils.getPermission(mActivity);
    }

    @OnClick(R.id.loginBtn)
    public void loginBtnClick() {
        String username, password;

            username = etName.getText().toString();
            password = etPass.getText().toString();

        login(username, password);

    }

    public void login(final String username, final String password) {
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
                CommonUtil.showToast(mActivity, msg);
            }


        }).execute();
    }
}

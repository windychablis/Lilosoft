package com.chablis.repair.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.chablis.repair.R;
import com.chablis.repair.base.BaseActivity;
import com.chablis.repair.base.SoapAsyncTask;
import com.chablis.repair.base.TaskCallBack;
import com.chablis.repair.utils.CommonUtil;
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
    }

    @OnClick(R.id.loginBtn)
    public void loginBtnClick() {
        /*nextActivity(MainActivity.class);
        mActivity.finish();*/
        final String username= etName.getText().toString();
        final String password= etPass.getText().toString();
        new SoapAsyncTask(new TaskCallBack() {
            @Override
            public String doInBackground() {
                return SoapUtils.login(username, password);
            }

            @Override
            public void onSuccess(String result) {
                Log.d("LoginActivity", result);
                nextActivity(MainActivity.class);
                mActivity.finish();
                CommonUtil.showToast(mActivity,"登录成功");
            }

            @Override
            public void onFailure(String msg) {
                Log.d("LoginActivity", msg);
                CommonUtil.showToast(mActivity,msg);
            }


        }).execute();
    }
}

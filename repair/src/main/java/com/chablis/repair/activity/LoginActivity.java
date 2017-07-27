package com.chablis.repair.activity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.alibaba.fastjson.JSONObject;
import com.chablis.repair.R;
import com.chablis.repair.base.BaseActivity;
import com.chablis.repair.base.SoapAsyncTask;
import com.chablis.repair.base.TaskCallBack;
import com.chablis.repair.model.User;
import com.chablis.repair.utils.CommonUtil;
import com.chablis.repair.utils.PermissionUtils;
import com.chablis.repair.utils.PrefUtils;
import com.chablis.repair.utils.SoapUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

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
        if(PrefUtils.getUrl().isEmpty()) settingUrlDialog();
    }

    public void settingUrlDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.s_content)
                .content(R.string.s_url)
                .theme(Theme.LIGHT)
                .canceledOnTouchOutside(false)
                .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_URI)
                .input(R.string.s_url, R.string.s_port, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        Log.d("LoginActivity", input.toString());
                        SoapUtils.settingUrl = input.toString();
                        PrefUtils.saveUrl(input.toString());
                    }
                }).show();
    }

    @OnClick(R.id.loginBtn)
    public void loginBtnClick() {
        if (PrefUtils.getUrl().isEmpty()) {
            settingUrlDialog();
            return;
        }
        String username, password;

        username = etName.getText().toString();
        password = etPass.getText().toString();

        login(username, password);

    }

    public void login(final String username, final String password) {
        hud = KProgressHUD.create(mActivity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        new SoapAsyncTask(new TaskCallBack() {
            @Override
            public String doInBackground() {
                return SoapUtils.login(username, password);
            }

            @Override
            public void onSuccess(String result) {
                hud.dismiss();
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
                hud.dismiss();
                Log.d("LoginActivity", msg);
                CommonUtil.showToast(mActivity, msg);
            }


        }).execute();
    }

    @OnClick(R.id.iv_logo)
    public void onViewClicked() {
        settingUrlDialog();
    }
}

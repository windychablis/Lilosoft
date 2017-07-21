package com.chablis.repair.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.chablis.repair.R;
import com.chablis.repair.base.BaseActivity;
import com.chablis.repair.base.SoapAsyncTask;
import com.chablis.repair.base.TaskCallBack;
import com.chablis.repair.utils.CommonUtil;
import com.chablis.repair.utils.SoapUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QRCodeActivity extends BaseActivity {

    @BindView(R.id.et_number)
    EditText etNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.searchBtn, R.id.tv_scan,R.id.tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.searchBtn:
                String num=etNumber.getText().toString();
                getEquipmentInfo(num);
                break;
            case R.id.tv_scan:
                nextActivity(ScanActivity.class);
                break;
            case R.id.tv_back:
                mActivity.finish();
                break;
        }
    }

    /**
     *  获取设备信息
     */
    public void getEquipmentInfo(final String num) {
        hud= KProgressHUD.create(mActivity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        new SoapAsyncTask(new TaskCallBack() {
            @Override
            public String doInBackground() {
                return SoapUtils.equipmentInfo(num);
            }

            @Override
            public void onSuccess(String result) {
                hud.dismiss();
                Log.d("QRCodeActivity", result);
                Intent intent=new Intent(mActivity,InformationActivity.class);
                intent.putExtra("info",result);
                startActivity(intent);
            }

            @Override
            public void onFailure(String msg) {
                hud.dismiss();
                Log.d("QRCodeActivity", msg);
                CommonUtil.showToast(mActivity,msg);
            }
        }).execute();
    }


}

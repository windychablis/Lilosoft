package com.chablis.repair.activity;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import com.chablis.repair.R;
import com.chablis.repair.base.BaseActivity;
import com.chablis.repair.base.SoapAsyncTask;
import com.chablis.repair.base.TaskCallBack;
import com.chablis.repair.utils.CommonUtil;
import com.chablis.repair.utils.SoapUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;

public class ScanActivity extends BaseActivity implements QRCodeView.Delegate {
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    private String info;
    @BindView(R.id.zxingview)
    QRCodeView mQRCodeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);
        mQRCodeView.setDelegate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_BACK);
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);

        mQRCodeView.showScanRect();
        mQRCodeView.startSpot();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
//        vibrate();
//        mQRCodeView.startSpot();
        getEquipmentInfo(result);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
    }

    @OnClick(R.id.tv_back)
    public void onViewClicked() {
        this.finish();
    }

    /**
     * 获取设备信息
     */
    public void getEquipmentInfo(final String num) {
        new SoapAsyncTask(new TaskCallBack() {
            @Override
            public String doInBackground() {
                return SoapUtils.equipmentInfo(num);
            }

            @Override
            public void onSuccess(String result) {
                Log.d("QRCodeActivity", result);
                Intent intent=new Intent(mActivity,InformationActivity.class);
                intent.putExtra("info",result);
                startActivity(intent);
            }

            @Override
            public void onFailure(String msg) {
                Log.d("ScanActivity", msg);
                CommonUtil.showToast(mActivity,msg);
                vibrate();
                mQRCodeView.startSpot();
            }
        }).execute();
    }


}

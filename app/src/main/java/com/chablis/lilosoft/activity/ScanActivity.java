package com.chablis.lilosoft.activity;

import android.content.Intent;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.model.Affair;
import com.chablis.lilosoft.model.AffairItem;
import com.chablis.lilosoft.model.MapAddress;
import com.chablis.lilosoft.model.Material;
import com.chablis.lilosoft.utils.WebUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;

public class ScanActivity extends BaseActivity implements QRCodeView.Delegate {
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;
    @BindView(R.id.zxingview)
    QRCodeView mQRCodeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);
        mQRCodeView.setDelegate(this);
        getData("2");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
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
        Log.i(TAG, "result:" + result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
//        vibrate();
//        mQRCodeView.startSpot();
        getData(result);
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

    @OnClick(R.id.tv_back)
    public void onViewClicked() {
        this.finish();
    }

    public void getData(final String id) {
        new AsyncTask<String, Integer, String[]>() {

            @Override
            protected String[] doInBackground(String... params) {
                return WebUtil.getAffairByQRCode(id);
            }

            @Override
            protected void onPostExecute(String[] s) {
                super.onPostExecute(s);
                if (s != null) {
                    if (s[0].equals("0")) {
                        //事项列表
                        Intent intent=new Intent(mActivity,MatterListActivity.class);
                        intent.putExtra("affairs",s[1]);
                        startActivity(intent);
                    } else {
                        //办事指南
                        Gson gson = new Gson();
                        Material material=gson.fromJson(s[1], Material.class);
                        Intent intent=new Intent(mActivity,AffairActivity.class);
                        intent.putExtra("from","scan");
                        intent.putExtra("material",material);
                        startActivity(intent);
                    }
                }
            }
        }.execute();
    }
}

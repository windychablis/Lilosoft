package com.chablis.repair.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import com.chablis.repair.R;
import com.chablis.repair.base.AppConfig;
import com.chablis.repair.base.BaseTitleActivity;
import com.chablis.repair.model.Equipment;
import com.chablis.repair.rx.PermissionObserver;
import com.chablis.repair.utils.PermissionUtils;
import com.chablis.repair.widget.ImageManagerView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportActivity extends BaseTitleActivity {
    private final int RESULT_TAKE_PHOTO = 0;

    private String mCurrentPhotoPath;
    @BindView(R.id.image_managerView)
    ImageManagerView imageManagerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_report);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Equipment.ClientInfo clientInfo = (Equipment.ClientInfo) intent.getSerializableExtra("clientinfo");
        imageManagerView.setOnImageClickListener(new ImageManagerView.OnImageClickListener() {
            @Override
            public void onImageClick(int position, String filePath, ImageView iv) {

            }

            @Override
            public void onDeleteClick(int position, String filePath) {
                imageManagerView.removeImage(position);
            }

            @Override
            public void onAddClick() {
                imageManagerView.addImage(null);
                getPermission();
            }
        });
    }

    /**
     * 激活系统拍照
     */
    /*private void startTakePicture() {
        imageName = getPhotoFileName();
        tempFile = new File(appConfig.CACHE_UPLOAD_IMAGES_DIR, imageName);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定调用相机拍照后照片的储存路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, IMAGE_TAKE_RESULT);
    }*/
    public void getPermission() {
        RxPermissions rxPermissions = new RxPermissions(mActivity);
        if (rxPermissions.isGranted(Manifest.permission.CAMERA)) {
            takeCamera();
            return;
        }
        PermissionUtils.getPermission(mActivity);
    }

    private void takeCamera() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(mActivity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            photoFile = createImageFile();
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
            }
        }

        startActivityForResult(takePictureIntent, RESULT_TAKE_PHOTO);//跳转界面传回拍照所得数据
    }

    private File createImageFile() {
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File image = null;
        try {
            image = File.createTempFile(
                    generateFileName(),  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public static String generateFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        return imageFileName;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK ) {
            if (requestCode == RESULT_TAKE_PHOTO){{
                Log.d("ReportActivity", mCurrentPhotoPath);
            }}
        }
    }
}

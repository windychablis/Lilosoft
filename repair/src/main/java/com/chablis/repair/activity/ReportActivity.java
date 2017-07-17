package com.chablis.repair.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.OptionsPickerView;
import com.chablis.repair.R;
import com.chablis.repair.base.BaseTitleActivity;
import com.chablis.repair.base.SoapAsyncTask;
import com.chablis.repair.base.TaskCallBack;
import com.chablis.repair.model.BreakType;
import com.chablis.repair.model.Equipment;
import com.chablis.repair.rx.Response;
import com.chablis.repair.rx.RxObserverableCallBack;
import com.chablis.repair.rx.SoapObservable;
import com.chablis.repair.rx.SoapObserver;
import com.chablis.repair.utils.CommonUtil;
import com.chablis.repair.utils.PermissionUtils;
import com.chablis.repair.utils.SoapUtils;
import com.chablis.repair.widget.ImageManagerView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ReportActivity extends BaseTitleActivity {
    private final int RESULT_TAKE_PHOTO = 0;
    @BindView(R.id.tv_class2)
    TextView tvClass2;
    @BindView(R.id.tv_class4)
    TextView tvClass4;

    private String mCurrentPhotoPath;
    @BindView(R.id.image_managerView)
    ImageManagerView imageManagerView;
    private OptionsPickerView pickerView1;
    private OptionsPickerView pickerView2;
    private BreakType breakType;
    private List<String> imageUrls;
    private Equipment.ClientInfo clientInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_report);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        clientInfo = (Equipment.ClientInfo) intent.getSerializableExtra("clientinfo");
        getClasses();
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
                getPermission();
            }
        });
    }

    /**
     * 获取大类小类
     */
    private void getClasses() {

        new SoapAsyncTask(new TaskCallBack() {
            @Override
            public String doInBackground() {
                return SoapUtils.getClasses();
            }

            @Override
            public void onSuccess(String result) {
                Log.d("RepairDetailActivity", result);
                breakType = JSONObject.parseObject(result, BreakType.class);
                initView();
            }

            @Override
            public void onFailure(String msg) {
                Log.d("RepairDetailActivity", msg);
                CommonUtil.showToast(mActivity, msg);
            }
        }).execute();
    }

    private void initView() {
        List bigList = new ArrayList();
        for (BreakType.BigClassList big : breakType.getBigClassList()) {
            bigList.add(big.getDICT_NAME());
        }
        List smallList = new ArrayList();
        for (BreakType.SmallClassList small : breakType.getSmallClassList()) {
            smallList.add(small.getDICT_NAME());
        }
        pickerView1 = new OptionsPickerView.Builder(mActivity, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String str = breakType.getBigClassList().get(options1).getDICT_NAME();
                tvClass2.setText(str);
            }
        }).setSelectOptions(0)
                .setLineSpacingMultiplier(2.0f)//设置两横线之间的间隔倍数
                .build();
        pickerView1.setPicker(bigList);

        pickerView2 = new OptionsPickerView.Builder(mActivity, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String str = breakType.getSmallClassList().get(options1).getDICT_NAME();
                tvClass4.setText(str);
            }
        }).setSelectOptions(0)
                .setLineSpacingMultiplier(2.0f)//设置两横线之间的间隔倍数
                .build();
        pickerView2.setPicker(smallList);
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
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_TAKE_PHOTO) {
                {
                    imageManagerView.addImage(mCurrentPhotoPath);
                    Log.d("ReportActivity", mCurrentPhotoPath);

                }
            }
        }
    }


    @OnClick({R.id.rv_class, R.id.rv_class2, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rv_class:
                pickerView1.show();
                break;
            case R.id.rv_class2:
                pickerView2.show();
                break;
            case R.id.button:
                uploadImage();
                break;
        }
    }

    /**
     * 图片上传
     */
    public void uploadImage() {
        imageUrls = imageManagerView.getAllImages();
        for (String url:imageUrls){
            Bitmap bitmap= BitmapFactory.decodeFile(url);
            String str=CommonUtil.Bitmap2StrByBase64(bitmap);
            Log.d("ReportActivity", CommonUtil.getFileName(url));
            rxUpload(str,CommonUtil.getFileName(url),"009300f8697640daaf4abcb5ca995c67");

        }
    }

    private void rxUpload(final String image, final String fileName, final String mainTainId){
        Observable<Response> observable = SoapObservable.getAnyObservable(new RxObserverableCallBack() {
            @Override
            public String doWebRequest() {
                return SoapUtils.updateImage(image,fileName,mainTainId);
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers
                        .mainThread()).subscribe(new SoapObserver<Response>() {
            @Override
            public void onSuccess(String s) {
                Log.d("ReportActivity", s);
            }

            @Override
            public void onFailure(String s) {
                Log.d("ReportActivity", s);
            }
        });
    }
}

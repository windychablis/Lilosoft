package com.chablis.repair.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.chablis.repair.utils.FileUtils;
import com.chablis.repair.utils.PermissionUtils;
import com.chablis.repair.utils.SoapUtils;
import com.chablis.repair.utils.StringUtils;
import com.chablis.repair.widget.ImageManagerView;
import com.kaopiz.kprogresshud.KProgressHUD;
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
    @BindView(R.id.tv_title1)
    EditText tvTitle1;
    @BindView(R.id.tv_title2)
    EditText tvTitle2;

    private String mCurrentPhotoPath;
    @BindView(R.id.image_managerView)
    ImageManagerView imageManagerView;
    private OptionsPickerView pickerView1;
    private OptionsPickerView pickerView2;
    private BreakType breakType;
    private List<String> imageUrls;
    private Equipment.ClientInfo clientInfo;
    private BreakType.ClassType currentBig;
    private BreakType.ClassType currentSmall;

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
                imageUrls.remove(position);
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
        final List<BreakType.ClassType> bigClassLists = breakType.getBigClassList();
        final List<BreakType.ClassType> smallClassLists = breakType.getSmallClassList();
        currentBig=bigClassLists.get(0);
        currentSmall=smallClassLists.get(0);
        tvClass2.setText(currentBig.getDICT_NAME());
        tvClass4.setText(currentSmall.getDICT_NAME());
        for (BreakType.ClassType big : bigClassLists) {
            bigList.add(big.getDICT_NAME());
        }
        List smallList = new ArrayList();
        for (BreakType.ClassType small : smallClassLists) {
            smallList.add(small.getDICT_NAME());
        }
        pickerView1 = new OptionsPickerView.Builder(mActivity, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                currentBig = bigClassLists.get(options1);
                String str = currentBig.getDICT_NAME();
                tvClass2.setText(str);
            }
        }).setSelectOptions(0)
                .setLineSpacingMultiplier(2.0f)//设置两横线之间的间隔倍数
                .build();
        pickerView1.setPicker(bigList);

        pickerView2 = new OptionsPickerView.Builder(mActivity, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                currentSmall = smallClassLists.get(options1);
                String str = currentSmall.getDICT_NAME();
                tvClass4.setText(str);
            }
        }).setSelectOptions(0)
                .setLineSpacingMultiplier(2.0f)//设置两横线之间的间隔倍数
                .build();
        pickerView2.setPicker(smallList);
    }


    public void getPermission() {
        RxPermissions rxPermissions = new RxPermissions(mActivity);
        if (rxPermissions.isGranted(Manifest.permission.CAMERA)) {
            takeCamera();
            return;
        }
        PermissionUtils.getPermission(mActivity);
    }

    private void takeCamera() {
        File photoFile = null;
        photoFile = createImageFile();
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(mActivity, "com.chablis.lilosoft.repair.fileprovider", photoFile);//通过FileProvider创建一个content类型的Uri
        } else {
            imageUri = Uri.fromFile(photoFile);
        }
        Intent intent = new Intent();
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }*/
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
        startActivityForResult(intent, RESULT_TAKE_PHOTO);

    }

    private File createImageFile() {
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        Log.d("ReportActivity", "storageDir:" + storageDir);
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
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
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
        } else {
            FileUtils.deleteDir(mCurrentPhotoPath);
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
        List<String> temp = new ArrayList<String>();
        for (String url : imageUrls) {
            Bitmap bitmap = BitmapFactory.decodeFile(url);
            FileUtils.deleteDir(url);
            String str = CommonUtil.Bitmap2StrByBase64(bitmap);
            temp.add(str);
        }
        String urls = StringUtils.list2String(temp);
        rxUploadImage(urls);
    }

    private void rxUploadImage(final String image) {
        hud= KProgressHUD.create(mActivity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("正在上传...")
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        Observable<Response> observable = SoapObservable.getAnyObservable(new RxObserverableCallBack() {
            @Override
            public String doWebRequest() {
                return SoapUtils.updateImage(image,"","1");
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers
                        .mainThread()).subscribe(new SoapObserver<Response>() {
            @Override
            public void onSuccess(String s) {
                hud.dismiss();
                String id = JSONObject.parseObject(s).getString("mainTainId");
                String title=tvTitle1.getText().toString().equals("")?currentBig.getDICT_NAME()+"|"+currentSmall.getDICT_NAME():"";
                String problem=tvTitle2.getText().toString();
                Log.d("ReportActivity", id);
                rxUploadRepairInfo(currentBig.getDICT_ID(),currentSmall.getDICT_ID(),title,problem,appContext.user.getUser_ID(),clientInfo.getCLIENT_TYPE(),id);
            }

            @Override
            public void onFailure(String s) {
                hud.dismiss();
                Log.d("ReportActivity", s);
                CommonUtil.showToast(mActivity, s);
            }
        });
    }

    private void rxUploadRepairInfo(final String bigClass, final String smallClass, final String title, final String problemDtion, final String repairUserId, final String clienttype, final String mainTainId) {
        Observable<Response> observable = SoapObservable.getAnyObservable(new RxObserverableCallBack() {
            @Override
            public String doWebRequest() {
                return SoapUtils.updateRepairInfo(bigClass, smallClass, title, problemDtion, repairUserId, clienttype, mainTainId);
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers
                        .mainThread()).subscribe(new SoapObserver<Response>() {
            @Override
            public void onSuccess(String s) {
                Log.d("ReportActivity", s);
                CommonUtil.showToast(mActivity,"上传成功");
                mActivity.finish();
            }

            @Override
            public void onFailure(String s) {
                Log.d("ReportActivity", s);
                CommonUtil.showToast(mActivity, s);
            }
        });
    }


}

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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chablis.repair.R;
import com.chablis.repair.adapter.ImagesAdapter;
import com.chablis.repair.adapter.SpaceItemDecoration;
import com.chablis.repair.base.BaseTitleActivity;
import com.chablis.repair.model.Equipment;
import com.chablis.repair.model.RepairDetail;
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

public class RepairDetailActivity extends BaseTitleActivity {

    @BindView(R.id.image_managerView)
    ImageManagerView imageManagerView;
    @BindView(R.id.tv_describe)
    TextView tvDescribe;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_area1)
    TextView tvArea1;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_area2)
    TextView tvArea2;
    @BindView(R.id.tv_floor)
    TextView tvFloor;
    @BindView(R.id.tv_ip)
    TextView tvIp;
    @BindView(R.id.tv_remarks)
    TextView tvRemarks;
    @BindView(R.id.et_propose)
    EditText etPropose;
    @BindView(R.id.tv_title2)
    TextView tvTitle2;
    @BindView(R.id.tv_divice2)
    TextView tvDivice2;
    @BindView(R.id.rv_images1)
    RecyclerView rvImages1;

    private List<String> imageUrls;
    private Equipment.RepairInfo repairInfo;
    private ImagesAdapter mAdapter;
    private final int RESULT_TAKE_PHOTO = 0;
    private String mCurrentPhotoPath;
    private String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_repair_detail);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        repairInfo = (Equipment.RepairInfo) intent.getSerializableExtra("repairInfo");
        getEquipmentInfo(repairInfo.getMAINTAIN_ID());
        Log.d("RepairDetailActivity", "repairInfo:" + repairInfo);

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

    public void getPermission() {
        RxPermissions rxPermissions = new RxPermissions(mActivity);
        if (rxPermissions.isGranted(Manifest.permission.CAMERA)) {
            takeCamera();
            return;
        }
        PermissionUtils.getPermission(mActivity);
    }

    public void initView(final RepairDetail repairDetail) {
        tvDescribe.setText(repairDetail.getClientInfo().getCLIENT_TYPE());
        tvType.setText(repairDetail.getClientInfo().getTERMTYPE());
        tvArea1.setText(repairDetail.getClientInfo().getAREACODE());
        tvArea2.setText(repairDetail.getClientInfo().getREMARK());
        tvDate.setText(repairDetail.getClientInfo().getTIMEVIEW());
        tvFloor.setText(repairDetail.getClientInfo().getFLOORNUM());
        tvIp.setText(repairDetail.getClientInfo().getCLIENT_IP());
        tvRemarks.setText(repairDetail.getClientInfo().getREMARK());
        tvTitle2.setText(repairDetail.getClientInfo().getTITLE());
        tvDivice2.setText(repairDetail.getClientInfo().getPROBLEMDTION());
        mAdapter = new ImagesAdapter(repairDetail.getDePiclist());
        initRecylerView(rvImages1, mAdapter);
        mAdapter.setItemClickListener(new ImagesAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mActivity, BigPictureActivity.class);
                ArrayList<RepairDetail.RepairImage> images = repairDetail.getDePiclist();
                intent.putExtra("url", images.get(position).getFILE_URL());
                startActivity(intent);
            }
        });
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

    /**
     * 获取设备信息
     */
    public void getEquipmentInfo(final String repairId) {
        Observable<Response> observable = SoapObservable.getAnyObservable(new RxObserverableCallBack() {
            @Override
            public String doWebRequest() {
                return SoapUtils.repairDetail(repairId);
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers
                        .mainThread()).subscribe(new SoapObserver<Response>() {
            @Override
            public void onSuccess(String s) {
                Log.d("InformationDetailActivi", s);
                final RepairDetail repairDetail = JSONObject.parseObject(s, RepairDetail.class);
                initView(repairDetail);
            }

            @Override
            public void onFailure(String s) {
                Log.d("InformationDetailActivi", s);
                CommonUtil.showToast(mActivity, s);
            }
        });
    }

    public void initRecylerView(RecyclerView recyclerView, ImagesAdapter adapter) {
        GridLayoutManager mLayoutManager = new GridLayoutManager(mActivity, 4);//设置为一个4列的纵向网格布局
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.space);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 图片上传
     */
    public void uploadImage() {
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
                return SoapUtils.updateImage(image, repairInfo.getMAINTAIN_ID(),"2");
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers
                        .mainThread()).subscribe(new SoapObserver<Response>() {
            @Override
            public void onSuccess(String s) {
                hud.dismiss();
                Log.d("RepairDetailActivity", s);
                rxUpdateRepairAnswer(answer);
            }

            @Override
            public void onFailure(String s) {
                hud.dismiss();

                Log.d("RepairDetailActivity", s);
                CommonUtil.showToast(mActivity, s);
            }
        });
    }

    private void rxUpdateRepairAnswer(final String answer) {
        Observable<Response> observable = SoapObservable.getAnyObservable(new RxObserverableCallBack() {
            @Override
            public String doWebRequest() {
                return SoapUtils.updateRepairAnswer(repairInfo.getMAINTAIN_ID(),appContext.user.getUser_ID(),answer);
            }
        });

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers
                        .mainThread()).subscribe(new SoapObserver<Response>() {
            @Override
            public void onSuccess(String s) {
                Log.d("RepairDetailActivity", s);
                CommonUtil.showToast(mActivity,"上传成功");
                mActivity.finish();
            }

            @Override
            public void onFailure(String s) {
                Log.d("RepairDetailActivity", s);
                CommonUtil.showToast(mActivity, s);
            }
        });
    }




    @OnClick(R.id.button)
    public void onViewClicked() {
        imageUrls = imageManagerView.getAllImages();
        answer=etPropose.getText().toString();
        if (imageUrls.size()==0||answer.length()==0){
            CommonUtil.showToast(mActivity,"请拍照或者填写维修结果");
            return;
        }
        uploadImage();
    }
}

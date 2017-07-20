package com.chablis.repair.activity;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chablis.repair.R;
import com.chablis.repair.base.BaseActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.photodraweeview.OnPhotoTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

public class BigPictureActivity extends BaseActivity {


    @BindView(R.id.photoView)
    PhotoDraweeView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_picture);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Log.d("BigPictureActivity", url);
        //TODO 零时属性
//        Uri uri = Uri.parse("http://img4.duitang.com/uploads/item/201609/20/20160920212544_GJxW3.jpeg");
//        photoView.setImageURI(uri);


        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setUri(Uri.parse(url));
        controller.setOldController(photoView.getController());
// You need setControllerListener
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null || photoView == null) {
                    return;
                }
                photoView.update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        });
        photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
//                mActivity.finish();
                onBackPressed();
            }
        });
        photoView.setController(controller.build());
    }


}

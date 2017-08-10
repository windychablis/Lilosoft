package com.chablis.repair.base;
/**
 * Created by chablis on 2016/11/7.
 */

import android.app.Application;
import android.graphics.Bitmap;

import com.chablis.repair.model.User;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class AppContext extends Application {

    private static Application sInstance;
    public User user;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheFileCount(500)
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
        Fresco.initialize(this);
    }

    /**
     * 是否启动检查更新
     *
     * @return
     */
    public boolean isCheckUp() {
        return true;
    }

    public boolean isPlayAnim() {
        return false;
    }

    public static Application get() {
        return sInstance;
    }

}

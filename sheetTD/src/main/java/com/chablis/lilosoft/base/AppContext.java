package com.chablis.lilosoft.base;
/**
 * Created by chablis on 2016/11/7.
 */

import android.app.Application;
import android.graphics.Bitmap;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.model.ClientInfo;
import com.chablis.lilosoft.model.Dept;
import com.chablis.lilosoft.model.TDDept;
import com.chablis.lilosoft.model.TDForm;
import com.chablis.lilosoft.utils.SoundPoolUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AppContext extends Application {

    private static Application sInstance;
    public List<TDDept> list_dept;
    public List<ClientInfo> list_client;
    public List<TDForm[]> TDFormList = new ArrayList<TDForm[]>();
    public TDForm[] temp;
    public String tdURL;
    public String formURL;
    public Dept dept;

    public int TAB=0;
    public SoundPoolUtil soundPoolUtil;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisc(true).considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext())
                .defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .discCacheFileCount(500)
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
        playSoundInit();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        // 初始化服务地址
//        Global.WebServerUrl = getResources().getString(R.string.WebServerUrl);
        Global.AppFileRootPath = getResources().getString(
                R.string.AppFileRootPath);
    }
    public void playSoundInit()
    {
        soundPoolUtil = new SoundPoolUtil(this);
        soundPoolUtil.loadSfx(R.raw.yy1, 1);
        soundPoolUtil.loadSfx(R.raw.yy2, 2);
        soundPoolUtil.loadSfx(R.raw.yy3, 3);
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

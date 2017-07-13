package com.chablis.repair.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.chablis.repair.utils.CommonUtil;

import java.io.File;

/**
 * 应用程序配置类：用于保存用户相关信息及设置
 *
 * @author chenhao
 */
public class AppConfig {
    public static String HEAD = "";
    public static String CACHE_IMAGES_DIR;
    public static String DOWNLOAD_DIR = HEAD;
    /**
     * 缓存相关保存文件的名称
     */
    public static final String APP_CACHE_DIR_NAME = "/Repair";
    /**
     * 错误日志保存路径
     */
    public static final String DIR_LOG = APP_CACHE_DIR_NAME + "/Log";

    public static void createCacheDir(Context context) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            HEAD = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            HEAD = context.getCacheDir().getAbsolutePath();
        }
        CACHE_IMAGES_DIR = HEAD + APP_CACHE_DIR_NAME + "/cache_images";
        DOWNLOAD_DIR = HEAD + APP_CACHE_DIR_NAME + "/downloads";
        File cache_upload_images = new File(CACHE_IMAGES_DIR);
        if (!cache_upload_images.exists()) {
            cache_upload_images.mkdirs();
        }
        File download_dir = new File(DOWNLOAD_DIR);
        if (!download_dir.exists()) {
            download_dir.mkdirs();
        }
    }

    public static void doClearCache() {
        File file = new File(getSdCard() + APP_CACHE_DIR_NAME);
        CommonUtil.DeleteFile(file);
    }

    public static String getSdCard() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir == null ? null : sdDir.toString();

    }
}

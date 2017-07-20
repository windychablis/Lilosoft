package com.chablis.lilosoft.base;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import java.io.File;

public class Global {

    public static String AppFileRootPath;

    public static boolean isProxy = false;
    public static String ProxyHostName = "192.168.88.80";
    public static int ProxyPort = 9991;

    public static String areacode = "110105000000";

    public static String webUrl = "http://192.168.1.107:9003/SPService.svc";
//    public static String webUrl = "http://10.180.227.251:9003/SPService.svc";

    public static String updateUrl = "http://192.168.1.107:9002/tdconfig.xml";

    public static String backup = "backup";

    public static void setWebUrl(String ip) {
        webUrl = "http://" + ip + "/SPService.svc";
    }

    public static void setUpdateUrl(String ip) {
        updateUrl = "http://" + ip + "/tdconfig.xml";
    }

    public static void setAreacode(Context context, String areacode) {
        Global.areacode = areacode;
        SharedPreferences sp = context.getSharedPreferences("newegov", Context.MODE_PRIVATE);
        sp.edit().putString("areacode", areacode).commit();
    }

    /**
     * 设备机器码
     */
    public static String CurrentAGMCode = "";

    /**
     * 服务地址
     */
    public static String WebServerUrl = "http://www.egovsoft.cn:8002/egov/agm/Interface.do";
    /**
     * 打印
     */
    public static String WS_printService = "printService";

    /**
     * 评分
     */
    public static String WS_setScore = "setScore";

    /**
     * 判断数据库是否存在，只支持系统默认路径getDatabasePath
     *
     * @param context
     * @param dbname
     * @return
     */
    public static boolean dbExists(Context context, String dbname) {
        return context.getDatabasePath(
                Global.getSDCardPath(context, Global.AppFileRootPath) + File.separator
                        + dbname).exists();
    }

    /**
     * 判断压缩包是否存在
     */
    public static boolean zipExists(Context context, String zipname) {
        File file = new File(Global.getSDCardPath(context, Global.AppFileRootPath) + File.separator + zipname);
        return file.exists();
    }

    /**
     * 获取根目录
     *
     * @param context
     * @param rootpath
     * @return
     */
    public static String getSDCardPath(Context context, String rootpath) {
        if (rootpath == null || rootpath.length() == 0) {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String DEVICE_ID = tm.getDeviceId();
            rootpath = DEVICE_ID;
        }
        String sdcardPath = "";
        if (ExistSDCard()) {
            sdcardPath = Environment.getExternalStorageDirectory() + "/"
                    + rootpath;
        } else {
            sdcardPath = context.getFilesDir().getAbsolutePath() + "/"
                    + rootpath;
        }
        File destDir = new File(sdcardPath);
        if (!destDir.exists()) {
            try {
                if (!destDir.mkdirs()) {
                    sdcardPath = context.getFilesDir().getAbsolutePath();
                }
            } catch (Exception e) {
                sdcardPath = context.getFilesDir().getAbsolutePath();
            }
        }
        return sdcardPath;
    }

    /**
     * 获取应用下载保存的文件物理路径
     *
     * @param context
     * @param icopath
     * @return
     */
    public static String getAppFileAbsolutePath(Context context, String icopath) {
        String rootpath = Global.getSDCardPath(context, Global.AppFileRootPath);
//		SharedPreferences sp = context.getSharedPreferences("newegov", Context.MODE_PRIVATE);
        String filepath = rootpath + File.separator + icopath;
        return filepath;
    }

    public static Bitmap loadBigImage(String pathName) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, opts);

        opts.inSampleSize = computeSampleSize(opts, -1, 1024 * 1024);
        opts.inJustDecodeBounds = false;
        Bitmap bmp = Bitmap.createBitmap(1, 1, Config.ALPHA_8);
        try {
            bmp = BitmapFactory.decodeFile(pathName, opts);
        } catch (OutOfMemoryError err) {
        }
        return bmp;
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * SDCard是否存在
     *
     * @return
     */
    public static boolean ExistSDCard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else
            return false;
    }


    public static PopupWindow createPopupWindow(Context context, int resource,
                                                int cancelResource) {

        LayoutInflater inflater = LayoutInflater.from(context);
        // LayoutInflater inflater =
        // (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 引入窗口配置文件
        View view = inflater.inflate(resource, null);
        // 创建PopupWindow对象
        final PopupWindow pop = new PopupWindow(view,
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, false);
        // pop.setBackgroundDrawable(view.getResources().getDrawable(
        // R.drawable.rounded_corners_view));
        pop.update();
        // 设置此参数获得焦点，否则无法点击
        pop.setFocusable(true);
        // 设置点击窗口外边窗口消失
        pop.setOutsideTouchable(false);
        Button cancel = (Button) view.findViewById(cancelResource);
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (pop.isShowing()) {
//					pop.dismiss();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }
        });
        pop.showAtLocation(view, Gravity.CENTER, 0, 0);
        return pop;
    }
}

package com.chablis.repair.utils;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

    public static void saveLog(String time, String strs) {
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            try {
                String path = Environment.getExternalStorageDirectory().toString();
                File file = new File(path + "/repair/Log.txt");
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fw = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fw);
//				String info = time+","+strs[1]+","+strs[2]+"\n";
                String info = time + "," + strs + "\n";
                bw.write(info);
                bw.flush();

                fw.close();
                bw.close();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

    public static String md5(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] byteArray = md.digest(text.getBytes());
            StringBuffer md5StrBuff = new StringBuffer();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                    md5StrBuff.append("0").append(
                            Integer.toHexString(0xFF & byteArray[i]));
                else
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
            return md5StrBuff.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";

    }

    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(14[57])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isIdCardNO(String idcard) {
        Pattern p = Pattern
                .compile("^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$");
        Matcher m = p.matcher(idcard);
        return m.matches();
    }

    public static void showToast(Context context, String title) {
        Toast.makeText(context, title, Toast.LENGTH_SHORT).show();
    }
}

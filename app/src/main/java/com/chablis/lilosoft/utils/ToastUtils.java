package com.chablis.lilosoft.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.chablis.lilosoft.R;

/**
 * Created by chablis on 2017/3/30.
 */

public class ToastUtils {

    public static void showToast(Context context, String message) {
        //加载Toast布局
        View toastRoot = LayoutInflater.from(context).inflate(R.layout.toast, null);
        //初始化布局控件
        TextView mTextView = (TextView) toastRoot.findViewById(R.id.tv_title);
        //为控件设置属性
        mTextView.setText(message);
        //Toast的初始化
        Toast toastStart = new Toast(context);
        toastStart.setGravity(Gravity.CENTER_VERTICAL, 0, 300);
        toastStart.setDuration(Toast.LENGTH_SHORT);
        toastStart.setView(toastRoot);
        toastStart.show();
    }
}

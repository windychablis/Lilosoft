package com.chablis.repair.utils;

import android.Manifest;
import android.app.Activity;

import com.chablis.repair.rx.PermissionObserver;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * Created by chablis on 2017/7/13.
 */

public class PermissionUtils {
    public static void getPermission(final Activity activity){
        RxPermissions rxPermissions=new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new PermissionObserver() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure() {

            }
        });
    }
}

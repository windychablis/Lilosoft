package com.chablis.repair.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.chablis.repair.base.AppConfig;
import com.chablis.repair.base.BaseActivity;
import com.chablis.repair.rx.PermissionObserver;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by chablis on 2017/7/13.
 */

public class PermissionUtils {
    public static void getPermission(final Activity activity){
        RxPermissions rxPermissions=new RxPermissions(activity);
        rxPermissions.request(Manifest.permission.CAMERA).subscribe(new PermissionObserver() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure() {

            }
        });
    }
}

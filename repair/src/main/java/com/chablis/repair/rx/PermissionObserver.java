package com.chablis.repair.rx;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by chablis on 2017/7/10.
 */

public abstract class PermissionObserver implements Observer<Boolean> {
    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull Boolean aBoolean) {
        if (aBoolean) {
            onSuccess();
        } else {
            onFailure();
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess();

    public abstract void onFailure();
}



package com.chablis.repair.rx;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by chablis on 2017/7/10.
 */

public abstract class SoapObserverArray implements Observer<String> {
    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull String s) {
        onSuccess(s);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        if (e instanceof RequestException) {
            onFailure(e.getMessage());
        }
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(String s);

    public abstract void onFailure(String s);
}



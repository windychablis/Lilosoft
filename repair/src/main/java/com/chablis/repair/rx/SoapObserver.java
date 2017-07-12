package com.chablis.repair.rx;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by chablis on 2017/7/10.
 */

public abstract class SoapObserver<T extends Response> implements Observer<T> {
    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(T t) {
        if (t.getCode() == 0) {
            onSuccess(t.getData());
        } else {
            onFailure(t.getMessage());
        }

    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(String s);

    public abstract void onFailure(String s);
}



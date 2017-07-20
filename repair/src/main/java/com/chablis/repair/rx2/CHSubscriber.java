package com.chablis.repair.rx2;

import com.chablis.repair.rx.Response;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by chablis on 2017/7/20.
 */

public abstract class CHSubscriber<T extends Response> implements Subscriber<T> {
    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
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

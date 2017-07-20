package com.chablis.repair.rx2;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by chablis on 2017/7/20.
 */

public abstract class CHArraySubscriber implements Subscriber<String> {
    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(String s) {
        onSuccess(s);
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

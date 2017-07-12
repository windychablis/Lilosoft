package com.chablis.repair.rx;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Created by chablis on 2017/7/7.
 */

public class SoapObservable{


    public static Observable<String> getObservable(final RxObserverableCallBack call){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                String result=call.doWebRequest();
                Log.d("SoapObservable", result);
                int code= JSONObject.parseObject(result).getIntValue("code");
                if (code==0) {
                    String data = JSONObject.parseObject(result).getString("data");
                    e.onNext(data);
                }else{
                    String msg = JSONObject.parseObject(result).getString("message");
                    e.onError(new RequestException(msg));
                }
                e.onComplete();

            }
        });
    }

}

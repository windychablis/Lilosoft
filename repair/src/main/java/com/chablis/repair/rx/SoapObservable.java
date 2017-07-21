package com.chablis.repair.rx;

import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Created by chablis on 2017/7/7.
 */

public class  SoapObservable{


    //专门用来合并请求的
    public static Observable<String> getStringObservable(final RxObserverableCallBack call){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                String result=call.doWebRequest();
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

    //单个请求，生成任意跟Reponse一样的json对象
    public static Observable<Response> getAnyObservable(final RxObserverableCallBack call){
        return Observable.create(new ObservableOnSubscribe<Response>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<Response> e) throws Exception {
                String result=call.doWebRequest();
                Response response=JSONObject.parseObject(result,Response.class);
                e.onNext(response);
                e.onComplete();
            }
        });
    }

}

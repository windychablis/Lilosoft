package com.chablis.repair.rx2;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.chablis.repair.rx.RequestException;
import com.chablis.repair.rx.Response;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;

/**
 * Created by chablis on 2017/7/20.
 */

public class CHFlowable {
    //单个请求，生成任意跟Reponse一样的json对象
    public static Flowable<Response> getAnyFlowable(final CHRequestCallBack call){
       return Flowable.create(new FlowableOnSubscribe<Response>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Response> e) throws Exception {
                String result=call.doWebRequest();
                Log.d("CHFlowable", result);
                Response response= JSONObject.parseObject(result,Response.class);
                e.onNext(response);
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR);
    }

    //专门用来合并请求的
    public static Flowable<String> getStringFlowable(final CHRequestCallBack call){
        return Flowable.create(new FlowableOnSubscribe<String>() {

            @Override
            public void subscribe(@NonNull FlowableEmitter<String> e) throws Exception {
                String result=call.doWebRequest();
                Log.d("CHFlowable", result);
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
        }, BackpressureStrategy.ERROR);
    }
}

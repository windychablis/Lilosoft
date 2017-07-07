package com.chablis.repair.base;

import android.os.AsyncTask;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by chablis on 2017/7/5.
 */

public class SoapAsyncTask extends AsyncTask<String, Integer, String> {
    private TaskCallBack callBack;

    public SoapAsyncTask(TaskCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    protected String doInBackground(String... params) {
        return callBack.doInBackground();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s==null){
            callBack.onFailure("网络超时");
            return;
        }
        int code= JSONObject.parseObject(s).getIntValue("code");
        if (code==0) {
            String result = JSONObject.parseObject(s).getString("data");
            callBack.onSuccess(result);
        }else{
            String msg = JSONObject.parseObject(s).getString("message");
            callBack.onFailure(msg);
        }
    }
}


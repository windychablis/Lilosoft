package com.chablis.repair.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by chablis on 2017/7/7.
 */

public class JsonUtils {
    public static String parseJson(String json){
        if (json==null){
            return "网络超时";
        }
        int code= JSONObject.parseObject(json).getIntValue("code");
        if (code==0) {
            return JSONObject.parseObject(json).getString("data");
        }else{
            return JSONObject.parseObject(json).getString("message");
        }
    }
}

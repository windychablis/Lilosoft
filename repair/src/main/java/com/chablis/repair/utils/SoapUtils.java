package com.chablis.repair.utils;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.chablis.repair.rx.Response;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by chablis on 2017/6/27.
 */

public class SoapUtils {
    public static String http = "http://";
    public static String settingUrl = "27.17.62.40:8899";
    public static String url = http + settingUrl + "/wisdomgov/ws";
//    public static String url = "http://192.168.2.56:8080/wisdomgov/ws";

    /**
     * 登录
     */
    public static String login(String username, String password) {
        LinkedHashMap map = new LinkedHashMap();
        map.put("loginName", username);
        map.put("passWord", CommonUtil.md5(password));
        return SoapResuest("LoginDo", "loginService", map);
    }

    /**
     * 获取设备信息
     *
     * @param equipmentId 设备id
     * @return
     */
    public static String equipmentInfo(String equipmentId) {
        HashMap map = new HashMap();
        map.put("clientType", equipmentId);
        return SoapResuest("queryTainIdByClientType", "repairService", map);
    }

    /**
     * 获取设备信息详情
     *
     * @param repairId 设备id
     * @return
     */
    public static String repairDetail(String repairId) {
        HashMap map = new HashMap();
        map.put("mainTainId", repairId);
        return SoapResuest("queryMainTainById", "repairService", map);
    }

    /**
     * 获取我的报修列表
     *
     * @param userId
     * @return
     */
    public static String getMyRepair(String userId, String code) {
        LinkedHashMap map = new LinkedHashMap();
        map.put("repairUserId", userId);
        map.put("areaCode", code);
        return SoapResuest("queryListByRepairId", "repairService", map);
    }

    /**
     * 获取报修列表
     *
     * @return
     */

    public static String getRepairList(String code) {
        HashMap map = new HashMap();
        map.put("area_code", code);
        return SoapResuest("queryMainTainByCode", "repairService", map);
    }

    /**
     * 获取大类小类集合
     *
     * @return
     */
    public static String getClasses() {
        return SoapResuest("insertView", "repairService");
    }

    /**
     * 上传图片
     *
     * @return
     */
    public static String updateImage(String image, String mainTainId, String type) {
        LinkedHashMap map = new LinkedHashMap();
        map.put("folder", "repair/image");
        map.put("type", type);
        map.put("file", image);
        map.put("fileName", "temp.jpg");
        map.put("mainTainId", mainTainId);
        return SoapResuest("insertPic", "repairService", map);
    }

    /**
     * 上传维修信息
     *
     * @return
     */
    public static String updateRepairInfo(String bigClass, String smallClass, String title, String problemDtion, String repairUserId, String clienttype, String mainTainId) {
        LinkedHashMap map = new LinkedHashMap();
        map.put("bigClass", bigClass);
        map.put("smallClass", smallClass);
        map.put("title", title);
        map.put("problemDtion", problemDtion);
        map.put("repairUserId", repairUserId);
        map.put("clienttype", clienttype);
        map.put("mainTainId", mainTainId);
        return SoapResuest("insertRepair", "repairService", map);
    }

    /**
     * 上传维修回复
     *
     * @return
     */
    public static String updateRepairAnswer(String mainTainId, String repairId, String answer) {
        LinkedHashMap map = new LinkedHashMap();
        map.put("mainTainId", mainTainId);
        map.put("disposeUserId", repairId);
        map.put("serverResult", answer);
        return SoapResuest("updateMainTain", "repairService", map);
    }


    /**
     * 获取政务中心
     *
     * @return
     */
    public static String getAreaList() {
        return SoapResuest("queryAllArea", "repairService");
    }


    /**
     * soap请求
     */
    public static String SoapResuest(String namespace, String method, String service, HashMap params) {
        SoapObject request = new SoapObject(namespace,
                method);
        if (params != null) {
            Iterator it = params.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                request.addProperty(entry.getKey().toString(), entry.getValue());
            }
        }
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        HttpTransportSE trans = new HttpTransportSE(url + "/" + service + "?wsdl", 5 * 1000);
        trans.debug = true;
        try {
            trans.call(null, envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            if (count > 0) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String jsonVal = (String) object.toString();
                return jsonVal;
            }
        } catch (Exception e) {
            Log.d("SoapUtils", "e:" + e);
            e.printStackTrace();
            Response response = new Response();
            response.setCode(-1);
            response.setMessage("网络错误");
            String str = JSONObject.toJSONString(response);
            return str;
        }

        return null;
    }

    /**
     * soap请求
     */

    public static String SoapResuest(String method, String service, HashMap params) {
        return SoapResuest("http://webservice.egs.lilosoft.com/", method, service, params);
    }

    /**
     * soap请求
     */

    public static String SoapResuest(String method, String service) {
        return SoapResuest("http://webservice.egs.lilosoft.com/", method, service, null);
    }


}

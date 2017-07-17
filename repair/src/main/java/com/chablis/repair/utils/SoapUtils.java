package com.chablis.repair.utils;

import android.util.Log;

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
    public static String http = "http://27.17.62.40";
//    public static String url = http + ":8899/wisdomgov/ws";
    public static String url = "http://192.168.2.56:8080/wisdomgov/ws";
    public static String otherUrl = "http://192.168.2.82:8080/wisdomgov/ws";

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
    public static String getMyRepair(String userId) {
        HashMap map = new HashMap();
        map.put("repairUserId", userId);
        return SoapResuest("queryListByRepairId", "repairService", map);
    }

    /**
     * 获取报修列表
     *
     * @return
     */
    public static String getRepairList() {
        return SoapResuest("queryServiceList", "repairService");
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
    public static String updateImage(String image,String fileName,String mainTainId) {
        HashMap map = new HashMap();
        map.put("folder", "repair/image");
        map.put("type", "jpg");
        map.put("file", image);
        map.put("fileName", fileName);
        map.put("mainTainId", mainTainId);
        return SoapResuest("insertPic", "repairService");
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
            Log.d("SoapUtils", "envelope.bodyIn:" + envelope.bodyIn);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            if (count > 0) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String jsonVal = (String) object.toString();
                return jsonVal;
            }
        } catch (Exception e) {
            e.printStackTrace();
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

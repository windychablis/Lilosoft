package com.chablis.repair.utils;

import android.provider.Settings;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by chablis on 2017/6/27.
 */

public class SoapUtils {
    public static String http = "192.168.1.107";
    public static String url = http + ":8899/wisdomgov/ws";
    public static String loginUrl="http://192.168.2.56:8080/wisdomgov/ws";
    public static String otherUrl="http://192.168.2.82:8080/wisdomgov/ws";

    /**
     * 登录
     */
    public static String login(String username,String password) {
        SoapObject request = new SoapObject("http://webservice.egs.lilosoft.com/",
                "LoginDo");

        request.addProperty("loginName", username);
        request.addProperty("passWord", CommonUtil.md5(password));
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        HttpTransportSE trans = new HttpTransportSE(otherUrl + "/loginService?wsdl");
        trans.debug = true;
        try {
            trans.call(null,envelope);
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
     * 获取设备信息
     * @param equipmentId  设备id
     * @return
     */
    public static String equipmentInfo(String equipmentId) {
        SoapObject request = new SoapObject("http://webservice.egs.lilosoft.com/",
                "queryTainIdByClientType");

        request.addProperty("clientType", equipmentId);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        HttpTransportSE trans = new HttpTransportSE(otherUrl + "/repairService?wsdl");
        trans.debug = true;
        try {
            trans.call(null,envelope);
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
     * 获取设备信息
     * @param equipmentId  设备id
     * @return
     */
    public static String equipmentRepairList(String equipmentId) {
        SoapObject request = new SoapObject("http://webservice.egs.lilosoft.com/",
                "queryServiceListByClientType");

        request.addProperty("clientType", equipmentId);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        HttpTransportSE trans = new HttpTransportSE(otherUrl + "/repairService?wsdl");
        trans.debug = true;
        try {
            trans.call(null,envelope);
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
}

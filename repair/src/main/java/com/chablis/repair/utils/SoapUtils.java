package com.chablis.repair.utils;

import android.provider.Settings;

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

    /**
     * 获取部门图片压缩包
     */
    public String GetZipDownloadUrl(String areacode) {
        SoapObject request = new SoapObject("http://webservice.egs.lilosoft.com/",
                "LoginService");
//        request.addProperty("areacode", Settings.Global.areacode);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(url + "/LoginService?wsdl");
        trans.debug = true;
        try {
            trans.call(null,
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            CommonUtil.saveLog("URL", result.toString());
            int count = result.getPropertyCount();
            if (count > 0) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String jsonVal = (String) object.toString();
                return jsonVal;
            }
        } catch (Exception e) {
            CommonUtil.saveLog("URL_ERROR", e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}

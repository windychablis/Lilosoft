package com.chablis.lilosoft.utils;

import android.content.Context;

import com.chablis.lilosoft.base.Global;
import com.chablis.lilosoft.model.Answer;
import com.chablis.lilosoft.model.ImageUrls;
import com.chablis.lilosoft.model.Question;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.reflect.Type;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;

public class WebUtil {


    /**
     * 获取部门图片压缩包
     */
    public String GetZipDownloadUrl(String areacode) {
        SoapObject request = new SoapObject("http://tempuri.org/",
                "GetTDDownloadPatch");
        request.addProperty("areacode", Global.areacode);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/GetTDDownloadPatch",
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

    /**
     * 获取部门信息
     *
     * @param areacode
     * @return
     */
    public String GetTableOfDept(String areacode) {
        SoapObject request = new SoapObject("http://tempuri.org/",
                "ListALLTDDept");
        request.addProperty("areacode", Global.areacode);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);

        envelope.dotNet = true;
        envelope.bodyOut = request;
        HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/ListALLTDDept",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            if (count > 0) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String jsonVal = (String) object.toString();
                JSONObject jsonO = new JSONObject(jsonVal);
                String jsonArray = jsonO.getJSONArray("TDDept").toString();
                return jsonArray;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取表单信息
     *
     * @param areacode
     * @return
     */
    public String GetTableOfForm(String areacode) {
        SoapObject request = new SoapObject("http://tempuri.org/",
                "ListALLTDForm");
        request.addProperty("areacode", Global.areacode);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/ListALLTDForm",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            if (count > 0) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String jsonVal = (String) object.toString();
                JSONObject jsonO = new JSONObject(jsonVal);
                String jsonArray = jsonO.getJSONArray("TDForm").toString();
                return jsonArray;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 获取材料信息
     *
     * @param areacode
     * @return
     */
    public String GetTableOfMaterials(String areacode) {
        SoapObject request = new SoapObject("http://tempuri.org/",
                "ListALLFormMaterials");
        request.addProperty("areacode", Global.areacode);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/ListALLFormMaterials",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            if (count > 0) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String jsonVal = (String) object.toString();
                JSONObject jsonO = new JSONObject(jsonVal);
                String jsonArray = jsonO.getJSONArray("FormMaterials").toString();
                return jsonArray;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 填单台发布类型
     *
     * @return
     */
    public String GetTDPublish() {
        SoapObject request = new SoapObject("http://tempuri.org/",
                "GetTDPublish");
        request.addProperty("areacode", Global.areacode);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/GetTDPublish",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            if (count > 0) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String jsonVal = (String) object.toString();
                JSONObject jsonO = new JSONObject(jsonVal);
                String json = jsonO.getJSONArray("TDPublish").toString().replace("[", "").replace("]", "");
                return json;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取终端信息
     *
     * @param context
     * @return
     */
    public String GetTDClientInfo(Context context) {
        SoapObject request = new SoapObject("http://tempuri.org/",
                "GetTDClientInfo");
        String ip = getIPhost(context);
        request.addProperty("ip", ip);
        request.addProperty("areacode", Global.areacode);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/GetTDClientInfo",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            CommonUtil.saveLog("success", "ip =" + ip + "\n areacode=" + Global.areacode + result.toString());
            int count = result.getPropertyCount();
            if (count > 0) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String jsonVal = (String) object.toString();
                JSONObject jsonO = new JSONObject(jsonVal);
                String json = jsonO.getJSONObject("ClientInfo").toString();
                return json;
            }
        } catch (Exception e) {
            CommonUtil.saveLog("error", e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取本地ip
     *
     * @param context
     * @return
     */
    public static String getIPhost(Context context) {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        //if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet6Address) {
                        String a = inetAddress.getHostAddress().toString();
                        return a;
                    }
                }
            }
        } catch (Exception e) {
        }
        return "";
    }

    /**
     * 获取屏保图片
     *
     * @return
     */
    public String[] getImageURL() {
        String json = "";
        SoapObject request = new SoapObject("http://tempuri.org/",
                "ListScreensaver");
        request.addProperty("type", "03");
        request.addProperty("areacode", Global.areacode);
//		addAreaCode(request);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/ListScreensaver",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            if (count > 0) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                json = object.toString();
                Gson gson = new Gson();
                ImageUrls urls = gson.fromJson(json, ImageUrls.class);
                String[] imageUrls = new String[urls.Screensaver.size()];
                if (urls.Screensaver != null && urls.Screensaver.size() > 0) {
                    for (int i = 0; i < urls.Screensaver.size(); i++) {
                        String url = urls.Screensaver.get(i).pic.replace("\\", "/");
                        imageUrls[i] = url;
                    }
                    return imageUrls;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 获取问卷调查列表
     */
    public static String getAllQuestionnaire() {
        SoapObject request = new SoapObject("http://tempuri.org/",
                "ListAllIndagate");
        request.addProperty("areacode", Global.areacode);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/ListAllIndagate",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            boolean flag = result.getProperty(0).toString().contains("anyType{}");
            if (count > 0 && !flag) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String jsonVal = (String) object.toString();
                JSONObject jsonO = new JSONObject(jsonVal);
                String json = jsonO.getJSONArray("Indagate").toString();
                return json;
            }
        } catch (Exception e) {
            CommonUtil.saveLog("error", e.getMessage());
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 获取问卷的问题列表
     *
     * @param id 问卷的id
     * @return
     */
    public static String getQuestion(String id) {
        SoapObject request = new SoapObject("http://tempuri.org/",
                "ListVoteConfigByIndagateID");
        request.addProperty("id", id);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/ListVoteConfigByIndagateID",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            boolean flag = result.getProperty(0).toString().contains("anyType{}");
            if (count > 0 && !flag) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String jsonVal = (String) object.toString();
                JSONObject jsonO = new JSONObject(jsonVal);
                String json = jsonO.getJSONArray("VoteConfig").toString();
                return json;
            }
        } catch (Exception e) {
            CommonUtil.saveLog("error", e.getMessage());
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 根据问题id获取答案列表
     *
     * @param id 问题的id
     * @return
     */
    public static ArrayList<Question> getAnswer(ArrayList<Question> questions) {
        for (Question question : questions) {
            SoapObject request = new SoapObject("http://tempuri.org/",
                    "ListVoteByCID");
            request.addProperty("id", question.getVote_c_id());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            envelope.bodyOut = request;
            envelope.dotNet = true;
            HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
            trans.debug = true;
            try {
                trans.call("http://tempuri.org/ISPService/ListVoteByCID",
                        envelope);
                SoapObject result = (SoapObject) envelope.bodyIn;
                int count = result.getPropertyCount();
                if (count > 0) {
                    SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                    String jsonVal = (String) object.toString();
                    JSONObject jsonO = new JSONObject(jsonVal);
                    String json = jsonO.getJSONArray("Vote").toString();

                    Type type = new TypeToken<ArrayList<Answer>>() {
                    }.getType();
                    Gson gson = new Gson();
                    ArrayList<Answer> answers = gson.fromJson(json, type);
                    question.setAnswers(answers);
                }
            } catch (Exception e) {
                CommonUtil.saveLog("error", e.getMessage());
                e.printStackTrace();
            }
        }
        return questions;
    }


    /**
     * 提交问卷
     */
    public static boolean updateQuestionnaire(String ids) {
        SoapObject request = new SoapObject("http://tempuri.org/",
                "UpdateVoteResultByID");
        request.addProperty("voteid", ids);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/UpdateVoteResultByID",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            boolean flag = result.getProperty(0).toString().contains("true");
            if (count > 0 && flag) {
                return true;
            }
        } catch (Exception e) {
            CommonUtil.saveLog("error", e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


}

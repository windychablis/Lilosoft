package com.chablis.lilosoft.utils;

import android.content.Context;
import android.util.Log;
import android.util.Xml;

import com.chablis.lilosoft.base.Global;
import com.chablis.lilosoft.model.Affair;
import com.chablis.lilosoft.model.AffairItem;
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
import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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
     * @param questions 问题实体
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


    public static Map<String, String> getVersion(String urlStr) {
        Map<String, String> result = null;
        try {
            URL urlxml = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) urlxml
                    .openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();
            if (code == 200) {
                XmlPullParser parser = Xml.newPullParser();
                InputStream in = conn.getInputStream();
                parser.setInput(in, "UTF-8");
                int type = parser.getEventType();
                result = new HashMap<String, String>();
                while (type != XmlPullParser.END_DOCUMENT) {
                    switch (type) {
                        case XmlPullParser.START_TAG:
                            if ("version".equals(parser.getName())) {
                                result.put("version", parser.nextText());
                            } else if ("url".equals(parser.getName())) {
                                result.put("url", parser.nextText());
                            } else if ("name".equals(parser.getName())) {
                                result.put("name", parser.nextText());
                            }
                            break;
                        default:
                            break;
                    }
                    type = parser.next();
                }
            }
        } catch (Exception e) {
            // String strErr = e.getMessage();
            // e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取地图区划数据
     */
    public static String getMapData(String areacode) {
        SoapObject request = new SoapObject("http://tempuri.org/",
                "GetMapXYByAreaCode");
        request.addProperty("areacode", areacode);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/GetMapXYByAreaCode",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            boolean flag = result.getProperty(0).toString().contains("anyType{}");
            if (count > 0 && !flag) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String jsonVal = (String) object.toString();
                JSONObject jsonO = new JSONObject(jsonVal);
                String json = jsonO.getJSONArray("Map").toString();
                return json;
            }
        } catch (Exception e) {
            CommonUtil.saveLog("error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取办事指南部门数据
     */
    public static String getDeptList(String areacode) {
        SoapObject request = new SoapObject("http://tempuri.org/",
                "ListAllDeptToAndroid");
        request.addProperty("areacode", areacode);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/ListAllDeptToAndroid",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            boolean flag = result.getProperty(0).toString().contains("anyType{}");
            if (count > 0 && !flag) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String jsonVal = (String) object.toString();
                JSONObject jsonO = new JSONObject(jsonVal);
                String json = jsonO.getJSONArray("Dept").toString();
                return json;
            }
        } catch (Exception e) {
            CommonUtil.saveLog("error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取预约部门数据
     */
    public static String getAppointmentDeptList(String areacode) {
        SoapObject request = new SoapObject("http://tempuri.org/",
                "ListSubscribeDeptByAreaCode");
        request.addProperty("areacode", areacode);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/ListSubscribeDeptByAreaCode",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            boolean flag = result.getProperty(0).toString().contains("anyType{}");
            if (count > 0 && !flag) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String jsonVal = (String) object.toString();
                JSONObject jsonO = new JSONObject(jsonVal);
                String json = jsonO.getJSONArray("dept").toString();
                return json;
            }
        } catch (Exception e) {
            CommonUtil.saveLog("error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取事项大项列表数据
     */
    public static String getAffairList(String deptId) {
        SoapObject request = new SoapObject("http://tempuri.org/",
                "ListProjectItemByDeptID");
        request.addProperty("deptid", deptId);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/ListProjectItemByDeptID",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            boolean flag = result.getProperty(0).toString().contains("anyType{}");
            if (count > 0 && !flag) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String jsonVal = (String) object.toString();
                JSONObject jsonO = new JSONObject(jsonVal);
                String json = jsonO.getJSONArray("ProjectItem").toString();
                return json;
            }
        } catch (Exception e) {
            CommonUtil.saveLog("error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取预约事项大项列表数据
     */
    public static String getAppointmentAffairList(String deptId) {
        SoapObject request = new SoapObject("http://tempuri.org/",
                "ListSubscribeProjectItemByDeptID");
        request.addProperty("deptid", deptId);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/ListSubscribeProjectItemByDeptID",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            boolean flag = result.getProperty(0).toString().contains("anyType{}");
            if (count > 0 && !flag) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String jsonVal = (String) object.toString();
                JSONObject jsonO = new JSONObject(jsonVal);
                String json = jsonO.getJSONArray("Project").toString();
                return json;
            }
        } catch (Exception e) {
            CommonUtil.saveLog("error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取事项小项列表数据
     */
    public static ArrayList<Affair> getAffairItemList(ArrayList<Affair> affairs) {
        ArrayList<ArrayList<AffairItem>> list = new ArrayList<ArrayList<AffairItem>>();
        for (Affair affair : affairs) {
            SoapObject request = new SoapObject("http://tempuri.org/",
                    "ListProjectByProjectItemNo");
            request.addProperty("itemno", affair.getItem_no());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            envelope.bodyOut = request;
            envelope.dotNet = true;
            HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
            trans.debug = true;
            try {
                trans.call("http://tempuri.org/ISPService/ListProjectByProjectItemNo",
                        envelope);
                SoapObject result = (SoapObject) envelope.bodyIn;
                int count = result.getPropertyCount();
                boolean flag = result.getProperty(0).toString().contains("anyType{}");
                if (count > 0 && !flag) {
                    SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                    String jsonVal = (String) object.toString();
                    JSONObject jsonO = new JSONObject(jsonVal);
                    String json = jsonO.getJSONArray("Project").toString();

                    Type type = new TypeToken<ArrayList<AffairItem>>() {
                    }.getType();
                    Gson gson = new Gson();
                    ArrayList<AffairItem> affairItems = gson.fromJson(json, type);
                    affair.setAffairItems(affairItems);
                }
            } catch (Exception e) {
                CommonUtil.saveLog("error", e.getMessage());
                e.printStackTrace();
            }
        }
        return affairs;
    }

    /**
     * 获取事项办理材料数据
     */
    public static String getMaterialList(String id) {
        SoapObject request = new SoapObject("http://tempuri.org/",
                "ListProjectSummaryByProjectIDToAndroid");
        request.addProperty("projectid", id);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/ListProjectSummaryByProjectIDToAndroid",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            boolean flag = result.getProperty(0).toString().contains("anyType{}");
            if (count > 0 && !flag) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String jsonVal = (String) object.toString();
                JSONObject jsonO = new JSONObject(jsonVal);
                String json = jsonO.getJSONObject("ProjectSummary").toString();
                return json;
            }
        } catch (Exception e) {
            CommonUtil.saveLog("error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取事项办理材料数据
     */
    public static String getMaterialList2(String id) {
        SoapObject request = new SoapObject("http://tempuri.org/",
                "ListMaterialsByProjectIDToAndroid");
        request.addProperty("projectid", id);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/ListMaterialsByProjectIDToAndroid",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            boolean flag = result.getProperty(0).toString().contains("anyType{}");
            if (count > 0 && !flag) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String jsonVal = (String) object.toString();
                JSONObject jsonO = new JSONObject(jsonVal);
                String json = jsonO.getJSONArray("ProjectMaterials").toString();
                return json;
            }
        } catch (Exception e) {
            CommonUtil.saveLog("error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取预约时间
     */
    public static String getAppointmentTime(String areacode) {
        SoapObject request = new SoapObject("http://tempuri.org/",
                "ListSubscribeTimeToMode3");
        request.addProperty("areacode", areacode);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/ListSubscribeTimeToMode3",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            boolean flag = result.getProperty(0).toString().contains("anyType{}");
            if (count > 0 && !flag) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String jsonVal = (String) object.toString();
                JSONObject jsonO = new JSONObject(jsonVal);
                String json = jsonO.getJSONArray("SubscribeDate").toString();
                return json;
            }
        } catch (Exception e) {
            CommonUtil.saveLog("error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取预约时间
     */
    public static String getAppointmentData(String time, String projectno, String areacode) {
        SoapObject request = new SoapObject("http://tempuri.org/",
                "ListSubscribeDataToMode3");
        request.addProperty("stime", time);
        request.addProperty("projectno", projectno);
        request.addProperty("areacode", areacode);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/ListSubscribeDataToMode3",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            boolean flag = result.getProperty(0).toString().contains("anyType{}");
            if (count > 0 && !flag) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String json = (String) object.toString();
//                JSONObject jsonO = new JSONObject(jsonVal);
//                String json = jsonO.getJSONArray("SubscribeDate").toString();
                return json;
            }
        } catch (Exception e) {
            CommonUtil.saveLog("error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验证票码是否存在
     */
    public static String existsTicketCode(String ticketcode) {
        SoapObject request = new SoapObject("http://tempuri.org/",
                "ExistsTicketCode");
        request.addProperty("ticketcode", ticketcode);
        request.addProperty("areacode", Global.areacode);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/ExistsTicketCode",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            boolean flag = result.getProperty(0).toString().contains("anyType{}");
            if (count > 0 && !flag) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String json = (String) object.toString();
//                JSONObject jsonO = new JSONObject(jsonVal);
//                String json = jsonO.getJSONArray("SubscribeDate").toString();
                return json;
            }
        } catch (Exception e) {
            CommonUtil.saveLog("error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 添加预约
     */
    public static String AddAppointment(String projectno, String date, String time, String idcard, String name, String mobile, String ticketcode) {
        SoapObject request = new SoapObject("http://tempuri.org/",
                "AddSubscribeToMode3");
        request.addProperty("type", 5);
        request.addProperty("prmid", "");
        request.addProperty("projectid", projectno);
        request.addProperty("orderdate", date);
        request.addProperty("ordertime", time);
        request.addProperty("idcard", idcard);
        request.addProperty("username", name);
        request.addProperty("mobile", mobile);
        request.addProperty("ticketcode", ticketcode);
        request.addProperty("areacode", Global.areacode);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/AddSubscribeToMode3",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            boolean flag = result.getProperty(0).toString().contains("anyType{}");
            if (count > 0 && !flag) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String json = (String) object.toString();
//                JSONObject jsonO = new JSONObject(jsonVal);
//                String json = jsonO.getJSONArray("SubscribeDate").toString();
                return json;
            }
        } catch (Exception e) {
            CommonUtil.saveLog("error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送短信
     */
    public static boolean sendMessage(String mobile, String content) {
        SoapObject request = new SoapObject("http://tempuri.org/",
                "SendSMSToAndroid");
        request.addProperty("mobile", mobile);
        request.addProperty("content", content);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/SendSMSToAndroid",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            boolean flag = result.getProperty(0).toString().contains("anyType{}");
            if (count > 0 && !flag) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String jsonVal = (String) object.toString();
                JSONObject jsonO = new JSONObject(jsonVal);
                boolean success = jsonO.getBoolean("success");
                return success;
            }
        } catch (Exception e) {
            CommonUtil.saveLog("error", e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据扫描的二维码获取事项列表或是办事指南
     */
    public static String[] getAffairByQRCode(String id) {
        SoapObject request = new SoapObject("http://tempuri.org/",
                "GetProjectInfoByQueueID");
        request.addProperty("id", id);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.setOutputSoapObject(request);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        HttpTransportSE trans = new HttpTransportSE(Global.webUrl);
        trans.debug = true;
        try {
            trans.call("http://tempuri.org/ISPService/GetProjectInfoByQueueID",
                    envelope);
            SoapObject result = (SoapObject) envelope.bodyIn;
            int count = result.getPropertyCount();
            boolean flag = result.getProperty(0).toString().contains("anyType{}");
            if (count > 0 && !flag) {
                SoapPrimitive object = (SoapPrimitive) result.getProperty(0);
                String json = (String) object.toString();
                JSONObject jsonO = new JSONObject(json);
                String [] str=new String[2];
                if (jsonO.has("ProjectItem")){
                    Log.d("WebUtil", "jsonO.get(ProjectItem):" + jsonO.get("ProjectItem"));
                    str[0]="0";
                    str[1]=jsonO.getString("ProjectItem");

                }else if (jsonO.has("ProjectSummary")){
                    Log.d("WebUtil", "jsonO.get(ProjectSummary):" + jsonO.get("ProjectSummary"));
                    str[0]="1";
                    str[1]=jsonO.getString("ProjectSummary");
                }
                return str;
            }
        } catch (Exception e) {
            CommonUtil.saveLog("error", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}

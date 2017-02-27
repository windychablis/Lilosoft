package com.chablis.lilosoft.base;
/**
 * Created by chablis on 2016/11/7.
 */

import android.app.Application;

import com.chablis.lilosoft.model.ClientInfo;
import com.chablis.lilosoft.model.TDDept;
import com.chablis.lilosoft.model.TDForm;
import com.chablis.lilosoft.R;

import java.util.ArrayList;
import java.util.List;

public class AppContext extends Application {

    private static Application sInstance;
    public List<TDDept> list_dept;
    public List<ClientInfo> list_client;
    public List<TDForm[]> TDFormList = new ArrayList<TDForm[]>();
    public TDForm[] temp;
    public String tdURL;
    public String formURL;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        // 初始化服务地址
//        Global.WebServerUrl = getResources().getString(R.string.WebServerUrl);
        Global.AppFileRootPath = getResources().getString(
                R.string.AppFileRootPath);
    }

    /**
     * 是否启动检查更新
     *
     * @return
     */
    public boolean isCheckUp() {
        return true;
    }

    public boolean isPlayAnim() {
        return false;
    }

    public static Application get() {
        return sInstance;
    }

}

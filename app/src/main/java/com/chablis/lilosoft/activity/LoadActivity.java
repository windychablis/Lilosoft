package com.chablis.lilosoft.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.base.BaseFragment;
import com.chablis.lilosoft.base.Global;
import com.chablis.lilosoft.db.DatabaseAdapter;
import com.chablis.lilosoft.db.SQL2VOHelper;
import com.chablis.lilosoft.db.dbConfig;
import com.chablis.lilosoft.model.ClientInfo;
import com.chablis.lilosoft.model.TDDept;
import com.chablis.lilosoft.model.TDForm;
import com.chablis.lilosoft.utils.SoundPoolUtil;
import com.chablis.lilosoft.utils.UpdateUtil;

import java.util.ArrayList;
import java.util.List;

public class LoadActivity  extends BaseActivity implements BaseFragment.OnFragmentInteractionListener {
    public static List<TDDept> list_dept;
    public static List<ClientInfo> list_client;
    public static List<TDForm[]> TDFormList = new ArrayList<TDForm[]>();

    public static SoundPoolUtil soundPoolUtil;
    private View activity_view;
    private PopupWindow pop;
    private SharedPreferences.Editor editor;
    SharedPreferences sp;
    ProgressDialog mypDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        Global.AppFileRootPath = getResources().getString(
                R.string.AppFileRootPath);

        playSoundInit();

        sp = getSharedPreferences("newegov", Context.MODE_PRIVATE);
        editor = sp.edit();

        String ip = sp.getString("ip", "");
        String areacode = sp.getString("areacode", "");
        String update_ip=sp.getString("update_ip","");
        if(!TextUtils.isEmpty(ip)){
            Global.setWebUrl(ip);
        }
        if(!TextUtils.isEmpty(ip)){
            Global.areacode = areacode;
        }
        if(!TextUtils.isEmpty(ip)){
            Global.setUpdateUrl(update_ip);
        }
    }

   /* @Override
    protected void onStart() {
        super.onStart();
        if(Global.dbExists(this, dbConfig.DB_NAME)){
            //第一次进入
            mHandler.sendEmptyMessageDelayed(2, 1000);
        }else{
            LoadToMainActivity();
        }
*//*new AsyncTask<String,Integer,String>(){

    @Override
    protected String doInBackground(String... strings) {
        WebUtil wu=new WebUtil();
        String str=wu.GetTableOfDept(Global.areacode);
        ArrayList<TDDept> list=(ArrayList<TDDept>) JSONObject.parseArray(str, TDDept.class);
        Log.d("LoadActivity", list.toString());
        return null;
    }
}.execute();*//*
    }*/

    @Override
    protected void onStart() {
        super.onStart();
        if(!Global.dbExists(this, dbConfig.DB_NAME)){
            //第一次进入
            mHandler.sendEmptyMessageDelayed(2, 1000);
        }else{
            LoadToMainActivity();
        }
    }


    public void FristLoad(){
        pop = Global.createPopupWindow(this, R.layout.dialog_frist_load, R.id.btn_Cancel);
        final EditText et_area = (EditText) pop.getContentView().findViewById(R.id.et_area);
        final EditText et_ip = (EditText) pop.getContentView().findViewById(R.id.et_ip);
        final EditText et_update = (EditText) pop.getContentView().findViewById(R.id.et_update);
        Button btn_OK = (Button) pop.getContentView().findViewById(R.id.btn_OK);

        et_area.setText(sp.getString("areacode", ""));
        et_ip.setText(sp.getString("ip", ""));
        et_update.setText(sp.getString("update_ip", ""));

        btn_OK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Global.areacode = et_area.getText().toString();
                Global.setWebUrl(et_ip.getText().toString());
                Global.setUpdateUrl(et_update.getText().toString());

                editor.putString("areacode", et_area.getText().toString());
                editor.putString("ip", et_ip.getText().toString());
                editor.putString("update_ip", et_update.getText().toString());
                editor.commit();

                UpdateUtil updateUtil = new UpdateUtil(LoadActivity.this);
                updateUtil.starUpdate();

                pop.dismiss();
            }
        });
    }

    public void LoadToMainActivity(){
        // 加载数据到对象变量
        showMessageBox(getResources().getString(R.string.App_CN_Name),
                "正在加载数据，请稍后...");

        //加载基本信息(显示方式)
        String sql_dept = getDeptSQL();

        if(sql_dept.equals("")) return;

        //准备部门数据
        DatabaseAdapter tabtypeadp = new DatabaseAdapter(
                LoadActivity.this, dbConfig.DB_NAME,
                dbConfig.DB_VERSION,
                dbConfig.CreateSql_EGS_TD_DEPT,
                dbConfig.EGS_TD_DEPT, dbConfig.DB_TABLE_TabType_PK);

        tabtypeadp.open();
        String sql = "select * from " + tabtypeadp.DB_TABLE;
        list_dept = SQL2VOHelper.sql2VOList(tabtypeadp.mSQLiteDatabase, sql_dept, TDDept.class);
        appContext.list_dept=list_dept;
        for (int i = 0; i < list_dept.size(); i++) {
            sql = "select * from " + dbConfig.EGS_TD_FORM
                    + " where DEPT_ID='" + list_dept.get(i).getDEPT_ID()
                    + "' order by IS_ORDER asc";
            List<TDForm> edlist = SQL2VOHelper.sql2VOList(
                    tabtypeadp.mSQLiteDatabase, sql,
                    TDForm.class);
            TDForm[] eds = (TDForm[]) edlist
                    .toArray(new TDForm[0]);
            TDFormList.add(eds);
        }
        appContext.TDFormList=TDFormList;
        tabtypeadp.close();
        mHandler.sendEmptyMessage(7);
        //加载完成，跳转界面
        mHandler.sendEmptyMessage(0);
    }

    public String getDeptSQL(){
        String sql = "select * from " + dbConfig.EGS_TD_DEPT;
        DatabaseAdapter clientadp = new DatabaseAdapter(
                LoadActivity.this, dbConfig.DB_NAME,
                dbConfig.DB_VERSION,
                dbConfig.CreateSql_EGS_TD_CLIENTINFO,
                dbConfig.EGS_TD_CLIENTINFO, dbConfig.PUBLISH_TYPE);
        clientadp.open();
        String sql_client = "select * from " + clientadp.DB_TABLE;
        list_client = SQL2VOHelper.sql2VOList(clientadp.mSQLiteDatabase, sql_client, ClientInfo.class);
        if(list_client.size()!=0) {
            appContext.list_client = list_client;
            ClientInfo clientInfo = list_client.get(0);
            if (clientInfo.getPublish_type().equals("1")) {
                //按area查找部门
                sql = sql + " where AREA='" + clientInfo.getArea() + "' and IS_SHOW=1 order by FLOORNUM asc,IS_ORDER asc";
            } else if (clientInfo.getPublish_type().equals("2")) {
                //按floornum查找部门
                sql = sql + " where FLOORNUM='" + clientInfo.getFloornum() + "' and IS_SHOW=1 order by IS_ORDER asc";
            } else if (clientInfo.getPublish_type().equals("3")) {
                //查找全部
                sql = sql + " where IS_SHOW=1 order by FLOORNUM asc,IS_ORDER asc";
            }
        }else{
            sql="";
        }
        return sql;
    }

    public void playSoundInit()
    {
        soundPoolUtil = new SoundPoolUtil(this);
        soundPoolUtil.loadSfx(R.raw.yy1, 1);
        soundPoolUtil.loadSfx(R.raw.yy2, 2);
        soundPoolUtil.loadSfx(R.raw.yy3, 3);
    }

    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    startActivity(new Intent(LoadActivity.this,
                            MainActivity.class));
                    finish();
                    break;
                case 1:
                    if (Global.dbExists(getApplicationContext(), dbConfig.DB_NAME)) {
                        // 正常加载
                        LoadToMainActivity();
                    }
                    break;
                case 2:
                    FristLoad();
                    break;
                case 7:
                    if (mypDialog.isShowing())
                    mypDialog.dismiss();
                    break;

                default:
                    break;
            }
        };
    };

    public void showMessageBox(String title, String msg) {
        if (mypDialog != null && mypDialog.isShowing()) {
            mypDialog.dismiss();
        }
        mypDialog = new ProgressDialog(LoadActivity.this);
        // 实例化
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // 设置进度条风格，风格为圆形，旋转的
        mypDialog.setTitle(title);
        // 设置ProgressDialog 标题
        mypDialog.setMessage(msg);
        // 设置ProgressDialog 提示信息
        mypDialog.setIcon(R.mipmap.ic_launcher);
        // 设置ProgressDialog 标题图标
        mypDialog.setIndeterminate(false);
        // 设置ProgressDialog 的进度条是否不明确
        mypDialog.setCancelable(false);
        // 设置ProgressDialog 是否可以按退回按键取消
        mypDialog.show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onDestroy() {
        if (mypDialog!=null&&mypDialog.isShowing()) {
            mypDialog.dismiss();
        }
        super.onDestroy();
    }
}

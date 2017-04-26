package com.chablis.lilosoft.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.adapter.MaterialAdapter;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.model.AffairItem;
import com.chablis.lilosoft.model.Dept;
import com.chablis.lilosoft.model.Material;
import com.chablis.lilosoft.model.Material2;
import com.chablis.lilosoft.utils.ToastUtils;
import com.chablis.lilosoft.utils.WebUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AffairActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.rg_tab)
    RadioGroup rgTab;
    @BindView(R.id.fwjg)
    TextView fwjg;
    @BindView(R.id.splx)
    TextView splx;
    @BindView(R.id.sxbh)
    TextView sxbh;
    @BindView(R.id.bldd)
    TextView bldd;
    @BindView(R.id.cnqx)
    TextView cnqx;
    @BindView(R.id.sfbz)
    TextView sfbz;
    @BindView(R.id.expandtv1)
    TextView expandtv1;
    @BindView(R.id.arrow1)
    ImageView arrow1;
    @BindView(R.id.zxdh)
    TextView zxdh;
    @BindView(R.id.arrow2)
    ImageView arrow2;
    @BindView(R.id.expandtv2)
    TextView expandtv2;
    @BindView(R.id.slsj)
    TextView slsj;
    @BindView(R.id.arrow3)
    ImageView arrow3;
    @BindView(R.id.expandtv3)
    TextView expandtv3;
    @BindView(R.id.tv_affair_name)
    TextView tvAffairName;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.ll_content1)
    LinearLayout llContent1;
    @BindView(R.id.ll_content2)
    LinearLayout llContent2;
    @BindView(R.id.btn_OK)
    Button btnOK;

    private AffairItem affairItem;

    private MaterialAdapter mAdapter;

    private String from;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affair);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        from = intent.getStringExtra("from");
        if (from != null) {
            btnOK.setText("预约");
            Material material = (Material) intent.getSerializableExtra("material");
            affairItem = new AffairItem();
            affairItem.setProject_type(material.getProject_type());
            affairItem.setProject_name(material.getProject_name());
            affairItem.setProject_no(material.getProject_no());
            affairItem.setProject_id(material.getProject_id());
            appContext.dept=new Dept();
            appContext.dept.setDept_name(material.getDeptname());
            getData(affairItem.getProject_id());
        } else {
            affairItem = (AffairItem) intent.getSerializableExtra("affair_item");
            tvAffairName.setText(affairItem.getProject_name());
            getData(affairItem.getProject_id());
        }
    }

    @OnClick({R.id.tv_back, R.id.rl_sfbz, R.id.rl_splc, R.id.rl_xsyj, R.id.btn_OK, R.id.rb1, R.id.rb2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                mActivity.finish();
                break;
            case R.id.rl_sfbz:
                if (expandtv1.getVisibility() == View.GONE) {
                    expandtv1.setVisibility(View.VISIBLE);
                    arrow1.setImageResource(R.mipmap.expand_02);
                } else {
                    expandtv1.setVisibility(View.GONE);
                    arrow1.setImageResource(R.mipmap.expand_01);
                }
                break;
            case R.id.rl_splc:
                if (expandtv2.getVisibility() == View.GONE) {
                    expandtv2.setVisibility(View.VISIBLE);
                    arrow2.setImageResource(R.mipmap.expand_02);
                } else {
                    expandtv2.setVisibility(View.GONE);
                    arrow2.setImageResource(R.mipmap.expand_01);
                }
                break;
            case R.id.rl_xsyj:
                if (expandtv3.getVisibility() == View.GONE) {
                    expandtv3.setVisibility(View.VISIBLE);
                    arrow3.setImageResource(R.mipmap.expand_02);
                } else {
                    expandtv3.setVisibility(View.GONE);
                    arrow3.setImageResource(R.mipmap.expand_01);
                }
                break;
            case R.id.btn_OK:
                if (from != null) {
                    //TODO 进入预约界面
                    Intent intent = new Intent(mActivity, AppointmentActivity.class);
                    intent.putExtra("appointment",affairItem);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(mActivity, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                break;

            case R.id.rb1:
                llContent1.setVisibility(View.VISIBLE);
                llContent2.setVisibility(View.GONE);
                break;
            case R.id.rb2:
                llContent1.setVisibility(View.GONE);
                llContent2.setVisibility(View.VISIBLE);
                break;
        }
    }


    public void getData(final String id) {
        new AsyncTask<String, Integer, String>() {


            @Override
            protected String doInBackground(String... params) {
                return WebUtil.getMaterialList(id);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Gson gson = new Gson();
                Material material = gson.fromJson(s, Material.class);
                if (material != null) {
                    initView(material);
                } else {
                    ToastUtils.showToast(mActivity, "暂无数据");
                }
            }
        }.execute();

        new AsyncTask<String, Integer, String>() {


            @Override
            protected String doInBackground(String... params) {
                return WebUtil.getMaterialList2(id);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Type type = new TypeToken<ArrayList<Material2>>() {
                }.getType();
                Gson gson = new Gson();
                ArrayList<Material2> material2s = gson.fromJson(s, type);
                if (material2s != null) {
                    initView2(material2s);
                } else {
                    ToastUtils.showToast(mActivity, "暂无数据");
                }
            }
        }.execute();
    }

//    public void getMaterrial2(final String id){
//        new AsyncTask<String, Integer, String>() {
//
//
//            @Override
//            protected String doInBackground(String... params) {
//                return WebUtil.getMaterialList2(id);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                Type type = new TypeToken<ArrayList<Material2>>() {
//                }.getType();
//                Gson gson = new Gson();
//                ArrayList<Material2> material2s = gson.fromJson(s, type);
//                if (material2s != null) {
//                    initView2(material2s);
//                } else {
//                    ToastUtils.showToast(mActivity, "暂无数据");
//                }
//            }
//        }.execute();
//    }

    private void initView2(ArrayList<Material2> materials) {
        if (materials != null) {
            mAdapter = new MaterialAdapter(mActivity, materials);
            listview.setAdapter(mAdapter);
        }
    }

    private void initView(Material material) {

        fwjg.setText(material.getAccept_name());
        if (affairItem.getProject_type().equals("01")) {
            splx.setText("行政许可审批");
        } else if (affairItem.getProject_type().equals("02")) {
            splx.setText("非行政许可审批");
        } else if (affairItem.getProject_type().equals("03")) {
            splx.setText("服务类审批");
        }
        if (sxbh != null)
            sxbh.setText(affairItem.getProject_no());
        bldd.setText(material.getAccept_name());
        cnqx.setText(material.getPromise_desc());
        expandtv1.setText(material.getCharge_standard());
        zxdh.setText(material.getView_consulting_mode());
        expandtv2.setText(material.getProcess());
        expandtv3.setText(material.getFoundation());

    }

}

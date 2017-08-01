package com.chablis.lilosoft.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.adapter.MatterAdapter;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.model.Affair;
import com.chablis.lilosoft.model.AffairItem;
import com.chablis.lilosoft.model.Dept;
import com.chablis.lilosoft.utils.ToastUtils;
import com.chablis.lilosoft.utils.WebUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 事项列表
 */
public class MatterListActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.listview)
    ExpandableListView listview;

    private MatterAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matter_list);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        String json=intent.getStringExtra("affairs");
        if (json==null) {
            Dept dept = (Dept) intent.getSerializableExtra("dept");
            tvTitle.setText(dept.getDept_name());
            Log.d("MatterListActivity", "dept:" + dept);
            appContext.dept = dept;
            getData(dept.getDept_id());
        }else{
            Type type = new TypeToken<ArrayList<Affair>>() {
            }.getType();
            Gson gson = new Gson();
            ArrayList<Affair> affairs = gson.fromJson(json, type);
            if (affairs != null) {
                getItemData(affairs);
            }else{
                ToastUtils.showToast(mActivity,"暂无数据");
            }
        }

    }

    @OnClick(R.id.tv_back)
    public void onClick() {
        this.finish();
    }

    //根据部门id获取事项大项列表
    public void getData(final String deptId) {
        new AsyncTask<String, Integer, String>() {

            @Override
            protected String doInBackground(String... params) {

                if (mActivity.appContext.TAB == 0) {
                    return WebUtil.getAffairList(deptId);
                } else if (mActivity.appContext.TAB == 1) {
                    return WebUtil.getAppointmentAffairList(deptId);
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Type type = new TypeToken<ArrayList<Affair>>() {
                }.getType();
                Gson gson = new Gson();
                ArrayList<Affair> affairs = gson.fromJson(s, type);
                if (affairs != null) {
                    getItemData(affairs);
                }else{
                    ToastUtils.showToast(mActivity,"暂无数据");
                }
            }
        }.execute();
    }

    //根据部门id获取事项小项列表
    public void getItemData(final ArrayList<Affair> affairs) {
        new AsyncTask<String, Integer, ArrayList<Affair>>() {


            @Override
            protected ArrayList<Affair> doInBackground(String... params) {
                return WebUtil.getAffairItemList(affairs);
            }

            @Override
            protected void onPostExecute(ArrayList<Affair> arrayLists) {
                super.onPostExecute(arrayLists);
                Log.d("MatterListActivity", "arrayLists.size():" + arrayLists.size());
                initView(affairs);
            }
        }.execute();
    }

    public void initView(final ArrayList<Affair> affairs) {
        mAdapter = new MatterAdapter(mActivity, affairs);
        listview.setAdapter(mAdapter);
        listview.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int count = listview.getExpandableListAdapter().getGroupCount();
                for (int j = 0; j < count; j++) {
                    if (j != groupPosition) {
                        listview.collapseGroup(j);
                    }
                }
            }
        });

        listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                ArrayList<AffairItem> temp=mAdapter.getChild(groupPosition,0);
                if (temp==null){
                    return  true;
                }else {
                    return false;
                }
            }
        });
    }
}

package com.chablis.lilosoft.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.widget.GridView;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.adapter.DeptAdapter;
import com.chablis.lilosoft.adapter.MyViewPagerAdapter;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.base.BaseFragment;
import com.chablis.lilosoft.base.Global;
import com.chablis.lilosoft.model.Dept;
import com.chablis.lilosoft.utils.WebUtil;
import com.chablis.lilosoft.widget.RoundNavigationIndicator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 部门列表界面
 */
public class DeptListActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener {
    @BindView(R.id.indicator)
    RoundNavigationIndicator indicator;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private ArrayList<GridView> array;
    private static final float APP_PAGE_SIZE = 16.0f;
    private DeptAdapter deptAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_list);
        ButterKnife.bind(this);
        getData();
    }

    public void initView(ArrayList<Dept> depts) {
        final int PageCount = (int) Math.ceil(depts.size() / APP_PAGE_SIZE);
        array = new ArrayList<GridView>();
        for (int i = 0; i < PageCount; i++) {
            deptAdapter = new DeptAdapter(this, depts, i);
            GridView gridView = new GridView(this);
            gridView.setAdapter(deptAdapter);
            gridView.setNumColumns(4);
            gridView.setGravity(Gravity.CENTER);
            gridView.setVerticalSpacing(20);
            gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
            array.add(gridView);
        }

        //初始化圆点导航指示器
        indicator.setLenght(PageCount);
        indicator.setSelected(0);
        indicator.draw();


//        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(), fragments)); //设置适配器
        myViewPagerAdapter = new MyViewPagerAdapter(this, array);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //根据viewpager的改变修正圆点导航指示器
                indicator.setSelected(position);
                indicator.draw();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @OnClick(R.id.tv_back)
    public void onClick() {
        this.finish();
    }

    //获取办事指南和预约的部门列表数据
    public void getData() {

        new AsyncTask<String, Integer, String>() {

            @Override
            protected String doInBackground(String... params) {
                if (mActivity.appContext.TAB == 0) {
                    return WebUtil.getDeptList(Global.areacode);
                } else if (mActivity.appContext.TAB == 1) {
                    return null;
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Type type = new TypeToken<ArrayList<Dept>>() {
                }.getType();
                Gson gson = new Gson();
                ArrayList<Dept> depts = gson.fromJson(s, type);
                if (depts != null) {
                    initView(depts);
                }
            }
        }.execute();
    }
}

package com.chablis.lilosoft.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.widget.GridView;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.adapter.GridViewAdapter;
import com.chablis.lilosoft.adapter.MyViewPagerAdapter;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.base.BaseFragment;
import com.chablis.lilosoft.widget.RoundNavigationIndicator;

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
    private GridViewAdapter mAdapter;
    private ArrayList<GridView> array;
    private static final float APP_PAGE_SIZE = 16.0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dept_list);
        ButterKnife.bind(this);
        initView();
    }

    public void initView() {
        final int PageCount = (int) Math.ceil(appContext.list_dept.size() / APP_PAGE_SIZE);
        array = new ArrayList<GridView>();
        for (int i = 0; i < PageCount; i++) {
            mAdapter = new GridViewAdapter(this, appContext.list_dept, i);
            GridView gridView = new GridView(this);
            gridView.setAdapter(mAdapter);
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
}

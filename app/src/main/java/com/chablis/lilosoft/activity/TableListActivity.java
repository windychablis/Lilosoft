package com.chablis.lilosoft.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.adapter.GridViewTableListAdapter;
import com.chablis.lilosoft.adapter.MyViewPagerAdapter;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.model.TDForm;
import com.chablis.lilosoft.widget.RoundNavigationIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TableListActivity extends BaseActivity {

    @BindView(R.id.indicator)
    RoundNavigationIndicator indicator;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.et_search)
    EditText etSearch;

    private MyViewPagerAdapter myViewPagerAdapter;
    private GridViewTableListAdapter mAdapter;
    private ArrayList<GridView> array;
    private static final float APP_PAGE_SIZE = 12.0f;

    private TDForm[] exampleArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_list);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LoadActivity.soundPoolUtil.play(2, 0);
    }

    public void initView() {
        getData();
        /*final int PageCount = (int) Math.ceil(iconModels.size() / APP_PAGE_SIZE);
        array = new ArrayList<GridView>();
        for (int i = 0; i < PageCount; i++) {
            mAdapter = new GridViewTableListAdapter(this, iconModels, i);
            GridView gridView = new GridView(this);
            gridView.setAdapter(mAdapter);
            gridView.setNumColumns(3);
            gridView.setGravity(Gravity.CENTER);
            gridView.setVerticalSpacing(25);
            gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
            array.add(gridView);
        }

        //初始化圆点导航指示器
        indicator.setLenght(PageCount);
        indicator.setSelected(0);
        indicator.draw();
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
        });*/
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String str = textView.getText().toString();
                    Log.d("TableListActivity", "搜索");
                    if (getCurrentFocus() != null) {
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(getCurrentFocus()
                                                .getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                        List<TDForm> examplelist = new ArrayList<TDForm>();
                        for (int a = 0; a < exampleArray.length; a++) {
                            TDForm t = exampleArray[a];
                            if (t.FORM_NAME.contains(str)) {
                                examplelist.add(t);
                            }
                        }
                        initAdapter(examplelist);
                    }
                }
                return false;
            }
        });
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.finish();
            }
        });
    }

    public void getData() {
        int position = getIntent().getIntExtra("position", 0);
        appContext.tdURL = appContext.list_dept.get(position).getDIR_URL();
        exampleArray = appContext.TDFormList.get(position);
        appContext.temp = exampleArray;
        Log.d("TableListActivity", "position:" + exampleArray);
        if (exampleArray != null && exampleArray.length > 0) {
            List<TDForm> examplelist = Arrays.asList(exampleArray);

            final int PageCount = (int) Math.ceil(examplelist.size() / APP_PAGE_SIZE);
            array = new ArrayList<GridView>();
            for (int i = 0; i < PageCount; i++) {
                mAdapter = new GridViewTableListAdapter(this, examplelist, i);
                GridView gridView = new GridView(this);
                gridView.setAdapter(mAdapter);
                gridView.setNumColumns(3);
                gridView.setGravity(Gravity.CENTER);
                gridView.setVerticalSpacing(25);
                gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
                gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
                array.add(gridView);
            }

            //初始化圆点导航指示器
            indicator.setLenght(PageCount);
            indicator.setSelected(0);
            indicator.draw();
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
    }

    public void initAdapter(List<TDForm> examplelist) {
        final int PageCount = (int) Math.ceil(examplelist.size() / APP_PAGE_SIZE);
        array = new ArrayList<GridView>();
        for (int i = 0; i < PageCount; i++) {
            mAdapter = new GridViewTableListAdapter(this, examplelist, i);
            GridView gridView = new GridView(this);
            gridView.setAdapter(mAdapter);
            gridView.setNumColumns(3);
            gridView.setGravity(Gravity.CENTER);
            gridView.setVerticalSpacing(25);
            gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
            array.add(gridView);
        }

        //初始化圆点导航指示器
        indicator.setLenght(PageCount);
        indicator.setSelected(0);
        indicator.draw();
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

    @OnClick(R.id.tv_cancel)
    public void onClick() {
        etSearch.setText("");
        getData();
    }
}

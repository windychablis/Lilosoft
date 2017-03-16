package com.chablis.lilosoft.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.widget.GridView;
import android.widget.TextView;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.adapter.MyViewPagerAdapter;
import com.chablis.lilosoft.adapter.QuestionnaireListAdapter;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.model.Questionnaire;
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
 * 问卷列表界面
 */
public class QuestionnaireListActivity extends BaseActivity {
    @BindView(R.id.indicator)
    RoundNavigationIndicator indicator;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_back)
    TextView tvBack;

    private MyViewPagerAdapter myViewPagerAdapter;
    private QuestionnaireListAdapter mAdapter;
    private ArrayList<GridView> array;
    private static final float APP_PAGE_SIZE = 12.0f;

    private ArrayList<Questionnaire> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire_list);
        ButterKnife.bind(this);
        getData();
    }

    @OnClick(R.id.tv_back)
    public void onClick() {
        this.finish();
    }

    public void getData(){
        new AsyncTask<String,Integer,String>(){

            @Override
            protected String doInBackground(String... strings) {
                String json=WebUtil.getAllQuestionnaire();
                return json;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s!=null){
                    Type type = new TypeToken<ArrayList<Questionnaire>>()
                    {}.getType();
                    Gson gson=new Gson();
                    data=gson.fromJson(s,type);
                    final int PageCount = (int) Math.ceil(data.size() / APP_PAGE_SIZE);
                    array = new ArrayList<GridView>();
                    for (int i = 0; i < PageCount; i++) {
                        mAdapter = new QuestionnaireListAdapter(mActivity, data, i);
                        GridView gridView = new GridView(mActivity);
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
                    myViewPagerAdapter = new MyViewPagerAdapter(mActivity, array);
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
        }.execute();
    }
}

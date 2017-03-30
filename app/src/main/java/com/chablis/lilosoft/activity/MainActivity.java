package com.chablis.lilosoft.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.adapter.GridViewAdapter;
import com.chablis.lilosoft.adapter.MyViewPagerAdapter;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.base.BaseFragment;
import com.chablis.lilosoft.base.Global;
import com.chablis.lilosoft.model.IconModel;
import com.chablis.lilosoft.model.TDDept;
import com.chablis.lilosoft.utils.DateUtil;
import com.chablis.lilosoft.utils.ToastUtils;
import com.chablis.lilosoft.utils.UpdateManager;
import com.chablis.lilosoft.widget.RoundNavigationIndicator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements BaseFragment.OnFragmentInteractionListener {

    @BindView(R.id.tabIndicators)
    RadioGroup tabIndicators;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.indicator)
    RoundNavigationIndicator indicator;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tabGuideButton)
    RadioButton tabGuideButton;
    @BindView(R.id.tabMapButton)
    RadioButton tabMapButton;
    @BindView(R.id.tabConsultButton)
    RadioButton tabConsultButton;
    @BindView(R.id.tabAppointmentButton)
    RadioButton tabAppointmentButton;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
//    @BindView(R.id.ll_dots)
//    LinearLayout llDots;

    private MyViewPagerAdapter myViewPagerAdapter;
    private GridViewAdapter mAdapter;
    private ArrayList<GridView> array;
    private static final float APP_PAGE_SIZE = 16.0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        String ip=getSharedPreferences("newegov", Context.MODE_PRIVATE).getString("ip","").split(":")[0];
        //检查更新
        UpdateManager manager = new UpdateManager(this);
        manager.checkUpdate(Global.updateUrl);


        initView();
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 1000);
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        LoadActivity.soundPoolUtil.play(1, 0);
        super.onStart();
    }

    public void initView() {
//        getData();
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

    /*public void getData() {
        //初始化数据
        iconModels = new ArrayList<IconModel>();
        IconModel zhaoshangju = new IconModel(R.mipmap.zhaoshangju, "招商局");
        IconModel shiyaojian = new IconModel(R.mipmap.shiyaojian, "食药监");
        IconModel huanbaoju = new IconModel(R.mipmap.huanbaoju, "环保局");
        IconModel weijiwei = new IconModel(R.mipmap.weijiwei, "卫计委");
        IconModel renziju = new IconModel(R.mipmap.renziju, "人资局");
        IconModel shuiwuju = new IconModel(R.mipmap.shuiwuju, "水务局");
        IconModel jiansheju = new IconModel(R.mipmap.jiansheju, "建设局");
        IconModel yuanlinju = new IconModel(R.mipmap.yuanlinju, "园林局");
        IconModel chengguanju = new IconModel(R.mipmap.chengguanju, "城管局");
        IconModel jiaotongju = new IconModel(R.mipmap.jiaotongju, "交通局");
        IconModel dishuiju = new IconModel(R.mipmap.dishuiju, "地税局");
        IconModel jiaoyuju = new IconModel(R.mipmap.jiaoyuju, "教育局");
        IconModel zhijianju = new IconModel(R.mipmap.zhijianju, "质监局");
        IconModel anjianju = new IconModel(R.mipmap.anjianju, "安监局");
        IconModel sifaju = new IconModel(R.mipmap.sifaju, "司法局");
        IconModel fagaiwei = new IconModel(R.mipmap.fagaiwei, "发改委");

        IconModel sifaju2 = new IconModel(R.mipmap.sifaju, "司法局");
        IconModel fagaiwei2 = new IconModel(R.mipmap.fagaiwei, "发改委");
        iconModels.add(zhaoshangju);
        iconModels.add(shiyaojian);
        iconModels.add(huanbaoju);
        iconModels.add(weijiwei);
        iconModels.add(renziju);
        iconModels.add(shuiwuju);
        iconModels.add(jiansheju);
        iconModels.add(yuanlinju);
        iconModels.add(chengguanju);
        iconModels.add(jiaotongju);
        iconModels.add(dishuiju);
        iconModels.add(jiaoyuju);
        iconModels.add(zhijianju);
        iconModels.add(anjianju);
        iconModels.add(sifaju);
        iconModels.add(fagaiwei);

        iconModels.add(sifaju2);
        iconModels.add(fagaiwei2);
    }*/

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            getTime();
            handler.postDelayed(this, 1000);
        }
    };

    public void getTime() {
        Date date = new Date();
        String a = DateUtil.date2Str(date, DateUtil.FORMAT_HM);
        String b = DateUtil.getWeekOfDate(date);
        String c = DateUtil.date2Str(date, DateUtil.FORMAT_YMD);
        tvDate.setText(c);
        tvTime.setText(a + "   " + b);
    }


    @OnClick({R.id.tabGuideButton, R.id.tabMapButton, R.id.tabConsultButton, R.id.tabAppointmentButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tabGuideButton:
                mActivity.appContext.TAB=0;
                nextActivity(DeptListActivity.class);
                break;
            case R.id.tabMapButton:
                nextActivity(MapActivity.class);
                break;
            case R.id.tabConsultButton:
                nextActivity(QuestionnaireListActivity.class);
                break;
            case R.id.tabAppointmentButton:
//                mActivity.appContext.TAB=1;
//                nextActivity(DeptListActivity.class);
//                Toast.makeText(mActivity, "尽请期待", Toast.LENGTH_SHORT).show();
                ToastUtils.showToast(mActivity,"尽请期待");
                break;
        }
    }
}

package com.chablis.lilosoft.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
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

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.adapter.GridViewAdapter;
import com.chablis.lilosoft.adapter.MyViewPagerAdapter;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.base.BaseFragment;
import com.chablis.lilosoft.base.Global;
import com.chablis.lilosoft.utils.DateUtil;
import com.chablis.lilosoft.utils.ImageLoaderUtils;
import com.chablis.lilosoft.utils.PrefUtils;
import com.chablis.lilosoft.utils.ToastUtils;
import com.chablis.lilosoft.utils.UpdateManager;
import com.chablis.lilosoft.widget.ExitDialog;
import com.chablis.lilosoft.widget.RoundNavigationIndicator;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

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
    @BindView(R.id.iv_logo)
    ImageView ivLogo;
//    @BindView(R.id.ll_dots)
//    LinearLayout llDots;

    private MyViewPagerAdapter myViewPagerAdapter;
    private GridViewAdapter mAdapter;
    private ArrayList<GridView> array;
    private static final float APP_PAGE_SIZE = 16.0f;

    private ExitDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        String ip = getSharedPreferences("newegov", Context.MODE_PRIVATE).getString("ip", "").split(":")[0];
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
        appContext.soundPoolUtil.play(1, 0);
        super.onStart();
    }


    public void initView() {
        ImageLoaderUtils.loaderImage(ivLogo,Global.logoUrl);
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


    @OnClick({R.id.tabGuideButton, R.id.tabMapButton, R.id.tabScanButton, R.id.tabConsultButton, R.id.tabAppointmentButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tabGuideButton:
                mActivity.appContext.TAB = 0;
                nextActivity(DeptListActivity.class);
                break;
            case R.id.tabMapButton:
                nextActivity(MapActivity.class);
                break;
            case R.id.tabScanButton:
                nextActivity(ScanActivity.class);
                break;
            case R.id.tabConsultButton:
                nextActivity(QuestionnaireListActivity.class);
                break;
            case R.id.tabAppointmentButton:
//                mActivity.appContext.TAB=1;
//                nextActivity(DeptListActivity.class);
                ToastUtils.showToast(mActivity, "暂未开放");
                break;

        }
    }


    @OnLongClick(R.id.exit)
    public boolean onViewClicked() {
//        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
//        View view= LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_exit,null);
//        Button btn1=(Button)view.findViewById(R.id.button1);
//        Button btn2=(Button)view.findViewById(R.id.button2);
//        btn1.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                //退出登录
//                MainActivity.this.finish();
//                nextActivity(LoadActivity.class);
//            }
//        });
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //退出程序
//                android.os.Process.killProcess(android.os.Process.myPid());   //获取PID
//                System.exit(0);
//            }
//        });
//
//        builder.setView(view);
//        builder.show();
        dialog = new ExitDialog(this, R.style.Dialog);
        dialog.show();
        dialog.setOnButtonClickListener(new ExitDialog.OnButtonClickListener() {
            @Override
            public void logoutListener() {
                Log.d("MainActivity", "退出登录");
                //退出登录
                dialog.dismiss();
                PrefUtils.putCacheLoginState(false);
                nextActivity(LoadActivity.class);
                MainActivity.this.finish();
            }

            @Override
            public void exitListener() {
                Log.d("MainActivity", "退出程序");
                dialog.dismiss();
                //退出程序
                Process.killProcess(Process.myPid());   //获取PID
                System.exit(0);
            }
        });
        return true;
    }

}

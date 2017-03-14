package com.chablis.lilosoft.activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.base.Global;
import com.chablis.lilosoft.db.DatabaseAdapter;
import com.chablis.lilosoft.db.SQL2VOHelper;
import com.chablis.lilosoft.db.dbConfig;
import com.chablis.lilosoft.model.TDForm;
import com.chablis.lilosoft.model.TDMaterials;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 调单材料界面
 */
public class TableDetailActivity extends BaseActivity {

    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.ib_print)
    ImageButton ibPrint;
    List<TDMaterials> edlist;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private ArrayList<ImageView> imageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_detail);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LoadActivity.soundPoolUtil.play(3, 0);
    }

    public void initView() {
        getData();
    }

    public void getData() {
        int position = getIntent().getIntExtra("position", 0);
        TDForm edpo = appContext.temp[position];
        appContext.formURL = appContext.temp[position].getDIR_URL();
        DatabaseAdapter ExampleInfoAdp = new DatabaseAdapter(
                this, dbConfig.DB_NAME, dbConfig.DB_VERSION,
                dbConfig.CreateSql_EGS_TD_MATERIALS,
                dbConfig.EGS_TD_MATERIALS,
                dbConfig.DB_TABLE_TabType_PK);
        ExampleInfoAdp.open();
        String sql = "select * from " + ExampleInfoAdp.DB_TABLE
                + " where FORM_ID='" + edpo.FORM_ID + "'";
        edlist = SQL2VOHelper.sql2VOList(ExampleInfoAdp.mSQLiteDatabase, sql,
                TDMaterials.class);

        ExampleInfoAdp.close();
        LayoutInflater inflater = getLayoutInflater();
        imageViews = new ArrayList<ImageView>();
        for (int i = 0; i < edlist.size(); i++) {
            ImageView imageView = (ImageView) inflater.inflate(R.layout.image_item, null);
            File file = new File(Global.getAppFileAbsolutePath(this,
                    appContext.tdURL + File.separator + appContext.formURL + File.separator + edlist.get(i).getDIR_URL() + File.separator + edlist.get(i).PIC_PATH));

            if (file.exists()) {
                imageView.setImageDrawable(new BitmapDrawable(Global
                        .loadBigImage(Global.getAppFileAbsolutePath(this,
                                appContext.tdURL + File.separator + appContext.formURL + File.separator + edlist.get(i).getDIR_URL() + File.separator + edlist.get(i).PIC_PATH))));
            }
            imageViews.add(imageView);
        }
        MyViewPagerAdapter myViewPagerAdapter=new MyViewPagerAdapter(imageViews);
        viewPager.setAdapter(myViewPagerAdapter);

    }


    @OnClick(R.id.tv_back)
    public void goBack() {
        this.finish();
    }

    class MyViewPagerAdapter extends PagerAdapter{
        private ArrayList<ImageView> mImageViews;

        public MyViewPagerAdapter(ArrayList<ImageView> mImageViews) {
            this.mImageViews = mImageViews;
        }

        @Override
        public int getCount() {
            return mImageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager)container).removeView(mImageViews.get(position % mImageViews.size()));

        }

        /**
         * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
         */
        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager)container).addView(mImageViews.get(position % mImageViews.size()), 0);
            return mImageViews.get(position % mImageViews.size());
        }


    }

}

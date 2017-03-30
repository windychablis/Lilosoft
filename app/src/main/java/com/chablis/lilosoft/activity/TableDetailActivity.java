package com.chablis.lilosoft.activity;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.base.Global;
import com.chablis.lilosoft.db.DatabaseAdapter;
import com.chablis.lilosoft.db.SQL2VOHelper;
import com.chablis.lilosoft.db.dbConfig;
import com.chablis.lilosoft.model.TDForm;
import com.chablis.lilosoft.model.TDMaterials;
import com.chablis.lilosoft.widget.HackyViewPager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoView;

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
    HackyViewPager viewPager;

    private ArrayList<Drawable> PhotoViews;

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
        PhotoViews = new ArrayList<Drawable>();
        for (int i = 0; i < edlist.size(); i++) {
//            View view = inflater.inflate(R.layout.image_item, null);
//            PhotoView PhotoView= (PhotoView) view.findViewById(R.id.photoView);
            File file = new File(Global.getAppFileAbsolutePath(this,
                    appContext.tdURL + File.separator + appContext.formURL + File.separator + edlist.get(i).getDIR_URL() + File.separator + edlist.get(i).PIC_PATH));

            if (file.exists()) {
//                PhotoView.setImageDrawable(new BitmapDrawable(Global
//                        .loadBigImage(Global.getAppFileAbsolutePath(this,
//                                appContext.tdURL + File.separator + appContext.formURL + File.separator + edlist.get(i).getDIR_URL() + File.separator + edlist.get(i).PIC_PATH))));
                PhotoViews.add(new BitmapDrawable(Global
                        .loadBigImage(Global.getAppFileAbsolutePath(this,
                                appContext.tdURL + File.separator + appContext.formURL + File.separator + edlist.get(i).getDIR_URL() + File.separator + edlist.get(i).PIC_PATH))));
            }
//            PhotoViews.add(PhotoView);
        }
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter(PhotoViews);
        viewPager.setAdapter(myViewPagerAdapter);

    }


    @OnClick(R.id.tv_back)
    public void goBack() {
        this.finish();
    }

    class MyViewPagerAdapter extends PagerAdapter {
        private ArrayList<Drawable> mPhotoViews;

        public MyViewPagerAdapter(ArrayList<Drawable> mPhotoViews) {
            this.mPhotoViews = mPhotoViews;
        }

        @Override
        public int getCount() {
            return mPhotoViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.image_item, null);
            PhotoView photoView= (PhotoView) view.findViewById(R.id.photoView);
            photoView.setImageDrawable(mPhotoViews.get(position));
            container.addView(view);
            return view;
        }
    }

}

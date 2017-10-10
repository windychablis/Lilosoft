package com.chablis.lilosoft.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.base.Global;
import com.chablis.lilosoft.utils.ImageLoaderUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Map2Activity extends BaseActivity {

    @BindView(R.id.iv_map)
    ImageView ivMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map2);
        ButterKnife.bind(this);
        ImageLoaderUtils.loaderImage(ivMap, Global.mapUrl);
    }

    @OnClick(R.id.tv_back)
    public void onViewClicked() {
        this.finish();
    }
}

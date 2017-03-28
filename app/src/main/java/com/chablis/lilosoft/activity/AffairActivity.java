package com.chablis.lilosoft.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AffairActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.rg_tab)
    RadioGroup rgTab;
    @BindView(R.id.fwjg)
    TextView fwjg;
    @BindView(R.id.splx)
    TextView splx;
    @BindView(R.id.sxbh)
    TextView sxbh;
    @BindView(R.id.bldd)
    TextView bldd;
    @BindView(R.id.cnqx)
    TextView cnqx;
    @BindView(R.id.sfbz)
    TextView sfbz;
    @BindView(R.id.expandtv1)
    TextView expandtv1;
    @BindView(R.id.arrow1)
    ImageView arrow1;
    @BindView(R.id.zxdh)
    TextView zxdh;
    @BindView(R.id.arrow2)
    ImageView arrow2;
    @BindView(R.id.expandtv2)
    TextView expandtv2;
    @BindView(R.id.slsj)
    TextView slsj;
    @BindView(R.id.arrow3)
    ImageView arrow3;
    @BindView(R.id.expandtv3)
    TextView expandtv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affair);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_back, R.id.rl_sfbz, R.id.rl_splc, R.id.rl_xsyj, R.id.btn_OK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                mActivity.finish();
                break;
            case R.id.rl_sfbz:
                if (expandtv1.getVisibility() == View.GONE) {
                    expandtv1.setVisibility(View.VISIBLE);
                    arrow1.setImageResource(R.mipmap.expand_02);
                }else{
                    expandtv1.setVisibility(View.GONE);
                    arrow1.setImageResource(R.mipmap.expand_01);
                }
                break;
            case R.id.rl_splc:
                if (expandtv2.getVisibility() == View.GONE) {
                    expandtv2.setVisibility(View.VISIBLE);
                    arrow2.setImageResource(R.mipmap.expand_02);
                }else{
                    expandtv2.setVisibility(View.GONE);
                    arrow2.setImageResource(R.mipmap.expand_01);
                }
                break;
            case R.id.rl_xsyj:
                if (expandtv3.getVisibility() == View.GONE) {
                    expandtv3.setVisibility(View.VISIBLE);
                    arrow3.setImageResource(R.mipmap.expand_02);
                }else{
                    expandtv3.setVisibility(View.GONE);
                    arrow3.setImageResource(R.mipmap.expand_01);
                }
                break;
            case R.id.btn_OK:
                break;
        }
    }

    @OnClick(R.id.tv_back)
    public void onViewClicked() {
    }
}

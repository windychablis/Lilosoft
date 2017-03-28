package com.chablis.lilosoft.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppointmentActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_back, R.id.ll_date, R.id.btn_OK})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_back:
                this.finish();
                break;
            case R.id.ll_date:
                intent=new Intent(mActivity,AppointmentTimeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_OK:
                intent=new Intent(mActivity,AppointmentInfoActivity.class);
                startActivity(intent);
                break;
        }
    }
}

package com.chablis.lilosoft.activity;

import android.os.Bundle;
import android.view.View;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppointmentInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_info);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_back, R.id.btn_OK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                mActivity.finish();
                break;
            case R.id.btn_OK:
                mActivity.finish();
                break;
        }
    }
}

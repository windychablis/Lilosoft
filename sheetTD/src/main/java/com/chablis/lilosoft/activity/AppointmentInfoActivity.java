package com.chablis.lilosoft.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.model.AffairItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppointmentInfoActivity extends BaseActivity {

    @BindView(R.id.tv_affair)
    TextView tvAffair;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.tv_idcard)
    TextView tvIdcard;
    @BindView(R.id.tv_dept)
    TextView tvDept;
    @BindView(R.id.tv_date)
    TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_info);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        AffairItem affairItem = (AffairItem) intent.getSerializableExtra("affairItem");
        tvAffair.setText(affairItem.getProject_name());
        tvDept.setText(appContext.dept.getDept_name());
        tvDate.setText(intent.getStringExtra("date"));
        tvIdcard.setText(intent.getStringExtra("idcard"));
        tvMobile.setText(intent.getStringExtra("mobile"));
        tvName.setText(intent.getStringExtra("name"));
    }

    @OnClick({R.id.tv_back, R.id.btn_OK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                mActivity.finish();
                break;
            case R.id.btn_OK:
//                mActivity.finish();
                Intent intent=new Intent(mActivity,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }
}

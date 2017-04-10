package com.chablis.lilosoft.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.model.AffairItem;
import com.chablis.lilosoft.utils.CommonUtil;
import com.chablis.lilosoft.utils.ToastUtils;
import com.chablis.lilosoft.utils.WebUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppointmentActivity extends BaseActivity {
    private final static int REQUESTCODE = 1; // 返回的结果码
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.et_idcard)
    EditText etIdcard;
    @BindView(R.id.tv_dept)
    TextView tvDept;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_affair)
    TextView tvAffair;
    private AffairItem affairItem;
    private String date = "";
    private String time = "";
    private String name = "";
    private String mobile = "";
    private String idcard = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        ButterKnife.bind(this);
        affairItem = (AffairItem) getIntent().getSerializableExtra("appointment");
        tvAffair.setText(affairItem.getProject_name());
        tvDept.setText(appContext.dept.getDept_name());
    }


    @OnClick({R.id.tv_back, R.id.ll_date, R.id.btn_OK})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_back:
                this.finish();
                break;
            case R.id.ll_date:
                intent = new Intent(mActivity, AppointmentTimeActivity.class);
                intent.putExtra("affairItem", affairItem);
                startActivityForResult(intent, REQUESTCODE);
//                startActivity(intent);
                break;
            case R.id.btn_OK:
                name = etName.getText().toString();
                idcard = etIdcard.getText().toString();
                mobile = etMobile.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(mActivity, "姓名不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mobile.equals("") || !CommonUtil.isMobileNO(mobile)) {
                    Toast.makeText(mActivity, "无效的电话号码!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (idcard.equals("") || !CommonUtil.isIdCardNO(idcard)) {
                    Toast.makeText(mActivity, "无效的身份证号码!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (date.equals("") || time.equals("")) {
                    Toast.makeText(mActivity, "请选择预约时间!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int num = (int) ((Math.random() * 9 + 1) * 100000);
                existsTicketCode(String.valueOf(num));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            date = data.getStringExtra("date");
            time = data.getStringExtra("time");
            tvTime.setText(date + "   " + time);

        }
    }

    public void addAppointment(final String projectno, final String date, final String time, final String idcard, final String name, final String mobile, final String ticketcode) {
        new AsyncTask<String, Integer, String>() {

            @Override
            protected String doInBackground(String... params) {
                return WebUtil.AddAppointment(projectno, date, time, idcard, name, mobile, ticketcode);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    Intent intent = new Intent(mActivity, AppointmentInfoActivity.class);
                    intent.putExtra("affairItem", affairItem);
                    intent.putExtra("name", name);
                    intent.putExtra("mobile", mobile);
                    intent.putExtra("idcard", idcard);
                    intent.putExtra("date", date + "   " + time);
                    startActivity(intent);
                }

            }
        }.execute();
    }

    public void existsTicketCode(final String ticketcode) {
        new AsyncTask<String, Integer, String>() {

            @Override
            protected String doInBackground(String... params) {
                return WebUtil.existsTicketCode(ticketcode);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    if (s.equals("0")) {
                        addAppointment(affairItem.getProject_no(), date, time, idcard, name, mobile, ticketcode);
                    } else if (s.equals("1")) {
                        ToastUtils.showToast(mActivity, "网络异常，请重试");
                    } else if (s.equals("-2")) {
                        ToastUtils.showToast(mActivity, "服务器异常");
                    }
                }

            }
        }.execute();
    }
}

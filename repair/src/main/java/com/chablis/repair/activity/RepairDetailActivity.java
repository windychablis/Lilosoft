package com.chablis.repair.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chablis.repair.R;
import com.chablis.repair.base.BaseTitleActivity;
import com.chablis.repair.base.SoapAsyncTask;
import com.chablis.repair.base.TaskCallBack;
import com.chablis.repair.model.Equipment;
import com.chablis.repair.utils.CommonUtil;
import com.chablis.repair.utils.SoapUtils;
import com.chablis.repair.widget.ImageManagerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepairDetailActivity extends BaseTitleActivity {

    @BindView(R.id.image_managerView)
    ImageManagerView imageManagerView;
    @BindView(R.id.tv_describe)
    TextView tvDescribe;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_area1)
    TextView tvArea1;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_area2)
    TextView tvArea2;
    @BindView(R.id.tv_floor)
    TextView tvFloor;
    @BindView(R.id.tv_ip)
    TextView tvIp;
    @BindView(R.id.tv_remarks)
    TextView tvRemarks;
    @BindView(R.id.et_propose)
    EditText etPropose;
    @BindView(R.id.tv_title2)
    TextView tvTitle2;
    @BindView(R.id.tv_divice2)
    TextView tvDivice2;

    private Equipment.RepairInfo repairInfo;
    private Equipment equipment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_repair_detail);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        repairInfo = (Equipment.RepairInfo) intent.getSerializableExtra("repairInfo");
        getEquipmentInfo(repairInfo.getCLIENT_TYPE());
        Log.d("RepairDetailActivity", "repairInfo:" + repairInfo);

        imageManagerView.setOnImageClickListener(new ImageManagerView.OnImageClickListener() {
            @Override
            public void onImageClick(int position, String filePath, ImageView iv) {

            }

            @Override
            public void onDeleteClick(int position, String filePath) {
                imageManagerView.removeImage(position);
            }

            @Override
            public void onAddClick() {
                imageManagerView.addImage(null);
            }
        });
    }

    public void initView() {
        tvDescribe.setText(equipment.getClientinfo().getCLIENT_TYPE());
        tvType.setText(equipment.getClientinfo().getTERM_TYPE());
        tvArea1.setText(equipment.getClientinfo().getAREA_CODE());
        tvArea2.setText(equipment.getClientinfo().getREMARK());
        tvDate.setText(equipment.getClientinfo().getTIMEVIEW());
        tvFloor.setText(equipment.getClientinfo().getFLOORNUM());
        tvIp.setText(equipment.getClientinfo().getCLIENT_IP());
        tvRemarks.setText(equipment.getClientinfo().getREMARK());

        tvTitle2.setText(repairInfo.getBIGCLASS());
        tvDivice2.setText(repairInfo.getSMALLCLASS());

    }

    /**
     *  获取设备信息
     */
    public void getEquipmentInfo(final String num) {
        new SoapAsyncTask(new TaskCallBack() {
            @Override
            public String doInBackground() {
                return SoapUtils.equipmentInfo(num);
            }

            @Override
            public void onSuccess(String result) {
                Log.d("RepairDetailActivity", result);
                equipment = JSONObject.parseObject(result, Equipment.class);
                initView();
            }

            @Override
            public void onFailure(String msg) {
                Log.d("RepairDetailActivity", msg);
                CommonUtil.showToast(mActivity,msg);
            }
        }).execute();
    }
}

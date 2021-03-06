package com.chablis.repair.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.bigkoo.pickerview.OptionsPickerView;
import com.chablis.repair.R;
import com.chablis.repair.adapter.InformationAdapter;
import com.chablis.repair.base.BaseTitleActivity;
import com.chablis.repair.model.Area;
import com.chablis.repair.model.Equipment;
import com.chablis.repair.rx.Response;
import com.chablis.repair.rx.RxObserverableCallBack;
import com.chablis.repair.rx.SoapObservable;
import com.chablis.repair.rx.SoapObserver;
import com.chablis.repair.rx.SoapObserverArray;
import com.chablis.repair.utils.CommonUtil;
import com.chablis.repair.utils.SoapUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class RepairActivity extends BaseTitleActivity {
    @BindView(R.id.linearLayout3)
    LinearLayout linearLayout3;
    @BindView(R.id.tv_gov)
    TextView tvGov;
    private OptionsPickerView pickerView;
    private ArrayList data;
    private ArrayList<Area> areas;
    private InformationAdapter mAdapter;
    private View nodata;
    private View footer;

    @BindView(R.id.list)
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_repair);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAreaAndRepairList();
    }

    public void getAreaAndRepairList() {
        hud= KProgressHUD.create(mActivity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        Observable<String> observable1 = SoapObservable.getStringObservable(new RxObserverableCallBack() {
            @Override
            public String doWebRequest() {
                return SoapUtils.getRepairList("");
            }
        });
        Observable<String> observable2 = SoapObservable.getStringObservable(new RxObserverableCallBack() {
            @Override
            public String doWebRequest() {
                return SoapUtils.getAreaList();
            }
        });
        Observable.combineLatest(observable1, observable2, new BiFunction<String, String, String>() {
            @Override
            public String apply(@NonNull String s, @NonNull String s2) throws Exception {
                return s + "||" + s2;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers
                        .mainThread()).subscribe(new SoapObserverArray() {

            @Override
            public void onSuccess(String s) {
                hud.dismiss();
                String a = s.split("\\|\\|")[0];
                String b = s.split("\\|\\|")[1];
                initList(a);
                initArea(b);
            }

            @Override
            public void onFailure(String s) {
                hud.dismiss();
                Log.d("RepairActivity", s);
                CommonUtil.showToast(mActivity, s);
                View nodata = getLayoutInflater().inflate(R.layout.information_nodata, null);
                list.addFooterView(nodata, null, false);
            }
        });
    }

    public void getRepairList(final String areaCode) {
        hud= KProgressHUD.create(mActivity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        Observable<Response> observable = SoapObservable.getAnyObservable(new RxObserverableCallBack() {
            @Override
            public String doWebRequest() {
                return SoapUtils.getRepairList(areaCode);
            }
        });
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers
                        .mainThread()).subscribe(new SoapObserver<Response>() {
            @Override
            public void onSuccess(String s) {
                hud.dismiss();
                Log.d("RepairActivity", s);
                initList(s);
            }

            @Override
            public void onFailure(String s) {
                hud.dismiss();
                Log.d("RepairActivity", s);
                CommonUtil.showToast(mActivity, s);
                View nodata = getLayoutInflater().inflate(R.layout.information_nodata, null);
                list.addFooterView(nodata, null, false);
            }
        });
    }

    public void initArea(String json) {
        List data = new ArrayList();
        final List<Area> areas = JSONArray.parseArray(json, Area.class);
        tvGov.setText(areas.get(0).getName());
        for (Area area : areas) {
            data.add(area.getName());
        }
        pickerView = new OptionsPickerView.Builder(mActivity, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                Area area = areas.get(options1);
                tvGov.setText(area.getName());
                getRepairList(area.getAreaCode());
            }
        }).setSelectOptions(0)
                .setLineSpacingMultiplier(2.0f)//设置两横线之间的间隔倍数
                .build();
        pickerView.setPicker(data);
    }

    public void initList(String json) {
        Log.d("RepairActivity", json);
        linearLayout3.setVisibility(View.VISIBLE);
        list.removeFooterView(footer);
        list.removeFooterView(nodata);
        data = (ArrayList) JSONArray.parseArray(json, Equipment.RepairInfo.class);
        mAdapter = new InformationAdapter(mActivity, data);
        list.setAdapter(mAdapter);//没有数据
        if (data.size() == 0) {
            nodata = getLayoutInflater().inflate(R.layout.information_nodata, null);
            list.addFooterView(nodata, null, false);

        }
        footer = getLayoutInflater().inflate(R.layout.information_table2, null);
        list.addFooterView(footer, null, false);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "position:" + position);
                Equipment.RepairInfo repairInfo = (Equipment.RepairInfo) data.get(position);
                Intent intent = new Intent(mActivity, RepairDetailActivity.class);
                intent.putExtra("repairInfo", repairInfo);
                startActivity(intent);
            }
        });
    }


    @OnClick(R.id.relativeLayout)
    public void onViewClicked() {
        pickerView.show();
    }

}

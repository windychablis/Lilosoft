package com.chablis.repair.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class RepairActivity extends BaseTitleActivity {
    private OptionsPickerView pickerView;
    private ArrayList data;
    private ArrayList<Area> areas;
    private TextView tv_gov;

    @BindView(R.id.list)
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_repair);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getRepairList();
        View header = getLayoutInflater().inflate(R.layout.repair_table1, null);
        tv_gov = (TextView) header.findViewById(R.id.tv_gov);
        list.addHeaderView(header);

        View footer = getLayoutInflater().inflate(R.layout.information_table2, null);
        list.addFooterView(footer, null, false);


    }

    public void getRepairList() {
        Observable<String> observable1 = SoapObservable.getStringObservable(new RxObserverableCallBack() {
            @Override
            public String doWebRequest() {
                return SoapUtils.getRepairList();
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
                return s + "|" + s2;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers
                        .mainThread()).subscribe(new SoapObserverArray() {

            @Override
            public void onSuccess(String s) {
                String a = s.split("\\|")[0];
                String b = s.split("\\|")[1];
                initList(a);
                initArea(b);
            }

            @Override
            public void onFailure(String s) {

            }
        });
    }

    public void initArea(String json) {
        List data = new ArrayList();
        final List<Area> areas = JSONArray.parseArray(json, Area.class);
        for (Area area : areas) {
            data.add(area.getName());
        }
        pickerView = new OptionsPickerView.Builder(mActivity, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                Area area = areas.get(options1);
                tv_gov.setText(area.getName());
            }
        }).setSelectOptions(0)
                .setContentTextSize(20)//设置滚轮文字大小
                .setLineSpacingMultiplier(2.0f)//设置两横线之间的间隔倍数
                .build();
        pickerView.setPicker(data);

    }

    public void initList(String json) {
        data = (ArrayList) JSONArray.parseArray(json, Equipment.RepairInfo.class);
        list.setAdapter(new InformationAdapter(mActivity, data));//没有数据
        if (data.size() == 0) {
            View nodata = getLayoutInflater().inflate(R.layout.information_nodata, null);
            list.addFooterView(nodata, null, false);
        }
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //listview有头部，下标从1开始
                if (position == 0) {
                    pickerView.show();
                    return;
                }
                Log.d(TAG, "position:" + position);
                Equipment.RepairInfo repairInfo = (Equipment.RepairInfo) data.get(position - 1);
                Intent intent = new Intent(mActivity, RepairDetailActivity.class);
                intent.putExtra("repairInfo", repairInfo);
                startActivity(intent);
            }
        });
    }
}

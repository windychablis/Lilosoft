package com.chablis.repair.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chablis.repair.R;
import com.chablis.repair.model.Equipment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chablis on 2017/6/26.
 */

public class InformationAdapter extends BaseAdapter {
    private LayoutInflater mInflater = null;
    private ArrayList<Equipment.RepairInfo> data;

    public InformationAdapter(Context context, ArrayList<Equipment.RepairInfo> data) {
        mInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Equipment.RepairInfo getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Equipment.RepairInfo repairInfo = getItem(position);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_information, parent,
                    false);
            holder = new ViewHolder(convertView);
            holder.tvBreakType.setText(repairInfo.getTITLE().equals("")?repairInfo.getBIGCLASS()+"|"+repairInfo.getSMALLCLASS():repairInfo.getTITLE());
//            holder.tvDescribe.setText(repairInfo.getSMALLCLASS());
            holder.tvTime.setText(repairInfo.getREPAIRDATE());
            holder.tvType.setText(repairInfo.getSTATUS().equals("0") ? "未维修" : "已维修");

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_break_type)
        TextView tvBreakType;
        @BindView(R.id.tv_describe)
        TextView tvDescribe;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_type)
        TextView tvType;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

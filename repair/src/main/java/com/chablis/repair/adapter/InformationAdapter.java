package com.chablis.repair.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chablis.repair.R;

import java.util.ArrayList;

/**
 * Created by chablis on 2017/6/26.
 */

public class InformationAdapter extends BaseAdapter {
    private LayoutInflater mInflater = null;
    private ArrayList<String> data;

    public InformationAdapter(Context context, ArrayList<String> data) {
        mInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = mInflater.inflate(R.layout.item_information, parent,
                    false);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        return convertView;
    }

    static class Holder {
        private TextView textView;
    }
}

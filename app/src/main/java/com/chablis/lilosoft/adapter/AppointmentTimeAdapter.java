package com.chablis.lilosoft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.chablis.lilosoft.R;

import java.util.ArrayList;

/**
 * Created by chablis on 2017/3/24.
 */

public class AppointmentTimeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> data;
    private LayoutInflater mInflater = null;

    public AppointmentTimeAdapter(Context context, ArrayList<String> data) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = mInflater.inflate(R.layout.item_time, parent,
                    false);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        return convertView;
    }

    static class Holder {
    }
}

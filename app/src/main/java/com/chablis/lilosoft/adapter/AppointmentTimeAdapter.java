package com.chablis.lilosoft.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.activity.AppointmentTimeActivity;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.model.AppointmentTime;

import java.util.ArrayList;

/**
 * Created by chablis on 2017/3/24.
 */

public class AppointmentTimeAdapter extends BaseAdapter {
    private BaseActivity context;
    private ArrayList<AppointmentTime> data;
    private LayoutInflater mInflater = null;

    public AppointmentTimeAdapter(BaseActivity context, ArrayList<AppointmentTime> data) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public AppointmentTime getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        final AppointmentTime appointmentTime=getItem(position);
        if (convertView == null) {
            holder = new Holder();
            convertView = mInflater.inflate(R.layout.item_time, parent,
                    false);
            holder.title= (TextView) convertView.findViewById(R.id.tv_time);
            holder.num1= (TextView) convertView.findViewById(R.id.tv_num1);
            holder.num2= (TextView) convertView.findViewById(R.id.tv_num2);
            holder.btn_OK= (Button) convertView.findViewById(R.id.btn_OK);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.title.setText(appointmentTime.getRegion());
        holder.num1.setText(appointmentTime.getSubNum());
        holder.num2.setText(appointmentTime.getiCount());
        holder.btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date=((AppointmentTimeActivity)context).date;
                context.setResult(Activity.RESULT_OK, context.getIntent().putExtra("date",date).putExtra("time",appointmentTime.getRegion()));
                context.finish();
            }
        });
        return convertView;
    }

    static class Holder {
        private TextView title;
        private TextView num1;
        private TextView num2;
        private Button btn_OK;
    }
}

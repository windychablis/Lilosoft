package com.chablis.lilosoft.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chablis.lilosoft.R;

import java.util.ArrayList;


/**
 * 完善资料的适配器
 *
 * @author chenhao
 * @date 2015-05-04
 */
public class QuestionAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater = null;
    private ArrayList<Object> data;

    public QuestionAdapter(Context context, ArrayList<Object> data) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public QuestionAdapter getItem(int position) {
        return null;
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
            convertView = mInflater.inflate(R.layout.item_questionnaire, parent,
                    false);
            holder.tv_no = (TextView) convertView.findViewById(R.id.tv_no);
            holder.tv_question = (TextView) convertView.findViewById(R.id.tv_question);
            holder.radio_a = (RadioButton) convertView.findViewById(R.id.radio_a);
            holder.radio_b = (RadioButton) convertView.findViewById(R.id.radio_b);
            holder.radio_c = (RadioButton) convertView.findViewById(R.id.radio_c);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        String no = position + 1 + "";
        holder.tv_no.setText(no);
        holder.radio_a.setText("A、环境不错");
        holder.radio_b.setText("B、环境不错");
        holder.radio_c.setText("C、环境不错");

        return convertView;
    }

    static class Holder {
        private TextView tv_no;
        private TextView tv_question;
        private RadioButton radio_a;
        private RadioButton radio_b;
        private RadioButton radio_c;
    }
}

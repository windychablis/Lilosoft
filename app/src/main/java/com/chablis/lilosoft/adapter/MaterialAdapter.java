package com.chablis.lilosoft.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.activity.DeptListActivity;
import com.chablis.lilosoft.activity.MatterListActivity;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.model.Dept;
import com.chablis.lilosoft.model.Material2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chablis on 2017/1/16.
 */

public class MaterialAdapter extends BaseAdapter {
    private List<Material2> mList;
    private LayoutInflater mInflater = null;
    private Context context;

    public MaterialAdapter(Context context, List<Material2> data) {
        this.mList = data;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final Material2 material2 = mList.get(i);
        Holder holder = null;
        if (view == null) {
            holder = new Holder();
            view = mInflater.inflate(R.layout.item_affair, viewGroup,
                    false);
            holder.name = (TextView) view.findViewById(R.id.tv_name);
            holder.tv2 = (TextView) view.findViewById(R.id.tv2);
            holder.tv4 = (TextView) view.findViewById(R.id.tv4);
            holder.expand_flag = (ImageView) view.findViewById(R.id.expand_flag);
            holder.rl_expand= (RelativeLayout) view.findViewById(R.id.rl_expand);
            holder.rl_top= (RelativeLayout) view.findViewById(R.id.rl_top);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        final Holder finalHolder = holder;
        holder.rl_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalHolder.rl_expand.getVisibility()==View.GONE){
                    finalHolder.expand_flag.setImageResource(R.mipmap.expand_02);
                    finalHolder.rl_expand.setVisibility(View.VISIBLE);
                }else{
                    finalHolder.expand_flag.setImageResource(R.mipmap.expand_01);
                    finalHolder.rl_expand.setVisibility(View.GONE);
                }
            }
        });

        holder.name.setText(material2.getMaterial_name());
        holder.tv2.setText(material2.getDescription());
        holder.tv4.setText(material2.getIs_declare());

        return view;
    }

    static class Holder {
        private TextView name;
        private TextView tv2;
        private TextView tv4;
        private ImageView expand_flag;
        private RelativeLayout rl_expand;
        private RelativeLayout rl_top;
    }
}

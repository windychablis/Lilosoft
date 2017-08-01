package com.chablis.lilosoft.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.activity.DeptListActivity;
import com.chablis.lilosoft.activity.MatterListActivity;
import com.chablis.lilosoft.base.BaseActivity;
import com.chablis.lilosoft.model.Dept;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chablis on 2017/1/16.
 */

public class DeptAdapter extends BaseAdapter {
    private List<Dept> mList;
    private LayoutInflater mInflater = null;
    private Context context;
    public static final int APP_PAGE_SIZE = 16;//每一页装载数据的大小

    public DeptAdapter(Context context, List<Dept> data) {
        this.mList = data;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public DeptAdapter(Context context, List<Dept> data, int page) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mList = new ArrayList<Dept>();
        //根据当前页计算装载的应用，每页只装载16个
        int i = page * APP_PAGE_SIZE;//当前页的其实位置
        int iEnd = i + APP_PAGE_SIZE;//所有数据的结束位置
        while ((i < data.size()) && (i < iEnd)) {
            mList.add(data.get(i));
            i++;
        }

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
        final Dept dept = mList.get(i);
        Holder holder = null;
        if (view == null) {
            holder = new Holder();
            view = mInflater.inflate(R.layout.grid_item, viewGroup,
                    false);
            holder.icon = (ImageView) view.findViewById(R.id.image);
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        Context c = ((BaseActivity) context).getBaseContext();
        if (!dept.getDept_icon().equals("")) {
            int id = context.getResources().getIdentifier(dept.getDept_icon(), "mipmap", context.getPackageName());
            holder.icon.setImageResource(id);
        }
        holder.name.setText(dept.getDept_name());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof DeptListActivity) {
//                    if (((DeptListActivity) context).appContext.TAB == 0) {
//                        //TODO 办事指南
//                        Intent intent = new Intent(context, AffairActivity.class);
//                        context.startActivity(intent);
//                    } else if (((DeptListActivity) context).appContext.TAB == 1) {
//                        //TODO 预约办事
//                        Intent intent = new Intent(context, AppointmentActivity.class);
//                        context.startActivity(intent);
//                    }
                    Intent intent = new Intent(context, MatterListActivity.class);
                    intent.putExtra("dept", dept);
                    context.startActivity(intent);
                }
            }
        });

        return view;
    }

    static class Holder {
        private ImageView icon;
        private TextView name;
    }
}

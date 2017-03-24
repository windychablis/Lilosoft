package com.chablis.lilosoft.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.activity.AffairActivity;
import com.chablis.lilosoft.activity.AppointmentActivity;
import com.chablis.lilosoft.activity.DeptListActivity;
import com.chablis.lilosoft.activity.MainActivity;
import com.chablis.lilosoft.activity.MatterListActivity;
import com.chablis.lilosoft.activity.TableListActivity;
import com.chablis.lilosoft.base.Global;
import com.chablis.lilosoft.model.TDDept;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chablis on 2017/1/16.
 */

public class GridViewAdapter extends BaseAdapter {
    private List<TDDept> mList;
    private LayoutInflater mInflater = null;
    private Context context;
    public static final int APP_PAGE_SIZE = 16;//每一页装载数据的大小

    public GridViewAdapter(Context context, List<TDDept> data) {
        this.mList = data;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public GridViewAdapter(Context context, List<TDDept> data, int page) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mList = new ArrayList<TDDept>();
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
        TDDept dept = mList.get(i);
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
        File file = new File(Global.getAppFileAbsolutePath(context,
                dept.getDIR_URL() + File.separator + dept.getICO_PATH()));
        if (file.exists()) {
            String path = Global.getAppFileAbsolutePath(context,
                    dept.getDIR_URL() + File.separator + dept.getICO_PATH());
            holder.icon.setImageDrawable(Drawable
                    .createFromPath(path));
        }
//        holder.icon.setImageResource(dept.getRes());
        holder.name.setText(dept.getDEPT_NAME());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof MainActivity) {
                    //i为0开始的下标
                    Intent intent = new Intent(context, TableListActivity.class);
                    intent.putExtra("position", i);
                    context.startActivity(intent);
                } else if (context instanceof DeptListActivity) {
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
                    intent.putExtra("position", i);
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

package com.chablis.lilosoft.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chablis.lilosoft.activity.TableDetailActivity;
import com.chablis.lilosoft.model.TDForm;
import com.chablis.lilosoft.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chablis on 2017/1/16.
 */

public class GridViewTableListAdapter extends BaseAdapter {
    private List<TDForm> mList;
    private LayoutInflater mInflater = null;
    private Context context;
    public static final int APP_PAGE_SIZE = 12;//每一页装载数据的大小

    public GridViewTableListAdapter(Context context, List<TDForm> data) {
        this.mList = data;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    public GridViewTableListAdapter(Context context, List<TDForm> data, int page) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mList = new ArrayList<TDForm>();
        //根据当前页计算装载的应用，每页只装载12个
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
        TDForm form = mList.get(i);
        Holder holder = null;
        if (view == null) {
            holder = new Holder();
            view = mInflater.inflate(R.layout.table_list_item, viewGroup,
                    false);
//            holder.icon=(ImageView) view.findViewById(R.id.image);
            holder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
//        holder.icon.setImageResource(form.getRes());
        holder.name.setText(form.getFORM_NAME());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TDForm appInfo =mList.get(i);
                Intent intent = new Intent(context, TableDetailActivity.class);
                intent.putExtra("position",i);
                context.startActivity(intent);
            }
        });
        return view;
    }

    static class Holder {
        private ImageView icon;
        private TextView name;
    }
}

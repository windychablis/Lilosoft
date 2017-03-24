package com.chablis.lilosoft.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.activity.AppointmentActivity;

import java.util.ArrayList;

/**
 * Created by chablis on 2017/3/23.
 */

public class MatterAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<String> gData;
    private ArrayList<ArrayList<String>> iData;

    public MatterAdapter(Context mContext, ArrayList<String> gData, ArrayList<ArrayList<String>> iData) {
        this.context = mContext;
        this.gData = gData;
        this.iData = iData;
    }

    @Override
    public int getGroupCount() {
        return 3;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return gData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return iData.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderGroup groupHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_matter_group, parent, false);
            groupHolder = new ViewHolderGroup();
            groupHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            groupHolder.expand_flag = (ImageView) convertView.findViewById(R.id.expand_flag);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (ViewHolderGroup) convertView.getTag();
        }
        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.ll_group);
        if (isExpanded) {
            linearLayout.setBackgroundResource(R.drawable.d_bg_matter_group_selected);
            groupHolder.expand_flag.setBackgroundResource(R.mipmap.expand_02);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 0);
            linearLayout.setLayoutParams(layoutParams);
        } else {
            linearLayout.setBackgroundResource(R.drawable.d_bg_matter_group);
            groupHolder.expand_flag.setBackgroundResource(R.mipmap.expand_01);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 30);
            linearLayout.setLayoutParams(layoutParams);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Resources resources = context.getResources();
//        ArrayList<String> arrayList = (ArrayList<String>) getChild(groupPosition, childPosition);
        convertView = LayoutInflater.from(context).inflate(
                R.layout.item_matter_item, parent, false);
        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.ll_item);

        for (int i = 0; i < 3; i++) {
            TextView textView = new TextView(context);
            textView.setText("这是什么事项啊啊啊啊啊");
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setTextColor(resources.getColor(R.color.color_liuliuliu));
            textView.setPadding(10, 10, 0, 10);
            textView.setTextSize(24);
            linearLayout.addView(textView);
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,AppointmentActivity.class);
                    context.startActivity(intent);

                }
            });
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private static class ViewHolderGroup {
        private TextView tv_name;
        private ImageView expand_flag;
    }
}

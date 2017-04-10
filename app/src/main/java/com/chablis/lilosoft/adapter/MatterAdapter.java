package com.chablis.lilosoft.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chablis.lilosoft.R;
import com.chablis.lilosoft.activity.AffairActivity;
import com.chablis.lilosoft.activity.AppointmentActivity;
import com.chablis.lilosoft.activity.MatterListActivity;
import com.chablis.lilosoft.model.Affair;
import com.chablis.lilosoft.model.AffairItem;

import java.util.ArrayList;

/**
 * Created by chablis on 2017/3/23.
 */

public class MatterAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<Affair> gData;
    private ArrayList<ArrayList<AffairItem>> iData;

    public MatterAdapter(Context mContext, ArrayList<Affair> gData) {
        this.context = mContext;
        this.gData = gData;
        this.iData = iData;
    }

    @Override
    public int getGroupCount() {
        return gData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return getGroup(groupPosition).getAffairItems()==null?0:1;
    }

    @Override
    public Affair getGroup(int groupPosition) {
        return gData.get(groupPosition);
    }

    @Override
    public ArrayList<AffairItem> getChild(int groupPosition, int childPosition) {
        return iData.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
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
        Affair affair = getGroup(groupPosition);
        ViewHolderGroup groupHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_matter_group, parent, false);
            groupHolder = new ViewHolderGroup();
            groupHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            groupHolder.expand_flag = (ImageView) convertView.findViewById(R.id.expand_flag);
            groupHolder.iv_img= (ImageView) convertView.findViewById(R.id.iv_img);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (ViewHolderGroup) convertView.getTag();
        }
        groupHolder.tv_name.setText(affair.getItem_name());

        if (((MatterListActivity) context).appContext.TAB == 0){
            //TODO 办事指南
            groupHolder.iv_img.setImageResource(R.mipmap.ban);
        }else if (((MatterListActivity) context).appContext.TAB == 1) {
            //TODO 预约办事
            groupHolder.iv_img.setImageResource(R.mipmap.yu_icon);
        }

        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.ll_group);
        if (isExpanded) {
            linearLayout.setBackgroundResource(R.drawable.d_bg_matter_group_selected);
            groupHolder.expand_flag.setBackgroundResource(R.mipmap.expand_02);
            linearLayout.setPadding(20,0,20,0);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 70);
            layoutParams.setMargins(0, 0, 0, 0);
            linearLayout.setLayoutParams(layoutParams);
        } else {
            linearLayout.setBackgroundResource(R.drawable.d_bg_matter_group);
            groupHolder.expand_flag.setBackgroundResource(R.mipmap.expand_01);
            linearLayout.setPadding(20,0,20,0);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 70);
            layoutParams.setMargins(0, 0, 0, 30);
            linearLayout.setLayoutParams(layoutParams);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Resources resources = context.getResources();
//        ArrayList<AffairItem> arrayList = getChild(groupPosition, childPosition);
        ArrayList<AffairItem> arrayList=gData.get(groupPosition).getAffairItems();
        Log.d("MatterAdapter", "arrayList:" + arrayList);

        convertView = LayoutInflater.from(context).inflate(
                R.layout.item_matter_item, parent, false);
        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.ll_item);
        int length = arrayList.size();
        for (int i = 0; i < length; i++) {
            final AffairItem affairItem = arrayList.get(i);
            TextView textView = new TextView(context);
            textView.setText(affairItem.getProject_name());
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setTextColor(resources.getColor(R.color.color_liuliuliu));
            textView.setPadding(10, 10, 0, 10);
            textView.setTextSize(24);
            linearLayout.addView(textView);
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (((MatterListActivity) context).appContext.TAB == 0) {
                        //TODO 办事指南
                        Intent intent = new Intent(context, AffairActivity.class);
                        intent.putExtra("affair_item",affairItem);
                        context.startActivity(intent);
                    } else if (((MatterListActivity) context).appContext.TAB == 1) {
                        //TODO 预约办事
                        Intent intent = new Intent(context, AppointmentActivity.class);
                        intent.putExtra("appointment",affairItem);
                        context.startActivity(intent);
                    }
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
        private ImageView iv_img;
    }
}

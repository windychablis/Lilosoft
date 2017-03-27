package com.chablis.lilosoft.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chablis.lilosoft.R;

import java.util.List;

/**
 * Created by chabli on 2016/1/5.
 */
public class AppointmentDateAdapter extends RecyclerView.Adapter<AppointmentDateAdapter.ViewHolder>{
    private List<String> data;
    private Context mContext;
    private OnItemClickLitener mOnItemClickLitener;
    private int layoutPosition;

    //单击和长按监听
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public OnItemClickLitener getmOnItemClickLitener() {
        return mOnItemClickLitener;
    }

    public void setmOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;

        public ViewHolder(View v) {
            super(v);
            tv = (TextView) v.findViewById(R.id.tv_time);
        }
    }

    public AppointmentDateAdapter(List<String> data) {
        this.data = data;
    }

    public AppointmentDateAdapter(Context mContext, List<String> data) {
        this.data = data;
        this.mContext=mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        mContext=parent.getContext();
        ViewHolder vh=new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_text,parent,false));
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tv.setText(data.get(position));
        holder.tv.setTag(data.get(position));
        /*holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutPosition=holder.getLayoutPosition();
                notifyDataSetChanged();
                mOnItemClickLitener.onItemClick(holder.tv, layoutPosition);
            }
        });*/

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null){
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutPosition = holder.getLayoutPosition();
                    Log.d("AppointmentDateAdapter", "layoutPosition:" + layoutPosition);
                    notifyDataSetChanged();
                    mOnItemClickLitener.onItemClick(holder.tv, layoutPosition);
                }
            });
            holder.tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.tv, pos);
                    return false;
                }
            });
        }
        //更改状态
        if(position == layoutPosition){
            holder.tv.setTextColor(mContext.getResources().getColor(R.color.white));
        }else{
            holder.tv.setTextColor(mContext.getResources().getColor(R.color.sixty_transparent));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

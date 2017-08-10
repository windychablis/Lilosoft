package com.chablis.repair.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chablis.repair.R;
import com.chablis.repair.model.RepairDetail;
import com.chablis.repair.utils.ImageLoaderUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chablis on 2017/7/10.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {
    private ArrayList<RepairDetail.RepairImage> images;
    private onItemClickListener onItemClickListener;

    public onItemClickListener getItemClickListener() {
        return onItemClickListener;
    }

    public void setItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener {
        void onItemClick(View view,int position);
    }

    public ImagesAdapter(ArrayList<RepairDetail.RepairImage> images) {
        this.images = images;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        ViewHolder holder = null;
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        RepairDetail.RepairImage image = images.get(position);
        Uri uri = Uri.parse(image.getFILE_URL());
//        holder.myImageView.setImageURI(uri);
        ImageLoaderUtils.loaderImage(holder.myImageView,image.getFILE_URL());
        if (onItemClickListener!=null){
            holder.myImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=holder.getLayoutPosition();
                    onItemClickListener.onItemClick(v,pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.my_image_view)
        ImageView myImageView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

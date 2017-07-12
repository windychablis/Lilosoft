package com.chablis.repair.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chablis.repair.R;
import com.chablis.repair.model.EquImage;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chablis on 2017/7/10.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {
    private ArrayList<EquImage> images;

    public ImagesAdapter(ArrayList<EquImage> images) {
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
    public void onBindViewHolder(ViewHolder holder, int position) {
//        EquImage image=images.get(position);
        Uri uri = Uri.parse("http://img5.imgtn.bdimg.com/it/u=4040470692,460373187&fm=26&gp=0.jpg");
        holder.myImageView.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return 4;
    }



    class ViewHolder extends RecyclerView.ViewHolder  {
        @BindView(R.id.my_image_view)
        SimpleDraweeView myImageView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

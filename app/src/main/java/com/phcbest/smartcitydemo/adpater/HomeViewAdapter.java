package com.phcbest.smartcitydemo.adpater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

public class HomeViewAdapter extends BannerAdapter<String, HomeViewAdapter.ViewHolder> {
    public List<String> imageAddress;

    public HomeViewAdapter(List<String> datas) {
        super(datas);
        this.imageAddress = datas;
    }

    @Override
    public HomeViewAdapter.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        //注意，必须设置为match_parent，这个是viewpager2强制要求的
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new ViewHolder(imageView);
    }

    @Override
    public void onBindView(ViewHolder holder, String data, int position, int size) {
        Glide.with(holder.imageView)
                .load(data)
                .into(holder.imageView);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView;
        }
    }

}

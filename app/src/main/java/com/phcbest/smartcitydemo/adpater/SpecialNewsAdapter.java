package com.phcbest.smartcitydemo.adpater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.phcbest.smartcitydemo.R;
import com.phcbest.smartcitydemo.pojo.SpecialNewsPojo;
import com.phcbest.smartcitydemo.utils.OkHttpUtils;

import java.util.List;

public class SpecialNewsAdapter extends RecyclerView.Adapter<SpecialNewsAdapter.ViewHolder> {

    private List<SpecialNewsPojo.RowsBean> data;

    public SpecialNewsAdapter(List<SpecialNewsPojo.RowsBean> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_special_news, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SpecialNewsPojo.RowsBean model = data.get(position);
        Glide.with(holder.itemView).load(OkHttpUtils.ROOT_URL + model.getImgUrl())
                .into(holder.mIvItem);
        holder.mTvItem.setText(model.getTitle() + "\n" + model.getContent());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvItem;
        private TextView mTvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mIvItem = (ImageView) itemView.findViewById(R.id.iv_item);
            mTvItem = (TextView) itemView.findViewById(R.id.tv_item);

        }
    }
}

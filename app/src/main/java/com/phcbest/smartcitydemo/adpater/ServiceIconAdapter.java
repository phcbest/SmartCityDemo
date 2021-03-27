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
import com.phcbest.smartcitydemo.pojo.ServiceCategoryPojo;
import com.phcbest.smartcitydemo.utils.OkHttpUtils;

import java.util.ArrayList;

public class ServiceIconAdapter extends RecyclerView.Adapter<ServiceIconAdapter.ViewHolder> {
    ArrayList<ServiceCategoryPojo.RowsBean> rowsBeans;

    public ServiceIconAdapter(ArrayList<ServiceCategoryPojo.RowsBean> rowsBeans) {
        this.rowsBeans = rowsBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_icon, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiceCategoryPojo.RowsBean rowsBean = rowsBeans.get(position);

        Glide.with(holder.itemView).load(OkHttpUtils.ROOT_URL + rowsBean.getImgUrl())
                .into(holder.mIvIcon);
        holder.mTvName.setText(rowsBean.getServiceName());
    }

    @Override
    public int getItemCount() {
        return rowsBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvIcon;
        private TextView mTvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mIvIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);

        }
    }
}

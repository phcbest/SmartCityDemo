package com.phcbest.smartcitydemo.adpater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.phcbest.smartcitydemo.R;
import com.phcbest.smartcitydemo.pojo.SubWayLinePojo;

import java.util.List;

public class SubWarLineAdapter extends RecyclerView.Adapter<SubWarLineAdapter.ViewHolder> {
    SubWayLinePojo subWayLinePojo;
    String name;

    public SubWarLineAdapter(SubWayLinePojo subWayLinePojo, String name) {
        this.subWayLinePojo = subWayLinePojo;
        this.name = name;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subwar_station, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<SubWayLinePojo.DataBean.MetroStepsListBean> metroStepsList = subWayLinePojo.getData().getMetroStepsList();
        holder.mTvStation.setText(metroStepsList.get(position).getName());
        if (metroStepsList.get(position).getName().equals(name)) {
            holder.mIvNaw.setVisibility(View.VISIBLE);
        } else {
            holder.mIvNaw.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return subWayLinePojo.getData().getMetroStepsList().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mIvNaw;
        private TextView mTvStation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mIvNaw = (ImageView) itemView.findViewById(R.id.iv_naw);
            mTvStation = (TextView) itemView.findViewById(R.id.tv_station);
        }
    }
}

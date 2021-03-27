package com.phcbest.smartcitydemo.adpater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.phcbest.smartcitydemo.R;
import com.phcbest.smartcitydemo.pojo.ServiceCategoryPojo;

import java.util.ArrayList;

public class ServiceAllAdapter extends BaseAdapter {

    ArrayList<ArrayList<ServiceCategoryPojo.RowsBean>> scll;
    ArrayList<String> title;

    public ServiceAllAdapter(ArrayList<ArrayList<ServiceCategoryPojo.RowsBean>> scll, ArrayList<String> title) {
        this.scll = scll;
        this.title = title;
    }

    @Override
    public int getCount() {
        return title.size();
    }

    @Override
    public Object getItem(int position) {
        return scll.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, null);

        TextView mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        RecyclerView mRvItem = (RecyclerView) view.findViewById(R.id.rv_item);
        mTvTitle.setText(title.get(position));
        mRvItem.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.HORIZONTAL));
        mRvItem.setAdapter(new ServiceIconAdapter(scll.get(position)));
        return view;
    }
}

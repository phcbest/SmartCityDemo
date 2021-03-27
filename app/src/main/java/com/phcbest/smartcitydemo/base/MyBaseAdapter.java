package com.phcbest.smartcitydemo.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class MyBaseAdapter<E> extends BaseAdapter {

    private int mLayoutRes;
    private List<E> data;

    public MyBaseAdapter(int mLayoutRes, List<E> data) {
        this.mLayoutRes = mLayoutRes;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mLayoutRes, null);
        doAdapter(view, data.get(position), position);
        return view;
    }

    protected abstract void doAdapter(View view, E e, int position);
}

package com.phcbest.smartcitydemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.phcbest.smartcitydemo.R;
import com.phcbest.smartcitydemo.adpater.HomeViewAdapter;
import com.phcbest.smartcitydemo.base.MyBaseAdapter;
import com.phcbest.smartcitydemo.pojo.CategoryPojo;
import com.phcbest.smartcitydemo.pojo.HomeImagePojo;
import com.phcbest.smartcitydemo.pojo.NewsPojo;
import com.phcbest.smartcitydemo.ui.NewItemActivity;
import com.phcbest.smartcitydemo.utils.OkHttpUtils;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;


public class NewsFragment extends Fragment {


    private Banner mBannerNews;
    private GridView mGvLabel;
    private ListView mLvNews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        initView(view);
        //
        doData();
        doEvent();
        return view;
    }

    private void doEvent() {


    }

    private static final String TAG = "NewsFragment";

    private void doData() {

        OkHttpUtils.netWork("/userinfo/rotation/lists?pageNum=1&pageSize=10&type=47",
                "GET", null, null, new OkHttpUtils.OkHttpCB() {
                    @Override
                    public void onSuccess(String response) {
                        HomeImagePojo homeImagePojo = new Gson()
                                .fromJson(response, HomeImagePojo.class);
                        final ArrayList<String> images = new ArrayList<>();
                        for (HomeImagePojo.RowsBean row : homeImagePojo.getRows()) {
                            images.add(OkHttpUtils.ROOT_URL + row.getImgUrl());
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mBannerNews.setAdapter(new HomeViewAdapter(images));
                                mBannerNews.setIndicator(new CircleIndicator(
                                        NewsFragment.this.getContext()));
                            }
                        });

                    }

                    @Override
                    public void onFailure(Exception e) {
                    }
                });


        OkHttpUtils.netWork("/system/dict/data/type/press_category", "GET",
                null, null, new OkHttpUtils.OkHttpCB() {
                    @Override
                    public void onSuccess(String response) {
                        final CategoryPojo categoryPojo = new Gson().fromJson(response, CategoryPojo.class);
                        getActivity().runOnUiThread((Runnable) () -> {
                            mGvLabel.setNumColumns(categoryPojo.getData().size());
                            mGvLabel.setAdapter(new MyBaseAdapter<CategoryPojo.DataBean>(
                                    R.layout.item_text, categoryPojo.getData()) {
                                @Override
                                protected void doAdapter(View view,
                                                         CategoryPojo.DataBean dataBean,
                                                         int position) {
                                    TextView mTvInfo = (TextView) view.findViewById(R.id.tv_info);
                                    mTvInfo.setText(dataBean.getDictLabel());
                                }
                            });
                            showNews(categoryPojo, 0);
                            //??????????????????
                            mGvLabel.setOnItemClickListener((parent, view, position, id) -> {
                                showNews(categoryPojo, position);
                            });
                        });
                    }

                    private void showNews(CategoryPojo categoryPojo, int position) {
                        OkHttpUtils.netWork("/press/press/list?pageNum=1&pageSize=10&pressCategory="
                                        + categoryPojo.getData().get(position).getDictCode(), "GET",
                                null, null,
                                new OkHttpUtils.OkHttpCB() {
                                    @Override
                                    public void onSuccess(String response) {
                                        NewsPojo newsPojo = new Gson().fromJson(response, NewsPojo.class);
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mLvNews.setAdapter(new MyBaseAdapter<NewsPojo.RowsBean>(
                                                        R.layout.item_news, newsPojo.getRows()
                                                ) {
                                                    @Override
                                                    protected void doAdapter(View view,
                                                                             NewsPojo.RowsBean rowsBean,
                                                                             int position) {
                                                        ImageView mIvItem = (ImageView) view.findViewById(R.id.iv_item);
                                                        TextView mTvItem = (TextView) view.findViewById(R.id.tv_item);
                                                        TextView mTvInfo = (TextView) view.findViewById(R.id.tv_info);
                                                        //?????????????????????????????????????????????????????????????????????
                                                        mTvInfo.setText(
                                                                "???????????????" + rowsBean.getViewsNumber()+"\n"+
                                                                "????????????" + rowsBean.getLikeNumber()
                                                                );
                                                        Glide.with(mIvItem).load(
                                                                OkHttpUtils.ROOT_URL + rowsBean.getImgUrl())
                                                                .into(mIvItem);
                                                        mTvItem.setText(
                                                                rowsBean.getTitle() + "\n" +
                                                                        rowsBean.getContent() + "\n" +
                                                                        rowsBean.getIsRecommend() + "\n" +
                                                                        rowsBean.getUpdateTime() + "\n"
                                                        );
                                                    }

                                                });
                                                mLvNews.setOnItemClickListener((parent, view1, position1, id) -> {

                                                    Intent intent = new Intent(parent.getContext(),
                                                            NewItemActivity.class);
                                                    intent.putExtra("img", OkHttpUtils.ROOT_URL +
                                                            newsPojo.getRows().get(position1).getImgUrl());
                                                    intent.putExtra("title", newsPojo.getRows().get(position1).getTitle());
                                                    intent.putExtra("content", newsPojo.getRows().get(position1).getContent());
                                                    intent.putExtra("id", newsPojo.getRows().get(position1).getId());
                                                    parent.getContext().startActivity(intent);
                                                });

                                            }
                                        });

                                    }

                                    @Override
                                    public void onFailure(Exception e) {

                                    }
                                });
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
    }

    private void initView(View view) {

        mBannerNews = (Banner) view.findViewById(R.id.banner_news);
        mGvLabel = (GridView) view.findViewById(R.id.gv_label);
        mLvNews = (ListView) view.findViewById(R.id.lv_news);
    }
}
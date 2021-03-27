package com.phcbest.smartcitydemo.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.phcbest.smartcitydemo.R;
import com.phcbest.smartcitydemo.base.BaseActivity;
import com.phcbest.smartcitydemo.base.MyBaseAdapter;
import com.phcbest.smartcitydemo.pojo.NewsPojo;
import com.phcbest.smartcitydemo.utils.OkHttpUtils;

import java.lang.annotation.Documented;

public class NewItemActivity extends BaseActivity {


    private android.widget.ImageView mIvBack;
    private android.widget.TextView mTvTitle;
    private android.widget.ImageView mIvImage;
    private android.widget.TextView mTvContent;
    private TextView mTvTitleInfo;
    private android.widget.ListView mLvNews;
    private TextView mBtnComment;
    private int id;

    @Override
    protected int getView() {
        return R.layout.activity_new_item;
    }

    @Override
    protected void doView() {
        initView();
        mTvTitle.setText("新闻详情");
        mIvBack.setColorFilter(0xffffffff);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //

        OkHttpUtils.netWork("/press/press/list?pageNum=1&pageSize=10&pressCategory=48", "GET",
                null, null,
                new OkHttpUtils.OkHttpCB() {
                    @Override
                    public void onSuccess(String response) {
                        NewsPojo newsPojo = new Gson().fromJson(response, NewsPojo.class);
                        if (newsPojo.getRows().size() > 3) {
                            for (int i = 0; i < newsPojo.getRows().size() - 3; i++) {
                                newsPojo.getRows().remove(i);
                            }
                        }
                        runOnUiThread(() -> {
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
                                    //列表显示新闻图片、新闻名称、观看人数、点赞数等
                                    mTvInfo.setText(
                                            "观看人数：" + rowsBean.getViewsNumber() + "\n" +
                                                    "点赞数：" + rowsBean.getLikeNumber()
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
                        });
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
    }

    @Override
    protected void doData() {
        Intent intent = getIntent();
        String img = intent.getStringExtra("img");
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        id = intent.getIntExtra("id", 0);
        mTvContent.setText(content);
        mTvTitleInfo.setText(title);
        Glide.with(mIvImage).load(img).into(mIvImage);
    }

    @Override
    protected void doEvent() {
        mBtnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CommentActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvTitleInfo = (TextView) findViewById(R.id.tv_title_info);
        mIvImage = (ImageView) findViewById(R.id.iv_image);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        mLvNews = (ListView) findViewById(R.id.lv_news);
        mBtnComment = (TextView) findViewById(R.id.btn_comment);
    }
}
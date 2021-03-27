package com.phcbest.smartcitydemo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.phcbest.smartcitydemo.R;
import com.phcbest.smartcitydemo.base.BaseActivity;
import com.phcbest.smartcitydemo.utils.OkHttpUtils;

public class CommentActivity extends BaseActivity {


    private android.widget.ImageView mIvBack;
    private android.widget.TextView mTvTitle;
    private android.widget.ListView mLvCommentList;
    private android.widget.EditText mEtInput;
    private android.widget.TextView mTvSend;
    private int id;
    private String userId;

    @Override
    protected int getView() {
        return R.layout.activity_comment;
    }

    @Override
    protected void doView() {
        initView();
        mIvBack.setColorFilter(0xffffffff);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTvTitle.setText("评论详情");
    }

    @Override
    protected void doData() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        SharedPreferences user_pwd = getSharedPreferences("user_pwd", MODE_PRIVATE);
        userId = user_pwd.getString("userId", "1");
    }

    @Override
    protected void doEvent() {
        mTvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mEtInput.getText().toString();
                if (input.isEmpty()) {
                    Toast.makeText(CommentActivity.this, "请输入评论", Toast.LENGTH_SHORT).show();
                } else {
                    String json = "{\n" +
                            "\"userId\": \"" + userId + "\",\n" +
                            "\"pressId\": \"" + id + "\",\n" +
                            "\"content\": \"" + input + "\"\n" +
                            "}";
                    OkHttpUtils.netWork("/press/pressComment", "POST", null,
                            json, new OkHttpUtils.OkHttpCB() {
                                @Override
                                public void onSuccess(String response) {

                                }

                                @Override
                                public void onFailure(Exception e) {

                                }
                            });
                }
            }
        });

    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mLvCommentList = (ListView) findViewById(R.id.lv_comment_list);
        mEtInput = (EditText) findViewById(R.id.et_input);
        mTvSend = (TextView) findViewById(R.id.tv_send);
    }
}
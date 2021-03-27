package com.phcbest.smartcitydemo.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.phcbest.smartcitydemo.R;
import com.phcbest.smartcitydemo.base.BaseActivity;
import com.phcbest.smartcitydemo.utils.OkHttpUtils;

public class SetPwdActivity extends BaseActivity {


    private android.widget.ImageView mIvBack;
    private android.widget.TextView mTvTitle;
    private android.widget.EditText mEdOldPwd;
    private android.widget.EditText mEdNewPwd;
    private android.widget.TextView mBtnSet;
    private SharedPreferences user_pwd;

    @Override
    protected int getView() {
        return R.layout.activity_set_pwd;
    }

    @Override
    protected void doView() {
        initView();

        mTvTitle.setText("修改密码");
        mIvBack.setColorFilter(0xffffffff);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void doData() {

    }

    @Override
    protected void doEvent() {
        mBtnSet.setOnClickListener(v -> {
            String old = mEdOldPwd.getText().toString();
            String nzw = mEdNewPwd.getText().toString();
            user_pwd = getSharedPreferences("user_pwd", Context.MODE_PRIVATE);
            String userId = user_pwd.getString("userId", null);

            String json = "{\n" +
                    "\"userId\": \"" + userId + "\",\n" +
                    "\"oldPwd \": \"" + old + "\",\n" +
                    "\"password\": \"" + nzw + "\"\n" +
                    "}";
            OkHttpUtils.netWork("/system/user/resetPwd", "PUT", null,
                    json, new OkHttpUtils.OkHttpCB() {
                        @Override
                        public void onSuccess(String response) {

                        }

                        @Override
                        public void onFailure(Exception e) {

                        }
                    });
        });
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mEdOldPwd = (EditText) findViewById(R.id.ed_old_pwd);
        mEdNewPwd = (EditText) findViewById(R.id.ed_new_pwd);
        mBtnSet = (TextView) findViewById(R.id.btn_set);
    }
}
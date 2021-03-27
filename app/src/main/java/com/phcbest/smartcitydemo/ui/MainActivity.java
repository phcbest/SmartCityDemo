package com.phcbest.smartcitydemo.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.phcbest.smartcitydemo.R;
import com.phcbest.smartcitydemo.adpater.HomeViewAdapter;
import com.phcbest.smartcitydemo.base.BaseActivity;
import com.phcbest.smartcitydemo.pojo.HomeImagePojo;
import com.phcbest.smartcitydemo.utils.OkHttpUtils;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.listener.OnPageChangeListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private com.youth.banner.Banner mBannerStart;
    private android.widget.Button mBtnNet;
    private android.widget.Button mBtnStart;

    private SharedPreferences appinfo = null;

    @Override
    protected void doView() {
        initView();
        appinfo = getSharedPreferences(getString(R.string.app_info), MODE_PRIVATE);
        //判断当前是否第一次进入app
        int is_first = appinfo.getInt("is_first", 0);
        if (is_first == 1) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
        //

    }

    @Override
    protected void doData() {
        OkHttpUtils.netWork(
                "/userinfo/rotation/lists?pageNum=1&pageSize=10&type=47",
                "GET", null, null, new OkHttpUtils.OkHttpCB() {
                    @Override
                    public void onSuccess(String response) {
                        HomeImagePojo homeImagePojo = new Gson().fromJson(response, HomeImagePojo.class);
                        final ArrayList<String> images = new ArrayList<>();
                        for (HomeImagePojo.RowsBean row : homeImagePojo.getRows()) {
                            images.add(OkHttpUtils.ROOT_URL + row.getImgUrl());
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mBannerStart.setAdapter(new HomeViewAdapter(images));
                                mBannerStart.setIndicator(new CircleIndicator(MainActivity.this));

                                mBannerStart.addOnPageChangeListener(new OnPageChangeListener() {
                                    @Override
                                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                                    }

                                    @Override
                                    public void onPageSelected(int position) {
                                        Log.i(TAG, "onPageSelected: " + position);
                                        if (position == images.size() - 1) {
                                            mBtnNet.setVisibility(View.VISIBLE);
                                            mBtnStart.setVisibility(View.VISIBLE);
                                        } else {
                                            mBtnNet.setVisibility(View.GONE);
                                            mBtnStart.setVisibility(View.GONE);
                                        }
                                    }

                                    @Override
                                    public void onPageScrollStateChanged(int state) {

                                    }
                                });

                            }
                        });


                    }

                    @Override
                    public void onFailure(Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "网路错误",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
        );


    }

    @Override
    protected void doEvent() {

        mBtnNet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("请设置网络ip和端口号");
                View view = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_net, null);

                final EditText mEtIp = (EditText) view.findViewById(R.id.et_ip);
                final EditText mEtPort = (EditText) view.findViewById(R.id.et_port);

                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String ip = mEtIp.getText().toString();
                        String port = mEtPort.getText().toString();
                        if (!ip.isEmpty() && !port.isEmpty()) {
                            //进行ip和端口存储
                            SharedPreferences.Editor edit = appinfo.edit();
                            edit.putString("ip", ip);
                            edit.putString("port", port);
                            edit.commit();
                            Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "输入不完善", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "取消保存", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setView(view);
                builder.show();
            }
        });

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = appinfo.edit();
                edit.putInt("is_first", 1);
                edit.commit();
                startActivity(new Intent(v.getContext(), HomeActivity.class));
                finish();
            }
        });

    }

    @Override
    protected int getView() {
        return R.layout.activity_main;
    }

    private void initView() {
        mBannerStart = (Banner) findViewById(R.id.banner_start);
        mBtnNet = (Button) findViewById(R.id.btn_net);
        mBtnStart = (Button) findViewById(R.id.btn_start);
    }
}
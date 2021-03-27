package com.phcbest.smartcitydemo.ui;

import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.phcbest.smartcitydemo.R;
import com.phcbest.smartcitydemo.adpater.SubWarLineAdapter;
import com.phcbest.smartcitydemo.base.BaseActivity;
import com.phcbest.smartcitydemo.pojo.SubWayLinePojo;
import com.phcbest.smartcitydemo.utils.OkHttpUtils;

public class LineInfoActivity extends BaseActivity {


    private android.widget.ImageView mIvBack;
    private android.widget.TextView mTvTitle;
    private android.widget.TextView mTvFrom;
    private android.widget.TextView mTvTo;
    private android.widget.TextView mTvTime;
    private android.widget.TextView mTvSpeed;
    private RecyclerView mRvStation;
    private String name;
    private int line;
    private int time;

    @Override
    protected int getView() {
        return R.layout.activity_line_info;
    }

    @Override
    protected void doView() {
        initView();
        line = getIntent().getIntExtra("line", 31);
        name = getIntent().getStringExtra("name");
        time = getIntent().getIntExtra("time", 10);
        mTvTitle.setText(name);
        mIvBack.setColorFilter(0xffffffff);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private static final String TAG = "LineInfoActivity";

    @Override
    protected void doData() {
        OkHttpUtils.netWork("/metro/" + line, "GET", null, null, new OkHttpUtils.OkHttpCB() {
            @Override
            public void onSuccess(String response) {
                SubWayLinePojo subWayLinePojo = new Gson().fromJson(response, SubWayLinePojo.class);
                String info = subWayLinePojo.getData().getName();
                String[] split = info.split("\\(");
                String[] fromandto = split[2].replace(")", "").split("-");
                runOnUiThread(() -> {
                    mTvFrom.setText(fromandto[0]);
                    mTvTo.setText(fromandto[1]);
                    mTvTime.setText(time + "分钟");
                    mTvSpeed.setText(subWayLinePojo.getData().getStationsNumber() + "站/" + subWayLinePojo.getData().getKm() + "km");
                    mRvStation.setLayoutManager(new StaggeredGridLayoutManager(1,
                            StaggeredGridLayoutManager.HORIZONTAL));
                    mRvStation.setAdapter(new SubWarLineAdapter(subWayLinePojo, name));

                });
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    @Override
    protected void doEvent() {

    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvFrom = (TextView) findViewById(R.id.tv_from);
        mTvTo = (TextView) findViewById(R.id.tv_to);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mTvSpeed = (TextView) findViewById(R.id.tv_speed);
        mRvStation = (RecyclerView) findViewById(R.id.rv_station);
    }
}
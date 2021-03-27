package com.phcbest.smartcitydemo.ui;

import android.app.Service;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.phcbest.smartcitydemo.R;
import com.phcbest.smartcitydemo.base.BaseActivity;
import com.phcbest.smartcitydemo.fragment.HomeFragment;
import com.phcbest.smartcitydemo.fragment.NewsFragment;
import com.phcbest.smartcitydemo.fragment.PeopleFragment;
import com.phcbest.smartcitydemo.fragment.ServiceFragment;
import com.phcbest.smartcitydemo.fragment.StudyFragment;

public class HomeActivity extends BaseActivity {


    private android.widget.LinearLayout mLv1;
    private android.widget.ImageView mIv1;
    private android.widget.TextView mTv1;
    private android.widget.LinearLayout mLv2;
    private android.widget.ImageView mIv2;
    private android.widget.TextView mTv2;
    private android.widget.LinearLayout mLv3;
    private android.widget.ImageView mIv3;
    private android.widget.TextView mTv3;
    private android.widget.LinearLayout mLv4;
    private android.widget.ImageView mIv4;
    private android.widget.TextView mTv4;
    private android.widget.LinearLayout mLv5;
    private android.widget.ImageView mIv5;
    private android.widget.TextView mTv5;
    private android.widget.FrameLayout mFlContent;
    private HomeFragment homeFragment;
    private NewsFragment newsFragment;
    private PeopleFragment peopleFragment;
    private ServiceFragment serviceFragment;
    private StudyFragment studyFragment;

    @Override
    protected int getView() {
        return R.layout.activity_home;
    }

    @Override
    protected void doView() {
        homeFragment = new HomeFragment();
        newsFragment = new NewsFragment();
        peopleFragment = new PeopleFragment();
        serviceFragment = new ServiceFragment();
        studyFragment = new StudyFragment();
        initView();
        switchFun(0);
    }

    private void switchFun(int index) {
        setColor(0xff000000, mIv1, mTv1);
        setColor(0xff000000, mIv2, mTv2);
        setColor(0xff000000, mIv3, mTv3);
        setColor(0xff000000, mIv4, mTv4);
        setColor(0xff000000, mIv5, mTv5);
        switch (index) {
            case 0:
                setColor(0xffff0000, mIv1, mTv1);
                setFragment(R.id.fl_content, homeFragment);
                break;
            case 1:
                setColor(0xffff0000, mIv2, mTv2);
                setFragment(R.id.fl_content, serviceFragment);
                break;
            case 2:
                setColor(0xffff0000, mIv3, mTv3);
                setFragment(R.id.fl_content, studyFragment);
                break;
            case 3:
                setColor(0xffff0000, mIv4, mTv4);
                setFragment(R.id.fl_content, newsFragment);
                break;
            case 4:
                setColor(0xffff0000, mIv5, mTv5);
                setFragment(R.id.fl_content, peopleFragment);
                break;
        }
    }

    private void setFragment(int view, Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(view, fragment);
        fragmentTransaction.commit();
    }

    private void setColor(int color, View... views) {
        ((ImageView) views[0]).setColorFilter(color);
        ((TextView) views[1]).setTextColor(color);
    }

    @Override
    protected void doData() {

    }

    @Override
    protected void doEvent() {
        mLv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFun(0);
            }
        });
        mLv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFun(1);
            }
        });
        mLv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFun(2);
            }
        });
        mLv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFun(3);
            }
        });
        mLv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFun(4);
            }
        });

    }

    private void initView() {
        mLv1 = (LinearLayout) findViewById(R.id.lv_1);
        mIv1 = (ImageView) findViewById(R.id.iv_1);
        mTv1 = (TextView) findViewById(R.id.tv_1);
        mLv2 = (LinearLayout) findViewById(R.id.lv_2);
        mIv2 = (ImageView) findViewById(R.id.iv_2);
        mTv2 = (TextView) findViewById(R.id.tv_2);
        mLv3 = (LinearLayout) findViewById(R.id.lv_3);
        mIv3 = (ImageView) findViewById(R.id.iv_3);
        mTv3 = (TextView) findViewById(R.id.tv_3);
        mLv4 = (LinearLayout) findViewById(R.id.lv_4);
        mIv4 = (ImageView) findViewById(R.id.iv_4);
        mTv4 = (TextView) findViewById(R.id.tv_4);
        mLv5 = (LinearLayout) findViewById(R.id.lv_5);
        mIv5 = (ImageView) findViewById(R.id.iv_5);
        mTv5 = (TextView) findViewById(R.id.tv_5);
        mFlContent = (FrameLayout) findViewById(R.id.fl_content);
    }
}
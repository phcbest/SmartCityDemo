package com.phcbest.smartcitydemo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.phcbest.smartcitydemo.R;
import com.phcbest.smartcitydemo.base.BaseActivity;
import com.phcbest.smartcitydemo.base.MyBaseAdapter;
import com.phcbest.smartcitydemo.pojo.OrdersPojo;
import com.phcbest.smartcitydemo.utils.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends BaseActivity {


    private android.widget.ImageView mIvBack;
    private android.widget.TextView mTvTitle;
    private android.widget.TextView mTvFinish;
    private android.widget.TextView mTvUnFinish;
    private android.widget.ListView mLvOrder;

    @Override
    protected int getView() {
        return R.layout.activity_orders;
    }

    @Override
    protected void doView() {
        initView();
        mTvTitle.setText("订单详情");
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

        OkHttpUtils.netWork("/userinfo/orders/list", "GET", null, null,
                new OkHttpUtils.OkHttpCB() {
                    @Override
                    public void onSuccess(String response) {
                        OrdersPojo ordersPojo = new Gson().fromJson(response, OrdersPojo.class);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapterOrdersList(ordersPojo, 0);
                                mTvFinish.setOnClickListener(v -> {
                                    adapterOrdersList(ordersPojo, 0);
                                });
                                mTvUnFinish.setOnClickListener(v -> {
                                    adapterOrdersList(ordersPojo, 1);
                                });
                            }
                        });

                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
    }

    private void adapterOrdersList(OrdersPojo ordersPojo, int state) {
        List<OrdersPojo.DataBean> dataBeans = new ArrayList<>();
        for (OrdersPojo.DataBean datum : ordersPojo.getData()) {
            if (datum.getStatus() == state) {
                dataBeans.add(datum);
            }
        }
        mLvOrder.setAdapter(new MyBaseAdapter<OrdersPojo.DataBean>(
                android.R.layout.simple_list_item_1, dataBeans) {
            @Override
            protected void doAdapter(View view, OrdersPojo.DataBean dataBean, int position) {
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                tv.setText(
                        "订单号:" + dataBean.getOrderNum() + "\n" +
                                "订单生成日期:" + dataBean.getCreateTime() + "\n" +
                                "订单类型:" + dataBean.getPath()
                );
            }
        });
    }

    @Override
    protected void doEvent() {

    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvFinish = (TextView) findViewById(R.id.tv_finish);
        mTvUnFinish = (TextView) findViewById(R.id.tv_un_finish);
        mLvOrder = (ListView) findViewById(R.id.lv_order);
    }
}
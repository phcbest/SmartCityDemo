package com.phcbest.smartcitydemo.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.phcbest.smartcitydemo.R;
import com.phcbest.smartcitydemo.base.BaseActivity;
import com.phcbest.smartcitydemo.base.MyBaseAdapter;
import com.phcbest.smartcitydemo.pojo.SubWayLinePojo;
import com.phcbest.smartcitydemo.pojo.SubWayPojo;
import com.phcbest.smartcitydemo.utils.GLocationUtils;
import com.phcbest.smartcitydemo.utils.OkHttpUtils;

import java.util.List;

public class SubWayActivity extends BaseActivity {


    private android.widget.ImageView mIvBack;
    private android.widget.TextView mTvTitle;
    private android.widget.EditText mEtSearch;
    private android.widget.TextView mTvSubwayLine;
    private android.widget.ListView mLvSubway;
    private static final int REQUEST_LOCATION_CODE = 1;

    @Override
    protected int getView() {
        return R.layout.activity_sub_way;
    }

    @Override
    protected void doUiOption() {
        mTvTitle.setText("城市地铁");
        mIvBack.setColorFilter(0xffffffff);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void doView() {
        initView();
        //进行建国门适配
        adapterSubLineInfo("建国门");
        mEtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String search = v.getText().toString();
                    if (!search.isEmpty()) {
                        adapterSubLineInfo(search);
                    }
                }
                return false;
            }
        });
    }

    private void adapterSubLineInfo(String name) {
        OkHttpUtils.netWork("/metro/list?pageNum=1&pageSize=10&currentName=" + name,
                "GET", null, null, new OkHttpUtils.OkHttpCB() {
                    @Override
                    public void onSuccess(String response) {
                        SubWayPojo subWayPojo = new Gson().fromJson(response, SubWayPojo.class);
                        runOnUiThread((Runnable) () -> mLvSubway.setAdapter(new MyBaseAdapter<SubWayPojo.RowsBean>
                                (R.layout.item_subway, subWayPojo.getRows()) {
                            @Override
                            protected void doAdapter(View view, SubWayPojo.RowsBean rowsBean,
                                                     int position) {
                                TextView mTvInfo = (TextView) view.findViewById(R.id.tv_info);
                                TextView mTvTime = (TextView) view.findViewById(R.id.tv_time);
                                OkHttpUtils.netWork("/metro/" + rowsBean.getLineId(), "GET",
                                        null, null, new OkHttpUtils.OkHttpCB() {
                                            @Override
                                            public void onSuccess(String response) {
                                                SubWayLinePojo subWayLinePojo = new Gson().fromJson(response, SubWayLinePojo.class);
                                                runOnUiThread(() -> {
                                                    for (int i = 0; i < subWayLinePojo.getData().getMetroStepsList().size(); i++) {
                                                        SubWayLinePojo.DataBean.MetroStepsListBean metroStepsListBean = subWayLinePojo.getData().getMetroStepsList().get(i);
                                                        if (metroStepsListBean.getName().equals(name)) {
                                                            String st = name;
                                                            if (subWayLinePojo.getData().getMetroStepsList().size() - 1 < i + 1) {
                                                            } else {
                                                                SubWayLinePojo.DataBean.MetroStepsListBean mlb = subWayLinePojo.getData().getMetroStepsList().get(i + 1);
                                                                st = mlb.getName();
                                                            }
                                                            mTvInfo.setText(rowsBean.getLineName() + "\n" + st);
                                                            mTvTime.setText(rowsBean.getReachTime() + "分钟后");
                                                        }
                                                    }
                                                });

                                            }

                                            @Override
                                            public void onFailure(Exception e) {

                                            }
                                        });

                                mLvSubway.setOnItemClickListener((parent, view1, position1, id) -> {
                                    Intent intent = new Intent(SubWayActivity.this, LineInfoActivity.class);
                                    intent.putExtra("line", rowsBean.getLineId());
                                    intent.putExtra("name", name);
                                    intent.putExtra("time", rowsBean.getReachTime());
                                    //如果使用上下文,需要添加一个flag
//                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                });

                            }
                        }));

                    }

                    @Override
                    public void onFailure(Exception e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SubWayActivity.this, "搜索失败,关键字或网络异常", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
    }

    @Override
    protected void doData() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //请求对应权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
        } else {
            //执行需要权限的操作
            doGetLocation();
        }

        //进行搜索

    }

    private static final String TAG = "SubWayActivity";

    private void doGetLocation() {
        Log.i(TAG, "doGetLocation: 进行定位操作");
        GLocationUtils.getLatAndLog(location -> {
            List<Address> address = GLocationUtils.getAddress(location);
            if (address.size() == 0) {
                Toast.makeText(this, "没有找到匹配的信息", Toast.LENGTH_SHORT).show();
            } else {
                mTvSubwayLine.setText(address.get(0).getLocality());
            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    doGetLocation();
                } else {
                    Toast.makeText(this, "用户没有授予权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void doEvent() {

    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mTvSubwayLine = (TextView) findViewById(R.id.tv_subway_line);
        mLvSubway = (ListView) findViewById(R.id.lv_subway);
    }
}
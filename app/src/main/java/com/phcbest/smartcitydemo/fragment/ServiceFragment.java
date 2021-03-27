package com.phcbest.smartcitydemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.phcbest.smartcitydemo.R;
import com.phcbest.smartcitydemo.adpater.ServiceAllAdapter;
import com.phcbest.smartcitydemo.base.MyBaseAdapter;
import com.phcbest.smartcitydemo.pojo.ServiceCategoryPojo;
import com.phcbest.smartcitydemo.pojo.ServiceTypePojo;
import com.phcbest.smartcitydemo.utils.OkHttpUtils;

import java.util.ArrayList;


public class ServiceFragment extends Fragment {


    private ListView mLvCategory;
    private ListView mLvAll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        initView(view);
        OkHttpUtils.netWork("/system/dict/data/type/sys_service", "GET", null,
                null, new OkHttpUtils.OkHttpCB() {
                    @Override
                    public void onSuccess(String response) {
                        ServiceTypePojo st = new Gson().fromJson(response, ServiceTypePojo.class);
                        getActivity().runOnUiThread(() -> {
                            mLvCategory.setAdapter(new MyBaseAdapter<ServiceTypePojo.DataBean>(
                                    R.layout.item_text, st.getData()) {
                                @Override
                                protected void doAdapter(View view, ServiceTypePojo.DataBean dataBean, int position) {
                                    TextView tv = (TextView) view.findViewById(R.id.tv_info);
                                    tv.setText(dataBean.getDictLabel());
                                }
                            });
                        });
                        OkHttpUtils.netWork("/service/service/list?pageNum=1&pageSize=10", "GET", null,
                                null, new OkHttpUtils.OkHttpCB() {
                                    @Override
                                    public void onSuccess(String response) {
                                        ServiceCategoryPojo sc = new Gson().fromJson(response, ServiceCategoryPojo.class);
                                        //---------------------------
                                        ArrayList<ArrayList<ServiceCategoryPojo.RowsBean>> scll = new ArrayList<>();
                                        ArrayList<String> title = new ArrayList<>();
                                        //---------------------------
                                        for (ServiceTypePojo.DataBean datum : st.getData()) {
                                            ArrayList<ServiceCategoryPojo.RowsBean> scrb = new ArrayList<>();
                                            title.add(datum.getDictLabel());
                                            for (ServiceCategoryPojo.RowsBean row : sc.getRows()) {
                                                if (datum.getDictValue().equals(row.getServiceType())) {
                                                    scrb.add(row);
                                                }
                                            }
                                            scll.add(scrb);
                                        }

                                        getActivity().runOnUiThread(() -> {
                                            mLvAll.setAdapter(new ServiceAllAdapter(scll, title));
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

        return view;
    }

    private void initView(View view) {
        mLvCategory = (ListView) view.findViewById(R.id.lv_category);
        mLvAll = (ListView) view.findViewById(R.id.lv_all);
    }
}
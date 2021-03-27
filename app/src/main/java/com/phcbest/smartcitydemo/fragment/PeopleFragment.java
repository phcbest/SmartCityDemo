package com.phcbest.smartcitydemo.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.phcbest.smartcitydemo.R;
import com.phcbest.smartcitydemo.pojo.UserInfoPojo;
import com.phcbest.smartcitydemo.ui.OrdersActivity;
import com.phcbest.smartcitydemo.ui.SetPwdActivity;
import com.phcbest.smartcitydemo.ui.UpSetActivity;
import com.phcbest.smartcitydemo.utils.OkHttpUtils;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PeopleFragment extends Fragment {


    private CircleImageView mIvImage;
    private TextView mTvUpset;
    private TextView mTvOrder;
    private TextView mTvUpPwd;
    private TextView mTvOpinion;
    private TextView mBtnExit;
    private SharedPreferences user_pwd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmet
        View view = inflater.inflate(R.layout.fragment_people, container, false);
        initView(view);
        user_pwd = getContext().getSharedPreferences("user_pwd", Context.MODE_PRIVATE);
        String sp_user = user_pwd.getString("user", "");
        String sp_pwd = user_pwd.getString("pwd", "");
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_login, null);
        builder.setView(dialogView);
        EditText mEtUser = (EditText) dialogView.findViewById(R.id.et_user);
        EditText mEtPwd = (EditText) dialogView.findViewById(R.id.et_pwd);
        mEtUser.setText(sp_user);
        mEtPwd.setText(sp_pwd);
        builder.setNegativeButton("登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String user = mEtUser.getText().toString();
                String pwd = mEtPwd.getText().toString();
                String json = "{\n" +
                        "\"username\": \"" + user + "\",\n" +
                        "\"password\": \"" + pwd + "\"\n" +
                        "}";
                if (!user.isEmpty() && !pwd.isEmpty()) {
                    OkHttpUtils.netWork("/login", "POST",
                            null, json, new OkHttpUtils.OkHttpCB() {
                                @Override
                                public void onSuccess(String response) {
                                    Map<String, Object> map = (Map<String, Object>)
                                            new Gson().fromJson(response,
                                                    new TypeToken<Map<String, Object>>() {
                                                    }.getRawType());
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(), map.get("msg").toString(), Toast.LENGTH_SHORT).show();
                                            //存储token
                                            if (map.get("token") != null) {
                                                SharedPreferences.Editor edit = user_pwd.edit();
                                                edit.putString("token", map.get("token").toString());
                                                edit.putString("user", user);
                                                edit.putString("pwd", pwd);
                                                edit.apply();
                                                OkHttpUtils.netWork("/getInfo",
                                                        "GET",
                                                        null,
                                                        null,
                                                        new OkHttpUtils.OkHttpCB() {
                                                            @Override
                                                            public void onSuccess(String response) {
                                                                UserInfoPojo userInfoPojo = new Gson().fromJson(response, UserInfoPojo.class);
                                                                String avatar = userInfoPojo.getUser().getAvatar();
                                                                getActivity().runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        SharedPreferences.Editor edit = user_pwd.edit();
                                                                        edit.putString("userId", userInfoPojo.getUser().getUserId() + "");
                                                                        edit.apply();
                                                                        if (avatar == null && avatar.isEmpty()) {
//                                                                            mIvImage.setColorFilter(0xFF000000);
                                                                            mIvImage.setImageResource(R.drawable.prson);
                                                                        } else {
                                                                            Glide.with(getContext())
                                                                                    .load("https://avatars.githubusercontent.com/u/1824223?s=400&u=aa6e9da381f52fe0fef2d3dc8e8b4b2f8911007f&v=4")
                                                                                    .into(mIvImage);
                                                                        }
                                                                    }
                                                                });

                                                            }

                                                            @Override
                                                            public void onFailure(Exception e) {

                                                            }
                                                        });
                                            }

                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Exception e) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getContext(), "登录失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                }


            }
        });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

        mTvUpset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UpSetActivity.class));
            }
        });
        mTvUpPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SetPwdActivity.class));
            }
        });
        mTvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), OrdersActivity.class));
            }
        });
        return view;
    }

    private void initView(View view) {
        mIvImage = (CircleImageView) view.findViewById(R.id.iv_image);
        mTvUpset = (TextView) view.findViewById(R.id.tv_upset);
        mTvOrder = (TextView) view.findViewById(R.id.tv_order);
        mTvUpPwd = (TextView) view.findViewById(R.id.tv_up_pwd);
        mTvOpinion = (TextView) view.findViewById(R.id.tv_opinion);
        mBtnExit = (TextView) view.findViewById(R.id.btn_exit);
    }
}
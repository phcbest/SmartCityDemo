package com.phcbest.smartcitydemo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.phcbest.smartcitydemo.base.MyApplication;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpUtils {

    private static OkHttpClient okHttpClient = new OkHttpClient();

    HashMap<String, String> header = new HashMap<>();


    public OkHttpUtils addHeader(String key, String value) {
        header.put(key, value);
        return this;
    }

    public interface OkHttpCB {
        void onSuccess(String response);

        void onFailure(Exception e);
    }

    private static final String TAG = "OkHttpUtils";
    public static String ROOT_URL = "http://124.93.196.45:10002";

    public static void netWork(String url,
                               String method,
                               OkHttpUtils okHttpUtils,
                               String json,
                               final OkHttpCB cb) {

        RequestBody requestBody = null;
        if (json != null) {
            requestBody = RequestBody.create(json, MediaType.parse("application/json"));
        }
        Request.Builder requestBuilder = new Request.Builder();
        if (okHttpUtils == null) okHttpUtils = new OkHttpUtils();
        SharedPreferences user_pwd = MyApplication.getContext().getSharedPreferences("user_pwd", Context.MODE_PRIVATE);
        String token = user_pwd.getString("token", "");
        okHttpUtils.addHeader("Authorization", token);
        for (String key : okHttpUtils.header.keySet()) {
            requestBuilder.addHeader(key, okHttpUtils.header.get(key));
        }
        requestBuilder.url(ROOT_URL + url);
        requestBuilder.method(method, requestBody);
        okHttpClient.newCall(requestBuilder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "onFailure: " + e.getMessage());
                cb.onFailure(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String string = response.body().string();
                Log.i(TAG, "onResponse: " + string);
                cb.onSuccess(string);
                response.close();
            }
        });


    }

    public static void netWorkFormRequest(String url,
                                          String method,
                                          OkHttpUtils okHttpUtils,
                                          RequestBody requestBody,
                                          final OkHttpCB cb) {


        Request.Builder requestBuilder = new Request.Builder();
        if (okHttpUtils == null) okHttpUtils = new OkHttpUtils();
        SharedPreferences user_pwd = MyApplication.getContext().getSharedPreferences("user_pwd", Context.MODE_PRIVATE);
        String token = user_pwd.getString("token", "");
        okHttpUtils.addHeader("Authorization", token);
        for (String key : okHttpUtils.header.keySet()) {
            requestBuilder.addHeader(key, okHttpUtils.header.get(key));
        }
        requestBuilder.url(ROOT_URL + url);
        requestBuilder.method(method, requestBody);
        okHttpClient.newCall(requestBuilder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "onFailure: " + e.getMessage());
                cb.onFailure(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String string = response.body().string();
                Log.i(TAG, "onResponse: " + string);
                cb.onSuccess(string);
                response.close();
            }
        });
    }
}

package com.phcbest.smartcitydemo.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.phcbest.smartcitydemo.R;
import com.phcbest.smartcitydemo.base.BaseActivity;
import com.phcbest.smartcitydemo.base.MyApplication;
import com.phcbest.smartcitydemo.utils.OkHttpUtils;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;

public class UpSetActivity extends BaseActivity {

    private static final int REQUEST_WRITE_CODE = 0;
    private android.widget.ImageView mIvBack;
    private android.widget.TextView mTvTitle;
    private de.hdodenhof.circleimageview.CircleImageView mIvImage;
    private android.widget.EditText mTvNickName;
    private android.widget.EditText mTvSex;
    private android.widget.EditText mTvCardId;
    private android.widget.EditText mTvPhone;
    private android.widget.TextView mBtnYes;
    private byte[] bytes;
    private SharedPreferences user_pwd;

    @Override
    protected int getView() {
        return R.layout.activity_up_set;
    }

    @Override
    protected void doView() {
        initView();

        user_pwd = getSharedPreferences("user_pwd", Context.MODE_PRIVATE);
        mTvTitle.setText("修改信息");
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

        mIvImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //申请文件权限
                if (ContextCompat.checkSelfPermission(UpSetActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //请求对应权限
                    ActivityCompat.requestPermissions(UpSetActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_CODE);
                } else {
                    //执行需要权限的操作
                    doSelectPhoto();
                }
            }
        });

        mBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickName = mTvNickName.getText().toString();
                String sex = mTvSex.getText().toString();
                String cardId = mTvCardId.getText().toString();
                String phone = mTvPhone.getText().toString();

                if (bytes != null && bytes.length != 0) {

                    MultipartBody.Builder builder = new MultipartBody.Builder();
                    String userId = user_pwd.getString("userId", "");
                    builder.addFormDataPart("userId", userId);
                    builder.addFormDataPart("idCard", cardId);
                    builder.addFormDataPart("nickName", nickName);
                    builder.addFormDataPart("email", "");
                    builder.addFormDataPart("phonenumber", phone);
                    builder.addFormDataPart("sex", sex.equals("男") ? "1" : "0");
                    builder.addFormDataPart("remark", "android");
                    builder.addFormDataPart("file", "icon.jpg",
                            MultipartBody.create(bytes, MediaType.parse("application/octet-stream")));

                    OkHttpUtils.netWorkFormRequest("/system/user/updata", "POST",
                            null, builder.build(), new OkHttpUtils.OkHttpCB() {
                                @Override
                                public void onSuccess(String response) {
                                }

                                @Override
                                public void onFailure(Exception e) {

                                }
                            });
                } else {
                    Toast.makeText(UpSetActivity.this, "头像未选择", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                doSelectPhoto();
            }
        }
    }

    private void doSelectPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    private static final String TAG = "UpSetActivity";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            //对uri进行操作
            Uri uri = data.getData();
            //获得内容解析器
            ContentResolver contentResolver = MyApplication.getContext().getContentResolver();
            //获得fd文件描述符
            try {
                ParcelFileDescriptor pfd = contentResolver.openFileDescriptor(uri, "r");
                FileDescriptor fileDescriptor = pfd.getFileDescriptor();
                //使用位图工厂将fd变为位图
                Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                mIvImage.setImageBitmap(bitmap);
                //将文件转换为byte数组
                FileInputStream fileInputStream = new FileInputStream(fileDescriptor);
                bytes = new byte[fileInputStream.available()];
                int read = fileInputStream.read(bytes);
                Log.i(TAG, "onActivityResult: " + read);

                pfd.close();
                fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mIvImage = (CircleImageView) findViewById(R.id.iv_image);
        mTvNickName = (EditText) findViewById(R.id.tv_nick_name);
        mTvSex = (EditText) findViewById(R.id.tv_sex);
        mTvCardId = (EditText) findViewById(R.id.tv_card_id);
        mTvPhone = (EditText) findViewById(R.id.tv_phone);
        mBtnYes = (TextView) findViewById(R.id.btn_yes);
    }
}
package com.phcbest.smartcitydemo.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    /*
    需要进行ui操作的生命周期
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getView());
        doView();
        doUiOption();
        doData();
        doEvent();
    }

    protected void doUiOption() {

    }

    protected abstract int getView();

    protected abstract void doView();

    protected abstract void doData();

    protected abstract void doEvent();
}

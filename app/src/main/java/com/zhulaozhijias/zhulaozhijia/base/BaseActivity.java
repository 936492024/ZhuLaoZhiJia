package com.zhulaozhijias.zhulaozhijia.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;

/**
 * Created by Administrator on 2017/9/4.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOrientation();
        BPApplication.getInstance().addActivity(this);
        addLayout();
        initView();
    }

    //加载布局的方法
    public abstract  void addLayout();
    //findviewbyid控件初始化方法
    public abstract void initView();
    protected void setOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    public void gotoActivity(Class<?> activity) {//跳转
        Intent intent = new Intent(this,activity);
        startActivity(intent);
        finish();
    }

}

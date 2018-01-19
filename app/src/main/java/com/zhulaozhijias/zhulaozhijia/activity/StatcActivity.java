package com.zhulaozhijias.zhulaozhijia.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.service.ServiceLogin;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;

import java.io.IOException;


/**
 * Created by Administrator on 2017/9/6.
 */

public class StatcActivity extends BaseActivity implements View.OnClickListener {
    private SharedPreferences sharedPreferences;
    private Boolean user_first;
    private Intent intent;

    @Override
    public void addLayout() {
            setContentView(R.layout.activity_statcactivity);
    }

    @Override
    public void initView()  {
        sharedPreferences= getSharedPreferences("IsFrist", 0);
        user_first = sharedPreferences.getBoolean("FIRST", true);
        BPApplication.getInstance().setLogined(false);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                isfirst();
            }
        }, 2000);
    }

    public void isfirst() {
        if (user_first) {// 第一次进入程序
            sharedPreferences.edit().putBoolean("FIRST", false).commit();
            BPApplication.getInstance().setTitle("1");
            gotoActivity(MainActivity.class);
        } else {
            intent=new Intent(StatcActivity.this, ServiceLogin.class);
            startService(intent);
            BPApplication.getInstance().setTitle("1");
            gotoActivity(MainActivity.class);
        }
    }

    @Override
    public void onClick(View view) {

    }
}

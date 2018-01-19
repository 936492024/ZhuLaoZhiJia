package com.zhulaozhijias.zhulaozhijia.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;

/**
 * Created by asus on 2017/10/14.
 */

public class TiShi_Activity extends BaseActivity implements View.OnClickListener{
    private LinearLayout tishi_back;
    private TextView number;
    private TextView wancheng;
    @Override
    public void addLayout() {
        setContentView(R.layout.tishi_activity);
    }

    @Override
    public void initView() {
        tishi_back= (LinearLayout) findViewById(R.id.tishi_back);
        tishi_back.setOnClickListener(this);
        number= (TextView) findViewById(R.id.number);
        wancheng= (TextView) findViewById(R.id.wancheng);
        wancheng.setOnClickListener(this);
        if(!TextUtils.isEmpty(getIntent().getStringExtra("nums"))){
        }else {
//            number.setText(getIntent().getStringExtra("nums"));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tishi_back:
                finish();
                break;
            case R.id.wancheng:
                Intent intent = new Intent(TiShi_Activity.this,HuZhu_Invition.class);
                startActivity(intent);
                finish();
//                BPApplication.getInstance().exits(TiShi_Activity.this);
                break;
        }
    }
}

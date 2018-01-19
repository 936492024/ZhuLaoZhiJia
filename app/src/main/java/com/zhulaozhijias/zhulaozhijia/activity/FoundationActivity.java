package com.zhulaozhijias.zhulaozhijia.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;

/**
 * Created by asus on 2017/11/8.
 */

public class FoundationActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout foundation_back;
    private TextView foundation_phone_1,foundation_phone_2,text_1,text_2,xieyi;
    private Intent intent;
    private ImageView foundation_donate,wechat_juanzeng;
    private LayoutInflater inflater;
    private Dialog dialog;
    @Override
    public void addLayout() {
        setContentView(R.layout.foundation_activity);
    }

    @Override
    public void initView() {
        foundation_back= (LinearLayout) findViewById(R.id.foundation_back);
        foundation_back.setOnClickListener(this);
        foundation_phone_1= (TextView) findViewById(R.id.foundation_phone_1);
        foundation_phone_1.setOnClickListener(this);
        foundation_phone_2= (TextView) findViewById(R.id.foundation_phone_2);
        foundation_phone_2.setOnClickListener(this);
        foundation_donate= (ImageView) findViewById(R.id.foundation_donate);
        foundation_donate.setOnClickListener(this);
        wechat_juanzeng= (ImageView) findViewById(R.id.wechat_juanzeng);
        wechat_juanzeng.setOnClickListener(this);
        text_1= (TextView) findViewById(R.id.text_1);
        text_2= (TextView) findViewById(R.id.text_2);
        String clickTxt = "公募基金会";
        String clickTxt2 = "为国家社会分忧，替天下儿女尽孝";
        int startIndex = getResources().getString(R.string.foundation_text_1).indexOf(clickTxt);
        int endIndex = startIndex + clickTxt.length();
        int startIndex2 = getResources().getString(R.string.foundation_text_2).indexOf(clickTxt2);
        int endIndex2 = startIndex2 + clickTxt2.length();
        setTVColor(getResources().getString(R.string.foundation_text_1),startIndex,endIndex,text_1);
        setTVColor(getResources().getString(R.string.foundation_text_2),startIndex2,endIndex2,text_2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.foundation_back:
                finish();
                break;
            case R.id.foundation_phone_1:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "028-96552"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.foundation_phone_2:
                intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + "028-87777350"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.foundation_donate:
                if(!checkIsLogined()){
                    return;
                }else {
                intent = new Intent(FoundationActivity.this,Mine_RechargeActiviy.class);
                intent.putExtra("FoundationActivity","FoundationActivity");
                startActivity(intent);
                }
                break;
            case R.id.wechat_juanzeng:
                if(!checkIsLogined()){
                    return;
                }else {
                    intent = new Intent(FoundationActivity.this,Mine_RechargeActiviy.class);
                    intent.putExtra("FoundationActivity","FoundationActivity");
                    startActivity(intent);
                }
                break;
        }
    }

    private void setTVColor(String str , int ch1 , int ch2 , TextView tv){
        SpannableStringBuilder spannable = new SpannableStringBuilder(str);
        spannable.setSpan(new ForegroundColorSpan(Color.RED),ch1,ch2
         , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(spannable);
    }

    private boolean checkIsLogined() {
        if (!BPApplication.getInstance().isLogined()) {
            Intent intent = new Intent(FoundationActivity.this, LoginActivity.class);
            startActivity(intent);
            return false;
        }
        return true;
    }


}

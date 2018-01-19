package com.zhulaozhijias.zhulaozhijia.activity;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;

/**
 * Created by asus on 2017/10/8.
 */

public class HuZhu_PlanActivity_GongYue_2 extends BaseActivity {
    private LinearLayout huzhu_plan_gongyue2_back;
    private WebView huzhu_plan_gongyue_2_web_2;
    private ProgressBar progressBar4;
    @Override
    public void addLayout() {
        setContentView(R.layout.huzhu_plan_activity_gongyue_2);
    }

    @Override
    public void initView() {
        progressBar4= (ProgressBar) findViewById(R.id.progressBar4);
        huzhu_plan_gongyue2_back= (LinearLayout) findViewById(R.id.huzhu_plan_gongyue2_back);
        huzhu_plan_gongyue_2_web_2= (WebView) findViewById(R.id.huzhu_plan_gongyue_2_web_2);
        huzhu_plan_gongyue2_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        huzhu_plan_gongyue_2_web_2.loadUrl(SystemConstant.HuZhuPlan.HuZhuPlan_PACT_CANCER);
        WebSettings settings = huzhu_plan_gongyue_2_web_2.getSettings();
        //声明WebSettings子类
        WebSettings webSettings = huzhu_plan_gongyue_2_web_2.getSettings();
        webSettings.setJavaScriptEnabled(true);
        huzhu_plan_gongyue_2_web_2.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        huzhu_plan_gongyue_2_web_2.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根
                if(newProgress==100){
                    progressBar4.setVisibility(View.GONE);//加载完网页进度条消失
                }
                else{
                    progressBar4.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar4.setProgress(newProgress);//设置进度值
                }
            }
        });

    }
}

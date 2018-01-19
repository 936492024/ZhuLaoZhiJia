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

public class HuZhu_PlanActivity_GongYue_1 extends BaseActivity{
    private LinearLayout huzhu_plan_gongyue1_back;
    private WebView huzhu_plan_gongyue_1_web_1;
    private ProgressBar progressBar3;
    @Override
    public void addLayout() {
        setContentView(R.layout.huzhu_plan_activity_gongyue_1);
    }

    @Override
    public void initView() {
        progressBar3= (ProgressBar) findViewById(R.id.progressBar3);
        huzhu_plan_gongyue1_back= (LinearLayout) findViewById(R.id.huzhu_plan_gongyue1_back);
        huzhu_plan_gongyue_1_web_1= (WebView) findViewById(R.id.huzhu_plan_gongyue_1_web_1);
        huzhu_plan_gongyue1_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        huzhu_plan_gongyue_1_web_1.loadUrl(SystemConstant.HuZhuPlan.HuZhuPlan_PACT_HEALTH);
        WebSettings settings = huzhu_plan_gongyue_1_web_1.getSettings();
        //声明WebSettings子类
        WebSettings webSettings = huzhu_plan_gongyue_1_web_1.getSettings();
        webSettings.setJavaScriptEnabled(true);
        huzhu_plan_gongyue_1_web_1.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        huzhu_plan_gongyue_1_web_1.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根
                if(newProgress==100){
                    progressBar3.setVisibility(View.GONE);//加载完网页进度条消失
                }
                else{
                    progressBar3.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar3.setProgress(newProgress);//设置进度值
                }
            }
        });


    }
}

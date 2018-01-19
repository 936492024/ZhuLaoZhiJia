package com.zhulaozhijias.zhulaozhijia.activity;

import android.content.Intent;
import android.net.Uri;
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
 * Created by asus on 2017/10/5.
 */

public class Mine_ProblemActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout mine_problem_back;
    private WebView problem_webview ;
    private ProgressBar pg2;
    @Override
    public void addLayout() {
        setContentView(R.layout.mine_problem_activity);
    }

    @Override
    public void initView() {
        mine_problem_back= (LinearLayout) findViewById(R.id.mine_problem_back);
        mine_problem_back.setOnClickListener(this);
        pg2= (ProgressBar) findViewById(R.id.progressBar2);
        problem_webview= (WebView) findViewById(R.id.problem_webview);
        problem_webview.loadUrl(SystemConstant.GEREN_ZHONGXIN.Mine_USER_ABOUT_ISSUE);
        WebSettings settings = problem_webview.getSettings();
        //声明WebSettings子类
        WebSettings webSettings = problem_webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        problem_webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        problem_webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根
                if(newProgress==100){
                    pg2.setVisibility(View.GONE);//加载完网页进度条消失
                }
                else{
                    pg2.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    pg2.setProgress(newProgress);//设置进度值
                }
            }
        });


    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_problem_back:
                finish();
                break;
        }
    }
}

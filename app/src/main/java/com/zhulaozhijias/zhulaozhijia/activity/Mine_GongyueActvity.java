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
 * Created by asus on 2017/10/5.
 */

public class Mine_GongyueActvity extends BaseActivity implements View.OnClickListener{
    private LinearLayout mine_gongyue_back;
    private WebView web_gongyue;
    private ProgressBar pg1;
    @Override
    public void addLayout() {
        setContentView(R.layout.mine_gongyue_actvity);
    }

    @Override
    public void initView() {
        mine_gongyue_back= (LinearLayout) findViewById(R.id.mine_gongyue_back);
        mine_gongyue_back.setOnClickListener(this);
        pg1= (ProgressBar) findViewById(R.id.progressBar1);
        web_gongyue= (WebView) findViewById(R.id.web_gongyue);
        web_gongyue.loadUrl(SystemConstant.GEREN_ZHONGXIN.Mine_USER_ABOUT_CONVENTION);
        //声明WebSettings子类
        WebSettings webSettings = web_gongyue.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web_gongyue.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        web_gongyue.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根
                if(newProgress==100){
                    pg1.setVisibility(View.GONE);//加载完网页进度条消失
                }
                else{
                    pg1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    pg1.setProgress(newProgress);//设置进度值
                }
            }
        });



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_gongyue_back:
                finish();
                break;

        }
    }
}

package com.zhulaozhijias.zhulaozhijia.activity;

import android.app.Instrumentation;
import android.text.TextUtils;
import android.view.KeyEvent;
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
 * Created by asus on 2017/11/9.
 */

public class TrendsActivity extends BaseActivity implements View.OnClickListener{
    private WebView webView;
    private ProgressBar trends_progressBar;
    private LinearLayout trends_back;
    @Override
    public void addLayout() {
        setContentView(R.layout.trends_activity);
    }

    @Override
    public void initView() {
        webView= (WebView) findViewById(R.id.trends_webview);
        trends_progressBar= (ProgressBar) findViewById(R.id.progressBar_trends);
        trends_back= (LinearLayout) findViewById(R.id.trends_back);
        trends_back.setOnClickListener(this);
        String url = SystemConstant.PublicConstant.Public_WECHAT_RECENTNEWS;
        if(!TextUtils.isEmpty(getIntent().getStringExtra("article_id"))){
            webView.loadUrl(url+"?article_id="+getIntent().getStringExtra("article_id"));
        }
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO 自动生成的方法存根
                if(newProgress==100){
                    trends_progressBar.setVisibility(View.GONE);//加载完网页进度条消失
                }
                else{
                    trends_progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    trends_progressBar.setProgress(newProgress);//设置进度值
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.trends_back:
                actionKey(KeyEvent.KEYCODE_BACK);
//                finish();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void actionKey(final int keyCode) {
        new Thread () {
            public void run () {
                try {
                    Instrumentation inst=new Instrumentation();
                    inst.sendKeyDownUpSync(keyCode);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}

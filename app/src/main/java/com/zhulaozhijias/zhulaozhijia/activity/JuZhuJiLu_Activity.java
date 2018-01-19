package com.zhulaozhijias.zhulaozhijia.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.adpter.ListView_JuZhu_Adapter;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;

import net.sf.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by asus on 2017/9/26.
 */

public class JuZhuJiLu_Activity extends BaseActivity implements MainView,View.OnClickListener{
    private MainPresenter mainPresenter;
    private ListView juanzhu_listview;
    private LinearLayout juanzhujilu__back;
    private RelativeLayout juanzhujilu_defaults;
    private Button juanzhu_btn;
    private ProgressBar acount_progress_bar_2;
    @Override
    public void addLayout() {
        setContentView(R.layout.juzhujilu_activity);
    }

    @Override
    public void initView() {
        mainPresenter= new MainPresenter(this,this);
        acount_progress_bar_2= (ProgressBar) findViewById(R.id.acount_progress_bar_2);
        juanzhu_listview= (ListView) findViewById(R.id.juanzhu_listview);
        juanzhujilu__back= (LinearLayout) findViewById(R.id.juanzhujilu__back);
        juanzhujilu__back.setOnClickListener(this);
        juanzhujilu_defaults= (RelativeLayout) findViewById(R.id.juanzhujilu_defaults);
        juanzhu_btn= (Button) findViewById(R.id.juanzhu_btn);
        juanzhu_btn.setOnClickListener(this);
        alldata();

    }


    public  void alldata(){
        acount_progress_bar_2.setVisibility(View.VISIBLE);
        Map<String ,String> map = new HashMap<>();
        map.put("member_id", BPApplication.getInstance().getMember_Id());
        map.put("secret", CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+SystemConstant.PublicConstant.Public_SECRET));
        mainPresenter.postMap(SystemConstant.LoginedInformatio.USER_JUANZHUJILU,map);
    }

    @Override
    public void getView(String s) {

    }

    @Override
    public void postView(final String s) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONArray jsonArray = JSONArray.fromObject(s);
                    if(jsonArray.size()==0){
                         juanzhujilu_defaults.setVisibility(View.VISIBLE);
                        juanzhu_listview.setVisibility(View.GONE);
                    }else {
                        juanzhujilu_defaults.setVisibility(View.GONE);
                        juanzhu_listview.setVisibility(View.VISIBLE);
                        juanzhu_listview.setAdapter(new ListView_JuZhu_Adapter(JuZhuJiLu_Activity.this, jsonArray,1));
                    }
                    acount_progress_bar_2.setVisibility(View.GONE);
                }
            });
    }

    @Override
    public void postViews(String s) {

    }

    @Override
    public void postViewss(String s) {

    }

    @Override
    public void postViewsss(String s) {

    }

    @Override
    public void postViewsss_1(String s) {

    }

    @Override
    public void postViewsss_2(String s) {

    }

    @Override
    public void fail(String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showToast(JuZhuJiLu_Activity.this,"网络异常，请稍后再试");
                acount_progress_bar_2.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void imgView(Bitmap bitmap) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.juanzhujilu__back:
                finish();
                break;
            case R.id.juanzhu_btn:
                Intent intent = new Intent(JuZhuJiLu_Activity.this,MapActivity.class);
                startActivity(intent);
                break;
        }
    }
}

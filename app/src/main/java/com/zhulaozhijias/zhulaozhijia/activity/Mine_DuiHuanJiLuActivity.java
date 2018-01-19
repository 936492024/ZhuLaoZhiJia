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
 * Created by asus on 2017/10/2.兑换记录
 */

public class Mine_DuiHuanJiLuActivity extends BaseActivity implements View.OnClickListener,MainView{
    private LinearLayout mine_aixinduihuan_back;
    private MainPresenter mainPresenter;
    private ListView duihuan_listview;
    private JSONArray jsonArray;
    private RelativeLayout duihuan_defaults;
    private Button duihuan_btn;
    private ProgressBar acount_progress_bar_3;
    @Override
    public void addLayout() {
        setContentView(R.layout.mine_duihuanjllu_activity);
    }

    @Override
    public void initView() {
        mainPresenter=new MainPresenter(this,this);
        acount_progress_bar_3= (ProgressBar) findViewById(R.id.acount_progress_bar_3);
        mine_aixinduihuan_back= (LinearLayout) findViewById(R.id.mine_aixinduihuan_back);
        mine_aixinduihuan_back.setOnClickListener(this);
        duihuan_listview= (ListView) findViewById(R.id.duihuan_listview);
        duihuan_defaults= (RelativeLayout) findViewById(R.id.duihuan_defaults);
        duihuan_btn= (Button) findViewById(R.id.duihuan_btn);
        duihuan_btn.setOnClickListener(this);
        acount_progress_bar_3.setVisibility(View.VISIBLE);
        Map<String ,String> map = new HashMap<>();
        map.put("member_id",BPApplication.getInstance().getMember_Id());
        map.put("secret", CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"z!l@z#j$"));
        mainPresenter.postMap(SystemConstant.GEREN_ZHONGXIN.Mine_EXCHANGE_RECORD,map);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_aixinduihuan_back:
                finish();
                break;
            case R.id.duihuan_btn:
                Intent intent = new Intent(Mine_DuiHuanJiLuActivity.this,Heart_ExchangeActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void getView(String s) {

    }

    @Override
    public void postView(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONArray jsonObject = JSONArray.fromObject(s);
                jsonArray = new JSONArray();
                if(jsonObject.size()!=0){
                    for(int i=0;i<jsonObject.size();i++){
                        jsonArray.add(jsonObject.getJSONObject(i));
                    }
                    duihuan_defaults.setVisibility(View.GONE);
                    duihuan_listview.setVisibility(View.VISIBLE);
                    duihuan_listview.setAdapter(new ListView_JuZhu_Adapter(Mine_DuiHuanJiLuActivity.this,jsonArray,2));
                }else {
                    duihuan_defaults.setVisibility(View.VISIBLE);
                    duihuan_listview.setVisibility(View.GONE);
                }
                acount_progress_bar_3.setVisibility(View.GONE);
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
                ToastUtil.showToast(Mine_DuiHuanJiLuActivity.this,"网络异常，请稍后再试");
                acount_progress_bar_3.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void imgView(Bitmap bitmap) {

    }
}

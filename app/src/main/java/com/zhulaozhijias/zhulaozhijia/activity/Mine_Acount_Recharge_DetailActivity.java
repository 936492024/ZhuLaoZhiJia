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
import com.zhulaozhijias.zhulaozhijia.adpter.ListView_JuanZeng_Adapter;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by asus on 2017/9/29.充值记录(捐赠记录)
 */

public class Mine_Acount_Recharge_DetailActivity extends BaseActivity implements View.OnClickListener,MainView{
    private MainPresenter mainPresenter;
    private LinearLayout juanzengjilu__back;
    private RelativeLayout juanzengjilu_defaults;
    private ListView juanzeng_listview;
    private JSONArray jsonArray;
    private Button juanzeng_btn;
    private ProgressBar acount_progress_bar_1;
    @Override
    public void addLayout() {
        setContentView(R.layout.mine_acount_recharge_detail_activity);
    }

    @Override
    public void initView() {
        mainPresenter=new MainPresenter(this,this);
        acount_progress_bar_1= (ProgressBar) findViewById(R.id.acount_progress_bar_1);
        acount_progress_bar_1.setVisibility(View.VISIBLE);
        Map<String ,String> map = new HashMap<>();
        map.put("member_id",BPApplication.getInstance().getMember_Id());
        map.put("secret", CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"z!l@z#j$"));
        mainPresenter.postMap(SystemConstant.GEREN_ZHONGXIN.Mine_RECHARGE_DETAIL,map);
        juanzengjilu__back= (LinearLayout) findViewById(R.id.juanzengjilu__back);
        juanzengjilu__back.setOnClickListener(this);
        juanzengjilu_defaults= (RelativeLayout) findViewById(R.id.juanzengjilu_defaults);
        juanzeng_listview= (ListView) findViewById(R.id.juanzeng_listview);
        juanzeng_btn= (Button) findViewById(R.id.juanzeng_btn);
        juanzeng_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.juanzengjilu__back:
                finish();
                break;
            case R.id.juanzeng_btn:
                Intent intent = new Intent(Mine_Acount_Recharge_DetailActivity.this,Mine_RechargeActiviy.class);
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
                    juanzengjilu_defaults.setVisibility(View.GONE);
                    juanzeng_listview.setVisibility(View.VISIBLE);
                }else {
                    juanzengjilu_defaults.setVisibility(View.VISIBLE);
                    juanzeng_listview.setVisibility(View.GONE);
                }
                acount_progress_bar_1.setVisibility(View.GONE);
                juanzeng_listview.setAdapter(new ListView_JuanZeng_Adapter(Mine_Acount_Recharge_DetailActivity.this,jsonArray));
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
                ToastUtil.showToast(Mine_Acount_Recharge_DetailActivity.this,"网络异常，请稍后再试");
                acount_progress_bar_1.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void imgView(Bitmap bitmap) {

    }
}

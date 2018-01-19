package com.zhulaozhijias.zhulaozhijia.activity;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.adpter.HuZhu_PingZhengAdapter;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by asus on 2017/9/29.
 */

public class HuZhu_PingZhengActivity extends BaseActivity implements View.OnClickListener,MainView{
    private LinearLayout huzhu_pingzheng_back;
    private ListView huzhu_pingzheng_listview;
    private MainPresenter mainPresenter;
    private RelativeLayout pingzheng_defaults;
    @Override
    public void addLayout() {
        setContentView(R.layout.huzhu_pingzheng_activity);
    }

    @Override
    public void initView() {
        mainPresenter=new MainPresenter(this,this);
        huzhu_pingzheng_back= (LinearLayout) findViewById(R.id.huzhu_pingzheng_back);
        huzhu_pingzheng_back.setOnClickListener(this);
        huzhu_pingzheng_listview= (ListView) findViewById(R.id.huzhu_pingzheng_listview);
        pingzheng_defaults= (RelativeLayout) findViewById(R.id.pingzheng_defaults);
        Map<String,String> map = new HashMap<>();
        String getMember_Id=BPApplication.getInstance().getMember_Id();
        map.put("member_id", getMember_Id);
        map.put("secret", CreateMD5.getMd5(getMember_Id+"z!l@z#j$"));
        mainPresenter.postMap(SystemConstant.GEREN_ZHONGXIN.Mine_PINGZHENG,map);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.huzhu_pingzheng_back:
                finish();
                break;


        }
    }

    @Override
    public void getView(String s) {

    }

    @Override
    public void postView(final String s) {
        Log.e("sss",s);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONArray jsonObject = JSONArray.fromObject(s);
                if (jsonObject.size() != 0) {
                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < jsonObject.size(); i++) {
                        jsonArray.add(jsonObject.getJSONObject(i));
                    }
                    huzhu_pingzheng_listview.setAdapter(new HuZhu_PingZhengAdapter(HuZhu_PingZhengActivity.this, jsonArray));
                    pingzheng_defaults.setVisibility(View.GONE);
                    huzhu_pingzheng_listview.setVisibility(View.VISIBLE);
                }else {
                    pingzheng_defaults.setVisibility(View.VISIBLE);
                    huzhu_pingzheng_listview.setVisibility(View.GONE);
                }
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

    }

    @Override
    public void imgView(Bitmap bitmap) {

    }
}

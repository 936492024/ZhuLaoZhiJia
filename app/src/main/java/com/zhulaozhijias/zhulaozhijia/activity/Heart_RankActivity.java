package com.zhulaozhijias.zhulaozhijia.activity;

import android.graphics.Bitmap;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.adpter.Rank_RecycleviewAdapter;
import com.zhulaozhijias.zhulaozhijia.adpter.Tab_Heart_Adapter;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.fragment.Rank_Tab1;
import com.zhulaozhijias.zhulaozhijia.fragment.Rank_Tab2;
import com.zhulaozhijias.zhulaozhijia.fragment.Rank_Tab3;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.LogLog;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;


import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by asus on 2017/9/14.
 */

public class Heart_RankActivity extends BaseActivity implements MainView ,View.OnClickListener{
    private TabLayout tabLayout;
    private String [] title ={"邀请总榜","捐助次数月榜","捐赠总额榜"};
    private ViewPager vp_pager;
    private ArrayList<Fragment> list;
    private Tab_Heart_Adapter adapter;
    private MainPresenter presenter;
    private LinearLayout heart_back;
    private ProgressBar heart_ranking_progress_bar;
    public static JSONArray arrayList_1 = new JSONArray();//分享榜
    public static JSONArray arrayList_2 = new JSONArray();//捐助榜
    public static JSONArray arrayList_3 = new JSONArray();//捐赠榜
    @Override
    public void addLayout() {
        setContentView(R.layout.heart_rankactivity);
    }

    @Override
    public void initView() {
        presenter=new MainPresenter(this,this);
        tabLayout= (TabLayout) findViewById(R.id.tabLayout);
        vp_pager= (ViewPager) findViewById(R.id.vp_pager);
        heart_ranking_progress_bar= (ProgressBar) findViewById(R.id.heart_ranking_progress_bar);
        heart_ranking_progress_bar.setVisibility(View.VISIBLE);
        tabLayout.setupWithViewPager(vp_pager);
        heart_back= (LinearLayout) findViewById(R.id.heart_back);
        heart_back.setOnClickListener(this);
        presenter.getRequest(SystemConstant.PublicConstant.Public_Ranking);
    }

    @Override
    public void getView(final String s) {
        Log.e("排行",s);
       runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONArray jsonArray = JSONArray.fromObject(s);
                        arrayList_1.clear();
                        arrayList_2.clear();
                        arrayList_3.clear();
                        for (int i = 0; i < jsonArray.size(); i++) {
                            String a = jsonArray.getJSONObject(i).getString("thing");
                            if (a.equals("分享榜")) {
                                arrayList_1.add(jsonArray.getJSONObject(i));
                            } else if (a.equals("捐赠人数")) {
                                arrayList_2.add(jsonArray.getJSONObject(i));
                            } else {
                                arrayList_3.add(jsonArray.getJSONObject(i));
                            }
                        }
                        list = new ArrayList<>();
                        list.add(new Rank_Tab1());
                        list.add(new Rank_Tab2());
                        list.add(new Rank_Tab3());
                        adapter = new Tab_Heart_Adapter(getSupportFragmentManager(), list, title);
                        vp_pager.setAdapter(adapter);
                        vp_pager.setOffscreenPageLimit(3);
                        heart_ranking_progress_bar.setVisibility(View.GONE);
                }
            });
    }

    @Override
    public void postView(String s) {

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
                  ToastUtil.showToast(Heart_RankActivity.this,"网络连接超时，请稍后再试");
                  heart_ranking_progress_bar.setVisibility(View.GONE);
              }
          });
    }

    @Override
    public void imgView(Bitmap bitmap) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.heart_back:
                finish();
                break;
        }
    }
}

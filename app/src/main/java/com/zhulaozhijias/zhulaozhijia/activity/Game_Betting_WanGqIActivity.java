package com.zhulaozhijias.zhulaozhijia.activity;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.adpter.Game_Adaper;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.PullBaseView;
import com.zhulaozhijias.zhulaozhijia.widgets.PullRecyclerView;

import net.sf.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by asus on 2017/10/16.
 */

public class Game_Betting_WanGqIActivity extends BaseActivity implements View.OnClickListener,MainView{
    private LinearLayout game_wangqi_back;
    private ListView game_betting_listview;
    private Game_Adaper game_adaper ;
    private ArrayList<String> arrayList = new ArrayList<>();
    private MainPresenter mainPresenter;
    private JSONArray jsonArray1;
    private ProgressBar game_betting_wangqi_progress_bar;
    @Override
    public void addLayout() {
        setContentView(R.layout.game_betting_wangqi_activity);
    }

    @Override
    public void initView() {
        mainPresenter=new MainPresenter(this,this);
        game_wangqi_back= (LinearLayout) findViewById(R.id.game_wangqi_back);
        game_wangqi_back.setOnClickListener(this);
        game_betting_listview= (ListView) findViewById(R.id.game_betting_recyclerview);
        game_betting_wangqi_progress_bar= (ProgressBar) findViewById(R.id.game_betting_wangqi_progress_bar);
        game_betting_wangqi_progress_bar.setVisibility(View.VISIBLE);
        Map<String ,String> map = new HashMap<>();
        map.put("member_id",BPApplication.getInstance().getMember_Id());
        map.put("secret", CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"z!l@z#j$"));
        mainPresenter.postMap(SystemConstant.GAME.Game_HISTORY_TOPIC,map);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.game_wangqi_back:
                finish();
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
                JSONArray jsonArray = JSONArray.fromObject(s);
                jsonArray1 = new JSONArray();
                for (int i=0;i<jsonArray.size();i++){
                    jsonArray1.add(jsonArray.getJSONObject(i));
                }
                game_betting_wangqi_progress_bar.setVisibility(View.GONE);
                game_adaper=new Game_Adaper(Game_Betting_WanGqIActivity.this,jsonArray1);
                game_betting_listview.setAdapter(game_adaper);
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

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
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by asus on 2017/10/2.
 */

public class Mine_Game_RecordActivity extends BaseActivity implements View.OnClickListener ,MainView{
    private LinearLayout mine_game_back;
    private Button game_btn;
    private RelativeLayout geme_record_defaults;
    private ListView geme_record_listview;
    private MainPresenter mainPresenter;
    private JSONArray jsonarray2;
    private ProgressBar acount_progress_bar_4;
    @Override
    public void addLayout() {
        setContentView(R.layout.mine_game_record_activity);
    }

    @Override
    public void initView() {
        mainPresenter=new MainPresenter(this,this);
        acount_progress_bar_4= (ProgressBar) findViewById(R.id.acount_progress_bar_4);
        mine_game_back= (LinearLayout) findViewById(R.id.mine_game_back);
        mine_game_back.setOnClickListener(this);
        geme_record_defaults= (RelativeLayout) findViewById(R.id.geme_record_defaults);
        game_btn= (Button) findViewById(R.id.game_btn);
        game_btn.setOnClickListener(this);
        geme_record_listview= (ListView) findViewById(R.id.geme_record_listview);
        acount_progress_bar_4.setVisibility(View.VISIBLE);
        Map<String ,String> map = new HashMap<>();
        map.put("member_id", BPApplication.getInstance().getMember_Id());
        map.put("secret", CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"z!l@z#j$"));
        mainPresenter.postMap(SystemConstant.GAME.Game_AbOUT_SEL,map);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_game_back:
                finish();
                break;
            case R.id.game_btn:
                Intent intent = new Intent(Mine_Game_RecordActivity.this,Invite_FriendActivity.class);
                intent.putExtra("Mine_Game_RecordActivity","Mine_Game_RecordActivity");
                finish();
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
                JSONObject json = JSONObject.fromObject(s);
                jsonarray2=new JSONArray();
                if(json.getString("success").equals("true")){
                    JSONArray jsonarray= JSONArray.fromObject(json.getString("data"));
                    if(jsonarray.size()!=0){
                        geme_record_listview.setVisibility(View.VISIBLE);
                        geme_record_defaults.setVisibility(View.GONE);
                    for(int i=0;i<jsonarray.size();i++){
                        jsonarray2.add(jsonarray.getJSONObject(i));
                    }
                        geme_record_listview.setAdapter(new ListView_JuZhu_Adapter(Mine_Game_RecordActivity.this,jsonarray2,3));
                  }
                }else {
                    geme_record_listview.setVisibility(View.GONE);
                    geme_record_defaults.setVisibility(View.VISIBLE);
                }
                acount_progress_bar_4.setVisibility(View.GONE);
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
                ToastUtil.showToast(Mine_Game_RecordActivity.this,"网络异常，请稍后再试");
                acount_progress_bar_4.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void imgView(Bitmap bitmap) {

    }
}

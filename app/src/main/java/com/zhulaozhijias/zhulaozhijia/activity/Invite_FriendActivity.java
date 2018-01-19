package com.zhulaozhijias.zhulaozhijia.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;

import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;
import net.sf.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


/**
 * Created by Administrator on 2017/9/4.
 */

public class Invite_FriendActivity extends BaseActivity implements View.OnClickListener,MainView {
    private ToastUtil toastUtil;
    private ArrayList <String> arrayList = new ArrayList<>();
    private RelativeLayout anctivity_clock_rt1,anctivity_betting_rt2;
    private Intent intent;
    private MainPresenter mainPresenter;
    private TextView early_total,topic_total;
    private ImageView activity_running,activity_succeed,activity_betting_running,activity_betting_succeed;
    private Button clock_btn,topic_btn,activity_sign_btn,activity_share_btn,activity_recharge_btn,activity_donate_btn,activity_plan_btn;
    private boolean isGetData = false;
    private ImageView activity_signing,activity_signed,activity_shareing,activity_shared,activity_rechargeing,activity_recharged,
            activity_donateing,activity_donated,activity_planing,activity_planed;
    private TextView jihuacishu;
    private boolean hadIntercept;
    private LinearLayout lin_1,lin_2,lin_3,lin_4,lin_5,lin_6,lin_7,mine_activity_back;
    private TextView plan_heart;


    @Override
    public void addLayout() {
         setContentView(R.layout.invite_friend_acivity);
    }

    @Override
    public void initView() {
        initviews();
        Map<String ,String > map = new HashMap<>();
        map.put("member_id",BPApplication.getInstance().getMember_Id());
        map.put("secret", CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"z!l@z#j$"));
        mainPresenter.postMap(SystemConstant.GAME.Game_ACTIVITY,map);
    }
    
//    @Override
//    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
//        //   进入当前Fragment
//            if (enter && !isGetData) {
//                isGetData = true;
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if(!checkIsLogined_2()){
//                            return;
//                        }else {
//
//                        }
//                    }
//                });
//            }
//            else {
//                isGetData = false;
//            }
//
//        return super.onCreateAnimation(transit, enter, nextAnim);
//    }

    public void initviews(){
        mainPresenter=new MainPresenter(Invite_FriendActivity.this,this);
        anctivity_clock_rt1= (RelativeLayout) findViewById(R.id.anctivity_clock_rt1);
        anctivity_clock_rt1.setOnClickListener(this);
        anctivity_clock_rt1.requestFocus();
        early_total= (TextView) findViewById(R.id.early_total);
        anctivity_betting_rt2= (RelativeLayout) findViewById(R.id.anctivity_betting_rt2);
        anctivity_betting_rt2.setOnClickListener(this);
        activity_running= (ImageView) findViewById(R.id.activity_running);
        activity_succeed= (ImageView) findViewById(R.id.activity_succeed);
        clock_btn= (Button) findViewById(R.id.clock_btn);
        activity_betting_running= (ImageView) findViewById(R.id.activity_betting_running);
        activity_betting_succeed= (ImageView) findViewById(R.id.activity_betting_succeed);
        topic_btn= (Button) findViewById(R.id.topic_btn);
        topic_btn.setOnClickListener(this);
        topic_total= (TextView) findViewById(R.id.topic_total);
        activity_sign_btn= (Button) findViewById(R.id.activity_sign_btn);
        activity_sign_btn.setOnClickListener(this);
        activity_signing= (ImageView) findViewById(R.id.activity_signing);
        activity_signed= (ImageView) findViewById(R.id.activity_signed);
        activity_share_btn= (Button) findViewById(R.id.activity_share_btn);
        activity_share_btn.setOnClickListener(this);
        activity_shareing= (ImageView) findViewById(R.id.activity_shareing);
        activity_shared= (ImageView) findViewById(R.id.activity_shared);
        activity_recharge_btn= (Button) findViewById(R.id.activity_recharge_btn);
        activity_recharge_btn.setOnClickListener(this);
        activity_rechargeing= (ImageView) findViewById(R.id.activity_rechargeing);
        activity_recharged= (ImageView) findViewById(R.id.activity_recharged);
        activity_donate_btn= (Button) findViewById(R.id.activity_donate_btn);
        activity_donate_btn.setOnClickListener(this);
        activity_donateing= (ImageView)findViewById(R.id.activity_donateing);
        activity_donated= (ImageView)findViewById(R.id.activity_donated);
        activity_plan_btn= (Button) findViewById(R.id.activity_plan_btn);
        activity_plan_btn.setOnClickListener(this);
        activity_planing= (ImageView) findViewById(R.id.activity_planing);
        activity_planed= (ImageView) findViewById(R.id.activity_planed);
        jihuacishu= (TextView) findViewById(R.id.jihuacishu);
        plan_heart= (TextView) findViewById(R.id.plan_heart);
        lin_1= (LinearLayout) findViewById(R.id.lin_1);
        lin_2= (LinearLayout) findViewById(R.id.lin_2);
        lin_3= (LinearLayout) findViewById(R.id.lin_3);
        lin_4= (LinearLayout) findViewById(R.id.lin_4);
        lin_5= (LinearLayout) findViewById(R.id.lin_5);
        lin_6= (LinearLayout) findViewById(R.id.lin_6);
        lin_7= (LinearLayout) findViewById(R.id.lin_7);
        mine_activity_back= (LinearLayout) findViewById(R.id.mine_activity_back);
        mine_activity_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.anctivity_clock_rt1:
                if(!checkIsLogined()){
                    return;
                }else {
                    intent=new Intent(Invite_FriendActivity.this, Game_ClockActivity.class);
                    startActivity(intent);
                }
//                Toast.makeText(getContext(),"敬请期待",Toast.LENGTH_SHORT).show();
                break;
            case R.id.anctivity_betting_rt2:
//                Toast.makeText(getContext(),"敬请期待",Toast.LENGTH_SHORT).show();
                if(!checkIsLogined()){
                    return;
                }else {
                    intent=new Intent(Invite_FriendActivity.this, Game_BettingActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mine_activity_back:
                finish();
                break;

        }
    }

    private boolean checkIsLogined_2() {
        if (!BPApplication.getInstance().isLogined()) {

            return false;
        }
        return true;
    }

    private boolean checkIsLogined() {
        if (!BPApplication.getInstance().isLogined()) {
            Intent intent = new Intent(Invite_FriendActivity.this, LoginActivity.class);
            startActivity(intent);
            return false;
        }
        return true;
    }

    @Override
    public void getView(String s) {

    }

    @Override
    public void postView(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final JSONObject jsonObject1 =JSONObject.fromObject(s);
                if(jsonObject1.getString("success").equals("true")){
                    final JSONObject jsonObject =JSONObject.fromObject(jsonObject1.getString("data"));
                    early_total.setText(jsonObject.getString("early_total"));
                    topic_total.setText(jsonObject.getString("topic_total"));
                    if(jsonObject.getString("early_status").equals("0")){
                        clock_btn.setVisibility(GONE);
                        activity_running.setVisibility(VISIBLE);
                        activity_succeed.setVisibility(GONE);
                        lin_3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                intent = new Intent(Invite_FriendActivity.this, Game_ClockActivity.class);
                                startActivity(intent);
                            }
                        });
                    }else if(jsonObject.getString("early_status").equals("1")){
                        clock_btn.setVisibility(VISIBLE);//待领取
                        activity_running.setVisibility(GONE);//待完成
                        activity_succeed.setVisibility(GONE);//领取成功
                        clock_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Map<String ,String >map = new HashMap<String, String>();
                                map.put("member_id",BPApplication.getInstance().getMember_Id());
                                map.put("early_status",jsonObject.getString("early_status"));
                                map.put("secret",CreateMD5.getMd5(jsonObject.getString("early_status")+BPApplication.getInstance().getMember_Id()+"z!l@z#j$"));
                                mainPresenter.wodes(SystemConstant.GAME.Game_REWARD,map);
                            }
                        });
                    }else if(jsonObject.getString("early_status").equals("2")){
                        clock_btn.setVisibility(GONE);
                        activity_running.setVisibility(GONE);
                        activity_succeed.setVisibility(VISIBLE);
                    }
                    if(jsonObject.getString("topic_status").equals("0")){
                        topic_btn.setVisibility(GONE);
                        activity_betting_running.setVisibility(VISIBLE);
                        activity_betting_succeed.setVisibility(GONE);
                        lin_4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                intent = new Intent(Invite_FriendActivity.this, Game_BettingActivity.class);
                                startActivity(intent);
                            }
                        });
                    }else if(jsonObject.getString("topic_status").equals("1")){
                        topic_btn.setVisibility(VISIBLE);//领取领取
                        activity_betting_running.setVisibility(GONE);//待完成
                        activity_betting_succeed.setVisibility(GONE);//领取成功
                        topic_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Map<String ,String >map = new HashMap<String, String>();
                                map.put("member_id",BPApplication.getInstance().getMember_Id());
                                map.put("topic_status",jsonObject.getString("topic_status"));
                                map.put("secret",CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+jsonObject.getString("topic_status")+"z!l@z#j$"));
                                mainPresenter.wodes(SystemConstant.GAME.Game_REWARD_YABAO,map);
                            }
                        });
                    }else if(jsonObject.getString("topic_status").equals("2")){
                        topic_btn.setVisibility(GONE);
                        activity_betting_running.setVisibility(GONE);
                        activity_betting_succeed.setVisibility(VISIBLE);
                    }

                    if(jsonObject.getString("qiandao").equals("0")){//签到
                        activity_sign_btn.setVisibility(GONE);
                        activity_signing.setVisibility(VISIBLE);
                        activity_signed.setVisibility(GONE);
                        lin_1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                intent = new Intent(Invite_FriendActivity.this, MainActivity.class);
                                intent.putExtra("lin_1","lin_1");
                                BPApplication.getInstance().setTitle("0");
                                startActivity(intent);
                            }
                        });
                    }else if(jsonObject.getString("qiandao").equals("1")){
                        activity_sign_btn.setVisibility(VISIBLE);//待领取
                        activity_signing.setVisibility(GONE);//待完成
                        activity_signed.setVisibility(GONE);//领取成功
                        activity_sign_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Map<String ,String >map = new HashMap<String, String>();
                                map.put("member_id",BPApplication.getInstance().getMember_Id());
                                map.put("secret",CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"z!l@z#j$"));
                                mainPresenter.wodes(SystemConstant.ACTIVITY_REWARD.ACTIVITY_REWARD_QIANDAO,map);
                            }
                        });
                    }else if(jsonObject.getString("qiandao").equals("2")){
                        activity_sign_btn.setVisibility(GONE);
                        activity_signing.setVisibility(GONE);
                        activity_signed.setVisibility(VISIBLE);
                    }
                    //分享
                    if(jsonObject.getString("fenxiang").equals("0")){
                        activity_share_btn.setVisibility(GONE);
                        activity_shareing.setVisibility(VISIBLE);
                        activity_shared.setVisibility(GONE);
                        lin_2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                intent = new Intent(Invite_FriendActivity.this, MainActivity.class);
                                intent.putExtra("Invite_FriendActivity","Invite_FriendActivity");
                                BPApplication.getInstance().setTitle("0");
                                startActivity(intent);
                            }
                        });
                    }else if(jsonObject.getString("fenxiang").equals("1")){
                        activity_share_btn.setVisibility(VISIBLE);//待领取
                        activity_shareing.setVisibility(GONE);//待完成
                        activity_shared.setVisibility(GONE);//领取成功
                        activity_share_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Map<String ,String >map = new HashMap<String, String>();
                                map.put("member_id",BPApplication.getInstance().getMember_Id());
                                map.put("secret",CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"z!l@z#j$"));
                                mainPresenter.wodes(SystemConstant.ACTIVITY_REWARD.ACTIVITY_REWARD_FENXIANG,map);
                            }
                        });
                    }else if(jsonObject.getString("fenxiang").equals("2")){
                        activity_share_btn.setVisibility(GONE);
                        activity_shareing.setVisibility(GONE);
                        activity_shared.setVisibility(VISIBLE);
                    }

                    //充值
                    if(jsonObject.getString("chongzhi").equals("0")){
                        activity_recharge_btn.setVisibility(GONE);
                        activity_rechargeing.setVisibility(VISIBLE);
                        activity_recharged.setVisibility(GONE);
                        lin_5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                intent = new Intent(Invite_FriendActivity.this, Mine_RechargeActiviy.class);
                                startActivity(intent);
                            }
                        });
                    }else if(jsonObject.getString("chongzhi").equals("1")){
                        activity_recharge_btn.setVisibility(VISIBLE);//待领取
                        activity_rechargeing.setVisibility(GONE);//待完成
                        activity_recharged.setVisibility(GONE);//领取成功
                        activity_recharge_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Map<String ,String >map = new HashMap<String, String>();
                                map.put("member_id",BPApplication.getInstance().getMember_Id());
                                map.put("secret",CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"z!l@z#j$"));
                                mainPresenter.wodes(SystemConstant.ACTIVITY_REWARD.ACTIVITY_REWARD_CHONGZHI,map);
                            }
                        });
                    }else if(jsonObject.getString("chongzhi").equals("2")){
                        activity_recharge_btn.setVisibility(GONE);
                        activity_rechargeing.setVisibility(GONE);
                        activity_recharged.setVisibility(VISIBLE);
                    }
                    if(jsonObject.getString("juanzhu").equals("0")){
                        activity_donate_btn.setVisibility(GONE);
                        activity_donateing.setVisibility(VISIBLE);
                        activity_donated.setVisibility(GONE);
                        lin_6.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                intent = new Intent(Invite_FriendActivity.this, MapActivity.class);
                                startActivity(intent);
                            }
                        });
                    }else if(jsonObject.getString("juanzhu").equals("1")){
                        activity_donate_btn.setVisibility(VISIBLE);//待领取
                        activity_donateing.setVisibility(GONE);//待完成
                        activity_donated.setVisibility(GONE);//领取成功
                        activity_donate_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Map<String ,String >map = new HashMap<String, String>();
                                map.put("member_id",BPApplication.getInstance().getMember_Id());
                                map.put("secret",CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"z!l@z#j$"));
                                mainPresenter.wodes(SystemConstant.ACTIVITY_REWARD.ACTIVITY_REWARD_JUANZHU,map);
                            }
                        });
                    }else if(jsonObject.getString("juanzhu").equals("2")){
                        activity_donate_btn.setVisibility(GONE);
                        activity_donateing.setVisibility(GONE);
                        activity_donated.setVisibility(VISIBLE);
                    }
                    Log.e("dsadasd",s);
                    if((Integer.valueOf(jsonObject.getString("jihuacishu")))>=2){
                        jihuacishu.setText("计划("+jsonObject.getString("jihuacishu")+")");
                        plan_heart.setText((1000*Integer.valueOf(jsonObject.getString("jihuacishu")))+"");
                    }else {
                        jihuacishu.setText("计划");
                        plan_heart.setText("1000");
                    }
                    if(jsonObject.getString("jihua").equals("0")){
                        activity_plan_btn.setVisibility(GONE);
                        activity_planing.setVisibility(VISIBLE);
                        activity_planed.setVisibility(GONE);
                        lin_7.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                intent = new Intent(Invite_FriendActivity.this, HuZhu_PlanActivity.class);
                                startActivity(intent);
                            }
                        });
                    }else if(jsonObject.getString("jihua").equals("1")){
                        activity_plan_btn.setVisibility(VISIBLE);//待领取
                        activity_planing.setVisibility(GONE);//待完成
                        activity_planed.setVisibility(GONE);//领取成功
                        activity_plan_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Map<String ,String >map = new HashMap<String, String>();
                                map.put("member_id",BPApplication.getInstance().getMember_Id());
                                map.put("secret",CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"z!l@z#j$"));
                                mainPresenter.wodes(SystemConstant.ACTIVITY_REWARD.ACTIVITY_REWARD_JIHUA,map);
                            }
                        });
                    }else if(jsonObject.getString("jihua").equals("2")){
                        activity_plan_btn.setVisibility(GONE);
                        activity_planing.setVisibility(GONE);
                        activity_planed.setVisibility(VISIBLE);
                    }

                }else {
                    toastUtil.showToast(Invite_FriendActivity.this,jsonObject1.getString("msg"));
                }
            }
        });
    }

    @Override
    public void postViews(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject =JSONObject.fromObject(s);
                if( jsonObject.getString("success").equals("true")) {
                    if(jsonObject.getString("number").equals("5")){
                        activity_recharge_btn.setVisibility(GONE);//待领取
                        activity_rechargeing.setVisibility(GONE);//待完成
                        activity_recharged.setVisibility(VISIBLE);//领取成功
                    }else if(jsonObject.getString("number").equals("1")){
                        activity_sign_btn.setVisibility(GONE);//待领取
                        activity_signing.setVisibility(GONE);//待完成
                        activity_signed.setVisibility(VISIBLE);//领取成功
                    }else if(jsonObject.getString("number").equals("2")){
                        activity_share_btn.setVisibility(GONE);
                        activity_shareing.setVisibility(GONE);
                        activity_shared.setVisibility(VISIBLE);
                    }else if(jsonObject.getString("number").equals("3")){
                        clock_btn.setVisibility(GONE);//待领取
                        activity_running.setVisibility(GONE);//待完成
                        activity_succeed.setVisibility(VISIBLE);//领取成功
                    }else if(jsonObject.getString("number").equals("4")){
                        topic_btn.setVisibility(GONE);//领取领取
                        activity_betting_running.setVisibility(GONE);//待完成
                        activity_betting_succeed.setVisibility(VISIBLE);//领取成功
                    }else if(jsonObject.getString("number").equals("6")){
                        activity_donate_btn.setVisibility(GONE);
                        activity_donateing.setVisibility(GONE);
                        activity_donated.setVisibility(VISIBLE);
                    }else if(jsonObject.getString("number").equals("7")){
                        activity_plan_btn.setVisibility(GONE);
                        activity_planing.setVisibility(GONE);
                        activity_planed.setVisibility(VISIBLE);
                        jihuacishu.setText("计划");
                        plan_heart.setText("1000");
                    }
//                    EventBus.getDefault().post(new MainSendEvent("clock_num"));
//                    EventBus.getDefault().post(new MainSendEvent("topic_status"));
                    toastUtil.showToast(Invite_FriendActivity.this,jsonObject.getString("msg"));
                }else {
                    toastUtil.showToast(Invite_FriendActivity.this,jsonObject.getString("msg"));
                }
            }
        });
    }

    @Override
    public void postViewss(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject =JSONObject.fromObject(s);
            }
        });
    }

//    @Override
//    public boolean onBackPressed() {
//        if(hadIntercept){//点两次
//            BPApplication.getInstance().setLogined(false);
//            return false;
//        }else{
//            Toast.makeText(getActivity(), "再点一次退出程序", Toast.LENGTH_SHORT).show();
//            hadIntercept = true;
//            return true;
//        }
//    }


    @Override
    public void postViewsss(String s) {
        Log.e("dasdas",s);
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
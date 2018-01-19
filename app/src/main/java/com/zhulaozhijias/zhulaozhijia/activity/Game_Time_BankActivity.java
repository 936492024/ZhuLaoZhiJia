package com.zhulaozhijias.zhulaozhijia.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.GlideCircleTransform;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;
import com.zhulaozhijias.zhulaozhijia.widgets.eventbus.MainSendEvent;
import com.zhulaozhijias.zhulaozhijia.widgets.numberscroll.NumberScrollTextView;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import de.greenrobot.event.EventBus;

/**
 * Created by asus on 2017/10/5.
 */

public class Game_Time_BankActivity extends BaseActivity implements View.OnClickListener,MainView{
    private LinearLayout mine_wake_back;
    private TextView number_scroll;
    private MainPresenter mainPresenter;
    private SweetAlertDialog pDialog;
    private TextView this_time,is_receive_time,bean,is_receive_bean,state_text;
    private ImageView game_bank_heading;
    private RelativeLayout game_bank_donate_1,game_bank_donate_2;
    private Intent intent;
    private String name;
    private RelativeLayout rt_1,rt_2,rt_3;
    private int flag=0;

    @Override
    public void addLayout() {
        setContentView(R.layout.game_wake_activity);
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        mainPresenter=new MainPresenter(this,this);
        mine_wake_back= (LinearLayout) findViewById(R.id.mine_wake_back);
        mine_wake_back.setOnClickListener(this);
        number_scroll= (TextView) findViewById(R.id.number_scroll);
        this_time= (TextView) findViewById(R.id.this_time);
        is_receive_time= (TextView) findViewById(R.id.is_receive_time);
        is_receive_time.setOnClickListener(this);
        bean= (TextView) findViewById(R.id.bean);
        is_receive_bean= (TextView) findViewById(R.id.is_receive_bean);
        is_receive_bean.setOnClickListener(this);
        game_bank_donate_1= (RelativeLayout) findViewById(R.id.game_bank_donate_1);
        game_bank_donate_1.setOnClickListener(this);
        game_bank_donate_2= (RelativeLayout) findViewById(R.id.game_bank_donate_2);
        game_bank_donate_2.setOnClickListener(this);
        rt_1= (RelativeLayout) findViewById(R.id.rt_1);
        rt_1.setOnClickListener(this);
        rt_2= (RelativeLayout) findViewById(R.id.rt_2);
        rt_2.setOnClickListener(this);
        rt_3= (RelativeLayout) findViewById(R.id.rt_3);
        rt_3.setOnClickListener(this);
        state_text= (TextView) findViewById(R.id.state_text);
        game_bank_heading= (ImageView) findViewById(R.id.game_bank_heading);
        Glide.with(Game_Time_BankActivity.this).load(BPApplication.getInstance().getHeadimgurl()).placeholder(R.mipmap.portrait)
                .error(R.mipmap.portrait) .transform(new GlideCircleTransform(Game_Time_BankActivity.this)).into(game_bank_heading);
        name = BPApplication.getInstance().getName();
        pDialog  = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("加载中...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(true);
        pDialog.show();
        time();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_wake_back:
                finish();
                break;
            case R.id.is_receive_time://健康时间可领取状态
                is_receive_time.setBackgroundResource(R.drawable.grey_round_border_2);
                is_receive_time.setEnabled(false);
                Map<String,String> map_1 = new HashMap<>();
                map_1.put("member_id", BPApplication.getInstance().getMember_Id());
                map_1.put("secret", CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+SystemConstant.PublicConstant.Public_SECRET));
                mainPresenter.wodes(SystemConstant.GAME.Game_BANK_RECEIVE_TIME,map_1);
                break;
            case R.id.is_receive_bean://健康豆子可领取状态
                is_receive_bean.setBackgroundResource(R.drawable.grey_round_border_2);
                is_receive_bean.setEnabled(false);
                Map<String,String> map_2 = new HashMap<>();
                map_2.put("member_id", BPApplication.getInstance().getMember_Id());
                map_2.put("secret", CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+SystemConstant.PublicConstant.Public_SECRET));
                mainPresenter.wodess(SystemConstant.GAME.Game_BANK_RECEIVE_BEAN,map_2);
                break;
            case R.id.game_bank_donate_1:
                intent=new Intent(Game_Time_BankActivity.this,FoundationActivity.class);
                startActivity(intent);
                break;
            case R.id.game_bank_donate_2:
                intent=new Intent(Game_Time_BankActivity.this,Mine_RechargeActiviy.class);
                startActivity(intent);
                break;
            case R.id.rt_1://邀请
                intent =new Intent(Game_Time_BankActivity.this,MainActivity.class);
                intent.putExtra("Game_BANK_RECEIVE_BEAN","Game_BANK_RECEIVE_BEAN");
                BPApplication.getInstance().setTitle("0");
                startActivity(intent);
//                showShare();
                break;
            case R.id.rt_2://抢健康豆
                ToastUtil.showToast(Game_Time_BankActivity.this,"版本更新中");
                break;
            case R.id.rt_3://健康服务
                ToastUtil.showToast(Game_Time_BankActivity.this,"版本更新中");
                break;
        }
    }

    @Override
    public void getView(String s) {

    }

    @Override
    public void postView(final String s) {
        Log.e("dsadadsa",s);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = JSONObject.fromObject(s);
                if( jsonObject.getString("success").equals("true")){
//                    number_scroll.setFromAndEndNumber(0, Integer.valueOf(jsonObject.getString("total_time")));
//                    number_scroll.setDuration(2000);
//                    number_scroll.start();
                    number_scroll.setText(jsonObject.getString("total_time"));
                    JSONObject jsonObject1 = JSONObject.fromObject(jsonObject.getString("time"));
                    this_time.setText(jsonObject1.getString("this_time"));
                    if(jsonObject1.getString("is_receive_time").equals("0")){//健康服务时间领取状态
                        state_text.setText("恭喜您~本次捐赠获得下列奖励");
                        is_receive_time.setBackgroundResource(R.drawable.red_round_border);
                        is_receive_time.setEnabled(true);
                    }else {//不可领取
                        is_receive_time.setBackgroundResource(R.drawable.grey_round_border_2);
                        is_receive_time.setEnabled(false);
                    }
                    bean.setText(jsonObject1.getString("bean"));
                    if(jsonObject1.getString("is_receive_bean").equals("0")){//健康都可领取状态
                        state_text.setText("恭喜您~本次捐赠获得下列奖励");
                        is_receive_bean.setBackgroundResource(R.drawable.red_round_border);
                        is_receive_bean.setEnabled(true);
                    }else {//不可领取
                        is_receive_bean.setBackgroundResource(R.drawable.grey_round_border_2);
                        is_receive_bean.setEnabled(false);
                    }
                    if(jsonObject1.getString("this_time").equals("0")){
                        is_receive_time.setBackgroundResource(R.drawable.grey_round_border_2);
                        is_receive_time.setEnabled(false);
                    }
                    if(jsonObject1.getString("bean").equals("0")){
                        is_receive_bean.setBackgroundResource(R.drawable.grey_round_border_2);
                        is_receive_bean.setEnabled(false);
                    }
                    pDialog.dismiss();
                }else {

                }
            }
        });
    }

    @Override
    public void postViews(final String s) {
        Log.e("dsadadsa",s);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
             JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")) {
                    time();
                    ToastUtil.showToast(Game_Time_BankActivity.this,jsonObject.getString("msg"));
                }
            }
        });
    }

    @Override
    public void postViewss(final String s) {
          Log.e("dsadadsa",s);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")) {
                    time();
                    ToastUtil.showToast(Game_Time_BankActivity.this,jsonObject.getString("msg"));
                }
            }
        });
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
                pDialog.dismiss();
                ToastUtil.showToast(Game_Time_BankActivity.this,"网络连接超时，请稍后再试");
            }
        });
    }

    @Override
    public void imgView(Bitmap bitmap) {

    }

    //事件接受
    public void onEventMainThread(MainSendEvent event){
        if(event != null){
            if(event.getStringMsgData().equals("充值支付2")){
               time();
            }
        }
    }
    private void time(){
        Map<String,String> map = new HashMap<>();
        map.put("member_id", BPApplication.getInstance().getMember_Id());
        map.put("secret", CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+SystemConstant.PublicConstant.Public_SECRET));
        mainPresenter.postMap(SystemConstant.GAME.Game_BANK_INDEX,map);
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if("ShortMessage".equals(platform.getName())){
                    paramsToShare.setText("爱心是一盏灯，照亮别人，温暖自己http://app.sesdf.org/");
                }else if("Wechat".equals(platform.getName())){
                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                    paramsToShare.setTitle(name+"邀请你加入互帮互助基金");
                    paramsToShare.setText("爱心是一盏灯，照亮别人，温暖自己");
                    paramsToShare.setUrl("http://app.sesdf.org/");
                    paramsToShare.setImageUrl("http://oxycohppa.bkt.clouddn.com/logo.jpg");
                }else if("WechatMoments".equals(platform.getName())){
                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                    paramsToShare.setTitle(name+"邀请你加入互帮互助基金");
                    paramsToShare.setUrl("http://app.sesdf.org/");
                    paramsToShare.setImageUrl("http://oxycohppa.bkt.clouddn.com/logo.jpg");
                }else if("SinaWeibo".equals(platform.getName())){
                    paramsToShare.setText("http://app.sesdf.org/");
                    paramsToShare.setImageUrl("http://oxycohppa.bkt.clouddn.com/logo.jpg");
                }else if("QQ".equals(platform.getName())){
                    paramsToShare.setText("爱心是一盏灯，照亮别人，温暖自己");
                    paramsToShare.setTitle(name+"邀请你加入互帮互助基金");
                    paramsToShare.setTitleUrl("http://app.sesdf.org/");
                    paramsToShare.setImageUrl("http://oxycohppa.bkt.clouddn.com/logo.jpg");
                }else if("QZone".equals(platform.getName())){
                    paramsToShare.setTitle(name+"邀请你加入互帮互助基金");
                    paramsToShare.setText("爱心是一盏灯，照亮别人，温暖自己");
                    paramsToShare.setTitleUrl("http://app.sesdf.org/");
                    paramsToShare.setImageUrl("http://oxycohppa.bkt.clouddn.com/logo.jpg");
                }
            }
        });
        oks.show(this);
    }
}

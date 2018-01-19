package com.zhulaozhijias.zhulaozhijia.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
import com.zhulaozhijias.zhulaozhijia.widgets.Ammount_view;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.GlideCircleTransform;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;

import net.sf.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by asus on 2017/10/5.
 */

public class Game_BettingActivity extends BaseActivity  implements View.OnClickListener,MainView{
    private LinearLayout mine_betting_back;
    private LayoutInflater inflater;
    private Dialog dialog;
    private RelativeLayout wangqi_rt;
    private Intent intent;
    private TextView game_betting_red,game_betting_blue;
    private Ammount_view mAmountView;
    private MainPresenter mainPresenter;
    private String topic_time;
    private int red_bet_number=1,blue_bet_number=1;
    private ImageView betting_img;
    private TextView red_title,red_content,blue_title,blue_content,title,is_join;
    private TextView title_2,red_number,blue_number,winner,winner_name,my_team_my_code_my_win_code,time;
    private RelativeLayout rt_1,rt_2,game_guize_1,rt_count_down;
    private ToastUtil toastUtil;
    private String blue_Love_code;
    private String red_Love_code;
    private  long  times;
    private TextView count_down,count_down_2;
    private LinearLayout linearLayout6;
    private SweetAlertDialog pDialog;
    private int flag_1=0,flag_2=0;

    @Override
    public void addLayout() {
        setContentView(R.layout.game_betting_activity);
    }

    @Override
    public void initView() {
        mainPresenter=new MainPresenter(this,this);
        mine_betting_back= (LinearLayout) findViewById(R.id.mine_betting_back);
        mine_betting_back.setOnClickListener(this);
        game_guize_1= (RelativeLayout) findViewById(R.id.game_guize_1);
        game_guize_1.setOnClickListener(this);
        wangqi_rt= (RelativeLayout) findViewById(R.id.wangqi_rt);
        wangqi_rt.setOnClickListener(this);
        game_betting_red= (TextView) findViewById(R.id.game_betting_red);
        game_betting_red.setOnClickListener(this);
        game_betting_blue= (TextView) findViewById(R.id.game_betting_blue);
        game_betting_blue.setOnClickListener(this);
        red_title= (TextView) findViewById(R.id.red_title);
        red_content= (TextView) findViewById(R.id.red_content);
        blue_title= (TextView) findViewById(R.id.blue_title);
        blue_content= (TextView) findViewById(R.id.blue_content);
        betting_img=(ImageView)findViewById(R.id.betting_img);
        title= (TextView) findViewById(R.id.title);
        title_2= (TextView) findViewById(R.id.title_2);
        red_number= (TextView) findViewById(R.id.red_number);
        blue_number= (TextView) findViewById(R.id.blue_number);
        rt_1= (RelativeLayout) findViewById(R.id.rt_1);
        rt_2= (RelativeLayout) findViewById(R.id.rt_2);
        winner= (TextView) findViewById(R.id.winner);
        winner_name= (TextView) findViewById(R.id.winner_name);
        my_team_my_code_my_win_code= (TextView) findViewById(R.id.my_team_my_code_my_win_code);
        time= (TextView) findViewById(R.id.time);
        is_join= (TextView) findViewById(R.id.is_join);
        count_down=(TextView) findViewById(R.id.count_down);
        rt_count_down= (RelativeLayout) findViewById(R.id.rt_count_down);
        linearLayout6= (LinearLayout) findViewById(R.id.linearLayout6);
        count_down_2= (TextView) findViewById(R.id.count_down_2);

        pDialog  = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("加载中...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(true);
        pDialog.show();
        Glide.with(Game_BettingActivity.this).load(BPApplication.getInstance().getHeadimgurl()).placeholder(R.mipmap.portrait)
                .error(R.mipmap.portrait).transform(new GlideCircleTransform(Game_BettingActivity.this)).into(betting_img);
        Map<String ,String> map = new HashMap<>();
        map.put("member_id",BPApplication.getInstance().getMember_Id());
        map.put("secret",CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"z!l@z#j$"));
        mainPresenter.wodes(SystemConstant.GAME.Game_ACTIVITY_FIRStT,map);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_betting_back:
                finish();
                break;
            case R.id.game_guize_1:
                game_guize_1();
                break;
            case R.id.wangqi_rt:
                intent=new Intent(Game_BettingActivity.this,Game_Betting_WanGqIActivity.class);
                startActivity(intent);
                break;
            case R.id.game_betting_red:
                if(flag_1==0){
                    game_betting_red_dialog();
                }else {
                    toastUtil.showToast(Game_BettingActivity.this,"你已投注蓝方");
                }
                break;
            case R.id.game_betting_blue:
                if(flag_2==0){
                    game_betting_blue_dialog();
                }else {
                    toastUtil.showToast(Game_BettingActivity.this,"你已投注红方");
                }
                break;
        }
    }

    public void game_guize_1(){
        inflater = LayoutInflater.from(Game_BettingActivity.this);
        View view = inflater.inflate(R.layout.game_guize_1,null);
        TextView woyiyuedu= (TextView)view. findViewById(R.id.woyiyuedu);
        dialog = new Dialog(Game_BettingActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);//
        dialog.show();
        // 设置宽度为屏幕的宽度
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width =  (display.getWidth()); // 设置宽度
        lp.height=getResources().getDimensionPixelSize(R.dimen.getheight);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.mainfstyle);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);
        woyiyuedu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }


    public void game_betting_red_dialog() {
        final LayoutInflater inflater = LayoutInflater.from(Game_BettingActivity.this);
        View view = inflater.inflate(R.layout.game_betting_red_dialog, null);
        final Dialog dialog = new Dialog(Game_BettingActivity.this);
        mAmountView = (Ammount_view)view.findViewById(R.id.amount_view);
        ImageView cancel_red = (ImageView) view.findViewById(R.id.cancel_red);
        Button red_sure = (Button)view.findViewById(R.id.red_sure);
        TextView red_love_code_1=(TextView)view.findViewById(R.id.red_love_code_1);
        final TextView red_love_code_2=(TextView)view.findViewById(R.id.red_love_code_2);
        red_Love_code =BPApplication.getInstance().getLove_code();
        red_love_code_1.setText(red_Love_code);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);//
        dialog.show();
        // 设置宽度为屏幕的宽度
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width =  getResources().getDimensionPixelSize(R.dimen.weight);
        lp.height=getResources().getDimensionPixelSize(R.dimen.height);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);

        mAmountView.setGoods_storage((Integer.valueOf(red_Love_code))/10);
        mAmountView.setOnAmountChangeListener(new Ammount_view.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                red_bet_number=amount;
                red_love_code_2.setText((amount*10)+"");
            }
        });
        red_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String ,String> map = new HashMap<String, String>();
                map.put("member_id",BPApplication.getInstance().getMember_Id());
                map.put("topic_time",topic_time);
                map.put("bet","0");
                map.put("bet_number",String.valueOf(red_bet_number*10));
                map.put("secret", CreateMD5.getMd5("0"+String.valueOf(red_bet_number*10)+BPApplication.getInstance().getMember_Id()+topic_time+"z!l@z#j$"));
                mainPresenter.postMap(SystemConstant.GAME.Game_TOPIC,map);
                red_bet_number=1;
                dialog.cancel();
            }
        });
        cancel_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                red_bet_number=1;
                dialog.cancel();

            }
        });
    }

    public void game_betting_blue_dialog() {
        final LayoutInflater inflater = LayoutInflater.from(Game_BettingActivity.this);
        View view = inflater.inflate(R.layout.game_betting_blue_dialog, null);
        final Dialog dialog = new Dialog(Game_BettingActivity.this);
        mAmountView = (Ammount_view)view.findViewById(R.id.amount_view_2);
        ImageView cancel_blue = (ImageView) view.findViewById(R.id.cancel_blue);
        Button blue_sure = (Button)view.findViewById(R.id.blue_sure);
        TextView blue_love_code_1 =(TextView) view.findViewById(R.id.blue_love_code_1);
        final TextView blue_love_code_2 =(TextView)view.findViewById(R.id.blue_love_code_2);
        blue_Love_code =BPApplication.getInstance().getLove_code();
        blue_love_code_1.setText(blue_Love_code);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);//
        dialog.show();
        // 设置宽度为屏幕的宽度
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width =  getResources().getDimensionPixelSize(R.dimen.weight);
        lp.height=getResources().getDimensionPixelSize(R.dimen.height);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);
        mAmountView.setGoods_storage((Integer.valueOf(blue_Love_code))/10);
        mAmountView.setOnAmountChangeListener(new Ammount_view.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                blue_bet_number=amount;
                blue_love_code_2.setText((amount*10)+"");
            }
        });
        blue_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String ,String> map = new HashMap<String, String>();
                map.put("member_id",BPApplication.getInstance().getMember_Id());
                map.put("topic_time",topic_time);
                map.put("bet","1");
                map.put("bet_number",String.valueOf(blue_bet_number*10));
                map.put("secret", CreateMD5.getMd5("1"+String.valueOf(blue_bet_number*10)+BPApplication.getInstance().getMember_Id()+topic_time+"z!l@z#j$"));
                mainPresenter.wodess(SystemConstant.GAME.Game_TOPIC,map);
                blue_bet_number=1;
                dialog.cancel();
            }
        });
        cancel_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    @Override
    public void getView(final String s) {

    }

    @Override
    public void postView(final String s) {
        Log.e("dsadds",s);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")){
                    toastUtil.showToast(Game_BettingActivity.this,jsonObject.getString("msg"));
                    int red_lovecode=Integer.valueOf(red_Love_code)-(blue_bet_number*10);
                    BPApplication.getInstance().setLove_code(String.valueOf(red_lovecode));
                    is_join.setTextColor(0xffFE5E5F);
                    is_join.setText("我已投注红方");
                    flag_2=1;
                }else {
//                    toastUtil.showToast(Game_BettingActivity.this,jsonObject.getString("msg"));
                }
            }
        });
    }

    @Override
    public void postViews(final String s) {
        Log.e("dadwdasasd",s);
                runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject1 =JSONObject.fromObject(s);
                if(jsonObject1.getString("success").equals("true")){
                    pDialog.dismiss();
                    DecimalFormat df = new DecimalFormat("#.##");
                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                    String game_content = jsonObject1.getString("game_content");
                    if(jsonObject1.getString("is_time").equals("1")){
                        rt_count_down.setVisibility(View.VISIBLE);
                        linearLayout6.setVisibility(View.GONE);
                        times=Long.valueOf(jsonObject1.getString("time1"));
                        handler.postDelayed(runnable, 1000);
                    }else {
                        linearLayout6.setVisibility(View.VISIBLE);
                        rt_count_down.setVisibility(View.GONE);
                    }
                    JSONObject jsonObject = JSONObject.fromObject(game_content);
                    topic_time=jsonObject.getString("topic_time");//期数
                    title.setText(jsonObject.getString("title"));
                    if(jsonObject1.getString("join_team").equals("0")){
                        flag_2=1;
                        is_join.setTextColor(0xffFE5E5F);
                        is_join.setText("我已投注红方"+",已投"+jsonObject.getString("my_count")+"注");
                    }else if(jsonObject1.getString("join_team").equals("1")){
                        flag_1=1;
                        is_join.setTextColor(0xff5481FF);
                        is_join.setText("我已投注蓝方"+",已投"+jsonObject.getString("my_count")+"注");
                    }else {
                        is_join.setText("我还没有参加，快去投注吧");
                    }
                    int person_number = (Integer.valueOf(jsonObject.getString("red_number"))+Integer.valueOf(jsonObject.getString("blue_number")));
                    int bet_counts = (Integer.valueOf(jsonObject.getString("red_count"))+Integer.valueOf(jsonObject.getString("blue_count")));
                    time.setText("截至"+formatter.format((Long.valueOf(jsonObject1.getString("time")))*1000)+"本场累计有"+person_number+"人参加，累计押注"+bet_counts+"注");
                    red_title.setText(jsonObject.getString("red_title"));
                    red_content.setText(jsonObject.getString("red_content"));
                    blue_title.setText(jsonObject.getString("blue_title"));
                    blue_content.setText(jsonObject.getString("blue_content"));
                    JSONObject jsonObject2 = JSONObject.fromObject(jsonObject1.getString("last_topic"));
                    title_2.setText("上期话题："+jsonObject2.getString("title"));
                    red_number.setText(jsonObject2.getString("red_number")+"人");
                    blue_number.setText("蓝方"+jsonObject2.getString("blue_number")+"人");
                    Float all_num = (Float.valueOf(jsonObject2.getString("red_number"))+Float.valueOf(jsonObject2.getString("blue_number")));
                    Float red_num =Float.valueOf(jsonObject2.getString("red_number"));
                    Float blue_bum =Float.valueOf(jsonObject2.getString("blue_number"));
                    Float  a =Float.valueOf(df.format((red_num/all_num)));
                    Float  b =Float.valueOf(df.format((blue_bum/all_num)));
                    if(a<0.3){
                        a=(float)0.3;
                        b=(float)0.7;
                    }else if(b<0.3){
                        b=(float)0.3;
                        a=(float)0.7;
                    }
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,a);
                    rt_1.setLayoutParams(lp);
                    LinearLayout.LayoutParams lplp = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,b);
                    rt_2.setLayoutParams(lplp);
                    String my_team;
                    if(jsonObject2.getString("my_team").equals("0")){
                        my_team="红方";
                    }else {
                        my_team="蓝方";
                    }
                        if (jsonObject2.getString("winner").equals("0")) {
                            winner.setText("红方押注人数少，为获胜方");
                            winner_name.setText(jsonObject2.getString("winner_name") + "赢得" + jsonObject2.getString("best_winner_code") + "颗爱心值");
                            my_team_my_code_my_win_code.setText("押" + my_team + jsonObject2.getString("my_code") + "颗爱心值,已赢得" + jsonObject2.getString("my_win_code") + "颗爱心值");
                        } else if (jsonObject2.getString("winner").equals("1")) {
                            winner.setText("蓝方押注人数少，为获胜方");
                            winner_name.setText(jsonObject2.getString("winner_name") + "赢得" + jsonObject2.getString("best_winner_code") + "颗爱心值");
                            my_team_my_code_my_win_code.setText("押" + my_team + jsonObject2.getString("my_code") + "颗爱心值,已赢得" + jsonObject2.getString("my_win_code") + "颗爱心值");
                        } else if(jsonObject2.getString("winner").equals("2")){
                            if((Integer.valueOf(jsonObject2.getString("red_number")))<(Integer.valueOf(jsonObject2.getString("blue_number")))){
                                winner.setText("红方押注人数少，为获胜方");
                            }else {
                                winner.setText("蓝方押注人数少，为获胜方");
                            }
                            winner_name.setText("无人");
                            my_team_my_code_my_win_code.setText("押" + my_team + jsonObject2.getString("my_code") + "颗爱心,已返还");
                        }else {
                            winner.setText("红方押注人数" + jsonObject2.getString("red_number") + "，蓝方押注人数" + jsonObject2.getString("blue_number") + "，平局！");
                            winner_name.setText("平局，没有最佳");
                            my_team_my_code_my_win_code.setText("押" + my_team + jsonObject2.getString("my_code") + "颗爱心,已返还");
                    }
                    if(jsonObject2.getString("my_team").equals("null")){
                        my_team_my_code_my_win_code.setText("您没有参加上期的话题");
                        return;
                    }

                }else {

                }
            }
        });
    }

    @Override
    public void postViewss(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")){
                    toastUtil.showToast(Game_BettingActivity.this,jsonObject.getString("msg"));
                    int blue_lovecode=Integer.valueOf(blue_Love_code)-(blue_bet_number*10);
                    BPApplication.getInstance().setLove_code(String.valueOf(blue_lovecode));
                    is_join.setTextColor(0xff5481FF);
                    is_join.setText("我已投注蓝方");
                    flag_1=1;
                }else {
//                    toastUtil.showToast(Game_BettingActivity.this,jsonObject.getString("msg"));
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
                toastUtil.showToast(Game_BettingActivity.this,"网络连接超时，请稍后再试");
            }
        });
    }

    @Override
    public void imgView(Bitmap bitmap) {

    }


    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            times--;
            String formatLongToTimeStr = formatLongToTimeStr(times);
            String[] split = formatLongToTimeStr.split("：");
            for (int i = 0; i < split.length; i++) {
//                if(i==0){
//                    count_down.setText("距离打卡时间"+split[0]+"小时");
//                }
                if(i==1){
                    count_down.setText("停止押注，开奖倒计时："+split[1]+"：");
                }
                if(i==2){
                    count_down_2.setText(split[2]);
                }
            }
            if(times>0){
                handler.postDelayed(this, 1000);
            }else if(times==0){
                Map<String ,String> map = new HashMap<>();
                map.put("member_id",BPApplication.getInstance().getMember_Id());
                map.put("secret",CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"z!l@z#j$"));
                mainPresenter.wodes(SystemConstant.GAME.Game_ACTIVITY_FIRStT,map);
            }
        }
    };

    public  String formatLongToTimeStr(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = l.intValue() ;
        if (second > 60) {
            minute = second / 60;         //取整
            second = second % 60;         //取余
        }

        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        String strtime = hour+"："+minute+"："+second;
        return strtime;

    }
}

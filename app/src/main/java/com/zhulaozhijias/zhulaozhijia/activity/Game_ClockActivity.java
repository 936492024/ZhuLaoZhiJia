package com.zhulaozhijias.zhulaozhijia.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.AnimationPercentPieView;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.GlideCircleTransform;
import com.zhulaozhijias.zhulaozhijia.widgets.MyRadioGroup;
import com.zhulaozhijias.zhulaozhijia.widgets.PayResult;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;
import com.zhulaozhijias.zhulaozhijia.widgets.eventbus.MainSendEvent;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;
import de.greenrobot.event.EventBus;

/**
 * Created by asus on 2017/10/5.
 */

public class Game_ClockActivity extends BaseActivity implements View.OnClickListener,MainView{
    private LinearLayout mine_clock_back;
    private ImageView game_clock_heading;
    private AnimationPercentPieView animationpercentpieView;
    private ToastUtil toastUtil;
    private Button game_clock_button_2,invition_zaoqi;
    private Intent intent;
    private MainPresenter mainPresenter;
    private RelativeLayout daojishi_rt,game_clock_headings;
    private TextView tvtime1,tvtime2,tvtime3;
    private  long  time;
    private Map<String,String> map = new HashMap<>();
    private TextView today,today_2,good_number,fail_number,early_name,early_day
            ,lucky_name,lucky_money,morning_name,morning_time;
    private ImageView early_img,lucky_img,morning_img;
    private String name;
    private String result="0";
    private String wallet="0";
    private static final int SDK_PAY_FLAG = 1;
    private final IWXAPI api = WXAPIFactory.createWXAPI(this, null);
    private int sum=0;
    private String jine;
    private SweetAlertDialog pDialog;

    @Override
    public void addLayout() {
        setContentView(R.layout.game_clock_activity);
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        mainPresenter=new MainPresenter(this,this);
        mine_clock_back= (LinearLayout) findViewById(R.id.mine_clock_back);
        mine_clock_back.setOnClickListener(this);
        game_clock_heading= (ImageView) findViewById(R.id.game_clock_heading);
        Glide.with(Game_ClockActivity.this).load(BPApplication.getInstance().getHeadimgurl()).placeholder(R.mipmap.portrait)
                .error(R.mipmap.portrait) .transform(new GlideCircleTransform(Game_ClockActivity.this)).into(game_clock_heading);
        animationpercentpieView= (AnimationPercentPieView) findViewById(R.id.animationpercentpieView);
        game_clock_button_2= (Button) findViewById(R.id.game_clock_button_2);
        game_clock_button_2.setOnClickListener(this);
        invition_zaoqi= (Button) findViewById(R.id.invition_zaoqi);
        invition_zaoqi.setOnClickListener(this);
        daojishi_rt= (RelativeLayout) findViewById(R.id.daojishi_rt);
        tvtime1=(TextView)findViewById(R.id.tvtime1);
        tvtime2=(TextView) findViewById(R.id.tvtime2);
        tvtime3=(TextView) findViewById(R.id.tvtime3);
        today= (TextView) findViewById(R.id.today);
        today_2= (TextView) findViewById(R.id.today_2);
        good_number= (TextView) findViewById(R.id.good_number);
        fail_number= (TextView) findViewById(R.id.fail_number);
        early_name= (TextView) findViewById(R.id.early_name);
        early_img= (ImageView) findViewById(R.id.early_img);
        early_day= (TextView) findViewById(R.id.early_day);
        lucky_name= (TextView) findViewById(R.id.lucky_name);
        lucky_img= (ImageView) findViewById(R.id.lucky_img);
        lucky_money= (TextView) findViewById(R.id.lucky_money);
        morning_name= (TextView) findViewById(R.id.morning_name);
        morning_img= (ImageView) findViewById(R.id.morning_img);
        morning_time= (TextView) findViewById(R.id.morning_time);
        game_clock_headings= (RelativeLayout) findViewById(R.id.game_clock_headings);
        game_clock_headings.setOnClickListener(this);
        name = BPApplication.getInstance().getName();
        pDialog  = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("加载中...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(true);
        pDialog.show();
        state();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_clock_back:
                finish();
                break;
            case R.id.invition_zaoqi:
                showShare();
                break;
            case R.id.game_clock_headings:
                 intent = new Intent(Game_ClockActivity.this,Game_Clock_Exploits_Activity.class);
                startActivity(intent);
                break;
        }
    }

    @SuppressWarnings("static-access")
    public void game_clock_dialog() {
        final LayoutInflater inflater = LayoutInflater.from(Game_ClockActivity.this);
        View view = inflater.inflate(R.layout.make_password_dialog, null);
        Button cancel_dialog = (Button) view.findViewById(R.id.cancel_dialog);
        ImageView game_clock_dialog_cancel =(ImageView) view.findViewById(R.id.game_clock_dialog_cancel);
        final Dialog dialog = new Dialog(Game_ClockActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        dialog.setCanceledOnTouchOutside(true);//
        dialog.show();
        // 设置宽度为屏幕的宽度
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width =  getResources().getDimensionPixelSize(R.dimen.clock_weight);
        lp.height=getResources().getDimensionPixelSize(R.dimen.clock_height);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);
        game_clock_dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game_clock_pay_dialog();
                dialog.cancel();
            }
        });
    }


    @SuppressWarnings("static-access")
    public void game_clock_pay_dialog() {
        final LayoutInflater inflater = LayoutInflater.from(Game_ClockActivity.this);
        View view = inflater.inflate(R.layout.game_clock_pay_dialog, null);
        final Dialog dialog = new Dialog(Game_ClockActivity.this);
        final ImageView game_clock_pay_cancel = (ImageView) view.findViewById(R.id.game_clock_pay_cancel);
        final MyRadioGroup game_myradiogroup= (MyRadioGroup) view.findViewById(R.id.game_myradiogroup);
        final Button game_sure= (Button) view.findViewById(R.id.game_sure);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0xffffff));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);//
        dialog.show();
        game_myradiogroup.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                result = radioButton.getText().toString();
                if (result.equals("0")) {
                    wallet = result;
                } else if (result.equals("1")) {
                    wallet = result;
                }
            }
        });
        // 设置宽度为屏幕的宽度
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width =  (display.getWidth()); // 设置宽度
        lp.height=getResources().getDimensionPixelSize(R.dimen.clock_height);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.mainfstyle);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);
        game_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wallet.equals("0")){
                    Map<String,String> map = new HashMap<String, String>();
                    map.put("member_id",BPApplication.getInstance().getMember_Id());
                    map.put("money","1");
                    map.put("secret",CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"1"+"z!l@z#j$"));
                    mainPresenter.wodesss_1(SystemConstant.GEREN_ZHONGXIN.Mine_EARLY_TRADE,map);
                    dialog.cancel();
                }else {
                Map<String,String> map = new HashMap<String, String>();
                map.put("member_id",BPApplication.getInstance().getMember_Id());
                map.put("money","1");
                map.put("secret",CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"1"+"z!l@z#j$"));
                mainPresenter.wodesss(SystemConstant.GEREN_ZHONGXIN.Mine_EARLY_TRADE,map);
                dialog.cancel();
                }
            }
        });

        game_clock_pay_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }


    public void circle(int success_number,int fail_number){
        int[] data = new int[]{success_number, fail_number};
        String[] name = new String[]{"成功", "失败"};
        int[] color = new int[]{
                getResources().getColor(R.color.orange2),
                getResources().getColor(R.color.orange1),
        };
        animationpercentpieView.setData(data, name,color);
    }


    @Override
    public void getView(String s) {

    }

    @Override
    public void postView(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")){
                    if(jsonObject.getString("status").equals("0")){
                        //重来没有参加过
                        game_clock_button_2.setVisibility(View.VISIBLE);
                        daojishi_rt.setVisibility(View.GONE);
                        game_clock_button_2.setText("支付一元，参与打卡");
                        game_clock_button_2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                game_clock_pay_dialog();
                            }
                        });
                    }else if(jsonObject.getString("status").equals("1")){
                        //已报名，未打卡（时间未到）
                        time=Long.valueOf(jsonObject.getString("seconds"));
                        game_clock_button_2.setVisibility(View.GONE);
                        daojishi_rt.setVisibility(View.VISIBLE);
                        invition_zaoqi.setVisibility(View.VISIBLE);
                        handler.postDelayed(runnable, 1000);
                    }else if(jsonObject.getString("status").equals("2")){
                        //可以打卡（时间到了）
                        game_clock_button_2.setVisibility(View.VISIBLE);
                        daojishi_rt.setVisibility(View.GONE);
                        game_clock_button_2.setText("点击打卡");
                        game_clock_button_2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Map<String,String> map = new HashMap<>();
                                map.put("member_id",BPApplication.getInstance().getMember_Id());
                                map.put("secret", CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"z!l@z#j$"));
                                mainPresenter.wodes(SystemConstant.GAME.Game_CLOCK,map);
                            }
                        });
                    }else if(jsonObject.getString("status").equals("3")){
                        //已打卡，未参加下一个
                        game_clock_button_2.setVisibility(View.VISIBLE);
                        daojishi_rt.setVisibility(View.GONE);
                        game_clock_button_2.setText("支付一元，参与打卡");
                        game_clock_button_2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                game_clock_pay_dialog();
                            }
                        });
                    }else if(jsonObject.getString("status").equals("4")){
                        //已打卡，已经参加下一个
                        time=Long.valueOf(jsonObject.getString("seconds"));
                        game_clock_button_2.setVisibility(View.GONE);
                        daojishi_rt.setVisibility(View.VISIBLE);
                        invition_zaoqi.setVisibility(View.VISIBLE);
                        handler.postDelayed(runnable, 1000);
                    }
                }else if(jsonObject.getString("success").equals("false")){
                       toastUtil.showToast(Game_ClockActivity.this,"服务器异常");
                }
            }
        });
    }

    @Override
    public void postViews(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(s.indexOf("trade_no")!=-1){
                    JSONObject jsonObject = JSONObject.fromObject(s);
                    if(jsonObject.getString("success").equals("true")){
                        aliPayData(jsonObject.getString("alipay"));
                    }
                }else {
                    JSONObject jsonObject = JSONObject.fromObject(s);
                    if (jsonObject.getString("success").equals("true")) {
                        game_clock_dialog();
                        game_clock_button_2.setText("支付一元，参与打卡");
                    } else {

                    }
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
                if (jsonObject.getString("success").equals("true")){
                   String data = jsonObject.getString("data");
                    JSONObject jsonObject1 = JSONObject.fromObject(data);
                    jine=jsonObject1.getString("today");
                    today.setText(jine);
                    today_2.setText(jine);
                    good_number.setText(jsonObject1.getString("success_number")+"人");
                    fail_number.setText(jsonObject1.getString("fail_number")+"人");
                    early_name.setText(jsonObject1.getString("early_name"));
                    early_day.setText("连续"+jsonObject1.getString("early_day")+"天");
                    if(Util.isOnMainThread()){
                        Glide.with(Game_ClockActivity.this).load(jsonObject1.getString("early_img")).placeholder(R.mipmap.portrait)
                                .error(R.mipmap.portrait).transform(new GlideCircleTransform(Game_ClockActivity.this)).into(early_img);
                        Glide.with(Game_ClockActivity.this).load(jsonObject1.getString("lucky_img")).placeholder(R.mipmap.portrait)
                                .error(R.mipmap.portrait).transform(new GlideCircleTransform(Game_ClockActivity.this)).into(lucky_img);
                        Glide.with(Game_ClockActivity.this).load(jsonObject1.getString("morning_img")).placeholder(R.mipmap.portrait)
                                .error(R.mipmap.portrait).transform(new GlideCircleTransform(Game_ClockActivity.this)).into(morning_img);
                    }
                    lucky_name.setText(jsonObject1.getString("lucky_name"));
//                    Double a =Double.valueOf(jsonObject1.getString("lucky_money"))+1;
                    lucky_money.setText(jsonObject1.getString("lucky_money")+"元");
                    morning_name.setText(jsonObject1.getString("morning_name"));
                    morning_time.setText(jsonObject1.getString("morning_time")+"打卡");
                    circle(Integer.valueOf(jsonObject1.getString("success_number")), Integer.valueOf(jsonObject1.getString("fail_number")));
                    pDialog.dismiss();
                }else {
                    toastUtil.showToast(Game_ClockActivity.this,jsonObject.getString("msg"));
                }
            }
        });

    }

    @Override
    public void postViewsss(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(s.indexOf("trade_no")!=-1){
                    JSONObject jsonObject =JSONObject.fromObject(s);
                    Map<String,String >map = new HashMap<String, String>();
                    map.put("trade_no",jsonObject.getString("trade_no"));
                    map.put("secret",CreateMD5.getMd5(jsonObject.getString("trade_no")+"z!l@z#j$"));
                    mainPresenter.wodes(SystemConstant.GEREN_ZHONGXIN.Mine_ALIPAY_EARLY_PAY,map);
                }
            }
        });
    }

    @Override
    public void postViewsss_1(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")){
                    Map<String,String> map  = new HashMap<>();
                    map.put("trade_no",jsonObject.getString("trade_no"));
                    map.put("secret",CreateMD5.getMd5(jsonObject.getString("trade_no")+"z!l@z#j$"));
                    mainPresenter.wodesss_2(SystemConstant.GEREN_ZHONGXIN.Mine_WECHATPAY_PAY_EARLY,map);
                }
            }
        });
    }

    @Override
    public void postViewsss_2(final String s) {
        Log.e("dsasa",s);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")){
                    String data = jsonObject.getString("data");
                    JSONObject jsonObject1 =JSONObject.fromObject(data);
                    wechat(jsonObject1.getString("appid"),jsonObject1.getString("partnerid"),jsonObject1.getString("prepayid"),jsonObject1.getString("package"),jsonObject1.getString("noncestr"),jsonObject1.getString("timestamp"),jsonObject1.getString("sign"));
                    BPApplication.getInstance().setWeChat_4("4");
                }
            }
        });
    }

    @Override
    public void fail(String s) {
         runOnUiThread(new Runnable() {
             @Override
             public void run() {
                 pDialog.dismiss();
                 toastUtil.showToast(Game_ClockActivity.this,"网络连接超时，请稍后再试");
             }
         });
    }

    //事件接受
    public void onEventMainThread(MainSendEvent event){
        if(event != null){
            if(event.getStringMsgData().equals("打卡支付")){
                if(sum==0){
                    Log.e("微信支付","微信支付");
                    BPApplication.getInstance().setWeChat_4("");
                    today.setText((Integer.valueOf(jine)+1)+"");
                    today_2.setText((Integer.valueOf(jine)+1)+"");
                    map.put("member_id",BPApplication.getInstance().getMember_Id());
                    map.put("secret", CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"z!l@z#j$"));
                    mainPresenter.postMap(SystemConstant.GAME.Game_SEL_EARLY,map);
                    sum=1;
                }
            }
        }
    }

    public void wechat(String appId,String partnerId,String prepayId,String packageValue,String nonceStr,
                       String timeStamp,String sign){

        PayReq request = new PayReq();

        request.appId = appId;

        request.partnerId = partnerId;

        request.prepayId= prepayId;

        request.packageValue = packageValue;

        request.nonceStr= nonceStr;

        request.timeStamp= timeStamp;

        request.sign= sign;

        api.sendReq(request);
    }

    @Override
    public void imgView(Bitmap bitmap) {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Util.isOnMainThread()) {
            Glide.with(getApplication()).pauseRequests();
        }
    }
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
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
                    paramsToShare.setText("爱心是一盏灯，照亮别人，温暖自己http://app.sesdf.org/");
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
        //启动分享
        oks.show(this);
    }
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            time--;
            String formatLongToTimeStr = formatLongToTimeStr(time);
            String[] split = formatLongToTimeStr.split("：");
            for (int i = 0; i < split.length; i++) {
                if(i==0){
                    tvtime1.setText("距离打卡时间"+split[0]+"小时");
                }
                if(i==1){
                    tvtime2.setText(split[1]+"分钟");
                }
                if(i==2){
                    tvtime3.setText(split[2]+"秒");
                }
            }
            if(time>0){
                handler.postDelayed(this, 1000);
            }else if(time==0){
                daojishi_rt.setVisibility(View.GONE);
                game_clock_button_2.setVisibility(View.VISIBLE);
                game_clock_button_2.setText("打卡");
                game_clock_button_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,String> map = new HashMap<>();
                        map.put("member_id",BPApplication.getInstance().getMember_Id());
                        map.put("secret", CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"z!l@z#j$"));
                        mainPresenter.wodes(SystemConstant.GAME.Game_CLOCK,map);
//                        game_clock_dialog();
                    }
                });
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





    public void aliPayData(String aliPayBean) {
        //支付宝支付请求所需的签名字符串
        final String orderInfo = aliPayBean;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(Game_ClockActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    //支付宝返回数据handler
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    String result = "";
                    // 判断resultStatus 为9000则代表支付成功
                    Log.e("TAG", resultStatus);
                    if (TextUtils.equals(resultStatus, "9000")) {
                        //支付成功
                        result = "支付成功";
                        state();
                    } else if ("6001".equals(resultStatus)) {
                        result = "您取消了支付";
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        result = "支付失败";
                    }
                    ToastUtil.showToast(Game_ClockActivity.this, result);
                    break;
                }
            }
        }
    };

    public void state(){
        map.put("member_id",BPApplication.getInstance().getMember_Id());
        map.put("secret", CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"z!l@z#j$"));
        mainPresenter.postMap(SystemConstant.GAME.Game_SEL_EARLY,map);
        map.put("secret",CreateMD5.getMd5("z!l@z#j$"));
        mainPresenter.wodess(SystemConstant.GAME.Game_SEL_NUMBER,map);
    }
}

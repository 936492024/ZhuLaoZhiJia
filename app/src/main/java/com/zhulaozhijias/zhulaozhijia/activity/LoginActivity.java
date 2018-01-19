package com.zhulaozhijias.zhulaozhijia.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.mob.tools.utils.UIHandler;
import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;

import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.HeaderPresenter;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.IHeaderView;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.FileUtil;
import com.zhulaozhijias.zhulaozhijia.widgets.IDCardValidate;
import com.zhulaozhijias.zhulaozhijia.widgets.JtFtConvert;
import com.zhulaozhijias.zhulaozhijia.widgets.MyCountDownTimer;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;
import net.sf.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by asus on 2017/9/8.
 */

public class LoginActivity extends BaseActivity implements Handler.Callback, PlatformActionListener ,View.OnClickListener,MainView{
    private ImageView wechat_login,phone_cancel;
    private Button yanzheng_btn,login_btn,regiter_btn,login_btn2,yanzhengma_btn;
    private RelativeLayout re_name,re_idcard,re_delu_password,re_quren_password;
    private MainPresenter mainPresenter = new MainPresenter(LoginActivity.this, this);
    private EditText login_import_phone,login_import_dlpassword,login_import_qrpassword,login_import_name,login_import_idcard,yanzheng_login_text;
    private Map<String, String> map= new HashMap<>();
    private ToastUtil toastUtil;
    private IDCardValidate idCardValidate=new IDCardValidate(this);
    private Intent intent;
    private RelativeLayout weixin_rt;
    private LinearLayout short_code;
    private boolean backFlag = false;
    private TextView forget_password;
    private SweetAlertDialog pDialog;
    private String captcha,phone_code,cjyz;
    private JtFtConvert jtFtConvert=new JtFtConvert();
    private String code_flag="0";
    private String status_flag,phone_xing;
    private ImageView saomiao;
    private static final int MSG_ACTION_CCALLBACK = 0;
    private static final int REQUEST_CODE_CAMERA = 102;
    private static final int REQUEST_CODE_DRIVING_LICENSE = 103;
    private static final int REQUEST_CODE_VEHICLE_LICENSE = 104;

    @Override
    public void addLayout() {
        setContentView(R.layout.activity_login);
    }
    @Override
    public void initView() {
        wechat_login= (ImageView) findViewById(R.id.wechat_login);//微信登陆
        wechat_login.setOnClickListener(this);
//        login_yanzheng= (RelativeLayout) findViewById(R.id.login_yanzheng);//验证relativelayout
        re_name= (RelativeLayout) findViewById(R.id.re_name);
        re_idcard= (RelativeLayout) findViewById(R.id.re_idcard);
        re_delu_password= (RelativeLayout) findViewById(R.id.re_delu_password);
//        yanzheng_btn= (Button) findViewById(R.id.yanzheng_btn);//验证button按钮
//        yanzheng_btn.setOnClickListener(this);
        phone_cancel= (ImageView) findViewById(R.id.phone_cancel);
        phone_cancel.setOnClickListener(this);
        login_import_qrpassword= (EditText) findViewById(R.id.login_import_qrpassword);
        login_import_phone= (EditText) findViewById(R.id.login_import_phone);
        re_quren_password= (RelativeLayout) findViewById(R.id.re_quren_password);
        login_btn= (Button) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(this);
        yanzhengma_btn= (Button) findViewById(R.id.yanzhengma_btn);
        yanzhengma_btn.setOnClickListener(this);
        login_import_dlpassword= (EditText) findViewById(R.id.login_import_dlpassword);
        regiter_btn= (Button) findViewById(R.id.regiter_btn);
        regiter_btn.setOnClickListener(this);
//        saomiao= (ImageView) findViewById(R.id.saomiao);
//        saomiao.setOnClickListener(this);
        login_import_name= (EditText) findViewById(R.id.login_import_name);
        login_import_idcard= (EditText) findViewById(R.id.login_import_idcard);
        login_btn2= (Button) findViewById(R.id.login_btn2);
        login_btn2.setOnClickListener(this);
        weixin_rt= (RelativeLayout) findViewById(R.id.weixin_rt);
        forget_password= (TextView) findViewById(R.id.forget_password);
        forget_password.setOnClickListener(this);
        short_code= (LinearLayout) findViewById(R.id.short_code);
        yanzheng_login_text= (EditText) findViewById(R.id.yanzheng_login_text);
        pDialog  = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("加载中...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(true);
        if(!TextUtils.isEmpty(getIntent().getStringExtra("LoginActivity"))){
            BPApplication.getInstance().exits(LoginActivity.this);
            BPApplication.getInstance().setLogined(false);
            BPApplication.getInstance().setMember_Id("");
            BPApplication.getInstance().setMobile("");
            BPApplication.getInstance().setToken("");
        }
        edit_onclic();
//        initAccessTokenWithAkSk();

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.wechat_login:
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                wechat.setPlatformActionListener(this);
                wechat.SSOSetting(false);
                authorize(wechat);
                break;
            case R.id.login_btn://登录
                String getphone_1=login_import_phone.getText().toString();
                String login_import_dlpassword_texts=login_import_dlpassword.getText().toString();
                if(!isNumeric(getphone_1)){
                    toastUtil.showToast(LoginActivity.this,"请输入正确的电话号码");
                    return;
                }
                if(!TextUtils.isEmpty(cjyz)){
                    if(yanzheng_login_text.getText().toString().equals(cjyz)){

                    }else {
                        toastUtil.showToast(LoginActivity.this,"验证码不匹配");
                    }
                }
                if(status_flag.equals("0")){
                    if(!TextUtils.isEmpty(captcha)){
                        if(!yanzheng_login_text.getText().toString().equals(captcha)){
                            toastUtil.showToast(LoginActivity.this,"验证码不匹配");
                            return;
                        }
                    }
                }
                if(!TextUtils.isEmpty(login_import_dlpassword_texts)){
                    pDialog.setTitleText("登录中...");
                    pDialog.show();
                        Map<String ,String> mapss = new HashMap<>();
                        mapss.put("mobile",login_import_phone.getText().toString());
                        mapss.put("password",login_import_dlpassword_texts);
                        if(status_flag.equals("0")){
                            mapss.put("status","1");
                            mapss.put("secret",CreateMD5.getMd5(login_import_phone.getText().toString()+login_import_dlpassword_texts+"1"+"z!l@z#j$"));
                        }else {
                            mapss.put("status","1");
                            mapss.put("secret",CreateMD5.getMd5(login_import_phone.getText().toString()+login_import_dlpassword_texts+"1"+"z!l@z#j$"));
                        }
                        mainPresenter.wodes(SystemConstant.UserConstant.USER_ACCOUNT_LOGIN,mapss);

                }else {
                    toastUtil.showToast(LoginActivity.this,"密码不能为空");
                }
                break;
            case R.id.login_btn2://微信登录的用户
                String getphone_2=login_import_phone.getText().toString();
                String login_import_dlpassword_textss=login_import_dlpassword.getText().toString();
                String login_import_qrpassword_textss=login_import_qrpassword.getText().toString();
                if(!isNumeric(getphone_2)){
                    toastUtil.showToast(LoginActivity.this,"请输入正确的电话号码");
                    return;
                }
                if(!TextUtils.isEmpty(cjyz)){
                    if(yanzheng_login_text.getText().toString().equals(cjyz)){

                    }else {
                        toastUtil.showToast(LoginActivity.this,"验证码不匹配");
                    }
                }
                if(status_flag.equals("0")){
                    if(!TextUtils.isEmpty(captcha)){
                        if(!yanzheng_login_text.getText().toString().equals(captcha)){
                            toastUtil.showToast(LoginActivity.this,"验证码不匹配");
                            return;

                        }
                    }
                }
                if(TextUtils.isEmpty(login_import_dlpassword_textss)&&TextUtils.isEmpty(login_import_qrpassword_textss)){
                    toastUtil.showToast(LoginActivity.this,"密码不能为空");
                    return;
                }
                if (!login_import_dlpassword_textss.equals(login_import_qrpassword_textss)) {
                    toastUtil.showToast(LoginActivity.this,"两次输入的密码不一致");
                    return;
                }
                pDialog.setTitleText("登录中...");
                pDialog.show();
                Map<String ,String> mapss = new HashMap<>();
                mapss.put("mobile",login_import_phone.getText().toString());
                mapss.put("password",login_import_dlpassword_textss);
                if(status_flag.equals("0")){
                    mapss.put("status","1");
                    mapss.put("secret",CreateMD5.getMd5(login_import_phone.getText().toString()+login_import_qrpassword.getText().toString()+"1"+"z!l@z#j$"));
                }else {
                    mapss.put("status","1");
                    mapss.put("secret",CreateMD5.getMd5(login_import_phone.getText().toString()+login_import_qrpassword.getText().toString()+"1"+"z!l@z#j$"));
                }
                mainPresenter.wodes(SystemConstant.UserConstant.USER_WECHAT_PASSWORD,mapss);
                break;
            case R.id.regiter_btn://注册

                String getphone_3=login_import_phone.getText().toString();
                String login_import_dlpassword_text=login_import_dlpassword.getText().toString();
                String login_import_qrpassword_text=login_import_qrpassword.getText().toString();
                if(!isNumeric(getphone_3)){
                    toastUtil.showToast(LoginActivity.this,"请输入正确的电话号码");
                    return;
                }
                if(!yanzheng_login_text.getText().toString().equals(captcha)){
                    toastUtil.showToast(LoginActivity.this,"验证码不匹配");
                    return;
                }
                if(TextUtils.isEmpty(login_import_name.getText().toString())){
                    toastUtil.showToast(LoginActivity.this,"请输入您的姓名");
                    return;
                }
                if(!isNameric(login_import_name.getText().toString())){
                    toastUtil.showToast(LoginActivity.this,"姓名必须为简体中文");
                    return;
                }
                if(login_import_name.getText().toString().length()<2){
                    toastUtil.showToast(LoginActivity.this,"姓名不得少于两个字");
                    return;
                }
                if(login_import_name.getText().toString().length()>4){
                    toastUtil.showToast(LoginActivity.this,"姓名不得大于四个字");
                    return;
                }
                login_import_name.setText(jtFtConvert.convert(login_import_name.getText().toString(),0));
                if(TextUtils.isEmpty(login_import_idcard.getText().toString())){
                    toastUtil.showToast(LoginActivity.this,"请输入您的身份证号");
                    return;
                }
                Pattern pattern = Pattern.compile(SystemConstant.PublicConstant.id_regular);
                Matcher matcher = pattern.matcher(login_import_idcard.getText().toString());
                boolean b = matcher.matches();
                if(!b){
                    toastUtil.showToast(LoginActivity.this,"身份证号输入不正确");
                    return;
                }
                else {
                }
                if (TextUtils.isEmpty(login_import_dlpassword_text) || TextUtils.isEmpty(login_import_qrpassword_text)) {
                    toastUtil.showToast(LoginActivity.this,"请输入您的密码");
                    return;
                    }
                if (!login_import_dlpassword_text.equals(login_import_qrpassword_text)) {
                    toastUtil.showToast(LoginActivity.this,"两次输入的密码不一致");
                    return;
                    }
                if (!phone_code.equals(login_import_phone.getText().toString())) {
                    toastUtil.showToast(LoginActivity.this,"输入手机号与验证过手机号不一致");
                    return;
                }
                pDialog.setTitleText("登录中...");
                pDialog.show();
                            Map<String, String> maps = new HashMap<>();
                            maps.put("card_id", login_import_idcard.getText().toString());
                            maps.put("mobile", login_import_phone.getText().toString());
                            maps.put("name", login_import_name.getText().toString());
                            maps.put("platform", "1");
                            maps.put("password", login_import_dlpassword.getText().toString());
                            if(code_flag.equals("0")){
                                maps.put("status","1");
                                maps.put("secret", CreateMD5.getMd5(login_import_idcard.getText().toString() +
                                        login_import_phone.getText().toString() + login_import_name.getText().toString() +
                                        login_import_qrpassword.getText().toString() +"1"+"1"+
                                        "z!l@z#j$"));
                              }else {
                                maps.put("secret", CreateMD5.getMd5(login_import_idcard.getText().toString() +
                                        login_import_phone.getText().toString() + login_import_name.getText().toString() +
                                        login_import_qrpassword.getText().toString() +"1"+
                                        "z!l@z#j$"));
                            }
                            mainPresenter.wodess(SystemConstant.UserConstant.USER_ACCOUNT_REGISTER, maps);
                break;
            case R.id.forget_password:
                intent = new Intent(LoginActivity.this,Forget_PassWordActivity.class);
                startActivity(intent);
                break;
            case R.id.phone_cancel:
                login_import_phone.setText("");
                break;
//            case R.id.saomiao:
//                Intent intent = new Intent(LoginActivity.this, CameraActivity.class);
//                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
//                        FileUtil.getSaveFile(getApplication()).getAbsolutePath());
//                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
//                startActivityForResult(intent, REQUEST_CODE_CAMERA);
//                break;
            case R.id.yanzhengma_btn://获取验证码
                String phone=login_import_phone.getText().toString();
                if(!isNumeric(phone)){
                    toastUtil.showToast(LoginActivity.this,"请输入正确的电话号码");
                }else {
                    map.put("mobile", phone);
                    map.put("secret", CreateMD5.getMd5(phone+"z!l@z#j$"));
                    mainPresenter.wodesss_1(SystemConstant.UserConstant.USER_VERIFICATION,map);
                }
                break;
        }
    }

    //授权
    private void authorize(Platform plat) {
        if (plat == null) {
            return;
        }
          pDialog.setTitleText("登录中...");
          pDialog.show();
        if (plat.isAuthValid()) { //如果授权就删除授权资料
            plat.removeAccount(true);
        }
        plat.showUser(null);//授权并获取用户信息
    }

    //登陆授权成功的回调
    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> res) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 1;
        msg.arg2 = 11;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);   //发送消息
    }

    //登陆授权错误的回调
    @Override
    public void onError(Platform platform, int i, Throwable t) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 2;
        msg.arg2 = 12;
        msg.obj = t;
        UIHandler.sendMessage(msg, this);
    }

    //登陆授权取消的回调
    @Override
    public void onCancel(Platform platform, int i) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 3;
        msg.arg2 = 13;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    //登陆发送的handle消息在这里处理
    @Override
    public boolean handleMessage(Message message) {
        switch (message.arg1) {
            case 1: { // 成功
                //获取用户资料
                Platform platform = (Platform) message.obj;
                String openid   = platform.getDb().get("openid");//获取用户的openid
                String token = platform.getDb().getToken();//获取用户的token值
                String userId = platform.getDb().getUserId();//获取用户账号
                String userName = platform.getDb().getUserName();//获取用户名字
                String userIcon = platform.getDb().getUserIcon();//获取用户头像
                String userGender = platform.getDb().getUserGender(); //获取用户性别，m = 男, f = 女，如果微信没有设置性别,默认返回null
                if(String.valueOf(userGender).equals("f")){
                    userGender="0";
                }else{
                    userGender="1";
                }
                Map<String,String> map = new HashMap<>();
                map.put("open_id",openid);
                map.put("unionid",platform.getDb().get("unionid"));
                map.put("username",userName);
                map.put("gender",userGender);
                map.put("access_token",token);
                if(userIcon.equals("\0")||TextUtils.isEmpty(userIcon)){
                    userIcon="http://img.sesdf.org/holdHead.jpg";
                }else {
                    userIcon=platform.getDb().getUserIcon();
                }
                map.put("headimgurl",userIcon);
                map.put("platform","1");
                map.put("secret",CreateMD5.getMd5(token+userGender+userIcon+openid+"1"+platform.getDb().get("unionid")+userName+"z!l@z#j$"));
                mainPresenter.wodesss(SystemConstant.UserConstant.USER_APP_OAUTH,map);
                //下面就可以利用获取的用户信息登录自己的服务器或者做自己想做的事啦!
            }
            break;
            case 2: { // 失败
                Toast.makeText(LoginActivity.this, "授权登陆失败", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
            break;
            case 3: { // 取消
                Toast.makeText(LoginActivity.this, "授权登陆取消", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
            break;
        }
        return false;
    }

    @Override
    public void getView(String s) {

    }

    @Override
    public void postView(final String s) {
        Log.e("check",s);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
            JSONObject json = JSONObject.fromObject(s);
                if(s.indexOf("status")!=-1){
                    status_flag=json.getString("status");
                }else{
                    code_flag="0";
                }
                    if (json.size()<5) {
                        toastUtil.showToast(LoginActivity.this, "请填写注册资料");
                        re_name.setVisibility(View.VISIBLE);//姓名
                        re_idcard.setVisibility(View.VISIBLE);//身份证
                        re_delu_password.setVisibility(View.VISIBLE);//登录密码
                        re_quren_password.setVisibility(View.VISIBLE);//确认登录密码
//                        login_yanzheng.setVisibility(View.VISIBLE);//验证按钮
                        regiter_btn.setVisibility(View.VISIBLE);//注册下的登录按钮
                        login_btn2.setVisibility(View.GONE);
                        login_btn.setVisibility(View.GONE);
                        login_import_dlpassword.setText("");
                        login_import_qrpassword.setText("");
                        forget_password.setVisibility(View.GONE);
                        short_code.setVisibility(View.VISIBLE);
                        regiter_btn.setText("登录");
                    } else if (json.size()>=5){
                        String password=json.getString("password");
                        if(status_flag.equals("0")){
                            short_code.setVisibility(View.VISIBLE);
                        }else {
                            short_code.setVisibility(View.GONE);
                        }
                        if(password.equals("null")||password.equals("")) {    //微信端有账号，app端没有账号
                            //app端有密码  并且登录
                            toastUtil.showToast(LoginActivity.this, "请设置密码");
                            re_name.setVisibility(View.GONE);//姓名
                            re_idcard.setVisibility(View.GONE);//身份证
                            re_delu_password.setVisibility(View.VISIBLE);
                            re_quren_password.setVisibility(View.VISIBLE);
                            weixin_rt.setVisibility(View.GONE);
                            login_btn2.setVisibility(View.VISIBLE);
                            regiter_btn.setVisibility(View.GONE);
                            login_btn.setVisibility(View.GONE);
                            forget_password.setVisibility(View.GONE);
                            login_import_dlpassword.setText("");
                            login_import_qrpassword.setText("");
                        }else {
                            toastUtil.showToast(LoginActivity.this, "请输入登录密码");
                            re_name.setVisibility(View.GONE);//姓名
                            re_idcard.setVisibility(View.GONE);//身份证
                            re_quren_password.setVisibility(View.GONE);
                            re_delu_password.setVisibility(View.VISIBLE);
                            login_btn.setVisibility(View.VISIBLE);
                            login_btn2.setVisibility(View.GONE);
                            regiter_btn.setVisibility(View.GONE);
                            forget_password.setVisibility(View.VISIBLE);
                            login_import_dlpassword.setText("");
                            login_import_qrpassword.setText("");
                            login_import_dlpassword.setHint("请输入您的密码");
                        }
                    }
                pDialog.dismiss();
           }
        });
    }

    @Override
    public void postViews(final String s) {
        Log.e("登录登录",s);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject json = JSONObject.fromObject(s);
                if(json.size()>5) {
                    BPApplication.getInstance().setLogined(true);
                    BPApplication.getInstance().init(json.getString("member_id"),json.getString("name"),
                            json.getString("card_id"),json.getString("mobile"),json.getString("banlance"),
                            json.getString("headimgurl"),json.getString("token"),json.getString("join_time"),
                            true,json.getString("point"),json.getString("level"),json.getString("love_code")
                            ,json.getString("signer"),json.getString("pay_password"));
                    BPApplication.getInstance().exits(LoginActivity.this);
                    BPApplication.getInstance().setTitle("1");
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    toastUtil.showToast(LoginActivity.this,json.getString("msg"));
                }
                pDialog.dismiss();
            }
    });
    }

    @Override
    public void postViewss(final String s) {
        Log.e("注册",s);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject json = JSONObject.fromObject(s);
                if (json.getString("success").equals("true")) {
                    BPApplication.getInstance().init(json.getString("member_id"), json.getString("name"),
                            json.getString("card_id"), json.getString("mobile"), json.getString("banlance"),
                            json.getString("headimgurl"), json.getString("token"), json.getString("join_time"),
                            true, json.getString("point"), json.getString("level"), json.getString("love_code"),
                            json.getString("signer"),json.getString("pay_password"));
                    toastUtil.showToast(LoginActivity.this, "登录成功");
                    BPApplication.getInstance().exits(LoginActivity.this);
                    BPApplication.getInstance().setTitle("1");
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    pDialog.dismiss();
                }else {
                    if(json.getString("status").equals("1")){
                        JSONObject jsonObject = JSONObject.fromObject(json.getString("data"));
                        Log.e("dsadada",jsonObject.getString("mobile"));
                        if(!TextUtils.isEmpty(jsonObject.getString("mobile")) && jsonObject.getString("mobile").length() > 6 ){
                            StringBuilder sb  =new StringBuilder();
                            for (int i = 0; i < jsonObject.getString("mobile").length(); i++) {
                                char c = jsonObject.getString("mobile").charAt(i);
                                if (i >= 3 && i <= 6) {
                                    sb.append('*');
                                } else {
                                    sb.append(c);
                                }
                            }
                            phone_xing=sb.toString();
                        }
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("温馨提示");
                        builder.setMessage("您的身份证号已被注册，请使用手机号码"+phone_xing+"进行登陆，若有疑问请拨打电话:028-96552咨询。");
                        builder.setCancelable(false);
                        builder.setPositiveButton("确定",null);
                        builder.setIcon(R.drawable.ic_launcher);
                        builder.show();
                    }else {

                    }
                    pDialog.dismiss();
                    return;
                }
            }
          });
    }

    @Override
    public void postViewsss(final String s) {
        Log.e("微信登录",s);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(TextUtils.isEmpty(s)){
                    toastUtil.showToast(LoginActivity.this,"登录异常，请使用其它方式登录");
                   return;
                }
                JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")){
                    JSONObject json = JSONObject.fromObject(jsonObject.getString("member"));
                    if(jsonObject.getString("status").equals("0")){//新用户
                        Intent intent = new Intent(LoginActivity.this,Wechat_LoginActivity.class);
                        intent.putExtra("member_id",json.getString("member_id"));
                        pDialog.dismiss();
                        startActivity(intent);
                    }else if(jsonObject.getString("status").equals("1")){//老用户
                        BPApplication.getInstance().init(json.getString("member_id"),json.getString("name"),
                                json.getString("card_id"),json.getString("mobile"),json.getString("banlance"),
                                json.getString("headimgurl"),json.getString("token"),json.getString("join_time"),
                                true,json.getString("point"),json.getString("level"),json.getString("love_code")
                                ,json.getString("signer"),json.getString("pay_password"));
                        BPApplication.getInstance().exits(LoginActivity.this);
                        BPApplication.getInstance().setTitle("1");
                        intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        toastUtil.showToast(LoginActivity.this,"登录成功");
                    }
                }else {

                }

            }
        });
    }

    @Override
    public void postViewsss_1(final String s) {
        Log.e("dasdasda",s);
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               JSONObject json = JSONObject.fromObject(s);
               if(json.getString("success").equals("true")){
                   toastUtil.showToast(LoginActivity.this,"发送成功");
                   MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000,yanzhengma_btn);
                   myCountDownTimer.start();
                   captcha=json.getString("captcha");
                   phone_code=json.getString("mobile");
               }else {
                   cjyz=json.getString("cjyz");
                   toastUtil.showToast(LoginActivity.this,json.getString("msg"));
               }
           }
       });
    }

    @Override
    public void postViewsss_2(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void fail(String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pDialog.dismiss();
                toastUtil.showToast(LoginActivity.this,"网络连接超时，请稍后再试");
            }
        });
    }

    @Override
    public void imgView(Bitmap bitmap) {

    }

    @Override
    public void onBackPressed() {
        if(!TextUtils.isEmpty(getIntent().getStringExtra("LoginActivity"))){
//        if(backFlag){
//            //退出
//            super.onBackPressed();
//        }else{
//            //单击一次提示信息
//            toastUtil.showToast(LoginActivity.this,"再点一次退出程序");
//            backFlag=true;
//            new Thread(){
//                public void run() {
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                    //3秒之后，修改flag的状态
//                    backFlag=false;
//                }
//            }.start();
//        }
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            BPApplication.getInstance().setLogined(false);
//            BPApplication.getInstance().exits(LoginActivity.this);
//            BPApplication.getInstance().setMobile("");
//            BPApplication.getInstance().setToken("");
            BPApplication.getInstance().setTitle("0");
            startActivity(intent);
            finish();
        }else {
            super.onBackPressed();
        }
    }


    public boolean isNameric(String str){
        Pattern pattern = Pattern.compile("^[\\u4E00-\\u9FA5]+$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("^1[3|4|5|7|8|9][0-9]{9}$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    public void edit_onclic(){
        login_import_phone.addTextChangedListener(new TextWatcher() {//电话号码的监听
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                phone_cancel.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()==0){
                    phone_cancel.setVisibility(View.GONE);
                }
                if(editable.toString().length()>=11){
                    String getphone=login_import_phone.getText().toString();
                    if(!isNumeric(getphone)){
                        toastUtil.showToast(LoginActivity.this,"请输入正确的电话号码");
                    }else {
                        pDialog.setTitleText("验证中...");
                        pDialog.show();
                        map.put("mobile", getphone);
                        map.put("secret", CreateMD5.getMd5(getphone + "z!l@z#j$"));
                        mainPresenter.postMap(SystemConstant.UserConstant.USER_ACCOUNT_CHECK, map);
                    }
                }
            }
        });
    }

    private void showDialog(String str) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage(str);
        builder.setCancelable(false);
        builder.setPositiveButton("确定",null);
        builder.setIcon(R.drawable.ic_launcher);
        builder.show();
    }

}


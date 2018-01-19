package com.zhulaozhijias.zhulaozhijia.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.JtFtConvert;
import com.zhulaozhijias.zhulaozhijia.widgets.MyCountDownTimer;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by asus on 2017/11/3.
 */

public class Wechat_LoginActivity extends BaseActivity implements View.OnClickListener,MainView{
    private RelativeLayout re_name_2,re_code_2,re_phone_2,re_idcard_2,re_login_password_2,re_login_srue_password_2,progress_bar_wanshan;
    private EditText login_import_name_2,login_import_code_2,login_import_phone_2,login_import_idcard_2,login_login_password_2,login_login_srue_password_2;
    private Button code_btn,sure_btn;
    private MainPresenter presenter;
    private String captcha;
    private String member_id,phone_code,phone_xing;
    private Intent intent;
    private JtFtConvert jtFtConvert=new JtFtConvert();
    private LinearLayout wechat_back;

    @Override
    public void addLayout() {
        setContentView(R.layout.wechat_login_activity);
    }

    @Override
    public void initView() {
        showDialog("授权登录成功，请您完善信息。");
        presenter =new MainPresenter(this,this);
        re_name_2= (RelativeLayout) findViewById(R.id.re_name_2);
        re_code_2= (RelativeLayout) findViewById(R.id.re_code_2);
        re_phone_2= (RelativeLayout) findViewById(R.id.re_phone_2);
        re_idcard_2= (RelativeLayout) findViewById(R.id.re_idcard_2);
        re_login_password_2= (RelativeLayout) findViewById(R.id.re_login_password_2);
        re_login_srue_password_2= (RelativeLayout) findViewById(R.id.re_login_srue_password_2);
        login_import_name_2= (EditText) findViewById(R.id.login_import_name_2);
        login_import_code_2= (EditText) findViewById(R.id.login_import_code_2);
        login_import_phone_2= (EditText) findViewById(R.id.login_import_phone_2);
        login_import_idcard_2= (EditText) findViewById(R.id.login_import_idcard_2);
        login_login_password_2= (EditText) findViewById(R.id.login_login_password_2);
        login_login_srue_password_2= (EditText) findViewById(R.id.login_login_srue_password_2);
        progress_bar_wanshan= (RelativeLayout) findViewById(R.id.progress_bar_wanshan);
        wechat_back= (LinearLayout) findViewById(R.id.wechat_back);
        wechat_back.setOnClickListener(this);
        code_btn= (Button) findViewById(R.id.code_btn);
        code_btn.setOnClickListener(this);
        sure_btn= (Button) findViewById(R.id.sure_btn);
        sure_btn.setOnClickListener(this);
        if(!TextUtils.isEmpty(getIntent().getStringExtra("member_id"))){
            member_id=getIntent().getStringExtra("member_id");
        }
    }

    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("^1[3|4|5|7|8][0-9]{9}$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.code_btn:
                if(!isNumeric(login_import_phone_2.getText().toString())){
                    ToastUtil.showToast(Wechat_LoginActivity.this,"请输入正确的电话号码");
                }else {
                    Map<String,String> map = new HashMap<>();
                    map.put("mobile", login_import_phone_2.getText().toString());
                    map.put("secret", CreateMD5.getMd5(login_import_phone_2.getText().toString()+"z!l@z#j$"));
                    presenter.postMap(SystemConstant.UserConstant.USER_VERIFICATION,map);
                }
                break;
            case R.id.sure_btn:
                if(TextUtils.isEmpty(login_import_phone_2.getText().toString())&&!isNumeric(login_import_phone_2.getText().toString())){
                    ToastUtil.showToast(Wechat_LoginActivity.this,"请输入您的手机号码");
                    return;
                }
                if(TextUtils.isEmpty(login_import_name_2.getText().toString())){
                    ToastUtil.showToast(Wechat_LoginActivity.this,"请输入您的姓名");
                    return;
                }
                if(!isNameric(login_import_name_2.getText().toString())){
                    ToastUtil.showToast(Wechat_LoginActivity.this,"姓名必须为简体中文");
                    return;
                }
                if(login_import_name_2.getText().toString().length()<2){
                    ToastUtil.showToast(Wechat_LoginActivity.this,"姓名不得少于两个字");
                    return;
                }
                if(login_import_name_2.getText().toString().length()>4){
                    ToastUtil.showToast(Wechat_LoginActivity.this,"姓名不得大于四个字");
                    return;
                }
                login_import_name_2.setText(jtFtConvert.convert(login_import_name_2.getText().toString(),0));
                if(TextUtils.isEmpty(login_import_idcard_2.getText().toString())){
                    ToastUtil.showToast(Wechat_LoginActivity.this,"请输入您的身份证号码");
                    return;
                }
                Pattern pattern = Pattern.compile(SystemConstant.PublicConstant.id_regular);
                Matcher matcher = pattern.matcher(login_import_idcard_2.getText().toString());
                boolean b = matcher.matches();
                if(!b){
                    ToastUtil.showToast(Wechat_LoginActivity.this,"身份证号输入不正确");
                    return;
                }else {
                }
                if(TextUtils.isEmpty(login_login_password_2.getText().toString())){
                    ToastUtil.showToast(Wechat_LoginActivity.this,"请输入您的密码");
                    return;
                }
                if(TextUtils.isEmpty(login_login_srue_password_2.getText().toString())){
                    ToastUtil.showToast(Wechat_LoginActivity.this,"请确认您的密码");
                    return;
                }
                    if(TextUtils.isEmpty(login_import_code_2.getText().toString())){
                        ToastUtil.showToast(Wechat_LoginActivity.this,"验证码不能为空");
                       return;
                    }
                    if(!login_import_code_2.getText().toString().equals(captcha)){
                       ToastUtil.showToast(Wechat_LoginActivity.this,"验证码不匹配");
                       return;
                    }
                if(!login_login_password_2.getText().toString().equals(login_login_srue_password_2.getText().toString())){
                    ToastUtil.showToast(Wechat_LoginActivity.this,"两次输入的密码不一致");
                    return;
                }
                if (!phone_code.equals(login_import_phone_2.getText().toString())) {
                    ToastUtil.showToast(Wechat_LoginActivity.this,"输入手机号与验证过手机号不一致");
                    return;
                }
                Map<String,String> map = new HashMap<>();
                map.put("mobile",login_import_phone_2.getText().toString());
                map.put("member_id",member_id);
                map.put("card_id",login_import_idcard_2.getText().toString());
                map.put("password",login_login_password_2.getText().toString());
                map.put("name",login_import_name_2.getText().toString());
                map.put("platform", "1");
                map.put("status","1");
                map.put("secret",CreateMD5.getMd5(login_import_idcard_2.getText().toString()+member_id+login_import_phone_2.getText().
                        toString()+login_import_name_2.getText().toString()+login_login_password_2.getText().toString()+"1"+"1"+"z!l@z#j$"));
                    presenter.wodes(SystemConstant.UserConstant.USER_APP_REGISTER,map);
                progress_bar_wanshan.setVisibility(View.VISIBLE);
                break;
            case R.id.wechat_back:
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
                JSONObject json = JSONObject.fromObject(s);
                if(json.getString("success").equals("true")){
                    ToastUtil.showToast(Wechat_LoginActivity.this,"发送成功");
                    MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000,code_btn);
                    myCountDownTimer.start();
                    captcha=json.getString("captcha");
                    phone_code=json.getString("mobile");
                }else {
                    ToastUtil.showToast(Wechat_LoginActivity.this,json.getString("msg"));
                }
            }
        });
    }

    @Override
    public void postViews(final String s) {
        Log.e("微信登录",s);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")) {
//                    ToastUtil.showToast(Wechat_LoginActivity.this,jsonObject.getString("msg"));
                    JSONObject json = JSONObject.fromObject(jsonObject.getString("data"));
                    BPApplication.getInstance().init(json.getString("member_id"),json.getString("name"),
                            json.getString("card_id"),json.getString("mobile"),json.getString("banlance"),
                            json.getString("headimgurl"),json.getString("token"),json.getString("join_time"),
                            true,json.getString("point"),json.getString("level"),json.getString("love_code")
                            ,json.getString("signer"),json.getString("pay_password"));;
                    BPApplication.getInstance().exits(Wechat_LoginActivity.this);
                    BPApplication.getInstance().setTitle("1");
                    progress_bar_wanshan.setVisibility(View.GONE);
                    intent = new Intent(Wechat_LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    ToastUtil.showToast(Wechat_LoginActivity.this,"登录成功");
                }else {
                    progress_bar_wanshan.setVisibility(View.GONE);
                    if(jsonObject.getString("status").equals("1")){
                        JSONObject jsonObjects = JSONObject.fromObject(jsonObject.getString("data"));
                        if(!TextUtils.isEmpty(jsonObjects.getString("mobile")) && jsonObjects.getString("mobile").length() > 6 ){
                            StringBuilder sb  =new StringBuilder();
                            for (int i = 0; i < jsonObjects.getString("mobile").length(); i++) {
                                char c = jsonObjects.getString("mobile").charAt(i);
                                if (i >= 3 && i <= 6) {
                                    sb.append('*');
                                } else {
                                    sb.append(c);
                                }
                            }
                            phone_xing=sb.toString();
                        }
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Wechat_LoginActivity.this);
                        builder.setTitle("温馨提示");
                        builder.setMessage("您的身份证号已被注册，请使用手机号码"+phone_xing+"进行登陆，若有疑问请拨打电话:028-96552咨询。");
                        builder.setCancelable(false);
                        builder.setPositiveButton("确定",null);
                        builder.setIcon(R.drawable.ic_launcher);
                        builder.show();
                    }else {

                    }
                }
            }
        });
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
              ToastUtil.showToast(Wechat_LoginActivity.this,"网络异常，请稍后再试");
              progress_bar_wanshan.setVisibility(View.GONE);
          }
      });
    }

    @Override
    public void imgView(Bitmap bitmap) {

    }

    private void showDialog(String str) {
  /*
  这里使用了 android.support.v7.app.AlertDialog.Builder
  可以直接在头部写 import android.support.v7.app.AlertDialog
  那么下面就可以写成 AlertDialog.Builder
  */
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("小助温馨提示");
        builder.setMessage(str);
        builder.setCancelable(false);
        builder.setPositiveButton("确定",null);
        builder.setIcon(R.drawable.ic_launcher);
        builder.show();
    }

    public boolean isNameric(String str){
        Pattern pattern = Pattern.compile("^[\\u4E00-\\u9FA5]+$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}

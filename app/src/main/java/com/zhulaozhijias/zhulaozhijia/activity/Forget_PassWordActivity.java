package com.zhulaozhijias.zhulaozhijia.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.MyCountDownTimer;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by asus on 2017/9/21.
 */

public class Forget_PassWordActivity extends BaseActivity implements View.OnClickListener ,MainView{
    private Button yanzhengma_btn,login_btn;
    private MainPresenter presenter;
    private EditText forget_password_import_phone,yanzheng_import_text,login_import_dlpassword,login_import_qrpassword;
    private ToastUtil toastUtil;
    private Map<String, String> map = new HashMap<>();
    private String captcha;


    @Override
    public void addLayout() {
        setContentView(R.layout.forget_password_activity);
    }

    @Override
    public void initView() {
        presenter = new MainPresenter(Forget_PassWordActivity.this, this);
        yanzhengma_btn=(Button)findViewById(R.id.yanzhengma_btn);
        yanzhengma_btn.setOnClickListener(this);
        forget_password_import_phone= (EditText) findViewById(R.id.forget_password_import_phone);
        yanzheng_import_text= (EditText) findViewById(R.id.yanzheng_import_text);
        login_btn= (Button) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(this);
        login_import_dlpassword= (EditText) findViewById(R.id.login_import_dlpassword);
        login_import_qrpassword= (EditText) findViewById(R.id.login_import_qrpassword);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.yanzhengma_btn:
                String phone=forget_password_import_phone.getText().toString();
                if(!isNumeric(phone)){
                    toastUtil.showToast(Forget_PassWordActivity.this,"请输入正确的电话号码");
                }else {
                    map.put("mobile", phone);
                    map.put("secret", CreateMD5.getMd5(phone+"z!l@z#j$"));
                    presenter.postMap(SystemConstant.UserConstant.USER_VERIFICATION,map);
                }
                break;
            case R.id.login_btn:
                if(forget_password_import_phone.getText().toString().length()!=11){
                    toastUtil.showToast(Forget_PassWordActivity.this,"手机号码输入有误");
                    return;
                }
                if(!yanzheng_import_text.getText().toString().equals(captcha)){
                    toastUtil.showToast(Forget_PassWordActivity.this,"验证码错误");
                    return;
                }
                if(login_import_dlpassword.getText().toString().equals(login_import_qrpassword)){
                    toastUtil.showToast(Forget_PassWordActivity.this,"两次输入的密码不一致");
                    return;
                }
                Map<String ,String > map = new HashMap<>();
                map.put("mobile",forget_password_import_phone.getText().toString());
                map.put("password",login_import_dlpassword.getText().toString());
                map.put("secret",CreateMD5.getMd5(forget_password_import_phone.getText().toString()+login_import_dlpassword.getText().toString()+"z!l@z#j$"));
                presenter.wodes(SystemConstant.UserConstant.USER_SER_PASSWORD,map);
                break;
        }
    }

    @Override
    public void getView(final String s) {

    }

    @Override
    public void postView(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject json = JSONObject.fromObject(s);
                if(json.getString("success").equals("true")){
                    toastUtil.showToast(Forget_PassWordActivity.this,"发送成功");
                    MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000,yanzhengma_btn);
                    myCountDownTimer.start();
                    captcha=json.getString("captcha");
                }else {
                    toastUtil.showToast(Forget_PassWordActivity.this,json.getString("msg"));
                }
            }
        });

    }

    @Override
    public void postViews(final String s) {
           Log.e("dsadas",s);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject =JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")){
                    toastUtil.showToast(Forget_PassWordActivity.this,jsonObject.getString("msg"));
                    finish();
                }else {
                    toastUtil.showToast(Forget_PassWordActivity.this,jsonObject.getString("msg"));
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

    }

    @Override
    public void imgView(Bitmap bitmap) {

    }

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SystemConstant.UserConstant.YANZHENG_ERROR:
//                    toastUtil.showToast(Forget_PassWordActivity.this, msg.what);
                    ToastUtil.showToast(Forget_PassWordActivity.this,(String)msg.obj);
                    break;

            }
        }
    };

    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("^1[3|4|5|7|8][0-9]{9}$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}

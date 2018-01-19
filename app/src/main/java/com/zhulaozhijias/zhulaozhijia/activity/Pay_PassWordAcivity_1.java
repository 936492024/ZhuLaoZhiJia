package com.zhulaozhijias.zhulaozhijia.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
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

/**
 * Created by asus on 2017/9/29.
 */

public class Pay_PassWordAcivity_1 extends BaseActivity implements View.OnClickListener ,MainView{
    private LinearLayout mine_paypassword_1_back;
    private Button mine_paypassword_1_btn,paypassword_yanzhangma_btn;
    private Intent intent;
    private TextView pay_phone;
    private MainPresenter mainPresenter;
    private String getmobile;
    private ToastUtil toastUtil;
    private EditText yanzhengma_edittext;
    private String captcha;//服务器验证码的获取
    @Override
    public void addLayout() {
        setContentView(R.layout.pay_password_acivity_1);
    }

    @Override
    public void initView() {
        mainPresenter=new MainPresenter(this,this);
        mine_paypassword_1_back= (LinearLayout) findViewById(R.id.mine_paypassword_1_back);
        mine_paypassword_1_back.setOnClickListener(this);
        mine_paypassword_1_btn= (Button) findViewById(R.id.mine_paypassword_1_btn);
        mine_paypassword_1_btn.setOnClickListener(this);
        pay_phone= (TextView) findViewById(R.id.pay_phone);
        paypassword_yanzhangma_btn= (Button) findViewById(R.id.paypassword_yanzhangma_btn);
        paypassword_yanzhangma_btn.setOnClickListener(this);
        yanzhengma_edittext= (EditText) findViewById(R.id.yanzhengma_edittext);
        getmobile =BPApplication.getInstance().getMobile();
        pay_phone.setText(getmobile);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_paypassword_1_back:
                finish();
                break;
            case R.id.mine_paypassword_1_btn:
                if(!TextUtils.isEmpty(yanzhengma_edittext.getText().toString())){
                    if(captcha.equals(yanzhengma_edittext.getText().toString())){
                        intent=new Intent(Pay_PassWordAcivity_1.this,Pay_PassWordAcivity_2.class);
                        startActivity(intent);
                    }else {
                        toastUtil.showToast(Pay_PassWordAcivity_1.this,"验证码不匹配");
                    }
                }else {
                    toastUtil.showToast(Pay_PassWordAcivity_1.this,"验证码不能为空");
                }
                break;
            case R.id.paypassword_yanzhangma_btn:
                Map<String,String> map = new HashMap<>();
                map.put("mobile", getmobile);
                map.put("secret", CreateMD5.getMd5(getmobile+"z!l@z#j$"));
                mainPresenter.postMap(SystemConstant.UserConstant.USER_VERIFICATION,map);
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
                    captcha=json.getString("captcha");
                    toastUtil.showToast(Pay_PassWordAcivity_1.this,"发送成功");
                    MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000,paypassword_yanzhangma_btn);
                    myCountDownTimer.start();
                }else {
                    toastUtil.showToast(Pay_PassWordAcivity_1.this,json.getString("msg"));
                }
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

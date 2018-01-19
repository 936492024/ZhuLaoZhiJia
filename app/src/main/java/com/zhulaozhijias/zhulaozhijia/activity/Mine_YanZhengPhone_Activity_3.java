package com.zhulaozhijias.zhulaozhijia.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
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
 * Created by asus on 2017/10/5.
 */

public class Mine_YanZhengPhone_Activity_3 extends BaseActivity implements View.OnClickListener ,MainView{
    private LinearLayout mine_yanzheng_phone_3_back;
    private Button jiebang_yanzhangma_btn,yanzheng_wancheng;
    private EditText edittext_jiebang_phone;
    private MainPresenter mainPresenter;
    private ToastUtil toastUtil;
    private String captcha;//服务器验证码的获取
    private TextView code_text;
    @Override
    public void addLayout() {
        setContentView(R.layout.mine_yanzhengphone_activity_3);
    }

    @Override
    public void initView() {
        mainPresenter=new MainPresenter(this,this);
        mine_yanzheng_phone_3_back= (LinearLayout) findViewById(R.id.mine_yanzheng_phone_3_back);
        mine_yanzheng_phone_3_back.setOnClickListener(this);
        jiebang_yanzhangma_btn= (Button) findViewById(R.id.jiebang_yanzhangma_btn);
        jiebang_yanzhangma_btn.setOnClickListener(this);
        edittext_jiebang_phone= (EditText) findViewById(R.id.edittext_jiebang_phone);
        yanzheng_wancheng= (Button) findViewById(R.id.yanzheng_wancheng);
        yanzheng_wancheng.setOnClickListener(this);
        code_text= (TextView) findViewById(R.id.code_text);
        code_text.setText("点击获取验证码,我们会给您的手机号码"+getIntent().getStringExtra("phone")+"发送了一条短信");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_yanzheng_phone_3_back:
                finish();
                break;
            case R.id.jiebang_yanzhangma_btn:
                    Map<String,String> map = new HashMap<>();
                    map.put("mobile", "18328008870");
                    map.put("secret", CreateMD5.getMd5("18328008870"+"z!l@z#j$"));
                    mainPresenter.postMap(SystemConstant.UserConstant.USER_VERIFICATION,map);
                break;
            case R.id.yanzheng_wancheng:
                if(!TextUtils.isEmpty(edittext_jiebang_phone.getText().toString())){
                    if(captcha.equals(edittext_jiebang_phone.getText().toString())){
                        toastUtil.showToast(Mine_YanZhengPhone_Activity_3.this,"修改成功");
                        Intent intent  = new Intent(Mine_YanZhengPhone_Activity_3.this,MainActivity.class);
                        intent.putExtra("Mine_YanZhengPhone_Activity_3","Mine_YanZhengPhone_Activity_3");
                        BPApplication.getInstance().setTitle("0");
                        startActivity(intent);
                        finish();
                    }else {
                        toastUtil.showToast(Mine_YanZhengPhone_Activity_3.this,"验证码错误");
                    }
                }else {
                    toastUtil.showToast(Mine_YanZhengPhone_Activity_3.this,"验证码为空");
                }
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
                    toastUtil.showToast(Mine_YanZhengPhone_Activity_3.this,"发送成功");
                    MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000,jiebang_yanzhangma_btn);
                    myCountDownTimer.start();
                }else {
                    toastUtil.showToast(Mine_YanZhengPhone_Activity_3.this,json.getString("msg"));
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

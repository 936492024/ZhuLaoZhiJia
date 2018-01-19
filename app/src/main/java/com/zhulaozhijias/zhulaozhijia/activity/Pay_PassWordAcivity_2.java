package com.zhulaozhijias.zhulaozhijia.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.PayPwdEditText;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by asus on 2017/9/29.
 */

public class Pay_PassWordAcivity_2 extends BaseActivity implements View.OnClickListener,MainView{
    private LinearLayout mine_paypassword_back;
    private ToastUtil toastUtil;
    private PayPwdEditText password_edittext;
    private PayPwdEditText password_edittexts;
    private String password_1;
    private String password_2;
    private Button pay_password_ok;
    private MainPresenter mainPresenter;
    private ProgressBar pay_set_progress_bar;
    @Override
    public void addLayout() {
        setContentView(R.layout.pay_password_acivity_2);
    }

    @Override
    public void initView() {
        mainPresenter=new MainPresenter(this,this);
        mine_paypassword_back= (LinearLayout) findViewById(R.id.mine_paypassword_back);
        mine_paypassword_back.setOnClickListener(this);
        pay_set_progress_bar= (ProgressBar) findViewById(R.id.pay_set_progress_bar);
        password_edittext= (PayPwdEditText) findViewById(R.id.password_edittext);
        password_edittexts= (PayPwdEditText) findViewById(R.id.password_edittexts);
        pay_password_ok= (Button) findViewById(R.id.pay_password_ok);
        pay_password_ok.setOnClickListener(this);
        Pay_PassWordAcivity_2.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        password_edittext.initStyle(R.drawable.edit_num_bg, 6, 0.33f, R.color.color999999, R.color.color999999, 20);
        password_edittext.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {
                password_1=str;
                toastUtil.showToast(Pay_PassWordAcivity_2.this,"请再次输入");
            }
        });

        password_edittexts.initStyle(R.drawable.edit_num_bg, 6, 0.33f, R.color.color999999, R.color.color999999, 20);
        password_edittexts.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {
                password_2=str;
//
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
          case R.id.mine_paypassword_back:
            finish();
            break;
            case R.id.pay_password_ok:
                pay_set_progress_bar.setVisibility(View.VISIBLE);
                if(password_1.equals(password_2)){
                    Map<String ,String> map = new HashMap<>();
                    map.put("member_id",BPApplication.getInstance().getMember_Id());
                    map.put("password",password_1);
                    map.put("secret", CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+password_1+"z!l@z#j$"));
                    mainPresenter.postMap(SystemConstant.GEREN_ZHONGXIN.Mine_SetPWD,map);
                }else {
                    toastUtil.showToast(Pay_PassWordAcivity_2.this,"两次输入的密码不一致");
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
                JSONObject jsonObject = JSONObject.fromObject(s);
                if (jsonObject.getString("success").equals("true")){
                    toastUtil.showToast(Pay_PassWordAcivity_2.this,"设置成功");
                    pay_set_progress_bar.setVisibility(View.VISIBLE);
                    BPApplication.getInstance().setPay_password("1");
                    Intent intent = new Intent(Pay_PassWordAcivity_2.this,MainActivity.class);
                    intent.putExtra("grxx","1");
                    BPApplication.getInstance().setTitle("0");
                    startActivity(intent);
                    finish();
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

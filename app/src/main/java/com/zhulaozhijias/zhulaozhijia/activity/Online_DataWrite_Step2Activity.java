package com.zhulaozhijias.zhulaozhijia.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by asus on 2017/9/20.
 */

public class Online_DataWrite_Step2Activity extends BaseActivity implements View.OnClickListener ,MainView{
    private Button step_btn2;
    private Intent intent;
    private String notice_uid;
    private EditText event_location,reason,detail,money;
    private MainPresenter mainPresenter;
    private ToastUtil toastUtil;
    private String num;
    private LinearLayout huzhu_plan_2_back;
    @Override
    public void addLayout() {
        setContentView(R.layout.online_datawrite_step2_activity);
    }

    @Override
    public void initView() {
        mainPresenter= new MainPresenter(this,this);
        step_btn2= (Button) findViewById(R.id.step_btn2);
        step_btn2.setOnClickListener(this);
        event_location= (EditText) findViewById(R.id.event_location);
        reason= (EditText) findViewById(R.id.reason);
        detail= (EditText) findViewById(R.id.detail);
        money= (EditText) findViewById(R.id.money);
        huzhu_plan_2_back= (LinearLayout) findViewById(R.id.huzhu_plan_2_back);
        huzhu_plan_2_back.setOnClickListener(this);
        notice_uid = getIntent().getStringExtra("notice_uid");
        num=getIntent().getStringExtra("number");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.step_btn2:
                Map<String,String> map = new HashMap<>();
                if(TextUtils.isEmpty(event_location.getText().toString())){
                    toastUtil.showToast(Online_DataWrite_Step2Activity.this,"请输入事件地点");
                    return;
                }
                if(TextUtils.isEmpty(reason.getText().toString())){
                    toastUtil.showToast(Online_DataWrite_Step2Activity.this,"请输入原因");
                    return;
                }
                if(TextUtils.isEmpty(detail.getText().toString())){
                    toastUtil.showToast(Online_DataWrite_Step2Activity.this,"请输入事件详细情况");
                    return;
                }
                if(TextUtils.isEmpty(money.getText().toString())){
                    toastUtil.showToast(Online_DataWrite_Step2Activity.this,"请输入求助金额");
                    return;
                }
                map.put("steps","2");
                map.put("notice_uid",notice_uid);
                map.put("event_location",event_location.getText().toString());
                map.put("reason",reason.getText().toString());
                map.put("detail",detail.getText().toString());
                map.put("money",money.getText().toString());
                mainPresenter.postMap(SystemConstant.HuZhuPlan.NOTICE_NOTICE,map);

                break;
            case R.id.huzhu_plan_2_back:
                finish();
                break;
        }
    }

    @Override
    public void getView(String s) {

    }

    @Override
    public void postView(final String s) {
        Log.e("zsdsad",s);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")){
                    intent=new Intent(Online_DataWrite_Step2Activity.this,Online_DataWrite_Step3Activity.class);
                    intent.putExtra("notice_uid",jsonObject.getString("notice_uid"));
                    intent.putExtra("num",num);
                    startActivity(intent);
                }else {
                    toastUtil.showToast(Online_DataWrite_Step2Activity.this,jsonObject.getString("msg"));
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

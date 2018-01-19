package com.zhulaozhijias.zhulaozhijia.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.MyRadioGroup;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by asus on 2017/9/20.
 */

public class Online_DataWrite_Step3Activity extends BaseActivity implements View.OnClickListener,MainView{
    private MyRadioGroup radiogroup_1,radiogroup_2,radiogroup_3;
    private MainPresenter mainPresenter;
    private LinearLayout huzhu_plan_3_back;
    private EditText edittext_1,edittext_2,edittext_3,edittext_4,editText_5;
    private String result_1="无";
    private String result_2="无";
    private String result_3="无";
    private Button sure_onload;
    private String notice_uid;
    private String select1,select2,select3;
    private ToastUtil toastUtil;
    private String num;
    @Override
    public void addLayout() {
        setContentView(R.layout.online_datawrite_step3_activity);
    }

    @Override
    public void initView() {
        mainPresenter=new MainPresenter(this,this);
        radiogroup_1= (MyRadioGroup) findViewById(R.id.radiogroup_1);
        radiogroup_2 = (MyRadioGroup) findViewById(R.id.radiogroup_2);
        radiogroup_3= (MyRadioGroup) findViewById(R.id.radiogroup_3);
        edittext_1= (EditText) findViewById(R.id.edittext_1);
        edittext_2= (EditText) findViewById(R.id.edittext_2);
        edittext_3= (EditText) findViewById(R.id.edittext_3);
        edittext_4= (EditText) findViewById(R.id.edittext_4);
        editText_5= (EditText) findViewById(R.id.editText_5);
        sure_onload= (Button) findViewById(R.id.sure_onload);
        sure_onload.setOnClickListener(this);
        huzhu_plan_3_back= (LinearLayout) findViewById(R.id.huzhu_plan_3_back);
        huzhu_plan_3_back.setOnClickListener(this);
        if(TextUtils.isEmpty(getIntent().getStringExtra("notice_uid"))&&TextUtils.isEmpty(getIntent().getStringExtra("notice_uid"))){
            notice_uid = getIntent().getStringExtra("notice_uid");
            num=getIntent().getStringExtra("num");
        }
        if(result_1.equals("无")){
            edittext_1.setEnabled(false);
        }else {
            edittext_1.setEnabled(true);
        }
        if(result_2.equals("无")){
            edittext_2.setEnabled(false);

        }else {
            edittext_2.setEnabled(true);

        }
        if(result_3.equals("无")){
            edittext_3.setEnabled(false);
        }else {
            edittext_3.setEnabled(true);

        }
        radiogroup_1.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                result_1 = radioButton.getText().toString();
                if(result_1.equals("无")){
                    edittext_1.setEnabled(false);
                }else {
                    edittext_1.setEnabled(true);
                }
            }
        });
        radiogroup_2.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                result_2 = radioButton.getText().toString();
                if(result_2.equals("无")){
                    edittext_2.setEnabled(false);
                }else {
                    edittext_2.setEnabled(true);
                }
            }
        });
        radiogroup_3.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                result_3 = radioButton.getText().toString();
                if(result_3.equals("无")){
                    edittext_3.setEnabled(false);
                }else {
                    edittext_3.setEnabled(true);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.huzhu_plan_3_back:
                finish();
                break;
            case R.id.sure_onload:
                Map<String,String> map = new HashMap<>();
                if(!result_1.equals("无")){
                    if(TextUtils.isEmpty(edittext_1.getText().toString())){
                        toastUtil.showToast(Online_DataWrite_Step3Activity.this,"请输入其他投保情况");
                        return;
                    }
                }
                if(!result_2.equals("无")){
                    if(TextUtils.isEmpty(edittext_2.getText().toString())){
                        toastUtil.showToast(Online_DataWrite_Step3Activity.this,"请输入其他途径或平台");
                        return;
                    }
                }
                if(!result_3.equals("无")){
                    if(TextUtils.isEmpty(edittext_3.getText().toString())){
                        toastUtil.showToast(Online_DataWrite_Step3Activity.this,"请输入其他理赔或平台");
                        return;
                    }
                }

                if(TextUtils.isEmpty(edittext_4.getText().toString())){
                    toastUtil.showToast(Online_DataWrite_Step3Activity.this,"请输入社区街道电话");
                    return;
                }
                if(TextUtils.isEmpty(editText_5.getText().toString())){
                    toastUtil.showToast(Online_DataWrite_Step3Activity.this,"请输入其他理赔或平台");
                    return;
                }
                map.put("steps","3");
                map.put("notice_uid",notice_uid);
                map.put("social",edittext_1.getText().toString());
                map.put("platform",edittext_2.getText().toString());
                map.put("platform_other",edittext_3.getText().toString());
                map.put("street_tel",edittext_4.getText().toString());
                map.put("police",editText_5.getText().toString());
                mainPresenter.postMap(SystemConstant.HuZhuPlan.NOTICE_NOTICE,map);
                break;
        }
    }

    @Override
    public void getView(String s) {

    }

    @Override
    public void postView(final String s) {
        Log.e("ssda",s);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")){
                    Intent intent = new Intent(Online_DataWrite_Step3Activity.this,TiShi_Activity.class);
                    intent.putExtra("nums",num);
                    startActivity(intent);
                }else {
                    toastUtil.showToast(Online_DataWrite_Step3Activity.this,"失败");
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

package com.zhulaozhijias.zhulaozhijia.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.IDCardValidate;
import com.zhulaozhijias.zhulaozhijia.widgets.MyRadioGroup;
import com.zhulaozhijias.zhulaozhijia.widgets.PickerView;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by asus on 2017/9/16.
 */

public class Join_PlanActivity extends BaseActivity implements View.OnClickListener ,MainView{
    private IDCardValidate idCardValidate=new IDCardValidate(this);
    private ToastUtil toastUtil;
    private Button confirm_submit;
    private Intent intent;
    private LayoutInflater inflater;
    private Dialog dialog;
    private RelativeLayout privacy_protection;
    private LinearLayout join_plan_back,benren;
    private CheckBox checkBox_2,checkBox_3,checkBox_1;
    private TextView money_1,money_2,money_3,money_4,money_6,end_plans,text_msg;
    private int i=1;
    private int a=1;
    private int b=1;
    private int sum;
    private MainPresenter mainPresenter;
    private String money100,money200,money300;
    private String money,end_plan,other_money,plan_id,plan_name;
    private  int moneys;
    private EditText benren_name,benren_id;
    private int other_moneys;
    private int all_money;
    private TextView gongyue_dialog,plan_dialog;
    private String relation="本人",relations,radioGroup_result,radioGroup_wallet;
    private TextView relation_2,plan_name_2;
    private JSONArray jsonArray2=new JSONArray();
    private JSONArray jsonArray3=new JSONArray();
    private int flag=1;
    private String data,flag_1;
    private MyRadioGroup radioGroup;
    private SweetAlertDialog pDialog;
    @Override
    public void addLayout() {
        setContentView(R.layout.join_plan_activity);
    }

    @Override
    public void initView() {
        pDialog  = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("加载中...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(true);
        pDialog.show();
        mainPresenter=new MainPresenter(this,this);
        confirm_submit= (Button) findViewById(R.id.confirm_submit);
        confirm_submit.setOnClickListener(this);
        privacy_protection= (RelativeLayout) findViewById(R.id.privacy_protection);
        privacy_protection.setOnClickListener(this);
        join_plan_back= (LinearLayout) findViewById(R.id.join_plan_back);
        join_plan_back.setOnClickListener(this);
        radioGroup= (MyRadioGroup) findViewById(R.id.radioGroup);
        money_1= (TextView) findViewById(R.id.money_1);
        money_2= (TextView) findViewById(R.id.money_2);
        money_3= (TextView) findViewById(R.id.money_3);
        money_4= (TextView) findViewById(R.id.money_4);
        money_6= (TextView) findViewById(R.id.money_6);
        end_plans= (TextView) findViewById(R.id.end_plans);
        benren= (LinearLayout) findViewById(R.id.benren);
        relation_2= (TextView) findViewById(R.id.relation_2);
        relation_2.setOnClickListener(this);
        plan_name_2= (TextView) findViewById(R.id.plan_name_2);
        text_msg= (TextView) findViewById(R.id.text_msg);
//        checkBox_1.setChecked(true);
//        checkBox_2.setChecked(false);
//        checkBox_3.setChecked(false);
        if(!TextUtils.isEmpty(getIntent().getStringExtra("money"))){
            money=getIntent().getStringExtra("money");
            end_plan=getIntent().getStringExtra("end_plan");
            other_money=getIntent().getStringExtra("other_money");
            plan_id=getIntent().getStringExtra("plan_id");
            plan_name=getIntent().getStringExtra("plan_name");
            flag_1=getIntent().getStringExtra("flag");
            moneys = Integer.valueOf(money);
            other_moneys = Integer.valueOf(other_money);
            money_4.setText(other_money+"元");
            all_money=moneys*1+other_moneys;
            money_6.setText(all_money+"元");
            if(flag_1.equals("true")){
                text_msg.setText("");
            }else {
                text_msg.setText("（免费赠送一次健康检测）");
            }
            plan_name_2.setText(plan_name);
        }
        end_plans.setText(end_plan);
        benren_name= (EditText) findViewById(R.id.benren_name);
        benren_id= (EditText) findViewById(R.id.benren_id);
        gongyue_dialog= (TextView) findViewById(R.id.gongyue_dialog);
        gongyue_dialog.setOnClickListener(this);
        plan_dialog= (TextView) findViewById(R.id.plan_dialog);
        plan_dialog.setOnClickListener(this);
        Map<String,String> map = new HashMap<>();
        map.put("member_id",BPApplication.getInstance().getMember_Id());
        map.put("secret",CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"z!l@z#j$"));
        mainPresenter.postMap(SystemConstant.HuZhuPlan.NOTICE_API_XINXI,map);

        relation_2.setText(relation);
        benren_name.setText(BPApplication.getInstance().getName());
        benren_id.setText(BPApplication.getInstance().getCard_Id());
        radioGroup.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                radioGroup_result = radioButton.getText().toString();
                if (radioGroup_result.equals("100元")) {
                    radioGroup_wallet = radioGroup_result;
                    radioGroup_wallet = radioGroup_result;
                    all_money=moneys*1+other_moneys;
                    money_6.setText(all_money+"元");
                } else if (radioGroup_result.equals("200元")) {
                    radioGroup_wallet = radioGroup_result;
                    radioGroup_wallet = radioGroup_result;
                    all_money=moneys*2+other_moneys;
                    money_6.setText(all_money+"元");
                } else {
                    radioGroup_wallet = radioGroup_result;
                    all_money=moneys*3+other_moneys;
                    money_6.setText(all_money+"元");
                }
            }
        });
//        setText();
//        CompoundButton.OnCheckedChangeListener myCheckChangelistener = new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                // TODO Auto-generated method stub
//                //设置TextView的内容显示CheckBox的选择结果
//                setText();
//            }
//        };
//        checkBox_1.setOnCheckedChangeListener(myCheckChangelistener);
//        checkBox_2.setOnCheckedChangeListener(myCheckChangelistener);
//        checkBox_3.setOnCheckedChangeListener(myCheckChangelistener);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.confirm_submit:
                if((TextUtils.isEmpty(relation_2.getText().toString())&&TextUtils.isEmpty(relation_2.getText().toString()))){
                    toastUtil.showToast(Join_PlanActivity.this,"请选择与会员的亲属关系");
                    return;
                }
                if((TextUtils.isEmpty(benren_name.getText().toString()))){
                    toastUtil.showToast(Join_PlanActivity.this,"请填写姓名");
                    return;
                }
                if((TextUtils.isEmpty(benren_id.getText().toString()))){
                    toastUtil.showToast(Join_PlanActivity.this,"请填写身份证");
                    return;
                }
                Pattern pattern = Pattern.compile(SystemConstant.PublicConstant.id_regular);
                Matcher matcher = pattern.matcher(benren_id.getText().toString());
                boolean b = matcher.matches();
                if(!b){
                    toastUtil.showToast(Join_PlanActivity.this,"身份证号输入不规范");
                    return;
                }
                Intent intent = new Intent(Join_PlanActivity.this,Order_PayActivity_2.class);
                intent.putExtra("Join_PlanActivity","Join_PlanActivity");
                intent.putExtra("relative",relation_2.getText().toString());
                intent.putExtra("money",String.valueOf(all_money));
                intent.putExtra("plan_id",plan_id);
                intent.putExtra("plan_name",plan_name);
                intent.putExtra("name",benren_name.getText().toString());
                intent.putExtra("card_id",benren_id.getText().toString());
                intent.putExtra("flag_1",flag_1);
                startActivity(intent);
                break;
            case R.id.privacy_protection:
                join_plan_dialog();
                break;
            case R.id.join_plan_back:
                finish();
                break;
            case R.id.gongyue_dialog:
                Intent intent1 = new Intent(Join_PlanActivity.this,HuZhu_PlanActivity_GongYue_3.class);
                startActivity(intent1);
                break;
            case R.id.plan_dialog:
                Intent intent2 = new Intent(Join_PlanActivity.this,HuZhu_PlanActivity_GongYue_2.class);
                startActivity(intent2);
                break;
            case R.id.relation_2:
                game_clock_pay_dialog();
                break;
        }
    }
//    public void checbox1(){
//        if(i==1) {
//            checkBox_2.setChecked(true);
//            fuqin.setVisibility(View.VISIBLE);
//            i=0;
//        }else if(i==0){
//            checkBox_2.setChecked(false);
//            fuqin.setVisibility(View.GONE);
//            i=1;
//        }
//    }
//    public void checbox2(){
//        if(a==1) {
//            checkBox_3.setChecked(true);
//            muqin.setVisibility(View.VISIBLE);
//            a=0;
//        }else if(a==0){
//            checkBox_3.setChecked(false);
//            muqin.setVisibility(View.GONE);
//            a=1;
//        }
//    }
//    public void checbox3(){
//        if(b==1) {
//            checkBox_1.setChecked(false);
//            benren.setVisibility(View.GONE);
//            b=0;
//        }else if(b==0){
//            checkBox_1.setChecked(true);
//            benren.setVisibility(View.VISIBLE);
//            b=1;
//        }
//    }
//    private void setText(){
//        if (checkBox_1.isChecked()) {
//            sum++;
//        }
//        //如果CbPingpangball被选中，则加入TvResult内容显示
//        if (checkBox_2.isChecked()) {
//            sum++;
//        }
//        //如果CbFootball被选中，则加入TvResult内容显示
//        if (checkBox_3.isChecked()) {
//            sum++;
//        }
//        if(sum==3){
//            all_money=moneys*3+other_moneys;
//            money_6.setText(all_money+"");
//            money_1.setBackgroundResource(R.drawable.rdobten_selecter_2);
//            money_2.setBackgroundResource(R.drawable.rdobten_selecter_2);
//            money_3.setBackgroundResource(R.drawable.red_round_border);
//        }else if(sum==2){
//            all_money=moneys*2+other_moneys;
//            money_6.setText(all_money+"");
//            money200=money_2.getText().toString();
//            money_1.setBackgroundResource(R.drawable.rdobten_selecter_2);
//            money_2.setBackgroundResource(R.drawable.red_round_border);
//            money_3.setBackgroundResource(R.drawable.rdobten_selecter_2);
//        }else if(sum==1){
//            all_money=moneys+other_moneys;
//            money_6.setText(all_money+"");
//            money100=money_1.getText().toString();
//            money_1.setBackgroundResource(R.drawable.red_round_border);
//            money_2.setBackgroundResource(R.drawable.rdobten_selecter_2);
//            money_3.setBackgroundResource(R.drawable.rdobten_selecter_2);
//        }else if(sum==0){
//            money_6.setText(0+"元");
//            money_1.setBackgroundResource(R.drawable.rdobten_selecter_2);
//            money_2.setBackgroundResource(R.drawable.rdobten_selecter_2);
//            money_3.setBackgroundResource(R.drawable.rdobten_selecter_2);
//        }
//        sum=0;
//    }




    public void join_plan_dialog2(){
        inflater = LayoutInflater.from(Join_PlanActivity.this);
        View view = inflater.inflate(R.layout.join_plan_dialog_2,null);
        TextView woyiyuedu= (TextView)view. findViewById(R.id.woyiyuedu);
        dialog = new Dialog(Join_PlanActivity.this);
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

    public void join_plan_dialog3(){
        inflater = LayoutInflater.from(Join_PlanActivity.this);
        View view = inflater.inflate(R.layout.join_plan_dialog_3,null);
        TextView woyiyuedu= (TextView)view. findViewById(R.id.woyiyuedu);
        dialog = new Dialog(Join_PlanActivity.this);
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

    public void join_plan_dialog(){
        inflater = LayoutInflater.from(Join_PlanActivity.this);
        View view = inflater.inflate(R.layout.join_plan_dialog,null);
        TextView woyiyuedu= (TextView)view. findViewById(R.id.woyiyuedu);
        dialog = new Dialog(Join_PlanActivity.this);
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

    @Override
    public void getView(String s) {

    }

    @Override
    public void postView(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pDialog.dismiss();
                JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")){
                    data=jsonObject.getString("data");
                    JSONArray jsonArray = JSONArray.fromObject(data);
                    for(int i=0;i<jsonArray.size();i++){
                        jsonArray2.add(jsonArray.getJSONObject(i));
                    }
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pDialog.dismiss();
                toastUtil.showToast(Join_PlanActivity.this,"网络异常，请稍后再试");
            }
        });

    }

    @Override
    public void imgView(Bitmap bitmap) {

    }

    @SuppressWarnings("static-access")
    public void game_clock_pay_dialog() {
        final LayoutInflater inflater = LayoutInflater.from(Join_PlanActivity.this);
        View view = inflater.inflate(R.layout.sex_select_dialog, null);
        final Dialog dialog = new Dialog(Join_PlanActivity.this);
        final PickerView wv_birth_month= (PickerView) view.findViewById(R.id.wv_birth_month);
        TextView btn_sex_cancel= (TextView) view.findViewById(R.id.btn_sex_cancel);
        TextView btn_sex_sure= (TextView) view.findViewById(R.id.btn_sex_sure);
        List<String> mDatas=new ArrayList<>();
        mDatas.add("本人");
//        mDatas.add("父亲");
//        mDatas.add("母亲");
//        mDatas.add("子女");
//        mDatas.add("配偶");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0xffffff));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);//
        dialog.show();
        // 设置宽度为屏幕的宽度
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width =  (display.getWidth()); // 设置宽度
        lp.height=getResources().getDimensionPixelSize(R.dimen.sex_select_dialog_height);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
//        window.setWindowAnimations(R.style.mainfstyle);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);
        wv_birth_month.setData(mDatas);
        wv_birth_month.setSelected("本人");
        wv_birth_month.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                relation=text;
            }
        });
        btn_sex_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(relation.equals("本人")){
                    relation_2.setText(relation);
                    benren_name.setText(BPApplication.getInstance().getName());
                    benren_id.setText(BPApplication.getInstance().getCard_Id());
                    dialog.cancel();
                    return;
                }
                if(TextUtils.isEmpty(jsonArray2.toString())||jsonArray2.toString().equals("[]")){
                    relation_2.setText(relation);
                    toastUtil.showToast(Join_PlanActivity.this,"请填写姓名和身份证");
                    dialog.cancel();
                      return;
                }
                for(int i=0;i<jsonArray2.size();i++){
                    JSONObject jsonObject = jsonArray2.getJSONObject(i);
                    if(relation.equals(jsonObject.getString("between"))) {
                        if(relation.equals("子女")){
                            flag=1;
                            jsonArray3.add(jsonArray2.getJSONObject(i));
                            if(flag==1&&i==jsonArray2.size()-1){
                                dialog.dismiss();
                                children_dialog();
                                flag=0;
                            }
                        }else {
                            relation_2.setText(jsonObject.getString("between"));
                            benren_name.setText(jsonObject.getString("relatives"));
                            benren_id.setText(jsonObject.getString("card_id"));
                        }
                    }else if(data.indexOf(relation)==-1){
                        relation_2.setText(relation);
                        toastUtil.showToast(Join_PlanActivity.this,"请填写姓名和身份证");
                        benren_name.setText("");
                        benren_id.setText("");
                    }
                }
                dialog.cancel();
            }
        });
        btn_sex_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    @SuppressWarnings("static-access")
    public void children_dialog() {
        final LayoutInflater inflater = LayoutInflater.from(Join_PlanActivity.this);
        View view = inflater.inflate(R.layout.sex_select_dialog, null);
        final Dialog dialog = new Dialog(Join_PlanActivity.this);
        final PickerView wv_birth_month= (PickerView) view.findViewById(R.id.wv_birth_month);
        TextView btn_sex_cancel= (TextView) view.findViewById(R.id.btn_sex_cancel);
        TextView btn_sex_sure= (TextView) view.findViewById(R.id.btn_sex_sure);
        final List<String> mDatas=new ArrayList<>();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0xffffff));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);//
        dialog.show();
        // 设置宽度为屏幕的宽度
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width =  (display.getWidth()); // 设置宽度
        lp.height=getResources().getDimensionPixelSize(R.dimen.sex_select_dialog_height);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
//        window.setWindowAnimations(R.style.mainfstyle);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);
        mDatas.clear();
        for(int i=0;i<jsonArray3.size();i++){
            JSONObject jsonObject =jsonArray3.getJSONObject(i);
            mDatas.add(jsonObject.getString("relatives"));
            wv_birth_month.setData(mDatas);
            wv_birth_month.setSelected(jsonObject.getString("relatives"));
        }
        wv_birth_month.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                relations=text;
            }
        });
        btn_sex_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int j=0;j<jsonArray3.size();j++){
                    JSONObject jsonObject = jsonArray3.getJSONObject(j);
                    if(relations.equals(jsonObject.getString("relatives"))){
                        relation_2.setText(jsonObject.getString("between"));
                        benren_name.setText(jsonObject.getString("relatives"));
                        benren_id.setText(jsonObject.getString("card_id"));
                    }
                }
                dialog.cancel();
                jsonArray3.clear();
            }
        });
        btn_sex_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                jsonArray3.clear();
            }
        });
    }

}

package com.zhulaozhijias.zhulaozhijia.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.ChangeDatePopwindow;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.JtFtConvert;
import com.zhulaozhijias.zhulaozhijia.widgets.LogLog;
import com.zhulaozhijias.zhulaozhijia.widgets.PickerView;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by asus on 2017/9/20.
 */

public class Online_DataWrite_Step1Activity extends BaseActivity implements View.OnClickListener,MainView{
    private LinearLayout birthday_select,sex_select;
    private ToastUtil toastUtil;
    private TextView birthday,sex_selects;
    private Button next_step;
    private Intent intent;
    private String sex ="男";
    private String relation="本人";
    private RelativeLayout relation_diolog;
    private TextView relations;
    private EditText member_names,nation,job,idcard,origin,place,mobile,bank_num,account_holder,account_bank;
    private String sexint;
    private MainPresenter mainPresenter;
    private LinearLayout huzhu_plan_1_backl;
    //身份证正则
    private String id_regular="^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
    private JtFtConvert jtFtConvert=new JtFtConvert();
    @Override
    public void addLayout() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.online_datawrite_step1_activity);
    }
    @Override
    public void initView() {
        mainPresenter=new MainPresenter(this,this);
        birthday_select= (LinearLayout) findViewById(R.id.birthday_select);
        birthday_select.setOnClickListener(this);
        birthday=(TextView) findViewById(R.id.birthday);
        next_step=(Button) findViewById(R.id.next_step);
        next_step.setOnClickListener(this);
        sex_select= (LinearLayout) findViewById(R.id.sex_select);
        sex_select.setOnClickListener(this);
        sex_selects= (TextView) findViewById(R.id.sex_selects);
        relation_diolog= (RelativeLayout) findViewById(R.id.relation_diolog);
        relation_diolog.setOnClickListener(this);
        relations= (TextView) findViewById(R.id.relations);
        member_names= (EditText) findViewById(R.id.member_names);
        nation= (EditText) findViewById(R.id.nation);
        job= (EditText) findViewById(R.id.job);
        idcard= (EditText) findViewById(R.id.idcard);
        origin= (EditText) findViewById(R.id.origin);
        place= (EditText) findViewById(R.id.place);
        mobile= (EditText) findViewById(R.id.mobile);
        bank_num= (EditText) findViewById(R.id.bank_num);
        account_holder= (EditText) findViewById(R.id.account_holder);
        account_bank=(EditText)findViewById(R.id.account_bank);
        huzhu_plan_1_backl= (LinearLayout) findViewById(R.id.huzhu_plan_1_back);
        huzhu_plan_1_backl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.birthday_select:
                selectDate();
                break;
            case R.id.next_step:
                if (TextUtils.isEmpty(member_names.getText().toString())){
                    toastUtil.showToast(Online_DataWrite_Step1Activity.this,"请输入您的姓名");
                    return;
                }
                if(!isNameric(member_names.getText().toString())){
                    toastUtil.showToast(Online_DataWrite_Step1Activity.this,"姓名必须为简体中文");
                    return;
                }
                if(member_names.getText().toString().length()<2){
                    toastUtil.showToast(Online_DataWrite_Step1Activity.this,"姓名不得少于两个字");
                    return;
                }
                if(member_names.getText().toString().length()>4){
                    toastUtil.showToast(Online_DataWrite_Step1Activity.this,"姓名不得大于四个字");
                    return;
                }
                member_names.setText(jtFtConvert.convert(member_names.getText().toString(),0));


                if (TextUtils.isEmpty(sex_selects.getText().toString())){
                    toastUtil.showToast(Online_DataWrite_Step1Activity.this,"请选择您的性别");
                    return;
                }
                if(TextUtils.isEmpty(nation.getText().toString())){
                    toastUtil.showToast(Online_DataWrite_Step1Activity.this,"请输入您的民族");
                    return;
                }
                if(TextUtils.isEmpty(birthday.getText().toString())){
                    toastUtil.showToast(Online_DataWrite_Step1Activity.this,"请输入您的生日");
                    return;
                }
                if(TextUtils.isEmpty(relations.getText().toString())){
                    toastUtil.showToast(Online_DataWrite_Step1Activity.this,"请输入与会员的关系");
                    return;
                }
                if(TextUtils.isEmpty(job.getText().toString())){
                    toastUtil.showToast(Online_DataWrite_Step1Activity.this,"请输入您的职业");
                    return;
                }
                if(TextUtils.isEmpty(idcard.getText().toString())){
                    toastUtil.showToast(Online_DataWrite_Step1Activity.this,"请输入您的身份证号码");
                    return;
                }
                Pattern pattern = Pattern.compile(id_regular);
                Matcher matcher = pattern.matcher(idcard.getText().toString());
                boolean b = matcher.matches();
                if(!b){
                    toastUtil.showToast(Online_DataWrite_Step1Activity.this,"身份证号格式不正确");
                    return;
                }
                if(TextUtils.isEmpty(origin.getText().toString())){
                    toastUtil.showToast(Online_DataWrite_Step1Activity.this,"请输入您的籍贯");
                    return;
                }
                if(TextUtils.isEmpty(place.getText().toString())){
                    toastUtil.showToast(Online_DataWrite_Step1Activity.this,"请输入您的现居住地址");
                    return;
                }
                if(TextUtils.isEmpty(mobile.getText().toString())){
                    toastUtil.showToast(Online_DataWrite_Step1Activity.this,"请输入您的手机号码");
                    return;
                }
                if(TextUtils.isEmpty(bank_num.getText().toString())){
                    toastUtil.showToast(Online_DataWrite_Step1Activity.this,"请输入您的银行卡号");
                    return;
                }
                if(TextUtils.isEmpty(account_holder.getText().toString())){
                    toastUtil.showToast(Online_DataWrite_Step1Activity.this,"请输入开户人");
                    return;
                }
                if(!isNameric(account_holder.getText().toString())){
                    toastUtil.showToast(Online_DataWrite_Step1Activity.this,"姓名必须为简体中文");
                    return;
                }
                if(TextUtils.isEmpty(account_bank.getText().toString())){
                    toastUtil.showToast(Online_DataWrite_Step1Activity.this,"请输入开户银行");
                    return;
                }
                if(sex_selects.getText().toString().equals("男")){
                    sexint="1";
                }else if (sex_selects.getText().toString().equals("女")){
                    sexint="0";
                }
                Map<String ,String> map = new HashMap<>();
                map.put("steps","1");
                map.put("member_name",member_names.getText().toString());
                map.put("member_id",BPApplication.getInstance().getMember_Id());
                map.put("gender",sexint);
                map.put("nation",nation.getText().toString());
                map.put("birth_date",birthday.getText().toString());
                map.put("relationship",relations.getText().toString());
                map.put("occupation",job.getText().toString());
                map.put("card_id",idcard.getText().toString());
                map.put("place",origin.getText().toString());
                map.put("address",place.getText().toString());
                map.put("mobile",mobile.getText().toString());
                map.put("bank_number",bank_num.getText().toString());
                map.put("bank_member",account_holder.getText().toString());
                map.put("bank_name",account_bank.getText().toString());
                LogLog.e("mapmap",map.toString());
                mainPresenter.postMap(SystemConstant.HuZhuPlan.NOTICE_NOTICE,map);

                break;
            case R.id.sex_select:
                qiuzhu_sex_dialog();
                break;
            case R.id.relation_diolog:
                qiuzhu_relation_dialog();
                break;
            case R.id.huzhu_plan_1_back:
                finish();
                break;
        }
    }
    @SuppressWarnings("static-access")
    public void qiuzhu_sex_dialog() {
        final LayoutInflater inflater = LayoutInflater.from(Online_DataWrite_Step1Activity.this);
        View view = inflater.inflate(R.layout.sex_select_dialog, null);
        final Dialog dialog = new Dialog(Online_DataWrite_Step1Activity.this);
        final PickerView wv_birth_month= (PickerView) view.findViewById(R.id.wv_birth_month);
        TextView btn_sex_cancel= (TextView) view.findViewById(R.id.btn_sex_cancel);
        TextView btn_sex_sure= (TextView) view.findViewById(R.id.btn_sex_sure);
        List<String> mDatas=new ArrayList<>();
        mDatas.add("男");
        mDatas.add("女");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0xffffff));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);//
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
        wv_birth_month.setSelected("男");
        wv_birth_month.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                sex=text;
            }
        });
        btn_sex_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sex_selects.setText(sex);
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
    public void qiuzhu_relation_dialog() {
        final LayoutInflater inflater = LayoutInflater.from(Online_DataWrite_Step1Activity.this);
        View view = inflater.inflate(R.layout.sex_select_dialog, null);
        final Dialog dialog = new Dialog(Online_DataWrite_Step1Activity.this);
        final PickerView wv_birth_month= (PickerView) view.findViewById(R.id.wv_birth_month);
        TextView btn_sex_cancel= (TextView) view.findViewById(R.id.btn_sex_cancel);
        TextView btn_sex_sure= (TextView) view.findViewById(R.id.btn_sex_sure);
        List<String> mDatas=new ArrayList<>();
        mDatas.add("本人");
        mDatas.add("父亲");
        mDatas.add("母亲");
        mDatas.add("配偶");
        mDatas.add("子女");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0xffffff));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);//
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
                relations.setText(relation);
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

    private String[] selectDate() {
        final String[] str = new String[10];
        ChangeDatePopwindow mChangeBirthDialog = new ChangeDatePopwindow(
                this);
        mChangeBirthDialog.setDate("2017", "1", "1");
        mChangeBirthDialog.showAtLocation(birthday_select, Gravity.BOTTOM, 0, 0);
        mChangeBirthDialog.setBirthdayListener(new ChangeDatePopwindow.OnBirthListener() {
            @Override
            public void onClick(String year, String month, String day) {
                // TODO Auto-generated method stub
                StringBuilder sb = new StringBuilder();
                sb.append(year.substring(0, year.length() - 1)).append("-").append(month.substring(0, day.length() - 1)).append("-").append(day);
                str[0] = year + "-" + month + "-" + day;
                str[1] = sb.toString();
                birthday.setText(year + "-" + month + "-" + day);
            }
        });
        return str;
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
                    String notice_uid= jsonObject.getString("notice_uid");
                    intent=new Intent(Online_DataWrite_Step1Activity.this,Online_DataWrite_Step2Activity.class);
                    intent.putExtra("notice_uid",notice_uid);
                    intent.putExtra("number",jsonObject.getString("number"));
                    startActivity(intent);
                }else {
                    online_datawrite_dialog(jsonObject.getString("appmsg").replaceAll("<br/>","\n"));
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


    @SuppressWarnings("static-access")
    public void online_datawrite_dialog(String msg) {
        final LayoutInflater inflater = LayoutInflater.from(Online_DataWrite_Step1Activity.this);
        View view = inflater.inflate(R.layout.online_datawrite_dialog, null);
        TextView textView = (TextView) view.findViewById(R.id.sign_text);
        TextView quding_dialog = (TextView) view.findViewById(R.id.quding_dialog);
        textView.setText(msg);
        final Dialog dialog = new Dialog(Online_DataWrite_Step1Activity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);//
        dialog.show();
        // 设置宽度为屏幕的宽度
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width =  getResources().getDimensionPixelSize(R.dimen.heidhhhhh);
        lp.height=getResources().getDimensionPixelSize(R.dimen.heidhhhh);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);
        quding_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
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

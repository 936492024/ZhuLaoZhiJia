package com.zhulaozhijias.zhulaozhijia.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.adpter.Expand_List_Adapter_3;
import com.zhulaozhijias.zhulaozhijia.adpter.ListView_Num_Adapter;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.CustomExpandableListView;
import com.zhulaozhijias.zhulaozhijia.widgets.ListViewForScrollView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by asus on 2017/9/25.
 */

public class HuZhu_Invition extends BaseActivity implements View.OnClickListener ,MainView{
    private TextView shen_phone;
    private RelativeLayout online_data;
    private Intent intent;
//    private CustomExpandableListView huzhu_invition_expandlist;
    private String group [] = {"求助流程","所需资料"};
    private String child [] = new String[2];
    private LinearLayout huzhu_invition_back;
    private ListViewForScrollView listView;
    private MainPresenter mainPresenter;
    private JSONObject jsonObject1;
    private TextView invition_intent,plan_phone_2,text_gruop;
    private LinearLayout wenxintishi;
    private RelativeLayout rt_2;
    private LinearLayout lt_2;
    private ImageView arrow_2;
    private int b=0;
    @Override
    public void addLayout() {
        setContentView(R.layout.huzhu_invition);
    }

    @Override
    public void initView() {
        mainPresenter=new MainPresenter(this,this);
        shen_phone= (TextView) findViewById(R.id.shen_phone);
        shen_phone.setOnClickListener(this);
        invition_intent= (TextView) findViewById(R.id.invition_intent);
        invition_intent.setOnClickListener(this);
        online_data= (RelativeLayout) findViewById(R.id.online_data);
        online_data.setOnClickListener(this);
        wenxintishi= (LinearLayout) findViewById(R.id.wenxintishi);
        wenxintishi.setOnClickListener(this);
        rt_2= (RelativeLayout) findViewById(R.id.rt_2);
        rt_2.setOnClickListener(this);
        lt_2= (LinearLayout) findViewById(R.id.lt_2);
        arrow_2= (ImageView) findViewById(R.id.arrow_2);
        child[0]=getResources().getString(R.string.apply_2);
        child[1] =getResources().getString(R.string.apply_1);
        plan_phone_2= (TextView) findViewById(R.id.plan_phone_2);
        plan_phone_2.setOnClickListener(this);
        text_gruop= (TextView) findViewById(R.id.text_gruop);
        int width = getWindowManager().getDefaultDisplay().getWidth();
//        huzhu_invition_expandlist= (CustomExpandableListView) findViewById(R.id.huzhu_invition_expandlist);
//        huzhu_invition_expandlist.setAdapter(new Expand_List_Adapter_3(HuZhu_Invition.this,group,child));
//        huzhu_invition_expandlist.setIndicatorBounds(width-150, width);
        String clickTxt = "*";
        String clickTxt2 = "注：";
        int startIndex = child[1].indexOf(clickTxt);
        int endIndex = startIndex + clickTxt.length();
        int startIndex2 = child[1].indexOf(clickTxt2);
        int endIndex2 = startIndex2 + clickTxt2.length();
//        setTVColor(child[1],startIndex,endIndex,text_gruop);
        setTVColor(child[1],startIndex2,endIndex2,text_gruop);
        huzhu_invition_back= (LinearLayout) findViewById(R.id.huzhu_invition_back);
        huzhu_invition_back.setOnClickListener(this);
        Map<String,String> map = new HashMap<>();
        map.put("member_id", BPApplication.getInstance().getMember_Id());
        map.put("secret", CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"z!l@z#j$"));
        mainPresenter.postMap(SystemConstant.HuZhuPlan.NOTICE_SEL_STATUS,map);
        listView= (ListViewForScrollView) findViewById(R.id.listView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.shen_phone:
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "028-96552"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.online_data:
                intent = new Intent(HuZhu_Invition.this, Online_DataWrite_Step1Activity.class);
                startActivity(intent);
                break;
            case R.id.huzhu_invition_back:
                finish();
                break;
            case R.id.invition_intent:
                Intent intent2 = new Intent(HuZhu_Invition.this,HuZhu_PlanActivity_GongYue_2.class);
                startActivity(intent2);
                break;
            case R.id.rt_2:
                if(b==0){
                    lt_2.setVisibility(View.VISIBLE);
                    arrow_2.setImageResource(R.mipmap.arrows2);
                    b=1;
                }else {
                    lt_2.setVisibility(View.GONE);
                    arrow_2.setImageResource(R.mipmap.arrowright);
                    b=0;
                }
                break;
            case R.id.plan_phone_2:
                Intent intent4 = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + "028-96552"));
                intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent4);
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
                if(jsonObject.getString("success").equals("true")){
                    JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("date"));
                    JSONArray jsonArray1 = new JSONArray();
                    for (int i=0;i<jsonArray.size();i++){
                        jsonArray1.add(jsonArray.getJSONObject(i));
                    }
                    listView.setAdapter(new ListView_Num_Adapter(HuZhu_Invition.this,jsonArray1));
                }else {
                    wenxintishi.setVisibility(View.GONE);
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

    private void setTVColor(String str , int ch1 , int ch2 , TextView tv){
        SpannableStringBuilder spannable = new SpannableStringBuilder(str);
        spannable.setSpan(new ForegroundColorSpan(Color.RED),ch1,ch2
                , Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tv.setText(spannable);
    }
}

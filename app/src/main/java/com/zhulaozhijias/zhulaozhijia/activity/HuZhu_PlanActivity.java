package com.zhulaozhijias.zhulaozhijia.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.adpter.Expand_List_Adapter;
import com.zhulaozhijias.zhulaozhijia.adpter.Expand_List_Adapter_2;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.Connect_Check;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;

import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

/**
 * Created by asus on 2017/9/15.
 */

public class HuZhu_PlanActivity extends BaseActivity implements View.OnClickListener,MainView{
    private LinearLayout huzhu_plan_back;
    private RelativeLayout invite_share,confirm_join;
    private Intent intent;
    private TextView plan_phone,huzhu_planbaozhang_number,huzhu_planxinzeng_number,huzhu_planhuojuan_number;
    private MainPresenter mainPresenter;
    private ExpandableListView expandableListView;
//  private ExpandableListView expand_list2;
    private ImageView jiaodian;
    private RelativeLayout vip_gongyue,fangai_plan,jiankang_yaoqiu;
    private String money,other_money,end_plan;
    private ToastUtil toastUtil;
    private ArrayList<String> arrayList_problem = new ArrayList<>();
    private ArrayList<String> arrayList2_problem = new ArrayList<>();
    private ArrayList<String> arrayList_rule = new ArrayList<>();
    private ArrayList<String> arrayList_rule_2_title = new ArrayList<>();
    private ArrayList<String> arrayList_rule_2_content = new ArrayList<>();
    private String name,plan_id,plan_name;
    private String flag,msg,isjoin_plan;
    private SweetAlertDialog pDialog;
    @Override
    public void addLayout() {
        setContentView(R.layout.huzhu_planactivity);
    }

    @Override
    public void initView() {
        pDialog  = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("加载中...");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(true);
        pDialog.show();
        mainPresenter= new MainPresenter(this,this);
        invite_share= (RelativeLayout) findViewById(R.id.invite_share);
        invite_share.setOnClickListener(this);
        confirm_join= (RelativeLayout) findViewById(R.id.confirm_join);
        confirm_join.setOnClickListener(this);
        huzhu_plan_back= (LinearLayout) findViewById(R.id.huzhu_plan_back);
        huzhu_plan_back.setOnClickListener(this);
//        jiaodian= (ImageView) findViewById(R.id.jiaodian);
//        jiaodian.requestFocus();
        plan_phone=(TextView)findViewById(R.id.plan_phone);//电话跳转
        plan_phone.setOnClickListener(this);
        name = BPApplication.getInstance().getName();

        expandableListView= (ExpandableListView) findViewById(R.id.expand_list);
//        expand_list2= (ExpandableListView) findViewById(R.id.expand_list2);
//        expandableListView.setGroupIndicator(null);
        int width = getWindowManager().getDefaultDisplay().getWidth();
        expandableListView.setIndicatorBounds(width-150, width);
        setListViewHeightBasedOnChildren(expandableListView);
//        expand_list2.setIndicatorBounds(width-150, width);
//        setListViewHeightBasedOnChildren(expand_list2);
        huzhu_planbaozhang_number= (TextView) findViewById(R.id.huzhu_planbaozhang_number);
        huzhu_planxinzeng_number= (TextView) findViewById(R.id.huzhu_planxinzeng_number);
        huzhu_planhuojuan_number= (TextView) findViewById(R.id.huzhu_planhuojuan_number);
        vip_gongyue= (RelativeLayout) findViewById(R.id.vip_gongyue);
        vip_gongyue.setOnClickListener(this);
        fangai_plan= (RelativeLayout) findViewById(R.id.fangai_plan);
        fangai_plan.setOnClickListener(this);
        jiankang_yaoqiu= (RelativeLayout) findViewById(R.id.jiankang_yaoqiu);
        jiankang_yaoqiu.setOnClickListener(this);
//        plan_phone_2= (TextView) findViewById(R.id.plan_phone_2);
//        plan_phone_2.setOnClickListener(this);
        alldata();
    }

    public void alldata(){
        Map<String,String> map = new HashMap<>();
        map.put("plan_id","1");
        map.put("member_id",BPApplication.getInstance().getMember_Id());
        map.put("secret", CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"1"+"z!l@z#j$"));
        mainPresenter.wodes(SystemConstant.HuZhuPlan.HuZhuPlan_NUMBBR,map);
        if(Connect_Check.getCurrentNetType(HuZhu_PlanActivity.this)==0){
            toastUtil.showToast(HuZhu_PlanActivity.this,"网络繁忙，请检查网络设置");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.invite_share://分享
                if(!checkIsLogined()){
                    return;
                }else {
                    showShare();
                }
                break;
            case R.id.confirm_join://立即加入
                if(TextUtils.isEmpty(BPApplication.getInstance().getMember_Id())){
                    intent = new Intent(HuZhu_PlanActivity.this,LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                if(TextUtils.isEmpty(flag)){
                    return;
                }
                if(isjoin_plan.equals("false")){
                    showDialog();
                }else {
                    intent = new Intent(HuZhu_PlanActivity.this,Join_PlanActivity.class);
                    intent.putExtra("money",money);
                    intent.putExtra("other_money",other_money);
                    intent.putExtra("end_plan",end_plan);
                    intent.putExtra("plan_id",plan_id);
                    intent.putExtra("plan_name",plan_name);
                    intent.putExtra("flag",flag);
                    startActivity(intent);
                }
                break;
            case R.id.huzhu_plan_back:
                finish();
                break;
            case R.id.plan_phone:
                Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + "028-96552"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.vip_gongyue:
                Intent intent1 = new Intent(HuZhu_PlanActivity.this,HuZhu_PlanActivity_GongYue_3.class);
                startActivity(intent1);
                break;
            case R.id.fangai_plan:
                Intent intent2 = new Intent(HuZhu_PlanActivity.this,HuZhu_PlanActivity_GongYue_2.class);
                startActivity(intent2);
                break;
            case R.id.jiankang_yaoqiu:
                Intent intent3 = new Intent(HuZhu_PlanActivity.this,HuZhu_PlanActivity_GongYue_1.class);
                startActivity(intent3);
                break;
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
            @Override
            public void onShare(Platform platform, Platform.ShareParams paramsToShare) {
                if("ShortMessage".equals(platform.getName())){
                    paramsToShare.setText("爱心是一盏灯，照亮别人，温暖自己http://app.sesdf.org/");
                }else if("Wechat".equals(platform.getName())){
                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                    paramsToShare.setTitle(name+"邀请你加入互帮互助基金");
                    paramsToShare.setText("爱心是一盏灯，照亮别人，温暖自己");
                    paramsToShare.setUrl("http://app.sesdf.org/");
                    paramsToShare.setImageUrl("http://oxycohppa.bkt.clouddn.com/logo.jpg");
                }else if("WechatMoments".equals(platform.getName())){
                    paramsToShare.setShareType(Platform.SHARE_WEBPAGE);
                    paramsToShare.setTitle(name+"邀请你加入互帮互助基金");
                    paramsToShare.setUrl("http://app.sesdf.org/");
                    paramsToShare.setImageUrl("http://oxycohppa.bkt.clouddn.com/logo.jpg");
                }else if("SinaWeibo".equals(platform.getName())){
                    paramsToShare.setText("爱心是一盏灯，照亮别人，温暖自己http://app.sesdf.org/");
                    paramsToShare.setImageUrl("http://oxycohppa.bkt.clouddn.com/logo.jpg");
                }else if("QQ".equals(platform.getName())){
                    paramsToShare.setText("爱心是一盏灯，照亮别人，温暖自己");
                    paramsToShare.setTitle(name+"邀请你加入互帮互助基金");
                    paramsToShare.setTitleUrl("http://app.sesdf.org/");
                    paramsToShare.setImageUrl("http://oxycohppa.bkt.clouddn.com/logo.jpg");
                }else if("QZone".equals(platform.getName())){
                    paramsToShare.setTitle(name+"邀请你加入互帮互助基金");
                    paramsToShare.setText("爱心是一盏灯，照亮别人，温暖自己");
                    paramsToShare.setTitleUrl("http://app.sesdf.org/");
                    paramsToShare.setImageUrl("http://oxycohppa.bkt.clouddn.com/logo.jpg");
                }
            }
        });
        //zz
        oks.show(this);
    }

    @Override
    public void getView(String s) {

    }

    @Override
    public void postView(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void postViews(final String s) {
        Log.e("dsadsaddsaa",s);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(pDialog.isShowing()){
                    pDialog.dismiss();
                }
                JSONObject jsonObject = JSONObject.fromObject(s);
                JSONObject jsonObject1 = JSONObject.fromObject(jsonObject.getString("plan"));
                plan_id=jsonObject1.getString("plan_id");
                plan_name=jsonObject1.getString("name");
//                money=jsonObject.getString("money");
                other_money=jsonObject.getString("other_money");
                end_plan=jsonObject.getString("end_plan");
                flag=jsonObject.getString("screen");
                isjoin_plan=jsonObject.getString("is_join_plan");
                if(flag.equals("true")){
                    money=jsonObject1.getString("yes_screen_money");
                    msg=jsonObject1.getString("yes_screen_day")+"天（为防止带病加入）";
                }else {
                    money=jsonObject1.getString("no_screen_money");
                    msg=jsonObject1.getString("no_screen_day")+"天（为防止带病加入）";
                }
                huzhu_planbaozhang_number.setText("50"+end_plan);//已保障人数
                huzhu_planxinzeng_number.setText(jsonObject.getString("new_count"));
                huzhu_planhuojuan_number.setText(jsonObject.getString("service_count"));
                arrayList_problem.add(getResources().getString(R.string.problem_1));
                arrayList_problem.add(getResources().getString(R.string.problem_2));
                arrayList_problem.add(getResources().getString(R.string.problem_3));
                arrayList_problem.add(getResources().getString(R.string.problem_4));
                arrayList2_problem.add("互助介绍");
                arrayList2_problem.add("互助规则");
                arrayList2_problem.add("安全保障");
                arrayList2_problem.add("加入规则");
                Expand_List_Adapter_2 expandListAdapter = new Expand_List_Adapter_2(HuZhu_PlanActivity.this,arrayList_problem,arrayList2_problem);
//        expand_list2.setAdapter(expandListAdapter);
                arrayList_rule.add(getResources().getString(R.string.rule_1));
                arrayList_rule.add(getResources().getString(R.string.rule_2));
                arrayList_rule.add(getResources().getString(R.string.rule_3));
                arrayList_rule.add(getResources().getString(R.string.rule_4));
                arrayList_rule.add(getResources().getString(R.string.rule_5));
                arrayList_rule_2_title.add("事件范围");
                arrayList_rule_2_title.add("最高获取");
                arrayList_rule_2_title.add("捐助规则");
                arrayList_rule_2_title.add("计划要求");
                arrayList_rule_2_title.add("静默期");
                arrayList_rule_2_content.add("胃癌、肝癌等各种癌症");
                arrayList_rule_2_content.add("30万元健康救助金");
                arrayList_rule_2_content.add("单次不超过3元，每年约三百元");
                arrayList_rule_2_content.add("加入计划获得健康救助");
                arrayList_rule_2_content.add(msg);
                Expand_List_Adapter expandListAdapter_rule = new Expand_List_Adapter(HuZhu_PlanActivity.this,arrayList_rule,arrayList_rule_2_title,arrayList_rule_2_content);
                expandableListView.setAdapter(expandListAdapter_rule);
            }
        });
    }

    @Override
    public void postViewss(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {


            }
        });
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
                  if(pDialog.isShowing()){
                      pDialog.dismiss();
                  }
                  toastUtil.showToast(HuZhu_PlanActivity.this,"网络异常，请稍后再试");
              }
          });
    }

    @Override
    public void imgView(Bitmap bitmap) {

    }

    public  void setListViewHeightBasedOnChildren(ExpandableListView listView) {
        //获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   //listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);  //计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight();  //统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //listView.getDividerHeight()获取子项间分隔符占用的高度
        //params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    private boolean checkIsLogined() {
        if (!BPApplication.getInstance().isLogined()) {
            Intent intent = new Intent(HuZhu_PlanActivity.this, LoginActivity.class);
            startActivity(intent);
            return false;
        }
        return true;
    }

    private void showDialog() {
  /*
  这里使用了 android.support.v7.app.AlertDialog.Builder
  可以直接在头部写 import android.support.v7.app.AlertDialog
  那么下面就可以写成 AlertDialog.Builder
  */
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("您已加入该计划，请勿重复加入！");
        builder.setCancelable(false);
        builder.setPositiveButton("确定",null);
        builder.setIcon(R.drawable.ic_launcher);
        builder.show();
    }
}

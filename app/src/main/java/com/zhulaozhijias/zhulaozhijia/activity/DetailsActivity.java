package com.zhulaozhijias.zhulaozhijia.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.adpter.GirdView_XiangQingAdapter;
import com.zhulaozhijias.zhulaozhijia.adpter.GridView_Map_Adapter;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.ListViewForScrollView;
import com.zhulaozhijias.zhulaozhijia.widgets.MyRadioGroup;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;
import com.zhulaozhijias.zhulaozhijia.widgets.eventbus.MainSendEvent;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;

/**
 * Created by asus on 2017/9/14.
 */

public class DetailsActivity extends BaseActivity implements View.OnClickListener,MainView{
    private MainPresenter mainPresenter;
    private ImageView gongshi_item_heading,progress_ima_1;
    private LinearLayout Detail_back,lijibangzhu_btn_2;
    private ListViewForScrollView progress_iam_grid;
    private MyRadioGroup myRadioGroup_1;
    private Button lijibangzhu_btn;
    private String result="1",results;
    private ToastUtil toastUtil;
    private Intent intent;
    private String donated_ids,notice_ids,donated_names;
    private TextView gongshi_item_name,gongshi_item_age,gongshi_item_address,gongshi_item_number
            ,gongshi_item_plan,gongshi_item_join_date,gongshi_start_plan,gongshi_item_money,
            gongshi_shenhe_date,gongshi_item_cailiao,gongshi_item_pass,gongshi_item_date,gongshi_item_sex
    ,gongshi_item_bokuan_date,gongshi_item_process_1,gongshi_item_qishu;
    private TextView problem,intro,reason,gongshi_item_qishu3;
    private LayoutInflater inflater;
    private Dialog dialog;

    @Override
    public void addLayout() {
        setContentView(R.layout.details_activity);
    }

    @Override
    public void initView(){
        EventBus.getDefault().register(this);
        mainPresenter=new MainPresenter(this,this);
        String notice_id = getIntent().getStringExtra("1");//获取的notice_id
        String intent_id = getIntent().getStringExtra("2");//获取的notice_id
        Map<String,String> map = new HashMap<>();
        map.put("notice_id",notice_id);
        map.put("secret", CreateMD5.getMd5(notice_id+"z!l@z#j$"));
        mainPresenter.postMap(SystemConstant.PublicConstant.Public_GONGSHI_ITEM,map);
        mainPresenter.wodes(SystemConstant.PublicConstant.Public_GONGSHI_PROCESS,map);
        mainPresenter.wodess(SystemConstant.PublicConstant.Public_GONGSHI_PHOTO,map);
        gongshi_item_heading= (ImageView) findViewById(R.id.gongshi_item_heading);
        gongshi_item_name= (TextView) findViewById(R.id.gongshi_item_name);
        gongshi_item_age= (TextView) findViewById(R.id.gongshi_item_age);
        gongshi_item_address= (TextView) findViewById(R.id.gongshi_item_address);
        gongshi_item_number= (TextView) findViewById(R.id.gongshi_item_number);
        gongshi_item_plan= (TextView) findViewById(R.id.gongshi_item_plan);
        gongshi_item_join_date= (TextView) findViewById(R.id.gongshi_item_join_date);
        gongshi_item_sex = (TextView) findViewById(R.id.gongshi_item_sex);
        gongshi_item_qishu= (TextView) findViewById(R.id.gongshi_item_qishu);
        gongshi_start_plan= (TextView) findViewById(R.id.gongshi_start_plan);
        gongshi_item_money= (TextView) findViewById(R.id.gongshi_item_money);
        gongshi_shenhe_date= (TextView) findViewById(R.id.gongshi_shenhe_date);
        gongshi_item_cailiao= (TextView) findViewById(R.id.gongshi_item_cailiao);
        gongshi_item_pass= (TextView) findViewById(R.id.gongshi_item_pass);
        gongshi_item_date= (TextView) findViewById(R.id.gongshi_item_date);
        gongshi_item_bokuan_date= (TextView) findViewById(R.id.gongshi_item_bokuan_date);
        gongshi_item_qishu3= (TextView) findViewById(R.id.gongshi_item_qishu3);
        Detail_back= (LinearLayout) findViewById(R.id.Detail_back);
        Detail_back.setOnClickListener(this);
        gongshi_item_process_1= (TextView) findViewById(R.id.gongshi_item_process_1);
        progress_iam_grid= (ListViewForScrollView) findViewById(R.id.progress_iam_grid);
        lijibangzhu_btn= (Button) findViewById(R.id.lijibangzhu_btn);
        lijibangzhu_btn.setOnClickListener(this);
        lijibangzhu_btn_2= (LinearLayout) findViewById(R.id.lijibangzhu_btn_2);
        intro= (TextView) findViewById(R.id.intro);
        reason= (TextView) findViewById(R.id.reason);
        problem= (TextView) findViewById(R.id.problem);
        problem.setOnClickListener(this);
        if(intent_id.equals("map")){
            lijibangzhu_btn_2.setVisibility(View.VISIBLE);
        }else {
            lijibangzhu_btn_2.setVisibility(View.GONE);
        }
    }
    @Override
    public void onClick(View view) {
           switch (view.getId()){
               case  R.id.Detail_back :
                 finish();
               break;
               case R.id.lijibangzhu_btn:
                   if(TextUtils.isEmpty(BPApplication.getInstance().getMember_Id())){
                       intent = new Intent(DetailsActivity.this,LoginActivity.class);
                       startActivity(intent);
                       return;
                   }
                   Intent intent = new Intent(DetailsActivity.this,Order_PayActivity.class);
                   intent.putExtra("DetailsActivity","DetailsActivity");
                   intent.putExtra("donated_id",donated_ids);
                   intent.putExtra("donated_name",donated_names);
                   intent.putExtra("notice_id",notice_ids);
                   startActivity(intent);
               break;
               case R.id.problem:
                   details_problem_dialog();
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
                gongshi_item_qishu.setText(jsonObject.getString("year")+"年"+
                        jsonObject.getString("mounth")+"月第"+jsonObject.getString("count")+"期");
                Glide.with(DetailsActivity.this).load(jsonObject.getString("photo")).into(gongshi_item_heading);
                gongshi_item_name.setText(jsonObject.getString("member_name"));
                gongshi_item_age.setText(jsonObject.getString("age"));
                gongshi_item_address.setText(jsonObject.getString("address")+"  ");
                gongshi_item_number.setText(jsonObject.getString("number"));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                gongshi_item_join_date.setText(formatter.format((Long.valueOf(jsonObject.getString("join_time")))*1000));
                gongshi_start_plan.setText(formatter.format((Long.valueOf(jsonObject.getString("effecttime")))*1000));
                gongshi_shenhe_date.setText(formatter.format((Long.valueOf(jsonObject.getString("examine_time")))*1000));
                gongshi_item_money.setText(jsonObject.getString("get_money"));
                gongshi_item_plan.setText(jsonObject.getString("plan_name"));
                donated_ids=jsonObject.getString("member_id");
                notice_ids=jsonObject.getString("notice_id");
                donated_names=jsonObject.getString("member_name");
                intro.setText("     "+jsonObject.getString("intro"));
                reason.setText("     "+jsonObject.getString("notice_detail"));
                String notice_begin_time =formatter.format((Long.valueOf(jsonObject.getString("notice_begin_time")))*1000);
                String notice_begin_time_2 =formatter.format((Long.valueOf(jsonObject.getString("notice_end_time")))*1000);
                gongshi_item_qishu3.setText("公示期："+notice_begin_time+"~"+notice_begin_time_2);
                setTextMarquee(gongshi_item_address);
                gongshi_item_pass.setText(formatter.format((Long.valueOf(jsonObject.getString("pass_time")))*1000));
                if(jsonObject.getString("gender").equals("0")){
                    gongshi_item_sex.setText("女");
                }else {
                    gongshi_item_sex.setText("男");
                }
                gongshi_item_date.setText(notice_begin_time+"~"+notice_begin_time_2);
                gongshi_item_bokuan_date.setText(formatter.format((Long.valueOf(jsonObject.getString("issue_time")))*1000));
            }
         });
     }

    @Override
    public void postViews(final String s) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  if(s.equals("请求失败！")){
                        gongshi_item_process_1.setText("");
                  }else {
                      JSONObject jsonObject = JSONObject.fromObject(s);
                      if(jsonObject.size()!=0){
                          gongshi_item_process_1.setText((((jsonObject.getString("investigation")).replaceAll("</br>", "\n")).replaceAll("</p>", "")).replaceAll("<p>", ""));
                      }
                  }
                }
            });
    }

    @Override
    public void postViewss(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONArray jsonArray = JSONArray.fromObject(s);
                JSONArray jsonArray1 = new JSONArray();
                if(jsonArray.size()!=0){
                   for(int i=0;i<jsonArray.size();i++){
                       jsonArray1.add(jsonArray.getJSONObject(i));
                   }
                    gongshi_item_cailiao.setText(jsonArray.size()+"件");
                }else {
                    gongshi_item_cailiao.setText("0件");
                }
                progress_iam_grid.setAdapter(new GirdView_XiangQingAdapter(DetailsActivity.this,jsonArray1));
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

    }

    @Override
    public void imgView(Bitmap bitmap) {

    }
    public void fragment_2_dialog(){
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.fragment_2_dialog, null);
        bottomDialog.setContentView(contentView);
        Button btn_juankuan=(Button)contentView.findViewById(R.id.btn_juankuan);
        myRadioGroup_1=(MyRadioGroup)contentView.findViewById(R.id.myRadioGroup_1);
        final EditText myRadioGroup_2= (EditText) contentView.findViewById(R.id.myRadioGroup_2);
        final RadioButton radioButton_pay_1= (RadioButton) contentView.findViewById(R.id.radioButton_pay_1);
        final RadioButton radioButton_pay_2= (RadioButton) contentView.findViewById(R.id.radioButton_pay_2);
        final RadioButton radioButton_pay_3= (RadioButton) contentView.findViewById(R.id.radioButton_pay_3);
        final RadioButton radioButton_pay_4= (RadioButton) contentView.findViewById(R.id.radioButton_pay_4);
        final TextView xieyi= (TextView) contentView.findViewById(R.id.xieyi);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();

        myRadioGroup_2.setCursorVisible(false);
        myRadioGroup_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton_pay_1.setChecked(false);
                radioButton_pay_2.setChecked(false);
                radioButton_pay_3.setChecked(false);
                radioButton_pay_4.setChecked(false);
                radioButton_pay_1.setBackgroundResource(R.drawable.textview_round_border);
                radioButton_pay_2.setBackgroundResource(R.drawable.textview_round_border);
                radioButton_pay_3.setBackgroundResource(R.drawable.textview_round_border);
                radioButton_pay_4.setBackgroundResource(R.drawable.textview_round_border);
                myRadioGroup_2.setBackgroundResource(R.drawable.textview_round_border_red);
                myRadioGroup_2.setCursorVisible(true);
                result="";
            }
        });

        radioButton_pay_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(myRadioGroup_2.getWindowToken(), 0);
            }
        });
        radioButton_pay_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(myRadioGroup_2.getWindowToken(), 0);
            }
        });
        radioButton_pay_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(myRadioGroup_2.getWindowToken(), 0);
            }
        });
        radioButton_pay_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(myRadioGroup_2.getWindowToken(), 0);
            }
        });

        myRadioGroup_1.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                result = radioButton.getText().toString();
                myRadioGroup_2.setBackgroundResource(R.drawable.textview_round_border);
                if(result.equals("1元")){
                    radioButton_pay_1.setBackgroundResource(R.drawable.textview_round_border_red);
                    radioButton_pay_2.setBackgroundResource(R.drawable.textview_round_border);
                    radioButton_pay_3.setBackgroundResource(R.drawable.textview_round_border);
                    radioButton_pay_4.setBackgroundResource(R.drawable.textview_round_border);
                    myRadioGroup_2.setCursorVisible(false);
                    myRadioGroup_2.setText("");
                    result="1";
                }else if(result.equals("2元")){
                    radioButton_pay_1.setBackgroundResource(R.drawable.textview_round_border);
                    radioButton_pay_2.setBackgroundResource(R.drawable.textview_round_border_red);
                    radioButton_pay_3.setBackgroundResource(R.drawable.textview_round_border);
                    radioButton_pay_4.setBackgroundResource(R.drawable.textview_round_border);
                    myRadioGroup_2.setCursorVisible(false);
                    myRadioGroup_2.setText("");
                    result="2";
                }else if(result.equals("5元")){
                    radioButton_pay_1.setBackgroundResource(R.drawable.textview_round_border);
                    radioButton_pay_2.setBackgroundResource(R.drawable.textview_round_border);
                    radioButton_pay_3.setBackgroundResource(R.drawable.textview_round_border_red);
                    radioButton_pay_4.setBackgroundResource(R.drawable.textview_round_border);
                    myRadioGroup_2.setCursorVisible(false);
                    myRadioGroup_2.setText("");
                    result="5";
                }else if(result.equals("10元")){
                    radioButton_pay_1.setBackgroundResource(R.drawable.textview_round_border);
                    radioButton_pay_2.setBackgroundResource(R.drawable.textview_round_border);
                    radioButton_pay_3.setBackgroundResource(R.drawable.textview_round_border);
                    radioButton_pay_4.setBackgroundResource(R.drawable.textview_round_border_red);
                    myRadioGroup_2.setCursorVisible(false);
                    myRadioGroup_2.setText("");
                    result="10";
                }
            }
        });
        btn_juankuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                results= myRadioGroup_2.getText().toString();
                if(results.equals("0")){
                    ToastUtil.showToast(DetailsActivity.this,"请输入正确金额");
                    return;
                }
                if(!TextUtils.isEmpty(myRadioGroup_2.getText().toString())) {
                    if(!isNumeric(myRadioGroup_2.getText().toString())){
                        toastUtil.showToast(DetailsActivity.this,"请输入正确金额");
                        return;
                    }
                    intent =new Intent(DetailsActivity.this, Order_PayActivity.class);
                    intent.putExtra("DetailsActivity","DetailsActivity");
                    if(!TextUtils.isEmpty(results)){
                        intent.putExtra("money",results);
                    }else {
                        intent.putExtra("money",result);
                    }
                    intent.putExtra("donated_id",donated_ids);
                    intent.putExtra("donated_name",donated_names);
                    intent.putExtra("notice_id",notice_ids);
                    startActivity(intent);
                    bottomDialog.cancel();
                }else if(!TextUtils.isEmpty(result)){
                    intent =new Intent(DetailsActivity.this, Order_PayActivity.class);
                    intent.putExtra("DetailsActivity","DetailsActivity");
                    if(!TextUtils.isEmpty(results)){
                        intent.putExtra("money",results);
                    }else {
                        intent.putExtra("money",result);
                    }
                    intent.putExtra("donated_id",donated_ids);
                    intent.putExtra("donated_name",donated_names);
                    intent.putExtra("notice_id",notice_ids);
                    startActivity(intent);
                    bottomDialog.cancel();
                }else {
                    toastUtil.showToast(DetailsActivity.this,"请选择金额");
                    return;
                }
            }
        });
        xieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                join_plan_dialog();
                bottomDialog.cancel();
            }
        });
    }
    //事件接受
    public void onEventMainThread(MainSendEvent event){
        if(event != null){
            if(event.getStringMsgData().equals("关闭")){
                finish();
            }
        }
    }

    public void join_plan_dialog(){
        inflater = LayoutInflater.from(DetailsActivity.this);
        View view = inflater.inflate(R.layout.join_plan_dialog,null);
        TextView woyiyuedu= (TextView)view. findViewById(R.id.woyiyuedu);
        dialog = new Dialog(DetailsActivity.this);
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


    public void details_problem_dialog() {
        final LayoutInflater inflater = LayoutInflater.from(DetailsActivity.this);
        View view = inflater.inflate(R.layout.details_problem, null);
        TextView sure_cancel= (TextView) view.findViewById(R.id.sure_cancel);
        TextView textView = (TextView) view.findViewById(R.id.problem_text);
        final Dialog dialog = new Dialog(DetailsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);//
        dialog.show();
        // 设置宽度为屏幕的宽度
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width =  getResources().getDimensionPixelSize(R.dimen.heidhhhhh2);
        lp.height=getResources().getDimensionPixelSize(R.dimen.heidhhhh1);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);
        sure_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
    }

    public void setTextMarquee(TextView textView) {
        if (textView != null) {
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setSingleLine(true);
            textView.setSelected(true);
            textView.setFocusable(true);
            textView.setFocusableInTouchMode(true);
        }
    }

    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("^(([1-9]\\d{0,9})|0)(\\.\\d{1,2})?$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}

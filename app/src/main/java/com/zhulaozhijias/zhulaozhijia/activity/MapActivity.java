package com.zhulaozhijias.zhulaozhijia.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.activity.DetailsActivity;
import com.zhulaozhijias.zhulaozhijia.activity.Order_PayActivity;
import com.zhulaozhijias.zhulaozhijia.adpter.Fragment_2_Adapter;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.Connect_Check;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.LogLog;
import com.zhulaozhijias.zhulaozhijia.widgets.MyRadioGroup;
import com.zhulaozhijias.zhulaozhijia.widgets.PullBaseView;
import com.zhulaozhijias.zhulaozhijia.widgets.PullRecyclerView;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.zhulaozhijias.zhulaozhijia.R.id.radiogroup_4;

/**
 * Created by asus on 2017/9/13.
 */

public class MapActivity extends BaseActivity implements View.OnClickListener,MainView,PullBaseView.OnRefreshListener{
    private ToastUtil toastUtil;
    private PullRecyclerView publicity_recyclerview;
    private Fragment_2_Adapter fragment_2_adapter;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> list1 = new ArrayList<>();
    private LayoutInflater inflater;
    private Dialog dialog;
    private MyRadioGroup myRadioGroup;
    private Intent intent;
    private MainPresenter mainPresenter;
    private JSONArray jsonArray1;
    private JSONArray jsonArray4;
    private TextView gongshi_item_qishu,gongshi_item_qishu2;
    private JSONObject jsonObject;
    private LinearLayout huzhugongshi_back;
    private String result="1",results;
    private String donated_ids,donated_names,notice_ids;
    private ProgressBar map_activity_progress_bar;

    @Override
    public void addLayout() {
        setContentView(R.layout.map_activity);
    }

    @Override
    public void initView() {
        gongshi_item_qishu= (TextView) findViewById(R.id.gongshi_item_qishu);
        gongshi_item_qishu2= (TextView) findViewById(R.id.gongshi_item_qishu2);
        huzhugongshi_back= (LinearLayout) findViewById(R.id.huzhugongshi_back);
        huzhugongshi_back.setOnClickListener(this);
        mainPresenter = new MainPresenter(this,this);
        publicity_recyclerview= (PullRecyclerView) findViewById(R.id.publicity_recyclerview);
        publicity_recyclerview.setOnRefreshListener(this);
        map_activity_progress_bar= (ProgressBar) findViewById(R.id.map_activity_progress_bar);


        final Map<String,String> map = new HashMap<>();
        map.put("status","1");
        map.put("secret", CreateMD5.getMd5("1"+"z!l@z#j$"));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                map_activity_progress_bar.setVisibility(View.VISIBLE);
                mainPresenter.postMap(SystemConstant.PublicConstant.Public_GONGSHI,map);
                if(Connect_Check.getCurrentNetType(MapActivity.this)==0){
                    toastUtil.showToast(MapActivity.this,"网络不给力，请检查网络设置");
                }
            }
        });
    }
    public void fragment_1_dialog(final String donated_id,final String donated_name,final String notice_id){
        final Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.fragment_2_dialog, null);
        TextView xieyi = (TextView) contentView.findViewById(R.id.xieyi);
        final RadioButton radioButton_pay_1= (RadioButton) contentView.findViewById(R.id.radioButton_pay_1);
        final RadioButton radioButton_pay_2= (RadioButton) contentView.findViewById(R.id.radioButton_pay_2);
        final RadioButton radioButton_pay_3= (RadioButton) contentView.findViewById(R.id.radioButton_pay_3);
        final RadioButton radioButton_pay_4= (RadioButton) contentView.findViewById(R.id.radioButton_pay_4);
        MyRadioGroup myRadioGroup= (MyRadioGroup)contentView.findViewById(R.id.myRadioGroup_1);
        final EditText myRadioGroup_2 = (EditText) contentView.findViewById(R.id.myRadioGroup_2);
        bottomDialog.setContentView(contentView);
        Button btn_juankuan=(Button)contentView.findViewById(R.id.btn_juankuan);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
//        InputMethodManager imm1 = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm1.hideSoftInputFromWindow(contentView.getWindowToken(), 0);//从控件所在的窗口中隐藏
        myRadioGroup_2.setCursorVisible(false);
        myRadioGroup_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRadioGroup_2.setCursorVisible(true);
                myRadioGroup_2.setBackgroundResource(R.drawable.textview_round_border_red);
                radioButton_pay_1.setBackgroundResource(R.drawable.textview_round_border);
                radioButton_pay_2.setBackgroundResource(R.drawable.textview_round_border);
                radioButton_pay_3.setBackgroundResource(R.drawable.textview_round_border);
                radioButton_pay_4.setBackgroundResource(R.drawable.textview_round_border);
                result="";
            }
        });

        myRadioGroup.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                result = radioButton.getText().toString();
                myRadioGroup_2.setBackgroundResource(R.drawable.textview_round_border);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
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
                if(!TextUtils.isEmpty(myRadioGroup_2.getText().toString())) {
                    if(!isNumeric(myRadioGroup_2.getText().toString())){
                        toastUtil.showToast(MapActivity.this,"请输入正确金额");
                        return;
                    }
                    intent =new Intent(MapActivity.this, Order_PayActivity.class);
                    intent.putExtra("MapActivity","MapActivity");
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
                    intent =new Intent(MapActivity.this, Order_PayActivity.class);
                    intent.putExtra("MapActivity","MapActivity");
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
                    toastUtil.showToast(MapActivity.this,"请选择金额");
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
    public void join_plan_dialog(){
        inflater = LayoutInflater.from(MapActivity.this);
        View view = inflater.inflate(R.layout.join_plan_dialog,null);
        TextView woyiyuedu= (TextView)view. findViewById(R.id.woyiyuedu);
        dialog = new Dialog(MapActivity.this);
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
    public void onClick(View view) {
          switch (view.getId()){
              case R.id.huzhugongshi_back:
                  finish();
                  break;
          }
    }

    @Override
    public void getView(final String s) {

    }

    @Override
    public void postView(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONArray jsonArray = JSONArray.fromObject(s);
                jsonArray1 = new JSONArray();
                for (int i=0;i<jsonArray.size();i++) {
                    jsonArray1.add(jsonArray.getJSONObject(i));
                    jsonObject = jsonArray1.getJSONObject(i);
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                gongshi_item_qishu.setText(jsonObject.getString("year")+"年"+jsonObject.getString("mounth")+"月第"+
                        jsonObject.getString("count")+"期公示");
                String notice_begin_time =formatter.format((Long.valueOf(jsonObject.getString("notice_begin_time")))*1000);
                String notice_begin_time_2 =formatter.format((Long.valueOf(jsonObject.getString("notice_end_time")))*1000);
                gongshi_item_qishu2.setText("接受定向捐助"+notice_begin_time+"~"+notice_begin_time_2);
                fragment_2_adapter = new Fragment_2_Adapter(MapActivity.this,jsonArray1);
                publicity_recyclerview.setFragment2Adapterss(fragment_2_adapter);
                publicity_recyclerview.setFragment2LayoutManagers(new LinearLayoutManager(MapActivity.this));
                fragment_2_adapter.setOnClicListener(new Fragment_2_Adapter.OnItemClicListener() {
                    @Override
                    public void onItemClic(View view, int position, String donated_id, String donated_name, String notice_id) {
                        if(!checkIsLogined()){
                            return;
                        }else {
                            donated_ids=donated_id;
                            donated_names=donated_name;
                            notice_ids=notice_id;
                            fragment_1_dialog(donated_id,donated_name,notice_id);
                        }
                    }
                });
                fragment_2_adapter.setOnClicListeners(new Fragment_2_Adapter.OnItemClicListeners() {
                    @Override
                    public void onItemClics(View view, int position, String notice) {
                        if(!checkIsLogined()){
                            return;
                        }else {
                            intent = new Intent(MapActivity.this,DetailsActivity.class);
                            intent.putExtra("1",notice);
                            intent.putExtra("2","map");
                            startActivity(intent);
                        }
                    }
                });
                map_activity_progress_bar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void postViews(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")){
                String trade_no= jsonObject.getString("trade_no");
//                intent =new Intent(MapActivity.this, Order_PayActivity.class);
//                intent.putExtra("MapActivity","MapActivity");
//                if(!TextUtils.isEmpty(results)){
//                    intent.putExtra("money",results);
//                }else {
//                    intent.putExtra("money",result);
//                }
//                intent.putExtra("trade_no",trade_no);
//                intent.putExtra("donated_id",donated_ids);
//                intent.putExtra("donated_name",donated_names);
//                intent.putExtra("notice_id",notice_ids);
//                startActivity(intent);
                }else {
                    toastUtil.showToast(MapActivity.this,jsonObject.getString("msg"));
                }
            }
        });

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
                map_activity_progress_bar.setVisibility(View.GONE);
                toastUtil.showToast(MapActivity.this,"网络连接超时，请稍后再试");
            }
        });
    }

    @Override
    public void imgView(Bitmap bitmap) {

    }

    private boolean checkIsLogined() {
        if (!BPApplication.getInstance().isLogined()) {
            Intent intent = new Intent(MapActivity.this, LoginActivity.class);
            startActivity(intent);
            return false;
        }
        return true;
    }

    @Override
    public void onHeaderRefresh(PullBaseView view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Map<String,String> map = new HashMap<>();
                map.put("status","1");
                map.put("secret", CreateMD5.getMd5("1"+"z!l@z#j$"));
                mainPresenter.postMap(SystemConstant.PublicConstant.Public_GONGSHI,map);
                publicity_recyclerview.onHeaderRefreshComplete();
            }
        }, 1500);
    }

    @Override
    public void onFooterRefresh(PullBaseView view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                publicity_recyclerview.onFooterRefreshComplete();
            }
        }, 1500);
    }

    /**
    判断字符串是否为数字
     **/
    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("^(([1-9]\\d{0,9})|0)(\\.\\d{1,2})?$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}

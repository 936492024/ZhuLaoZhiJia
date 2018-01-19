package com.zhulaozhijias.zhulaozhijia.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.MyRadioGroup;
import com.zhulaozhijias.zhulaozhijia.widgets.PayPwdEditText;
import com.zhulaozhijias.zhulaozhijia.widgets.PayResult;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;
import com.zhulaozhijias.zhulaozhijia.widgets.eventbus.MainSendEvent;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;

/**
 * Created by asus on 2017/9/17.
 */

public class Order_PayActivity extends BaseActivity implements View.OnClickListener ,MainView {
    private LinearLayout order_back;
    private String all_moneys,other_money,moneys,plan_id,name,card_id,father,father_id,mother,mother_id;
//    private TextView pay_money;
    private MyRadioGroup radiogroup_4;
    private String wallet = "1";
    private Button sure_pay;
    private MainPresenter mainPresenter;
    private Map<String, String> map;
    private String money="1",donated_id,donated_name,notice_id;
    private ToastUtil toastUtil;
    private static final int SDK_PAY_FLAG = 1;
    private final IWXAPI api = WXAPIFactory.createWXAPI(this, null);
    private MyRadioGroup myRadioGroup_1;
    private Intent intent;
    private LayoutInflater inflater;
    private Dialog dialog;
    private String result="1",results,result_3;
    private EditText myRadioGroup_2;
    private SweetAlertDialog pDialog;
    @Override
    public void addLayout() {
        setContentView(R.layout.order_pay);
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        mainPresenter = new MainPresenter(this, this);
        order_back = (LinearLayout) findViewById(R.id.order_back);
        order_back.setOnClickListener(this);
        radiogroup_4 = (MyRadioGroup) findViewById(R.id.radiogroup_4);
//        pay_money = (TextView) findViewById(R.id.pay_money);
        sure_pay = (Button) findViewById(R.id.sure_pay);
        sure_pay.setOnClickListener(this);
        myRadioGroup_1= (MyRadioGroup) findViewById(R.id.myRadioGroup_1);
        myRadioGroup_2= (EditText) findViewById(R.id.myRadioGroup_2);
        if(!TextUtils.isEmpty(getIntent().getStringExtra("MapActivity"))){
//            money= getIntent().getStringExtra("money");
            donated_id=getIntent().getStringExtra("donated_id");
            donated_name=getIntent().getStringExtra("donated_name");
            notice_id=getIntent().getStringExtra("notice_id");
//            pay_money.setText(money + "元");
        }else if(!TextUtils.isEmpty(getIntent().getStringExtra("DetailsActivity"))){
//             money= getIntent().getStringExtra("money");
             donated_id=getIntent().getStringExtra("donated_id");
             donated_name=getIntent().getStringExtra("donated_name");
             notice_id=getIntent().getStringExtra("notice_id");
//             pay_money.setText(money + "元");
         }
        radiogroup_4.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                result_3 = radioButton.getText().toString();
                if (result_3.equals("1")) {
                    wallet = result_3;
                } else if (result.equals("2")) {
                    wallet = result_3;
                } else {
                    wallet = result_3;
                }
            }
        });
        fragment_2_dialog();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_back:
                finish();
                break;
            case R.id.sure_pay:
                if(TextUtils.isEmpty(BPApplication.getInstance().getMember_Id())){
                    intent = new Intent(Order_PayActivity.this,LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                results= myRadioGroup_2.getText().toString();
//                if(results.equals("0")){
//                    ToastUtil.showToast(Order_PayActivity.this,"请输入正确金额");
//                    return;
//                }
                if(!TextUtils.isEmpty(myRadioGroup_2.getText().toString())) {
                    if(!isNumeric(myRadioGroup_2.getText().toString())){
                        ToastUtil.showToast(Order_PayActivity.this,"请输入正确金额");
                        return;
                    }
                    if(Double.valueOf(results)<0.5||Double.valueOf(results)<0.50){
                        ToastUtil.showToast(Order_PayActivity.this,"捐助金额不能低于0.5元");
                        return;
                    }
                    if (!TextUtils.isEmpty(results)) {
                        money=results;
                    } else {
                        money=result;
                    }
                } else if(!TextUtils.isEmpty(result)){
                    if(!TextUtils.isEmpty(results)){
                        money=results;
                    }else {
                        money=result;
                    }
                }else {
                    ToastUtil.showToast(Order_PayActivity.this,"请选择金额");
                    return;
                }
                if (wallet.equals("1")) {
                    if(TextUtils.isEmpty(BPApplication.getInstance().getPay_password())||
                            BPApplication.getInstance().getPay_password().equals("null")){
                        pay_password_set();
                    }else {
                        pay_password();
                    }
                } else if (wallet.equals("2")) {
                    Map<String, String> mapss = new HashMap<>();
                    mapss.put("donated_id", donated_id);
                    mapss.put("donated_name", donated_name);
                    mapss.put("money", money);
                    mapss.put("notice_id", notice_id);
                    mapss.put("member_id", BPApplication.getInstance().getMember_Id());
                    mapss.put("secret", CreateMD5.getMd5(donated_id + donated_name + BPApplication.getInstance().getMember_Id()
                            + money + notice_id + "z!l@z#j$"));
                    mainPresenter.wodesss_1(SystemConstant.GEREN_ZHONGXIN.Mine_ALIPAY_RECORD, mapss);
                } else {
                        Map<String, String> maps = new HashMap<>();
                        maps.put("donated_id", donated_id);
                        maps.put("donated_name", donated_name);
                        maps.put("money", money);
                        maps.put("notice_id", notice_id);
                        maps.put("member_id", BPApplication.getInstance().getMember_Id());
                        maps.put("secret", CreateMD5.getMd5(donated_id + donated_name + BPApplication.getInstance().getMember_Id()
                                + money + notice_id + "z!l@z#j$"));
                        mainPresenter.wodess(SystemConstant.GEREN_ZHONGXIN.Mine_ALIPAY_RECORD, maps);
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
                if(jsonObject.getString("success").equals("true")){
                    map.put("donated_id",donated_id);
                    map.put("donated_name",donated_name);
                    map.put("money",money);
                    map.put("notice_id",notice_id);
                    map.put("member_id",BPApplication.getInstance().getMember_Id());
                    map.put("secret",CreateMD5.getMd5(donated_id+donated_name+BPApplication.getInstance().getMember_Id()
                    +money+notice_id+"z!l@z#j$"));
                    Log.e("dsadsada",map.toString());
                    mainPresenter.wodes(SystemConstant.GEREN_ZHONGXIN.Mine_RECORD_ACCOUNT,map);
                }else {
                    pDialog.dismiss();
                    showDialog(jsonObject.getString("msg"));
                }
            }
        });
    }

    @Override
    public void postViews(final String s) {
          runOnUiThread(new Runnable() {
              @Override
              public void run() {
                  JSONObject jsonObject =JSONObject.fromObject(s);
                  if(jsonObject.getString("success").equals("true")){
                      pDialog.dismiss();
                      showDialogs("捐助成功，获得"+jsonObject.getString("point")+"积分");
                  }else {
                      pDialog.dismiss();
                      showDialog(jsonObject.getString("msg"));
                  }
              }
          });
    }

    @Override
    public void postViewss(final String s) {
        Log.e("dsad",s);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")){
                    String trade_no_alipay=jsonObject.getString("trade_no");
                    Map<String,String> map_alipay = new HashMap<String, String>();
                    map_alipay.put("trade_no",trade_no_alipay);
                    map_alipay.put("secret",CreateMD5.getMd5(trade_no_alipay+"z!l@z#j$"));
                    mainPresenter.wodesss(SystemConstant.GEREN_ZHONGXIN.Mine_ALIPAY_ALIPAY,map_alipay);
                }else {

                }
            }
        });
    }

    @Override
    public void postViewsss(final String s) {
        Log.e("获取数据1",s);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")){
//                            alipay();;
                    aliPayData(jsonObject.getString("alipay"));
                }
            }
        });

    }

    @Override
    public void postViewsss_1(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")){
                    jsonObject.getString("trade_no");
                    Map<String,String> map  = new HashMap<>();
                    map.put("trade_no",jsonObject.getString("trade_no"));
                    map.put("secret",CreateMD5.getMd5(jsonObject.getString("trade_no")+"z!l@z#j$"));
                    mainPresenter.wodesss_2(SystemConstant.GEREN_ZHONGXIN.Mine_WECHATPAY_PUBLICITY,map);
                }

            }
        });
    }
    //事件接受
    public void onEventMainThread(MainSendEvent event){
        if(event != null){
            if(event.getStringMsgData().equals("随手捐支付")){
                BPApplication.getInstance().setWeChat_2("");
                finish();
            }
        }
    }


    @Override
    public void postViewsss_2(final String s) {
        JSONObject jsonObject = JSONObject.fromObject(s);
        if(jsonObject.getString("success").equals("true")){
            String data = jsonObject.getString("data");
            JSONObject jsonObject1 =JSONObject.fromObject(data);
            wechat(jsonObject1.getString("appid"),jsonObject1.getString("partnerid"),jsonObject1.getString("prepayid"),jsonObject1.getString("package"),jsonObject1.getString("noncestr"),jsonObject1.getString("timestamp"),jsonObject1.getString("sign"));
            BPApplication.getInstance().setWeChat_2("2");
        }
    }

    @Override
    public void fail(String s) {
        pDialog.dismiss();
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(Order_PayActivity.this);
        builder.setTitle("提示");
        builder.setMessage("网络连接超时，请稍后再试");
        builder.setCancelable(false);
        builder.setPositiveButton("确定",null);
        builder.setIcon(R.drawable.ic_launcher);
        builder.show();
    }


    public void wechat(String appId,String partnerId,String prepayId,String packageValue,String nonceStr,
                       String timeStamp,String sign){

        PayReq request = new PayReq();

        request.appId = appId;

        request.partnerId = partnerId;

        request.prepayId= prepayId;

        request.packageValue = packageValue;

        request.nonceStr= nonceStr;

        request.timeStamp= timeStamp;

        request.sign= sign;

        api.sendReq(request);

    }
    @Override
    public void imgView(Bitmap bitmap) {

    }

    @SuppressWarnings("static-access")
    public void pay_password() {
        final LayoutInflater inflater = LayoutInflater.from(Order_PayActivity.this);
        View view = inflater.inflate(R.layout.pay_password, null);
        final Dialog dialog = new Dialog(Order_PayActivity.this);
        PayPwdEditText password_qianbao= (PayPwdEditText) view.findViewById(R.id.password_qianbao);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        ImageView pay_cancel= (ImageView) view.findViewById(R.id.pay_cancel);
        TextView pay_money_1 = (TextView) view.findViewById(R.id.pay_money_1);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);//
        dialog.show();
        // 设置宽度为屏幕的宽度
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = getResources().getDimensionPixelSize(R.dimen.pay_dialog_wight);
        lp.height = getResources().getDimensionPixelSize(R.dimen.pay_dialog_height);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);
        if(!TextUtils.isEmpty(money)){
            pay_money_1.setText("￥"+money);
        }
        if(!TextUtils.isEmpty(results)){
            pay_money_1.setText("￥"+results);
        }
        pay_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        password_qianbao.initStyle(R.drawable.edit_num_bg, 6, 0.33f, R.color.color999999, R.color.color999999, 20);
        password_qianbao.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {
                map = new HashMap<>();
                map.put("member_id", BPApplication.getInstance().getMember_Id());
                map.put("pay_password", str);
                map.put("secret", CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+str+"z!l@z#j$"));
                mainPresenter.postMap(SystemConstant.GEREN_ZHONGXIN.Mine_SEL_PWD, map);
                dialog.cancel();
                pDialog  = new SweetAlertDialog(Order_PayActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("正在付款...");
                pDialog.setCancelable(false);
                pDialog.show();
            }
        });
    }

    public void fragment_2_dialog(){
        final RadioButton radioButton_pay_1= (RadioButton) findViewById(R.id.radioButton_pay_1);
        final RadioButton radioButton_pay_2= (RadioButton) findViewById(R.id.radioButton_pay_2);
        final RadioButton radioButton_pay_3= (RadioButton) findViewById(R.id.radioButton_pay_3);
        final RadioButton radioButton_pay_4= (RadioButton)findViewById(R.id.radioButton_pay_4);
        TextView xieyi_order= (TextView) findViewById(R.id.xieyi_order);
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
                money=result;
            }
        });

        xieyi_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                join_plan_dialog();
            }
        });
    }

    public void join_plan_dialog(){
        inflater = LayoutInflater.from(Order_PayActivity.this);
        View view = inflater.inflate(R.layout.join_plan_dialog,null);
        TextView woyiyuedu= (TextView)view. findViewById(R.id.woyiyuedu);
        dialog = new Dialog(Order_PayActivity.this);
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
    @SuppressWarnings("static-access")
    public void pay_password_set() {
        final LayoutInflater inflater = LayoutInflater.from(Order_PayActivity.this);
        View view = inflater.inflate(R.layout.pay_password_set, null);
        final Dialog dialog = new Dialog(Order_PayActivity.this);
        TextView text_pay_password = (TextView) view.findViewById(R.id.text_pay_password);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);//
        dialog.show();
        // 设置宽度为屏幕的宽度
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = getResources().getDimensionPixelSize(R.dimen.pay_dialog_wight);
        lp.height = getResources().getDimensionPixelSize(R.dimen.pay_dialog_height);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view);
        text_pay_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Order_PayActivity.this,Mine_AcountActivity.class);
                startActivity(intent);
                dialog.cancel();
            }
        });
    }



    private void showDialog(String str) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage(str);
        builder.setCancelable(false);
        builder.setPositiveButton("确定",null);
        builder.setIcon(R.drawable.ic_launcher);
        builder.show();
    }


    private void showDialogs(String str) {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage(str);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                EventBus.getDefault().post(new MainSendEvent("关闭"));
            }
        });
        builder.show();

    }

    public void aliPayData(String aliPayBean) {
        //支付宝支付请求所需的签名字符串
        final String orderInfo = aliPayBean;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(Order_PayActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    //支付宝返回数据handler
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    String result = "";
                    // 判断resultStatus 为9000则代表支付成功
                    Log.e("TAG", resultStatus);
                    if (TextUtils.equals(resultStatus, "9000")) {
                        //支付成功
                        result = "支付成功";
                        EventBus.getDefault().post(new MainSendEvent("充值支付2"));
                            finish();
                    } else if ("6001".equals(resultStatus)) {
                        result = "您取消了支付";
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        result = "支付失败";
                    }
                    ToastUtil.showToast(Order_PayActivity.this, result);
                    break;
                }
            }
        }
    };

    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("^(([1-9]\\d{0,9})|0)(\\.\\d{1,2})?$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}

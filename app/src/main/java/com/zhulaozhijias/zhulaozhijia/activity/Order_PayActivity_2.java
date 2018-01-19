package com.zhulaozhijias.zhulaozhijia.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;

/**
 * Created by asus on 2017/10/23.
 */

public class Order_PayActivity_2 extends BaseActivity implements View.OnClickListener ,MainView {
    private LinearLayout order_back;
    private String moneys,plan_id,plan_name,name,card_id,relative;
    private TextView pay_money;
    private MyRadioGroup radiogroup_4;
    private String result;
    private String wallet = "2";
    private Button sure_pay;
    private MainPresenter mainPresenter;
    private ToastUtil toastUtil;
    private static final int SDK_PAY_FLAG = 1;
    private final IWXAPI api = WXAPIFactory.createWXAPI(this, null);
    private SweetAlertDialog pDialog;
    private String screen;
    private String Member_Id;
    private Map<String,String > map ;
    Handler handler_order=new Handler();
    Runnable runnable_order=new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(Order_PayActivity_2.this,MainActivity.class);
            BPApplication.getInstance().setTitle("0");
            intent.putExtra("Order_PayActivity_2","Order_PayActivity_2");
            startActivity(intent);
            handler_order.removeCallbacks(runnable_order);
        }
    };
    @Override
    public void addLayout() {
        setContentView(R.layout.order_pay_2);
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        mainPresenter = new MainPresenter(this, this);
        order_back = (LinearLayout) findViewById(R.id.order_back);
        order_back.setOnClickListener(this);
        radiogroup_4 = (MyRadioGroup) findViewById(R.id.radiogroup_4);
        pay_money = (TextView) findViewById(R.id.pay_money);
        sure_pay = (Button) findViewById(R.id.sure_pay);
        sure_pay.setOnClickListener(this);
        if(!TextUtils.isEmpty(getIntent().getStringExtra("Join_PlanActivity"))){
            moneys=getIntent().getStringExtra("money");
            plan_id=getIntent().getStringExtra("plan_id");
            plan_name=getIntent().getStringExtra("plan_name");
            name=getIntent().getStringExtra("name");
            card_id=getIntent().getStringExtra("card_id");
            relative=getIntent().getStringExtra("relative");
            screen=getIntent().getStringExtra("flag_1");
            pay_money.setText(moneys + "元");
        }
        radiogroup_4.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                result = radioButton.getText().toString();
                if (result.equals("2")) {
                    wallet = result;
                } else if (result.equals("3")) {
                    wallet = result;
                } else {
                    wallet = result;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_back:
                finish();
                break;
            case R.id.sure_pay:
                pDialog  = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("正在跳转支付...");
                pDialog.setCancelable(false);
                pDialog.setCanceledOnTouchOutside(true);
                pDialog.show();
                Member_Id=BPApplication.getInstance().getMember_Id();
                if(TextUtils.isEmpty(Member_Id)){
                    toastUtil.showToast(Order_PayActivity_2.this,"网络异常，请稍后再试");
                    return;
                }
                    if (wallet.equals("2")) {
                    join_plan_plan();
                    mainPresenter.wodesss_1(SystemConstant.HuZhuPlan.JOJN_PLAN,map);
                } else {
                    join_plan_plan();
                    mainPresenter.wodess(SystemConstant.HuZhuPlan.JOJN_PLAN,map);
                }
                break;
        }
    }

    public void join_plan_plan(){
        map = new HashMap<>();
        map.put("money",moneys);
        map.put("plan_id",plan_id);
        map.put("name",name);
        map.put("card_id",card_id);
        map.put("member_id", Member_Id);
        map.put("relative", relative);
        map.put("plan_name", plan_name);
        map.put("screen",screen);
        map.put("secret", CreateMD5.getMd5(card_id+
                Member_Id+moneys+name +plan_id+plan_name+relative+screen+""+"z!l@z#j$"));
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

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
                    Log.e("dsadsd",trade_no_alipay);
                    Map<String,String> map_alipay = new HashMap<String, String>();
                    map_alipay.put("trade_no",trade_no_alipay);
                    map_alipay.put("secret",CreateMD5.getMd5(trade_no_alipay+"z!l@z#j$"));
                    mainPresenter.wodesss(SystemConstant.GEREN_ZHONGXIN.Mine_ALIPAY_JOIN_PLAN,map_alipay);
                }else {

                }
            }
        });
    }

    @Override
    public void postViewsss(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")){
                    pDialog.dismiss();
                    aliPayData(jsonObject.getString("alipay"));
                }
            }
        });
    }

    @Override
    public void postViewsss_1(final String s) {
        Log.e("获取数据1",s);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")){
                    jsonObject.getString("trade_no");
                    Map<String,String> map  = new HashMap<>();
                    map.put("trade_no",jsonObject.getString("trade_no"));
                    map.put("secret",CreateMD5.getMd5(jsonObject.getString("trade_no")+"z!l@z#j$"));
                    mainPresenter.wodesss_2(SystemConstant.GEREN_ZHONGXIN.Mine_WECHATPAY_JOIN_PLAN,map);
                }
            }
        });
    }

    //事件接受
    public void onEventMainThread(MainSendEvent event){
        if(event != null){
            if(event.getStringMsgData().equals("计划支付")){
                BPApplication.getInstance().setWeChat_3("");
                handler_order.postDelayed(runnable_order, 100);
            }
        }
    }
    @Override
    public void postViewsss_2(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")){
                    String data = jsonObject.getString("data");
                    JSONObject jsonObject1 =JSONObject.fromObject(data);
                    pDialog.dismiss();
                    wechat(jsonObject1.getString("appid"),jsonObject1.getString("partnerid"),jsonObject1.getString("prepayid"),jsonObject1.getString("package"),jsonObject1.getString("noncestr"),jsonObject1.getString("timestamp"),jsonObject1.getString("sign"));
                    BPApplication.getInstance().setWeChat_3("3");
                }
            }
        });
    }

    @Override
    public void fail(String s) {
         runOnUiThread(new Runnable() {
             @Override
             public void run() {
                 toastUtil.showToast(Order_PayActivity_2.this,"网络异常，请稍后再试");
                 pDialog.dismiss();
             }
         });
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


    public void aliPayData(String aliPayBean) {
        //支付宝支付请求所需的签名字符串
        final String orderInfo = aliPayBean;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(Order_PayActivity_2.this);
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
                        Intent intent = new Intent(Order_PayActivity_2.this,MainActivity.class);
                        intent.putExtra("Order_PayActivity_2","Order_PayActivity_2");
                        BPApplication.getInstance().setTitle("0");
                        startActivity(intent);
                    } else if ("6001".equals(resultStatus)) {
                        result = "您取消了支付";
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        result = "支付失败";
                    }
                    ToastUtil.showToast(Order_PayActivity_2.this, result);
                    break;
                }
            }
        }
    };

}

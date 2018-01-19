package com.zhulaozhijias.zhulaozhijia.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.zhulaozhijias.zhulaozhijia.widgets.PayResult;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;
import com.zhulaozhijias.zhulaozhijia.widgets.eventbus.MainSendEvent;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.greenrobot.event.EventBus;

import static com.zhulaozhijias.zhulaozhijia.R.id.radiogroup_4;

/**
 * Created by asus on 2017/9/21.
 */

public class Mine_RechargeActiviy extends BaseActivity implements View.OnClickListener,MainView{
    private LinearLayout huzhu_recharge_back;
    private MainPresenter mainPresenter;
    private Button mine_recharge_sure;
    private MyRadioGroup mine_recharge_radiobutton;
    private String result="1",results,resultss;
    private String wallet="0";
    private static final int SDK_PAY_FLAG = 1;
    private MyRadioGroup myRadioGroup;
    private EditText myRadioGroup_2;
    private String money;
    private TextView title_recharge,text_recharge_2,xieyi;
    private final IWXAPI api = WXAPIFactory.createWXAPI(this, null);
    private int flag=0;
    private LayoutInflater inflater;
    private Dialog dialog;
    Handler handlerss=new Handler();
    Runnable runnabless=new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            Intent intent = new Intent(Mine_RechargeActiviy.this,MainActivity.class);
            BPApplication.getInstance().setTitle("0");
            intent.putExtra("Mine_RechargeActiviy","Mine_RechargeActiviy");
            startActivity(intent);
            handlerss.removeCallbacks(runnabless);
        }
    };

    @Override
    public void addLayout() {
        setContentView(R.layout.mine_recharge_activiy);
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        mainPresenter=new MainPresenter(this,this);
        mine_recharge_radiobutton= (MyRadioGroup) findViewById(R.id.mine_recharge_radiobutton);
        huzhu_recharge_back= (LinearLayout) findViewById(R.id.huzhu_recharge_back);
        huzhu_recharge_back.setOnClickListener(this);
        mine_recharge_sure= (Button) findViewById(R.id.mine_recharge_sure);
        mine_recharge_sure.setOnClickListener(this);
        xieyi= (TextView) findViewById(R.id.xieyi);
        xieyi.setOnClickListener(this);
        myRadioGroup_2= (EditText) findViewById(R.id.myRadioGroup_2);
        title_recharge= (TextView) findViewById(R.id.title_recharge);
        text_recharge_2= (TextView) findViewById(R.id.text_recharge_2);
        if(!TextUtils.isEmpty(getIntent().getStringExtra("FoundationActivity"))){
            title_recharge.setText("向基金会捐赠");
            text_recharge_2.setText("捐赠金额");
            flag=1;
        }
        final RadioButton radioButton_pay_1= (RadioButton) findViewById(R.id.radioButton_pay_1);
        final RadioButton radioButton_pay_2= (RadioButton) findViewById(R.id.radioButton_pay_2);
        final RadioButton radioButton_pay_3= (RadioButton) findViewById(R.id.radioButton_pay_3);
        final RadioButton radioButton_pay_4= (RadioButton) findViewById(R.id.radioButton_pay_4);
        myRadioGroup= (MyRadioGroup) findViewById(R.id.myRadioGroup);
        mine_recharge_radiobutton.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                resultss = radioButton.getText().toString();
                if (resultss.equals("0")) {//微信
                    wallet = resultss;
                } else {
                    wallet = resultss;//支付宝
                }
            }
        });

        myRadioGroup_2.setCursorVisible(false);
        myRadioGroup_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton_pay_1.setChecked(false);
                radioButton_pay_2.setChecked(false);
                radioButton_pay_3.setChecked(false);
                radioButton_pay_4.setChecked(false);
                myRadioGroup_2.setCursorVisible(true);
                myRadioGroup_2.setBackgroundResource(R.drawable.textview_round_border_red);
                radioButton_pay_1.setBackgroundResource(R.drawable.textview_round_border);
                radioButton_pay_2.setBackgroundResource(R.drawable.textview_round_border);
                radioButton_pay_3.setBackgroundResource(R.drawable.textview_round_border);
                radioButton_pay_4.setBackgroundResource(R.drawable.textview_round_border);
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
        myRadioGroup.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.huzhu_recharge_back:
                finish();
                break;
            case R.id.xieyi:
                join_plan_dialog();
                break;
            case R.id.mine_recharge_sure:
                results= myRadioGroup_2.getText().toString();
//                if(results.equals("0")){
//                    ToastUtil.showToast(Mine_RechargeActiviy.this,"请输入正确金额");
//                    return;
//                }
                if(!TextUtils.isEmpty(myRadioGroup_2.getText().toString())) {
                    if(!isNumeric(myRadioGroup_2.getText().toString())){
                        ToastUtil.showToast(Mine_RechargeActiviy.this,"请输入正确金额");
                        return;
                    }
                    if(Double.valueOf(results)<0.5||Double.valueOf(results)<0.50){
                        if(flag==1){
                            ToastUtil.showToast(Mine_RechargeActiviy.this,"捐赠金额不能低于0.5元");
                        }else {
                            ToastUtil.showToast(Mine_RechargeActiviy.this,"充值金额不能低于0.5元");
                        }
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
                    ToastUtil.showToast(Mine_RechargeActiviy.this,"请选择金额");
                    return;
                }
                if(wallet.equals("0")){
                    Map<String,String> map = new HashMap<>();
                    map.put("member_id", BPApplication.getInstance().getMember_Id());
                    map.put("pay",wallet);
                    map.put("money",money);
                    map.put("secret", CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+money+wallet+"z!l@z#j$"));
                    mainPresenter.wodess(SystemConstant.GEREN_ZHONGXIN.Mine_ALIPAY_RECHARGE,map);
//                    ToastUtil.showToast(Mine_RechargeActiviy.this,"敬请期待");
                }else {//支付宝
                    Map<String,String> map = new HashMap<>();
                    map.put("member_id", BPApplication.getInstance().getMember_Id());
                    map.put("pay",wallet);
                    map.put("money",money);
                    map.put("secret", CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+money+wallet+"z!l@z#j$"));
                    mainPresenter.postMap(SystemConstant.GEREN_ZHONGXIN.Mine_ALIPAY_RECHARGE,map);
                }
                break;
        }
    }

    //事件接受
    public void onEventMainThread(MainSendEvent event){
        if(event != null){
            if(event.getStringMsgData().equals("充值支付2")||event.getStringMsgData().equals("充值支付")){
                BPApplication.getInstance().setWeChat_1("");
                handlerss.postDelayed(runnabless, 100);
            }
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
                    Map<String ,String> map = new HashMap<String, String>();
                    map.put("trade_no",jsonObject.getString("trade_no"));
                    if(flag==0){
                        map.put("way","0");
                        map.put("secret",CreateMD5.getMd5(jsonObject.getString("trade_no")+"0"+"z!l@z#j$"));
                    }else {
                        map.put("way","1");
                        map.put("secret",CreateMD5.getMd5(jsonObject.getString("trade_no")+"1"+"z!l@z#j$"));
                    }
                    mainPresenter.wodes(SystemConstant.GEREN_ZHONGXIN.Mine_RECHARGE_ALIPAY,map);
                }
            }
        });
    }

    @Override
    public void postViews(final String s) {
          Log.e("dsadasd",s);
       runOnUiThread(new Runnable() {
           @Override
           public void run() {
               JSONObject jsonObject = JSONObject.fromObject(s);
               if(jsonObject.getString("success").equals("true")){
                   aliPayData(jsonObject.getString("alipay"));
               }
           }
       });
    }

    @Override
    public void postViewss(final String s) {
        Log.e("dsadaa",s);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")){
                    Map<String,String> map  = new HashMap<>();
                    map.put("trade_no",jsonObject.getString("trade_no"));
                    if(flag==0){
                        map.put("way","0");
                        map.put("secret",CreateMD5.getMd5(jsonObject.getString("trade_no")+"0"+"z!l@z#j$"));
                    }else {
                        map.put("way","1");
                        map.put("secret",CreateMD5.getMd5(jsonObject.getString("trade_no")+"1"+"z!l@z#j$"));
                    }
                    mainPresenter.wodesss(SystemConstant.GEREN_ZHONGXIN.Mine_WECHATPAY_RECHARGE,map);
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
                    String data = jsonObject.getString("data");
                    JSONObject jsonObject1 =JSONObject.fromObject(data);
                    wechat(jsonObject1.getString("appid"),jsonObject1.getString("partnerid"),jsonObject1.getString("prepayid"),jsonObject1.getString("package"),jsonObject1.getString("noncestr"),jsonObject1.getString("timestamp"),jsonObject1.getString("sign"));
                    BPApplication.getInstance().setWeChat_1("1");
                }
            }
        });
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

    public void aliPayData(String aliPayBean) {
        //支付宝支付请求所需的签名字符串
        final String orderInfo = aliPayBean;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(Mine_RechargeActiviy.this);
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
                        Intent intent = new Intent(Mine_RechargeActiviy.this,MainActivity.class);
                        intent.putExtra("Mine_RechargeActiviy","Mine_RechargeActiviy");
                        BPApplication.getInstance().setTitle("0");
                        startActivity(intent);
                    } else if ("6001".equals(resultStatus)) {
                        result = "您取消了支付";

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        result = "支付失败";
                    }
                    ToastUtil.showToast(Mine_RechargeActiviy.this, result);
                    break;
                }
            }
        }
    };
    public void join_plan_dialog(){
        inflater = LayoutInflater.from(Mine_RechargeActiviy.this);
        View view = inflater.inflate(R.layout.join_plan_dialog,null);
        TextView woyiyuedu= (TextView)view. findViewById(R.id.woyiyuedu);
        dialog = new Dialog(Mine_RechargeActiviy.this);
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

    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("^(([1-9]\\d{0,9})|0)(\\.\\d{1,2})?$");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}

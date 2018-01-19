package com.zhulaozhijias.zhulaozhijia.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.Connect_Check;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.LogLog;
import com.zhulaozhijias.zhulaozhijia.widgets.eventbus.MainSendEvent;

import net.sf.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by asus on 2017/11/1.
 */

public class ServiceLogin extends Service implements MainView{
    private static final String TAG = "ServiceLogin" ;
    public static final String ACTION = ".ServiceLogin";
    private MainPresenter mainPresenter;
    private Intent intent ;
    private boolean flag=true;

    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "ServiceLogin onBind");
        return null;
    }

    @Override
    public void onCreate() {
        mainPresenter=new MainPresenter(this,this);
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.v(TAG, "ServiceLogin onStart");
        super.onStart(intent, startId);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "ServiceLogin onStartCommand");
        autologin();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        Log.v(TAG, "ServiceLogin onStop");
        super.onDestroy();
    }

    public void autologin(){
        String token = BPApplication.getInstance().getToken();
        String getMobile=BPApplication.getInstance().getMobile();
        if(Connect_Check.getCurrentNetType(ServiceLogin.this)==1||Connect_Check.getCurrentNetType(ServiceLogin.this)==2){
            if(!BPApplication.getInstance().isLogined()){
                if(!TextUtils.isEmpty(token)&&!TextUtils.isEmpty(getMobile)){
                        Map<String ,String> map = new HashMap<>();
                        map.put("mobile",getMobile);
                        map.put("token",token);
                        map.put("secret", CreateMD5.getMd5(getMobile+token+"z!l@z#j$"));
                        Log.e("tokensss",map.toString());
                        mainPresenter.postMap(SystemConstant.UserConstant.USER_AUTO_L0GIN,map);
                }else {
//                    intent = new Intent(ServiceLogin.this,MainActivity.class);
//                    startActivity(intent);
                    BPApplication.getInstance().setLogined(false);
                }
            }
        }else {
            BPApplication.getInstance().setLogined(false);
//            intent = new Intent(ServiceLogin.this,MainActivity.class);
//            startActivity(intent);
        }
    }

    @Override
    public void getView(String s) {

    }

    @Override
    public void postView(final String s) {
        LogLog.e("自动登录",s);
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")){
                    JSONObject json =JSONObject.fromObject(jsonObject.getString("msg"));
                    BPApplication.getInstance().init(json.getString("member_id"),json.getString("name"),
                            json.getString("card_id"),json.getString("mobile"),json.getString("banlance"),
                            json.getString("headimgurl"),json.getString("token"),json.getString("join_time"),
                            true,json.getString("point"),json.getString("level"),json.getString("love_code"),
                            json.getString("signer"),json.getString("pay_password"));
                    EventBus.getDefault().post(new MainSendEvent("自动登录成功"));
                    Intent intent = new Intent(ServiceLogin.this,ServiceLogin.class);
                    stopService(intent);
                }else {
                    BPApplication.getInstance().setMember_Id("");
                    BPApplication.getInstance().setLogined(false);
                }
            }
        }).start();
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

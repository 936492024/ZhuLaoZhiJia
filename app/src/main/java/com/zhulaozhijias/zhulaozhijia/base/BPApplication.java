package com.zhulaozhijias.zhulaozhijia.base;

/**
 * Created by asus on 2017/9/22.
 */
   import java.lang.Thread.UncaughtExceptionHandler;
        import java.util.HashMap;
        import java.util.LinkedList;
        import java.util.List;
        import java.util.Map;
        import android.app.Activity;
        import android.app.Application;

   import android.content.BroadcastReceiver;
   import android.content.ComponentName;
   import android.content.Context;
   import android.content.DialogInterface;
   import android.content.Intent;
   import android.content.IntentFilter;
   import android.content.SharedPreferences;
        import android.content.SharedPreferences.Editor;
   import android.net.ConnectivityManager;
   import android.net.NetworkInfo;
   import android.support.v7.app.AlertDialog;
   import android.util.Log;
   import android.widget.Toast;

   import com.mob.MobApplication;
   import com.tencent.bugly.crashreport.CrashReport;
   import com.zhulaozhijias.zhulaozhijia.activity.MainActivity;
   import com.zhulaozhijias.zhulaozhijia.activity.StatcActivity;
   import com.zhulaozhijias.zhulaozhijia.widgets.Connect_Check;
   import com.zhulaozhijias.zhulaozhijia.widgets.CrashHandler;

   import cn.sharesdk.framework.ShareSDK;

/**
 * 系统全局配置类
 *
 * @author fly
 *
 */
public class BPApplication extends MobApplication {
    /**
     * 应用实例(单例)
     */
    private static BPApplication singleton;

//    private static ;
    public static Map<String, Object> map = new HashMap<String, Object>();

    /**
     * 返回应用实例
     *
     * @return
     */
    public static BPApplication getInstance() {
        return singleton;
    }

    private SharedPreferences share;

    /**
     * 系统activity列表,保存所有activity实例列表
     */
    public List<Activity> activityList = new LinkedList<Activity>();

    /**
     * 添加activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);

    }

    /**
     * 关闭每一个list内的activity
     */
    public void exit() {
        try {
            for (Activity activity : activityList) {
                if (activity != null) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    public void init(String member_id,String name,String setCard_Id,String setMobile,
                     String banlance,String headimgurl, String token,String join_time,
                     Boolean yes,String point,String level,String love_code,String signer,String pay_password) {
        Editor editor = share.edit();
        editor.commit();
        setMember_Id(member_id);
        setName(name);
        setCard_Id(setCard_Id);
        setMobile(setMobile);
        setBanlance(banlance);
        setHeadimgurl(headimgurl);
        setToken(token);
        setJoin_time(join_time);
        setLogined(yes);
        setPoint(point);
        setLevel(level);
        setLove_code(love_code);
        setSigner(signer);
        setPay_password(pay_password);
    }

    /**
     * 关闭除nowactivity之外的所有Activity
     *
     * @param
     */
    public void exits(Activity nowactivity) {
//        activityList.get(1).finish();
        try {
            for (Activity activity : activityList) {
                if (activity == nowactivity) {
                    continue;
                }
                if (activity != null) {
                    activity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exit_1() {
        activityList.get(activityList.size()).finish();
        activityList.get(activityList.size()-1).finish();
    }

    @Override
    public void onCreate() {
        super.onCreate();
            share = getSharedPreferences(SystemConstant.SHARED_PREFERENCE_NAME,
                    MODE_PRIVATE);
            singleton = this;
        CrashReport.initCrashReport(getApplicationContext(), "70042f5bc3", false);
//            Thread.setDefaultUncaughtExceptionHandler(restartHandler); // 程序崩溃时触发线程
//            BPApplication.getInstance().setLogined(false);
    }



    /**
     * 异常捕获，重启应用
     */
    public UncaughtExceptionHandler restartHandler = new UncaughtExceptionHandler() {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
//            exit();
        }
    };

    /**
     * 杀进程
     */
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    public SharedPreferences getShare() {
        return share;
    }


    public String getName() {
        return share.getString("name", "");
    }

    public void setName(String name) {
        Editor editor = share.edit();
        editor.putString("name", name);
        editor.commit();
    }

    public String getToken() {
        return share.getString("token", "");
    }

    public void setToken(String token) {
        Editor editor = share.edit();
        editor.putString("token", token);
        editor.commit();
    }

    public String getHeadimgurl() {
        return share.getString("headimgurl", "");
    }

    public void setHeadimgurl(String headimgurl) {
        Editor editor = share.edit();
        editor.putString("headimgurl", headimgurl);
        editor.commit();
    }

    public String getLove_code() {
        return share.getString("love_code", "");
    }

    public void setLove_code(String love_code) {
        Editor editor = share.edit();
        editor.putString("love_code", love_code);
        editor.commit();
    }


    public String getBanlance() {
        return share.getString("banlance", "");
    }

    public void setBanlance(String banlance) {
        Editor editor = share.edit();
        editor.putString("banlance", banlance);
        editor.commit();
    }
    public String getJoin_time() {
        return share.getString("join_time", "");
    }

    public void setJoin_time(String join_time) {
        Editor editor = share.edit();
        editor.putString("join_time", join_time);
        editor.commit();
    }

    public boolean isLogined() {
        return share.getBoolean("logined", false);
    }

    public void setLogined(boolean logined) {
        Editor editor = share.edit();
        editor.putBoolean("logined", logined);
        editor.commit();
    }

    public String getWeChat_1() {
        return share.getString("wechat_1", "");
    }

    public void setWeChat_1(String wechat1) {
        Editor editor = share.edit();
        editor.putString("wechat_1", wechat1);
        editor.commit();
    }


    public String getWeChat_2() {
        return share.getString("wechat_2", "");
    }

    public void setWeChat_2(String wechat2) {
        Editor editor = share.edit();
        editor.putString("wechat_2", wechat2);
        editor.commit();
    }

    public String getWeChat_3() {
        return share.getString("wechat_3", "");
    }

    public void setWeChat_3(String wechat3) {
        Editor editor = share.edit();
        editor.putString("wechat_3", wechat3);
        editor.commit();
    }

    public String getWeChat_4() {
        return share.getString("wechat_4", "");
    }

    public void setWeChat_4(String wechat4) {
        Editor editor = share.edit();
        editor.putString("wechat_4", wechat4);
        editor.commit();
    }
    public String getMobile() {
        return share.getString("mobile", "");
    }

    public void setMobile(String mobile) {
        Editor editor = share.edit();
        editor.putString("mobile", mobile);
        editor.commit();
    }

    public String getPoint() {
        return share.getString("point", "");
    }

    public void setPoint(String point) {
        Editor editor = share.edit();
        editor.putString("point", point);
        editor.commit();
    }

    public String getSigner() {
        return share.getString("signer", "");
    }

    public void setSigner(String signer) {
        Editor editor = share.edit();
        editor.putString("signer", signer);
        editor.commit();
    }


    public String getPay_password() {
        return share.getString("pay_password", "");
    }

    public void setPay_password(String signer) {
        Editor editor = share.edit();
        editor.putString("pay_password", signer);
        editor.commit();
    }


    public String getLevel() {
        return share.getString("level", "");
    }

    public void setLevel(String level) {
        Editor editor = share.edit();
        editor.putString("level", level);
        editor.commit();
    }


    public String getCard_Id() {
        return share.getString("card_id", "");
    }

    public void setCard_Id(String card_id) {
        Editor editor = share.edit();
        editor.putString("card_id", card_id);
        editor.commit();
    }

    public String getMember_Id() {
        return share.getString("member_id", "");
    }

    public void setMember_Id(String member_id) {
        Editor editor = share.edit();
        editor.putString("member_id", member_id);
        editor.commit();
    }

    public String getTitle() {
        return share.getString("title", "");
    }

    public void setTitle(String title) {
        Editor editor = share.edit();
        editor.putString("title", title);
        editor.commit();
    }


    public String getUpdate() {
        return share.getString("update", "");
    }

    public void setUpdate(String update) {
        Editor editor = share.edit();
        editor.putString("update", update);
        editor.commit();
    }
}
















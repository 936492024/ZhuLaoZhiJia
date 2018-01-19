package com.zhulaozhijias.zhulaozhijia.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.allenliu.versionchecklib.core.AllenChecker;
import com.allenliu.versionchecklib.core.VersionParams;
import com.allenliu.versionchecklib.core.http.HttpParams;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;
import com.bumptech.glide.Glide;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.jauker.widget.BadgeView;
import com.zhulaozhijias.zhulaozhijia.activity.FoundationActivity;
import com.zhulaozhijias.zhulaozhijia.activity.HuZhu_Invition;
import com.zhulaozhijias.zhulaozhijia.activity.HuZhu_PlanActivity;
import com.zhulaozhijias.zhulaozhijia.activity.Invite_FriendActivity;
import com.zhulaozhijias.zhulaozhijia.activity.LoginActivity;
import com.zhulaozhijias.zhulaozhijia.activity.MapActivity;
import com.zhulaozhijias.zhulaozhijia.activity.TrendsActivity;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.model.BackHandledFragment;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.service.DemoService_2;
import com.zhulaozhijias.zhulaozhijia.service.ServiceLogin;
import com.zhulaozhijias.zhulaozhijia.utils.StringUtils;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.AutoTextView;
import com.zhulaozhijias.zhulaozhijia.widgets.Connect_Check;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.widgets.GlideCircleTransform;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;
import com.zhulaozhijias.zhulaozhijia.widgets.UPMarqueeView;
import com.zhulaozhijias.zhulaozhijia.widgets.eventbus.MainSendEvent;
import com.zhulaozhijias.zhulaozhijia.widgets.updata.CustomVersionDialogActivity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import de.greenrobot.event.EventBus;
import fm.jiecao.jcvideoplayer_lib.JCMediaManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/9/4.
 */

public class Fragment_1 extends BackHandledFragment implements View.OnClickListener, MainView{
    private Boolean user_first;
    private ToastUtil toastUtil;
    private RelativeLayout anli_button,huzhu_btn,heart_rank_btn,invite_friend,Relative,lub_rt;
    private TextView shouye_name,shouye_day,shouye_point,lunbo_text;
    private List<String> listss = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private List<View> views = new ArrayList<>();
    private Intent intent;
    private MainPresenter presenter;
    private JCVideoPlayerStandard jcVideoPlayerStandard;
    private ScrollView scrollView;
    private ImageView sign_btn,lunbo_touxiang;
//    private HeartHonorLayout heart_layout;
    private Random mRandom = new Random();
    private UPMarqueeView marqueeView;
    private ImageView juanzengjilu,heart_time;
    private TextView shouyexinx,shouye_day_text;
    private int sum_dialog=0;
    private int a=1;
    private boolean isGetData = false;
    private ServiceLogin serviceLogin = new ServiceLogin();
    private int sum =0;
    private JSONArray jsonArray2;
    private ImageView diandian,china;
    private long time = 0;
    final Handler handler = new Handler();
    private AutoTextView mTextView02;
    private static int sCount = 0;
    private float  mPosY,mCurPosY;
    private  Dialog dialog;
    private View view_dialog;
    private LinearLayout shouye_tianshu,lin_weight_up;
//    private SampleView sampleView;
    private List<String> name_list=new ArrayList<>();//姓名
    private List<String> amount_list=new ArrayList<>();//钱
    private List<String> time_list=new ArrayList<>();//时间
    private List<String> img_list = new ArrayList<>();//头像
    private Float up=(float)0.8;
    private Float down=(float)0.2;

    Handler handlers=new Handler();
    Runnable runnables=new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            handlers.postDelayed(this, 20000);
            danmu();
        }
    };

    Handler handlerss=new Handler();
    Runnable runnabless=new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
                    china_dialog();
                handlerss.removeCallbacks(runnabless);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_1,null);
        initview(view);
        return view;
    }

    public void initview(View view){
        EventBus.getDefault().register(this);
        presenter = new MainPresenter(getContext(), this);
        anli_button= (RelativeLayout) view.findViewById(R.id.anli_button);
        huzhu_btn= (RelativeLayout) view.findViewById(R.id.huzhu_btn);
        heart_rank_btn= (RelativeLayout) view.findViewById(R.id.heart_rank_btn);
        invite_friend= (RelativeLayout) view.findViewById(R.id.invite_friend);
        anli_button.setOnClickListener(this);
        huzhu_btn.setOnClickListener(this);
        heart_rank_btn.setOnClickListener(this);
        invite_friend.setOnClickListener(this);
        lunbo_touxiang= (ImageView) view.findViewById(R.id.lunbo_touxiang);
        lunbo_text= (TextView) view.findViewById(R.id.lunbo_text);
        lub_rt= (RelativeLayout) view.findViewById(R.id.lub_rt);
        scrollView=(ScrollView)view.findViewById(R.id.scrollView);
        jcVideoPlayerStandard = (JCVideoPlayerStandard) view.findViewById(R.id.jcVideoPlayerStandard);
        sign_btn=(ImageView) view.findViewById(R.id.sign_btn);
        sign_btn.setOnClickListener(this);
//        heart_layout= (HeartHonorLayout) view.findViewById(R.id.heart_layout);
        marqueeView=(UPMarqueeView) view.findViewById(R.id.marqueeView);
        shouye_day= (TextView) view.findViewById(R.id.shouye_day);
        shouye_name= (TextView) view.findViewById(R.id.shouye_name);
        shouye_point= (TextView) view.findViewById(R.id.shouye_point);
        heart_time= (ImageView) view.findViewById(R.id.heart_time);
        juanzengjilu= (ImageView) view.findViewById(R.id.juanzengjilu);
        //已为您的健康提供保障
        shouyexinx= (TextView) view.findViewById(R.id.shouyexinx);
        shouyexinx.setOnClickListener(this);
        shouye_tianshu= (LinearLayout) view.findViewById(R.id.shouye_tianshu);//下
        lin_weight_up= (LinearLayout) view.findViewById(R.id.lin_weight_up);//上
        //天
        shouye_day_text= (TextView) view.findViewById(R.id.shouye_day_text);
        juanzengjilu.setOnClickListener(this);
        diandian= (ImageView) view.findViewById(R.id.diandian);
        mTextView02= (AutoTextView) view.findViewById(R.id.mTextView02);
        Relative= (RelativeLayout) view.findViewById(R.id.Relative);
        china= (ImageView) view.findViewById(R.id.china);
        china.setOnClickListener(this);
//        sampleView= (SampleView) view.findViewById(R.id.sampleView);
//        sampleView.bringToFront();
        lub_rt.setVisibility(View.GONE);
        if(BPApplication.getInstance().getTitle().equals("1")){
            handlerss.postDelayed(runnabless, 500);
        }
        handlers.postDelayed(runnables, 20000);//每两秒执行一次runnable.
        if (BPApplication.getInstance().isLogined()) {
            lub_rt.setVisibility(View.VISIBLE);
        }
        danmu();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(Connect_Check.getCurrentNetType(getContext())==1){
                    updata();
                    checkIsLogined_3();
                    presenter.getRequest(SystemConstant.PublicConstant.Public_BROADCAST);
                }else if(Connect_Check.getCurrentNetType(getContext())==2){
                    toastUtil.showToast(getContext(), "你正在使用移动数据流量");
                    updata();
                    checkIsLogined_3();
                    presenter.getRequest(SystemConstant.PublicConstant.Public_BROADCAST);
                }
                else if(Connect_Check.getCurrentNetType(getContext())==0){
                    toastUtil.showToast(getContext(), "网络不给力，请检查网络设置");
                }
                jcVideoPlayerStandard.setUp("http://oxycohppa.bkt.clouddn.com/%E9%A6%96%E9%A1%B5video.mp4", JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
                Glide.with(getActivity()).load("http://oxycohppa.bkt.clouddn.com/homebac_1.png").into(jcVideoPlayerStandard.thumbImageView);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3500);
                    if(BPApplication.getInstance().getTitle().equals("1")){
                        if(sum_dialog==0){
                            if(dialog.isShowing()){
                                dialog.dismiss();
                                sum_dialog=1;
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.anli_button:
                intent= new Intent(getContext(), FoundationActivity.class);
                startActivity(intent);
                break;
            case R.id.huzhu_btn:
                intent= new Intent(getContext(), HuZhu_PlanActivity.class);
                startActivity(intent);
                break;
            case R.id.heart_rank_btn:
                if(!checkIsLogined()){
                    return;
                }else {
                    intent = new Intent(getContext(), MapActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.invite_friend:
                if(!checkIsLogined()){
                    return;
                }else {
                    intent = new Intent(getContext(), HuZhu_Invition.class);
                    startActivity(intent);
                }
                break;
            case R.id.sign_btn:
                if(!checkIsLogined()){
                    return;
                }else {
                    if(a==1){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Map<String, String> map = new HashMap<>();
                                String Member_Id = BPApplication.getInstance().getMember_Id();
                                map.put("member_id", Member_Id);
                                map.put("secret", CreateMD5.getMd5(Member_Id + "z!l@z#j$"));
                                presenter.wodess(SystemConstant.LoginedInformatio.USER_SIGN, map);
//                                heart_layout.addHeart(randomColor());
                            }
                        });
                        a=2;
                    }else {
//                        heart_layout.addHeart(randomColor());
                    }
                }
                break;
            case R.id.juanzengjilu:
                if(!checkIsLogined()){
                    return;
                }else {
                    intent= new Intent(getContext(), Invite_FriendActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.shouyexinx:
                if (!checkIsLogined()) {
                    return;
                }
                break;
            case R.id.china:
                china_dialog();
                break;
        }
    }

    @Override
    public void getView(final String s) {
        Log.e("dasdssadda",s);
        if (getActivity() == null) {
            return;
        }else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONArray jsStr = JSONArray.fromObject(s);
                    for (int i = 0; i < jsStr.size(); i++) {
                        JSONObject jsStrs = jsStr.getJSONObject(i);
                        String article_id = jsStrs.getString("article_id");
                        String title = jsStrs.getString("title");
                        listss.add(article_id);
                        list.add(title);
                    }
                    sCount = list.size();
                    mTextView02.setText(list.get(0));
                    //启动计时器
                    handler.postDelayed(runnable, 2000);
                }
            });
        }
    }

    @Override
    public void postView(final String s) {
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
               JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")) {
                    if(jsonObject.getString("money").equals("null")){
                        lunbo_text.setText("我已捐款0元");
                    }else {
                    lunbo_text.setText("我已捐款"+jsonObject.getString("money")+"元");
                    }
                    Glide.with(getContext()).load(jsonObject.getString("img")).placeholder(R.mipmap.portrait)
                            .error(R.mipmap.portrait).transform(new GlideCircleTransform(getContext())).into(lunbo_touxiang);
                    }else {
                }
            }
        });
    }

    @Override
    public void postViews(String s) {

    }

    @Override
    public void postViewss(final String s) {

        if (getActivity() == null) {
            return;
        }else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = JSONObject.fromObject(s);
                    if (jsonObject.getString("success").equals("true")) {
                        shouye_point.setText(jsonObject.getString("love_code"));
                        sign_btn.setImageResource(R.drawable.sign2);
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                        builder.setTitle("签到成功");
                        builder.setMessage("您本次获得"+jsonObject.getString("point")+"颗心");
                        builder.setCancelable(false);
                        builder.setPositiveButton("确定",null);
                        builder.setIcon(R.drawable.ic_launcher);
                        builder.show();
                    } else {

                    }
                }
            });
        }
    }

    @Override
    public void postViewsss(final String s) {
        Log.e("dsadadas",s);
        if (getActivity() == null) {
            return;
        }else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = JSONObject.fromObject(s);
                        if (jsonObject.size() != 0) {
                            if(!TextUtils.isEmpty(jsonObject.getString("notice_count"))) {
                                if(jsonObject.getString("is_join_plan").equals("false")){//已经加入计划
                                    shouye_tianshu.setVisibility(View.VISIBLE);
                                    shouye_day.setText(jsonObject.getString("join_plan_time"));
                                }else {//未加入
                                    shouye_tianshu.setVisibility(View.INVISIBLE);
                                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,down);
                                    shouye_tianshu.setLayoutParams(lp);
                                    LinearLayout.LayoutParams lplp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,0,up);
                                    lin_weight_up.setLayoutParams(lplp);
                                }
                                shouye_point.setText(jsonObject.getString("love_code"));
                                String aa = jsonObject.getString("notice_count");
                                BadgeView badgeView = new com.jauker.widget.BadgeView(getContext());
                                badgeView.setTargetView(diandian);
                                badgeView.setBadgeCount(Integer.valueOf(aa));
                            }
                        }
                }
            });
        }
    }

    @Override
    public void postViewsss_1(final String s) {

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

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
            if (getActivity() == null) {
            } else {
                if (enter && !isGetData) {
//                    marqueeView.startFlipping();
                    isGetData = true;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Map<String ,String> maps = new HashMap<>();
                            maps.put("member_id",BPApplication.getInstance().getMember_Id());
                            maps.put("secret",CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"z!l@z#j$"));
                            Map<String, String> map = new HashMap<>();
                            map.put("member_id", BPApplication.getInstance().getMember_Id());
                            map.put("secret", CreateMD5.getMd5(BPApplication.getInstance().getMember_Id() + "z!l@z#j$"));
                            if (!BPApplication.getInstance().getMember_Id().equals("")||!TextUtils.isEmpty(BPApplication.getInstance().getMember_Id())) {
                                if (Connect_Check.getCurrentNetType(getContext()) == 1) {
                                    if(!TextUtils.isEmpty(BPApplication.getInstance().getMember_Id())){
                                        presenter.postMap(SystemConstant.PublicConstant.Public_WECHAT_LUNBO,maps);
                                    }
                                    presenter.wodesss(SystemConstant.HuZhuPlan.NOTICE_MEMBER, map);
                                } else if (Connect_Check.getCurrentNetType(getContext()) == 2) {
                                    if(!TextUtils.isEmpty(BPApplication.getInstance().getMember_Id())){
                                        presenter.postMap(SystemConstant.PublicConstant.Public_WECHAT_LUNBO,maps);
                                    }
                                    presenter.wodesss(SystemConstant.HuZhuPlan.NOTICE_MEMBER, map);
                                } else if (Connect_Check.getCurrentNetType(getContext()) == 0) {
                                    toastUtil.showToast(getContext(), "网络不给力，请检查网络设置");
                                }
                            } else {

                            }
                        }
                    });
                } else {
                    if (JCMediaManager.instance().mediaPlayer.isPlaying()) {
                        JCVideoPlayer.releaseAllVideos();
                    }
//                    marqueeView.stopFlipping();
                    isGetData = false;
                }
            }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public boolean onBackPressed() {
        if (System.currentTimeMillis() - time > 2000) {
            Toast.makeText(getActivity(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            jcVideoPlayerStandard.backPress();
            time = System.currentTimeMillis();
            return true;
        } else {
            BPApplication.getInstance().setLogined(false);
            return false;
        }
    }

    @Override
    public void onDestroy() {
        handlers.removeCallbacks(runnables);
        super.onDestroy();
    }

    //事件接受
    public void onEventMainThread(final MainSendEvent event){
        if (getActivity() == null) {
            return;
        }else {
        if(event != null){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                  if(event.getStringMsgData().equals("自动登录成功")){
                        if(sum==0){
                            checkIsLogined_2();
                            sum=1;
                        }
                    }
                    }
                });
        }
        }
    }

    private void setView() {
        for (int i = 0; i < name_list.size(); i=i+1) {
            final int position = i;
            //设置滚动的单个布局;
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_view, null);
            //初始化布局的控件
            TextView tv1 = (TextView) moreView.findViewById(R.id.tv1);
            TextView tv2 = (TextView) moreView.findViewById(R.id.tv2);
            ImageView lun_im1 = (ImageView) moreView.findViewById(R.id.lun_im1);
            ImageView lun_im2 = (ImageView) moreView.findViewById(R.id.lun_im2);
            /**
             * 设置监听
             */
            moreView.findViewById(R.id.linearlayout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    intent = new Intent(getContext(), TrendsActivity.class);
//                    intent.putExtra("article_id",listss.get(position));
//                    startActivity(intent);
                }
            });

            //进行对控件赋值
            Glide.with(getContext()).load(img_list.get(i)).placeholder(R.mipmap.portrait)
                    .error(R.mipmap.portrait).transform(new GlideCircleTransform(getContext())).into(lun_im1);
            tv1.setText(name_list.get(i)+" 捐款"+amount_list.get(i)+"元 "+time_list.get(i));
            if (name_list.size() > i + 1) {
                tv2.setText(name_list.get(i+1)+" 捐款"+amount_list.get(i+1)+"元 "+time_list.get(i+1));
                Glide.with(getContext()).load(img_list.get(i+1)).placeholder(R.mipmap.portrait)
                        .error(R.mipmap.portrait).transform(new GlideCircleTransform(getContext())).into(lun_im2);
            } else {
                tv2.setText(name_list.get(0)+" 捐款"+amount_list.get(0)+"元 "+time_list.get(0));
                Glide.with(getContext()).load(img_list.get(0)).placeholder(R.mipmap.portrait)
                        .error(R.mipmap.portrait).transform(new GlideCircleTransform(getContext())).into(lun_im2);
                moreView.findViewById(R.id.rl2).setVisibility(View.VISIBLE);
            }
            //添加到循环滚动数组里面去
            views.add(moreView);
        }
        marqueeView.setViews(views);
    }

    private int randomColor() {
        return Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255));
    }

    private boolean checkIsLogined() {
        if (!BPApplication.getInstance().isLogined()) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            return false;
        }
        return true;
    }

    private void checkIsLogined_3(){
        if(!TextUtils.isEmpty(BPApplication.getInstance().getMember_Id())){
        lub_rt.setVisibility(View.VISIBLE);
        String join_time = BPApplication.getInstance().getJoin_time();
        String name = BPApplication.getInstance().getName();
        String level =BPApplication.getInstance().getLevel();
        shouye_point.setText(BPApplication.getInstance().getLove_code());
        Date a = new Date();
        long interval = ((a.getTime()) / 1000) - (Long.valueOf(join_time));
        shouyexinx.setText("已为您的健康提供服务");
        shouye_day_text.setVisibility(View.VISIBLE);
//        shouye_day.setText(String.valueOf((interval / 86400)+1));
        shouye_name.setText(name);
        if(BPApplication.getInstance().getSigner().equals("true")){
            sign_btn.setImageResource(R.drawable.sign2);
        }else {
            sign_btn.setImageResource(R.drawable.sign);
        }
        switch (level){
            case "1":
                heart_time.setImageResource(R.drawable.heart_fense_1);
                shouye_point.setTextColor(0xFFffa3b3);
                shouye_point.setBackgroundResource(R.drawable.start_1);
                break;
            case "2":
                heart_time.setImageResource(R.drawable.heart_orange_2);
                shouye_point.setTextColor(0xFFe97b23);
                shouye_point.setBackgroundResource(R.drawable.start_2);
                break;
            case "3":
                heart_time.setImageResource(R.drawable.heart_hongse_3);
                shouye_point.setTextColor(0xFFFF1E1E);
                shouye_point.setBackgroundResource(R.drawable.start_3);
                break;
            case "4":
                heart_time.setImageResource(R.drawable.heart_zise_4);
                shouye_point.setTextColor(0xFFd21fec);
                shouye_point.setBackgroundResource(R.drawable.start_4);
                break;
            case "5":
                heart_time.setImageResource(R.drawable.heart_jinse_5);
                shouye_point.setTextColor(0xFFffe631);
                shouye_point.setBackgroundResource(R.drawable.start_5);
                break;
        }
//        if(level.equals("1")){
//
//        }else if(level.equals("2")){
//
//        }else if(level.equals("3")){
//
//        }
//        else if(level.equals("4")){
//
//        }else if(level.equals("5")){
//
//        }
        }
    }

    private boolean checkIsLogined_2() {
        if (BPApplication.getInstance().isLogined()) {
            String join_time = BPApplication.getInstance().getJoin_time();
            String name = BPApplication.getInstance().getName();
            String level =BPApplication.getInstance().getLevel();
            shouye_point.setText(BPApplication.getInstance().getLove_code());
            Date a = new Date();
            long interval = ((a.getTime()) / 1000) - (Long.valueOf(join_time));
            shouyexinx.setText("已为您的健康提供服务");
            shouye_day_text.setVisibility(View.VISIBLE);
            shouye_name.setText(name);
            if(BPApplication.getInstance().getSigner().equals("true")){
                sign_btn.setImageResource(R.drawable.sign2);
            }else {
                sign_btn.setImageResource(R.drawable.sign);
            }
            switch (level){
                case "1":
                    heart_time.setImageResource(R.drawable.heart_fense_1);
                    shouye_point.setTextColor(0xFFffa3b3);
                    shouye_point.setBackgroundResource(R.drawable.start_1);
                    break;
                case "2":
                    heart_time.setImageResource(R.drawable.heart_orange_2);
                    shouye_point.setTextColor(0xFFe97b23);
                    shouye_point.setBackgroundResource(R.drawable.start_2);
                    break;
                case "3":
                    heart_time.setImageResource(R.drawable.heart_hongse_3);
                    shouye_point.setTextColor(0xFFFF1E1E);
                    shouye_point.setBackgroundResource(R.drawable.start_3);
                    break;
                case "4":
                    heart_time.setImageResource(R.drawable.heart_zise_4);
                    shouye_point.setTextColor(0xFFd21fec);
                    shouye_point.setBackgroundResource(R.drawable.start_4);
                    break;
                case "5":
                    heart_time.setImageResource(R.drawable.heart_jinse_5);
                    shouye_point.setTextColor(0xFFffe631);
                    shouye_point.setBackgroundResource(R.drawable.start_5);
                    break;
            }
//            if(level.equals("1")){
//                heart_time.setImageResource(R.drawable.heart_fense_1);
//                shouye_point.setTextColor(0xFFffa3b3);
//                shouye_point.setBackgroundResource(R.drawable.start_1);
//            }else if(level.equals("2")){
//                heart_time.setImageResource(R.drawable.heart_orange_2);
//                shouye_point.setTextColor(0xFFe97b23);
//                shouye_point.setBackgroundResource(R.drawable.start_2);
//            }else if(level.equals("3")){
//                heart_time.setImageResource(R.drawable.heart_hongse_3);
//                shouye_point.setTextColor(0xFFFF1E1E);
//                shouye_point.setBackgroundResource(R.drawable.start_3);
//            }
//            else if(level.equals("4")){
//                heart_time.setImageResource(R.drawable.heart_zise_4);
//                shouye_point.setTextColor(0xFFd21fec);
//                shouye_point.setBackgroundResource(R.drawable.start_4);
//            }else if(level.equals("5")){
//                heart_time.setImageResource(R.drawable.heart_jinse_5);
//                shouye_point.setTextColor(0xFFffe631);
//                shouye_point.setBackgroundResource(R.drawable.start_5);
//            }
            return false;
        }
        return true;
    }

    public void danmu(){
        String url = SystemConstant.PublicConstant.Public_WECHAT_LUNBO;
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call,  Response response) throws IOException {
               final String responses = response.body().string();
                if (getActivity() == null) {
                    return;
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(!isGoodJson(responses)){
                            ToastUtil.showToast(getContext(),"网络连接错误，请检查网络设置");
                            return;
                        }
                        JSONObject jsonObject = JSONObject.fromObject(responses);
                        if(jsonObject.getString("success").equals("true")) {
                            name_list.clear();
                            amount_list.clear();
                            time_list.clear();
                            img_list.clear();
                            views.clear();
                            JSONArray jsonArray=JSONArray.fromObject(jsonObject.getString("data"));
                            JSONArray jsonArray1 = new JSONArray();
                            JSONObject jsonObject1 = new JSONObject();
                            for(int i= 0;i<jsonArray.size();i++){
                                jsonArray1.add(jsonArray.getJSONObject(i));
                            }
                            for(int j=0;j<jsonArray1.size();j++){
                                jsonObject1=jsonArray1.getJSONObject(j);
                                name_list.add(jsonObject1.getString("name")) ;
                                amount_list.add(jsonObject1.getString("amount"));
                                time_list.add(jsonObject1.getString("time"));
                                img_list.add(jsonObject1.getString("img"));
                            }
                            if(!TextUtils.isEmpty(BPApplication.getInstance().getMember_Id())){
                                Map<String ,String> map = new HashMap<>();
                                map.put("member_id",BPApplication.getInstance().getMember_Id());
                                map.put("secret",CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"z!l@z#j$"));
                                presenter.postMap(SystemConstant.PublicConstant.Public_WECHAT_LUNBO,map);
                            }
                            marqueeView.stopFlipping();
                            setView();
                        }
                    }
                });
            }
        });
    }

    public  boolean isGoodJson(String json) {
        if (StringUtils.isEmptyString(json)) {
            return false;
        }
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonParseException e) {
            Log.e("bad json: ",json);
            return false;
        }
    }

    public void china_dialog() {
        final LayoutInflater inflater = LayoutInflater.from(getContext());
        view_dialog= inflater.inflate(R.layout.china_dialog_layout, null);
//        final View rt_onclik =  view_dialog.findViewById(R.id.viewview);
        final RelativeLayout china_ima = (RelativeLayout) view_dialog.findViewById(R.id.china_ima);
        final RelativeLayout china_imas = (RelativeLayout) view_dialog.findViewById(R.id.china_imas);
//        final SampleView sampleView= (SampleView) view_dialog.findViewById(R.id.sampleView);
//        sampleView.startAnimation(mReverse);
        dialog= new Dialog(getContext(),R.style.CustomDialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);//
        dialog.show();
        // 设置宽度为屏幕的宽度
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (display.getWidth()); // 设置宽度
        lp.height = getResources().getDimensionPixelSize(R.dimen.china_dialog_height); // 设置高度
        Window window = dialog.getWindow();
        window.setGravity(Gravity.TOP);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setWindowAnimations(R.style.toppopwindow_anim_style);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setContentView(view_dialog);

//        rt_onclik.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sampleView.startAnimation(false);
//                handlerss.postDelayed(runnabless,1500);
//            }
//        });

        china_ima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });
        china_imas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mTextView02.next();
            sCount++;
            if(sCount>=Integer.MAX_VALUE){
                sCount = list.size();
            }
            mTextView02.setText(list.get(sCount % (list.size())));
            if (list.size()>1) {
                handler.postDelayed(this, 2000);// 50是延时时长
            }

            Relative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent = new Intent(getContext(), TrendsActivity.class);
                    intent.putExtra("article_id",listss.get(sCount % (list.size())));
                    startActivity(intent);
                }
            });

        }
    };

    public void updata(){
        try {
            int versionCode = getActivity().getPackageManager().getPackageInfo("com.zhulaozhijias.zhulaozhijia", 0).versionCode;
            HttpParams httpParams = new HttpParams();
            httpParams.put("name","APP");
            httpParams.put("version",versionCode+"");
            httpParams.put("secret", CreateMD5.getMd5("APP"+versionCode+"z!l@z#j$"));
            Log.e("httpParams",httpParams.toString());
            VersionParams.Builder builder = new VersionParams.Builder()
                    .setRequestUrl("http://hbhz.sesdf.org/update/check")
                    .setRequestMethod(HttpRequestMethod.POST)
                    .setRequestParams(httpParams)
                    .setService(DemoService_2.class);
            getActivity().stopService(new Intent(getContext(), DemoService_2.class));
            CustomVersionDialogActivity.customVersionDialogIndex = 2;
            builder.setCustomDownloadActivityClass(CustomVersionDialogActivity.class);
            CustomVersionDialogActivity.isCustomDownloading = false;
            builder.setCustomDownloadActivityClass(CustomVersionDialogActivity.class);
            CustomVersionDialogActivity.isForceUpdate = false;
            builder.setCustomDownloadActivityClass(CustomVersionDialogActivity.class);
            AllenChecker.startVersionCheck(getContext(), builder.build());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}


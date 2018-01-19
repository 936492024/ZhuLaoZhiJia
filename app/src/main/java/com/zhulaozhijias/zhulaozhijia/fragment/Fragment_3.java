package com.zhulaozhijias.zhulaozhijia.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.activity.Heart_RankActivity;
import com.zhulaozhijias.zhulaozhijia.activity.LoginActivity;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.model.BackHandledFragment;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.GlideCircleTransform;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

import static com.zhulaozhijias.zhulaozhijia.R.id.jcVideoPlayerStandard;

/**
 * Created by asus on 2017/9/18.
 */

public class Fragment_3 extends BackHandledFragment implements View.OnClickListener,MainView{
    private ImageView circle_1,circle_2,circle_3,circle_4,circle_5,circle_6,circle_7,circle_8;
    private TextView qq,qq_space,message,chakanxiangxi_guize;
    private RelativeLayout invite_touxiang,wechat_share,sina,wechat_moments;
    private LayoutInflater inflater;
    private Dialog dialog;
    private MainPresenter mainPresenter;
    private ArrayList<String> arrayList = new ArrayList<>();
    private String name;
    private long time = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3,null);
        getid(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    public void getid (View view) {
        mainPresenter=new MainPresenter(getContext(),this);
        circle_1= (ImageView) view.findViewById(R.id.circle_1);
        circle_2= (ImageView) view.findViewById(R.id.circle_2);
        circle_3= (ImageView) view.findViewById(R.id.circle_3);
        circle_4= (ImageView) view.findViewById(R.id.circle_4);
        circle_5= (ImageView) view.findViewById(R.id.circle_5);
        circle_6= (ImageView) view.findViewById(R.id.circle_6);
        circle_7= (ImageView) view.findViewById(R.id.circle_7);
        circle_8= (ImageView) view.findViewById(R.id.circle_8);
        name = BPApplication.getInstance().getName();
        wechat_share=(RelativeLayout) view.findViewById(R.id.wechat);
        wechat_share.setOnClickListener(this);
        wechat_moments= (RelativeLayout)view. findViewById(R.id.wechat_moments);
        wechat_moments.setOnClickListener(this);
        sina= (RelativeLayout)view. findViewById(R.id.sina);
        sina.setOnClickListener(this);
        qq= (TextView) view.findViewById(R.id.qq);
        qq.setOnClickListener(this);
        qq_space= (TextView)view. findViewById(R.id.qq_space);
        qq_space.setOnClickListener(this);
        message= (TextView)view. findViewById(R.id.message);
        message.setOnClickListener(this);
        chakanxiangxi_guize= (TextView)view. findViewById(R.id.chakanxiangxi_guize);
        chakanxiangxi_guize.setOnClickListener(this);
        invite_touxiang= (RelativeLayout) view.findViewById(R.id.invite_touxiang);
        invite_touxiang.setOnClickListener(this);
        Map<String,String> map = new HashMap<>();
        map.put("secret", CreateMD5.getMd5("z!l@z#j$"));
        mainPresenter.postMap(SystemConstant.GEREN_ZHONGXIN.Mine_INVITW_SHARE,map);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.wechat:
                if(!checkIsLogined()){
                    return;
                }else {
                    showShare(Wechat.NAME);
                }
                break;
            case R.id.wechat_moments:
                if(!checkIsLogined()){
                    return;
                }else {
                    showShare(WechatMoments.NAME);
                }
                break;
            case R.id.sina:
                if(!checkIsLogined()){
                    return;
                }else {
                    showShare(SinaWeibo.NAME);
                }
                break;
            case R.id.qq:
                if(!checkIsLogined()){
                    return;
                }else {
                    showShare(QQ.NAME);
                }
                break;
            case R.id.qq_space:
                if(!checkIsLogined()){
                    return;
                }else {
                    showShare(QZone.NAME);
                }
                break;
            case R.id.message:
                if(!checkIsLogined()){
                    return;
                }else {
                    sendSMS("http://app.sesdf.org/");
                }
                break;
            case R.id.chakanxiangxi_guize:
                join_plan_dialog4();
                break;
            case R.id.invite_touxiang:
                if(!checkIsLogined()){
                    return;
                }else {
                    Intent intent = new Intent(getContext(),Heart_RankActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    private   void  sendSMS(String webUrl){
        String smsBody = "爱心是一盏灯，照亮别人，温暖自己" + webUrl;
        Uri smsToUri = Uri.parse( "smsto:" );
        Intent sendIntent =  new  Intent(Intent.ACTION_VIEW, smsToUri);
        //sendIntent.putExtra("address", "123456"); // 电话号码，这行去掉的话，默认就没有电话
        //短信内容
        sendIntent.putExtra( "sms_body", smsBody);
        sendIntent.setType( "vnd.android-dir/mms-sms" );
        startActivityForResult(sendIntent, 1002 );
    }
    public void join_plan_dialog4(){
        inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.join_plan_dialog_4,null);
        TextView woyiyuedu= (TextView)view. findViewById(R.id.woyiyuedu);
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);//
        dialog.show();
        // 设置宽度为屏幕的宽度
        WindowManager windowManager = getActivity().getWindowManager();
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
    private void showShare(String platform) {
        final OnekeyShare oks = new OnekeyShare();
        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        if (platform != null) {
            oks.setPlatform(platform);
        }
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(name+"邀请你加入互帮互助基金");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://app.sesdf.org/");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("爱心是一盏灯，照亮别人，温暖自己");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://oxycohppa.bkt.clouddn.com/logo.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://app.sesdf.org/");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("爱心是一盏灯，照亮别人，温暖自己");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(name+"邀请你加入互帮互助基金");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://app.sesdf.org/");
        //启动分享
        oks.show(getContext());
    }
    private boolean checkIsLogined() {
        if (!BPApplication.getInstance().isLogined()) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            return false;
        }
        return true;
    }

    @Override
    public void getView(String s) {

    }

    @Override
    public void postView(final String s) {
        if (getActivity() == null) {
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONArray jsonArray = JSONArray.fromObject(s);
                if(jsonArray.size()!=0){
                    JSONArray jsonArray1 = new JSONArray();
                    JSONObject jsonObject;
                    for(int i=0;i<jsonArray.size();i++){
                        jsonArray1.add(jsonArray.getJSONObject(i));
                        jsonObject = jsonArray1.getJSONObject(i);
                        arrayList.add(jsonObject.getString("headimgurl"));
                    }
                    Glide.with(getContext()).load(arrayList.get(0)).placeholder(R.mipmap.portrait)
                            .error(R.mipmap.portrait).transform(new GlideCircleTransform(getContext())).into(circle_1);
                    Glide.with(getContext()).load(arrayList.get(1)).placeholder(R.mipmap.portrait)
                            .error(R.mipmap.portrait).transform(new GlideCircleTransform(getContext())).into(circle_2);
                    Glide.with(getContext()).load(arrayList.get(2)).placeholder(R.mipmap.portrait)
                            .error(R.mipmap.portrait).transform(new GlideCircleTransform(getContext())).into(circle_3);
                    Glide.with(getContext()).load(arrayList.get(3)).placeholder(R.mipmap.portrait)
                            .error(R.mipmap.portrait).transform(new GlideCircleTransform(getContext())).into(circle_4);
                    Glide.with(getContext()).load(arrayList.get(4)).placeholder(R.mipmap.portrait)
                            .error(R.mipmap.portrait).transform(new GlideCircleTransform(getContext())).into(circle_5);
                    Glide.with(getContext()).load(arrayList.get(5)).placeholder(R.mipmap.portrait)
                            .error(R.mipmap.portrait).transform(new GlideCircleTransform(getContext())).into(circle_6);
                    Glide.with(getContext()).load(arrayList.get(6)).placeholder(R.mipmap.portrait)
                            .error(R.mipmap.portrait).transform(new GlideCircleTransform(getContext())).into(circle_7);
                    Glide.with(getContext()).load(arrayList.get(7)).placeholder(R.mipmap.portrait)
                            .error(R.mipmap.portrait).transform(new GlideCircleTransform(getContext())).into(circle_8);
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

    @Override
    public boolean onBackPressed() {
        if (System.currentTimeMillis() - time > 2000) {
            Toast.makeText(getActivity(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            JCVideoPlayer.releaseAllVideos();
            time = System.currentTimeMillis();
            return true;
        } else {
            BPApplication.getInstance().setLogined(false);
            return false;
        }
    }


}

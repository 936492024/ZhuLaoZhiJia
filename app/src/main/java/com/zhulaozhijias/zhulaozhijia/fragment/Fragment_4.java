package com.zhulaozhijias.zhulaozhijia.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jauker.widget.BadgeView;
import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.activity.DetailsActivity;
import com.zhulaozhijias.zhulaozhijia.activity.Game_BettingActivity;
import com.zhulaozhijias.zhulaozhijia.activity.Game_ClockActivity;
import com.zhulaozhijias.zhulaozhijia.activity.Game_Time_BankActivity;
import com.zhulaozhijias.zhulaozhijia.activity.Heart_ExchangeActivity;
import com.zhulaozhijias.zhulaozhijia.activity.Heart_RankActivity;
import com.zhulaozhijias.zhulaozhijia.activity.HuZhu_Invition;
import com.zhulaozhijias.zhulaozhijia.activity.HuZhu_PingZhengActivity;
import com.zhulaozhijias.zhulaozhijia.activity.Invite_FriendActivity;
import com.zhulaozhijias.zhulaozhijia.activity.LoginActivity;
import com.zhulaozhijias.zhulaozhijia.activity.MainActivity;
import com.zhulaozhijias.zhulaozhijia.activity.Mine_AcountActivity;
import com.zhulaozhijias.zhulaozhijia.activity.Mine_GongyueActvity;
import com.zhulaozhijias.zhulaozhijia.activity.Mine_ProblemActivity;
import com.zhulaozhijias.zhulaozhijia.activity.Mine_RechargeActiviy;
import com.zhulaozhijias.zhulaozhijia.activity.Mine_SetActivity;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.Connect_Check;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.GlideCircleTransform;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;
import com.zhulaozhijias.zhulaozhijia.widgets.eventbus.MainSendEvent;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;


/**
 * Created by Administrator on 2017/9/4.
 */

public class Fragment_4 extends Fragment implements View.OnClickListener ,MainView {
    private ToastUtil toastUtil;
    private ImageView mine_head,mine_head_bac,heart_colour;
    private TextView mine_name,mine_dispoint;
    private RelativeLayout mine_recharge,mine_problem,mine_gongyue,
            heart_exchange,mine_huzhushenqing,mine_game_time_bank;
    private Intent intent;
    private RelativeLayout mine_set,mine_game_clock,mine_game_Betting,user_message,user_voucher,user_service;
    private MainPresenter mainPresenter;
    private boolean isGetData = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_4,null);
        initview(view);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkIsLogined_2();
    }
    @Override
    public void onClick(View view) {
          switch (view.getId()){
              case R.id.mine_recharge:
                  if(!checkIsLogined()){
                      return;
                  }else {
                      intent=new Intent(getContext(), Mine_RechargeActiviy.class);
                      startActivity(intent);
                  }

                  break;
              case R.id.user_message:
                  if(!checkIsLogined()){
                      return;
                  }else {
                      intent=new Intent(getContext(), Mine_AcountActivity.class);
                      startActivity(intent);
                  }
                  break;
              case R.id.mine_huzhushenqing:
                  if(!checkIsLogined()){
                      return;
                  }else {
                      intent = new Intent(getContext(), Game_Time_BankActivity.class);
                      startActivity(intent);
                  }
                  break;
              case R.id.mine_set:
                  if(!checkIsLogined()){
                      return;
                  }else {
                      intent = new Intent(getContext(), Mine_SetActivity.class);
                      startActivityForResult(intent,0);
                  }
                  break;
              case R.id.user_service:
                  Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "028-96552"));
                  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                  startActivity(intent);
                  break;
              case R.id.heart_exchange:
                  if(!checkIsLogined()){
                      return;
                  }else {
                      intent = new Intent(getContext(), Heart_RankActivity.class);
                      startActivity(intent);
                  }
                  break;
              case R.id.user_voucher:
                  if(!checkIsLogined()){
                      return;
                  }else {
                      intent = new Intent(getContext(), HuZhu_PingZhengActivity.class);
                      startActivity(intent);
                  }
                  break;
              case R.id.mine_gongyue:
                  intent = new Intent(getContext(), Mine_GongyueActvity.class);
                  startActivity(intent);
                  break;
              case R.id.mine_problem:
                  intent= new Intent(getContext(), Mine_ProblemActivity.class);
                  startActivity(intent);
                  break;
              case R.id.mine_game_clock:
                  if(TextUtils.isEmpty(BPApplication.getInstance().getMember_Id())){
                      intent = new Intent(getContext(),LoginActivity.class);
                      startActivity(intent);
                      return;
                  }
                  if(!checkIsLogined()){
                      return;
                  }else {
                      intent = new Intent(getContext(), Game_ClockActivity.class);
                      startActivity(intent);
                  }
                  break;
              case R.id.mine_game_Betting:
                  if(!checkIsLogined()){
                      return;
                  }else {
                      intent = new Intent(getContext(), Game_BettingActivity.class);
                      startActivity(intent);
                  }
                  break;
              case R.id.mine_game_time_bank:
                  Toast.makeText(getContext(),"版本更新中",Toast.LENGTH_SHORT).show();
//                  if(!checkIsLogined()){
//                      return;
//                  }else {
//                      intent = new Intent(getContext(), Game_Time_BankActivity.class);
//                      startActivity(intent);
//                  }
                  break;
              case R.id.mine_head:
                  if(!checkIsLogined()){
                      return;
                  }else {
                      intent = new Intent(getContext(), Mine_SetActivity.class);
                      startActivityForResult(intent,0);
                  }
                  break;


          }
    }
    public void initview(View view){
        EventBus.getDefault().register(this);
        mainPresenter=new MainPresenter(getContext(),this);
        mine_head= (ImageView) view.findViewById(R.id.mine_head);
        mine_head.setOnClickListener(this);
        mine_head_bac=(ImageView) view.findViewById(R.id.mine_head_bac);
        Glide.with(getContext()).load(R.drawable.mine_head_bac).transform(new GlideCircleTransform(getContext())).into(mine_head_bac);
        user_message = (RelativeLayout) view.findViewById(R.id.user_message);
        user_message.setOnClickListener(this);
        user_voucher= (RelativeLayout) view.findViewById(R.id.user_voucher);
        user_voucher.setOnClickListener(this);
        user_service= (RelativeLayout) view.findViewById(R.id.user_service);
        user_service.setOnClickListener(this);
        Glide.with(getContext()).load(BPApplication.getInstance().getHeadimgurl()).placeholder(R.mipmap.portrait)
                .error(R.mipmap.portrait) .transform(new GlideCircleTransform(getContext())).into(mine_head);
        mine_recharge=(RelativeLayout)view.findViewById(R.id.mine_recharge);
        mine_recharge.setOnClickListener(this);

        mine_huzhushenqing= (RelativeLayout) view.findViewById(R.id.mine_huzhushenqing);
        mine_huzhushenqing.setOnClickListener(this);

        mine_set= (RelativeLayout) view.findViewById(R.id.mine_set);
        mine_set.setOnClickListener(this);

        heart_exchange= (RelativeLayout) view.findViewById(R.id.heart_exchange);
        heart_exchange.setOnClickListener(this);

        mine_name= (TextView) view.findViewById(R.id.mine_name);
        mine_dispoint= (TextView) view.findViewById(R.id.mine_dispoint);

        mine_gongyue= (RelativeLayout) view.findViewById(R.id.mine_gongyue);
        mine_gongyue.setOnClickListener(this);
        mine_problem= (RelativeLayout) view.findViewById(R.id.mine_problem);
        mine_problem.setOnClickListener(this);

        mine_game_clock= (RelativeLayout) view.findViewById(R.id.mine_game_clock);
        mine_game_clock.setOnClickListener(this);
        mine_game_Betting= (RelativeLayout) view.findViewById(R.id.mine_game_Betting);
        mine_game_Betting.setOnClickListener(this);
        mine_game_time_bank= (RelativeLayout) view.findViewById(R.id.mine_game_time_bank);
        mine_game_time_bank.setOnClickListener(this);
        heart_colour= (ImageView) view.findViewById(R.id.heart_colour);

    }
    //事件接受
    public void onEventMainThread(MainSendEvent event){
        if(event != null){
            if(event.getStringMsgData().equals("Headimgurl")){
                Glide.with(getContext()).load(BPApplication.getInstance().getHeadimgurl()).placeholder(R.mipmap.portrait)
                        .error(R.mipmap.portrait) .transform(new GlideCircleTransform(getContext())).into(mine_head);
            }
        }
    }
    @Override
    public void onDestroy() {
        //取消注册
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (getActivity() == null) {
        }else {
            if (enter && !isGetData) {
                isGetData = true;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> map = new HashMap<>();
                        map.put("member_id", BPApplication.getInstance().getMember_Id());
                        map.put("secret", CreateMD5.getMd5(BPApplication.getInstance().getMember_Id() + "z!l@z#j$"));
                        if (!BPApplication.getInstance().getMember_Id().equals("")&&!TextUtils.isEmpty(BPApplication.getInstance().getMember_Id())) {
                            mainPresenter.postMap(SystemConstant.HuZhuPlan.NOTICE_MEMBER, map);
                            if (Connect_Check.getCurrentNetType(getContext()) == 0) {
                                toastUtil.showToast(getContext(), "网络不给力，请检查网络设置");
                            }
                        }else {

                        }
                    }


                });
            } else {
                isGetData = false;
            }
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }


    private boolean checkIsLogined() {
        if (!BPApplication.getInstance().isLogined()) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            return false;
        }
        return true;
    }

    private boolean checkIsLogined_2() {
        if (BPApplication.getInstance().isLogined()) {
            String name = BPApplication.getInstance().getName();
            String point =BPApplication.getInstance().getLove_code();
            String level =BPApplication.getInstance().getLevel();
            mine_name.setText(name);
            Glide.with(getContext()).load(BPApplication.getInstance().getHeadimgurl()).placeholder(R.mipmap.portrait)
                    .error(R.mipmap.portrait) .transform(new GlideCircleTransform(getContext())).into(mine_head);
            if(level.equals("1")){
                heart_colour.setImageResource(R.drawable.heart_fense_1);
                mine_dispoint.setTextColor(0xFFffa3b3);
                mine_dispoint.setBackgroundResource(R.drawable.start_1);
            }else if(level.equals("2")){
                heart_colour.setImageResource(R.drawable.heart_orange_2);
                mine_dispoint.setTextColor(0xFFe97b23);
                mine_dispoint.setBackgroundResource(R.drawable.start_2);
            }else if(level.equals("3")){
                heart_colour.setImageResource(R.drawable.heart_hongse_3);
                mine_dispoint.setTextColor(0xFFff1e1e);
                mine_dispoint.setBackgroundResource(R.drawable.start_3);
            }
            else if(level.equals("4")){
                heart_colour.setImageResource(R.drawable.heart_zise_4);
                mine_dispoint.setTextColor(0xFFd21fec);
                mine_dispoint.setBackgroundResource(R.drawable.start_4);
            }else if(level.equals("5")){
                heart_colour.setImageResource(R.drawable.heart_jinse_5);
                mine_dispoint.setTextColor(0xFFffe631);
                mine_dispoint.setBackgroundResource(R.drawable.start_5);
            }
            return false;
        }else {
            Glide.with(getContext()).load(R.mipmap.portrait).transform(new GlideCircleTransform(getContext())).into(mine_head);
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
        }else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = JSONObject.fromObject(s);
                    if(jsonObject.size()!=0){
                        BPApplication.getInstance().setLove_code(jsonObject.getString("love_code"));
                        BPApplication.getInstance().setBanlance(jsonObject.getString("banlance"));
                        mine_dispoint.setText(jsonObject.getString("love_code"));
                    }
                }
            });
        }
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

package com.zhulaozhijias.zhulaozhijia.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jauker.widget.BadgeView;
import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.eventbus.MainSendEvent;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by asus on 2017/9/22.
 */

public class Mine_AcountActivity extends BaseActivity implements View.OnClickListener,MainView{
    private LinearLayout mine_zhanghu_back;

    private TextView mine_zhifu_text;
    private TextView mine_balance,mine_jiankangdou,mine_acount_love_code;
    private MainPresenter mainPresenter;
    private RelativeLayout juanzeng_rt1,juanzhu_rt2,jiankangdou_rt3,gemejilu_rt4;
    private TextView go_chongzhi;
    @Override
    public void addLayout() {
        setContentView(R.layout.mine_acount_activity);
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        mainPresenter=new MainPresenter(this,this);
        mine_zhanghu_back= (LinearLayout) findViewById(R.id.mine_zhanghu_back);
        mine_zhanghu_back.setOnClickListener(this);

        mine_zhifu_text= (TextView) findViewById(R.id.mine_zhifu_text);
        mine_zhifu_text.setOnClickListener(this);
        mine_balance= (TextView) findViewById(R.id.mine_balance);
        mine_balance.setText(BPApplication.getInstance().getBanlance());
        mine_jiankangdou= (TextView) findViewById(R.id.mine_jiankangdou);

        mine_acount_love_code= (TextView) findViewById(R.id.mine_acount_love_code);
        juanzeng_rt1= (RelativeLayout) findViewById(R.id.juanzeng_rt1);
        juanzeng_rt1.setOnClickListener(this);
        juanzhu_rt2= (RelativeLayout) findViewById(R.id.juanzhu_rt2);
        juanzhu_rt2.setOnClickListener(this);
        jiankangdou_rt3= (RelativeLayout) findViewById(R.id.jiankangdou_rt3);
        jiankangdou_rt3.setOnClickListener(this);
        gemejilu_rt4= (RelativeLayout) findViewById(R.id.gemejilu_rt4);
        gemejilu_rt4.setOnClickListener(this);
        go_chongzhi= (TextView) findViewById(R.id.go_chongzhi);
        go_chongzhi.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_zhanghu_back:
                finish();
                break;
            case R.id.mine_zhifu_text:
                Intent intent =new Intent(Mine_AcountActivity.this,Pay_PassWordAcivity_1.class);
                startActivity(intent);
                break;
            case R.id.juanzeng_rt1:
                Intent intent2 =new Intent(Mine_AcountActivity.this,Mine_Acount_Recharge_DetailActivity.class);
                startActivity(intent2);
                break;
            case R.id.juanzhu_rt2:
                Intent intent3 =new Intent(Mine_AcountActivity.this,JuZhuJiLu_Activity.class);
                startActivity(intent3);
                break;
            case R.id.jiankangdou_rt3:
                Intent intent4 =new Intent(Mine_AcountActivity.this,Mine_DuiHuanJiLuActivity.class);
                startActivity(intent4);
                break;
            case R.id.gemejilu_rt4:
                Intent intent5 =new Intent(Mine_AcountActivity.this,Mine_Game_RecordActivity.class);
                startActivity(intent5);
                break;
            case R.id.go_chongzhi:
                Intent intent6 =new Intent(Mine_AcountActivity.this,Mine_RechargeActiviy.class);
                startActivity(intent6);
                break;
        }
    }

    //事件接受
    public void onEventMainThread(MainSendEvent event){
        if(event != null){
            if(event.getStringMsgData().equals("充值支付2")){
                Log.e("dsada","dsaddas");
                Map<String, String> map = new HashMap<>();
                map.put("member_id", BPApplication.getInstance().getMember_Id());
                map.put("secret", CreateMD5.getMd5(BPApplication.getInstance().getMember_Id() + "z!l@z#j$"));
                mainPresenter.postMap(SystemConstant.HuZhuPlan.NOTICE_MEMBER, map);
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
                    if(jsonObject.size()!=0){
                        if(!TextUtils.isEmpty(jsonObject.getString("notice_count"))){
                            mine_balance.setText(jsonObject.getString("banlance"));
                        }
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
}

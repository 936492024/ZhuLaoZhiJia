package com.zhulaozhijias.zhulaozhijia.activity;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by asus on 2017/9/28.
 */

public class Heart_ExchangeActivity extends BaseActivity implements View.OnClickListener ,MainView{
    private LinearLayout mine_aixin_back;
    private MainPresenter mainPresenter;
    private Button aixin_duihuan;
    private TextView aixinzhi,jiankangdou;
    private String Love_code,all_bean;
    private ToastUtil toastUtil;
    private TextView zhanghu_heart,mine_jiankangdou;
    @Override
    public void addLayout() {
        setContentView(R.layout.heart_exchange_activity);
    }

    @Override
    public void initView() {
        mainPresenter=new MainPresenter(this,this);
        mine_aixin_back= (LinearLayout) findViewById(R.id.mine_aixin_back);
        mine_aixin_back.setOnClickListener(this);
        aixin_duihuan= (Button) findViewById(R.id.aixin_duihuan);
        aixin_duihuan.setOnClickListener(this);
        Love_code= BPApplication.getInstance().getLove_code();
        aixinzhi= (TextView) findViewById(R.id.aixinzhi);
        jiankangdou= (TextView) findViewById(R.id.jiankangdou);
        mine_jiankangdou= (TextView) findViewById(R.id.mine_jiankangdou);
        int love_code =Integer.valueOf(Love_code) ;
        jiankangdou.setText(String.valueOf(love_code/1000));
        zhanghu_heart= (TextView) findViewById(R.id.zhanghu_heart);
        String member_id=BPApplication.getInstance().getMember_Id();
        Map<String , String >map = new HashMap<>();
        map.put("member_id",member_id);
        map.put("secret", CreateMD5.getMd5(member_id+"z!l@z#j$"));
        mainPresenter.wodes(SystemConstant.GEREN_ZHONGXIN.Mine_SHOW_BEAN,map);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mine_aixin_back:
                finish();
                break;
            case R.id.aixin_duihuan :
                Map<String ,String> map= new HashMap<>();
                map.put("member_id", BPApplication.getInstance().getMember_Id());
                map.put("secret", CreateMD5.getMd5(BPApplication.getInstance().getMember_Id()+"z!l@z#j$"));
                mainPresenter.postMap(SystemConstant.GEREN_ZHONGXIN.Mine_EXCHANGE_BEAN,map);
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
                JSONObject jsonObject =JSONObject.fromObject(s);
                Log.e("jsonObject兑换记录",jsonObject.toString());
                if(jsonObject.getString("success").equals("true")){
                        jiankangdou.setText("0");
                        aixinzhi.setText(jsonObject.getString("more_point"));
                        zhanghu_heart.setText(jsonObject.getString("more_point"));
                        int all_beans=Integer.valueOf(all_bean)+Integer.valueOf(jsonObject.getString("bean"));
                        mine_jiankangdou.setText(all_beans+"");
                        BPApplication.getInstance().setLove_code(jsonObject.getString("more_point"));
                        toastUtil.showToast(Heart_ExchangeActivity.this,jsonObject.getString("msg"));
                } else {
                    toastUtil.showToast(Heart_ExchangeActivity.this,jsonObject.getString("msg"));
                }
            }
        });
    }
    @Override
    public void postViews(final String s) {
        Log.e("dasda",s);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = JSONObject.fromObject(s);
                if(jsonObject.getString("success").equals("true")){
                    zhanghu_heart.setText(jsonObject.getString("love_code"));
                    aixinzhi.setText(jsonObject.getString("love_code"));
                    all_bean=jsonObject.getString("bean");
                    mine_jiankangdou.setText(all_bean);
                }else {
                    mine_jiankangdou.setText("0");
                    zhanghu_heart.setText(BPApplication.getInstance().getLove_code());
                    aixinzhi.setText(BPApplication.getInstance().getLove_code());
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

    }

    @Override
    public void imgView(Bitmap bitmap) {

    }
}

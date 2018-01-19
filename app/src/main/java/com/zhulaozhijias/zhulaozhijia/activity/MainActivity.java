package com.zhulaozhijias.zhulaozhijia.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.fragment.Fragment_1;
import com.zhulaozhijias.zhulaozhijia.fragment.Fragment_2;
import com.zhulaozhijias.zhulaozhijia.fragment.Fragment_3;
import com.zhulaozhijias.zhulaozhijia.fragment.Fragment_4;
import com.zhulaozhijias.zhulaozhijia.model.BackHandledFragment;
import com.zhulaozhijias.zhulaozhijia.model.BackHandledInterface;
import com.zhulaozhijias.zhulaozhijia.widgets.StatusBarUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener ,BackHandledInterface {
    private BackHandledFragment mBackHandedFragment;
    private RelativeLayout frg_1,frg_2,frg_3,frg_4;
    private FragmentManager fragmentManager;
    private FragmentTransaction Transaction;
    private  int nowcount;
    private Fragment[] fragments = new Fragment[4];
    private ImageView im_1,im_2,im_3,im_4;
    private TextView tv_1,tv_2,tv_3,tv_4;
    public static MainActivity mainActivity;

    @Override
    public void addLayout() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initView() {
        mainActivity=this;
        StatusBarUtils.with(this)
                .init();
        frg_1= (RelativeLayout) findViewById(R.id.frag_1);
        frg_2= (RelativeLayout) findViewById(R.id.frag_2);
        frg_3= (RelativeLayout) findViewById(R.id.frag_3);
        frg_4= (RelativeLayout) findViewById(R.id.frag_4);
        frg_1.setOnClickListener(this);
        frg_2.setOnClickListener(this);
        frg_3.setOnClickListener(this);
        frg_4.setOnClickListener(this);
        im_1= (ImageView) findViewById(R.id.im_1);
        im_2= (ImageView) findViewById(R.id.im_2);
        im_3= (ImageView) findViewById(R.id.im_3);
        im_4= (ImageView) findViewById(R.id.im_4);
        tv_1= (TextView) findViewById(R.id.tv_1);
        tv_2= (TextView) findViewById(R.id.tv_2);
        tv_3= (TextView) findViewById(R.id.tv_3);
        tv_4= (TextView) findViewById(R.id.tv_4);
        fragmentManager=getSupportFragmentManager();
        Transaction=fragmentManager.beginTransaction();
        getIntent_string();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.frag_1:
                change1();
                choose(0);
                break;
            case R.id.frag_2:
                change2();
                choose(1);
//                JCVideoPlayer.releaseAllVideos();
                break;
            case R.id.frag_3:
                change3();
                choose(2);
//                JCVideoPlayer.releaseAllVideos();
                break;
            case R.id.frag_4:
                change4();
                choose(3);
//                JCVideoPlayer.releaseAllVideos();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        if(mBackHandedFragment == null || !mBackHandedFragment.onBackPressed()){
            if(getSupportFragmentManager().getBackStackEntryCount() == 0){
                super.onBackPressed();

            }else{
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    @Override
    public void setSelectedFragment(BackHandledFragment selectedFragment) {
        this.mBackHandedFragment = selectedFragment;
    }

    public void change1(){
        im_1.setImageResource(R.drawable.home_page_red);
        im_2.setImageResource(R.drawable.gongshi_gray);
        im_3.setImageResource(R.drawable.activity_gray);
        im_4.setImageResource(R.drawable.mine_gray);
        tv_1.setTextColor(0xFFAC141C);
        tv_2.setTextColor(0xFF888888);
        tv_3.setTextColor(0xFF888888);
        tv_4.setTextColor(0xFF888888);
    }

    public void change2(){
        im_1.setImageResource(R.drawable.home_page_gray);
        im_2.setImageResource(R.drawable.gongshi_red);
        im_3.setImageResource(R.drawable.activity_gray);
        im_4.setImageResource(R.drawable.mine_gray);
        tv_1.setTextColor(0xFF888888);
        tv_2.setTextColor(0xFFAC141C);
        tv_3.setTextColor(0xFF888888);
        tv_4.setTextColor(0xFF888888);
    }

    public void change3(){
        im_1.setImageResource(R.drawable.home_page_gray);
        im_2.setImageResource(R.drawable.gongshi_gray);
        im_3.setImageResource(R.drawable.activity_red);
        im_4.setImageResource(R.drawable.mine_gray);
        tv_1.setTextColor(0xFF888888);
        tv_2.setTextColor(0xFF888888);
        tv_3.setTextColor(0xFFAC141C);
        tv_4.setTextColor(0xFF888888);
    }

    public void change4(){
        im_1.setImageResource(R.drawable.home_page_gray);
        im_2.setImageResource(R.drawable.gongshi_gray);
        im_3.setImageResource(R.drawable.activity_gray);
        im_4.setImageResource(R.drawable.mine_red);
        tv_1.setTextColor(0xFF888888);
        tv_2.setTextColor(0xFF888888);
        tv_3.setTextColor(0xFF888888);
        tv_4.setTextColor(0xFFAC141C);
    }

    public void getIntent_string(){
        if(!TextUtils.isEmpty(getIntent().getStringExtra("Order_PayActivity_2"))){
            fragments[0]=new Fragment_1();
            Transaction.add(R.id.mainfrgment,fragments[0]);
            Transaction.commit();
            nowcount=0;
            change1();
            BPApplication.getInstance().exits(MainActivity.this);
        }else if(!TextUtils.isEmpty(getIntent().getStringExtra("Mine_RechargeActiviy"))){
            fragments[0]=new Fragment_1();
            Transaction.add(R.id.mainfrgment,fragments[0]);
            Transaction.commit();
            nowcount=0;
            change1();
            BPApplication.getInstance().exits(MainActivity.this);
        }else if(!TextUtils.isEmpty(getIntent().getStringExtra("Game_BANK_RECEIVE_BEAN"))){
            fragments[2]=new Fragment_3();
            Transaction.add(R.id.mainfrgment,fragments[2]);
            Transaction.commit();
            nowcount=2;
            change3();
            BPApplication.getInstance().exits(MainActivity.this);
        }else if(!TextUtils.isEmpty(getIntent().getStringExtra("Make_PasswordActivity"))){
            fragments[0]=new Fragment_1();
            Transaction.add(R.id.mainfrgment,fragments[0]);
            Transaction.commit();
            nowcount=0;
            change1();
            BPApplication.getInstance().exits(MainActivity.this);
        }else if(!TextUtils.isEmpty(getIntent().getStringExtra("Mine_YanZhengPhone_Activity_3"))){
            fragments[0]=new Fragment_1();
            Transaction.add(R.id.mainfrgment,fragments[0]);
            Transaction.commit();
            nowcount=0;
            change1();
            BPApplication.getInstance().exits(MainActivity.this);
        }else
        if(!TextUtils.isEmpty(getIntent().getStringExtra("rank_tab1"))){
            fragments[2]=new Fragment_3();
            Transaction.add(R.id.mainfrgment,fragments[2]);
            Transaction.commit();
            nowcount=2;
            change3();
            BPApplication.getInstance().exits(MainActivity.this);
        }else if(!TextUtils.isEmpty(getIntent().getStringExtra("lin_1"))) {
            fragments[0]=new Fragment_1();
            Transaction.add(R.id.mainfrgment,fragments[0]);
            Transaction.commit();
            nowcount=0;
            change1();
            BPApplication.getInstance().exits(MainActivity.this);
        }else if(!TextUtils.isEmpty(getIntent().getStringExtra("grxx"))){
            fragments[0]=new Fragment_1();
            Transaction.add(R.id.mainfrgment,fragments[0]);
            Transaction.commit();
            nowcount=0;
            change1();
            BPApplication.getInstance().exits(MainActivity.this);
        } else if(!TextUtils.isEmpty(getIntent().getStringExtra("Invite_FriendActivity"))){
            fragments[2]=new Fragment_3();
            Transaction.add(R.id.mainfrgment,fragments[2]);
            Transaction.commit();
            nowcount=2;
            change3();
            BPApplication.getInstance().exits(MainActivity.this);
        }
        else {
            fragments[0]=new Fragment_1();
            Transaction.add(R.id.mainfrgment,fragments[0]);
            Transaction.commit();
            nowcount=0;
        }
    }

    public void choose(int but_index){
        FragmentTransaction Transaction = fragmentManager.beginTransaction();
        if (nowcount != but_index) {
            if (fragments[but_index] == null) {
                fragments[but_index] = newfragment(but_index);
                Transaction.add(R.id.mainfrgment, fragments[but_index]);
            } else {
                Transaction.show(fragments[but_index]);
            }
            Transaction.hide(fragments[nowcount]);
            Transaction.commit();
            nowcount=but_index;
        }
    }

    public Fragment newfragment(int but_index){
        switch (but_index) {
            case 0:
                return new Fragment_1();
            case 1:
                return new Fragment_2();
            case 2:
                return new Fragment_3();
            case 3:
                return new Fragment_4();
        }
        return null;
    }
}

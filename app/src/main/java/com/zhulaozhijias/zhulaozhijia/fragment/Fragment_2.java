package com.zhulaozhijias.zhulaozhijia.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.activity.LoginActivity;
import com.zhulaozhijias.zhulaozhijia.activity.MapListActivity;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.Connect_Check;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;
import net.sf.json.JSONObject;

/**
 * Created by Administrator on 2017/9/4.
 */

public class Fragment_2 extends Fragment implements View.OnClickListener,MainView{
    private LinearLayout map_back,map_btn;
    private Intent intent;
    private MainPresenter mainPresenter;
    private ImageView map_imageview;
    private ToastUtil toastUtil;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2,null);
        initview(view);
        return view;
    }
    public void initview(View view){
        mainPresenter=new MainPresenter(getContext(),this);
        map_btn= (LinearLayout)view. findViewById(R.id.map_btn);
        map_btn.setOnClickListener(this);
        map_imageview= (ImageView)view. findViewById(R.id.map_imageview);
        mainPresenter.getRequest(SystemConstant.PublicConstant.Public_PROVINCE_COUNT);
        if(Connect_Check.getCurrentNetType(getContext())==0){
            toastUtil.showToast(getContext(), "网络不给力，请检查网络设置");
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.map_btn:
                if(!checkIsLogined()){
                    return;
                }else {
                    intent = new Intent(getContext(),MapListActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void getView(final String s) {
        if (getActivity() == null) {
            return;
        }else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = JSONObject.fromObject(s);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("0");
                    String path = jsonObject1.getString("path");
                    Glide.with(getContext()).load(path).into(map_imageview);
                }
            });
        }
    }


    @Override
    public void postView(final String s) {

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
    private boolean checkIsLogined() {
        if (!BPApplication.getInstance().isLogined()) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
            return false;
        }
        return true;
    }
}


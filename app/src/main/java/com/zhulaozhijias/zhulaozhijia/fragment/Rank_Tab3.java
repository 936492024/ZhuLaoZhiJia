package com.zhulaozhijias.zhulaozhijia.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.activity.FoundationActivity;
import com.zhulaozhijias.zhulaozhijia.activity.Heart_RankActivity;
import com.zhulaozhijias.zhulaozhijia.activity.MapActivity;
import com.zhulaozhijias.zhulaozhijia.activity.MapListActivity;
import com.zhulaozhijias.zhulaozhijia.adpter.Rank_RecycleviewAdapter;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.GlideCircleTransform;
import com.zhulaozhijias.zhulaozhijia.widgets.PullBaseView;
import com.zhulaozhijias.zhulaozhijia.widgets.PullRecyclerView;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by asus on 2017/9/14.
 */

public class Rank_Tab3 extends Fragment implements View.OnClickListener,MainView,PullBaseView.OnRefreshListener{
    private PullRecyclerView heart_recycleview3;
    private MainPresenter mainPresenter;
    private ImageView heart_no4_head_3;
    private TextView heart_no4_silver_3,heart_no4_nickname_3,heart_no4_personnum_3,woyaojuan_btn__3;
    private Rank_RecycleviewAdapter rank_recycleviewAdapter_3;
    private ToastUtil toastUtil;
    private JSONObject jsonObject = new JSONObject();
    private ArrayList<String> arrayList = new ArrayList<>();
    private Boolean flag=true;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rank_tab3,null);
        initview(view);
        return view;
    }
    public void initview(View view){
        mainPresenter = new MainPresenter(getContext(), this);
        heart_recycleview3= (PullRecyclerView) view.findViewById(R.id.heart_recycleview3);
        heart_recycleview3.setOnRefreshListener(this);
        heart_no4_head_3= (ImageView) view.findViewById(R.id.heart_no4_head_3);
        heart_no4_nickname_3= (TextView) view.findViewById(R.id.heart_no4_nickname_3);
        heart_no4_silver_3= (TextView) view.findViewById(R.id.heart_no4_silver_3);
        heart_no4_personnum_3= (TextView) view.findViewById(R.id.heart_no4_personnum_3);
        woyaojuan_btn__3= (TextView) view.findViewById(R.id.woyaojuan_btn__3);
        woyaojuan_btn__3.setOnClickListener(this);

        String Member_Id= BPApplication.getInstance().getMember_Id();
        String name = BPApplication.getInstance().getName();
        heart_no4_nickname_3.setText(name);
        Map<String ,String > map= new HashMap<>();
        map.put("member_id",Member_Id);
        map.put("secret", CreateMD5.getMd5(Member_Id+"z!l@z#j$"));
        mainPresenter.postMap(SystemConstant.LoginedInformatio.USER_DONATE_MONEY_RANK,map);
        if (getActivity() == null) {
            return;
        }else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<Heart_RankActivity.arrayList_3.size();i++) {
                        jsonObject = Heart_RankActivity.arrayList_3.getJSONObject(i);
                        arrayList.add(jsonObject.getString("member_id"));
                    }
                    rank_recycleviewAdapter_3 = new Rank_RecycleviewAdapter(getContext(), Heart_RankActivity.arrayList_3, 3,arrayList);
                    heart_recycleview3.setFragment2LayoutManager3(new LinearLayoutManager(getContext()));
                    heart_recycleview3.setFragment2Adapter3(rank_recycleviewAdapter_3);
                    rank_recycleviewAdapter_3.notifyDataSetChanged();
                    rank_recycleviewAdapter_3.setOnClicListener(new Rank_RecycleviewAdapter.OnItemClicListener() {
                        @Override
                        public void onItemClic(View view, int position, String rank_id) {
                            if (arrayList.get(position).equals(BPApplication.getInstance().getMember_Id())) {
                                return;
                            }
                            if(flag){
                                Map<String, String> map = new HashMap<>();
                                map.put("member_id", rank_id);
                                map.put("secret", CreateMD5.getMd5(rank_id + "z!l@z#j$"));
                                mainPresenter.wodes(SystemConstant.LoginedInformatio.USER_PRAISE_RANK, map);
                                flag=false;
                            }
                        }
                    });

                }
            });
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.woyaojuan_btn__3:
                Intent intent = new Intent(getContext(),FoundationActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void getView(final String s) {

    }

    @Override
    public void postView(final String s) {
        if (getActivity() == null) {
            return;
        }else {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject json = JSONObject.fromObject(s);
                heart_no4_silver_3.setText(json.getString("rank"));
                Glide.with(getContext()).load(BPApplication.getInstance().getHeadimgurl()).transform(new GlideCircleTransform(getContext())).into(heart_no4_head_3);
                heart_no4_personnum_3.setText(json.getString("money")+"元");
            }
        });
        }
    }

    @Override
    public void postViews(final String s) {
        if (getActivity() == null) {
            return;
        }else {
            Log.e("点赞", s);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = JSONObject.fromObject(s);
                    if (jsonObject.getString("success").equals("true")) {
                        toastUtil.showToast(getContext(), jsonObject.getString("msg"));
                    } else {
                        toastUtil.showToast(getContext(), jsonObject.getString("msg"));
                    }
                }
            });
        }
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
    public void onHeaderRefresh(PullBaseView view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                heart_recycleview3.onHeaderRefreshComplete();
            }
        }, 1500);
    }

    @Override
    public void onFooterRefresh(PullBaseView view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                heart_recycleview3.onFooterRefreshComplete();
            }
        }, 1500);
    }
}

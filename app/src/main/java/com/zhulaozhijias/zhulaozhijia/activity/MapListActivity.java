package com.zhulaozhijias.zhulaozhijia.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.adpter.GridView_Map_Adapter;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by asus on 2017/9/13.
 */

public class MapListActivity extends BaseActivity implements View.OnClickListener ,MainView{
    private GridView gridView;
    private GridView_Map_Adapter adapter;
    private LinearLayout map_list_back;
    private MainPresenter mainPresenter;
    private ArrayList<String> list = new ArrayList<>();
    private JSONObject jsonObject1;
    @Override
    public void addLayout() {
        setContentView(R.layout.maplist_activity);
    }

    @Override
    public void initView() {
        mainPresenter=new MainPresenter(this,this);
        gridView= (GridView) findViewById(R.id.grid_map);
        map_list_back= (LinearLayout) findViewById(R.id.map_list_back);
        map_list_back.setOnClickListener(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainPresenter.getRequest(SystemConstant.PublicConstant.Public_PROVINCE_COUNT);
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.map_list_back:
                finish();
                break;
        }
    }

    @Override
    public void getView(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONObject jsonObject = JSONObject.fromObject(s);
                String a =jsonObject.getString("list");
                JSONArray jsonArray = JSONArray.fromObject(a);
                JSONArray jsonArray1 = new JSONArray();
                for(int i=0;i<jsonArray.size();i++){
                    jsonArray1.add(jsonArray.getJSONObject(i));
                    list.add(jsonArray1.getJSONObject(i).getString("province"));
                }
                if(jsonArray1.size()!=0){
                adapter=new GridView_Map_Adapter(MapListActivity.this,jsonArray1);
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(MapListActivity.this,HuZhu_AnLiActivity.class);
                        intent.putExtra("province",list.get(i));
                        startActivity(intent);
                    }
                });
                }
            }
        });
    }

    @Override
    public void postView(String s) {

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

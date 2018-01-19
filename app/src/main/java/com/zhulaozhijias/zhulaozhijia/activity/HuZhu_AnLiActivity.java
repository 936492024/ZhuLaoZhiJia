package com.zhulaozhijias.zhulaozhijia.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.adpter.BaseAdapter;
import com.zhulaozhijias.zhulaozhijia.adpter.GridViewAdapter;
import com.zhulaozhijias.zhulaozhijia.adpter.InfoAdapter;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;
import com.zhulaozhijias.zhulaozhijia.base.SystemConstant;
import com.zhulaozhijias.zhulaozhijia.entry.InfoBean;
import com.zhulaozhijias.zhulaozhijia.presenter.MainPresenter;
import com.zhulaozhijias.zhulaozhijia.view.MainView;
import com.zhulaozhijias.zhulaozhijia.widgets.CreateMD5;
import com.zhulaozhijias.zhulaozhijia.widgets.PullBaseView;
import com.zhulaozhijias.zhulaozhijia.widgets.PullRecyclerView;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2017/9/13.
 */

public class HuZhu_AnLiActivity extends BaseActivity  implements BaseAdapter.OnItemClickListener,
        BaseAdapter.OnViewClickListener, PullBaseView.OnRefreshListener,View.OnClickListener,MainView{
    private RelativeLayout itmel;
    private GridView gridView;
    private PullRecyclerView recyclerView;
    private List<Object> mDatas;
    private InfoAdapter infoAdapter;
    private LinearLayout huzhu_anli_back;
    private MainPresenter mainPresenter;
    private JSONArray jsonArray2 = new JSONArray();
    private TextView province_text;
    private JSONArray jsonArray1;
    private JSONObject jsonObject;
    private String []citys ;
    private ToastUtil toastUtil;
    @Override
    public void addLayout() {
        setContentView(R.layout.huzhu_anli_activity);
    }

    @Override
    public void initView() {
        getId();
        initData();
        String province= getIntent().getStringExtra("province");
        province_text.setText(province+"省");
        mainPresenter=new MainPresenter(this,this);
        Map<String ,String > map = new HashMap<>();
        map.put("province",province);
        map.put("secret", CreateMD5.getMd5(province+"z!l@z#j$"));
        mainPresenter.postMap(SystemConstant.PublicConstant.Public_CITY,map);
    }

    public void getId(){
        province_text= (TextView) findViewById(R.id.province);
        gridView = (GridView) findViewById(R.id.grid);
        huzhu_anli_back= (LinearLayout) findViewById(R.id.huzhu_anli_back);
        huzhu_anli_back.setOnClickListener(this);
    }

    /**设置GirdView参数，绑定数据*/
    private void setGridView() {
        Map<String ,String > maps = new HashMap<>();
        maps.put("city",citys[0]);
        maps.put("status","2");
        maps.put("secret", CreateMD5.getMd5("2"+"z!l@z#j$"));
        mainPresenter.wodes(SystemConstant.PublicConstant.Public_GONGSHI,maps);
        int size = jsonArray2.size();
        int length = 100;
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 4) * density);
        int itemWidth = (int) (length * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gridView.setColumnWidth(itemWidth); // 设置列表项宽
        gridView.setHorizontalSpacing(5); // 设置列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size); // 设置列数量=列表集合数
        GridViewAdapter adapter = new GridViewAdapter(getApplicationContext(), jsonArray2);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String ,String > maps = new HashMap<>();
                maps.put("city",citys[i]);
                maps.put("status","2");
                maps.put("secret", CreateMD5.getMd5("2"+"z!l@z#j$"));
                mainPresenter.wodes(SystemConstant.PublicConstant.Public_GONGSHI,maps);
            }
        });

    }



    protected void initData() {
        mDatas = new ArrayList<>();
        List<Object> imageList = new ArrayList<>();
        imageList.add("https://ss0.baidu.com/7Po3dSag_xI4khGko9WTAnF6hhy/image/h%3D200/sign=71cd4229be014a909e3e41bd99763971/472309f7905298221dd4c458d0ca7bcb0b46d442.jpg");
        for (int i = 0; i < 5; i++) {
            InfoBean info = new InfoBean();
            info.setText("大病基金互助（中青年版）");
            info.setImgList(imageList);
            mDatas.add(info);
        }
    }

    private void initRecyclerView() {
        recyclerView = (PullRecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        infoAdapter = new InfoAdapter(this, jsonArray1, this);

        // TODO: 2017/9/25 监听
        infoAdapter.setOnClicListeners1(new InfoAdapter.OnItemClicListeners1() {
            @Override
            public void onItemClic(View view, int position, String notice_id) {
                if(!checkIsLogined()){
                    return;
                }else {
                    Intent intent = new Intent(HuZhu_AnLiActivity.this,DetailsActivity.class);
                    intent.putExtra("1",notice_id);
                    intent.putExtra("2","huzhu");
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(infoAdapter);
    }
    @Override
    public void onViewClick(int position, int viewtype) {

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onHeaderRefresh(PullBaseView view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InfoBean info = new InfoBean();
                info.setText("新增新增新增新增新增新增新增新增新增新增新增新增新增新增新增新增新增新增");
                mDatas.add(0, info);
                infoAdapter.notifyDataSetChanged();
                recyclerView.onHeaderRefreshComplete();
            }
        }, 1500);
    }

    @Override
    public void onFooterRefresh(PullBaseView view) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                InfoBean info = new InfoBean();
//                info.setText("更多更多更多更多更多更多更多更多更多更多更多更多更多更多更多更多更多");
//                mDatas.add(info);
//                infoAdapter.notifyDataSetChanged();
                recyclerView.onFooterRefreshComplete();
            }
        }, 1500);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.huzhu_anli_back:
                finish();
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
                int i;
                JSONArray jsonArray = JSONArray.fromObject(s);
                citys=new String[jsonArray.size()];
                for( i=0;i<jsonArray.size();i++){
                    jsonArray2.add(jsonArray.getJSONObject(i));
                    jsonObject = jsonArray2.getJSONObject(i);
                    citys[i] =jsonObject.getString("city");
                }
                setGridView();
            }
        });
    }

    @Override
    public void postViews(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JSONArray jsonArray = JSONArray.fromObject(s);
                jsonArray1 = new JSONArray();
                for(int i=0;i<jsonArray.size();i++){
                    jsonArray1.add(jsonArray.getJSONObject(i));
                }
                initRecyclerView();
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
    private boolean checkIsLogined() {
        if (!BPApplication.getInstance().isLogined()) {
            Intent intent = new Intent(HuZhu_AnLiActivity.this, LoginActivity.class);
            startActivity(intent);
            return false;
        }
        return true;
    }
}

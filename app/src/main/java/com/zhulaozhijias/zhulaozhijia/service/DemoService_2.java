package com.zhulaozhijias.zhulaozhijia.service;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.allenliu.versionchecklib.core.AVersionService;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.utils.StringUtils;
import com.zhulaozhijias.zhulaozhijia.widgets.ToastUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by asus on 2017/11/22.
 */

public class DemoService_2 extends AVersionService {
    private String msg;
    private String web;
    public DemoService_2() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    //
    @Override
    public void onResponses(AVersionService service, String response) {
        Log.e("DemoServices", response);
        //可以在判断版本之后在设置是否强制更新或者VersionParams
        // versionParams.isForceUpdate=true;
        if(TextUtils.isEmpty(response)){
            return;
        }
        if(!isGoodJson(response)){
            ToastUtil.showToast(DemoService_2.this,"网络连接错误，请检查网络设置");
            return;
        }
        Bundle bundle = new Bundle();
        ArrayList<String> arrayList = new ArrayList<>();
        JSONObject jsonObject = JSONObject.fromObject(response);
        if(jsonObject.getString("success").equals("true")){
            BPApplication.getInstance().setUpdate(jsonObject.getString("update"));
            if(jsonObject.getString("update").equals("0")){//已经是最新版本
            }else if(jsonObject.getString("update").equals("1")){//不强制更新
                web =jsonObject.getString("web");
                JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("intro"));
                JSONArray jsonArray2=new JSONArray();
                JSONObject jsonObject1 = new JSONObject();
                JSONArray jsonArray1 = new JSONArray();
                for(int i =0;i<jsonArray.size();i++){
                    jsonArray2.add(jsonArray.getJSONObject(i));
                    jsonObject1=jsonArray2.getJSONObject(i);
                    msg =jsonObject1.getString("intro");
                    arrayList.add(msg);
                }
                bundle.putStringArrayList("arrayList",arrayList);
                showVersionDialog(web, jsonObject.getString("title")+"版本更新", msg,bundle);
            }else {//强制更新
                web =jsonObject.getString("web");
                JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("intro"));
                JSONArray jsonArray2=new JSONArray();
                JSONObject jsonObject1 = new JSONObject();
                JSONArray jsonArray1 = new JSONArray();
                for(int i =0;i<jsonArray.size();i++){
                    jsonArray2.add(jsonArray.getJSONObject(i));
                    jsonObject1=jsonArray2.getJSONObject(i);
                    msg =jsonObject1.getString("intro");
                    arrayList.add(msg);
                }
                bundle.putStringArrayList("arrayList",arrayList);
                showVersionDialog(web, jsonObject.getString("title")+"版本更新", msg,bundle);
            }
        }else {

        }
//        or
//        showVersionDialog("http://www.apk3.com/uploads/soft/guiguangbao/UCllq.apk", "检测到新版本", getString(R.string.updatecontent),bundle);
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
}


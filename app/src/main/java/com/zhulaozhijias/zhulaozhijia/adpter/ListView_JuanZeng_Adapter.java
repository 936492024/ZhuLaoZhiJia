package com.zhulaozhijias.zhulaozhijia.adpter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by asus on 2017/9/29.
 */

public class ListView_JuanZeng_Adapter extends android.widget.BaseAdapter {
    Context context;
    JSONArray list = new JSONArray();
    public ListView_JuanZeng_Adapter(Context _context, JSONArray list) {
        this.list = list;
        this.context = _context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.juanzhu_listitem, null);
        final JSONObject item = list.getJSONObject(position);
        // TODO: 2017/9/29 判断状态的
//        if(item.getString("status").equals("1")){
//
//        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TextView donated_money = (TextView) convertView.findViewById(R.id.donated_money);
        TextView donated_name = (TextView) convertView.findViewById(R.id.donated_name);
        TextView donated_time = (TextView) convertView.findViewById(R.id.donated_time);
        donated_money.setText(item.getString("money")+"元");
        if(item.getString("pay").equals("0")){
            if(!item.getString("way").equals("null")){
                donated_name.setText("通过微信支付捐赠("+item.getString("way")+")");
            }else {
                donated_name.setText("通过微信支付捐赠");
            }
        }else {
            if(!item.getString("way").equals("null")) {
                donated_name.setText("通过支付宝支付捐赠("+item.getString("way")+")");
            }else {
                donated_name.setText("通过支付宝支付捐赠");
            }
        }
        donated_time.setText(formatter.format((Long.valueOf(item.getString("time")))*1000));

        return convertView;
    }
}

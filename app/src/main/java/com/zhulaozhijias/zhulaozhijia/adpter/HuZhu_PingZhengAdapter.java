package com.zhulaozhijias.zhulaozhijia.adpter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.zhulaozhijias.zhulaozhijia.R;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by asus on 2017/9/29.
 */

public class HuZhu_PingZhengAdapter extends android.widget.BaseAdapter {

    Context context;
    JSONArray list = new JSONArray();
    public HuZhu_PingZhengAdapter(Context _context, JSONArray list) {
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
        convertView = layoutInflater.inflate(R.layout.huzhu_pingzheng_item, null);
        final JSONObject item = list.getJSONObject(position);
        TextView pingzheng_number = (TextView) convertView.findViewById(R.id.pingzheng_number);
        TextView pingzheng_plan = (TextView) convertView.findViewById(R.id.pingzheng_plan);
        TextView pingzheng_money = (TextView) convertView.findViewById(R.id.pingzheng_money);
        TextView pingzheng_name = (TextView) convertView.findViewById(R.id.pingzheng_name);
        TextView pingzheng_date = (TextView) convertView.findViewById(R.id.pingzheng_date);
        TextView pingzheng_idcard =(TextView) convertView.findViewById(R.id.pingzheng_idcard);
        TextView jingmoqi_date = (TextView) convertView.findViewById(R.id.jingmoqi_date);

        pingzheng_number.setText(item.getString("number"));
        pingzheng_plan.setText(item.getString("plan_name"));
//        pingzheng_money.setText(item.getString(""));
        pingzheng_name.setText(item.getString("name"));
        pingzheng_date.setText(item.getString("timeDate"));
        pingzheng_idcard.setText(item.getString("card_id"));
        jingmoqi_date.setText(item.getString("time")+"至"+item.getString("silent"));

        return convertView;
    }
}

package com.zhulaozhijias.zhulaozhijia.adpter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhulaozhijias.zhulaozhijia.R;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by asus on 2017/9/13.
 */

public class GridViewAdapter extends BaseAdapter {
    Context context;
    JSONArray list = new JSONArray();
    public GridViewAdapter(Context _context, JSONArray list) {
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
        convertView = layoutInflater.inflate(R.layout.list_item, null);
        final JSONObject item = list.getJSONObject(position);
        TextView tvCity = (TextView) convertView.findViewById(R.id.tvCity);
        TextView tvCode = (TextView) convertView.findViewById(R.id.tvCode);
        tvCity.setText(item.getString("city"));
        tvCode.setText(item.getString("count")+"例");
        return convertView;
    }
}


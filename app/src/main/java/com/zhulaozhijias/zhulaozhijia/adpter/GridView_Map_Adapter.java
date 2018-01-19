package com.zhulaozhijias.zhulaozhijia.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.bumptech.glide.Glide;
import com.zhulaozhijias.zhulaozhijia.R;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by asus on 2017/9/13.
 */

public class GridView_Map_Adapter extends android.widget.BaseAdapter {
    Context context;
    JSONArray list = new JSONArray();
    public GridView_Map_Adapter(Context _context, JSONArray list) {
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
        final JSONObject item = list.getJSONObject(position);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.map_list_item, null);
        ImageView imageView= (ImageView) convertView.findViewById(R.id.imageView_map);
        TextView weizhi= (TextView) convertView.findViewById(R.id.weizhi);
        TextView number = (TextView) convertView.findViewById(R.id.number);
        Glide.with(context).load(item.getString("path")).into(imageView);
        number.setText(item.getString("count"));
        weizhi.setText(item.getString("province"));
        return convertView;
    }

}

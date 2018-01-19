package com.zhulaozhijias.zhulaozhijia.adpter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhulaozhijias.zhulaozhijia.R;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by asus on 2017/10/15.
 */

public class ListView_Num_Adapter extends android.widget.BaseAdapter{
    Context context;
    JSONArray list = new JSONArray();
    public ListView_Num_Adapter(Context _context, JSONArray list) {
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
       ViewHolder holder;
        final JSONObject item = list.getJSONObject(position);
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.mine_num_child, null);
            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.text.setText(item.getString("name")+"的互助凭证编码："+item.getString("number"));
        return convertView;
    }

    public class ViewHolder {
        TextView text;
    }
}
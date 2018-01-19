package com.zhulaozhijias.zhulaozhijia.adpter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhulaozhijias.zhulaozhijia.R;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by asus on 2017/9/28.
 */

public class GirdView_XiangQingAdapter extends android.widget.BaseAdapter{

    Context context;
    JSONArray list = new JSONArray();
    public GirdView_XiangQingAdapter(Context _context, JSONArray list) {
        this.list = list;
        this.context = _context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        Log.e("dsadsdadas",list.size()+"");
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final JSONObject item = list.getJSONObject(position);
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.girdview_xiangqing_item, null);
            holder = new ViewHolder();
            holder.imageView= (ImageView) convertView.findViewById(R.id.xiangqing_image);
            holder.text= (TextView) convertView.findViewById(R.id.cailiao);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(item.getString("img_path")).into(holder.imageView);
        holder.text.setText("材料"+(position+1)+":"+item.getString("img_name"));

        return convertView;
    }
    public class ViewHolder {
        TextView text;
        ImageView imageView;
    }
}

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
 * Created by asus on 2017/10/5.
 */

public class ListView_MineSet_Adapter extends android.widget.BaseAdapter{
    private Context context;
    private JSONArray list = new JSONArray();
    private ArrayList<String> arrayList = new ArrayList<>();
    private int i;
    public ListView_MineSet_Adapter(Context _context, JSONArray list,int i) {
        this.list = list;
        this.context = _context;
        this.i=i;
    }
    public ListView_MineSet_Adapter(Context _context,ArrayList<String> arrayList ,int i) {
        this.arrayList = arrayList;
        this.context = _context;
        this.i=i;
    }
    @Override
    public int getCount() {
        if(i==1){
            return list.size();
        }else {
            return arrayList.size();
        }

    }

    @Override
    public Object getItem(int position) {
        if(i==1){
            return list.get(position);
        }else {
            return arrayList.get(position);
        }

    }

    @Override
    public long getItemId(int position) {
        if(i==1){
            return position;
        }else {
            return position;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(i==1){
            final JSONObject item = list.getJSONObject(position);
            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                convertView = layoutInflater.inflate(R.layout.mine_set_child, null);
                holder = new ViewHolder();
                holder.mine_relation = (TextView) convertView.findViewById(R.id.mine_relation);
                holder.mine_name = (TextView) convertView.findViewById(R.id.mine_name);
                holder.mine_plan = (TextView) convertView.findViewById(R.id.mine_plan);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mine_relation.setText(item.getString("between"));
            holder.mine_name.setText(item.getString("relatives"));
//        holder.mine_plan.setText(item.getString("card_id"));
        }else if(i==2){
            ViewHolder holder2;
            if (convertView == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                convertView = layoutInflater.inflate(R.layout.upload_dialog_item, null);
                holder2 = new ViewHolder();
                holder2.upload_item_text = (TextView) convertView.findViewById(R.id.upload_item_text);
                convertView.setTag(holder2);
            } else {
                holder2 = (ViewHolder) convertView.getTag();
            }
            holder2.upload_item_text.setText((position+1)+"."+arrayList.get(position));
        }
        return convertView;
    }

    public class ViewHolder {
        TextView mine_relation,mine_name,mine_plan,upload_item_text;
    }

}

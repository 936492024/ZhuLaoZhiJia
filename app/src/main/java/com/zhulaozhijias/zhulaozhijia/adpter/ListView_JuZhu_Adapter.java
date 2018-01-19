package com.zhulaozhijias.zhulaozhijia.adpter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhulaozhijias.zhulaozhijia.R;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by asus on 2017/9/13.
 */

public class ListView_JuZhu_Adapter extends BaseAdapter {
    Context context;
    JSONArray list = new JSONArray();
    int z;
    public ListView_JuZhu_Adapter(Context _context, JSONArray list,int z) {
        this.list = list;
        this.context = _context;
        this.z=z;
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
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TextView donated_money = (TextView) convertView.findViewById(R.id.donated_money);
        TextView donated_name = (TextView) convertView.findViewById(R.id.donated_name);
        TextView donated_time = (TextView) convertView.findViewById(R.id.donated_time);
        TextView jilujilu= (TextView) convertView.findViewById(R.id.jilujilu);
        if(z==1){
            donated_name.setText("捐助："+item.getString("donated_name"));
            donated_time.setText(formatter.format((Long.valueOf(item.getString("time")))*1000));
            donated_money.setText("+"+item.getString("money"));
            jilujilu.setText("捐助成功");
        }
        if(z==2){
            donated_money.setText("+"+item.getString("exchange_number")+"(颗)");
            donated_time.setText(formatter.format((Long.valueOf(item.getString("time")))*1000));
            jilujilu.setText("兑换成功");
        }if(z==3){
            if(item.getString("game_name").equals("1")){
                donated_name.setText("话题押宝");
                Double game_reward=Double.valueOf(item.getString("game_reward"));
                int a =(int)(game_reward/1);
                donated_money.setText("+"+a+"（颗）");
                String str = item.getString("cre_time");
                donated_time.setText(str);
                jilujilu.setTextColor(0xffAC141C);
            }else if(item.getString("game_name").equals("0")){
                donated_name.setText("早起打卡");
                donated_money.setText("+"+item.getString("game_reward")+"（元）");
                String str = item.getString("cre_time");
                donated_time.setText(str);
            }else {
                donated_name.setText("邀请："+item.getString("invitation_name")+"(已注册)");
                donated_time.setText(item.getString("cre_time"));
                donated_money.setText("+"+item.getString("game_reward")+"（元）");
            }
            jilujilu.setText("已到账");
        }
        return convertView;
    }
}


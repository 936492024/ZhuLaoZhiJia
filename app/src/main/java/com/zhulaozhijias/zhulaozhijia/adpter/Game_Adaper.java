package com.zhulaozhijias.zhulaozhijia.adpter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zhulaozhijias.zhulaozhijia.R;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * Created by asus on 2017/10/16.
 */

public class Game_Adaper extends android.widget.BaseAdapter{
        Context context;
        JSONArray list = new JSONArray();
        DecimalFormat df = new DecimalFormat("#.##");
        String my_team;
public Game_Adaper(Context _context,  JSONArray list) {
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
        Log.e("dsadsadadd",item.toString());
        if (convertView == null) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.game__item_activity, null);
        holder = new ViewHolder();
        holder.game_betting_topic_time = (TextView) convertView.findViewById(R.id.game_betting_topic_time);
        holder.game_betting_topic_title = (TextView) convertView.findViewById(R.id.game_betting_topic_title);
        holder. game_betting_topic_red_number = (TextView) convertView.findViewById(R.id. game_betting_topic_red_number);
        holder.game_betting_topic_blue_number = (TextView) convertView.findViewById(R.id.game_betting_topic_blue_number);
        holder.winner = (TextView) convertView.findViewById(R.id.winner);
        holder.best_member_name = (TextView) convertView.findViewById(R.id.best_member_name);
        holder.my_team = (TextView) convertView.findViewById(R.id.my_team);
        convertView.setTag(holder);
        } else {
        holder = (ViewHolder) convertView.getTag();
        }
        String str=item.getString("topic_time");
        String str1= str.substring(0,4);
        String str2= str.substring(4,6);
        String str3= str.substring(6,8);
        holder.game_betting_topic_time.setText(str1+"年"+str2+"月"+str3+"日");
        holder.game_betting_topic_title.setText(item.getString("title"));
        Float all_num = (Float.valueOf(item.getString("red_number"))+Float.valueOf(item.getString("blue_number")));
        Float red_num =Float.valueOf(item.getString("red_number"));
        Float blue_bum =Float.valueOf(item.getString("blue_number"));
        Float  a =Float.valueOf(df.format((red_num/all_num)));
        Float  b =Float.valueOf(df.format((blue_bum/all_num)));
        if(a<0.3){
                a=(float)0.3;
                b=(float)0.7;
        }else if(b<0.3){
                b=(float)0.3;
                a=(float)0.7;
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,a);
        LinearLayout.LayoutParams lplp = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,b);
        holder.game_betting_topic_red_number.setLayoutParams(lp);
        holder.game_betting_topic_blue_number.setLayoutParams(lplp);
        holder.game_betting_topic_red_number.setText("红方"+item.getString("red_number")+"人");
        holder.game_betting_topic_blue_number.setText("蓝方"+item.getString("blue_number")+"人");
        if(item.getString("my_team").equals("0")){
                my_team="红方";
        }else {
                my_team="蓝方";
        }
        if(item.getString("winner").equals("0")){
                holder.winner.setText("红方押注人数少，为获胜方");
                holder.best_member_name.setText(item.getString("best_member_name")+"赢得"+item.getString("best")+"颗爱心值");
                holder.my_team.setText("押"+my_team+item.getString("my_topic_code")+"颗爱心值，已赢得"+item.getString("win_code")+"颗爱心值");
        }else if(item.getString("winner").equals("1")){
                holder.winner.setText("蓝方押注人数少，为获胜方");
                holder.best_member_name.setText(item.getString("best_member_name")+"赢得"+item.getString("best")+"颗爱心值");
                holder.my_team.setText("押"+my_team+item.getString("my_topic_code")+"颗爱心值，已赢得"+item.getString("win_code")+"颗爱心值");
        }else if(item.getString("winner").equals("2")){
                if((Integer.valueOf(item.getString("red_number")))<(Integer.valueOf(item.getString("blue_number")))){
                        holder.winner.setText("红方押注人数少，为获胜方");
                }else {
                        holder.winner.setText("蓝方押注人数少，为获胜方");
                }
                holder.best_member_name.setText("无人");
                holder.my_team.setText("押"+my_team+item.getString("my_topic_code")+"颗爱心,已返还");
        }else {
                holder.winner.setText("红方押注人数"+item.getString("red_number")+"，蓝方押注人数"+item.getString("blue_number")+"，平局！");
                holder.best_member_name.setText("平局，没有最佳");
                holder.my_team.setText("押"+my_team+item.getString("my_topic_code")+"颗爱心,已返还");
        }
        if(item.getString("my_team").equals("null")){
                holder.my_team.setText("您没有参加上期的话题");
        }
        return convertView;
        }

     public class ViewHolder {
    TextView game_betting_topic_time,game_betting_topic_title,game_betting_topic_red_number,game_betting_topic_blue_number,winner,best_member_name,my_team;
}
}
package com.zhulaozhijias.zhulaozhijia.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.widgets.GlideCircleTransform;
import com.zhulaozhijias.zhulaozhijia.widgets.HorizontalProgressBar;
import com.zhulaozhijias.zhulaozhijia.widgets.XCRoundRectImageView;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by asus on 2017/9/19.
 */

public class Fragment_2_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public Context context;
    public JSONArray lists = new JSONArray();
    public Fragment_2_Adapter(Context context,JSONArray lists) {
        this.context=context;
        this.lists=lists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.publicity_item_activity,null);
        viewHolder=new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final JSONObject item = lists.getJSONObject(position);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.0");
        MyViewHolder holder1 = (MyViewHolder) holder;
        Glide.with(context).load(item.getString("photo")).into(holder1.publicity_head);
        holder1.publicity_img.setImageResource(R.drawable.publicity_img);
        Double d = Double.valueOf(item.getString("required"));
        Double d1 =Double.valueOf(item.getString("get_money"));
        float d2=(new Double(((d1/d)*100))).floatValue();
        df.format(d2);
        Double e = d/10000;
        holder1.get_money.setText(df.format(e)+"万");
        holder1.publicity_name.setText(item.getString("member_name"));
        holder1.publicity_plan.setText(item.getString("plan_name"));
        holder1.hor.setProgress(d2);
        holder1.hor.startProgressAnimation();
//        String notice_begin_time =formatter.format(Long.valueOf(item.getString("notice_begin_time")));
//        holder1.publicity_begin.setText(notice_begin_time);
//        holder1.publicity_date.setText(item.getString("notice_end_time"));
//        holder1.suishou_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(onItemClicListener!=null) {
//                    onItemClicListener.onItemClic(view, position,
//                            item.getString("member_id"),item.getString("member_name"),item.getString("notice_id"));
//                }
//            }
//        });

        holder1.observiue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClicListeners!=null) {
                    onItemClicListeners.onItemClics(view, position,item.getString("notice_id"));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView publicity_img;
        private ImageView publicity_head;
        private TextView publicity_name,publicity_plan,publicity_date,
                observiue_btn,get_money,publicity_begin;
//        private Button suishou_btn;
        private HorizontalProgressBar hor;
        public MyViewHolder(View itemView) {
            super(itemView);
            publicity_head= (ImageView) itemView.findViewById(R.id.publicity_head);
            publicity_img= (ImageView) itemView.findViewById(R.id.publicity_img);
            publicity_name= (TextView) itemView.findViewById(R.id.publicity_name);
            publicity_plan= (TextView) itemView.findViewById(R.id.publicity_plan);
//            publicity_date= (TextView) itemView.findViewById(R.id.publicity_date);
            get_money= (TextView) itemView.findViewById(R.id.get_money);
            hor= (HorizontalProgressBar) itemView.findViewById(R.id.hor);
//            suishou_btn= (Button) itemView.findViewById(R.id.suishou_btn);
            observiue_btn= (TextView) itemView.findViewById(R.id.observiue_btn);
//            publicity_begin= (TextView) itemView.findViewById(R.id.publicity_begin);
        }
    }

    private OnItemClicListener onItemClicListener;
    public void setOnClicListener(OnItemClicListener onItemClicListener){
        this.onItemClicListener = onItemClicListener;
    }
    public  interface OnItemClicListener{
        void onItemClic(View view, int position,String donated_id,String donated_name,String notice_id);
    }

    private OnItemClicListeners onItemClicListeners;
    public void setOnClicListeners(OnItemClicListeners onItemClicListeners){
        this.onItemClicListeners = onItemClicListeners;
    }
    public  interface OnItemClicListeners{
        void onItemClics(View view, int position,String notice);
    }
}

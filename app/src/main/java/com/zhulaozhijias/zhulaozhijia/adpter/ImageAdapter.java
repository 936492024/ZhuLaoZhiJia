package com.zhulaozhijias.zhulaozhijia.adpter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.zhulaozhijias.zhulaozhijia.R;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * Created by baiyuliang on 2016-5-27.
 */
public class ImageAdapter extends BaseAdapter<ImageAdapter.MyViewHolder> {

    DisplayMetrics dm;
    private Activity mActivity;
    private JSONArray jjj;

    public ImageAdapter(Context context, JSONArray listDatas) {
        super(context, listDatas);
        dm = new DisplayMetrics();
        mActivity= (Activity) context;
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grid,null);
//        动态设置ImageView的宽高，根据自己每行item数量计算
//        dm.widthPixels-dip2px(20)即屏幕宽度-左右10dp+10dp=20dp再转换为px的宽度，最后/3得到每个item的宽高
        RelativeLayout.LayoutParams lp;
        lp = new RelativeLayout.LayoutParams((dm.widthPixels ), (int)((dm.widthPixels )/1.5));
//        lp = new RelativeLayout.LayoutParams((dm.widthPixels ), (dm.widthPixels ));
        view.setLayoutParams(lp);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final JSONObject item = listDatas.getJSONObject(position);



        String url =  item.getString("photo");//转换
        Glide.with(context).load(url).into(holder.iv);
        holder.gongshi_name.setText(item.getString("member_name"));
        holder.gongshi_reson.setText(item.getString("plan_name"));
        holder.gongshi_money.setText(item.getString("get_money")+"元");
        holder.describe.setText(item.getString("intro"));
        setTextMarquee(holder.describe);

        //            // TODO: 2017/9/25 监听
        holder.chakanxiangqing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClicListener.onItemClic(view,position,item.getString("notice_id"));
            }
        });
    }
    @Override
    public int getItemCount() {
        return listDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;
        private TextView gongshi_name,gongshi_reson,gongshi_money,describe;
        private TextView chakanxiangqing;

        public MyViewHolder(View view) {
            super(view);
            iv = (ImageView) view.findViewById(R.id.iv);
            gongshi_name= (TextView) view.findViewById(R.id.gongshi_name);
            gongshi_reson= (TextView) view.findViewById(R.id.gongshi_reson);
            gongshi_money= (TextView) view.findViewById(R.id.gongshi_money);
            describe= (TextView) view.findViewById(R.id.describe);
            chakanxiangqing= (TextView) view.findViewById(R.id.chakanxiangqing);
        }
    }
    private OnItemClicListeners onItemClicListener;
    public void setOnClicListeners(OnItemClicListeners onItemClicListener){
        this.onItemClicListener = onItemClicListener;
    }

    public  interface OnItemClicListeners{
        void onItemClic(View view, int position,String notice_id);
    }


    int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setTextMarquee(TextView textView) {
        if (textView != null) {
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setLines(6);
            textView.setSelected(true);
            textView.setFocusable(true);
            textView.setFocusableInTouchMode(true);
        }
    }
}

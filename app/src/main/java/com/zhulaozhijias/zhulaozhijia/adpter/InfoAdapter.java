package com.zhulaozhijias.zhulaozhijia.adpter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.zhulaozhijias.zhulaozhijia.R;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Created by baiyuliang on 2016-5-27.
 */
public class InfoAdapter extends BaseAdapter<InfoAdapter.MyViewHolder> {

    public InfoAdapter(Context context, JSONArray listDatas, OnViewClickListener onViewClickListener) {
        super(context, listDatas, onViewClickListener);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.item_info, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final JSONObject item=listDatas.getJSONObject(position);
            String a =item.getString("0");
            JSONArray jsonArray = JSONArray.fromObject(a);
            holder.gongshi_qishu.setText(item.getString("year")+"年"+
                item.getString("mounth")+"月"+"第"+
                item.getString("count")+"期");//填充数据
            ImageAdapter imageAdapter = new ImageAdapter(context, jsonArray);
            holder.rv_grid.setLayoutManager(new LinearLayoutManager(context));
            holder.rv_grid.setAdapter(imageAdapter);
        //// TODO: 2017/9/25 监听

            imageAdapter.setOnClicListeners(new ImageAdapter.OnItemClicListeners() {
                @Override
                public void onItemClic(View view, int position, String notice_id) {
                    onItemClicListener1.onItemClic(view,position,notice_id);
                }
            });

//        holder.iv_z.setOnClickListener(new ViewClikListener(onViewClickListener, position, 1));//赞 viewtype=1代表赞点击事件
//        holder.iv_pl.setOnClickListener(new ViewClikListener(onViewClickListener, position, 2));//评论 viewtype=2代表评论点击事件
    }

    @Override
    public int getItemCount() {
        return listDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView gongshi_qishu,fabu_time;//内容
        RecyclerView rv_grid;

        public MyViewHolder(View view) {
            super(view);
            gongshi_qishu = (TextView) view.findViewById(R.id.gongshi_qishu);
            rv_grid = (RecyclerView) view.findViewById(R.id.rv_grid);
            fabu_time= (TextView) view.findViewById(R.id.fabu_time);
        }
    }

    /**
     * view的点击事件
     */
    private OnItemClicListeners1 onItemClicListener1;
    public void setOnClicListeners1(OnItemClicListeners1 onItemClicListener){
        this.onItemClicListener1 = onItemClicListener;
    }

    public  interface OnItemClicListeners1{
        void onItemClic(View view, int position,String notice_id);
    }

}

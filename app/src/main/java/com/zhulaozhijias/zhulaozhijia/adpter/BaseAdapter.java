package com.zhulaozhijias.zhulaozhijia.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.sf.json.JSONArray;

/**
 * BaseAdapter
 * Created by baiyuliang on 2016-5-27.
 */
public class BaseAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    public Context context;//上下文
    public JSONArray listDatas;//数据源
    public LayoutInflater mInflater;
    public OnViewClickListener onViewClickListener;//item子view点击事件
    public OnItemClickListener onItemClickListener;//item点击事件


    public BaseAdapter(Context context, JSONArray listDatas) {
        init(context,listDatas);
    }

    /**
     * 如果item的子View有点击事件，可使用该构造方法
     * @param context
     * @param listDatas
     * @param onViewClickListener
     */
    public BaseAdapter(Context context, JSONArray listDatas, OnViewClickListener onViewClickListener) {
        init(context,listDatas);
        this.onViewClickListener = onViewClickListener;
    }

    /**
     * 初始化
     * @param context
     * @param listDatas
     */
    void init(Context context, JSONArray listDatas){
        this.context = context;
        this.listDatas = listDatas;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(T holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {//item点击事件
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listDatas.size();
    }

    /**
     * item中子view的点击事件（回调）
     */
    public interface OnViewClickListener {
        /**
         * @param position item position
         * @param viewtype 点击的view的类型，调用时根据不同的view传入不同的值加以区分
         */
        void onViewClick(int position, int viewtype);
    }

    /**
     * item点击事件
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }



    /**
     * 设置item点击事件
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



}

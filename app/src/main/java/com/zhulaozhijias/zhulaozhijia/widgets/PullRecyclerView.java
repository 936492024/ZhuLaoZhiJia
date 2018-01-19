package com.zhulaozhijias.zhulaozhijia.widgets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * RecyclerView中的所有方法都可以在此类中设置，暴露出去以供调用
 */
public class PullRecyclerView extends PullBaseView<RecyclerView> {


    public PullRecyclerView(Context context) {
        this(context, null);
    }

    public PullRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected RecyclerView createRecyclerView(Context context, AttributeSet attrs) {
        return new RecyclerView(context, attrs);
    }


    /**
     * 案例适配器
     * @param adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mRecyclerView.setLayoutManager(manager);
    }

    /**
     *
     * 排行榜适配器
     * @param adapter
     */
    public void setRank_TabAdapters(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }
    public void setRank_TabLayoutManagers(RecyclerView.LayoutManager manager) {
        mRecyclerView.setLayoutManager(manager);
    }


    /**
     * Frgment_2适配器
     * @param adapter
     */
    public void setFragment2Adapterss(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }


    public void setFragment2LayoutManagers(RecyclerView.LayoutManager manager) {
        mRecyclerView.setLayoutManager(manager);
    }


    public void setFragment2Adapter2(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }


    public void setFragment2LayoutManager2(RecyclerView.LayoutManager manager) {
        mRecyclerView.setLayoutManager(manager);
    }

    public void setFragment2Adapter3(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }


    public void setFragment2LayoutManager3(RecyclerView.LayoutManager manager) {
        mRecyclerView.setLayoutManager(manager);
    }


    /**
     * 游戏往期话题回顾适配器
     * @param adapter
     */
    public void setGameAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }


    public void setGameLayoutManager(RecyclerView.LayoutManager manager) {
        mRecyclerView.setLayoutManager(manager);
    }
}

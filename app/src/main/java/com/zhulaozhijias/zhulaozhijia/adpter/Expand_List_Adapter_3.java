package com.zhulaozhijias.zhulaozhijia.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.zhulaozhijias.zhulaozhijia.R;

/**
 * Created by asus on 2017/10/8.
 */

public class Expand_List_Adapter_3 extends BaseExpandableListAdapter {
    public Context context;
    private String group [] = new String[2];
    private String child [] = new String[1];

    public Expand_List_Adapter_3(Context context,String group [] ,String child []) {
        this.context = context;
        this.group=group;
        this.child=child;
    }

    @Override
    public int getGroupCount() {
        return group.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return group[i];
    }

    @Override
    public Object getChild(int i, int i1) {
        return i1;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder groupViewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.group_3, viewGroup, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tvTitle1 = (TextView) view.findViewById(R.id.group_text_3);
            view.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) view.getTag();
        }
        groupViewHolder.tvTitle1.setText(group[i]);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.child, viewGroup, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tvTitle2 = (TextView) view.findViewById(R.id.text_gruop);

            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }
        childViewHolder.tvTitle2.setText(child[i]);
        return view;
    }
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    static class GroupViewHolder {
        TextView tvTitle1;

    }
    static class ChildViewHolder {
        TextView tvTitle2;
    }
}


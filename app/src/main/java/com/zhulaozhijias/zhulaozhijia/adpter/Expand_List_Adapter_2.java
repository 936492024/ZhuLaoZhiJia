package com.zhulaozhijias.zhulaozhijia.adpter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.zhulaozhijias.zhulaozhijia.R;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by asus on 2017/9/27.
 */

public class Expand_List_Adapter_2 extends BaseExpandableListAdapter {
    public Context context;
    private ArrayList<String> jsonArray = new ArrayList();
    private ArrayList<String> jsonArray2= new ArrayList<>();



    public Expand_List_Adapter_2(Context context,ArrayList<String> jsonArray,ArrayList<String> jsonArray2) {
        this.context = context;
        this.jsonArray=jsonArray;
        this.jsonArray2=jsonArray2;
    }

    @Override
    public int getGroupCount() {
        return jsonArray.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return jsonArray.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return 1;
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
            view = LayoutInflater.from(context).inflate(R.layout.group, viewGroup, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tvTitle = (TextView) view.findViewById(R.id.group_text);
            groupViewHolder.intro = (TextView) view.findViewById(R.id.intro);
            view.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) view.getTag();
        }
        groupViewHolder.intro.setText(jsonArray2.get(i));
        groupViewHolder.tvTitle.setText("常见问题"+(i+1));
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ChildViewHolder childViewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.child, viewGroup, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tvTitle = (TextView) view.findViewById(R.id.text_gruop);

            view.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) view.getTag();
        }
        childViewHolder.tvTitle.setText(jsonArray.get(i));
        return view;
    }
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    static class GroupViewHolder {
        TextView tvTitle;
        TextView intro;
    }
    static class ChildViewHolder {
        TextView tvTitle;
    }
}

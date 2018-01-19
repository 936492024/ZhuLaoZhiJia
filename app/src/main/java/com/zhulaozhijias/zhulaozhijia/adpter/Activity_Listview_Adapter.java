package com.zhulaozhijias.zhulaozhijia.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by asus on 2017/10/5.
 */

public class Activity_Listview_Adapter extends android.widget.BaseAdapter {
    Context context;
    ArrayList<String> list = new ArrayList<>();
    public Activity_Listview_Adapter(Context _context, ArrayList<String> list) {
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
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.activity_listview_item, null);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年-MM月-dd日");

        return convertView;
    }
}

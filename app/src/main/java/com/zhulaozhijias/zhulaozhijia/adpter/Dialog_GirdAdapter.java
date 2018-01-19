package com.zhulaozhijias.zhulaozhijia.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhulaozhijias.zhulaozhijia.R;
import java.util.ArrayList;

public class Dialog_GirdAdapter  extends BaseAdapter{
    private LayoutInflater inflater;
    private ArrayList<String> list;
    public Context context;

    public Dialog_GirdAdapter(Context context,ArrayList<String> list) {
        this.context=context;
        this.list=list;
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
    class ViewHolder {
        public TextView t1;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.fragment_2_dialog, null);
            viewHolder = new ViewHolder();
//            viewHolder.t1 = (TextView) convertView.findViewById(R.id.t1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        viewHolder.t1.setText(list.get(position));
        return convertView;
    }
}


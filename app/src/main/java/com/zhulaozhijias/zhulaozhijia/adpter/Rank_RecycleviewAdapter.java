package com.zhulaozhijias.zhulaozhijia.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.zhulaozhijias.zhulaozhijia.R;
import com.zhulaozhijias.zhulaozhijia.base.BPApplication;
import com.zhulaozhijias.zhulaozhijia.widgets.GlideCircleTransform;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by asus on 2017/9/18.
 */

public class Rank_RecycleviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Context context;
    private JSONArray  list = new JSONArray();
    private int i ;
    private ArrayList<String> arrayList = new ArrayList<>();
    public Rank_RecycleviewAdapter(Context context,JSONArray  list ,int i,ArrayList<String> arrayList) {
        this.context=context;
        this.list=list;
        this.i=i;
        this.arrayList=arrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;
        RecyclerView.ViewHolder holder=null;
        if(viewType==1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.heart_rank_item1,null);
            holder = new MyViewHolder(view);
        }else if(viewType==2){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.heart_rank_item2,null);
            holder = new MyViewHolder2(view);
        }else if(viewType==3){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.heart_rank_item3,null);
            holder = new MyViewHolder3(view);
        }else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.heart_rank_item4,null);
            holder = new MyViewHolder4(view);
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return 1;
        }else if(position==1){
            return 2;
        }else if(position==2){
            return 3;
        }else {
            return 4;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        final JSONObject item = list.getJSONObject(position);
          if(holder instanceof MyViewHolder){
             final MyViewHolder holder1 = (MyViewHolder) holder;
              if(i==1){
                  Glide.with(context).load(R.mipmap.yaoqing).into(holder1.rt_1);
                  holder1.heart_no1_person_num.setText(item.getString("count")+"人");
                  holder1.fenxiang.setText("累计邀请");
              }else if(i==2){
                  Glide.with(context).load(R.mipmap.renshu).into(holder1.rt_1);
                  holder1.heart_no1_person_num.setText(item.getString("count")+"次");
                  holder1.fenxiang.setText("累计捐助");
              }else  if(i==3){
                  Glide.with(context).load(R.mipmap.juanzhujine).into(holder1.rt_1);
                  holder1.fenxiang.setText("累计捐赠");
                  holder1.heart_no1_person_num.setText(item.getString("money")+"元");
              }
              holder1.heart_no1_nickname.setText(item.getString("name"));
              final String praises=item.getString("praise");
              holder1.zan_num.setText(praises);
              Glide.with(context).load(item.getString("img_path")).placeholder(R.mipmap.portrait)
                      .error(R.mipmap.portrait) .transform(new GlideCircleTransform(context)).into(holder1.heart_no1_head);
//            Glide.with(context).load(R.mipmap.touxiang).into(holder1.heart_no1_head);
              arrayList.add(item.getString("member_id"));
              holder1.praise_1.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      if(onItemClicListener!=null) {
                          onItemClicListener.onItemClic(view, position,item.getString("member_id"));
                          if (arrayList.get(position).equals(BPApplication.getInstance().getMember_Id())) {
                             return;
                          }
                              holder1.heart_zan.setImageResource(R.mipmap.redzan);
                              holder1.zan_num.setText((Integer.valueOf(praises)+1)+"");
                      }
                  }
              });

          }else if(holder instanceof MyViewHolder2){
              final MyViewHolder2 holder2 = (MyViewHolder2) holder;
              if(i==1){
                  Glide.with(context).load(R.mipmap.yaoqing).into(holder2.rt_2);
                  holder2.heart_no2_personnum.setText(item.getString("count")+"人");
                  holder2.yaoqings.setText("累计邀请");
              }else if(i==2){
                  holder2.heart_no2_personnum.setText(item.getString("count")+"次");
                  Glide.with(context).load(R.mipmap.renshu).into(holder2.rt_2);
                  holder2.yaoqings.setText("累计捐助");
              }else  if(i==3){
                  Glide.with(context).load(R.mipmap.juanzhujine).into(holder2.rt_2);
                  holder2.yaoqings.setText("累计捐赠");
                  holder2.heart_no2_personnum.setText(item.getString("money")+"元");
              }
              holder2.heart_no2_nickname.setText(item.getString("name"));
              final String praises_2=item.getString("praise");
              holder2.heart_no2_zannum.setText(praises_2);
              Glide.with(context).load(item.getString("img_path")).placeholder(R.mipmap.portrait)
                      .error(R.mipmap.portrait) .transform(new GlideCircleTransform(context)).into(holder2.heart_no2_head);
              holder2.praise_2.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      if(onItemClicListener!=null) {
                          onItemClicListener.onItemClic(view, position,item.getString("member_id"));
                          if (arrayList.get(position).equals(BPApplication.getInstance().getMember_Id())) {
                              return;
                          }
                          holder2.heart_no2_zan.setImageResource(R.mipmap.redzan);
                          holder2.heart_no2_zannum.setText((Integer.valueOf(praises_2)+1)+"");
                      }
                  }
              });

          }else if(holder instanceof MyViewHolder3){
              final MyViewHolder3 holder3 = (MyViewHolder3) holder;

              if(i==1){
                  Glide.with(context).load(R.mipmap.yaoqing).into(holder3.rt_3);
                  holder3.heart_no3_personnum.setText(item.getString("count")+"人");
                  holder3.juanzeng.setText("累计邀请");
              }else if(i==2){
                  Glide.with(context).load(R.mipmap.renshu).into(holder3.rt_3);
                  holder3.heart_no3_personnum.setText(item.getString("count")+"次");
                  holder3.juanzeng.setText("累计捐助");
              }else  if(i==3){
                  Glide.with(context).load(R.mipmap.juanzhujine).into(holder3.rt_3);
                  holder3.juanzeng.setText("累计捐赠");
                  holder3.heart_no3_personnum.setText(item.getString("money")+"元");
              }
              holder3.heart_no3_nickname.setText(item.getString("name"));
              final  String praises_3 =item.getString("praise");
              holder3.heart_no3_zannum.setText(praises_3);
              Glide.with(context).load(item.getString("img_path")).placeholder(R.mipmap.portrait)
                      .error(R.mipmap.portrait) .transform(new GlideCircleTransform(context)).into(holder3.heart_no3_head);
              holder3.praise_3.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      if(onItemClicListener!=null) {
                          onItemClicListener.onItemClic(view, position,item.getString("member_id"));
                          if (arrayList.get(position).equals(BPApplication.getInstance().getMember_Id())) {
                              return;
                          }
                          holder3.heart_no3_zan.setImageResource(R.mipmap.redzan);
                          holder3.heart_no3_zannum.setText((Integer.valueOf(praises_3)+1)+"");
                      }
                  }
              });

          }else if(holder instanceof MyViewHolder4){
              final MyViewHolder4 holder4 = (MyViewHolder4) holder;

              if(i==1){
                  holder4.heart_no4_personnum.setText(item.getString("count")+"人");
                  Glide.with(context).load(R.mipmap.yaoqing).into(holder4.rt_4);
                  holder4.juanzeng_2.setText("累计邀请");
              }else if(i==2){
                  holder4.heart_no4_personnum.setText(item.getString("count")+"次");
                  Glide.with(context).load(R.mipmap.renshu).into(holder4.rt_4);
                  holder4.juanzeng_2.setText("累计捐助");
              }else  if(i==3){
                  Glide.with(context).load(R.mipmap.juanzhujine).into(holder4.rt_4);
                  holder4.juanzeng_2.setText("累计捐赠");
                  holder4.heart_no4_personnum.setText(item.getString("money")+"元");
              }
              holder4.heart_no4_nickname.setText(item.getString("name"));
              final  String praises_4=item.getString("praise");
              holder4.heart_no4_zannum.setText(praises_4);
              holder4.heart_no4_silver.setText((position+1)+"");
              Glide.with(context).load(item.getString("img_path")).placeholder(R.mipmap.portrait)
                      .error(R.mipmap.portrait) .transform(new GlideCircleTransform(context)).into(holder4.heart_no4_head);
              holder4.praise_4.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      if(onItemClicListener!=null) {
                          onItemClicListener.onItemClic(view, position,item.getString("member_id"));
                          if (arrayList.get(position).equals(BPApplication.getInstance().getMember_Id())) {
                              return;
                          }
                          holder4.heart_no4_zan.setImageResource(R.mipmap.redzan);
                          holder4.heart_no4_zannum.setText((Integer.valueOf(praises_4)+1)+"");
                      }
                  }
              });
          }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView heart_no1_head,heart_zan,rt_1;
        private RelativeLayout praise_1;
        private TextView heart_no1_person_num,heart_no1_nickname,zan_num,fenxiang;
        public MyViewHolder(View itemView) {
            super(itemView);
            heart_no1_head= (ImageView) itemView.findViewById(R.id.heart_no1_head);
            heart_zan= (ImageView) itemView.findViewById(R.id.heart_zan);
            heart_no1_person_num= (TextView) itemView.findViewById(R.id.heart_no1_person_num);
            heart_no1_nickname= (TextView) itemView.findViewById(R.id.heart_no1_nickname);
            zan_num= (TextView) itemView.findViewById(R.id.zan_num);
            praise_1= (RelativeLayout) itemView.findViewById(R.id.praise_1);
            rt_1= (ImageView) itemView.findViewById(R.id.rt_1);
            fenxiang= (TextView) itemView.findViewById(R.id.fenxiang);
        }
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder {
        private ImageView heart_no2_head,heart_no2_zan,rt_2;
        private RelativeLayout praise_2;
        private TextView heart_no2_nickname,heart_no2_personnum,heart_no2_zannum,yaoqings;
        public MyViewHolder2(View itemView) {
            super(itemView);
            heart_no2_head= (ImageView) itemView.findViewById(R.id.heart_no2_head);
            heart_no2_zan= (ImageView) itemView.findViewById(R.id.heart_no2_zan);
            heart_no2_nickname= (TextView) itemView.findViewById(R.id.heart_no2_nickname);
            heart_no2_personnum= (TextView) itemView.findViewById(R.id.heart_no2_personnum);
            heart_no2_zannum= (TextView) itemView.findViewById(R.id.heart_no2_zannum);
            rt_2= (ImageView) itemView.findViewById(R.id.rt_2);
            yaoqings= (TextView) itemView.findViewById(R.id.yaoqings);
            praise_2= (RelativeLayout) itemView.findViewById(R.id.praise_2);
        }
    }
    class MyViewHolder3 extends RecyclerView.ViewHolder {
        private RelativeLayout praise_3;
        private ImageView heart_no3_head,heart_no3_zan,rt_3;
        private TextView heart_no3_nickname,heart_no3_personnum,heart_no3_zannum,juanzeng;
        public MyViewHolder3(View itemView) {
            super(itemView);
            praise_3= (RelativeLayout) itemView.findViewById(R.id.praise_3);
            heart_no3_head= (ImageView) itemView.findViewById(R.id.heart_no3_head);
            heart_no3_zan= (ImageView) itemView.findViewById(R.id.heart_no3_zan);
            heart_no3_nickname= (TextView) itemView.findViewById(R.id.heart_no3_nickname);
            heart_no3_personnum= (TextView) itemView.findViewById(R.id.heart_no3_personnum);
            heart_no3_zannum= (TextView) itemView.findViewById(R.id.heart_no3_zannum);
            rt_3= (ImageView) itemView.findViewById(R.id.rt_3);
            juanzeng= (TextView) itemView.findViewById(R.id.juanzeng);
        }
    }
    class MyViewHolder4 extends RecyclerView.ViewHolder {
        private RelativeLayout praise_4;
        private ImageView heart_no4_head,heart_no4_zan,rt_4;
        private TextView heart_no4_nickname,heart_no4_personnum,heart_no4_zannum,heart_no4_silver,juanzeng_2;
        public MyViewHolder4(View itemView) {
            super(itemView);
            praise_4= (RelativeLayout) itemView.findViewById(R.id.praise_4);
            heart_no4_zan= (ImageView) itemView.findViewById(R.id.heart_no4_zan);
            heart_no4_head= (ImageView) itemView.findViewById(R.id.heart_no4_head);
            heart_no4_silver= (TextView) itemView.findViewById(R.id.heart_no4_silver);
            heart_no4_nickname= (TextView) itemView.findViewById(R.id.heart_no4_nickname);
            heart_no4_personnum= (TextView) itemView.findViewById(R.id.heart_no4_personnum);
            heart_no4_zannum= (TextView) itemView.findViewById(R.id.heart_no4_zannum);
            rt_4= (ImageView) itemView.findViewById(R.id.rt_4);
            juanzeng_2= (TextView) itemView.findViewById(R.id.juanzeng_2);
        }
    }

    private OnItemClicListener onItemClicListener;
    public void setOnClicListener(OnItemClicListener onItemClicListener){
        this.onItemClicListener = onItemClicListener;
    }
    public  interface OnItemClicListener{
        void onItemClic(View view, int position,String meberid);
    }
}

package com.zhulaozhijias.zhulaozhijia.adpter;

/**
 * Created by asus on 2017/9/15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.zhulaozhijias.zhulaozhijia.R;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * @author: lijuan
 * @description: 适配器
 * @date: 2016-12-04
 * @time: 17:04
 */
public class VideoAdapter extends android.widget.BaseAdapter {

    int[] videoIndexs = {1};
    Context context;
    LayoutInflater mInflater;

    public VideoAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return videoIndexs.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null && convertView.getTag() != null && convertView.getTag() instanceof VideoHolder) {
            ((VideoHolder) convertView.getTag()).jcVideoPlayer.release();
        }
        if (videoIndexs[position] == 1) {
            VideoHolder viewHolder;
            if (convertView != null && convertView.getTag() != null && convertView.getTag() instanceof VideoHolder) {
                viewHolder = (VideoHolder) convertView.getTag();
            } else {
                viewHolder = new VideoHolder();
                LayoutInflater mInflater = LayoutInflater.from(context);
                convertView = mInflater.inflate(R.layout.item_videoview, null);
                viewHolder.jcVideoPlayer = (JCVideoPlayerStandard) convertView.findViewById(R.id.videoplayer);
                convertView.setTag(viewHolder);
            }
            boolean setUp = viewHolder.jcVideoPlayer.setUp(
                    "http://www.zhulaozhijia.org/skin/shipin/20170821.mp4", JCVideoPlayer.SCREEN_LAYOUT_LIST,"");
            if (setUp) {
                Glide.with(context).load("http://a4.att.hudong.com/05/71/01300000057455120185716259013.jpg").into(viewHolder.jcVideoPlayer.thumbImageView);
            }
        }
        return convertView;
    }

    class VideoHolder {
        JCVideoPlayerStandard jcVideoPlayer;
    }
}

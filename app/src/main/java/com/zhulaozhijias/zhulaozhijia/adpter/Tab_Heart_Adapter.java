package com.zhulaozhijias.zhulaozhijia.adpter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;


/**
 * Created by asus on 2017/9/14.
 */

public class Tab_Heart_Adapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> list= new ArrayList();
    private String [] titles= new String[3];
    public Tab_Heart_Adapter(FragmentManager fm,ArrayList<Fragment> list,String [] titles) {
        super(fm);
        this.list=list;
        this.titles=titles;
    }
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    //重写这个方法，将设置每个Tab的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}


package com.yl.baiduren.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by yan on 2017/12/5.
 */

public class AssignmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mListfragment; //创建一个List<Fragment>


    public AssignmentPagerAdapter(FragmentManager fm,List<Fragment> mListfragment) {
        super(fm);
        this.mListfragment = mListfragment;
        FragmentManager mFragmetnmanager = fm;
    }
    @Override
    public Fragment getItem(int position) {
        return mListfragment.get(position);
    }

    @Override
    public int getCount() {
        return mListfragment.size();
    }
}

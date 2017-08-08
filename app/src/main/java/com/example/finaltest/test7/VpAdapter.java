package com.example.finaltest.test7;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by pinan on 2017/7/24.
 */

public class VpAdapter extends FragmentPagerAdapter {
    private String[] title = {constan.T1,constan.T2};
    
    public VpAdapter(FragmentManager fm) {
        super(fm);
    }
    
    @Override
    public Fragment getItem(int position) {
        return MyFragment.newIntance(title[position]);
    }
    
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
    
    @Override
    public int getCount() {
        return title.length;
    }
}

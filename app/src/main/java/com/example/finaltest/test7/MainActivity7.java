package com.example.finaltest.test7;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.finaltest.R;
import com.example.finaltest.app.BaseActivity;

import butterknife.Bind;

/**
 * Created by pinan on 2017/7/18.
 */

public class MainActivity7 extends BaseActivity {
    @Bind(R.id.vp)
    ViewPager vp;
    @Bind(R.id.tl)
    TabLayout tl;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        vp.setAdapter(new VpAdapter(getSupportFragmentManager()));
        tl.setupWithViewPager(vp);
    }
}

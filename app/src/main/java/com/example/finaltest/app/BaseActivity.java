package com.example.finaltest.app;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by pinan on 2017/7/7.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getDelegate().setContentView(layoutResID);
        ButterKnife.bind(this);
    }
    
    @Override
    public void setContentView(View view) {
        getDelegate().setContentView(view);
        ButterKnife.bind(this);
    }
    
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().setContentView(view, params);
        ButterKnife.bind(this);
    }
    
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}

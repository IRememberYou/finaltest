package com.example.finaltest.test4;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.finaltest.R;
import com.example.finaltest.app.BaseActivity;

import butterknife.OnClick;

/**
 * Created by pinan on 2017/7/14.
 */

public class main extends BaseActivity {
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mian);
    }
    
    @OnClick(R.id.calendar)
    void calendar() {
        startActivity(new Intent(main.this, MainActivity4.class));
    }
    
    @OnClick(R.id.date)
    void date() {
        
    }
}
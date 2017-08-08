package com.example.finaltest.test6;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.finaltest.R;
import com.example.finaltest.app.BaseActivity;

/**
 * Created by pinan on 2017/7/18.
 */

public class MainActivity6 extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
//        PercentView percentView = (PercentView) findViewById(R.id.percentview);
//        percentView.setAngel(56);
//        percentView.setRankText("11111", "100");
    
        MyView myView = (MyView) findViewById(R.id.myview);
        myView.setAngel(49);
        myView.setCircleColor(Color.parseColor("#FF4E87F2"));
        MyView myView2 = (MyView) findViewById(R.id.myview222);
        myView2.setAngel(60);
        myView2.setCircleColor(Color.parseColor("#FF4E87F2"));
    }
}

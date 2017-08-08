package com.example.finaltest.test9;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.finaltest.R;
import com.example.finaltest.app.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;

/**
 * Created by pinan on 2017/7/27.
 */

public class MainActivity9 extends BaseActivity {
    @Bind(R.id.rv)
    RecyclerView rv;
    //    private Main9Adapter2 mAdapter;
    private MyAdapter mAdapter;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);
//        mAdapter = new Main9Adapter2(getData());
        List<TimerItem> data2 = getData2();Collections.sort(data2,new TimerItem(null,-1));
        
        mAdapter = new MyAdapter(this, data2);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(mAdapter);
        for (int i = 0; i < data2.size(); i++) {
            Log.d("data2", (data2.get(i).expirationTime - System.currentTimeMillis()) / 1000 + "");
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mAdapter.unRegisterCountDownTimer();
    }
    
    public List<Bean> getData() {
        ArrayList<Bean> list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Bean bean = new Bean();
            bean.title = "item " + i;
            bean.time = 20 + (int) (Math.random() * 100 + 10);
            list.add(bean);
        }
        return list;
    }
    
    public List<TimerItem> getData2() {
        List<TimerItem> lstTimerItems = new ArrayList<>();
        lstTimerItems.add(new TimerItem("A", System.currentTimeMillis() + 11 * 1000));
        lstTimerItems.add(new TimerItem("B", System.currentTimeMillis() + 22 * 1000));
        lstTimerItems.add(new TimerItem("C", System.currentTimeMillis() + 26 * 1000));
        lstTimerItems.add(new TimerItem("D", System.currentTimeMillis() + 33 * 1000));
        lstTimerItems.add(new TimerItem("E", System.currentTimeMillis() + 44 * 1000));
        lstTimerItems.add(new TimerItem("F", System.currentTimeMillis() + 98 * 1000));
        lstTimerItems.add(new TimerItem("G", System.currentTimeMillis() + 14 * 1000));
        lstTimerItems.add(new TimerItem("H", System.currentTimeMillis() + 36 * 1000));
        lstTimerItems.add(new TimerItem("I", System.currentTimeMillis() + 58 * 1000));
        lstTimerItems.add(new TimerItem("J", System.currentTimeMillis() + 47 * 1000));
        lstTimerItems.add(new TimerItem("K", System.currentTimeMillis() + 66 * 1000));
        lstTimerItems.add(new TimerItem("L", System.currentTimeMillis() + 55 * 1000));
        lstTimerItems.add(new TimerItem("M", System.currentTimeMillis() + 62 * 1000));
        lstTimerItems.add(new TimerItem("N", System.currentTimeMillis() + 45 * 1000));
        lstTimerItems.add(new TimerItem("O", System.currentTimeMillis() + 15 * 1000));
        lstTimerItems.add(new TimerItem("p", System.currentTimeMillis() + 15 * 1000));
        lstTimerItems.add(new TimerItem("l", System.currentTimeMillis() + 15 * 1000));
        lstTimerItems.add(new TimerItem("L", System.currentTimeMillis() + 15 * 1000));
        return lstTimerItems;
    }
}
 
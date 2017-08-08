package com.example.finaltest.test1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.finaltest.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ViewSwitcher.ViewFactory {
    @Bind(R.id.ts)
    TextSwitcher ts;
    // 要显示的文本
    private String[] strs = new String[]
        {
            "one",
            "two",
            "three"
        };
    private int curStr = 0;
    private int curr;
    Timer countTimer = new Timer();
    
    TimerTask coTimerTask = new TimerTask() {
        
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (ts != null) {
                        curr=curStr++ % strs.length;
                        ts.setText(strs[curr]);
                    }
                }
            });
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        ButterKnife.bind(this);
        ts.setFactory(this);
        ts.setInAnimation(this, R.anim.m_in);
        ts.setOutAnimation(this, R.anim.m_out);
        countTimer.schedule(coTimerTask, 1, 2000);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        coTimerTask.cancel();
        countTimer.cancel();
        ButterKnife.unbind(this);
    }
    
    
    @Override
    public View makeView() {
        Log.d("makeView", "makeView: ---------");
         TextView tv = new TextView(MainActivity.this);
        tv.setTextSize(40);
        // 字体颜色品红
        tv.setTextColor(Color.MAGENTA);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,strs[curr], Toast.LENGTH_SHORT).show();
            }
        });
        return tv;
    }
}
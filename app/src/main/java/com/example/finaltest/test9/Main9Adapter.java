package com.example.finaltest.test9;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.finaltest.R;

import java.util.List;

/**
 * Created by pinan on 2017/7/27.
 */

public class Main9Adapter extends BaseQuickAdapter<Bean, BaseViewHolder> {
    
    private Timer mTimer;
    
    public Main9Adapter(@Nullable List<Bean> data) {
        super(R.layout.item_main9, data);
    }
    
    @Override
    protected void convert(BaseViewHolder holder, Bean b) {
        holder.setText(R.id.tv_title, b.title);
        
        TextView textView = holder.getView(R.id.tv_time);
        
        mTimer = new Timer(b.time, textView);
        CountdownManager.getInstance().registerCountDownTimer(mTimer);
        
    }
    
    class Timer extends CountDownTimer {
        
        private final TextView mTextView;
        
        public Timer(long time, TextView textView) {
            remainTime = time;
            mTextView = textView;
        }
        
        @Override
        public void onTimeChange(int hour, int minute, int second, long remainTime) {
            if (remainTime <= 0) {
                if (mTimer != null) {
                    CountdownManager.getInstance().unRegisterCountDownTimer(mTimer);
                    mTimer = null;
                        mTextView.setText("时间到了");
                }
                Log.d("registerCountDownTimer ", hour + "===时1" + minute + "===分" + second + "====秒" + mTimer);
            } else {
                Log.d("registerCountDownTimer ", hour + "===时" + minute + "===分" + second + "====秒" + mTimer);
                    mTextView.setText("倒计时(h:m:s)格式：" + hour + "：" + minute + ":" + second + "   ,S(格式)：" + remainTime);
            }
        }
    }
    
    public void unRegisterCountDownTimer() {
        if (mTimer != null) {
            Log.v("registerCountDownTimer", mTimer + "销毁时间");
            CountdownManager.getInstance().unRegisterCountDownTimer(mTimer);
            mTimer = null;
        }
    }
}

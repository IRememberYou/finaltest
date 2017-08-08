package com.example.finaltest.test9;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finaltest.R;

import java.util.List;

//适配器
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    
    private List<TimerItem> mDatas;
    //用于退出activity,避免countdown，造成资源浪费。
    private SparseArray<CountDownTimer> countDownMap;
    
    public MyAdapter(Context context, List<TimerItem> datas) {
        mDatas = datas;
        countDownMap = new SparseArray<>();
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_common, parent, false);
        return new ViewHolder(view);
    }
    
    /**
     * 清空资源
     */
    public void cancelAllTimers() {
        if (countDownMap == null) {
            return;
        }
        Log.e("TAG", "size :  " + countDownMap.size());
        for (int i = 0, length = countDownMap.size(); i < length; i++) {
            CountDownTimer cdt = countDownMap.get(countDownMap.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
    }
    
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final TimerItem data = mDatas.get(position);
        holder.statusTv.setText(data.name);
        long time = data.expirationTime;
        time = time - System.currentTimeMillis();
        //将前一个缓存清除
        if (holder.countDownTimer != null) {
            holder.countDownTimer.cancel();
            holder.countDownTimer =null;
        }
        if (time > 0) {
            holder.countDownTimer = new CountDownTimer(time, 1000) {
                public void onTick(long millisUntilFinished) {
                    holder.timeTv.setText(millisUntilFinished/1000+"s");
                    Log.e("TAG", data.name + " :  " + millisUntilFinished);
                }
                
                public void onFinish() {
                    holder.timeTv.setText("00:00:00");
                    holder.statusTv.setText(data.name + ":结束");
                }
            }.start();
            
            countDownMap.put(holder.timeTv.hashCode(), holder.countDownTimer);
        } else {
            holder.timeTv.setText("00:00:00");
            holder.statusTv.setText(data.name + ":结束");
        }
        
    }
    
    @Override
    public int getItemCount() {
        if (mDatas != null && !mDatas.isEmpty()) {
            return mDatas.size();
        }
        return 0;
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView statusTv;
        public TextView timeTv;
        public CountDownTimer countDownTimer;
        
        public ViewHolder(View itemView) {
            super(itemView);
            statusTv = (TextView) itemView.findViewById(R.id.tv_status);
            timeTv = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }
}
 
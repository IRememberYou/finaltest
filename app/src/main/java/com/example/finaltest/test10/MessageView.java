package com.example.finaltest.test10;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by pinan on 2017/8/8.
 */

public class MessageView extends View {
    private String TAG = "MessageView";
    private PointF mDragPoint;
    private PointF mFixationPoint;
    
    private int mDragRadius = dip2px(10);//拖拽圆半径
    private int mFixationRadius = dip2px(7);//固定圆半径
    private Paint mPaint;
    private Paint mStrokePaint;
    
    
    public MessageView(Context context) {
        super(context);
        init();
    }
    
    public MessageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    
    public MessageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        //辅助画笔（用于画圆）
        mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mStrokePaint.setColor(Color.GRAY);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        
        PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);//设置为虚线
        mStrokePaint.setPathEffect(effects);
        
        
    }
    
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDragPoint == null || mFixationPoint == null) {
            return;
        }
        //画辅助圆
        canvas.drawCircle(mFixationPoint.x, mFixationPoint.y, dip2px(50), mStrokePaint);
        
        //画拖拽圆
        canvas.drawCircle(mDragPoint.x, mDragPoint.y, mDragRadius, mPaint);
        int distance = getDistance(mDragPoint, mFixationPoint);
        mFixationRadius = dip2px(7) - distance / 14;
        if (mFixationRadius > dip2px(3) || distance < dip2px(50)) {
            //画固定圆
            canvas.drawCircle(mFixationPoint.x, mFixationPoint.y, mFixationRadius, mPaint);
            canvas.drawPath(getBezierPath(), mPaint);
        }
    }
    
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                dealDown(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                dealMove(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                dealUp(event.getX(), event.getY());
                break;
        }
        invalidate();
        return true;
    }
    
    private void dealUp(float x, float y) {
        if (getDistance(mFixationPoint, new PointF(x, y)) > dip2px(50)) {
            mDragPoint = null;
            mFixationPoint = null;
        }else {
            mDragPoint = mFixationPoint;
        }
    }
    
    private void dealDown(float x, float y) {
        mDragPoint = new PointF(x, y);
        mFixationPoint = new PointF(x, y);
        Log.d(TAG, "startPoint: " + mDragPoint.toString());
    }
    
    
    private void dealMove(float x, float y) {
        mDragPoint.x = x;
        mDragPoint.y = y;
        Log.d(TAG, "mFixationPoint: " + mFixationPoint.toString());
    }
    
    
    /**
     * 获取两点之间的距离
     *
     * @param pointF1
     * @param pointF2
     * @return
     */
    private int getDistance(PointF pointF1, PointF pointF2) {
        double powX = Math.abs(pointF1.x - pointF2.x);
        double powY = Math.abs(pointF1.y - pointF2.y);
        return (int) Math.sqrt(powX * powX + powY * powY);
    }
    
    
    /**
     * 获取 Bezier 曲线
     *
     * @return
     */
    public Path getBezierPath() {
        
        if (mFixationRadius < 3) {
            return null;
        }
        
        Path bezierPath = new Path();
        
        
        // 计算斜率
        float dx = mFixationPoint.x - mDragPoint.x;
        float dy = mFixationPoint.y - mDragPoint.y;
        if (dx == 0) {
            dx = 0.001f;
        }
        float tan = dy / dx;
        // 获取角a度值
        float arcTanA = (float) Math.atan(tan);
        
        // 依次计算 p0 , p1 , p2 , p3 点的位置
        float P0X = (float) (mFixationPoint.x + mFixationRadius * Math.sin(arcTanA));
        float P0Y = (float) (mFixationPoint.y - mFixationRadius * Math.cos(arcTanA));
        
        float P1X = (float) (mDragPoint.x + mDragRadius * Math.sin(arcTanA));
        float P1Y = (float) (mDragPoint.y - mDragRadius * Math.cos(arcTanA));
        
        float P2X = (float) (mDragPoint.x - mDragRadius * Math.sin(arcTanA));
        float P2Y = (float) (mDragPoint.y + mDragRadius * Math.cos(arcTanA));
        
        float P3X = (float) (mFixationPoint.x - mFixationRadius * Math.sin(arcTanA));
        float P3Y = (float) (mFixationPoint.y + mFixationRadius * Math.cos(arcTanA));
        // 求控制点 两个点的中心位置作为控制点
        PointF controlPoint = getPointByPercent(mDragPoint, mFixationPoint, 0.5f);
        
        // 整合贝塞尔曲线路径
        bezierPath.moveTo(P0X, P0Y);
        bezierPath.quadTo(controlPoint.x, controlPoint.y, P1X, P1Y);
        bezierPath.lineTo(P2X, P2Y);
        bezierPath.quadTo(controlPoint.x, controlPoint.y, P3X, P3Y);
        bezierPath.close();
        
        return bezierPath;
    }
    
    private PointF getPointByPercent(PointF dragPoint, PointF fixationPoint, float v) {
        float x = (dragPoint.x + fixationPoint.x) / 2;
        float y = (dragPoint.y + fixationPoint.y) / 2;
        return new PointF(x, y);
    }
    
    
    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }
}

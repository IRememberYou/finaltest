package com.example.finaltest.test6;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by pinan on 2017/8/3.
 */

public class BezierView extends View {
    
    
    private int mStartXPoint;
    private int mStartYPoint;
    private int mEndXPoint;
    private int mEndYPoint;
    private int mOneXPoint;
    private int mOneYPoint;
    private int mTwoXPoint;
    private int mTwoYPoint;
    private Path mPath;
    private Paint mPaint;
    private Paint mLinePaint;
    private Paint mTextPaint;
    private int mMoveXPoint;
    private int mMoveYPoint;
    
    
    public BezierView(Context context) {
        super(context);
        init4(context);
    }
    
    public BezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init4(context);
    }
    
    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init4(context);
    }
    
    private void init4(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int widthPixels = displayMetrics.widthPixels;
        int heightPixels = displayMetrics.heightPixels;
        mStartXPoint = widthPixels / 4;
        mStartYPoint = heightPixels / 2;
        
        mEndXPoint = widthPixels * 3 / 4;
        mEndYPoint = heightPixels / 2;
        
        mOneXPoint = widthPixels / 2 - 300;
        mOneYPoint = heightPixels / 2 - 400;
        
        mTwoXPoint = widthPixels / 2 + 100;
        mTwoYPoint = heightPixels / 2 - 400;
        
        mMoveXPoint = widthPixels / 4;
        mMoveYPoint = heightPixels / 2;
        
        
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        
        //fuzhuxian
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.GRAY);
        mLinePaint.setStrokeWidth(3);
        mLinePaint.setStyle(Paint.Style.STROKE);
        
        //wenben
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(20);
        mTextPaint.setStyle(Paint.Style.STROKE);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(mStartXPoint, mStartYPoint);
        mPath.cubicTo(mOneXPoint, mOneYPoint, mTwoXPoint, mTwoYPoint, mEndXPoint, mEndYPoint);
        canvas.drawPath(mPath, mPaint);
        
        //辅助线
        canvas.drawLine(mStartXPoint, mStartYPoint, mOneXPoint, mOneYPoint, mLinePaint);
        canvas.drawLine(mOneXPoint, mOneYPoint, mTwoXPoint, mTwoYPoint, mLinePaint);
        canvas.drawLine(mTwoXPoint, mTwoYPoint, mEndXPoint, mEndYPoint, mLinePaint);
        
        //文字
        drawPoint(canvas, mStartXPoint, mStartYPoint, "起始点", 30);
        drawPoint(canvas, mOneXPoint, mOneYPoint, "控制点1", -30);
        drawPoint(canvas, mTwoXPoint, mTwoYPoint, "控制点2", -30);
        drawPoint(canvas, mEndXPoint, mEndYPoint, "结束点", 30);
        
        canvas.drawCircle(mMoveXPoint, mMoveYPoint, 20, mPaint);
        
    }
    
    
    private void drawPoint(Canvas canvas, int x, int y, String text, int dx) {
        canvas.drawPoint(x, y, mPaint);
        canvas.drawText(text, x, y + dx, mTextPaint);
    }
    
    private boolean flag;
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            ValueAnimator valueAnimator = ValueAnimator.ofObject(new CirclePointEvaluator(), new Point(mStartXPoint, mStartYPoint),
                new Point(mEndXPoint, mEndYPoint));
            valueAnimator.setDuration(600);
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Point point = (Point) animation.getAnimatedValue();
                    mMoveXPoint = point.x;
                    mMoveYPoint = point.y;
                    invalidate();
                }
            });
            valueAnimator.start();
        }
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
                flag = true;
                break;
            case MotionEvent.ACTION_MOVE:
                mOneXPoint = (int) event.getX(0);
                mOneYPoint = (int) event.getY(0);
                if (flag) {
                    mTwoXPoint = (int) event.getX(1);
                    mTwoYPoint = (int) event.getY(1);
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                flag = false;
                break;
        }
        invalidate();
        
        return true;
        
    }
    
    /**
     * 自定义Evaluator
     */
    public class CirclePointEvaluator implements TypeEvaluator {
        
        /**
         * @param t          当前动画进度
         * @param startValue 开始值
         * @param endValue   结束值
         * @return
         */
        @Override
        public Object evaluate(float t, Object startValue, Object endValue) {
            
            Point A = (Point) startValue;
            Point B = new Point(mOneXPoint, mOneYPoint);
            Point C = new Point(mTwoXPoint, mTwoYPoint);
            Point D = (Point) endValue;
            
            float temp = 1 - t;
            
            int x = (int) (A.x * temp * temp * temp + B.x * 3 * temp * temp * t + C.x * 3 * temp * t * t + D.x * t * t * t);
            int y = (int) (A.y * temp * temp * temp + B.y * 3 * temp * temp * t + C.y * 3 * temp * t * t + D.y * t * t * t);
            
            return new Point(x, y);
        }
        
    }
    
    /**
     * (移动二阶贝塞尔曲线) extends View
     */
//    private Path mPath;
//    private Paint mPaint;
//    private Paint mCirlcePaint;
//    private int mCircleRadius;
//    private int mStartXPoint;
//    private int mStartYPoint;
//    private int mEndXPoint;
//    private int mEndYPoint;
//    private int mConXPoint;
//    private int mConYPoint;
//    private int mMoveXPoint;
//    private int mMoveYPoint;
//    private Paint mMovePaint;
//    private void init3(Context context) {
//        mStartXPoint = 100;
//        mStartYPoint = 100;
//        
//        mEndXPoint = 600;
//        mEndYPoint = 600;
//        
//        mConXPoint = 400;
//        mConYPoint = 0;
//        
//        mMoveXPoint = 100;
//        mMoveYPoint = 100;
//        
//        mPath = new Path();
//        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaint.setColor(Color.BLUE);
//        mPaint.setStrokeWidth(10);
//        mPaint.setStyle(Paint.Style.STROKE);
//        
//        mCircleRadius = 20;
//        mCirlcePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mCirlcePaint.setColor(Color.BLUE);
//        mCirlcePaint.setStyle(Paint.Style.FILL);
//    
//        mMovePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mMovePaint.setColor(Color.RED);
//        mMovePaint.setStyle(Paint.Style.FILL);
//    
//    
//    }
//    
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        mPath.reset();
//        mPath.moveTo(mStartXPoint, mStartYPoint);
//        mPath.quadTo(mConXPoint, mConYPoint, mEndXPoint, mEndYPoint);
//        canvas.drawPath(mPath, mPaint);
//        
//        canvas.drawCircle(mStartXPoint, mStartXPoint, mCircleRadius, mCirlcePaint);
//        canvas.drawCircle(mEndXPoint, mEndYPoint, mCircleRadius, mCirlcePaint);
//        canvas.drawCircle(mMoveXPoint, mMoveYPoint, mCircleRadius, mMovePaint);
//        
//    }
//    
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            ValueAnimator valueAnimator = ValueAnimator.ofObject(new MyTypeEvaluator(), new Point(mStartXPoint, mStartYPoint)
//                , new Point(mEndXPoint, mEndYPoint));
//            valueAnimator.setDuration(1000);
//            valueAnimator.setInterpolator(new DecelerateInterpolator());
//            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    Point point = (Point) animation.getAnimatedValue();
//                    mMoveXPoint = point.x;
//                    mMoveYPoint = point.y;
//                    invalidate();
//                }
//            });
//            valueAnimator.start();
//        }
//        return true;
//        
//    }
//    
//    class MyTypeEvaluator implements TypeEvaluator {
//        
//        @Override
//        public Object evaluate(float fraction, Object startValue, Object endValue) {
//            Point pointX = (Point) startValue;
//            Point pointY = (Point) endValue;
//            float temp = 1 - fraction;
//            int x = (int) (temp * temp * pointX.x + 2 * temp * fraction*mConXPoint + fraction * fraction * pointY.x);
//            int y = (int) (temp * temp * pointX.y + 2 * temp * fraction*mConYPoint + fraction * fraction * pointY.y);
//            
//            return new Point(x,y);
//        }
//    }
    
    /**
     * (二阶贝塞尔曲线) extends View
     */
//    private String TAG = "BezierView";
//    private int mStartXPoint;
//    private int mStartYPoint;
//    private int mEndXPoint;
//    private int mEndYPoint;
//    private int mConXPoint;
//    private int mConYPoint;
//    private Path mPath;
//    private Paint mPaint;
//    private Paint mLineLayoutPaint;
//    private Paint mTextPaint;
//    private void init2(Context context) {
//        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
//        int widthPixels = displayMetrics.widthPixels;
//        int heightPixels = displayMetrics.heightPixels;
//
//        mStartXPoint = widthPixels / 4;
//        mStartYPoint = heightPixels / 2;
//
//        mEndXPoint = widthPixels * 3 / 4;
//        mEndYPoint = heightPixels / 2;
//
//        mConXPoint = widthPixels / 2;
//        mConYPoint = heightPixels / 2 - 400;
//
//        mPath = new Path();
//        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaint.setColor(Color.RED);
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(8);
//
//        //辅助线
//        mLineLayoutPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mLineLayoutPaint.setColor(Color.BLUE);
//        mLineLayoutPaint.setStyle(Paint.Style.STROKE);
//        mLineLayoutPaint.setStrokeWidth(3);
//
//        //文本
//        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mTextPaint.setColor(Color.BLACK);
//        mTextPaint.setStyle(Paint.Style.STROKE);
//        mTextPaint.setTextSize(20);
//
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        mPath.reset();
//        mPath.moveTo(mStartXPoint, mStartYPoint);
//        mPath.quadTo(mConXPoint, mConYPoint, mEndXPoint, mEndYPoint);
//        canvas.drawPath(mPath, mPaint);
//
//        canvas.drawLine(mStartXPoint, mStartYPoint, mConXPoint, mConYPoint, mLineLayoutPaint);
//        canvas.drawLine(mConXPoint, mConYPoint, mEndXPoint, mEndYPoint, mLineLayoutPaint);
//
//        canvas.drawPoint(mStartXPoint, mStartYPoint, mPaint);
//        canvas.drawText("起始点", mStartXPoint, mStartYPoint + 30, mTextPaint);
//
//        canvas.drawPoint(mConXPoint, mConYPoint, mPaint);
//        canvas.drawText("控制点", mConXPoint, mConYPoint - 30, mTextPaint);
//
//        canvas.drawPoint(mEndXPoint, mEndYPoint, mPaint);
//        canvas.drawText("结束点", mEndXPoint, mEndYPoint + 30, mTextPaint);
//
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if (event.getAction() == MotionEvent.ACTION_MOVE) {
//            mConXPoint = (int) event.getX();
//            mConYPoint = (int) event.getY();
//            invalidate();
//        }
//        return true;
//    }
    /**
     *    画心 extends LinearLayout
     */

//    private void init() {
//        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heart);
//        Bitmap mOutBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
//        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        Canvas canvas = new Canvas(mOutBitmap);
//        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
//        canvas.drawColor(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
//        ImageView imageView = new ImageView(getContext());
//        imageView.setImageBitmap(mOutBitmap);
//        addView(imageView);
//    }
}

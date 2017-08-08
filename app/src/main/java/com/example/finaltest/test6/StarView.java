package com.example.finaltest.test6;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.finaltest.R;

import java.util.Random;

/**
 * Created by pinan on 2017/8/3.
 */

public class StarView extends RelativeLayout {
    
    
    private Paint mPaint;
    private Path mPath;
    private int mWidth;
    private int mHeight;
    private Random mRandom;
    private Bitmap mBitmap;
    private Point mStartPoint;
    private Point mEndPoint;
    private Point mConOnePoint;
    private Point mConTwoPoint;
    
    public StarView(Context context) {
        super(context);
    }
    
    public StarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    
    public StarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init() {
        mPath = new Path();
        
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        
        mRandom = new Random();
        
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_star);
        
        
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mStartPoint = new Point(mWidth / 2, mHeight);
        mEndPoint = new Point(mWidth / 2, 0);
        mConOnePoint = new Point(mWidth, mHeight * 3 / 4);
        mConTwoPoint = new Point(0, mHeight / 4);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mStartPoint = new Point(mWidth / 2, mHeight);
        mEndPoint = new Point((int) (mWidth / 2 + 150 * mRandom.nextFloat()), 0);
        mConOnePoint = new Point((int) (mWidth * mRandom.nextFloat()), (int) (mHeight * mRandom.nextFloat() * 3 / 4));
        mConTwoPoint = new Point(0, (int) (mHeight * mRandom.nextFloat() / 4));
        addStar();
        return true;
        
    }
    
    protected int[] mColors = {Color.BLUE, Color.CYAN, Color.GREEN, Color.RED, Color.MAGENTA, Color.YELLOW};
    
    
    private void addStar() {
        Bitmap bitmap = drawStar(mColors[mRandom.nextInt(mColors.length)]);
        ImageView imageView = new ImageView(getContext());
        imageView.setImageBitmap(bitmap);
        addView(imageView);
        
        
        
    }
    
    private Bitmap drawStar(int color) {
        Bitmap bitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        canvas.drawColor(color, PorterDuff.Mode.SRC_IN);
        return bitmap;
    }
}

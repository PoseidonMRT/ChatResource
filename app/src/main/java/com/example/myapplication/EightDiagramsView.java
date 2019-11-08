package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class EightDiagramsView extends View {

    private Point mTopCircleCenter;
    private Point mBottomCircleCenter;

    private float mSmallerCircleRadius = 100;

    private Paint mPaint;

    private Path mLeftDiagramPath;
    private Path mRightDiagramPath;

    public EightDiagramsView(Context context) {
        super(context);
    }

    public EightDiagramsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EightDiagramsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public EightDiagramsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制背景色
        canvas.drawColor(Color.GRAY);

        mTopCircleCenter = new Point(getWidth()/2,getWidth()/4);
        mBottomCircleCenter = new Point(getWidth()/2,getWidth()*3/4);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);

        mLeftDiagramPath = new Path();
        mLeftDiagramPath.addArc(0,0,getWidth(),getWidth(),90,180);
        mLeftDiagramPath.addArc(getWidth()/4,0,getWidth()*3/4,getWidth()/2,-90,180);
        mLeftDiagramPath.addArc(getWidth()/4,getWidth()/2,getWidth()*3/4,getWidth(),-90,-180);
        canvas.drawPath(mLeftDiagramPath,mPaint);

        mPaint.setColor(Color.BLACK);
        mRightDiagramPath = new Path();
        mRightDiagramPath.addArc(0,0,getWidth(),getWidth(),90,-180);
        mRightDiagramPath.addArc(getWidth()/4,0,getWidth()*3/4,getWidth()/2,-90,180);
        mRightDiagramPath.addArc(getWidth()/4,getWidth()/2,getWidth()*3/4,getWidth(),-90,-180);
        canvas.drawPath(mRightDiagramPath,mPaint);

        canvas.drawCircle(mTopCircleCenter.x,mTopCircleCenter.y,mSmallerCircleRadius,mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(mBottomCircleCenter.x,mBottomCircleCenter.y,mSmallerCircleRadius,mPaint);


        Path path = new Path();
        path.addCircle(300,300,200, Path.Direction.CW);
    }
}

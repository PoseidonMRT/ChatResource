package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class OtherCustomView extends View {
    public OtherCustomView(Context context) {
        super(context);
    }

    public OtherCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public OtherCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public OtherCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        //新建Path对象
        Path path = new Path();
        //移动路径起点到(30,30)，默认起点在(0,0)位置
        path.moveTo(30,30);
        //描述(30,30)到(600,30)这段直线段
        path.lineTo(600,30);
        //描述(600,30)到(600,1500)这段直线段
        path.lineTo(600,1500);
        //描述(600,1500)到(30,30)这段直线段
        path.close();//这里也可以调用path.close()方法，Path类会默认上一个点添加到起点的直线段

//        Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DITHER_FLAG);
        Paint linePaint = new Paint();
        linePaint.setStrokeWidth(20);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeJoin(Paint.Join.BEVEL);
        linePaint.setStrokeCap(Paint.Cap.SQUARE);


        canvas.drawPath(path,linePaint);
    }
}

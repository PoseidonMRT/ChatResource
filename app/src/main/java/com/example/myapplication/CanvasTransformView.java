package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CanvasTransformView extends View {
    public CanvasTransformView(Context context) {
        super(context);
    }

    public CanvasTransformView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CanvasTransformView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CanvasTransformView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        //移动画布坐标原点到View几何中心
//        canvas.translate(getWidth()/2,getHeight()/2);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);

        paint.setColor(Color.GRAY);
        //绘制原始圆环
        canvas.drawRect(100,100,600,600,paint);
        canvas.save();
        //y轴逆时针旋转45度，x轴不变
        canvas.skew(1,0);
        paint.setColor(Color.BLUE);
        //绘制缩放0.5f的圆环
        canvas.drawRect(100,100,600,600,paint);
        canvas.save();
        canvas.restore();
    }
}

package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class OfoBgView extends View {
    public OfoBgView(Context context) {
        super(context);
    }

    public OfoBgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public OfoBgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public OfoBgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        Path path = new Path();

        //选取整个View的宽高绘制背景
        path.moveTo(getLeft(), getTop()+(getBottom()-getTop())/10);
        //绘制A'D'，控制点在AD平分线上
        path.quadTo(getLeft()+(getRight()-getLeft())/2 , getTop()-(getBottom()-getTop())/10,getRight() , getTop()+(getBottom()-getTop())/10);
        //绘制D'C
        path.lineTo(getRight(), getBottom());
        //绘制CB
        path.lineTo(getLeft(), getBottom());

        //闭合曲线，自动绘制BA'
        path.close();
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path,paint);
    }
}

package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MyTextView extends View {
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

        String s = "Hello";


        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(100);


        textPaint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(s,500,300,textPaint);

        textPaint.setColor(Color.GREEN);
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(s,500,300,textPaint);

        textPaint.setColor(Color.BLACK);
        textPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(s,500,300,textPaint);

        Paint pointPaint = new Paint();
        pointPaint.setColor(Color.RED);
        pointPaint.setStrokeWidth(20);
        canvas.drawPoint(500,300,pointPaint);


        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

        TextPaint textPaint1 = new TextPaint();
        textPaint1.setColor(Color.BLACK);
        textPaint1.setTextSize(100);

        canvas.drawCircle(700,1400,600,paint);

        Rect rect = new Rect();
        textPaint.getTextBounds(s,0,s.length(),rect);
        int width = rect.width();
        int height = rect.height();

        canvas.drawText(s,700-width/2,1400+height/2,textPaint1);

        canvas.drawPoint(700,1400,pointPaint);
    }
}

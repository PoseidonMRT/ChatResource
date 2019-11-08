package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class FerModeView extends View {
    public FerModeView(Context context) {
        super(context);
    }

    public FerModeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FerModeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FerModeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        Bitmap dstBitmap = makeDst(getWidth()/2,getWidth()/2);
        Bitmap srcBitmap = makeSrc(getWidth(),getWidth()/2);

        Bitmap bitmap = createBitamp(getWidth(), getHeight());
        Canvas resultCanvas = new Canvas(bitmap);

        Paint xfermodePaint = new Paint();
        resultCanvas.drawBitmap(dstBitmap, 0, 0, xfermodePaint);
        xfermodePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
        resultCanvas.drawBitmap(srcBitmap, 0, 0, xfermodePaint);

        Paint paint = new Paint();
        canvas.drawBitmap(bitmap,0,0,paint);
    }

    //创建一个圆形bitmap，作为dst图
    private Bitmap makeDst(int width, int height) {
        Bitmap bm = createBitamp(width, height);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0xFFFFCC44);
        c.drawOval(new RectF(0, 0, width / 3 * 2, height / 3 * 2), p);
        return bm;
    }

    // 创建一个矩形bitmap，作为src图
    private Bitmap makeSrc(int width, int height) {
        Bitmap bm = createBitamp(width, height);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0xFF66AAFF);
        c.drawRect(100, 100, width, height, p);
        return bm;
    }

    private Bitmap createBitamp(int w, int h){
        return Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    }
}

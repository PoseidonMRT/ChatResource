package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class GradientView extends View {
    public GradientView(Context context) {
        super(context);
    }

    public GradientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);

        int[] mColors = {Color.RED,Color.YELLOW,Color.GREEN,Color.BLUE};

//        RadialGradient；

        LinearGradient linearGradient =
                new LinearGradient(0, 0, 400, 400, mColors, null, Shader.TileMode.MIRROR);
        paint.setShader(linearGradient);
        canvas.drawRect(0,0,400,400,paint);

        paint.setTextSize(200);
        canvas.drawText("注意：",200,600,paint);

        float[] sweepPositions = {0.1f,0.1f,0.8f};
        int[] sweepColors = {Color.RED,Color.YELLOW,Color.GREEN};
        SweepGradient sweepGradient = new SweepGradient(550,850, sweepColors, sweepPositions);

        paint.setShader(sweepGradient);
        canvas.drawRect(100,700,1000,1000,paint);

        int[] radialColors = {Color.RED,Color.YELLOW,Color.GREEN};
        RadialGradient radialGradient = new RadialGradient(700,1700,200,radialColors,null,TileMode.REPEAT);
        paint.setShader(radialGradient);
        canvas.drawCircle(700,1700,700,paint);
    }

    private Bitmap scaleBitmap(Bitmap origin, float ratio) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }
}

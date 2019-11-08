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

public class FiveStarredRedFlagView extends View {
    public static final String TAG = "FiveStarredRedFlagView";

    public FiveStarredRedFlagView(Context context) {
        super(context);
    }

    public FiveStarredRedFlagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FiveStarredRedFlagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FiveStarredRedFlagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
        //五星红旗背景长960，高640
        int width = 960;
        int height = 640;
        //五星红旗左上角顶点X坐标
        int startX = 100;
        //五星红旗左上角顶点Y坐标
        int startY = 100;

        //绘制五星红旗矩形背景
        Paint redBgPaint = new Paint();
        redBgPaint.setColor(Color.RED);
        canvas.drawRect(startX, startY, startX + width, startY + height, redBgPaint);

        //大五角星外接圆半径，直径为高度十分之三
        float bigRadius = (float) (height * 0.3) / 2.0f;
        //大五角星外接圆圆心，上五下五，左五右十
        Point bigCircleCenter = new Point(startX + width / 6, startY + height / 4);
        //大五角星绘制路径
        Path bigCirclePath = getStarredPath(bigCircleCenter.x, bigCircleCenter.y, bigRadius);

        //小五角星外接圆半径，直径为高度的十分之一
        float smallerRadius = (float) (height * 0.1) / 2.0f;

        //第一个小五角星外接圆圆心，上二下八height*(2/10)*(1/2)，左十右五width*(10/15)*(1/2)
        Point smallerCenter1 = new Point(startX + width / 3, startY + height / 10);
//        Path smallerPath1 = getSmallerStarredPath(smallerCenter1.x, smallerCenter1.y, smallerRadius,168);
        Path smallerPath1 = getStarredPath(smallerCenter1.x, smallerCenter1.y, smallerRadius);

        //第二个小五角星外接圆圆心，上四下六height*(4/10)*(1/2)，左十二右三width*(12/15)*(1/2)
        Point smallerCenter2 = new Point(startX + width*6 / 15, startY + height / 5);
//        Path smallerPath2 = getSmallerStarredPath(smallerCenter2.x, smallerCenter2.y, smallerRadius,188);
        Path smallerPath2 = getStarredPath(smallerCenter2.x, smallerCenter2.y, smallerRadius);

        //第三个小五角星外接圆圆心，上七下三height*(7/10)*(1/2)，左十二右三width*(12/15)*(1/2)
        Point smallerCenter3 = new Point(startX + width *6/ 15, startY + height *7/ 20);
//        Path smallerPath3 = getSmallerStarredPath(smallerCenter3.x, smallerCenter3.y, smallerRadius,28);
        Path smallerPath3 = getStarredPath(smallerCenter3.x, smallerCenter3.y, smallerRadius);

        //第四个小五角星外接圆圆心，上九下一height*(9/10)*(1/2)，左十右五width*(10/15)*(1/2)
        Point smallerCenter4 = new Point(startX + width / 3, startY + height *9/ 20);
//        Path smallerPath4 = getSmallerStarredPath(smallerCenter4.x, smallerCenter4.y, smallerRadius,48);
        Path smallerPath4 = getStarredPath(smallerCenter4.x, smallerCenter4.y, smallerRadius);

        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(10);
        canvas.drawPath(bigCirclePath, paint);
        canvas.drawPath(smallerPath1, paint);
        canvas.drawPath(smallerPath2, paint);
        canvas.drawPath(smallerPath3, paint);
        canvas.drawPath(smallerPath4, paint);
    }

    private Path getStarredPath(float centerX, float centerY, float radius) {
        Path path = new Path();
        float inR = radius * sin(18) / cos(36);

        path.moveTo(centerX + radius * cos(-72 * 0 - 18), centerY + radius * sin(-72 * 0 - 18));
        path.lineTo(centerX + inR * cos(-72 * 0 - 54), centerY + inR * sin(-72 * 0 - 54));
        path.lineTo(centerX + radius * cos(-72 * 1 - 18), centerY + radius * sin(-72 * 1 - 18));
        path.lineTo(centerX + inR * cos(-72 * 1 - 54), centerY + inR * sin(-72 * 1 - 54));
        path.lineTo(centerX + radius * cos(-72 * 2 - 18), centerY + radius * sin(-72 * 2 - 18));
        path.lineTo(centerX + inR * cos(-72 * 2 - 54), centerY + inR * sin(-72 * 2 - 54));
        path.lineTo(centerX + radius * cos(-72 * 3 - 18), centerY + radius * sin(-72 * 3 - 18));
        path.lineTo(centerX + inR * cos(-72 * 3 - 54), centerY + inR * sin(-72 * 3 - 54));
        path.lineTo(centerX + radius * cos(-72 * 4 - 18), centerY + radius * sin(-72 * 4 - 18));
        path.lineTo(centerX + inR * cos(-72 * 4 - 54), centerY + inR * sin(-72 * 4 - 54));
        path.close();
        return path;
    }

    private Path getSmallerStarredPath(float centerX, float centerY, float radius, int sweepAngel) {
        Path path = new Path();
        float inR = radius * sin(18) / sin(180 - 36 - 18);

        path.moveTo(centerX + radius * cos(-72 * 0 - 18 + sweepAngel), centerY + radius * sin(-72 * 0 - 18 + sweepAngel));
        path.lineTo(centerX + inR * cos(-72 * 0 - 54 + sweepAngel), centerY + inR * sin(-72 * 0 - 54 + sweepAngel));
        path.lineTo(centerX + radius * cos(-72 * 1 - 18 + sweepAngel), centerY + radius * sin(-72 * 1 - 18 + sweepAngel));
        path.lineTo(centerX + inR * cos(-72 * 1 - 54 + sweepAngel), centerY + inR * sin(-72 * 1 - 54 + sweepAngel));
        path.lineTo(centerX + radius * cos(-72 * 2 - 18 + sweepAngel), centerY + radius * sin(-72 * 2 - 18 + sweepAngel));
        path.lineTo(centerX + inR * cos(-72 * 2 - 54 + sweepAngel), centerY + inR * sin(-72 * 2 - 54 + sweepAngel));
        path.lineTo(centerX + radius * cos(-72 * 3 - 18 + sweepAngel), centerY + radius * sin(-72 * 3 - 18 + sweepAngel));
        path.lineTo(centerX + inR * cos(-72 * 3 - 54 + sweepAngel), centerY + inR * sin(-72 * 3 - 54 + sweepAngel));
        path.lineTo(centerX + radius * cos(-72 * 4 - 18 + sweepAngel), centerY + radius * sin(-72 * 4 - 18 + sweepAngel));
        path.lineTo(centerX + inR * cos(-72 * 4 - 54 + sweepAngel), centerY + inR * sin(-72 * 4 - 54 + sweepAngel));
        path.close();
        return path;
    }

    private float cos(int degree) {
        return (float) Math.cos(degree * Math.PI / 180);
    }

    private float sin(int degree) {
        return (float) Math.sin(degree * Math.PI / 180);
    }
}

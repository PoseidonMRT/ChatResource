package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MyCustomView extends View {
    /******    构造过程   ****/
    public MyCustomView(Context context) {
        super(context);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /******    布局过程中的测量部分   ****/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /******    布局过程中的布局部分   ****/
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    /******    测量过程   ****/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //使用30线宽在(20,20)的位置绘制一个点
        Paint pointPaint = new Paint();
        pointPaint.setColor(Color.BLUE);
        pointPaint.setStrokeWidth(30);
        canvas.drawPoint(20,20,pointPaint);

        //使用线宽10，在(20,60),(40,60)及(60,60)这三个位置各绘制一个点
        Paint pointsPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointsPaint.setColor(Color.BLUE);
        pointsPaint.setStrokeWidth(10);
        float[] pts = {20,60,40,60,60,60};
        canvas.drawPoints(pts,pointsPaint);

        //使用线宽10，以(20,80)为起点，(180,80)为终点绘制一条直线
        Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(10);
        linePaint.setColor(Color.BLUE);
        canvas.drawLine(20,80,180,80,linePaint);

        //使用线宽10，分别以(20,100)，(20,120),(20,140)为起点，(180,100),(180,120,180,140)为终点绘制直线
        Paint linesPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linesPaint.setStrokeWidth(10);
        linesPaint.setColor(Color.RED);
        float[] lines = {20,100,180,100,20,120,180,120,20,140,180,140};
        canvas.drawLines(lines,linesPaint);

        //以(20,160)为左上角,(220,260)为右下角，绘制一个矩形线框
        Paint strokeRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokeRectPaint.setStrokeWidth(10);
        strokeRectPaint.setStyle(Paint.Style.STROKE);
        strokeRectPaint.setColor(Color.BLUE);
        canvas.drawRect(20,160,220,260,strokeRectPaint);

        //以(20,270)为左上角,(220,300)为右下角，绘制一个实心矩形
        Paint rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectPaint.setStrokeWidth(10);
        rectPaint.setStyle(Paint.Style.FILL);
        rectPaint.setColor(Color.BLUE);
        canvas.drawRect(20,270,220,300,rectPaint);


        //以(120,400)为圆心，70为半径绘制填充圆
        Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(Color.RED);
        canvas.drawCircle(120,400,70,circlePaint);

        //以(280,400)为圆心，70为半径绘制圆环
        Paint strokeCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokeCirclePaint.setStyle(Paint.Style.STROKE);
        strokeCirclePaint.setColor(Color.RED);
        canvas.drawCircle(280,400,70,strokeCirclePaint);

        //绘制以(380，20)为左上顶点，(480，100)为右下顶点的内切填充椭圆
        Paint ovalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ovalPaint.setStyle(Paint.Style.FILL);
        ovalPaint.setColor(Color.RED);
        canvas.drawOval(380,20,480,100,ovalPaint);

        //绘制以(500，20)为左上顶点，(600，100)为右下顶点的内切椭圆线框
        Paint strokeOvalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        strokeOvalPaint.setStyle(Paint.Style.STROKE);
        strokeOvalPaint.setColor(Color.RED);
        canvas.drawOval(500,20,600,100,strokeOvalPaint);


        //以400，130为左上顶点绘制bitmap
        Paint bitmapPaint = new Paint();
        //通过BitmapFactory获取bitmap对象
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic);
        //调用canvas对象的drawBitmap方法绘制bitmap
        canvas.drawBitmap(bitmap,400,130,bitmapPaint);

        //使用matrix绘制bitmap，这里通过matrix将bitmap宽高压缩到0.2*width，0.2*height
        Paint matrixBitmapPaint = new Paint();
        Bitmap matrixBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pic);
        //新建Matrix对象
        Matrix matrix = new Matrix();
        matrix.postTranslate(1000,1000);
        //matrix对象应用缩放比
        matrix.postScale(0.2f,0.2f);
        canvas.drawBitmap(matrixBitmap,matrix,matrixBitmapPaint);


        //绘制左上顶点为(20,620),右下顶点为(200,820)的矩形内切椭圆从0度到90所围成的封闭区域
        Paint arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setStyle(Paint.Style.FILL);
        arcPaint.setColor(Color.BLUE);
        RectF rectF = new RectF(20,620,200 ,820 );
        canvas.drawArc(rectF,0,90.0f,false,arcPaint);

        //绘制左上顶点为(20,820),右下顶点为(200,1020)的矩形内切椭圆从0度到90划过的圆弧区域端点连接椭圆几何中心所围成的封闭区域
        Paint arcPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint2.setStyle(Paint.Style.FILL);
        arcPaint2.setColor(Color.BLUE);
        RectF rectF2 = new RectF(20,820,200 ,1020 );
        canvas.drawArc(rectF2,0,90.0f,true,arcPaint2);

        //绘制左上顶点为(20,1020),右下顶点为(200,1220)的矩形内切椭圆从0度到90划过的圆弧区域
        Paint arcStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcStrokePaint.setStyle(Paint.Style.STROKE);
        arcStrokePaint.setColor(Color.BLUE);
        RectF strokeRectF = new RectF(20,1020,200 ,1220 );
        canvas.drawArc(strokeRectF,0,90.0f,false,arcStrokePaint);

        //绘制左上顶点为(20,1220),右下顶点为(200,1420)的矩形内切椭圆从0度到90划过的圆弧区域端点连接椭圆几何中心所围成的线框区域
        Paint arcStrokePaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcStrokePaint2.setStyle(Paint.Style.STROKE);
        arcStrokePaint2.setColor(Color.BLUE);
        RectF strokeRectF2 = new RectF(20,1220,200 ,1420 );
        canvas.drawArc(strokeRectF2,0,90.0f,true,arcStrokePaint);

        //以(20,1620)为基点绘制字符串
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置字体画笔颜色
        textPaint.setColor(Color.BLUE);
        //设置字体大小
        textPaint.setTextSize(30);
        //设置字体样式
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText("Hello MyCustomView",20,1620,textPaint);
    }
}

package com.example.myapplication;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.text.DecimalFormat;

/**
 * @author 秤砣控件
 * @Date 2018/8/22
 * @description
 * @since 1.0.0
 */
public class WeightView extends View {

    /**
     * View的宽度和高度
     */
    private int mWidth;
    private int mHeight;

    /**
     * 上切面内部椭圆画笔
     */
    private Paint mInnerOvalPaint;

    /**
     * 上切面外部椭圆画笔
     */
    private Paint mOuterOvalPaint;

    /**
     * 体重值刻度画笔
     */
    private Paint mSwipeProgressRuleLinePaint;

    /**
     * 固定刻度画笔
     */
    private Paint mMarkLinePaint;

    /**
     * 固定刻度值画笔
     */
    private TextPaint mMarkTextPaint;

    /**
     * 刻度值高度
     */
    private float mMarkTextHeight;

    /**
     * 绘制在中央位置的刻度值
     */
    private int mCenterMarkValue = 50;

    /**
     * 精确的体重值
     */
    private int mWeight = -1;

    /**
     * 用于动画的体重值
     */
    private float mTmpWeight;

    private ValueAnimator mWeightAnimator;

    /**
     * 渐变部分Path路径
     */
    private Path mOvalPath;

    /**
     * 渐变部分画笔
     */
    private Paint mBottomOvalPaint;

    /**
     * 渐变层的动画
     */
    private ValueAnimator mValueAnimator;

    /**
     * 渐变左侧部分的宽度百分比
     */
    private float mSwipeProgress = 0.5f;

    /**
     * 在Animator内部配合绘制左侧渐变部分增长动画
     */
    private float mTmpSwipeProgress = 0.0f;

    private ValueAnimator mMarkValueAnimator;
    private float[] mPositionDistance;

    public WeightView(Context context) {
        this(context, null);
    }

    public WeightView(Context context,
                      @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeightView(Context context,
                      @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            mWidth = w;
            mHeight = h;
            if (mWeight != -1) {
                startAnimation();
            }
        }
    }

    private void init() {
        mInnerOvalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerOvalPaint.setColor(getResources().getColor(R.color.colorFF00D4FF));
        mInnerOvalPaint.setStrokeWidth(8);
        mInnerOvalPaint.setStyle(Paint.Style.STROKE);

        mOuterOvalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOuterOvalPaint.setColor(getResources().getColor(R.color.colorFF00404D));
        mOuterOvalPaint.setStrokeWidth(4);
        mOuterOvalPaint.setStyle(Paint.Style.STROKE);

        mBottomOvalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBottomOvalPaint.setColor(getResources().getColor(R.color.colorFF00404D));
        mBottomOvalPaint.setStrokeWidth(4);
        mBottomOvalPaint.setStyle(Style.FILL_AND_STROKE);

        mSwipeProgressRuleLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSwipeProgressRuleLinePaint.setColor(getResources().getColor(R.color.colorFF00D4FF));
        mSwipeProgressRuleLinePaint.setStrokeWidth(8);
        mSwipeProgressRuleLinePaint.setStyle(Paint.Style.STROKE);

        mMarkLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMarkLinePaint.setColor(Color.parseColor("#FF045B6D"));
        mMarkLinePaint.setStrokeWidth(8);

        mMarkTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mMarkTextPaint.setColor(Color.parseColor("#FF546D72"));
        mMarkTextPaint.setTextSize(30);

        mMarkTextHeight = getFontHeight(mMarkTextPaint) / 3;
    }

    public void setWeight(int weight) {
        mPositionDistance = new float[2];

        mWeight = weight;
        mTmpWeight = weight;

        mCenterMarkValue = (weight / 10) * 10;

        int tmp = weight % 10;
        float result = ((float) tmp) / 10;
        mSwipeProgress = 0.5f + result * 0.2f;

        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        drawLeftBottomOval(canvas);

        drawRightBottomOval(canvas);

        //上层外部椭圆
        canvas.drawOval(0 + mOuterOvalPaint.getStrokeWidth(), 0,
                mWidth - mOuterOvalPaint.getStrokeWidth(), mHeight * 0.4f, mOuterOvalPaint);

        //上层内部椭圆
        canvas
                .drawOval(mWidth * 0.1f, mHeight * 0.1f, mWidth * 0.9f, mHeight * 0.3f,
                        mInnerOvalPaint);

        //下层椭圆
        drawBottomOvalStroke(canvas);

        drawSwipeProgressRule(canvas);
        drawBottomText(canvas);

        //刻度和刻度数值在最上层，防止被覆盖
        drawMarkLineAndText(canvas);
    }

    private void drawRightBottomOval(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        int layerID = canvas.saveLayer(0, 0, getWidth(), getHeight(), paint, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(createDstBitmap(), 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(createRightSrcBitmap(), 0, 0, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(layerID);
    }

    private void drawLeftBottomOval(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        int layerID = canvas.saveLayer(0, 0, getWidth(), getHeight(), paint, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(createDstBitmap(), 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(createLeftSrcBitmap(), 0, 0, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(layerID);
    }

    private void drawBottomOvalStroke(Canvas canvas) {
        mOvalPath = new Path();
        mOvalPath.moveTo(0 + mOuterOvalPaint.getStrokeWidth(), mHeight * 0.2f);
        mOvalPath.addArc(new RectF(0 + mOuterOvalPaint.getStrokeWidth(), 0,
                mWidth - mOuterOvalPaint.getStrokeWidth(), mHeight * 0.4f), -180, -180);
        mOvalPath.lineTo(mWidth - mOuterOvalPaint.getStrokeWidth(), mHeight * 0.5f);
        mOvalPath.addArc(new RectF(0 + mOuterOvalPaint.getStrokeWidth(), mHeight * 0.3f,
                mWidth - mOuterOvalPaint.getStrokeWidth(), mHeight * 0.7f), 0, 180);
        mOvalPath.lineTo(0 + mOuterOvalPaint.getStrokeWidth(), mHeight * 0.2f);
        canvas.drawPath(mOvalPath, mOuterOvalPaint);
    }

    private void drawSwipeProgressRule(Canvas canvas) {
        float distance = (mHeight * 0.7f - mPositionDistance[1] - mBottomOvalPaint.getStrokeWidth());
        Log.e("TZB", mPositionDistance[0] + ">>>" + mPositionDistance[1]);
        canvas.drawLine(mWidth * mTmpSwipeProgress, mHeight * 0.35f - distance,
                mWidth * mTmpSwipeProgress,
                mHeight * 0.75f - distance, mSwipeProgressRuleLinePaint);
    }

    private void drawBottomText(Canvas canvas) {
        float textWidth1 = mMarkTextPaint.measureText("体重(Kg)") / 2;
        canvas.drawText("体重(Kg)", mWidth / 2 - textWidth1, mHeight * 0.8f + mMarkTextHeight,
                mMarkTextPaint);

        DecimalFormat df = new DecimalFormat("0.##");
        String formatResult = df.format(mTmpWeight);
        float textWidth2 = mMarkTextPaint.measureText(formatResult) / 2;
        canvas.drawText(formatResult, mWidth / 2 - textWidth2, mHeight * 0.8f + 4 * mMarkTextHeight,
                mMarkTextPaint);
    }

    private void drawMarkLineAndText(Canvas canvas) {

        mMarkLinePaint.setColor(Color.parseColor("#FF045B6D"));
        canvas.drawLine(mWidth * 0.1f, mHeight * 0.55f, mWidth * 0.1f, mHeight * 0.6f,
                mMarkLinePaint);

        mMarkLinePaint.setColor(Color.parseColor("#FF096E83"));

        canvas.drawLine(mWidth * 0.3f, mHeight * 0.62f, mWidth * 0.3f, mHeight * 0.67f,
                mMarkLinePaint);

        mMarkLinePaint.setColor(getResources().getColor(R.color.colorFF00D4FF));
        canvas.drawLine(mWidth * 0.5f, mHeight * 0.65f, mWidth * 0.5f, mHeight * 0.69f,
                mMarkLinePaint);

        mMarkLinePaint.setColor(Color.parseColor("#FF0D5A6A"));
        canvas.drawLine(mWidth * 0.7f, mHeight * 0.62f, mWidth * 0.7f, mHeight * 0.67f,
                mMarkLinePaint);

        mMarkLinePaint.setColor(Color.parseColor("#FF064855"));
        canvas.drawLine(mWidth * 0.9f, mHeight * 0.55f, mWidth * 0.9f, mHeight * 0.6f,
                mMarkLinePaint);

        mMarkTextPaint.setColor(Color.parseColor("#FF546D72"));
        float leftTextWidth = mMarkTextPaint.measureText((mCenterMarkValue - 20) + "") / 2;
        canvas.drawText((mCenterMarkValue - 20) + "", mWidth * 0.1f - leftTextWidth,
                mHeight * 0.55f - mMarkTextHeight, mMarkTextPaint);

        mMarkTextPaint.setColor(Color.parseColor("#FFFFFFFF"));
        float leftMiddleTextWidth = mMarkTextPaint.measureText((mCenterMarkValue - 10) + "") / 2;
        canvas.drawText((mCenterMarkValue - 10) + "", mWidth * 0.3f - leftMiddleTextWidth,
                mHeight * 0.62f - mMarkTextHeight, mMarkTextPaint);

        mMarkTextPaint.setColor(Color.parseColor("#FFFFFFFF"));
        float centerTextWidth = mMarkTextPaint.measureText((mCenterMarkValue) + "") / 2;
        canvas.drawText((mCenterMarkValue) + "", mWidth * 0.5f - centerTextWidth,
                mHeight * 0.65f - mMarkTextHeight, mMarkTextPaint);

        mMarkTextPaint.setColor(Color.parseColor("#FFFFFFFF"));
        float rightMiddleTextWidth = mMarkTextPaint.measureText((mCenterMarkValue + 10) + "") / 2;
        canvas.drawText((mCenterMarkValue + 10) + "", mWidth * 0.7f - rightMiddleTextWidth,
                mHeight * 0.62f - mMarkTextHeight, mMarkTextPaint);

        mMarkTextPaint.setColor(Color.parseColor("#FF4A4C4C"));
        float rightTextWidth = mMarkTextPaint.measureText((mCenterMarkValue + 20) + "") / 2;
        canvas.drawText((mCenterMarkValue + 20) + "", mWidth * 0.9f - rightTextWidth,
                mHeight * 0.55f - mMarkTextHeight, mMarkTextPaint);
    }

    private Bitmap createDstBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        mOvalPath = new Path();
        mOvalPath.moveTo(0 + mBottomOvalPaint.getStrokeWidth(), mHeight * 0.2f);
        mOvalPath.addArc(new RectF(0 + mBottomOvalPaint.getStrokeWidth(), 0,
                mWidth - mBottomOvalPaint.getStrokeWidth(), mHeight * 0.4f), -180, -180);
        mOvalPath.lineTo(mWidth - mBottomOvalPaint.getStrokeWidth(), mHeight * 0.5f);
        mOvalPath.addArc(new RectF(0 + mBottomOvalPaint.getStrokeWidth(), mHeight * 0.3f,
                mWidth - mBottomOvalPaint.getStrokeWidth(), mHeight * 0.7f), 0, 180);
        mOvalPath.lineTo(0 + mBottomOvalPaint.getStrokeWidth(), mHeight * 0.2f);
        canvas.drawPath(mOvalPath, mBottomOvalPaint);
        return bitmap;
    }

    private Bitmap createLeftSrcBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        //绘制渐变矩形
        Paint rectPaint = new Paint();
        LinearGradient lg = new LinearGradient(0 + mBottomOvalPaint.getStrokeWidth(), mHeight * 0.2f,
                mWidth * mTmpSwipeProgress, mHeight * 0.2f,
                new int[]{Color.parseColor("#FF001A20"), getResources().getColor(R.color.colorFF00D4FF)},
                null,
                Shader.TileMode.CLAMP);
        rectPaint.setShader(lg);
        canvas.drawRect(0 + mBottomOvalPaint.getStrokeWidth(), mHeight * 0.2f,
                mWidth * mTmpSwipeProgress, mHeight * 0.8f, rectPaint);
        return bitmap;
    }

    private Bitmap createRightSrcBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        //绘制渐变矩形
        Paint rectPaint = new Paint();
        LinearGradient lg = new LinearGradient(mWidth * mTmpSwipeProgress, mHeight * 0.2f,
                mWidth - mBottomOvalPaint.getStrokeWidth(), mHeight * 0.2f,
                new int[]{Color.parseColor("#FF103137"), Color.parseColor("#FF010101")}, null,
                Shader.TileMode.CLAMP);// CLAMP重复最后一个颜色至最后
        rectPaint.setShader(lg);
        canvas.drawRect(mWidth * mTmpSwipeProgress, mHeight * 0.2f,
                mWidth - mBottomOvalPaint.getStrokeWidth(), mHeight * 0.8f, rectPaint);
        return bitmap;
    }

    private float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }

    private void startAnimation() {
        mValueAnimator = ValueAnimator.ofFloat(0f, mSwipeProgress);
        mValueAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mTmpSwipeProgress = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        mWeightAnimator = ValueAnimator.ofFloat(0, mWeight);
        mWeightAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mTmpWeight = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        Log.e("TZB", mWidth + ">>>" + mHeight + ">>>");

        Path animationPath = new Path();
        animationPath.moveTo(0 + mBottomOvalPaint.getStrokeWidth(), mHeight * 0.5f);
        animationPath.addArc(new RectF(0 + mOuterOvalPaint.getStrokeWidth(), mHeight * 0.3f,
                mWidth - mOuterOvalPaint.getStrokeWidth(), mHeight * 0.7f), -180, -180 * mSwipeProgress);

        final PathMeasure pathMeasure = new PathMeasure(animationPath, false);
        Log.e("TZB", pathMeasure.getLength() + "");

        mMarkValueAnimator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
        mMarkValueAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                pathMeasure.getPosTan(value, mPositionDistance, null);
                postInvalidate();
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(5000);
        animatorSet.playTogether(mValueAnimator, mWeightAnimator, mMarkValueAnimator);
        animatorSet.start();
    }
}
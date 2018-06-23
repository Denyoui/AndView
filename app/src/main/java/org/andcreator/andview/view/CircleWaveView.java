package org.andcreator.andview.view;


import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class CircleWaveView extends View {

    private Context mContext;
    private float maxWaveRadius = 300;//扩散半径
    private long waveTime = 2000;//一个涟漪扩散的时间
    private int waveRate = 4;//涟漪的个数
    //...get/set方法略
    private Paint paint;
    private float[] waveList;//涟漪队列
    private int centerX;//控件中心x坐标
    private int centerY;//控件中心y坐标

    public CircleWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        setWillNotDraw(false);
        init(context);
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MessageBox);
//
//        //最后记得将TypedArray对象回收
//        a.recycle();
    }

    private void init(Context context) {
        paint = new Paint();
        waveList = new float[waveRate];
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(waveTime);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                for (int i = 0; i < waveList.length; i++) {
                    float v = value - i * 1.0f / waveRate;
                    if (v < 0 && waveList[i] > 0) {
                        v += 1;
                    }
                    waveList[i] = v;
                }
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Float waveRadius : waveList) {
            paint.setColor(Color.argb((int) (255 * (1 - waveRadius)), 60, 114, 236));//根据进度产生一个argb颜色
            canvas.drawCircle(centerX, centerY, waveRadius * maxWaveRadius, paint);//根据进度计算扩散半径
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerX = getMeasuredWidth() / 2;
        centerY = getMeasuredHeight()/2;
    }
}

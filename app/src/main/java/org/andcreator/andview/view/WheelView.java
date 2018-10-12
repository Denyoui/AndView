package org.andcreator.andview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import org.andcreator.andview.R;

public class WheelView extends View {
    private Paint paint;
    private Bitmap img;
    private int centerX;
    private int centerY;

    private static final int MaxSpeed = 25;

    public WheelView(Context context) {
        this(context, null);
    }

    public WheelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WheelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        //这里就在轮子上绘制几个android小人，有额外需求的话自行扩展
        this.img = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        valueAnimator = ValueAnimator.ofFloat(MaxSpeed, 0);
        valueAnimator = ValueAnimator.ofFloat(MaxSpeed, 0);//MaxSpeed作为轮子转动的最高转速
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animSpeed = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerX = getMeasuredWidth() / 2;
        centerY = getMeasuredHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float x = 0;
        float y = 0;
        angle = angle % 360;
        for (int i = 0; i < 5; i++) {
            int i1 = (int) (angle +360 / 5 * i);//把360度平分为5分，然后用数学函数计算xy坐标，这里的xy是0,0坐标，然后加上view中心点坐标来转换为view坐标
            y = (float) (Math.sin(i1 * Math.PI / 180) * 200);//这里设定半径为200，有额外需求自行扩展
            x = (float) (Math.cos(i1 * Math.PI / 180) * 200);

            x += centerX;
            y += centerY;
            canvas.drawBitmap(img, x - img.getWidth() / 2, y - img.getHeight() / 2, paint);
        }

        angle += animSpeed;
    }

    float touchAnglePre = 0;//记录上次触摸点相对于view中心点的角度
    float touchSpeed = 0;
    float angle = 0;
    float animSpeed = 0;
    ValueAnimator valueAnimator;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                valueAnimator.cancel();
                animSpeed = 0;
                touchSpeed = 0;

                float x = event.getX() - centerX;
                float y = event.getY() - centerY;
                touchAnglePre = (float) (Math.atan(x / y) / 2 / Math.PI * 360);
                break;
            case MotionEvent.ACTION_MOVE:
                float x1 = event.getX() - centerX;
                float y1 = event.getY() - centerY;
                float touchAngle = (float) (Math.atan(x1 / y1) / 2 / Math.PI * 360);
                if (touchAnglePre / Math.abs(touchAnglePre) == touchAngle / Math.abs(touchAngle)) {
                    touchSpeed = (touchAnglePre - touchAngle) * 2;//角度差，这里为了使滑动更灵敏，所以乘以2，有额外需求自行扩展
                }//因为使用的数学函数只能用于计算锐角，这里忽略处于正负0度和正负90度左右的事件
                angle += touchSpeed;
                touchAnglePre = touchAngle;
                invalidate();
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                //这里需要在触摸释放的时候让我们的轮子按照惯性继续滚动至停止
                valueAnimator.setFloatValues(Math.abs(touchSpeed) > MaxSpeed ? MaxSpeed * (touchSpeed / Math.abs(touchSpeed)) : touchSpeed, 0);
                valueAnimator.start();
                break;
            default:

        }
        return true;
    }
}

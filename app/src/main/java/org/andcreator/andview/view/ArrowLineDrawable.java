package org.andcreator.andview.view;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.FloatRange;

/**
 * Created by liuj on 2016/12/15.
 * 一条线的变形箭头,思路参考Pixel启动器
 */
public class ArrowLineDrawable extends Drawable implements ValueAnimator.AnimatorUpdateListener {
    private Paint paint;
    private Rect bounds;
    private float progress = 0;
    private float borderPro = 0.1f;
    private int borderWidth = 0;
    private Path path;
    private ValueAnimator valueAnimator;
    private long animatorDuration = 300;


    public ArrowLineDrawable() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setStrokeCap(Paint.Cap.BUTT);
        valueAnimator = new ValueAnimator();
        valueAnimator.addUpdateListener(this);
    }

    @Override
    public void draw(Canvas canvas) {
        if(path!=null){
            canvas.drawPath(path,paint);
        }
    }

    @Override
    public void setAlpha(int i) {
        paint.setAlpha(i);
        invalidateSelf();
    }

    private void offsetChange(){
        if(bounds!=null){
            if(path==null)
                path = new Path();
            path.reset();
            int offset = (int) ((bounds.height()-borderWidth)/2*progress);
            path.moveTo(borderWidth/2,bounds.centerY()-offset);
            path.lineTo(bounds.centerX(),bounds.centerY()+offset);
            path.lineTo(bounds.width()-borderWidth/2,bounds.centerY()-offset);
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        this.bounds = bounds;
        borderWidth = (int) (bounds.width()*borderPro);
        //Y轴中心点即高度的一半
        if(borderWidth>bounds.centerY())
            borderWidth = bounds.centerY();
        this.paint.setStrokeWidth(borderWidth);
        offsetChange();
    }

    public void setColor(int color) {
        paint.setColor(color);
        invalidateSelf();
    }

    public void setProgress(@FloatRange(from = -1,to = 1) float progress) {
        this.progress = progress;
        offsetChange();
        invalidateSelf();
    }

    public void upTo(){
        valueAnimator.cancel();
        valueAnimator.setDuration(animatorDuration);
        valueAnimator.setFloatValues(progress,-1);
        valueAnimator.start();
    }

    public void centreTo(){
        valueAnimator.cancel();
        valueAnimator.setDuration(animatorDuration/2);
        valueAnimator.setFloatValues(progress,0);
        valueAnimator.start();
    }

    public void downTo(){
        valueAnimator.cancel();
        valueAnimator.setDuration(animatorDuration);
        valueAnimator.setFloatValues(progress,1);
        valueAnimator.start();
    }

    public void setBorderPro(float borderPro) {
        this.borderPro = borderPro;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        setProgress((float) animation.getAnimatedValue());
    }
}

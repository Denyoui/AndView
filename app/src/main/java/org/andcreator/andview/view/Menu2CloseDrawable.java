package org.andcreator.andview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;

/**
 * 菜单按钮变为叉的Drawable
 * @author lollipop
 */
public class Menu2CloseDrawable extends Drawable {

    /**
     * 画笔
     */
    private Paint paint = new Paint();

    /**
     * 指定的宽度，如果没有，那么获取整个画板宽度
     */
    private float width = -1;
    /**
     * 指定高度，如果没有，那么获取整个画板高度
     */
    private float height = -1;

    /**
     * 线条宽度，如果没有，那么获取整体高度的1/5
     */
    private float barThickness = -1;

    /**
     * 线条在menu模式时的间隔高度
     * 默认为(height - barThickness * 3) / 2
     */
    private float barGap = -1;

    /**
     * 菜单模式的横向缩进
     * 第一条线
     */
    private float menuHorizontalPadding1 = 0;

    /**
     * 菜单模式的横向缩进
     * 第二条线
     */
    private float menuHorizontalPadding2 = 0;

    /**
     * 菜单模式的横向缩进
     * 第三条线
     */
    private float menuHorizontalPadding3 = 0;

    /**
     * 绘制的边框
     */
    private Rect drawBounds = new Rect();

    /**
     * 绘制的线条宽度
     */
    private float strokeWidth = 0;

    /**
     * 绘制的间隔高度
     */
    private float gapHeight = 0;

    /**
     * 绘制的路径
     */
    private Path drawPath = new Path();

    /**
     * 绘制的进度位置
     */
    private float drawPosition = 0F;

    /**
     * 是否同时旋转
     */
    private boolean isRotate = true;

    /**
     * 总的旋转角度
     */
    private int rotateAngle = 180;

    public Menu2CloseDrawable(){
        //设置边缘光滑
        paint.setAntiAlias(true);
        //设置抖动，用来做边缘光滑
        paint.setDither(true);
        //设置画笔模式为描边
        paint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {

        if(isRotate){
            //保存坐标系状态
            canvas.save();
            //以绘制范围的中心为圆心旋转指定角度
            canvas.rotate(drawPosition * rotateAngle,drawBounds.centerX(),drawBounds.centerY());
            //绘制图案
            canvas.drawPath(drawPath,paint);
            //还原坐标系状态
            canvas.restore();
        }else{
            canvas.drawPath(drawPath,paint);
        }

    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        //如果设置的高度为有效值，那么使用有效值并且居中
        if(width > 0){
            drawBounds.left = (int) (bounds.centerX() - (width / 2));
            drawBounds.right = (int) (drawBounds.left + width);
        }else{
            //否则使用容器尺寸
            drawBounds.left = bounds.left;
            drawBounds.right = bounds.right;
        }

        //同上
        if (height > 0){
            drawBounds.top = (int) (bounds.centerY() - (height / 2));
            drawBounds.bottom = (int) (drawBounds.top + height);
        }else{
            drawBounds.top = bounds.top;
            drawBounds.bottom = bounds.bottom;
        }

        //如果设置的线条宽度有效，那么使用设置的线条宽度，否则使用默认高度
        if(barThickness > 0){
            strokeWidth = barThickness;
        }else{
            strokeWidth = drawBounds.height() / 5;
        }

        //如果设置的线条间隔有效，那么使用线条间隔，否则使用默认值
        if(barGap > 0){
            gapHeight = barGap;
        }else{
            gapHeight = (drawBounds.height() - (strokeWidth * 3)) / 2;
        }

        paint.setStrokeWidth(strokeWidth);

        onPositionChange();
    }

    /**
     * 设置需要的绘制大小
     * @param w 宽度的像素值
     * @param h 高度的像素值
     * @return 当前对象
     */
    public Menu2CloseDrawable setSize(float w,float h){
        width = w;
        height = h;
        //重新设置尺寸后，需要重新计算并且排版，重绘
        onBoundsChange(getBounds());
        return this;
    }

    /**
     * 设置绘制尺寸的DP值
     * @param context 上下文
     * @param w 宽度的DP值
     * @param h 高度的DP值
     * @return 当前对象
     */
    public Menu2CloseDrawable setSizeDP(Context context,float w,float h){
        return setSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,w,context.getResources().getDisplayMetrics()),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,h,context.getResources().getDisplayMetrics()));
    }

    /**
     * 设置一个正方形的Drawable
     * @param size 正方形边长，px
     * @return 当前对象
     */
    public Menu2CloseDrawable setSize(float size){
        return setSize(size,size);
    }

    /**
     * 使用DP值设置正方形的Drawable
     * @param context 上下文
     * @param size 正方形边长，DP
     * @return 当前对象
     */
    public Menu2CloseDrawable setSizeDP(Context context,float size){
        return setSizeDP(context,size,size);
    }

    /**
     * 设置图案的颜色
     * @param color 颜色值
     * @return 当前对象
     */
    public Menu2CloseDrawable setColor(int color){
        paint.setColor(color);
        invalidateSelf();
        return this;
    }

    /**
     * 进度变化时，更新图案并且发起重绘
     * @param p 进度值，区间为0-1
     * @return 当前对象
     */
    public Menu2CloseDrawable onPositionChange(@FloatRange(from = 0.0,to = 1.0) float p){
        if(p == drawPosition){
            //如果进度一样，那么不再计算图案，直接重绘
            invalidateSelf();
            return this;
        }
        drawPosition = p;
        onPositionChange();
        return this;
    }

    /**
     * 设置图案的路径形状
     */
    private void onPositionChange(){

        //重置路径，可以理解为清空图案
        drawPath.reset();

        //角落的缩进，用于close模式
        float cornerOffset = (strokeWidth / 2) * drawPosition;

        /*-----------第一条线-----------*/
        //计算左上角开始的X坐标
        float topLeftStartX = drawBounds.left + menuHorizontalPadding1;
        //计算右上角开始的X坐标
        float topRightStartX = drawBounds.right - menuHorizontalPadding1;
        //计算第一条线开始的Y坐标
        float topStartY = drawBounds.centerY() - strokeWidth - gapHeight;

        //close模式的坐标固定为画板角落
        float topLeftEndX = drawBounds.left + cornerOffset;
        float topRightEndX = drawBounds.right - cornerOffset;
        float topLeftEndY = drawBounds.bottom - cornerOffset;
        float topRightEndY = drawBounds.top + cornerOffset;
        //计算方法： 起点与终点的距离 * 进度 + 起点坐标
        drawPath.moveTo(((topLeftEndX - topLeftStartX) * drawPosition) + topLeftStartX,
                ((topLeftEndY - topStartY) * drawPosition) + topStartY);
        drawPath.lineTo(((topRightEndX - topRightStartX) * drawPosition) + topRightStartX,
                ((topRightEndY - topStartY) * drawPosition) + topStartY);

        /*-----------第二条线-----------*/
        //中间线条的X轴起点以及终点
        float leftCenterX = drawBounds.left + menuHorizontalPadding2;
        float rightCenterX = drawBounds.right - menuHorizontalPadding2;
        //高度不变，因此直接取中点
        float centerY = drawBounds.centerY();
        //因为两侧同时收缩，因此两侧总的收缩长度为宽度一半
        float centerXOffset = ((drawBounds.width() / 2) - menuHorizontalPadding2) * drawPosition;
        //设置第二条线，同时为X轴加上偏移量
        drawPath.moveTo(leftCenterX + centerXOffset,centerY);
        drawPath.lineTo(rightCenterX - centerXOffset,centerY);

        /*-----------第三条线-----------*/
        //计算左上角开始的X坐标
        float bottomLeftStartX = drawBounds.left + menuHorizontalPadding3;
        //计算右上角开始的X坐标
        float bottomRightStartX = drawBounds.right - menuHorizontalPadding3;
        //计算第一条线开始的Y坐标
        float bottomStartY = drawBounds.centerY() + strokeWidth + gapHeight;

        //close模式的坐标固定为画板角落
        float bottomLeftEndX = drawBounds.left + cornerOffset;
        float bottomRightEndX = drawBounds.right - cornerOffset;
        float bottomLeftEndY = drawBounds.top + cornerOffset;
        float bottomRightEndY = drawBounds.bottom - cornerOffset;

        //计算方法： 起点与终点的距离 * 进度 + 起点坐标
        drawPath.moveTo(((bottomLeftEndX - bottomLeftStartX) * drawPosition) + bottomLeftStartX,
                ((bottomLeftEndY - bottomStartY) * drawPosition) + bottomStartY);
        drawPath.lineTo(((bottomRightEndX - bottomRightStartX) * drawPosition) + bottomRightStartX,
                ((bottomRightEndY - bottomStartY) * drawPosition) + bottomStartY);

        //形状确定后发起重绘
        invalidateSelf();
    }

    /**
     * 设置是否旋转，默认为true
     * @param r 是否旋转
     * @return 返回当前对象
     */
    public Menu2CloseDrawable setRotate(boolean r){
        isRotate = r;
        //记录状态后重绘刷新
        invalidateSelf();
        return this;
    }

    /**
     * 设置旋转的总得角度，默认为180
     * @param angle 角度值
     * @return 当前对象
     */
    public Menu2CloseDrawable setRotateAngle(int angle){
        rotateAngle = angle;
        //记录状态后重绘刷新
        invalidateSelf();
        return this;
    }

    /**
     * 设置线条宽度，默认为1/5
     * @param size 宽度像素值
     * @return 当前对象
     */
    public Menu2CloseDrawable setBarThickness(float size){
        barThickness = size;
        //记录后，需要重新计算并且排版，发起重绘
        onBoundsChange(getBounds());
        return this;
    }

    /**
     * 设置线条宽度，dp值
     * @param context 上下文
     * @param size 线条宽度，dp值
     * @return 当前对象
     */
    public Menu2CloseDrawable setBarThicknessDP(Context context,float size){
        return setBarThickness(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,size,context.getResources().getDisplayMetrics()));
    }

    /**
     * 设置间隔高度
     * @param gap 间隔的高度，px
     * @return 当前对象
     */
    public Menu2CloseDrawable setBarGap(float gap) {
        barGap = gap;
        //记录后，需要重新计算并且排版，发起重绘
        onBoundsChange(getBounds());
        return this;
    }

    /**
     * 设置间隔高度的DP值
     * @param context 上下文
     * @param gap 间隔的高度 dp
     * @return 当前对象
     */
    public Menu2CloseDrawable setBarGapDP(Context context,float gap) {
        return setBarGap(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,gap,context.getResources().getDisplayMetrics()));
    }

    /**
     * 设置目录模式的左右缩进
     * @param padding 缩进的长度，px
     * @return 当前对象
     */
    public Menu2CloseDrawable setMenuHorizontalPadding(float padding){
        return setMenuHorizontalPadding(padding,padding,padding);
    }

    /**
     * 分别设置三条线的横向缩进
     * @param padding1 第一条线
     * @param padding2 第二条线
     * @param padding3 第三条线
     * @return 当前对象
     */
    public Menu2CloseDrawable setMenuHorizontalPadding(float padding1,float padding2,float padding3){
        menuHorizontalPadding1 = padding1;
        menuHorizontalPadding2 = padding2;
        menuHorizontalPadding3 = padding3;
        //记录后，需要重新计算并且排版，发起重绘
        onBoundsChange(getBounds());
        return this;
    }

    /**
     * 分别设置三条线的横向缩进的DP值
     * @param context 上下文
     * @param padding1 第一条线
     * @param padding2 第二条线
     * @param padding3 第三条线
     * @return 当前对象
     */
    public Menu2CloseDrawable setMenuHorizontalPaddingDP(Context context,float padding1,float padding2,float padding3){
        return setMenuHorizontalPadding(
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,padding1,context.getResources().getDisplayMetrics()),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,padding2,context.getResources().getDisplayMetrics()),
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,padding3,context.getResources().getDisplayMetrics())
        );
    }

    /**
     * 设置目录模式的左右缩进
     * @param context 上下文
     * @param padding 缩进的长度，DP
     * @return 当前对象
     */
    public Menu2CloseDrawable setMenuHorizontalPaddingDP(Context context,float padding){
        return setMenuHorizontalPaddingDP(context,padding,padding,padding);
    }

    @Override
    public void setAlpha(int i) {
        paint.setAlpha(i);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }
}

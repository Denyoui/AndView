package org.andcreator.andview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import org.andcreator.andview.R;


public class Rounded extends ViewGroup {

    private int mColor;
    private boolean mCenter;
    private String mDirection;

    private int widthMargin;
    private int heightMargin;
    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBottom;

    public int mStartColor; //渐变开始颜色
    public int mEndColor; //渐变结束颜色

    public void setColor(int color){
        mColor = color;
        this.invalidate();
    }

    public Rounded(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setWillNotDraw(false);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Rounded);

        //第一个参数为属性集合里面的属性，R文件名称：R.styleable+属性集合名称+下划线+属性名称
        //第二个参数为，如果没有设置这个属性，则设置的默认的值
        mColor = a.getColor(R.styleable.Rounded_roundedColor, Color.GREEN);
        mCenter = a.getBoolean(R.styleable.Rounded_layoutCenter,true);
        mStartColor = a.getColor(R.styleable.Rounded_roundedColor, Color.GREEN);
        mEndColor = a.getColor(R.styleable.Rounded_roundedColor, Color.GREEN);

        //最后记得将TypedArray对象回收
        a.recycle();
    }
    /***
     * 获取子View中宽度最大的值
     */
    private int getMaxChildWidth() {
        int childCount = getChildCount();
        int maxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            LayoutParams params = (LayoutParams) childView.getLayoutParams();
            if (childView.getMeasuredWidth() > maxWidth)
                //子View需要的宽度 为 子View 本身宽度+marginLeft + marginRight
                widthMargin = params.leftMargin + params.rightMargin;
            maxWidth = childView.getMeasuredWidth();

        }

        return maxWidth;
    }

    /***
     * 将所有子View的高度相加
     **/
    private int getTotleHeight() {
        int childCount = getChildCount();
        int height = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            LayoutParams params = (LayoutParams) childView.getLayoutParams();
            height +=  childView.getMeasuredHeight() + params.topMargin + params.bottomMargin;
            heightMargin +=  childView.getMeasuredHeight() + params.topMargin + params.bottomMargin;

        }

        return height;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //将所有的子View进行测量，这会触发每个子View的onMeasure函数
        //注意要与measureChild区分，measureChild是对单个view进行测量
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        paddingRight = getPaddingRight();
        paddingBottom = getPaddingBottom();

        int childCount = getChildCount();

        int height = heightSize;
        int width = getMaxChildWidth()+20;
        setMeasuredDimension(width+paddingLeft+paddingRight+20, height+paddingBottom+paddingTop);

//        if (childCount == 0) {//如果没有子View,当前ViewGroup没有存在的意义，不用占用空间
//            setMeasuredDimension(0, 0);
//        } else {
//            //如果宽高都是包裹内容
//            if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
//                //我们将高度设置为所有子View的高度相加，宽度设为子View中最大的宽度
//                int height = getTotleHeight();
//                int width = getMaxChildWidth();
//                setMeasuredDimension(width+paddingLeft+paddingRight+100, height+paddingBottom+paddingTop+200);
//
//            } else if (heightMode == MeasureSpec.AT_MOST) {//如果只有高度是包裹内容
//                //宽度设置为ViewGroup自己的测量宽度，高度设置为所有子View的高度总和
//                setMeasuredDimension(widthSize, getTotleHeight());
//            } else if (widthMode == MeasureSpec.AT_MOST) {//如果只有宽度是包裹内容
//                //宽度设置为子View中宽度最大的值，高度设置为ViewGroup自己的测量值
//                setMeasuredDimension(getMaxChildWidth(), heightSize);
//
//            }
//        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
//        paint.setColor(mColor);
        LinearGradient linearGradient = new LinearGradient(0, 0, getMeasuredWidth(), 0,new int[]{mStartColor,mEndColor}, null, LinearGradient.TileMode.CLAMP);
        paint.setShader(linearGradient);
//            canvas.drawColor(Color.GREEN);
        RectF r2=new RectF();                           //RectF对象
        r2.left=0;                                 //左边
        r2.top=0;                                 //上边
        r2.right=getMeasuredWidth();                                   //右边
        r2.bottom=getMeasuredHeight();                              //下边
        canvas.drawRoundRect(r2, 80, 80, paint);        //绘制圆角矩形

//            canvas.translate(20, 20);
        canvas.save();

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        //记录当前的高度位置
        int curHeight = t;

        int childMeasureWidth = 0;
        int childMeasureHeight = 0;
//            MessageBox params = null;
        for ( int i = 0; i < count; i++) {
            View child = getChildAt(i);
            // 注意此处不能使用getWidth和getHeight，这两个方法必须在onLayout执行完，才能正确获取宽高
            childMeasureWidth = child.getMeasuredWidth();
            childMeasureHeight = child.getMeasuredHeight();

//                    params = (MessageBox) child.getLayoutParams();
            l = (getWidth() - childMeasureWidth) / 2;
            t = (getHeight() - childMeasureHeight) / 2;

            int childCount = getChildCount();
            int maxWidth = 0;
            for (int f = 0; f < childCount; f++) {
                View childView = getChildAt(f);
                LayoutParams params = (LayoutParams) childView.getLayoutParams();
                if (childView.getMeasuredWidth() > maxWidth)
                    //子View需要的宽度 为 子View 本身宽度+marginLeft + marginRight
                    widthMargin = params.leftMargin + params.rightMargin;

            }

            // 确定子控件的位置，四个参数分别代表（左上右下）点的坐标值
            child.layout(l+widthMargin/2, t, l + childMeasureWidth-widthMargin/2, t + childMeasureHeight);
        }

    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }


    // 继承自margin，支持子视图android:layout_margin属性
    public static class LayoutParams extends MarginLayoutParams {


        private LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }


        private LayoutParams(int width, int height) {
            super(width, height);
        }


        private LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }


        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }
    }

    public void setColor(int startColor,int endColor){
        mStartColor = startColor;
        mEndColor = endColor;
        this.invalidate();
    }
}

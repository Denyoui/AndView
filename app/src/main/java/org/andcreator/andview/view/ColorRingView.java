package org.andcreator.andview.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class ColorRingView extends View {

    private Context mContext;
    private float ringWidth = 72;
    private float width;
    private float height;

    Paint paintRing = new Paint();//环形笔刷
    Paint paintEnds = new Paint();//终点笔刷
    int[] ringColors = new int[]{
            Color.RED,
            Color.parseColor("#00FF0000")
    };

    public ColorRingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
//        setWillNotDraw(false);
        init();
    }
    private void init() {
        paintRing.setColor(Color.RED);
        paintRing.setStrokeWidth(ringWidth);
        paintRing.setStyle(Paint.Style.STROKE);
        paintRing.setColor(Color.WHITE);
        paintRing.setAntiAlias(true);
        paintEnds.setAntiAlias(true);
        paintEnds.setStyle(Paint.Style.FILL);
        paintEnds.setColor(Color.RED);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        int offsetX = (int) (Math.abs(width - height) / 2);
        int offsetY = (int) (Math.abs(height - width) / 2);
        int left = (int) ((width > height ? offsetX : 0) + ringWidth / 2);
        int top = (int) ((height > width ? offsetY : 0) + ringWidth / 2);
        int right = (int) (width - (width > height ? offsetX : 0) - ringWidth / 2);
        int bottom = (int) (height - (height > width ? offsetY : 0) - ringWidth / 2);
        RectF rectF = new RectF(left, top, right, bottom);

        paintRing.setShader(new SweepGradient(width / 2, height / 2, ringColors, null));

        canvas.drawArc(rectF, 0, 360, false, paintRing);
        canvas.drawCircle(width - ringWidth / 2, 1f * height / 2, ringWidth / 2, paintEnds);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }
}

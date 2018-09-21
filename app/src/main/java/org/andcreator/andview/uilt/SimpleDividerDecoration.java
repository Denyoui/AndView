package org.andcreator.andview.uilt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.andcreator.andview.R;

public class SimpleDividerDecoration extends RecyclerView.ItemDecoration {
    private int dividerHeight;
    private Paint dividerPaint;

    public SimpleDividerDecoration(Context context) {
        dividerPaint = new Paint();
        dividerPaint.setColor(context.getResources().getColor(R.color.transparent));
        dividerHeight = context.getResources().getDimensionPixelSize(R.dimen.recycler_paddingTop);
    }

    private int isF = 1;

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            float top = view.getTop();
            float bottom = view.getTop() + dividerHeight;
            if (i == 0) {
                if (isF == 1) {
                    view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + 10,
                            view.getPaddingRight(), view.getPaddingBottom());
                    isF = 0;
                }
                c.drawRect(left, top, right, bottom + 10, dividerPaint);
            } else
                c.drawRect(left, top, right, bottom, dividerPaint);
        }


    }
}

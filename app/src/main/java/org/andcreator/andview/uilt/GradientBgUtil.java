package org.andcreator.andview.uilt;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * ViewPager的渐变背景色工具类
 * @author lollipop
 *
 */
public class GradientBgUtil implements ViewPager.OnPageChangeListener {

    private ColorCallback callback;

    private OnColorChangeListener onColorChangeListener;

    public GradientBgUtil(ColorCallback callback, OnColorChangeListener onColorChangeListener) {

        this.callback = callback;
        this.onColorChangeListener = onColorChangeListener;

    }

    public GradientBgUtil(ColorCallback callback, View bgView) {
        this(callback,new SimpleColorChangeListener(bgView));
    }

    public GradientBgUtil(int[] colors, OnColorChangeListener onColorChangeListener) {
        this(new SimpleColorCallback(colors),onColorChangeListener);
    }

    public GradientBgUtil(int[] colors, View bgView) {
        this(new SimpleColorCallback(colors),new SimpleColorChangeListener(bgView));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        int previousColor = callback.getColor(position);

        if(positionOffset != 0){
            int nextColor = callback.getColor(position + 1);
            onColorChangeListener.onColorChange(getColor(previousColor,nextColor,positionOffset));
        }else{
            onColorChangeListener.onColorChange(previousColor);
        }

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private int getColor(int previousColor,int nextColor, float positionOffset){

        int a = (int)(( ( Color.alpha(nextColor) - Color.alpha(previousColor) ) * positionOffset ) + Color.alpha(previousColor));
        int r = (int)(( ( Color.red(nextColor) - Color.red(previousColor) ) * positionOffset ) + Color.red(previousColor));
        int g = (int)(( ( Color.green(nextColor) - Color.green(previousColor) ) * positionOffset ) + Color.green(previousColor));
        int b = (int)(( ( Color.blue(nextColor) - Color.blue(previousColor) ) * positionOffset ) + Color.blue(previousColor));

        return Color.argb(a,r,g,b);
    }

    private static class SimpleColorCallback implements ColorCallback{

        private int[] colors;

        private SimpleColorCallback(int[] colors) {
            this.colors = colors;
        }

        @Override
        public int getColor(int position) {
            return colors[position];
        }
    }

    private static class SimpleColorChangeListener implements OnColorChangeListener{

        private View bgView;

        public SimpleColorChangeListener(View bgView) {
            this.bgView = bgView;
        }

        @Override
        public void onColorChange(int color) {
            bgView.setBackgroundColor(color);
        }
    }

    /**
     * 获取颜色的回调函数
     */
    public interface ColorCallback{

        /**
         * 传出当前页面的最终颜色
         * @param position
         * @return
         */
        public int getColor(int position);

    }

    public interface OnColorChangeListener{

        public void onColorChange(int color);

    }

}

package org.andcreator.andview.uilt;


import android.graphics.Color;

public class Gradient {

    /**
     *
     * @param startColor
     * @param endColor
     * @param size
     * @param index
     * @return
     */
    public static int getGradient(int startColor,int endColor,int size,int index){

        int sR = Color.red(startColor);
        int sG = Color.green(startColor);
        int sB = Color.blue(startColor);

        float r = (Color.red(endColor) -sR) * 1.0F / size;
        float g = (Color.green(endColor) -sG) * 1.0F / size;
        float b = (Color.blue(endColor) -sB) * 1.0F / size;
        return Color.rgb((int)(sR + r * index),(int)(sG + g * index),(int)(sB + b *index));
    }
}

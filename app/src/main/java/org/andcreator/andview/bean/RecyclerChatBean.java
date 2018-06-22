package org.andcreator.andview.bean;

/**
 * Created by Andrew on 2018/4/7.
 */

public class RecyclerChatBean {

    private int viewType;
    private int icon;
    private String text;
    private float textSize;

    public RecyclerChatBean(int viewType, int icon, String text, float textSize){
        this.viewType = viewType;
        this.icon = icon;
        this.text = text;
        this.textSize = textSize;
    }

    public int getIcon() {
        return icon;
    }

    public String getText() {
        return text;
    }

    public int getViewType() {
        return viewType;
    }

    public float getTextSize() {
        return textSize;
    }
}

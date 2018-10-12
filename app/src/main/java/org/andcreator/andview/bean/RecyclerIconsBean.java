package org.andcreator.andview.bean;

public class RecyclerIconsBean {

    private String name;
    private int icon;

    public RecyclerIconsBean(String name,int icon){

        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }
}

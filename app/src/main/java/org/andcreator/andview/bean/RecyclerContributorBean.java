package org.andcreator.andview.bean;

public class RecyclerContributorBean {

    private int icon;
    private String name;
    private String motto;
    private int color;

    public RecyclerContributorBean(int icon,String name,String motto,int color){

        this.icon = icon;
        this.name = name;
        this.motto = motto;
        this.color = color;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getMotto() {
        return motto;
    }

    public int getColor() {
        return color;
    }
}

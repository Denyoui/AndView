package org.andcreator.andview.bean;

public class RecyclerAppsBean {

    private int iconId;
    private String appName;
    private String uri;

    public RecyclerAppsBean(int iconId, String appName,String uri){

        this.iconId = iconId;
        this.appName = appName;
        this.uri = uri;
    }

    public int getIconId() {
        return iconId;
    }

    public String getAppName() {
        return appName;
    }

    public String getUri() {
        return uri;
    }
}

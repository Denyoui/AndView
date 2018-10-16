package org.andcreator.andview.bean;

public class RecyclerOpenAppsBean {

    private int icon;
    private String name;
    private String introduction;
    private String website;
    private String download;

    public RecyclerOpenAppsBean(int icon,String name,String introduction,String website,String download){

        this.icon = icon;
        this.name = name;
        this.introduction = introduction;
        this.website = website;
        this.download = download;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getWebsite() {
        return website;
    }

    public String getDownload() {
        return download;
    }
}

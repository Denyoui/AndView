package org.andcreator.andview.bean;

public class RecyclerCommentBean {

    private String iconUrl;
    private String title;
    private String text;

    public RecyclerCommentBean(String iconUrl, String title, String text){

        this.iconUrl = iconUrl;
        this.title = title;
        this.text = text;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}

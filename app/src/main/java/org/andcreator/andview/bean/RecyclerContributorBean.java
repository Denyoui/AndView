package org.andcreator.andview.bean;

public class RecyclerContributorBean {

    private int icon;
    private String name;
    private String motto;
    private int color;
    private String blog;
    private String github;
    private String mail;

    public RecyclerContributorBean(int icon,String name,String motto,int color,String blog,String github,String mail){

        this.icon = icon;
        this.name = name;
        this.motto = motto;
        this.color = color;
        this.blog = blog;
        this.github = github;
        this.mail = mail;
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

    public String getBlog() {
        return blog;
    }

    public String getGithub() {
        return github;
    }

    public String getMail() {
        return mail;
    }
}

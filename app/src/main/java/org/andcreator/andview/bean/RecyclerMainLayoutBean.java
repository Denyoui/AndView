package org.andcreator.andview.bean;

import java.util.List;

public class RecyclerMainLayoutBean {

    private String title;
    private String description;
    private int[] contributorList;

    public RecyclerMainLayoutBean(String title,String description,int[] contributorList){
        this.title = title;
        this.description = description;
        this.contributorList = contributorList;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int[] getContributorList() {
        return contributorList;
    }

}

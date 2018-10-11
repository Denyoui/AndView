package org.andcreator.andview.bean;

import java.util.List;

public class RecyclerMainLayoutBean {

    private String title;
    private String description;
    private int[] contributorList;
    private int index;

    public RecyclerMainLayoutBean(String title,String description,int[] contributorList,int index){
        this.title = title;
        this.description = description;
        this.contributorList = contributorList;
        this.index = index;
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

    public int getIndex() {
        return index;
    }
}

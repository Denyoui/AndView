package org.andcreator.andview.bean;

import java.util.List;

public class RecyclerCircleBean {

    private int viewType;
    private String objId; //数据Id
    private String userImageUrl; //用户头像
    private String userName; //用户名称
    private String userTopic; //话题
    private String userMessage; //信息内容
    private String time; //发送时间
    private List<RecyclerImageBean> imageUrl; //图片内容
    private String commentOne; //第一条评论
    private int chatNum; //评论数量
    private int plusNum; //+1人数
    private int shareNum; //分享数量
    private boolean plusOne; //是否已经+1


    public RecyclerCircleBean(int viewType, String objId, String userImageUrl, String userName, String userTopic, String userMessage, String time, List<RecyclerImageBean> imageUrl, String commentOne, int chatNum, int plusNum, int shareNum, boolean plusOne){
        this.viewType = viewType;
        this.objId = objId;
        this.userImageUrl = userImageUrl;
        this.userName = userName;
        this.userTopic = userTopic;
        this.userMessage = userMessage;
        this.time = time;
        this.imageUrl = imageUrl;
        this.commentOne = commentOne;
        this.chatNum = chatNum;
        this.plusNum = plusNum;
        this.shareNum = shareNum;
        this.plusOne = plusOne;
    }

    public String getObjId() {
        return objId;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserTopic() {
        return userTopic;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public String getTime() {
        return time;
    }

    public List<RecyclerImageBean> getImageUrl() {
        return imageUrl;
    }

    public String getCommentOne() {
        return commentOne;
    }

    public int getChatNum() {
        return chatNum;
    }

    public int getPlusNum() {
        return plusNum;
    }

    public int getShareNum() {
        return shareNum;
    }

    public boolean isPlusOne() {
        return plusOne;
    }

    public int getViewType() {
        return viewType;
    }
}

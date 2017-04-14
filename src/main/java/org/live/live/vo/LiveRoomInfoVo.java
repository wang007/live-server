package org.live.live.vo;

import javax.persistence.Column;

/**
 * Created by wang on 2017/4/14.
 */
public class LiveRoomInfoVo {

    /**
     * 分类名
     */
    private String categoryName ;

    /**
     *  房间号
     */
    private String roomNum ;

    /**
     *  房间名
     */
    private String roomName ;

    /**
     *  房间标签。多个用“,”逗号间隔
     */
    private String roomLabel ;

    /**
     *  历史最高在线人数
     */
    private long historyMaxOnlineCount ;

    /**
     *  个性签名
     */
    private String description ;

    /**
     * 账号
     */
    private String account;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private String sex ;

    /**
     * 头像url地址
     */
    private String headImgUrl;

    public LiveRoomInfoVo() {
    }

    public LiveRoomInfoVo(String categoryName, String roomNum, String roomName, String roomLabel, long historyMaxOnlineCount, String description, String account, String nickname, String sex, String headImgUrl) {
        this.categoryName = categoryName;
        this.roomNum = roomNum;
        this.roomName = roomName;
        this.roomLabel = roomLabel;
        this.historyMaxOnlineCount = historyMaxOnlineCount;
        this.description = description;
        this.account = account;
        this.nickname = nickname;
        this.sex = sex;
        this.headImgUrl = headImgUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomLabel() {
        return roomLabel;
    }

    public void setRoomLabel(String roomLabel) {
        this.roomLabel = roomLabel;
    }

    public long getHistoryMaxOnlineCount() {
        return historyMaxOnlineCount;
    }

    public void setHistoryMaxOnlineCount(long historyMaxOnlineCount) {
        this.historyMaxOnlineCount = historyMaxOnlineCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }
}

package org.live.live.vo;

/**
 * Created by wang on 2017/4/13.
 */
public class LiveRoomVo {

    /**
     *  直播间id
     */
    private String liveroomId ;

    /**
     * 直播间号
     */
    private String roomNum ;

    /**
     * 直播间名称
     */
    private String roomName ;

    /**
     * 直播间封面地址
     */
    private String liveRoomCoverUrl ;

    /**
     * 直播地址
     */
    private String liveRoomUrl ;

    /**
     * 主播昵称
     */
    private String nickname ;

    /**
     *  在线人数
     */
    private int onlineCount ;

    /**
     * 正在直播的标记
     */
    private boolean liveFlag ;

    /**
     *  禁播标记， 默认是false
     */
    private boolean banLiveFlag ;

    public LiveRoomVo() {
    }

    public LiveRoomVo(String liveroomId, String roomNum, String roomName, String liveRoomCoverUrl, String liveRoomUrl, String nickname, int onlineCount, boolean liveFlag, boolean banLiveFlag) {
        this.liveroomId = liveroomId;
        this.roomNum = roomNum;
        this.roomName = roomName;
        this.liveRoomCoverUrl = liveRoomCoverUrl;
        this.liveRoomUrl = liveRoomUrl;
        this.nickname = nickname;
        this.onlineCount = onlineCount;
        this.liveFlag = liveFlag;
        this.banLiveFlag = banLiveFlag;
    }

    public String getLiveroomId() {
        return liveroomId;
    }

    public void setLiveroomId(String liveroomId) {
        this.liveroomId = liveroomId;
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

    public String getLiveRoomCoverUrl() {
        return liveRoomCoverUrl;
    }

    public void setLiveRoomCoverUrl(String liveRoomCoverUrl) {
        this.liveRoomCoverUrl = liveRoomCoverUrl;
    }

    public String getLiveRoomUrl() {
        return liveRoomUrl;
    }

    public void setLiveRoomUrl(String liveRoomUrl) {
        this.liveRoomUrl = liveRoomUrl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }

    public boolean isLiveFlag() {
        return liveFlag;
    }

    public void setLiveFlag(boolean liveFlag) {
        this.liveFlag = liveFlag;
    }

    public boolean isBanLiveFlag() {
        return banLiveFlag;
    }

    public void setBanLiveFlag(boolean banLiveFlag) {
        this.banLiveFlag = banLiveFlag;
    }
}

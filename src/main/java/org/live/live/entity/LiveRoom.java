package org.live.live.entity;

import org.live.common.base.BaseEntity;

import javax.persistence.*;

/**
 * 直播房间
 *
 * Created by Mr.wang on 2017/3/28.
 */
@Entity
@Table(name="live_liveroom")
public class LiveRoom extends BaseEntity {





    /**
     *  主播引用
     */
    @OneToOne
    @JoinColumn(name="anchor_id")
    private Anchor anchor ;

    /**
     *  房间分类
     */
    @ManyToOne
    @JoinColumn(name="live_category_id")
    private LiveCategory liveCategory ;

    /**
     *  房间名
     */
    @Column
    private String roomName ;

    /**
     *  房间封面的url
     */
    @Column
    private String coverUrl ;

    /**
     *  正在直播的标记，默认是false
     */
    @Column
    private boolean liveFlag ;

    /**
     *  房间标签。多个用“,”逗号间隔
     */
    @Column
    private String roomLabel ;

    /**
     * 在线人数。当关闭直播的时候，这里要清零
     */
    @Column
    private long onlineCount ;

    /**
     *  历史最高在线人数
     */
    @Column
    private long historyMaxOnlineCount ;

    public Anchor getAnchor() {
        return anchor;
    }

    public void setAnchor(Anchor anchor) {
        this.anchor = anchor;
    }

    public LiveCategory getLiveCategory() {
        return liveCategory;
    }

    public void setLiveCategory(LiveCategory liveCategory) {
        this.liveCategory = liveCategory;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public boolean isLiveFlag() {
        return liveFlag;
    }

    public void setLiveFlag(boolean liveFlag) {
        this.liveFlag = liveFlag;
    }

    public String getRoomLabel() {
        return roomLabel;
    }

    public void setRoomLabel(String roomLabel) {
        this.roomLabel = roomLabel;
    }

    public long getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(long onlineCount) {
        this.onlineCount = onlineCount;
    }

    public long getHistoryMaxOnlineCount() {
        return historyMaxOnlineCount;
    }

    public void setHistoryMaxOnlineCount(long historyMaxOnlineCount) {
        this.historyMaxOnlineCount = historyMaxOnlineCount;
    }
}

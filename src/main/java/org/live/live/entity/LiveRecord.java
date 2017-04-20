package org.live.live.entity;

import org.live.common.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 *  直播记录
 * Created by Mr.wang on 2017/3/28.
 */
@Entity
@Table(name="live_live_record")
public class LiveRecord extends BaseEntity {


    /**
     * 记录号
     */
    @Column
    private String recordNum ;


    /**
     * 直播间
     */
    @ManyToOne
    @JoinColumn(name = "live_room_id")
    private LiveRoom liveRoom ;

    /**
     *  最高在线人数
     */
    @Column
    private int maxOnlineCount ;


    /**
     *  开始时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime ;

    /**
     *  结束时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime ;

    public String getRecordNum() {
        return recordNum;
    }

    public void setRecordNum(String recordNum) {
        this.recordNum = recordNum;
    }

    public int getMaxOnlineCount() {
        return maxOnlineCount;
    }

    public void setMaxOnlineCount(int maxOnlineCount) {
        this.maxOnlineCount = maxOnlineCount;
    }

    public LiveRoom getLiveRoom() {
        return liveRoom;
    }

    public void setLiveRoom(LiveRoom liveRoom) {
        this.liveRoom = liveRoom;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}

package org.live.live.entity;

import org.live.common.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 主播限制用户权限
 * Created by wang on 2017/4/17.
 */
@Entity
@Table(name = "live_anchor_limitation")
public class AnchorLimitation extends BaseEntity {

    /**
     * 限制类型，禁言
     */
    public static final int LIMIT_TYPE_SHUTUP = 1 ;

    /**
     * 限制类型，踢出房间
     */
    public static final int LIMIT_TYPE_KICKOUT = 2 ;

    /**
     *  移动端用户
     */
    @ManyToOne
    @JoinColumn(name = "user_id")
    private MobileUser user ;

    /**
     * 直播间
     */
    @ManyToOne
    @JoinColumn(name = "live_room_id")
    private LiveRoom liveRoom ;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime ;

    /**
     * 限制类型 1.禁言。 2.踢出房间
     */
    private int limitType ;

    public MobileUser getUser() {
        return user;
    }

    public void setUser(MobileUser user) {
        this.user = user;
    }

    public LiveRoom getLiveRoom() {
        return liveRoom;
    }

    public void setLiveRoom(LiveRoom liveRoom) {
        this.liveRoom = liveRoom;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getLimitType() {
        return limitType;
    }

    public void setLimitType(int limitType) {
        this.limitType = limitType;
    }
}

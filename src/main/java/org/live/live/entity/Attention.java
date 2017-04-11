package org.live.live.entity;

import org.live.common.base.BaseEntity;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.Date;

/**
 *  关注表
 *  用户关注主播（房间），这个实体，充当中间实体的作用
 *
 * Created by Mr.wang on 2017/3/28.
 */
public class Attention extends BaseEntity{

    /**
     *  用户
     */
    @OneToOne
    @JoinColumn(name="mobile_user_id")
    private MobileUser user ;

    /**
     *  房间
     */
    @OneToOne
    @JoinColumn(name="live_room_id")
    private LiveRoom liveRoom ;

    /**
     *  创建时间，即关注时间
     */
    private Date createTime ;

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
}

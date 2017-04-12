package org.live.live.service;

import org.live.common.base.BaseService;
import org.live.live.entity.Anchor;
import org.live.live.entity.LiveRoom;
import org.live.live.entity.MobileUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by wang on 2017/4/12.
 */
public interface LiveRoomService extends BaseService<LiveRoom, String> {

    /**
     *  根据主播查询直播间
     * @param anchor
     * @return
     */
    public LiveRoom findLiveRoomByAnchor(Anchor anchor) ;

    /**
     * 根据移动端用户查询主播间
     * @param user
     * @return
     */
    public LiveRoom findLiveRoomByMobileUser(@Param(value="user") MobileUser user) ;

}

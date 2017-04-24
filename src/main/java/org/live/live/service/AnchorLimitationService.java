package org.live.live.service;

import org.live.common.base.BaseService;
import org.live.live.entity.AnchorLimitation;

import java.util.List;

/**
 *
 * Created by wang on 2017/4/23.
 */
public interface AnchorLimitationService extends BaseService<AnchorLimitation, String> {

    /**
     * 查询用户在某直播间的限制
     * @param userId
     * @param liveRoomId
     * @return
     */
    List<Integer> findLimitations(String userId, String liveRoomId) ;

    /**
     * 查询被直播间限制的所有用户
     * @param liveRoomId
     * @return
     */
    List<AnchorLimitation> findLimitationsForLiveRoom(String liveRoomId) ;

}

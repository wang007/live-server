package org.live.live.repository;

import org.live.common.base.BaseRepository;
import org.live.live.entity.AnchorLimitation;

/**
 * Created by wang on 2017/4/17.
 */
public interface AnchorLimitationRepository extends BaseRepository<AnchorLimitation, String> {

    /**
     * 根据账号，直播间号，删除用户在该直播间下的限制
     * @param account
     * @param roomNum
     * @return
     */
    void removeAnchorLimitationByUser_AccountAndLiveRoom_RoomNumAndLimitType(String account, String roomNum, int limitType) ;

}

package org.live.live.repository;

import org.live.common.base.BaseRepository;
import org.live.live.entity.AnchorLimitation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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

    /**
     * 查询用户在某直播间的限制
     * @param userId
     * @param liveRoomId
     * @return
     */
    @Query("select distinct al.limitType from AnchorLimitation al where al.user.id=:userId and al.liveRoom.id=:liveRoomId")
    List<Integer> findAnchorLimitations(@Param("userId") String userId, @Param("liveRoomId") String liveRoomId) ;

}

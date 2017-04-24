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

    /**
     * 查询被直播间限制的所有用户
     * @param liveRoomId
     * @return
     */
    @Query("select distinct new org.live.app.vo.LimitationVo(al.user.id, al.user.account, al.user.nickname, al.liveRoom.id, "
            + "al.liveRoom.roomNum, al.liveRoom.roomName, al.limitType) from AnchorLimitation al where al.liveRoom.id=:liveRoomId "
            + "order by al.createTime DESC")
    List<AnchorLimitation> findLimitationsForLiveRoom(@Param("liveRoomId") String liveRoomId) ;


}

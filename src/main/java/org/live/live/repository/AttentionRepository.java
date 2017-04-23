package org.live.live.repository;

import org.live.app.vo.AppLiveRoomVo;
import org.live.common.base.BaseRepository;
import org.live.live.entity.Attention;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by wang on 2017/4/15.
 */
public interface AttentionRepository extends BaseRepository<Attention, String> {

    /**
     * 根据账号，直播间号，删除用户关注的直播间
     * @param account
     * @param roomNum
     */
    void removeAttentionByUser_AccountAndLiveRoom_RoomNum(String account, String roomNum) ;

    /**
     * 查询用户关注的直播间。
     * 1.直播间是未禁播的。
     * 2.直播间所属的分类是未关闭的。
     * @param userId
     * @return
     */
    @Query("select new org.live.app.vo.AppLiveRoomVo(a.liveRoom.id, a.liveRoom.roomNum, a.liveRoom.coverUrl, a.liveRoom.liveRoomUrl, " +
            "a.liveRoom.roomName, a.liveRoom.anchor.user.nickname, a.liveRoom.anchor.user.headImgUrl, a.liveRoom.onlineCount, a.liveRoom.liveFlag) from Attention a " +
            "where a.liveRoom.banLiveFlag=false and a.liveRoom.liveCategory.enabled=true and a.user.id=:userId order by a.liveRoom.liveFlag desc, a.liveRoom.onlineCount desc")
    List<AppLiveRoomVo> findAttentionLiveRoomsForUser(@Param("userId")String userId) ;

    /**
     * 统计直播间的关注数
     * @param liveRoomId
     * @return
     */
    long countAttentionsByLiveRoom_Id(String liveRoomId) ;

    /**
     * 查看用户与主播的关注
     * @param userId
     * @param liveRoomId
     * @return
     */
    List<Attention> findAttentionsByUser_IdAndLiveRoom_Id(String userId, String liveRoomId) ;

}

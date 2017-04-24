package org.live.live.repository;

import org.live.common.base.BaseRepository;
import org.live.live.entity.Report;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by wang on 2017/4/24.
 */
public interface ReportRepository extends BaseRepository<Report, String> {

    /**
     * 获取用户最近举报某直播间的记录
     * @param userId
     * @param liveRoomId
     * @return
     */
    @Query("select re from Report re where re.mobileUser.id=:userId and re.liveRoom.id=:liveRoomId"
            +" and re.createTime in (select max(re0.createTime) "
            +"from Report re0 where re0.mobileUser.id=:userId and re0.liveRoom.id=:liveRoomId)" )
    Report getRecentlyReport(@Param("userId")String userId, @Param("liveRoomId")String liveRoomId) ;

}

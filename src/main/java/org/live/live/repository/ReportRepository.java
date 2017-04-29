package org.live.live.repository;

import org.live.common.base.BaseRepository;
import org.live.live.entity.Report;
import org.live.live.vo.ReportVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

/**
 * Created by wang on 2017/4/24.
 */
public interface ReportRepository extends BaseRepository<Report, String> {

    /**
     * 获取用户最近举报某直播间的记录
     *
     * @param userId
     * @param liveRoomId
     * @return
     */
    @Query("select re from Report re where re.user.id=:userId and re.liveRoom.id=:liveRoomId"
            + " and re.createTime in (select max(re0.createTime) "
            + "from Report re0 where re0.user.id=:userId and re0.liveRoom.id=:liveRoomId)")
    Report getRecentlyReport(@Param("userId") String userId, @Param("liveRoomId") String liveRoomId);

    /**
     * 获取举报的数据
     *
     * @param pageRequest
     * @param filter      过滤条件
     * @param handleType  获取是否已处理的举报表
     * @return
     */
    Page<ReportVo> findReports(PageRequest pageRequest, Map<String, Object> filter, boolean handleType);

}

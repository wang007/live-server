package org.live.live.repository;

import org.live.common.base.BaseRepository;
import org.live.live.entity.LiveRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Mr.wang on 2017/4/3.
 */
public interface LiveRoomRepository extends BaseRepository<LiveRoom, String> {

    /**
     * 统计直播间的数量，根据直播分类
     * @param liveCategoryId
     * @return
     */
    @Query("select count(*) from LiveRoom r where r.liveCategory.id=:id")
    public long countLiveRoomByLiveCategory(@Param("id") String liveCategoryId) ;

}

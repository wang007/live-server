package org.live.live.repository;

import org.live.common.base.BaseRepository;
import org.live.live.entity.Anchor;
import org.live.live.entity.LiveRoom;
import org.live.live.entity.MobileUser;
import org.live.live.vo.LiveRoomVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    long countLiveRoomByLiveCategory(@Param("id") String liveCategoryId) ;

    /**
     *  根据主播查询直播间
     * @param anchor
     * @return
     */
    LiveRoom findLiveRoomByAnchor(Anchor anchor) ;

    /**
     * 根据移动端用户查询主播间
     * @param user
     * @return
     */
    @Query("select lr from LiveRoom lr, MobileUser mu, Anchor an where lr.anchor=an and lr.anchor.user=mu and lr.anchor.user=:user")
    LiveRoom findLiveRoomByMobileUser(@Param("user") MobileUser user) ;

    /**
     * 分页查询直播间数据
     * @param page
     * @param searchStr 搜索条件
     * @return
     */
    Page<LiveRoomVo> findLiveRooms(PageRequest page, String searchStr) ;

}

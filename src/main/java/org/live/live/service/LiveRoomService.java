package org.live.live.service;

import org.live.common.base.BaseService;
import org.live.live.entity.Anchor;
import org.live.live.entity.LiveRoom;
import org.live.live.entity.MobileUser;
import org.live.live.vo.LiveRoomInfoVo;
import org.live.live.vo.LiveRoomVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public LiveRoom findLiveRoomByMobileUser(MobileUser user) ;

    /**
     * 分页查询直播间数据
     * @param page
     * @param searchStr 搜索条件
     * @return
     */
    Page<LiveRoomVo> findLiveRooms(PageRequest page, String searchStr) ;

    /**
     * 直播间详情
     * @param liveRoomId
     * @return
     */
    LiveRoomInfoVo getLiveRoomInfo(@Param("liveRoomId") String liveRoomId) ;

    /**
     *  查询直播间
     * @param roomNum 主播间号
     * @return
     */
    LiveRoom getLiveRoomByRoomNum(String roomNum) ;
}

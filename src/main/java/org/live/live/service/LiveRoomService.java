package org.live.live.service;

import org.live.app.vo.AppLiveRoomVo;
import org.live.common.base.BaseService;
import org.live.live.entity.Anchor;
import org.live.live.entity.LiveRoom;
import org.live.live.entity.MobileUser;
import org.live.live.vo.LiveRoomInfoVo;
import org.live.live.vo.LiveRoomVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;

import java.util.List;

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

    /**
     * 更改直播间的禁播状态
     * @param liveRoomId
     * @param liveRoomBanFlag
     */
    void changeLiveRoomBanFlag(String liveRoomId, boolean liveRoomBanFlag) ;

    /**
     * 查询未禁播的直播间，给移动端
     * @return
     */
    List<AppLiveRoomVo> findLiveRoomsForApp(String categoryId) ;

    /**
     * 查询用户关注的直播间。
     * 1.直播间是未禁播的。
     * 2.直播间所属的分类是未关闭的。
     * @param userId
     * @return
     */
    List<AppLiveRoomVo> findAttentionLiveRoomsForUser(String userId) ;

    /**
     * 移动端搜索直播间
     * @param searchStr
     * @return
     */
    List<AppLiveRoomVo> findLiveRoomsForAppSearch(String searchStr) ;

    /**
     * 用户举报直播间
     * @param userId
     * @param liveRoomId
     */
    void reportLiveRoom(String userId, String liveRoomId) ;

}

package org.live.live.repository;

import org.live.common.base.BaseRepositoryImpl;
import org.live.live.vo.LiveRoomVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wang on 2017/4/13.
 */
public class LiveRoomRepositoryImpl extends BaseRepositoryImpl {



    private static final String FIND_LIVEROOMS_XHQL = "select new org.live.live.vo.LiveRoomVo(lr.id, lr.roomNum, lr.roomName, lr.coverUrl, lr.liveRoomUrl, " +
            "lr.anchor.user.nickname, lr.onlineCount, lr.liveFlag, lr.banLiveFlag) from LiveRoom lr where ("
            + "/~ or lr.anchor.user.account like '%[account]%'~/"
            + "/~ or lr.anchor.realName like '%[realName]%'~/"
            + "/~ or lr.anchor.user.nickname like '%[nickname]%'~/"
            + "/~ or lr.roomNum like '%[roomNum]%'~/"
            + "/~ or lr.roomName like '%[roomName]%'~/"
            + ") order by lr.banLiveFlag ASC, lr.liveFlag DESC, lr.anchor.createTime ASC";
    /**
     * 分页查询直播间数据
     * @param page
     * @param searchStr 搜索条件
     * @return
     */
    public Page<LiveRoomVo> findLiveRooms(PageRequest page, String searchStr) {

        Map<String, Object> filter = new HashMap<>() ;
        filter.put("account", searchStr) ;
        filter.put("realName", searchStr) ;
        filter.put("nickname", searchStr) ;
        filter.put("roomNum", searchStr) ;
        filter.put("roomName", searchStr) ;
        String hql = this.xsqlConvertHql(FIND_LIVEROOMS_XHQL, filter) ;
        hql = hql.replace("where ()", "").replace("( or", "(") ;
        return this.find(page, hql, null) ;
    }

}

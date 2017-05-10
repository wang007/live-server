package org.live.live.repository;

import org.live.common.base.BaseRepositoryImpl;
import org.live.live.vo.LiveRecordVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 * 直播记录数据库操作实现
 * Created by KAM on 2017/5/10.
 */
public class LiveRecordRepositoryImpl extends BaseRepositoryImpl{
    final String hqlTemplate = "select " +
            "new org.live.live.vo.LiveRecordVo(" +
            "lr.id, " +
            "lr.recordNum, " +
            "lr.liveRoom.roomName, " +
            "lr.liveRoom.roomNum, " +
            "lr.maxOnlineCount, " +
            "lr.startTime, " +
            "lr.endTime) " +
            "from LiveRecord lr";
    final String xsqlBlock = "/~lr.recordNum like '%[recordNum]%'~/"
            + "/~ or lr.liveRoom.roomName like '%[roomName]%'~/"
            + "/~ or lr.liveRoom.roomNum like '%[roomNum]%'~/";

    Page<LiveRecordVo> find(PageRequest pageRequest, Map<String, Object> filter) {
        StringBuilder _hql = new StringBuilder();
        _hql.append(hqlTemplate);
        if (filter.size() > 0) {
            _hql.append(" where ");
            _hql.append(xsqlBlock);
        }
        String hql = super.xsqlConvertHql(_hql.toString(), filter);
        Page<LiveRecordVo> page = super.find(pageRequest, hql, null);
        return page;
    }
}

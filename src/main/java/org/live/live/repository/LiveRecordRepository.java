package org.live.live.repository;

import org.live.common.base.BaseRepository;
import org.live.live.entity.LiveRecord;
import org.live.live.entity.LiveRoom;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by wang on 2017/4/21.
 */
public interface LiveRecordRepository extends BaseRepository<LiveRecord, String> {

    /**
     * 当前主播正在直播的那条记录。
     *
     * @param liveRoom
     * @return
     */
    @Query("select lr from LiveRecord lr where lr.liveRoom=:liveRoom and lr.startTime in " +
            "(select max(lr0.startTime) from LiveRecord lr0 where lr0.liveRoom=:liveRoom)")
    LiveRecord getCurrentLiveRecordByLiveRoom(@Param("liveRoom") LiveRoom liveRoom) ;

    /**
     * 当前主播正在直播的那条记录。
     *
     * @param roomNum
     * @return
     */
    @Query("select lr from LiveRecord lr where lr.liveRoom.roomNum=:roomNum and lr.startTime in " +
            "(select max(lr0.startTime) from LiveRecord lr0 where lr0.liveRoom.roomNum=:roomNum)")
    LiveRecord getCurrentLiveRecordByRoomNum(@Param("roomNum") String roomNum) ;

}

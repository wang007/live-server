package org.live.live.repository;

import org.live.common.base.BaseRepository;
import org.live.live.entity.Attention;

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
}

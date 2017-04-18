package org.live.live.service.impl;

import org.live.common.base.BaseRepository;
import org.live.common.base.BaseServiceImpl;
import org.live.live.entity.Anchor;
import org.live.live.entity.AnchorLimitation;
import org.live.live.entity.LiveRoom;
import org.live.live.entity.MobileUser;
import org.live.live.repository.AnchorLimitationRepository;
import org.live.live.repository.LiveRoomRepository;
import org.live.live.repository.MobileUserRepository;
import org.live.live.service.LiveRoomService;
import org.live.live.vo.LiveRoomInfoVo;
import org.live.live.vo.LiveRoomVo;
import org.live.websocket.chat.OnChatListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 实现websocket中的onChatListener接口
 * Created by wang on 2017/4/12.
 */
@Service("liveRoomService")
public class LiveRoomServiceImpl extends BaseServiceImpl<LiveRoom, String> implements LiveRoomService, OnChatListener {

    @Resource
    private LiveRoomRepository repository ;

    @Resource
    private MobileUserRepository mobileUserRepository ;

    @Resource
    private AnchorLimitationRepository anchorLimitationRepository ;

    @Override
    protected BaseRepository<LiveRoom, String> getRepository() {
        return repository ;
    }

    @Override
    public LiveRoom findLiveRoomByAnchor(Anchor anchor) {
        return repository.findLiveRoomByAnchor(anchor) ;
    }

    @Override
    public LiveRoom findLiveRoomByMobileUser(MobileUser user) {
        return repository.findLiveRoomByMobileUser(user) ;
    }

    @Override
    public Page<LiveRoomVo> findLiveRooms(PageRequest page, String searchStr) {
        return repository.findLiveRooms(page, searchStr) ;
    }

    @Override
    public LiveRoomInfoVo getLiveRoomInfo(String liveRoomId) {
        return repository.getLiveRoomInfo(liveRoomId) ;
    }

    @Override
    public LiveRoom getLiveRoomByRoomNum(String roomNum) {
        return repository.getLiveRoomByRoomNum(roomNum) ;
    }


    /**
     * 主播上线了
     * @param chatRoomNum
     */
    @Override
    public void onAnchorOpenChatRoom(String chatRoomNum) {

        LiveRoom liveRoom = repository.getLiveRoomByRoomNum(chatRoomNum) ;
        liveRoom.setLiveFlag(true)  ;   //设置上线
        repository.save(liveRoom) ;
    }

    /**
     * 主播下线
     * @param chatRoomNum 直播间号
     */
    @Override
    public void onAnchorDissolveChatRoom(String chatRoomNum) {
        LiveRoom liveRoom = repository.getLiveRoomByRoomNum(chatRoomNum) ;
        if(liveRoom != null) {
            liveRoom.setLiveFlag(false) ;   //主播下线
            liveRoom.setOnlineCount(0) ;    //设置在线人数为0
            repository.save(liveRoom) ;
        }
    }

    /**
     * 用户被主播禁言
     * @param chatRoomNum 直播间号
     * @param userAccount 用户账号
     */
    @Override
    public void onShutupUserOnChatRoom(String chatRoomNum, String userAccount) {
        LiveRoom liveRoom = repository.getLiveRoomByRoomNum(chatRoomNum) ;  //主播间
        MobileUser mobileUser = mobileUserRepository.findMobileUserByAccount(userAccount);
        AnchorLimitation limitation = new AnchorLimitation() ;
        limitation.setCreateTime(new Date()) ;
        limitation.setUser(mobileUser) ;
        limitation.setLiveRoom(liveRoom);
        limitation.setLimitType(AnchorLimitation.LIMIT_TYPE_SHUTUP) ;
        anchorLimitationRepository.save(limitation) ;
    }

    /**
     * 用户被解除禁言
     * @param chatRoomNum
     * @param userAccount
     */
    @Override
    public void onRelieveShutupUserOnChatRoom(String chatRoomNum, String userAccount) {

        anchorLimitationRepository
                .removeAnchorLimitationByUser_AccountAndLiveRoom_RoomNumAndLimitType(userAccount, chatRoomNum, AnchorLimitation.LIMIT_TYPE_SHUTUP) ;
    }

    /**
     *  用户被踢出直播间
     * @param chatRoomNum 直播间
     * @param userAccount 用户账号
     */
    @Override
    public void onKickoutUserOnChatRoom(String chatRoomNum, String userAccount) {

    }

    /**
     * 用户被解除踢出直播间
     * @param chatRoomNum
     * @param userAccount
     */
    @Override
    public void onRelieveKickoutUserOnChatRoom(String chatRoomNum, String userAccount) {

    }
}

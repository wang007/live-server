package org.live.live.service.impl;

import org.live.common.base.BaseRepository;
import org.live.common.base.BaseServiceImpl;
import org.live.live.entity.Anchor;
import org.live.live.entity.LiveRoom;
import org.live.live.entity.MobileUser;
import org.live.live.repository.LiveRoomRepository;
import org.live.live.service.LiveRoomService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wang on 2017/4/12.
 */
@Service("liveRoomService")
public class LiveRoomServiceImpl extends BaseServiceImpl<LiveRoom, String> implements LiveRoomService {

    @Resource
    private LiveRoomRepository repository ;

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
}

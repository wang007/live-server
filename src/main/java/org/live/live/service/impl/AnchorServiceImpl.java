package org.live.live.service.impl;

import org.live.app.vo.AppAnchorInfo;
import org.live.common.base.BaseRepository;
import org.live.common.base.BaseServiceImpl;
import org.live.live.entity.Anchor;
import org.live.live.entity.Attention;
import org.live.live.entity.LiveRoom;
import org.live.live.entity.MobileUser;
import org.live.live.repository.AnchorRepository;
import org.live.live.repository.AttentionRepository;
import org.live.live.repository.LiveRoomRepository;
import org.live.live.service.AnchorService;
import org.live.live.service.LiveRoomService;
import org.live.live.vo.AnchorInfoVo;
import org.live.live.vo.AnchorVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wang on 2017/4/11.
 */
@Service("anchorService")
public class AnchorServiceImpl extends BaseServiceImpl<Anchor, String> implements AnchorService {

    @Resource
    private AnchorRepository repository ;

    @Resource
    private AttentionRepository attentionRepository ;

    @Resource
    private LiveRoomRepository liveRoomRepository ;

    @Override
    protected BaseRepository<Anchor, String> getRepository() {
        return this.repository ;
    }

    @Override
    public Page<AnchorVo> findAnchors(PageRequest page, String searchStr) {
        return repository.findAnchors(page, searchStr) ;
    }

    @Override
    public AnchorInfoVo findAnchorInfo(String anchorId) {
        return repository.findAnchorInfo(anchorId) ;
    }

    @Override
    public Anchor findAnchorByUser(MobileUser user) {
        return repository.findAnchorByUser(user) ;
    }

    @Override
    public AppAnchorInfo findAnchorForAppUser(String userId, String liveRoomId) {

        AppAnchorInfo info = new AppAnchorInfo() ;
        long attentionCount = attentionRepository.countAttentionsByLiveRoom_Id(liveRoomId);//关注数
        info.setAttentionCount((int) attentionCount) ;
        List<Attention> attentions = attentionRepository.findAttentionsByUser_IdAndLiveRoom_Id(userId, liveRoomId);
        if(attentions != null && attentions.size() > 0) {   //用户关注了该直播间
            info.setAttentionFlag(true) ;
        }
        LiveRoom liveRoom = liveRoomRepository.findOne(liveRoomId);
        info.setDescription(liveRoom.getAnchor().getDescription()) ;    //个性签名
        MobileUser user = liveRoom.getAnchor().getUser() ;  //主播信息
        info.setAccount(user.getAccount()) ;
        info.setAnchorUserId(user.getId()) ;
        info.setHeadImgUrl(user.getHeadImgUrl()) ;
        info.setNickname(user.getNickname()) ;
        return info ;
    }


}

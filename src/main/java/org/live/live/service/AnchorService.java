package org.live.live.service;

import org.live.app.vo.AppAnchorInfo;
import org.live.common.base.BaseService;
import org.live.live.entity.Anchor;
import org.live.live.entity.MobileUser;
import org.live.live.vo.AnchorInfoVo;
import org.live.live.vo.AnchorVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by wang on 2017/4/11.
 */
public interface AnchorService extends BaseService<Anchor, String> {

    /**
     * 查询主播信息
     * @param page
     * @param searchStr 搜索条件
     * @return
     */
    public Page<AnchorVo> findAnchors(PageRequest page, String searchStr) ;

    /**
     * 查询单条主播信息
     * @param anchorId
     * @return
     */
    public AnchorInfoVo findAnchorInfo(String anchorId) ;

    /**
     * 查询主播信息
     * @param user 移动端用户
     * @return
     */
    Anchor findAnchorByUser(MobileUser user) ;

    /**
     * 查询一些主播信息，for 移动端用户
     * @param userId
     * @param liveRoomId
     * @return
     */
    AppAnchorInfo findAnchorForAppUser(String userId, String liveRoomId) ;



}

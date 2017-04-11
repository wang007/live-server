package org.live.live.repository;

import org.live.common.base.BaseRepository;
import org.live.live.entity.Anchor;
import org.live.live.vo.AnchorInfoVo;
import org.live.live.vo.AnchorVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by wang on 2017/4/11.
 */
public interface AnchorRepository extends BaseRepository<Anchor, String> {

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
    @Query("select new org.live.live.vo.AnchorInfoVo(a.id, a.user.nickname, a.realName, a.user.headImgUrl, a.idCard, a.createTime, a.description, a.user.email, a.user.mobileNumber, a.user.lockFlag) from Anchor a where a.id=:anchorId")
    public AnchorInfoVo findAnchorInfo(@Param("anchorId") String anchorId) ;
}

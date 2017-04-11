package org.live.live.repository;

import org.live.common.base.BaseRepository;
import org.live.live.entity.Anchor;
import org.live.live.vo.AnchorVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
}

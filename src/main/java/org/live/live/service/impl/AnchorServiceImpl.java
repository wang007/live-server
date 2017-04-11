package org.live.live.service.impl;

import org.live.common.base.BaseRepository;
import org.live.common.base.BaseServiceImpl;
import org.live.live.entity.Anchor;
import org.live.live.repository.AnchorRepository;
import org.live.live.service.AnchorService;
import org.live.live.vo.AnchorInfoVo;
import org.live.live.vo.AnchorVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wang on 2017/4/11.
 */
@Service("anchorService")
public class AnchorServiceImpl extends BaseServiceImpl<Anchor, String> implements AnchorService {

    @Resource
    private AnchorRepository repository ;

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
}

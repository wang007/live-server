package org.live.live.service.impl;

import org.live.app.vo.ApplyAnchorVo;
import org.live.common.base.BaseRepository;
import org.live.common.base.BaseServiceImpl;
import org.live.live.entity.ApplyAnchor;
import org.live.live.repository.ApplyAnchorRepository;
import org.live.live.service.ApplyAnchorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by wang on 2017/4/15.
 */
@Service("applyAnchorService")
public class ApplyAnchorServiceImpl extends BaseServiceImpl<ApplyAnchor, String> implements ApplyAnchorService {

    @Resource
    private ApplyAnchorRepository repository;

    @Override
    protected BaseRepository<ApplyAnchor, String> getRepository() {
        return repository;
    }

    @Override
    public boolean judgeUserApplyCount(String userId, int systemMaxCount) {
        long userApplyCount = repository.countApplyAnchorByUser_Id(userId);
        if (userApplyCount >= systemMaxCount) {
            return false;
        }
        return true;
    }

    @Override
    public void createApplyAnchorForm(ApplyAnchorVo applyAnchorVo) {

    }


}

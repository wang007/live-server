package org.live.live.service.impl;

import org.live.common.base.BaseRepository;
import org.live.common.base.BaseServiceImpl;
import org.live.live.entity.LiveCategory;
import org.live.live.repository.LiveCategoryRepository;
import org.live.live.service.LiveCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Mr.wang on 2017/3/29.
 */
@Service("liveCategoryService")
public class LiveCategoryServiceImpl extends BaseServiceImpl<LiveCategory, String> implements LiveCategoryService {

    @Resource
    private LiveCategoryRepository repository ;

    @Override
    protected BaseRepository<LiveCategory, String> getRepository() {
        return this.repository ;
    }

    @Override
    public List<LiveCategory> findAllCategory() {

        return repository.findAllCategory() ;
    }

    @Override
    public Integer findMaxSerialNo() {

        return repository.findMaxSerialNo() ;
    }


}

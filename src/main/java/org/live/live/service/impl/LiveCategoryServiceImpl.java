package org.live.live.service.impl;

import org.live.common.base.BaseRepository;
import org.live.common.base.BaseServiceImpl;
import org.live.common.constants.SystemConfigConstants;
import org.live.common.support.ServletContextHolder;
import org.live.live.entity.LiveCategory;
import org.live.live.repository.LiveCategoryRepository;
import org.live.live.repository.LiveRoomRepository;
import org.live.live.service.LiveCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * Created by Mr.wang on 2017/3/29.
 */
@Service("liveCategoryService")
public class LiveCategoryServiceImpl extends BaseServiceImpl<LiveCategory, String> implements LiveCategoryService {

    @Resource
    private LiveCategoryRepository repository ;

    @Resource
    private LiveRoomRepository liveRoomRepository ;

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

    @Override
    public boolean deleteLiveCategoryById(String id) {

        long liveRoomCount = liveRoomRepository.countLiveRoomByLiveCategory(id) ;
        if(liveRoomCount > 0) return false ;
        LiveCategory category = findOne(id) ;
        String coverUrl = category.getCoverUrl() ;
        String realProjectDir = ServletContextHolder.getAttribute(SystemConfigConstants.REAL_PROJECT_DIR_KEY) ;
        File file = new File(realProjectDir, coverUrl) ;
        if(file.exists()) file.delete() ;
        delete(category) ;
        return  true ;
    }


}

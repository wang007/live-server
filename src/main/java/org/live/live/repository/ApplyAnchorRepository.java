package org.live.live.repository;

import net.sf.ehcache.search.impl.BaseResult;
import org.live.common.base.BaseRepository;
import org.live.live.entity.ApplyAnchor;

/**
 * Created by wang on 2017/4/15.
 */
public interface ApplyAnchorRepository extends BaseRepository<ApplyAnchor, String> {

    /**
     * 查询该用户申请次数
     * @param userId
     * @return
     */
    long countApplyAnchorByUser_Id(String userId) ;

}

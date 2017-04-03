package org.live.live.repository;

import org.live.common.base.BaseRepository;
import org.live.live.entity.LiveCategory;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Mr.wang on 2017/3/29.
 */
public interface LiveCategoryRepository extends BaseRepository<LiveCategory, String> {

    /**
     * 查询全部的直播分类，按enabled，serialNo排序
     * @return
     */
    @Query("select c from LiveCategory c order by c.enabled DESC, c.serialNo ASC")
    public List<LiveCategory> findAllCategory() ;

    /**
     *  获取max的最大值
     * @return
     */
    @Query(value=" select max(l.serialNo) from LiveCategory l")
    public Integer findMaxSerialNo() ;


}

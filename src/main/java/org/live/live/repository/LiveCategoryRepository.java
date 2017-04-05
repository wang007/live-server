package org.live.live.repository;

import org.live.app.vo.LiveCategoryVo;
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
    @Query("select l from LiveCategory l order by l.enabled DESC, l.serialNo ASC")
    public List<LiveCategory> findAllCategory() ;

    /**
     *  获取max的最大值
     * @return
     */
    @Query(value=" select max(l.serialNo) from LiveCategory l")
    public Integer findMaxSerialNo() ;

    /**
     * 查询直播分类给移动端
     * @return
     */
    @Query("select new org.live.app.vo.LiveCategoryVo(l.id, l.categoryName, l.coverUrl) from LiveCategory l where l.enabled=true order by l.serialNo ASC")
    public List<LiveCategoryVo> findLiveCategory4app() ;


}

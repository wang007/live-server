package org.live.live.service;

import org.live.common.base.BaseService;
import org.live.live.entity.LiveCategory;

import java.util.List;

/**
 * Created by Mr.wang on 2017/3/29.
 */
public interface LiveCategoryService extends BaseService<LiveCategory, String> {


    /**
     * 查询全部的分类
     * @return
     */
    public List<LiveCategory> findAllCategory() ;

    /**
     *  获取serialNo的最大值
     * @return
     */
    public Integer findMaxSerialNo() ;

    /**
     *  根据id，删除直播分类
     * @param id
     * @return true: 删除成功，false：该分类下存在直播间，不能进行删除
     */
    public boolean deleteLiveCategoryById(String id) ;


}

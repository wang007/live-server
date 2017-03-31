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


}

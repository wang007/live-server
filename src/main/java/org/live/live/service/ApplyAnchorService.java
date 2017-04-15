package org.live.live.service;

import org.live.app.vo.ApplyAnchorVo;
import org.live.common.base.BaseService;
import org.live.live.entity.ApplyAnchor;

/**
 * Created by wang on 2017/4/15.
 */
public interface ApplyAnchorService extends BaseService<ApplyAnchor, String> {

    /**
     * 判断用户是否超过最大申请数
     * @param userId
     * @param systemMaxCount
     * @return true:　申请通过， false： 申请不通过
     */
    boolean judgeUserApplyCount(String userId, int systemMaxCount) ;

    /**
     * 创建申请表单
     * @param applyAnchorVo
     */
    void createApplyAnchorForm(ApplyAnchorVo applyAnchorVo) ;

}

package org.live.live.service;

import org.live.app.vo.ApplyAnchorVo;
import org.live.common.base.BaseService;
import org.live.common.response.DataTableModel;
import org.live.live.entity.ApplyAnchor;
import org.live.live.vo.ApplyInfoVo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.Date;

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

    /**
     *  获取用户的最后一次提交的时间
     * @param userId
     * @return
     */
    Date getLastApplyAnchorDate(String userId) ;

    /**
     * 根据分页信息查询数据
     * @param auditFlag, true: 已审核， false： 未审核.
     * @param request
     * @return
     */
    DataTableModel findByPage(HttpServletRequest request, boolean auditFlag) ;

    /**
     * 查询主播申请表的详情
     * @param applyId
     * @return
     */
     ApplyInfoVo getApplyInfo(String applyId) ;

    /**
     * 更改主播表的申请状态，如果申请通过，那么创建直播间，主播。 更新mobileUser的anchorFlag字段
     * @param applyId 申请表id
     * @param passFlag 审核是否通过的标志
     * @param reason 审核不通过的理由
     * @return
     */
     void saveApplyPassFlag(String applyId, int passFlag, String reason) ;
}

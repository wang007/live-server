package org.live.live.repository;

import org.apache.commons.lang3.StringUtils;
import org.live.common.base.BaseRepositoryImpl;
import org.live.live.vo.ApplyVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wang on 2017/4/15.
 */
public class ApplyAnchorRepositoryImpl extends BaseRepositoryImpl {


    /**
     * 获取未审核的主播申请表
     */
    private static final String FIND_APPLYS_NOT_AUDIT_XHQL = "select new org.live.live.vo.ApplyVo(a.id, a.user.account, a.user.nickname, a.user.member.realName, a.category.categoryName,"
            + " a.passFlag, a.user.email, a.user.mobileNumber, a.createTime) from ApplyAnchor a where a.passFlag=0 ("
            + "/~ a.user.account like '%[account]%'~/"
            + "/~ or a.user.nickname like '%[nickname]%' ~/"
            + "/~ or a.user.member.realName like '%[realName]%' ~/"
            + ")";
    /**
     * 获取审核过的主播申请表，包括审核通过，未通过
     */
    private static final String FIND_APPLYS_AUDITED_XHQL = "select new org.live.live.vo.ApplyVo(a.id, a.user.account, a.user.nickname, a.user.member.realName, a.category.categoryName,"
            + " a.passFlag, a.user.email, a.user.mobileNumber, a.createTime) from ApplyAnchor a where a.passFlag!=0 ("
            + "/~ a.user.account like '%[account]%'~/"
            + "/~ or a.user.nickname like '%[nickname]%' ~/"
            + "/~ or a.user.member.realName like '%[realName]%' ~/"
            + ")";
    // auditFlag: true: 已审核的申请表， false： 未审核的申请表
    public Page<ApplyVo> findApplys(PageRequest page, String searchStr, boolean auditFlag) {
        Map<String, Object> filter = new HashMap<>(4);
        if (StringUtils.isNotEmpty(searchStr)) {
            filter.put("account", searchStr);
            filter.put("nickname", searchStr);
            filter.put("realName", searchStr);
        }
        String hql = this.xsqlConvertHql( auditFlag ? FIND_APPLYS_AUDITED_XHQL: FIND_APPLYS_NOT_AUDIT_XHQL, filter);
        hql = hql.replace("()", "");
        return this.find(page, hql, null);
    }





}

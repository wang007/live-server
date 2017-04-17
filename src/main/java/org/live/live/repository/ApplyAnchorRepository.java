package org.live.live.repository;

import net.sf.ehcache.search.impl.BaseResult;
import org.live.common.base.BaseRepository;
import org.live.live.entity.ApplyAnchor;
import org.live.live.entity.MobileUser;
import org.live.live.vo.ApplyInfoVo;
import org.live.live.vo.ApplyVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    /**
     *  获取用户的最后一次提交的时间
     * @param userId
     * @return
     */
    @Query("select max(a.createTime) from ApplyAnchor a where a.user.id=:userId")
    Date getLastApplyAnchorDate(@Param("userId") String userId) ;

    /**
     * 查询未审核主播申请表
     * @param auditFlag, true: 已审核， false： 未审核.
     * @return
     */
    Page<ApplyVo> findApplys(PageRequest page, String searchVal, boolean auditFlag) ;

    /**
     * 查询主播申请表的详情
     * @param applyId
     * @return
     */
    @Query("select new org.live.live.vo.ApplyInfoVo(a.id, a.user.account, a.user.nickname, a.idCard," +
            " a.realName, a.user.member.realName, a.user.member.sex, a.category.categoryName, a.passFlag," +
            " a.noPassReason, a.createTime, a.idImgUrl, a.user.email, a.user.mobileNumber) from ApplyAnchor a where a.id=:applyId")
    ApplyInfoVo getApplyInfo(@Param("applyId") String applyId) ;

    /**
     * 查询用户的申请表
     * @param user 用户
     * @return
     */
    List<ApplyAnchor> findApplyAnchorsByUser(MobileUser user) ;

}

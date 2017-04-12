package org.live.school.service;

import org.live.common.base.BaseService;
import org.live.common.response.DataTableModel;
import org.live.school.entity.Member;
import org.live.school.vo.SimpleMemberVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 成员业务接口
 * Created by KAM on 2017/4/6.
 */
public interface MemberService extends BaseService<Member, String> {

     DataTableModel findPage(HttpServletRequest request);

     List<SimpleMemberVo> findByRealName(String realName);

    Member findMemberBymemberNo(String memberNo) ;
}

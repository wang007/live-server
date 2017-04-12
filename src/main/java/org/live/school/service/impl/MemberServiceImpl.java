package org.live.school.service.impl;

import org.live.common.base.BaseRepository;
import org.live.common.base.BaseServiceImpl;
import org.live.common.response.DataTableModel;
import org.live.common.utils.DataTableUtils;
import org.live.school.entity.Member;
import org.live.school.repository.MemberRepository;
import org.live.school.service.MemberService;
import org.live.school.vo.MemberVo;
import org.live.school.vo.SimpleMemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 成员业务接口实现
 * Created by KAM on 2017/4/6.
 */
@Service(value = "memberService")
public class MemberServiceImpl extends BaseServiceImpl<Member, String> implements MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Override
    protected BaseRepository<Member, String> getRepository() {
        return memberRepository;
    }

    /**
     * 查询成员分页信息
     *
     * @return DataTableModel dataTable定制模型
     */
    public DataTableModel findPage(HttpServletRequest request) {
        Long recordsTotal = memberRepository.count(); // 查询总记录数
        Map<String, Object> params = DataTableUtils.parseMap(request); // 映射请求参数
        Page<MemberVo> page = memberRepository.find((PageRequest) params.get("pageRequest"), (Map<String, Object>) params.get("filter")); // 查询分页数据
        DataTableModel model = DataTableUtils.parseDataTableModel(page, (Integer) params.get("draw"), recordsTotal); // 映射成定制模型
        return model;
    }

    @Override
    public List<SimpleMemberVo> findByRealName(String realName) {
        return memberRepository.findByRealNameLike(realName);
    }

    @Override
    public Member findMemberBymemberNo(String memberNo) {
        return memberRepository.findMemberBymemberNo(memberNo) ;
    }
}

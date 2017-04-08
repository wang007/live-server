package org.live.school.repository;

import org.live.common.base.BaseRepository;
import org.live.live.vo.MobileUserVo;
import org.live.school.entity.Member;
import org.live.school.vo.MemberVo;
import org.live.school.vo.SimpleMemberVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * 成员数据访问接口
 * Created by KAM on 2017/4/6.
 */
public interface MemberRepository extends BaseRepository<Member, String> {
    Page<MemberVo> find(PageRequest pageRequest, Map<String, Object> filter);

    @Query("select new org.live.school.vo.SimpleMemberVo(m.id, m.memberNo, m.realName) from Member m where m.realName like %:realName%")
    List<SimpleMemberVo> findByRealNameLike(@Param("realName") String realName);
}

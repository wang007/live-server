package org.live.school.repository;

import org.live.common.base.BaseRepository;
import org.live.school.entity.Member;
import org.live.school.vo.MemberVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 * 成员数据访问接口
 * Created by KAM on 2017/4/6.
 */
public interface MemberRepository extends BaseRepository<Member, String> {
    Page<MemberVo> find(PageRequest pageRequest, Map<String, Object> filter);
}

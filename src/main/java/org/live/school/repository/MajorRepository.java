package org.live.school.repository;

import org.live.common.base.BaseRepository;
import org.live.school.entity.Major;
import org.live.school.vo.MajorVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 * Created by KAM on 2017/4/4.
 */
public interface MajorRepository extends BaseRepository<Major, String>{
    Page<MajorVo> find(PageRequest pageRequest, Map<String, Object> filter);
}

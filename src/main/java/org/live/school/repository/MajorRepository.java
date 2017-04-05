package org.live.school.repository;

import org.live.common.base.BaseRepository;
import org.live.school.entity.Major;
import org.live.school.vo.MajorVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * 专业数据库访问接口
 * Created by KAM on 2017/4/4.
 */
public interface MajorRepository extends BaseRepository<Major, String> {
    Page<MajorVo> find(PageRequest pageRequest, Map<String, Object> filter);

    @Query("select m from Major m where m.enableFlag = :enableFlag")
    List<Major> findByEnableFlag(@Param("enableFlag") boolean enableFlag);
}

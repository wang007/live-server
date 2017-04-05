package org.live.school.repository;

import org.live.common.base.BaseRepository;
import org.live.school.entity.Grade;
import org.live.school.vo.GradeVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * 班级数据库访问接口
 * Created by KAM on 2017/4/5.
 */
public interface GradeRepository extends BaseRepository<Grade, String> {
    Page<GradeVo> find(PageRequest pageRequest, Map<String, Object> filter);

    @Query("select g from Grade g where g.enableFlag = :enableFlag")
    List<Grade> findByEnableFlag(@Param("enableFlag") boolean enableFlag);
}

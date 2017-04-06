package org.live.school.repository;

import org.live.common.base.BaseRepository;
import org.live.school.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * 系部数据库访问接口
 * Created by KAM on 2017/4/4.
 */
public interface DepartmentRepository extends BaseRepository<Department, String> {
    Page<Department> find(PageRequest pageRequest, Map<String, Object> filter);
    @Query("select d from Department d where d.enableFlag = :enableFlag")
    List<Department> findByEnableFlag(@Param("enableFlag") boolean enableFlag);
}

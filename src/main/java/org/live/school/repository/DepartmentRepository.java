package org.live.school.repository;

import org.live.common.base.BaseRepository;
import org.live.school.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 * 系部数据库操作接口
 * Created by KAM on 2017/4/4.
 */
public interface DepartmentRepository extends BaseRepository<Department, String> {
    Page<Department> find(PageRequest pageRequest, Map<String, Object> filter);
}

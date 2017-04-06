package org.live.school.repository;

import org.live.common.base.BaseRepository;
import org.live.school.entity.Student;
import org.live.school.vo.StudentVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 * 学生数据访问接口
 * Created by KAM on 2017/4/6.
 */
public interface StudentRepository extends BaseRepository<Student, String> {
    Page<StudentVo> find(PageRequest pageRequest, Map<String, Object> filter);
}

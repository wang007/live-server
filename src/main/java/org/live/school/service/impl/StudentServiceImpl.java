package org.live.school.service.impl;

import org.live.common.base.BaseRepository;
import org.live.common.base.BaseServiceImpl;
import org.live.common.response.DataTableModel;
import org.live.common.utils.DataTableUtils;
import org.live.school.entity.Student;
import org.live.school.repository.StudentRepository;
import org.live.school.service.StudentService;
import org.live.school.vo.StudentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 学生业务接口实现
 * Created by KAM on 2017/4/6.
 */
@Service(value="studentService")
public class StudentServiceImpl extends BaseServiceImpl<Student, String> implements StudentService{
    @Autowired
    private StudentRepository studentRepository;
    @Override
    protected BaseRepository<Student, String> getRepository() {
        return studentRepository;
    }

    /**
     * 查询学生分页信息
     *
     * @return DataTableModel dataTable定制模型
     */
    public DataTableModel findPage(HttpServletRequest request) {
        Long recordsTotal = studentRepository.count(); // 查询总记录数
        Map<String, Object> params = DataTableUtils.parseMap(request); // 映射请求参数
        Page<StudentVo> page = studentRepository.find((PageRequest) params.get("pageRequest"), (Map<String, Object>) params.get("filter")); // 查询分页数据
        DataTableModel model = DataTableUtils.parseDataTableModel(page, (Integer) params.get("draw"), recordsTotal); // 映射成定制模型
        return model;
    }
}

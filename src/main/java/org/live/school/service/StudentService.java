package org.live.school.service;

import org.live.common.base.BaseService;
import org.live.common.response.DataTableModel;
import org.live.school.entity.Student;

import javax.servlet.http.HttpServletRequest;

/**
 * 学生业务接口
 * Created by KAM on 2017/4/6.
 */
public interface StudentService extends BaseService<Student, String> {
    public DataTableModel findPage(HttpServletRequest request);
}

package org.live.school.repository;

import org.live.common.base.BaseRepositoryImpl;
import org.live.school.vo.GradeVo;
import org.live.school.vo.StudentVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 * 学生数据访问接口实现
 * Created by KAM on 2017/4/6.
 */
public class StudentRepositoryImpl extends BaseRepositoryImpl{
    String hqlTemplate = "select new org.live.school.vo.StudentVo(s.id, s.sNo, s.realName, s.sex, g.className, s.age, s.birthday, s.outDate, s.registerDate) from Student s, Grade g where s.grade = g";
    String block = "/~s.sNo like '%[sNo]%'~/"
            + "/~ or s.realName like '%[realName]%'~/"
            + "/~ or s.sex like '%[sex]%'~/"
            + "/~ or g.className like '%[className]%'~/";

 /*   String hqlTemplate1 = "select new org.live.school.vo.MajorVo(m.id, m.code, m.name, m.description, m.createTime, m.department.name) from Major m where"
            + "/~ or m.code like '%[code]%'~/"
            + "/~ or m.name like '%[name]%'~/"
            + "/~ or m.description like '%[description]%'~/"
            + "/~ or m.department.name like '%[departmentName]%'~/";*/


    /**
     * 查询学生分页数据
     *
     * @param pageRequest
     * @param filter
     * @return
     */
    Page<StudentVo> find(PageRequest pageRequest, Map<String, Object> filter) {
        StringBuilder _hql = new StringBuilder();
        _hql.append(hqlTemplate);
        if (filter.size() > 0) {
            _hql.append(" and ( ");
            _hql.append(block);
            _hql.append(" )");
        }
        String hql = super.xsqlConvertHql(_hql.toString(), filter);
        System.out.println("hql---> " + hql);
        Page<StudentVo> page = super.find(pageRequest, hql, null);
        return page;
    }
}

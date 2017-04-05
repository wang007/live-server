package org.live.school.repository;

import org.live.common.base.BaseRepositoryImpl;
import org.live.school.entity.Grade;
import org.live.school.vo.GradeVo;
import org.live.school.vo.MajorVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 * 班级数据库访问接口实现
 * Created by KAM on 2017/4/5.
 */
public class GradeRepositoryImpl extends BaseRepositoryImpl {
    String hqlTemplate = "select new org.live.school.vo.GradeVo(g.id, g.classNo, g.gradeNo, g.className, m.name, g.createTime, g.enableFlag) from Grade g, Major m where g.major = m";
    String block = "/~g.classNo like '%[classNo]%'~/"
            + "/~ or g.gradeNo like '%[gradeNo]%'~/"
            + "/~ or g.className like '%[className]%'~/"
            + "/~ or m.name like '%[majorName]%'~/";

 /*   String hqlTemplate1 = "select new org.live.school.vo.MajorVo(m.id, m.code, m.name, m.description, m.createTime, m.department.name) from Major m where"
            + "/~ or m.code like '%[code]%'~/"
            + "/~ or m.name like '%[name]%'~/"
            + "/~ or m.description like '%[description]%'~/"
            + "/~ or m.department.name like '%[departmentName]%'~/";*/


    /**
     * 查询专业分页数据
     *
     * @param pageRequest
     * @param filter
     * @return
     */
    Page<GradeVo> find(PageRequest pageRequest, Map<String, Object> filter) {
        StringBuilder _hql = new StringBuilder();
        _hql.append(hqlTemplate);
        if (filter.size() > 0) {
            _hql.append(" and ( ");
            _hql.append(block);
            _hql.append(" )");
        }
        String hql = super.xsqlConvertHql(_hql.toString(), filter);
        System.out.println("hql---> " + hql);
        Page<GradeVo> page = super.find(pageRequest, hql, null);
        return page;
    }
}

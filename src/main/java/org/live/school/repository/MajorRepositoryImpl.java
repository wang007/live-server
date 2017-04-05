package org.live.school.repository;

import org.live.common.base.BaseRepositoryImpl;
import org.live.school.vo.MajorVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 * Created by KAM on 2017/4/4.
 */
public class MajorRepositoryImpl extends BaseRepositoryImpl {
    String hqlTemplate = "select new org.live.school.vo.MajorVo(m.id, m.code, m.name, m.description, m.createTime, d.name, m.enableFlag) from Major m, Department d where m.department = d";
    String block = "/~m.code like '%[code]%'~/"
            + "/~ or m.name like '%[name]%'~/"
            + "/~ or m.description like '%[description]%'~/"
            + "/~ or d.name like '%[departmentName]%'~/";

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
    Page<MajorVo> find(PageRequest pageRequest, Map<String, Object> filter) {
        StringBuilder _hql = new StringBuilder();
        _hql.append(hqlTemplate);
        if (filter.size() > 0) {
            _hql.append(" and ( ");
            _hql.append(block);
            _hql.append(" )");
        }
        String hql = super.xsqlConvertHql(_hql.toString(), filter);
        System.out.println("hql---> " + hql);
        Page<MajorVo> page = super.find(pageRequest, hql, null);
        return page;
    }
}

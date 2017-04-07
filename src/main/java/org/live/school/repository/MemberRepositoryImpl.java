package org.live.school.repository;

import org.live.common.base.BaseRepositoryImpl;
import org.live.school.vo.MemberVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 * 成员数据访问接口实现
 * Created by KAM on 2017/4/6.
 */
public class MemberRepositoryImpl extends BaseRepositoryImpl {
    String hqlTemplate = "select new org.live.school.vo.MemberVo(m.id, m.memberNo, m.realName, m.sex, m.grade.className, m.age, m.birthday, m.memberType, m.outDate, m.registerDate) from Member m left join m.grade";
    String block = "/~m.memberNo like '%[memberNo]%'~/"
            + "/~ or m.realName like '%[realName]%'~/"
            + "/~ or m.memberType like '%[memberType]%'~/"
            + "/~ or m.sex like '%[sex]%'~/"
            + "/~ or m.grade.className like '%[className]%'~/";

 /*   String hqlTemplate1 = "select new org.live.school.vo.MajorVo(m.id, m.code, m.name, m.description, m.createTime, m.department.name) from Major m where"
            + "/~ or m.code like '%[code]%'~/"
            + "/~ or m.name like '%[name]%'~/"
            + "/~ or m.description like '%[description]%'~/"
            + "/~ or m.department.name like '%[departmentName]%'~/";*/


    /**
     * 查询成员分页数据
     *
     * @param pageRequest
     * @param filter
     * @return
     */
    Page<MemberVo> find(PageRequest pageRequest, Map<String, Object> filter) {
        StringBuilder _hql = new StringBuilder();
        _hql.append(hqlTemplate);
        if (filter.size() > 0) {
            _hql.append(" where ");
            _hql.append(block);
        }
        String hql = super.xsqlConvertHql(_hql.toString(), filter);
        System.out.println("hql---> " + hql);
        Page<MemberVo> page = super.find(pageRequest, hql, null);
        return page;
    }
}

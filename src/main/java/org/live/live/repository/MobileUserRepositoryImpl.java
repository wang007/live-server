package org.live.live.repository;

import org.live.common.base.BaseRepositoryImpl;
import org.live.live.vo.MobileUserVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 * 移动端用户数据库访问接口实现
 * Created by KAM on 2017/4/6.
 */
public class MobileUserRepositoryImpl extends BaseRepositoryImpl {
    String hqlTemplate = "select new org.live.live.vo.MobileUserVo(mu.id, mu.headImgUrl, mu.account, mu.nickname, mu.email, mu.mobileNumber, mu.registerTime, mu.anchorFlag, mu.lockFlag, mu.lastLoginTime, " +
            "mu.lastLoginIp, m.memberNo, m.realName, m.sex, m.memberType, m.grade.className, m.age, m.birthday) from MobileUser mu left join mu.member m left join m.grade";
    String block = "/~mu.account like '%[account]%'~/"
            + "/~ or mu.nickname like '%[nickname]%'~/"
            + "/~ or mu.email like '%[email]%'~/"
            + "/~ or mu.mobileNumber like '%[mobileNumber]%'~/"
            + "/~ or m.memberNo like '%[memberNo]%'~/"
            + "/~ or m.realName like '%[realName]%'~/"
            + "/~ or m.sex like '%[sex]%'~/"
            + "/~ or m.memberType like '%[memberType]%'~/"
            + "/~ or m.grade.className like '%[className]%'~/"
            + "/~ or m.age like '%[age]%'~/";

 /*   String hqlTemplate1 = "select new org.live.school.vo.MajorVo(m.id, m.code, m.name, m.description, m.createTime, m.department.name) from Major m where"
            + "/~ or m.code like '%[code]%'~/"
            + "/~ or m.name like '%[name]%'~/"
            + "/~ or m.description like '%[description]%'~/"
            + "/~ or m.department.name like '%[departmentName]%'~/";*/


    /**
     * 查询移动端用户分页数据
     *
     * @param pageRequest
     * @param filter
     * @return
     */
    Page<MobileUserVo> find(PageRequest pageRequest, Map<String, Object> filter) {
        StringBuilder _hql = new StringBuilder();
        _hql.append(hqlTemplate);
        if (filter.size() > 0) {
            _hql.append(" where ");
            _hql.append(block);
        }
        String hql = super.xsqlConvertHql(_hql.toString(), filter);
        System.out.println("hql---> " + hql);
        Page<MobileUserVo> page = super.find(pageRequest, hql, null);
        return page;
    }
}

package org.live.module.demo.repository;

import org.live.common.base.BaseRepositoryImpl;
import org.live.module.demo.entity.DemoVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 * Created by KAM on 2017/3/28.
 */
public class DemoRepositoryImpl extends BaseRepositoryImpl {
    String hqlTemplate = "select new org.live.module.demo.entity.DemoVo(d.id, d.name, d.age, d.username, d.address, d.other) from Demo d";
    String block = "/~ d.name like '%[name]%'~/" +
            "/~ or d.age like '%[age]%'~/" +
            "/~ or d.username like '%[username]%'~/" +
            "/~ or d.address like '%[address]%'~/" +
            "/~ or d.other like '%[other]%'~/";

    /**
     * 查询菜单分页
     *
     * @param pageRequest
     * @param filter
     * @return
     */
    public Page<DemoVo> find(PageRequest pageRequest, Map<String, Object> filter) {
        String hql = hqlTemplate;
        StringBuilder xsql = new StringBuilder();
        if (filter.size() > 0) {
            xsql.append(hqlTemplate);
            xsql.append(" where");
            xsql.append(block);
            hql = super.xsqlConvertHql(xsql.toString(), filter);
        }
        System.out.println("hql---> " + hql);
        Page<DemoVo> page = super.find(pageRequest, hql, null);
        return page;
    }
}

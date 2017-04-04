package org.live.school.repository;

import org.live.common.base.BaseRepositoryImpl;
import org.live.dictionary.entity.DictType;
import org.live.school.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 * 系部数据库操作接口
 * Created by KAM on 2017/4/4.
 */
public class DepartmentRepositoryImpl extends BaseRepositoryImpl {
    String hqlTemplate = "from Department d";
    String block = "/~ d.code like '%[code]%'~/" +
            "/~ or d.name like '%[name]%'~/" +
            "/~ or d.description like '%[description]%'~/";

    /**
     * 查询字典类型分页数据
     *
     * @param pageRequest
     * @param filter
     * @return
     */
    Page<Department> find(PageRequest pageRequest, Map<String, Object> filter) {
        String hql = hqlTemplate;
        StringBuilder xsql = new StringBuilder();
        if (filter.size() > 0) {
            xsql.append(hqlTemplate);
            xsql.append(" where");
            xsql.append(block);
            hql = super.xsqlConvertHql(xsql.toString(), filter);
        }
        System.out.println("hql---> " + hql);
        Page<Department> page = super.find(pageRequest, hql, null);
        return page;
    }
}

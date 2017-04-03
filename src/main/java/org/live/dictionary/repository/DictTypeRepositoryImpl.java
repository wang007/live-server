package org.live.dictionary.repository;

import org.live.common.base.BaseRepositoryImpl;
import org.live.dictionary.entity.DictType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 * Created by KAM on 2017/4/2.
 */
public class DictTypeRepositoryImpl extends BaseRepositoryImpl{
    String hqlTemplate = "from DictType d";
    String block = "/~ d.typeName like '%[typeName]%'~/" +
            "/~ or d.dictTypeMark like '%[dictTypeMark]%'~/" +
            "/~ or d.description like '%[description]%'~/";

    /**
     * 查询字典类型分页数据
     *
     * @param pageRequest
     * @param filter
     * @return
     */
    public Page<DictType> find(PageRequest pageRequest, Map<String, Object> filter) {
        String hql = hqlTemplate;
        StringBuilder xsql = new StringBuilder();
        if (filter.size() > 0) {
            xsql.append(hqlTemplate);
            xsql.append(" where");
            xsql.append(block);
            hql = super.xsqlConvertHql(xsql.toString(), filter);
        }
        System.out.println("hql---> " + hql);
        Page<DictType> page = super.find(pageRequest, hql, null);
        return page;
    }
}

package org.live.dictionary.repository;

import org.live.common.base.BaseRepositoryImpl;

import org.live.dictionary.vo.DictionaryVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 * Created by KAM on 2017/4/3.
 */
public class DictRepositoryImpl extends BaseRepositoryImpl {
    String hqlTemplate = "select new org.live.dictionary.entity.DictionaryVo(d.id,dt.typeName,d.dictMark,d.dictValue,d.remarks) from Dictionary d, DictType dt where d.dictType = dt"+
    "/~ and d.dictMark like '%[dictMark]%'~/" +
            "/~ or d.dictValue like '%[dictValue]%'~/" +
            "/~ or d.remarks like '%[remarks]%'~/" +
            "/~ or dt.typeName like '%[typeName]%'~/";


    /**
     * 查询字典分页数据
     *
     * @param pageRequest
     * @param filter
     * @return
     */
    public Page<DictionaryVo> find(PageRequest pageRequest, Map<String, Object> filter) {
        String hql = super.xsqlConvertHql(hqlTemplate.toString(), filter);
        System.out.println("hql---> " + hql);
        Page<DictionaryVo> page = super.find(pageRequest, hql, null);
        return page;
    }
}

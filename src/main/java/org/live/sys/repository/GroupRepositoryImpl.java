package org.live.sys.repository;


import org.live.common.base.BaseRepositoryImpl;
import org.live.sys.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr.wang on 2016/12/7.
 */
public class GroupRepositoryImpl extends BaseRepositoryImpl {

    /**
     *
     * @param pageable
     * @param group
     * @return
     */
    public Page<Group> findGroups(Pageable pageable, Group group) {

        String xsql = "select g from Group g where 1=1 " +
                "/~ and g.serialNo= [serialNo] ~/" +
                "/~ and g.groupName like '%[groupName]%' ~/" +
                "order by g.serialNo ASC" ;

        Map<String, Object> filers = new HashMap<String, Object>() ;
        if(group != null) {
            filers.put("serialNo", group.getSerialNo()) ;
            filers.put("groupName", group.getGroupName()) ;
        }
        String hql = this.xsqlConvertSql(xsql, filers) ;
        return this.find(pageable, hql, null) ;

    }
}

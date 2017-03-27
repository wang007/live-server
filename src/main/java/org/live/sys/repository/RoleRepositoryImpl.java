package org.live.sys.repository;


import org.live.common.base.BaseRepositoryImpl;
import org.live.sys.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr.wang on 2016/12/7.
 */
public class RoleRepositoryImpl extends BaseRepositoryImpl {


    public Page<Role> findRoles(Pageable pageable, Role role) {

        String xsql = "select r from Role r where 1=1 " +
                "/~ and r.serialNo=[serialNo] ~/" +
                "/~ and r.roleName like '%[roleName]%' ~/" +
                "order by r.serialNo ASC" ;
        Map<String, Object> filters = new HashMap<String, Object>() ;
        if(role != null) {
            filters.put("serialNo", role.getSerialNo()) ;
            filters.put("roleName", role.getRoleName()) ;
        }
        String hql = this.xsqlConvertSql(xsql, filters) ;
        return this.find(pageable, hql, null);

    }
}

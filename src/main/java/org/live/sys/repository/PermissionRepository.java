package org.live.sys.repository;


import org.live.common.base.BaseRepository;
import org.live.sys.entity.Permission;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Mr.wang on 2016/11/29.
 */
public interface PermissionRepository extends BaseRepository<Permission, String> {

    /**
     * 根据账号查询角色
     * isEnable=1，确保这个属性是开启的
     * @param username
     * @return
     */
    @Query(value="select distinct rp.permission from RolePermission rp,GroupRole gr,User u " +
            "where rp.role= gr.role and u.group=gr.group and rp.permission.isEnable=1 " +
            "and gr.role.isEnable=1 and gr.group.isEnable=1 and u.username=:username")
    public List<Permission> findPermissionsByUsername(String username) ;

}

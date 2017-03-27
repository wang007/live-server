package org.live.sys.repository;

import org.live.common.base.BaseRepository;
import org.live.sys.entity.RolePermission;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Mr.wang on 2016/12/11.
 */
public interface RolePermissionRepository extends BaseRepository<RolePermission, String> {


    /**
     * 根据角色id，菜单id，删除角色与权限的关联实体。
     *
     * @param roleId 角色id
     * @param menuId 菜单id
     */
    //@Query(value="delete rp from RolePermission rp, Menu m,Permission p where rp.permission.id=p.id and p.id=m.permission.id and rp.role.id=:roleId and m.id=:menuId")

    @Query(value="delete rp from sys_role_permission rp,sys_role r, sys_permission p, sys_menu m where " +
            "rp.permission_id=p.id and p.id=m.permission_id and rp.role_id=:roleId and m.id=:menuId",nativeQuery = true)
    @Modifying
    public void deleteRolePermissionByIds(@Param(value = "roleId") String roleId, @Param(value = "menuId") String menuId) ;

    /**
     * 根据角色id，删除角色与权限的关联实体。
     *
     * @param roleId 角色id
     */
    @Query(value="delete from RolePermission rp where rp.role.id=:roleId")
    @Modifying
    public void deleteRolePermissionByRoleId(@Param(value = "roleId") String roleId) ;
}

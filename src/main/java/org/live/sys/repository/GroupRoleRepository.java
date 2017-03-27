package org.live.sys.repository;


import org.live.common.base.BaseRepository;
import org.live.sys.entity.Group;
import org.live.sys.entity.GroupRole;
import org.live.sys.entity.Role;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Mr.wang on 2016/12/2.
 */
public interface GroupRoleRepository extends BaseRepository<GroupRole, String> {

    @Query(value="select gr.role from GroupRole gr where gr.group=:group")
    public List<Role> findRoleByGroup(@Param(value = "group") Group group) ;


    /**
     * 删除用户组与角色的关联
     * @param groupId 用户组id
     */
    @Query(value="delete from GroupRole gr where gr.group.id=:groupId")
    @Modifying
    public void deleteGroupRoleByGroupId(@Param(value = "groupId") String groupId) ;

    /**
     * 删除用户组与角色的关联
     * @param roleId 角色id
     */
    @Query(value="delete from GroupRole gr where gr.role.id=:roleId and gr.group.id=:groupId")
    @Modifying
    public void deleteGroupRoleByRoleId(@Param(value = "groupId") String groupId, @Param(value = "roleId") String roleId) ;

    /**
     * 查询该用户组所拥有的角色名
     * @param groupId
     * @return
     */
    @Query(value="select gr.role.roleName from GroupRole gr where gr.group.id=:groupId")
    public String[] findRoleNamesByGroupId(@Param(value = "groupId") String groupId) ;

}

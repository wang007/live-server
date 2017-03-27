package org.live.sys.repository;


import org.live.common.base.BaseRepository;
import org.live.sys.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Mr.wang on 2016/11/29.
 */
public interface RoleRepository extends BaseRepository<Role,String> {

    /**
     * 根据账号查询角色
     * isEnable=1，确保这个属性是开启的
     *
     * @param username
     * @return
     */
    @Query(value = "select distinct gr.role from GroupRole gr,User u where gr.group=u.group and " +
            "gr.group.isEnable=1 and gr.role.isEnable=1 and u.username=:username")
    public List<Role> findRolesByUsername(String username) ;

    /**
     * 通过serialNo查询
     * @return
     */
    @Query(value="from Role r where r.serialNo=:serialNo")
    public List<Role> findRoleBySerialNo(@Param("serialNo") int serialNo);
    
    /**
     * 查询用户组信息
     * @param roleIds 用户组ids
     * @return
     */
    public List<Role> findByIdIn(String[] roleIds) ;

    /**
     *  删除角色
     * @param roleId 角色id
     */
    @Query(value="delete from Role r where r.id=:roleId")
    @Modifying
    public void deleteById(String roleId) ;

    /**
     *  分页查询角色
     * @param pageable
     * @param role
     * @return
     */
    public Page<Role> findRoles(Pageable pageable, Role role) ;
    
    /**
     * 获得serialNo最大值
     * @return
     */
    @Query(value=" select max(r.serialNo) from Role r")
    public Integer getMaxSerialNo();

}

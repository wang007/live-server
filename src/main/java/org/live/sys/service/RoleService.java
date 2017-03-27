package org.live.sys.service;


import org.live.common.base.BaseService;
import org.live.sys.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Mr.wang on 2016/12/2.
 */
public interface RoleService extends BaseService<Role,String> {

    /**
     *  查询角色
     * @param pageable
     * @param role
     * @return
     */
    public Page<Role> findRoles(Pageable pageable, Role role) ;
    
    /**
     * 生成serialNo
     * @return
     */
    public Integer createSerialNo();
    
    /**
     * 通过serialNo查询用户组
     * @param serialNo
     * @return
     */
    public List<Role> findRoleBySerialNo(int serialNo) ;
    
    /**
     * 检查序号是否已经存在
     * @param serialNo
     * @return
     */
    public boolean isExistSerialNo(int serialNo);

}

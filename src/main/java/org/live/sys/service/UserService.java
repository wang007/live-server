package org.live.sys.service;


import org.live.common.base.BaseService;
import org.live.sys.entity.Permission;
import org.live.sys.entity.Role;
import org.live.sys.entity.User;
import org.live.sys.vo.UserVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 用户的service
 *
 * Created by Mr.wang on 2016/11/29.
 */
public interface UserService extends BaseService<User,String> {

    /**
     *  根据用户账号查询用户
     * @param username 账号
     * @return
     */
    public List<User> findByUsername(String username) ;

    /**
     *  根据账号查询角色
     * @param username 账号
     * @return
     */
    public List<Role> findRolesByUsername(String username) ;

    /**
     * 获取角色值，用于shiro
     * @param username 账号
     * @return 角色值（shiro的权限值）
    */
    public List<String> getRoleValuesByUsername(String username) ;

    /**
     * 根据账号查询权限
     * @param username 账号
     * @return
     */
    public List<Permission> findPermissionsByUsername(String username) ;

    /**
     *  获取权限值，用于shiro
     * @param username 账号
     * @return 权限值集合 （shiro权限值）
     */
    public List<String> getPermissionValuesByUsername(String username) ;

    /**
     *  查询用户信息
     * @param pageable 分页请求
     * @param userVo 查询参数
     * @return
     */
    public Page<UserVo> findUsers(Pageable pageable, UserVo userVo) ;

    /**
     * 保存用户信息
     * @param userVo 用户数据载体
     *
     */
    public void saveUserInfo(UserVo userVo, String password) ;

    /**
     * 是否存在用户
     * @param username 账号
     * @return
     */
    public boolean isExistUser(String username) ;

    /**
     * 删除一组用户
     * @param ids 用户id数组
     */
    public void updateUser4isDelete(String[] ids) ;

    /**
     * 查询被删除标记标中的用户
     *
     * @param pageable 分页请求
     * @param userVo 查询参数
     * @return
     */
    public Page<UserVo> findUserisDelete(Pageable pageable, UserVo userVo) ;

    /**
     *  更新用户信息
     * @param userVo 用户信息载体
     */
    public void updateUserInfo(UserVo userVo) ;
}

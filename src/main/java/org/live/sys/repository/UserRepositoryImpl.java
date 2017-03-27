package org.live.sys.repository;

import org.live.common.base.BaseRepositoryImpl;
import org.live.common.constants.Constants;
import org.live.sys.vo.UserVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr.wang on 2016/11/30.
 */
public class UserRepositoryImpl extends BaseRepositoryImpl {

    /**
     *  查询用户信息
     * @param pageable 分页请求
     * @param userVo 查询参数
     * @param isDelete 是否有删除标记的属性
     * @return
     */
    public Page<UserVo> findUsers(Pageable pageable, UserVo userVo, boolean isDelete) {

        /**
         * sql解析：用户表左外连接用户-用户组的关联表，获取用户组的数据
         */
        final String xsql = "select u.id as id, u.username as username, u.user_type as userType, " +
                "u.name as name, u.register_time as registerTime, u.is_lock as isLock, u.last_login_time as lastLoginTime, " +
                "u.last_login_ip as lastLoginIp, g.group_name as userGroupName from sys_user u left join sys_group g on u.group_id=g.id where u.is_delete=?0" +
                "/~ and u.username like '%[username]%' ~/" +
                "/~ and u.name like '%[name]%' ~/" +
                "/~ and u.group_id='[groupId]' ~/" +
                " order by u.register_time " ;

        Map<String,Object> filter = new HashMap<String, Object>() ;
        if(userVo != null) {
            filter.put("username",userVo.getUsername()) ;
            filter.put("name",userVo.getName()) ;
            filter.put("groupId", userVo.getGroupId()) ;
        }

       String sql = this.xsqlConvertSql(xsql,filter) ;
        if(isDelete) {
            return this.findBySql(pageable, sql,new Object[]{Constants.DIC_YES},UserVo.class) ;
        }
        return this.findBySql(pageable, sql,new Object[]{Constants.DIC_NO},UserVo.class) ;

    }


}

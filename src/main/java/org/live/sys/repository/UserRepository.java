package org.live.sys.repository;


import org.live.common.base.BaseRepository;
import org.live.sys.entity.User;
import org.live.sys.vo.UserVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 *  用户持久层的接口
 *
 * Created by Mr.wang on 2016/11/29.
 */
public interface UserRepository extends BaseRepository<User, String> {

    /**
     *  根据用户账号查询用户
     * @param username 账号
     * @return
     */
    public List<User> findByUsername(String username) ;

    /**
     *  查询用户信息
     * @param pageable 分页请求
     * @param userVo 查询参数
     * @param isDelete 是否有删除标记的属性
     * @return
     */
    public Page<UserVo> findUsers(Pageable pageable, UserVo userVo, boolean isDelete) ;



}

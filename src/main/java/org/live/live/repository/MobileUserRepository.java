package org.live.live.repository;

import org.live.app.vo.SimpleUserVo;
import org.live.common.base.BaseRepository;
import org.live.live.entity.MobileUser;
import org.live.live.vo.MobileUserVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * 移动端用户数据库访问接口
 * Created by KAM on 2017/4/6.
 */
public interface MobileUserRepository extends BaseRepository<MobileUser, String> {

    Page<MobileUserVo> find(PageRequest pageRequest, Map<String, Object> filter);

    /**
     * 根据用户账号查询用户
     * @param account
     * @return
     */
    MobileUser findMobileUserByAccount(String account) ;

    /**
     * 根据用户信息，简易的
     * @param account
     * @return
     */
    @Query("select new org.live.app.vo.SimpleUserVo(u.id, u.account, u.nickname, u.headImgUrl) from MobileUser u "
           + "where u.account=:account")
    SimpleUserVo findMobileUserByAccountWithSimple(@Param("account") String account) ;
}

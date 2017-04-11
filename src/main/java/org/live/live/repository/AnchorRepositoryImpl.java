package org.live.live.repository;

import org.live.common.base.BaseRepositoryImpl;
import org.live.live.vo.AnchorVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wang on 2017/4/11.
 */
public class AnchorRepositoryImpl extends BaseRepositoryImpl {


    private final String FIND_ANCHOR_XSQL = "select new org.live.live.vo.AnchorVo(a.id, a.user.nickname, a.user.account, a.realName, a.idCard, a.user.headImgUrl, a.user.lockFlag) "
            +"from Anchor a where ("
            + "/~ or a.user.account like '%[account]%'~/"
            + "/~ or a.user.nickname like '%[nickname]%'~/"
            + "/~ or a.realName like '%[realName]%'~/"
            + ") order by a.createTime DESC";

    /**
     * 查询主播信息
     * @param page
     * @param searchStr 搜索条件
     * @return
     */
    public Page<AnchorVo> findAnchors(PageRequest page, String searchStr) {

        Map<String, Object> filter = new HashMap<>(3) ;
        filter.put("account", searchStr) ;
        filter.put("nickname", searchStr) ;
        filter.put("realName", searchStr) ;
        String hql = this.xsqlConvertHql(FIND_ANCHOR_XSQL, filter) ;
        hql = hql.replace("where ()", "").replace("( or", "(") ;
        Page<AnchorVo> pageResult = this.find(page, hql, null) ;
        return pageResult ;
    }

}

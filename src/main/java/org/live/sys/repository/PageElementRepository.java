package org.live.sys.repository;


import org.live.common.base.BaseRepository;
import org.live.sys.entity.PageElement;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *  页面元素的持久层实现
 *
 * Created by Mr.wang on 2016/12/7.
 */
public interface PageElementRepository extends BaseRepository<PageElement, String> {

    /**
     * 根据菜单id统计该菜单下页面元素的个数。
     * @param menuId 菜单id
     * @return
     */
    @Query(value="select count(*) from PageElement p where p.menu.id=:menuId")
    public long countPageElementByMenuId(@Param(value = "menuId") String menuId) ;


}

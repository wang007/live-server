package org.live.sys.repository;


import org.live.common.base.BaseRepository;
import org.live.sys.entity.Menu;
import org.live.sys.vo.MenuNode;
import org.live.sys.vo.SidebarNode;
import org.live.sys.vo.ZtreeNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Mr.wang on 2016/12/4.
 */

public interface MenuRepository extends BaseRepository<Menu, String> {

    /**
     * 查询全部菜单
     *
     * @return
     */
    @Query(value="select m from Menu m order by m.serialNo asc")
    public List<Menu> findAllMenu() ;
    /**
     * 分页查询全部菜单
     * @param pageable
     * @return
     */
    @Query(value="select m from Menu m order by m.serialNo asc")
	public Page<Menu> findAllMenu(Pageable pageable);

    
    

    /**
     * 根据父菜单节点的id，查询该父菜单节点下的子菜单
     * @param parentId
     * @return
     */
    @Query(value="select new org.live.sys.vo.MenuNode(m.id, m.menuName,m.parent.id,m.serialNo,m.menuType) from Menu m where m.parent.id=:parentId order by m.serialNo asc")
    public List<MenuNode> findMenuNodeByParentId(@Param("parentId") String parentId) ;

    /**
     * 查询所有的菜单节点
     * @return
     */
    @Query(value="select distinct new org.live.sys.vo.MenuNode(m.id, m.menuName,m.parent.id,m.serialNo) from Menu m order by m.serialNo asc")
    public List<MenuNode> findAllMenuNode() ;

    /**
     * 根据父菜单id，统计子菜单的个数
     * @return
     */
    @Query(value="select count(*) from Menu m where m.parent.id=:parentId")
    public long countChildMenuByParentId(@Param(value = "parentId") String parentId) ;

    /**
     *  查询所有菜单，不包括根结点。。用于ztree插件，simple-data的方式
     */
    @Query(value="select distinct new org.live.sys.vo.ZtreeNode(m.id, m.parent.id, m.menuName) from Menu m where m.id !='root' order by m.serialNo asc")
    public List<ZtreeNode> findAllMenuNoParent4ztree() ;

    /**
     * 根据角色id，查询与该角色关联的菜单id
     * @param roleId
     * @return
     */
    @Query(value="select distinct m.id from RolePermission pr,Menu m, Permission p where pr.permission.id=p.id and p.id=m.permission.id and pr.role.id=:roleId")
    public List<String> findMenuIdByRoleId(@Param(value = "roleId") String roleId) ;

    /**
     * 根据角色id，查询与该角色关联的菜单
     * @param roleId
     * @return
     */
    @Query(value="select distinct m from RolePermission rp, Menu m, Permission p where rp.permission.id=p.id and p.id=m.permission.id and rp.role.id=:roleId")
    public List<Menu> findMenuByRoleId(@Param(value = "roleId") String roleId) ;

    /**
     * 通过用户id查询菜单，封装到SidebarNode，
     * @param userId 用户id
     * @return
     */
    public List<SidebarNode> findMenu4SidebarNodeByUserId(String userId) ;

    /**
     * 查询单个菜单，封装到SidebarNode中
     * @param menuId
     * @return
     */
    @Query(value="select new org.live.sys.vo.SidebarNode(m.id, m.menuName, m.menuUrl, m.menuIcon, m.serialNo, m.parent.id) from Menu m where m.id=:menuId")
    public SidebarNode findMenu4sidebarNodeByMenuId(@Param(value = "menuId") String menuId) ;



}

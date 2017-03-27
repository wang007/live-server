package org.live.sys.service;


import org.live.common.base.BaseService;
import org.live.sys.entity.Menu;
import org.live.sys.vo.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Mr.wang on 2016/12/4.
 */
public interface MenuService extends BaseService<Menu, String> {


    /**
     * 查询所有菜单
     * @return
     */
    public List<Menu> findAllMenu() ;

    /**
     * 分页查询所有菜单
     * @param pageable
     * @return
     */

    public Page<Menu> findAllMenu(Pageable pageable);
    /**
     * 获取已经拼装好的全部菜单
     *
     * @return menu 系统的根目录
     */
    public Menu getAlreadyAssembleMenuTree() ;

    /**
     * 获取已经拼装好的全部菜单节点
     * @return
     */
    public MenuNode getAlreadyAssembleMenuNodeTree() ;

    /**
     * 根据父节点id，加载该父节点下的直接子节点（不包括子孙节点）
     * 用于treeview插件
     * @param parentId
     * @return
     */
    public List<TreeViewNode> loadTreeViewNodesByParentId(String parentId) ;

    /**
     * 根据父节点id，查询该父节点的所有子节点（包括子孙节点）
     * @param parentId 父菜单
     * @param rootMenuNode 全局菜单数
     * @param dumpMenuNode 临时保存子菜单的list
     * @return
     */
    public void findChildNodesByParentId(String parentId, MenuNode rootMenuNode, MenuNode dumpMenuNode) ;

    /**
     * 保存菜单信息
     * @param menuVo  含有菜单信息的数据
     */
    public void saveMenuInfo(MenuVo menuVo) ;

    /**
     * 更新菜单信息
     * @param menuVo
     */
    public void updateMenuInfo(MenuVo menuVo) ;

    /**
     * 删除菜单
     * @param menuId 菜单id
     */
    public void deleteMenuById(String menuId) ;

    /**
     *  查询菜单节点，以ztree插件所需的数据格式封装节点
     * @param roleId
     * @return
     */
    public List<ZtreeNode> getZtreeNodesByRoles(String roleId) ;

    /**
     * 查询所有的菜单节点
     * @return
     */
    public List<ZtreeNode> getAllZtreeNodes() ;

    /**
     * 保存角色与菜单的关联关系
     * @param roleId
     * @param menuIds
     */
    public void saveMenuRoleRel(String roleId, String[] menuIds) ;

    /**
     * 通过用户id查询菜单，封装到SidebarNode，
     * @param userId 用户id
     * @return
     */
    public List<SidebarNode> findSidebarNodesByUserId(String userId) ;

}

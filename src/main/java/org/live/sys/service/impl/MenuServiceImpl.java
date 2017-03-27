package org.live.sys.service.impl;


import org.apache.commons.lang3.StringUtils;
import org.live.common.base.BaseRepository;
import org.live.common.base.BaseServiceImpl;
import org.live.common.constants.SysConstants;
import org.live.common.exception.ServiceException;
import org.live.common.utils.CopyPropertiesUtils;
import org.live.sys.entity.Menu;
import org.live.sys.entity.Permission;
import org.live.sys.entity.Role;
import org.live.sys.entity.RolePermission;
import org.live.sys.repository.*;
import org.live.sys.service.MenuService;
import org.live.sys.support.CombinableUtils;
import org.live.sys.utils.MenuTreeUtils;
import org.live.sys.vo.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mr.wang on 2016/12/4.
 */
@Transactional(readOnly = true)
@Service("menuService")
public class MenuServiceImpl extends BaseServiceImpl<Menu, String> implements MenuService {

    @Resource
    private MenuRepository menuRepo ;

    @Resource
    private PermissionRepository permissionRepo ;

    @Resource
    private PageElementRepository elementRepo ;

    @Resource
    private RolePermissionRepository rolePermissionRepo ;

    @Resource
    private RoleRepository roleRepo ;

    @Override
    protected BaseRepository<Menu, String> getRepository() {
        return this.menuRepo ;
    }

    @Override
    public List<Menu> findAllMenu() {
        return this.menuRepo.findAllMenu() ;
    }

    @Override
    public Page<Menu> findAllMenu(Pageable pageable) {
        return this.menuRepo.findAllMenu(pageable) ;
    }


    @Override
    public Menu getAlreadyAssembleMenuTree() {

        List<Menu> allMenu = this.menuRepo.findAllMenu();//查询全部菜单.
        List<Menu> alreadyAssembleMenus = (List<Menu>) CombinableUtils.executeCombination(null,allMenu) ;   //执行菜单拼装
        return alreadyAssembleMenus != null && alreadyAssembleMenus.size() > 0 ? alreadyAssembleMenus.get(0): new Menu() ; //根目录
    }

    @Override
    public MenuNode getAlreadyAssembleMenuNodeTree() {
        List<MenuNode> allMenuNode = this.menuRepo.findAllMenuNode();
        List<MenuNode> alreadyAssembleMenuNodes = (List<MenuNode>)CombinableUtils.executeCombination(null, allMenuNode);//执行菜单节点的拼装.
        return alreadyAssembleMenuNodes != null && alreadyAssembleMenuNodes.size() > 0 ? alreadyAssembleMenuNodes.get(0) : new MenuNode();
    }

    @Override
    public List<TreeViewNode> loadTreeViewNodesByParentId(String parentId) {

        MenuNode rootMenNode = this.getAlreadyAssembleMenuNodeTree() ;
        MenuNode dumpMenuNode = new MenuNode() ;
        this.findChildNodesByParentId(parentId, rootMenNode, dumpMenuNode) ;   //查询该父节点下的所有子节点（包括子孙节点）
        List<TreeViewNode> treeViewNodes = MenuTreeUtils.toTreeViewLayout(dumpMenuNode.getChildMenuNodes());
        return treeViewNodes ;
    }

    @Override
    public void findChildNodesByParentId(String parentId, MenuNode rootMenuNode, MenuNode dumpMenuNode) {

        if(StringUtils.equals(rootMenuNode.getId(),parentId)) { //当前id = parentid，就把当前菜单的子菜单保存到临时list集合中
            dumpMenuNode.setChildMenuNodes( rootMenuNode.getChildMenuNodes()) ;
        }
        List<MenuNode> childMenuNodes = rootMenuNode.getChildMenuNodes();
        if(childMenuNodes != null) {    //继续递归查找子菜单，
            for(MenuNode childNode: childMenuNodes) {
                findChildNodesByParentId(parentId, childNode, dumpMenuNode) ;
            }
        }
    }

    @Transactional(rollbackFor = RuntimeException.class,readOnly = false)
    @Override
    public void saveMenuInfo(MenuVo menuVo) {

        //1.保存为同级菜单，2.保存为子级菜单
        int saveType = menuVo.getSaveType() ;
        if(menuVo.getReferenceId() == null || "".equals(menuVo.getReferenceId())) {
            throw new ServiceException("参考菜单不能为空") ;
        }
        Permission permission = new Permission() ;  //权限
        permission.setPermissionType(SysConstants.DIC_PERMISSIONTYPE_MENU) ;    //权限类型，页面权限
        Menu menu = new Menu() ;    //菜单
        CopyPropertiesUtils.copyPropertiesIgnoreNull(permission, menuVo) ;
        CopyPropertiesUtils.copyPropertiesIgnoreNull(menu, menuVo) ;

        //查找参考菜单
        Menu referenceMenu = this.findOne(menuVo.getReferenceId()) ;
        menu.setPermission(permission) ;    //
        menu.setMenuType(SysConstants.MENU_NODE_TYPE_ITEM) ;
        this.permissionRepo.save(permission) ;  //先持久化permission
        if(referenceMenu == null) {
            throw new ServiceException("参考菜单不能为空") ;
        }
        if(saveType == 2) { //保存为子级菜单
            if(!SysConstants.MENU_NODE_TYPE_FOLDER.equals(referenceMenu.getMenuType())) {   //父菜单的类型不是folder
                referenceMenu.setMenuType(SysConstants.MENU_NODE_TYPE_FOLDER) ;
                referenceMenu = this.save(referenceMenu) ;
            }
            menu.setParent(referenceMenu);
        } else {    //保存为同级
            menu.setParent(referenceMenu.getParent()) ;
        }
        this.save(menu) ;
    }

    @Transactional(rollbackFor = RuntimeException.class, readOnly = false)
    @Override
    public void updateMenuInfo(MenuVo menuVo) {

        if(menuVo.getId() == null || "".equals(menuVo.getId())) {
            throw new ServiceException("菜单未找到！") ;
        }
         Menu menuPersistent = this.findOne(menuVo.getId());
         if(menuPersistent == null) {
             throw new ServiceException("菜单未找到！") ;
         }
         CopyPropertiesUtils.copyPropertiesIgnoreNull(menuPersistent, menuVo) ; //修改菜单信息
        Permission permission = menuPersistent.getPermission();
        if(permission != null) {
            permission.setIsEnable(menuVo.getIsEnable()) ;
            permission.setPermissionValue(menuVo.getPermissionValue()) ;
        } else {    //菜单不存在权限
            permission = new Permission() ;
            permission.setIsEnable(menuVo.getIsEnable())  ;
            permission.setPermissionValue(menuVo.getPermissionValue()) ;
            permission.setPermissionType(SysConstants.DIC_PERMISSIONTYPE_MENU) ;
            this.permissionRepo.save(permission) ;
            menuPersistent.setPermission(permission) ;
        }

        this.menuRepo.save(menuPersistent)  ;

    }

    @Transactional(rollbackFor = RuntimeException.class, readOnly = false)
    @Override
    public void deleteMenuById(String menuId) {

        final long childLength = this.menuRepo.countChildMenuByParentId(menuId);//统计该菜单下的子菜单
        if(childLength > 0) {
            throw new ServiceException("删除失败，请先删除该菜单下的子菜单") ;
        }
        long elementLength = this.elementRepo.countPageElementByMenuId(menuId);//统计该菜单下的子菜单
        if(elementLength > 0) {
            throw new ServiceException("删除失败，请删除该菜单下的页面元素") ;
        }
        Menu menuPersistent = this.findOne(menuId) ;
        Menu parent = menuPersistent.getParent() ;
        Permission permission = menuPersistent.getPermission() ;
        this.delete(menuPersistent) ;
        if(parent != null) {    //设置父菜单的菜单类型
            long parentChildLength = this.menuRepo.countChildMenuByParentId(parent.getId()) ; //父菜单的子菜单数量
            if(parentChildLength <1) {
                parent.setMenuType(SysConstants.MENU_NODE_TYPE_ITEM) ;
                this.save(parent) ;
            }
        }
        if(permission != null) {
            this.permissionRepo.delete(permission) ;
        }
    }

    @Override
    public List<ZtreeNode> getZtreeNodesByRoles(String roleId) {
        List<ZtreeNode> ztreeNodes = this.menuRepo.findAllMenuNoParent4ztree() ;
        List<String> menuIds = this.menuRepo.findMenuIdByRoleId(roleId) ;   //该角色所拥有的菜单id
        if(ztreeNodes != null && menuIds != null) {
            for(ZtreeNode node: ztreeNodes) {
                if(menuIds.contains(node.getId())) {    //角色与该菜单有关联。
                    node.setChecked(true) ;
                }
            }
        }
        return ztreeNodes ;
    }

    @Override
    public List<ZtreeNode> getAllZtreeNodes() {

        return this.menuRepo.findAllMenuNoParent4ztree() ;
    }

    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    @Override
    public void saveMenuRoleRel(String roleId, String[] menuIds) {
        if(menuIds != null) {
            Role role = this.roleRepo.findOne(roleId);
            List<String> needsHandleMenuIds = Arrays.asList(menuIds) ;  //客户端发过来的menuIds，需要被处理的
            List<String> alreadyExistMenuIds = this.menuRepo.findMenuIdByRoleId(roleId) ;   //该角色中已经存在的与菜单的关联关系
            List<String> needDeleteMenuIds = new ArrayList<String>() ;  // needDeleteRoleMenuRel是暂存需要删除的MenuRoleRel
            List<String> alreadyHaveMenuIds = new ArrayList<String>() ;     //已经存在数据库的菜单id，在needsHandleMenuIds也有，即，这集合中的数据，是不需要处理的
            if(alreadyExistMenuIds != null) {
                for (String alreadyExistMenuId: alreadyExistMenuIds) {
                    if(!needsHandleMenuIds.contains(alreadyExistMenuId)) {  //数据库中存在，客户端不存在，那么删除
                        needDeleteMenuIds.add(alreadyExistMenuId) ;
                    }
                    alreadyHaveMenuIds.add(alreadyExistMenuId) ;
                }
            }
            for(String needDeleteMenuId: needDeleteMenuIds) {   //删除关联关系
                rolePermissionRepo.deleteRolePermissionByIds(roleId, needDeleteMenuId);
            }
            for(String needHandleMenuId: needsHandleMenuIds) {   //添加关联关系
                if(!alreadyHaveMenuIds.contains(needHandleMenuId)) {     //需要处理的
                    Menu menu = this.findOne(needHandleMenuId) ;
                    if(menu != null) {
                        this.rolePermissionRepo.save(new RolePermission(role, menu.getPermission())) ;
                    }
                }

            }
        } else {
            this.rolePermissionRepo.deleteRolePermissionByRoleId(roleId) ;

        }

    }


    private static final String rootMenuNode = "root" ;
    @Override
    public List<SidebarNode> findSidebarNodesByUserId(String userId) {
        SidebarNode rootSidebarNode = this.menuRepo.findMenu4sidebarNodeByMenuId(rootMenuNode) ;
        List<SidebarNode> sidebarNodes = this.menuRepo.findMenu4SidebarNodeByUserId(userId) ;
        List<SidebarNode> alreadyAssembleSidebarNodes = (List<SidebarNode>) CombinableUtils.executeCombination(rootSidebarNode, sidebarNodes);

        return alreadyAssembleSidebarNodes;
    }

}

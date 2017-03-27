package org.live.sys.controller;


import org.live.common.constants.Constants;
import org.live.common.exception.ServiceException;
import org.live.common.page.JqGridModel;
import org.live.common.page.PageUtils;
import org.live.common.response.ResponseModel;
import org.live.common.response.SimpleResponseModel;
import org.live.common.systemlog.LogLevel;
import org.live.common.systemlog.OperateType;
import org.live.common.systemlog.SystemLog;
import org.live.sys.entity.Menu;
import org.live.sys.entity.Permission;
import org.live.sys.entity.Role;
import org.live.sys.service.MenuService;
import org.live.sys.service.RoleService;
import org.live.sys.vo.MenuVo;
import org.live.sys.vo.SidebarNode;
import org.live.sys.vo.TreeViewNode;
import org.live.sys.vo.ZtreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Mr.wang on 2016/12/4.
 */
@Controller
@RequestMapping("/sys")
public class MenuController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuController.class) ;

    @Resource
    private MenuService menuService ;

    @Resource
    private RoleService roleService ;

    /**
     * 进入菜单管理页面
     * @return
     */
    @SystemLog(description = "进入菜单管理",logLevel = LogLevel.WARN)
    @RequestMapping(value="/menu/page",method = RequestMethod.GET)
    public String toMenuPage() {
        return "sys/menu" ;
    }
    
    @SystemLog(description="进入菜单列表管理")
    @RequestMapping(value="/menu/list",method = RequestMethod.GET)
    public String toMenuPageList(){
    	return "sys/menu_list";
    }
/**
 * jqgrid插件方式查询菜单
 * @param request
 * @param menu
 * @return
 */
    @SystemLog(description = "查询菜单", logLevel = LogLevel.INFO, operateType = OperateType.QUERY)
    @RequestMapping(value="/menu",method = RequestMethod.GET)
    @ResponseBody
    public JqGridModel<Menu> findMenus(HttpServletRequest request, Menu menu){
        PageRequest pageRequest = PageUtils.getPage4JqGrid(request) ;
        Page<Menu> page = this.menuService.findAllMenu(pageRequest);
        JqGridModel<Menu> model = PageUtils.pageConvertJqGrid(page);

        return model ;
    }

    @RequestMapping(value="/menu/treeview/{parentId}",method= RequestMethod.GET)
    @ResponseBody
    public ResponseModel<Object> loadMenuNodForTreeviewByParentId(@PathVariable String parentId) {
        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            List<TreeViewNode> treeViewNodes = this.menuService.loadTreeViewNodesByParentId(parentId) ;
            model.setData(treeViewNodes).success("加载菜单节点成功！") ;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e) ;
            model.error("加载菜单节点失败！") ;

        }
        return model ;
    }

    /**
     * 获取菜单信息
     * @param menuId
     * @return
     */
    @RequestMapping(value="/menu/{menuId}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<Object>  getMenuInfo(@PathVariable String menuId) {

        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            Menu menu = this.menuService.findOne(menuId);
            model.setData(menu).success() ;
        } catch (Exception e) {
            model.error() ;
            LOGGER.error(e.getMessage(),e) ;
        }
        return model ;
    }

    /**
     * 保存菜单
     * @param menuVo
     * @return
     */
    @SystemLog(description = "添加菜单",operateType = OperateType.ADD,logLevel = LogLevel.WARN)
    @ResponseBody
    @RequestMapping(value="/menu",method = RequestMethod.POST)
    public ResponseModel<Object> saveMenuInfo(MenuVo menuVo) {

        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            this.menuService.saveMenuInfo(menuVo) ;
            model.success("保存成功") ;
        } catch (ServiceException e){
            model.error(e.getMessage()) ;
            LOGGER.info(e.getMessage(),e) ;
        }catch (Exception e) {
            model.error("保存失败") ;
            LOGGER.error(e.getMessage(),e) ;
        }
        return model ;
    }

    /**
     * 修改信息
     * @param menuVo
     * @return
     */
    @SystemLog(description = "修改菜单信息",operateType = OperateType.UPDATE,logLevel = LogLevel.WARN)
    @RequestMapping(value = "/menu",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel<Object> updateMenuInfo(MenuVo menuVo) {
        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            this.menuService.updateMenuInfo(menuVo) ;
            model.success("修改成功！") ;
        } catch (ServiceException e) {
            model.error(e.getMessage()) ;
        }catch (Exception e) {
            LOGGER.error(e.getMessage(),e) ;
            model.error("修改失败！") ;
        }
        return model ;
    }

    /**
     * 删除菜单
     * @param menuId 菜单id
     * @return
     */
    @SystemLog(description = "删除菜单",operateType = OperateType.DELETE,logLevel = LogLevel.ERROR)
    @RequestMapping(value = "/menu/{menuId}",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseModel<Object> deleteMenuById(@PathVariable String menuId) {
        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            this.menuService.deleteMenuById(menuId) ;
            model.success("删除成功！") ;
        } catch (ServiceException e) {
            model.error(e.getMessage()) ;
        } catch (Exception e){
            model.error("删除失败！") ;
            LOGGER.error(e.getMessage(),e) ;
        }
        return model ;
    }

    /**
     * 进入角色跟菜单的管理界面
     * @return
     */
    @SystemLog(description = "进入角色关联菜单的页面",logLevel = LogLevel.INFO,operateType = OperateType.QUERY)
    @RequestMapping(value="/roleMenu",method = RequestMethod.GET)
    public String toRoleMenuPage(String roleId, Model model) {

        List<Role> roles = roleService.findAll() ;
        model.addAttribute("rows", roles) ;
        model.addAttribute("selectRoleId", roleId) ;
        return "sys/role_menu" ;
    }


    /**
     * 查询菜单树，用于ztree插件，simple-data的方式
     * @return
     */
    @RequestMapping(value="/ztree",method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<Object> findMenuTree4ztree() {
        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            List<ZtreeNode> ztreeNodes = this.menuService.getAllZtreeNodes() ;
            model.setData(ztreeNodes) ;
            model.success() ;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e) ;
            model.error() ;
        }
        return model ;
    }

    /**
     * 查询菜单树，根据角色id
     * 用于ztree插件，simple-data的方式
     * @param roleId 角色id
     * @return
     */
    @RequestMapping(value="/ztree/role/{roleId}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<Object> findMenuTree4ztreeByRoleId(@PathVariable String roleId) {

         ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
         try {
             List<ZtreeNode> ztreeNodes = this.menuService.getZtreeNodesByRoles(roleId) ;
             model.setData(ztreeNodes) ;
             model.success() ;
         } catch (Exception e) {
             LOGGER.error(e.getMessage(), e) ;
             model.error() ;
         }
         return model ;
    }

    /**
     * 保存角色与菜单之间的关系
     * @param menuIds
     * @return
     */
    @SystemLog(description = "保存角色与菜单之间的关系",operateType = OperateType.UPDATE, logLevel = LogLevel.WARN)
    @RequestMapping(value="/ztree/role/{roleId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel<Object> saveMenuRoleRel(@PathVariable String roleId, String[] menuIds) {

        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            this.menuService.saveMenuRoleRel(roleId, menuIds); ;
            model.success("保存成功！") ;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e) ;
            model.error("保存失败！") ;
        }
        return model ;
    }

    @RequestMapping(value="/test/{userId}")
    @ResponseBody
    public ResponseModel<Object> test(@PathVariable String userId) {

        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            List<SidebarNode> sidebarNodes= this.menuService.findSidebarNodesByUserId(userId);
            model.setData(sidebarNodes).success() ;

        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e) ;
            model.error() ;
        }
        return model ;
    }
/**
 * 更改是否可用
 * @param roleId
 * @return
 */
    @SystemLog(description = "更改菜单是否可用",operateType = OperateType.UPDATE,logLevel = LogLevel.WARN)
    @RequestMapping(value="/menu/{menuId}",method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseModel<Object> updateMenuisEnable(@PathVariable String menuId) {

        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            Menu menu = this.menuService.findOne(menuId) ;
            Permission permission=menu.getPermission();
            if(permission != null) {
            	permission.setIsEnable(permission.getIsEnable() == Constants.DIC_YES ? Constants.DIC_NO : Constants.DIC_YES) ;
            	menu.setPermission(permission);
            	this.menuService.save(menu) ;
            }
            model.success() ;
        } catch(Exception e) {
            LOGGER.error(e.getMessage(),e) ;
            model.error() ;
        }
        return model ;
    }



}

package org.live.sys.controller;


import org.live.common.constants.Constants;
import org.live.common.page.JqGridModel;
import org.live.common.page.PageUtils;
import org.live.common.response.ResponseModel;
import org.live.common.response.SimpleResponseModel;
import org.live.common.systemlog.LogLevel;
import org.live.common.systemlog.OperateType;
import org.live.common.systemlog.SystemLog;
import org.live.sys.entity.Role;
import org.live.sys.service.GroupService;
import org.live.sys.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
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
 *
 * Created by Mr.wang on 2016/12/3.
 */
@Controller
@RequestMapping("/sys")
public class RoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class) ;

    @Resource
    private RoleService roleService ;
    
    @Resource
    private GroupService GorupService ;
    
    
    /**
     * 跳转到角色的界面
     * @param model
     * @return
     */
    @SystemLog(description = "进入角色的界面",logLevel = LogLevel.WARN)
    @RequestMapping(value="/role/page",method = RequestMethod.GET)
    public String toRolePage(Model model) {
        List<Role> roles = this.roleService.findAll();  //查询角色
        model.addAttribute("roles",roles) ;
        return "sys/role" ;
    }

    /**
     * 生成serialNo
     * @return
     */
    @RequestMapping(value="/role/getSerialNo", method = RequestMethod.GET)
    @ResponseBody
    public int createSerialNo(){ 
    	return this.roleService.createSerialNo();
    }
    
    /**
	 * 校验序号是否已经存在
	 * @param serialNo
	 * @return
	 */
	@RequestMapping(value="/role/validation/{serialNo}",method = RequestMethod.GET)
	@ResponseBody
    public boolean isExistSerialNo(@PathVariable int serialNo){
		boolean passFlag =false;
		try{
			passFlag =!this.roleService.isExistSerialNo(serialNo);
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			passFlag =false;
		}
		return passFlag;
    }

    /**
     *  更改角色是否可用
     * @param roleId 角色id
     * @return
     */
    @SystemLog(description = "更改角色是否可用",operateType = OperateType.UPDATE,logLevel = LogLevel.WARN)
    @RequestMapping(value="/role/{roleId}",method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseModel<Object> updateRoleisEnable(@PathVariable String roleId) {

        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            Role role = this.roleService.findOne(roleId) ;
            if(role != null) {
                role.setIsEnable(role.getIsEnable() == Constants.DIC_YES ? Constants.DIC_NO : Constants.DIC_YES) ;
                this.roleService.save(role) ;
            }
            model.success() ;
        } catch(Exception e) {
            LOGGER.error(e.getMessage(),e) ;
            model.error() ;
        }
        return model ;
    }

    /**
     * 保存角色
     *
     * @param role
     * @return
     */
    @SystemLog(description = "保存角色",operateType = OperateType.ADD,logLevel = LogLevel.WARN)
    @RequestMapping(value="/role",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel<Object> saveRole(Role role) {

        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            this.roleService.save(role) ;
            model.success("添加成功！") ;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e) ;
            model.error("添加失败！") ;
        }
        return model ;
    }

    /**
     * 查询角色
     * @param request
     * @param role
     * @return
     */
    @RequestMapping(value = "/role", method = RequestMethod.GET)
    @ResponseBody
    public JqGridModel<Role> findRoles(HttpServletRequest request, Role role) {
        try {
            PageRequest pageRequest = PageUtils.getPage4JqGrid(request) ;
            Page<Role> rolePage = this.roleService.findRoles(pageRequest, role) ;
            JqGridModel<Role> model = PageUtils.pageConvertJqGrid(rolePage) ;
            return model ;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null ;
    }

    /**
     * 查询角色
     * @param roleId
     * @return
     */
    @RequestMapping(value="/role/{roleId}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<Object> findRole(@PathVariable String roleId) {

        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try{
            Role role = this.roleService.findOne(roleId);
            model.setData(role).success() ;
        } catch(Exception e) {
            LOGGER.error(e.getMessage(),e) ;
            model.error() ;
        }

        return model ;
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @SystemLog(description = "修改角色",operateType = OperateType.UPDATE,logLevel = LogLevel.WARN)
    @RequestMapping(value="/role",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel<Object> updateRole(Role role) {
        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            if(role != null) {
                Role roleInPersistent = this.roleService.findOne(role.getId());
                role.setVersion(roleInPersistent.getVersion());
                this.roleService.save(role) ;
                model.success("修改成功！") ;
            } else {
                model.error("修改失败！") ;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e) ;
            model.error("修改失败！") ;
        }
        return model ;
    }


    /**
     * 删除角色
     * @param roleId
     * @return
     */
    @SystemLog(description = "删除角色",operateType = OperateType.DELETE,logLevel = LogLevel.ERROR)
    @RequestMapping(value="/role/{roleId}",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseModel<Object> deleteRoleById(@PathVariable String roleId) {
        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            this.roleService.delete(roleId) ;
            model.success("删除成功") ;

        } catch (DataIntegrityViolationException e) {
            model.error("删除失败，请先删除与角色有关的数据！") ;
        } catch (Exception e) {
            model.error("删除失败") ;
            LOGGER.error(e.getMessage(),e) ;
        }
        return model ;
    }






}

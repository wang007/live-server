package org.live.sys.controller;


import org.apache.commons.beanutils.PropertyUtils;
import org.live.common.constants.Constants;
import org.live.common.page.JqGridModel;
import org.live.common.page.PageUtils;
import org.live.common.response.ResponseModel;
import org.live.common.response.SimpleResponseModel;
import org.live.common.systemlog.LogLevel;
import org.live.common.systemlog.OperateType;
import org.live.common.systemlog.SystemLog;
import org.live.sys.entity.Group;
import org.live.sys.entity.Role;
import org.live.sys.service.GroupService;
import org.live.sys.service.RoleService;
import org.live.sys.vo.FindGroupVo;
import org.live.sys.vo.GroupInfo;
import org.live.sys.vo.GroupVo;
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
 * Created by Mr.wang on 2016/12/2.
 */
@Controller
@RequestMapping("/sys")
public class GroupController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class) ;

    @Resource
    private GroupService GorupService ;

    @Resource
    private RoleService roleService ;

    /**
     * 跳转到用户组的界面
     * @param model
     * @return
     */
    @SystemLog(description = "进入用户组的界面",logLevel = LogLevel.WARN)
    @RequestMapping(value="/group/page",method = RequestMethod.GET)
    public String toGroupRolePage(Model model) {
        List<Role> roles = this.roleService.findAll();  //查询角色
        model.addAttribute("roles",roles) ;
        return "sys/group" ;
    }

    /**
     * 查询用户组
     * @param group
     * @return
     */
    @RequestMapping(value="/group",method = RequestMethod.GET)
    @ResponseBody
    public JqGridModel<FindGroupVo> findGroups(Group group, HttpServletRequest request) {
        try {
            PageRequest pageRequest = PageUtils.getPage4JqGrid(request) ;
            Page<FindGroupVo> pageGroupVos = this.GorupService.findGroups(pageRequest, group) ;
            JqGridModel<FindGroupVo> model = PageUtils.pageConvertJqGrid(pageGroupVos) ;
            return model ;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e) ;
        }
        return null ;
    }

    /**
     * 生成serialNo
     * @return
     */
    @RequestMapping(value="/group/getSerialNo", method = RequestMethod.GET)
    @ResponseBody
    public int createSerialNo(){
    	return this.GorupService.createSerialNo();
    }
    
    /**
	 * 校验序号是否已经存在
	 * @param serialNo
	 * @return
	 */
	@RequestMapping(value="/group/validation/{serialNo}",method = RequestMethod.GET)
	@ResponseBody
    public boolean isExistSerialNo(@PathVariable int serialNo){
		boolean passFlag =false;
		try{
			passFlag =!this.GorupService.isExistSerialNo(serialNo);
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			passFlag =false;
		}
		return passFlag;
    }

    /**
     * 保存用户组
     * @param group 用户组
     * @param roleIds 角色id数组
     * @return
     */
    @SystemLog(description = "添加用户组",operateType = OperateType.ADD,logLevel = LogLevel.WARN)
    @RequestMapping(value="/group",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel<Object> saveGroup(Group group, String[] roleIds) {

        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
          this.GorupService.saveGroupInfo(group, roleIds);
          model.success("保存成功！") ;

        } catch(Exception e) {
            LOGGER.error("保存用户组失败",e);
            model.error("保存失败！");
        }

        return model ;
    }


    @SystemLog(description = "更改用户组是否可用")
    @RequestMapping(value="/group/{groupId}",method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseModel<Object> updateGroupisEnable(@PathVariable String groupId) {

        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            Group group = this.GorupService.findOne(groupId);
            if(group != null) {
                group.setIsEnable(group.getIsEnable() == Constants.DIC_YES ? Constants.DIC_NO : Constants.DIC_YES) ;
            }
            this.GorupService.save(group) ;
            model.success() ;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e) ;
            model.error() ;
        }
        return model ;
    }

    /**
     * 查询用户组信息
     * @param groupId 用户组id
     * @return
     */
    @RequestMapping(value="/group/{groupId}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<Object> findGroupInfoByGroupId(@PathVariable String groupId) {

        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            GroupInfo info = this.GorupService.findGorupInfoByGroupId(groupId) ;
            GroupVo vo = new GroupVo() ;
            if(info.getGroup() != null) {   //属性复制
                PropertyUtils.copyProperties(vo,info.getGroup()) ;
            }
            List<Role> roles = info.getRoles();
            if(roles != null) {
                int length = roles.size() ;
                String[] roleIds = new String[length] ;
                for (int i = 0; i < length ; i++) {
                    roleIds[i] = roles.get(i).getId() ;
                }
                vo.setRoleIds(roleIds) ;
            }
            model.setData(vo).success() ;

        } catch (Exception e) {
            model.error() ;
            LOGGER.error(e.getMessage(),e) ;
        }
        return model ;
    }

    /**
     *
     * 更新用户组信息
     * @param groupVo 用户组信息
     * @return
     */
    @SystemLog(description = "更新用户组信息",operateType = OperateType.UPDATE,logLevel = LogLevel.WARN)
    @RequestMapping(value="/group",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel<Object> updateGroup(GroupVo groupVo) {
        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            this.GorupService.updateGroupInfo(groupVo) ;
            model.success("修改成功！") ;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e) ;
            model.error("修改失败！") ;

        }
        return model ;
    }

    @SystemLog(description = "删除用户组信息",operateType = OperateType.DELETE,logLevel = LogLevel.ERROR)
    @RequestMapping(value="/group/{groupId}",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseModel<Object> deleteGroupById(@PathVariable String groupId) {

        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            this.GorupService.delete(groupId) ;
            model.success("删除成功！") ;
        } catch (DataIntegrityViolationException e) {
            model.error("删除失败，请先删除与用户组有关的数据！") ;
        } catch(Exception e) {
            LOGGER.error(e.getMessage(),e) ;
            model.error("删除失败！") ;

        }
        return model ;
    }

}

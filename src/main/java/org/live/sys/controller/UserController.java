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
import org.live.common.utils.EncryptUtils;
import org.live.sys.entity.Group;
import org.live.sys.entity.User;
import org.live.sys.service.GroupService;
import org.live.sys.service.UserService;
import org.live.sys.vo.UserVo;
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
 *  用户的controller
 * Created by Mr.wang on 2016/11/29.
 */
@Controller
@RequestMapping("/sys")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class) ;

    @Resource
    private UserService userService ;

    @Resource
    private GroupService groupService ;

    /**
     * 跳转到用户管理界面
     *
     * @return
     */
    @RequestMapping(value="/user/page",method = RequestMethod.GET)
    @SystemLog(description = "进入用户管理界面",logLevel = LogLevel.WARN)
    public String toUserPage(Model model) {

        List<Group> groups = groupService.findAll() ;
        model.addAttribute("rows", groups) ;

        return "sys/user" ;
    }

    @RequestMapping(value="/user/deletePage",method = RequestMethod.GET)
    @SystemLog(description = "进入被删除用户管理界面")
    public String toDeleteUserPage(Model model) {

        List<Group> groups = groupService.findAll() ;
        model.addAttribute("rows",groups) ;
        return "sys/delete_user" ;
    }

    /**
     *  新建一个用户
     * @param userVo
     * @return
     */
    @SystemLog(description = "添加用户",logLevel = LogLevel.WARN, operateType = OperateType.ADD)
    @RequestMapping(value="/user",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel<String> saveUser(UserVo userVo, String password) {

        ResponseModel<String> model = new SimpleResponseModel<String>() ;
        try {
            this.userService.saveUserInfo(userVo, password) ;
            model.success() ;
        } catch (Exception e) {
            LOGGER.error("添加用户发生异常",e) ;
            model.error() ;
        }

        return model ;
    }

    @RequestMapping(value="/user/{userId}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseModel<Object> findUserById(@PathVariable String userId) {
        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            User user = this.userService.findOne(userId) ;
            if(user != null) {
                UserVo vo = new UserVo() ;
                PropertyUtils.copyProperties(vo,user) ;
                vo.setGroupId(user.getGroup() != null ? user.getGroup().getId() : null) ;
                model.setData(vo);
            }
            model.success() ;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e) ;
            model.error() ;
        }
        return model ;
    }




    /**
     *  jqgrid插件的方式查询用户信息
     * @param request
     * @param userVo
     * @return
     */
    @SystemLog(description = "查询用户", logLevel = LogLevel.INFO, operateType = OperateType.QUERY)
    @RequestMapping(value="/user",method = RequestMethod.GET)
    @ResponseBody
    public JqGridModel<UserVo> findUsers(HttpServletRequest request, UserVo userVo) {

        PageRequest pageRequest = PageUtils.getPage4JqGrid(request) ;
        Page<UserVo> page = this.userService.findUsers(pageRequest, userVo);
        JqGridModel<UserVo> model = PageUtils.pageConvertJqGrid(page);

        return model ;
    }

    @SystemLog(description = "更新用户信息",logLevel = LogLevel.WARN,operateType = OperateType.UPDATE)
    @RequestMapping(value="/user",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseModel<Object> updateUser(UserVo userVo) {
        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            this.userService.updateUserInfo(userVo) ;
            model.success("更新成功！") ;

        } catch (Exception e) {
            LOGGER.error(e.getMessage(),e) ;
            model.error("更新失败！") ;
        }

        return model ;

    }

    /**
     * 校验是否存在用户
     * @param username
     * @return
     */
    @RequestMapping(value="/user/validation/{username}",method = RequestMethod.GET)
    @ResponseBody
    public boolean isExistUser(@PathVariable String username) {

        boolean passFlag = false ;  //校验通过标记。 true：通过。
        try {
            passFlag = !this.userService.isExistUser(username) ;
        } catch (Exception e){
            LOGGER.error(e.getMessage(),e) ;
            passFlag = false ;
        }
        return passFlag ;
    }

    @RequestMapping(value="/user/lock/{userId}",method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseModel<Object> changeUserLock(@PathVariable String userId) {
        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try{
            User user = this.userService.findOne(userId);
            if(user != null) {
                if(user.getIsLock() == Constants.DIC_YES) {
                    user.setIsLock(Constants.DIC_NO);
                } else {
                    user.setIsLock(Constants.DIC_YES) ;
                }
                this.userService.save(user) ;
                model.success() ;
            } else {
                model.error() ;
            }
        } catch (Exception e) {
                model.error() ;
                LOGGER.error(e.getMessage(),e);
        }
        return model ;
    }

    /**
     * 重置密码 成初始化密码
     * @param userId
     * @return
     */
    @RequestMapping(value="/user/password/{userId}",method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseModel<Object> resetPassword(String userId) {

        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try{
            User user = this.userService.findOne(userId) ;
            if(user != null) {
                user.setPassword(EncryptUtils.encryptToBase64(user.getSalt()+Constants.INITIAL_PASSWORD));
            }
            model.success() ;
        } catch(Exception e){
            model.error() ;
            LOGGER.error(e.getMessage(),e) ;
        }
        return model ;
    }

    @SystemLog(description = "伪删除用户",operateType = OperateType.DELETE,logLevel = LogLevel.ERROR)
    @RequestMapping(value="/user",method = RequestMethod.PATCH)
    @ResponseBody
    public ResponseModel<Object> updateUser4isDelete(String[] ids) {
        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try{
            this.userService.updateUser4isDelete(ids) ;
            model.success() ;
        } catch (Exception e) {
            model.error() ;
            LOGGER.error(e.getMessage(),e) ;
        }
        return model ;
    }

    /**
     *
     */
    @SystemLog(description = "查询被伪删除的用户")
    @RequestMapping(value="/user/deletionUser",method= RequestMethod.GET)
    @ResponseBody
    public JqGridModel<UserVo> findUserIsDelete(HttpServletRequest request, UserVo userVo) {

        PageRequest pageRequest = PageUtils.getPage4JqGrid(request) ;
        Page<UserVo> page = this.userService.findUserisDelete(pageRequest, userVo);
        JqGridModel<UserVo> model = PageUtils.pageConvertJqGrid(page);
        return model ;
    }

    /**
     * 真正的删除用户信息
     * @param userId 用户id
     * @return
     */
    @RequestMapping(value="/user/{userId}",method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseModel<Object> deleteUser(@PathVariable String userId) {
        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try{
            if(userId != null) {
                this.userService.delete(userId) ;
            }
            model.success("删除成功！") ;
        } catch (DataIntegrityViolationException e) {
            model.error("删除失败，请先删除与用户有关的数据！") ;
        } catch (Exception e) {
            model.error("删除失败！") ;
        }
        return model ;

    }



}

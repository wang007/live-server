package org.live.sys.controller;


import org.live.common.constants.SystemConfigConstants;
import org.live.common.response.ResponseModel;
import org.live.common.response.SimpleResponseModel;
import org.live.common.shiro.RetryLimitHashedCredentialsMatcher;
import org.live.common.support.ServletContextHolder;
import org.live.common.support.SpringContextHolder;
import org.live.common.systemlog.LogLevel;
import org.live.common.systemlog.OperateType;
import org.live.common.systemlog.SystemLog;
import org.live.common.systemlog.SystemLogAspect;
import org.live.sys.vo.SysConfigVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *  系统参数设置的controller
 * Created by Mr.wang on 2016/12/15.
 */
@Controller
@RequestMapping("/sys")
public class ConfigController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigController.class) ;

    /**
     * 进入系统参数设置界面
     *
     * @return
     */
    @RequestMapping(value="/config/page",method = RequestMethod.GET)
    @SystemLog(description = "进入系统参数设置",logLevel = LogLevel.ERROR)
    public String toSysConfigPage(Model model) {
        try {
            String systemTitle = ServletContextHolder.getAttribute(SystemConfigConstants.SYSTEM_TITLE_KEY) ;
            RetryLimitHashedCredentialsMatcher matcherComponent = SpringContextHolder.getBean(RetryLimitHashedCredentialsMatcher.class) ;
            SystemLogAspect systemLogAspect = SpringContextHolder.getBean(SystemLogAspect.class) ;
            int passwordRetryCount = matcherComponent.getRetryLimitCount() ;
            int logLevel = systemLogAspect.getLogLevel() ;

            model.addAttribute("systemTitle",systemTitle) ;
            model.addAttribute("passwordRetryCount", passwordRetryCount) ;
            model.addAttribute("logLevel", logLevel) ;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return "sys/config" ;
    }

    @SystemLog(description = "设置系统参数",logLevel = LogLevel.ERROR,operateType = OperateType.UPDATE)
    @RequestMapping(value="/config",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel<Object> saveSystemConfig(SysConfigVo vo) {

        ResponseModel<Object> model = new SimpleResponseModel<Object>() ;
        try {
            int[] logLevelRange = {1,2,3,4} ;
            if(vo.getLogLevel()<1 || vo.getLogLevel()>4) {  //1.info级别，2.warn级别，3，error级别 4.关闭日志
                return model.error("系统日志参数错误") ;
            }
            if(vo.getPasswordRetryCount()<0 || vo.getPasswordRetryCount()>15) { //0.关闭限制 ，但值要小于15
                return model.error("密码重试次数限制的参数错误") ;
            }
            ServletContextHolder.setAttribute(SystemConfigConstants.SYSTEM_TITLE_KEY, vo.getSystemTitle()) ;
            RetryLimitHashedCredentialsMatcher matcherComponent = SpringContextHolder.getBean(RetryLimitHashedCredentialsMatcher.class) ;
            SystemLogAspect systemLogAspect = SpringContextHolder.getBean(SystemLogAspect.class) ;
            matcherComponent.setRetryLimitCount(vo.getPasswordRetryCount()) ;
            systemLogAspect.setLogLevel(vo.getLogLevel()) ;
            model.success() ;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return model ;
    }


}

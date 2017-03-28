package org.live.common.systemlog;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.live.common.constants.Constants;
import org.live.common.utils.HttpServletUtils;
import org.live.sys.entity.Log;
import org.live.sys.entity.User;
import org.live.sys.service.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 定义一个记录系统操作日志的切面类
 * 
 * @author wzc
 * 
 */
@Aspect
@Component
public class SystemLogAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(SystemLogAspect.class) ;

    @Resource
    private LogService logService ;

    /**
     * 日志级别
     * 1.info
     * 2.warn
     * 3.error
     * 4.关闭日志记录
     */
	private int logLevel ;


    /**
	 * 定义controller方法的切入面
	 * 
	 * 执行每个模块内的controller包的public的任意参数方法
	 * 
	 */
	@Pointcut("@annotation(org.live.common.systemlog.SystemLog)")
	public void executeController(){}

	/**
     *
     * @param joinPoint
     */
    @Before(value = "executeController()")
    public void executePointcut(JoinPoint joinPoint) {
        try {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            SystemLog systemLog = method.getAnnotation(SystemLog.class) ;   //方法上的操作日志的注解

            int tempLogLevel = systemLog.logLevel().getLevel() ;  //获取注解上的日志级别
            if(logLevel > tempLogLevel) {   //如果当前级别大于注解的日志级别，那么不就记录了
                return ;
            }
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String ipAddr = HttpServletUtils.getIpAddr(request);
            HttpSession session = request.getSession() ;

            Log log = new Log() ;
            log.setIp(ipAddr) ;
            log.setDescription(systemLog.description()) ;
            log.setHandleTime(new Date()) ;
            log.setLogLevel(systemLog.logLevel().toString()) ;
            log.setOperateType(systemLog.operateType().toString()) ;
            User user = (User) session.getAttribute(Constants.CURRENT_LOGINUSER_KEY) ;
            if(user != null) {
                log.setUsername(user.getUsername()) ;
                log.setName(user.getName()) ;
            }
            logService.save(log) ;

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

    public int getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }

}

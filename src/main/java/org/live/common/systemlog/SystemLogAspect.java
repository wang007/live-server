package org.live.common.systemlog;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
        System.out.println("执行日志操作");
    }

    public int getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }

}

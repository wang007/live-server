package org.live.common.init;


import org.live.common.shiro.RetryLimitHashedCredentialsMatcher;
import org.live.common.systemlog.SystemLogAspect;
import org.live.config.dataComponent.SystemConfigDataComponent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 初始化SystemConfig.properties的数据中的密码重试次数，系统日志级别
 *
 * Created by Mr.wang on 2016/12/15.
 */
@Component
public class InitSysConfig implements ApplicationListener<ContextRefreshedEvent> {


    @Resource
    private SystemConfigDataComponent dataComponent ;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext() ;
        if(context.getParent() == null) {    //确保是spring 主容器启动
            RetryLimitHashedCredentialsMatcher matcher = context.getBean(RetryLimitHashedCredentialsMatcher.class);//得到shiro的凭证匹配器
            if(dataComponent.getPasswordRetryCount() != 0) {    //设置密码重试次数
                matcher.setRetryLimitCount(dataComponent.getPasswordRetryCount());
            }
            SystemLogAspect aspectComponent = context.getBean(SystemLogAspect.class);
            String logLevel = dataComponent.getLogLevel() == null || "".equals(dataComponent.getLogLevel()) ? "info" : dataComponent.getLogLevel() ;
            if("INFO".equalsIgnoreCase(logLevel)) {
                aspectComponent.setLogLevel(1) ;
            } else if("WARN".equalsIgnoreCase(logLevel)) {
                aspectComponent.setLogLevel(2) ;
            } else {
                aspectComponent.setLogLevel(3) ;
            }
        }

    }
}

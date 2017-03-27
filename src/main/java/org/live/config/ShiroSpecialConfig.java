package org.live.config;


import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationScheduler;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

/**
 * shiro的其他配置
 * 解决在{@code cn.school.config.ShiroConfig}中sessionManager和sessionValidationScheduler互相引用而导致错误的问题
 * <p/>
 * 往sessionManager添加shiro的listener
 *
 * @author Mr.wang
 */
@Component
public class ShiroSpecialConfig implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroSpecialConfig.class) ;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        ApplicationContext context = event.getApplicationContext() ;
        if(context.getParent() == null) {	//确保是spring 主容器启动
            LOGGER.info("springContext启动成功，并已成功监听");
            QuartzSessionValidationScheduler scheduler = (QuartzSessionValidationScheduler)context.getBean(SessionValidationScheduler.class) ;	//会话验证调度器
            DefaultWebSessionManager sessionManager = context.getBean(DefaultWebSessionManager.class) ;		//会话管理器

            Map<String, SessionListener> listenerMap = context.getBeansOfType(SessionListener.class) ;  //从spring容器中获取shiro的sessionListener
            Collection<SessionListener> listeners = listenerMap.values() ;
            if(listeners.size() > 0) {
                sessionManager.setSessionListeners(listeners) ;	//设置shiro监听器
                LOGGER.info("shiro的sessionListener已成功加到SessionManager");
            } else {
                LOGGER.info("spring容器中未找到shiro的sessionListener");
            }
            sessionManager.setSessionValidationScheduler(scheduler) ;	//设置会话验证调度器
            LOGGER.info("sessionManager的SessionValidationScheduler已经设置完成") ;
            scheduler.setSessionManager(sessionManager) ;	//设置会话管理器
            LOGGER.info("SessionValidationScheduler的sessionManager已经设置完成") ;
        }

    }
}

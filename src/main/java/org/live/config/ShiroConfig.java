package org.live.config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.SessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationScheduler;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.live.common.shiro.FullShiroFilterFactoryBean;
import org.live.common.shiro.LoginRealm;
import org.live.common.shiro.RetryLimitHashedCredentialsMatcher;
import org.live.config.dataComponent.ShiroDataComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro的配置
 *
 * Created by Mr.wang on 2016/11/22.
 */
@Configuration
public class ShiroConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroConfig.class) ;

    /**
     * ehcache中配置的授权缓存的key
     */
    public static final String AUTHORIZATION_CACHE_KEY = "authorizationCache" ;


    /**
     * ehcache中配置的认证缓存的key
     */
    public static final String AUTHENTICATION_CACHE_KEY = "authenticationCache" ;

    /**
     * ehcache中配置的session缓存的key
     */
    public static final String ACTIVESESSION_CACHE_KEY = "shiro-activeSessionCache" ;

    /**
     * shiro 访问控制 filter链
     */
    private static Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>() ;

    @Resource
    private ShiroDataComponent shiroData ;

    /**
     * shiro的缓存管理器
     * @return
     */
    @Bean
    public EhCacheManager shiroEhCacheManager() {

        EhCacheManager cacheManager = new EhCacheManager() ;
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager ;
    }

    /**
     * 凭证匹配器
     * @return
     */
    @Bean
    public CredentialsMatcher credentialsMatcher() {
        //设置缓存管理器
        RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher(shiroEhCacheManager()) ;
        return credentialsMatcher ;
    }

    /**
     * 配置shiro的realm
     * @return
     */
    @Bean
    public LoginRealm loginRealm() {

        LoginRealm loginRealm = new LoginRealm() ;
        loginRealm.setCredentialsMatcher(credentialsMatcher()) ;    //设置凭证匹配器
        loginRealm.setCachingEnabled(true) ; //开启缓存
        //开启授权缓存
        loginRealm.setAuthorizationCachingEnabled(true) ;
        loginRealm.setAuthorizationCacheName(AUTHORIZATION_CACHE_KEY);
        //开启认证缓存
        loginRealm.setAuthenticationCachingEnabled(true) ;
        loginRealm.setAuthenticationCacheName(AUTHENTICATION_CACHE_KEY);
        return loginRealm ;
    }

    /**
     *   shiro会话ID生成器
     */
    @Bean
    public SessionIdGenerator sessionIdGenerator() {

        JavaUuidSessionIdGenerator sessionIdGenerator = new JavaUuidSessionIdGenerator() ;
        return sessionIdGenerator ;
    }

    /**
     * 会话验证调度器
     * @return
     */
    @Bean
    public SessionValidationScheduler sessionValidationScheduler() {

        QuartzSessionValidationScheduler sessionValidationScheduler = new QuartzSessionValidationScheduler() ;
        sessionValidationScheduler.setSessionValidationInterval(shiroData.getSessionValidationInterval()) ; //调度验证周期

        //设置sessionManager在ShiroSpecialConfig中完成
        return sessionValidationScheduler ;
    }

    /**
     * 会话DAO
     * @return
     */
    @Bean
    public SessionDAO sessionDAO() {

        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO() ;
        sessionDAO.setActiveSessionsCacheName(ACTIVESESSION_CACHE_KEY) ;    //设置缓存名称
        sessionDAO.setSessionIdGenerator(sessionIdGenerator()) ;    //设置会话ID生成器
        return sessionDAO ;
    }

    /**
     * 会话Cookie 模板
     * @return
     */
    @Bean
    public SimpleCookie sessionIdCookie() {
        SimpleCookie sessionIdCookie = new SimpleCookie() ;
        LOGGER.info("shiroConfig->shiroData->cookieName :{}",shiroData.getCookieName());
        sessionIdCookie.setName(shiroData.getCookieName()) ; //设置 cookieName
        sessionIdCookie.setHttpOnly(true) ;
        sessionIdCookie.setMaxAge(-1) ; //即，关闭浏览器就失效cookie
        return sessionIdCookie ;
    }

    /**
     * 会话管理器
     * @return
     */
    @Bean
    public SessionManager sessionManager() {

        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager() ;
        sessionManager.setGlobalSessionTimeout(shiroData.getGlobalSessionTimeout()) ;   //设置shiro的失效时间
        sessionManager.setDeleteInvalidSessions(true) ; //设置是否可以删除失效的session
        sessionManager.setSessionValidationSchedulerEnabled(true) ; //设置开启任务调度
        //设置sessionValidationScheduler在ShiroSpecialConfig中完成
        //shiro的监听器在ShiroSpecialConfig中完成
        sessionManager.setSessionDAO(sessionDAO()) ;    //设置会话管理器
        sessionManager.setSessionIdCookieEnabled(true) ;    //设置SessionIdCookie
        sessionManager.setSessionIdCookie(sessionIdCookie());
        return  sessionManager ;
    }

    /**
     * 安全管理器
     * @return
     */
    @Bean
    public SecurityManager securityManager() {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager() ;
        securityManager.setRealm(loginRealm()) ;    //设置realm
        securityManager.setSessionManager(sessionManager()) ;   //设置会话管理器
        securityManager.setCacheManager(shiroEhCacheManager()) ; //设置缓存管理器
        return securityManager ;
    }

    /**
     * 相当于调用SecurityUtils.setSecurityManager(securityManager)
     * @return
     */
    @Bean
    public MethodInvokingFactoryBean invokeSetSecurityManager() {
        MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean() ;
        bean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        bean.setArguments(new Object[]{securityManager()}) ;
        return bean ;
    }

    /**
     * 基于Form表单的身份验证过滤器
     * @return
     */
    @Bean
    public FormAuthenticationFilter formAuthenticationFilter() {

        FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter() ;
        formAuthenticationFilter.setUsernameParam("username");  //设置登录表单的账号名
        formAuthenticationFilter.setPasswordParam("password");  //设置登录表单的密码名

        if(StringUtils.trimToNull(shiroData.getLoginUrl()) == null) {
            throw new IllegalArgumentException("shiro->loginUrl is null") ;
        } else {
            LOGGER.info("shiroConfig->shiroData->loginUrl:{}",shiroData.getLoginUrl()) ;
            //验证的失败时，跳转的地址,保存错误信息
            formAuthenticationFilter.setLoginUrl(shiroData.getLoginUrl()) ; //设置登录地址，默认值：index.jsp
        }
        return formAuthenticationFilter ;
    }

    /**
     * Shiro的Web过滤器
     * @return
     */
    @Bean
    public FullShiroFilterFactoryBean shiroFilter() {

        FullShiroFilterFactoryBean factoryBean = new FullShiroFilterFactoryBean() ;
        factoryBean.setSecurityManager(securityManager()) ; //设置安全管理器

        if(StringUtils.trimToNull(shiroData.getLoginUrl()) == null) {
            throw new IllegalArgumentException("shiro->loginUrl is null") ;
        } else {
            LOGGER.info("shiroConfig->shiroData->loginUrl:{}",shiroData.getLoginUrl()) ;
            //验证的失败时，跳转的地址,保存错误信息
            factoryBean.setLoginUrl(shiroData.getLoginUrl()) ; //设置登录地址，默认值：index.jsp
        }

        if(StringUtils.trimToNull(shiroData.getUnauthorizedUrl()) == null) {    //未配置未授权的地址
            LOGGER.warn("未配置shiro的未授权地址") ;
        } else {
            LOGGER.info("shiroConfig->shiroData->unauthorizedUrl:{}",shiroData.getUnauthorizedUrl()) ;
            factoryBean.setUnauthorizedUrl(shiroData.getUnauthorizedUrl()) ; //设置未授权的地址
        }

        if(StringUtils.trimToNull(shiroData.getSuccessUrl()) == null) {
            LOGGER.warn("未配置shiro的未授权地址") ;
        } else {
            LOGGER.info("shiroConfig->shiroData->successUrl:{}",shiroData.getSuccessUrl()) ;
            factoryBean.setSuccessUrl(shiroData.getSuccessUrl()) ;  //设置登录成功之后的跳转的地址
        }

        String filterChains = StringUtils.trimToNull(shiroData.getFilterChainDefinitions()) ;
        if(filterChains != null) {
            if(filterChains.contains("，")) throw new IllegalArgumentException("错误字符'，'") ; //中文的逗号
            String[] filters = filterChains.split(",") ;
            for(String filterChain: filters) {
                String[] urlAndFilter = filterChain.split("=") ;
                if(urlAndFilter.length!=2) throw new IllegalArgumentException("拦截路径跟filter没有一一对应:"+filterChain) ;
                LOGGER.info("shiro->filterchainDefinition--->:{}",filterChain);
                LOGGER.debug("interceptUrl->:{}",urlAndFilter[0]);
                LOGGER.debug("interceptFilter->:{}",urlAndFilter[1]);
                filterChainDefinitionMap.put(urlAndFilter[0],urlAndFilter[1]) ;
            }
        } else {
            LOGGER.warn("filterChainDefinitions is null");
        }
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap) ; //设置访问控制filter链
        return factoryBean ;

    }

    /**
     *  aop 代理
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);   //基于cglib的代理
        return daap;
    }

    /**
     * 开启shiro的注解权限的方式
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager()) ;
        return aasa ;
    }

    /**
     * Shiro生命周期处理器
     *
     * 这里一定要加static
     * 原因->: @see{ http://stackoverflow.com/questions/31388445/apache-shiro-jdbcrealm-with-javaconfig-and-spring-boot }
     * 另一个解决方式：不要在@Configuration的类下配置 BeanPostProcessor及子类
     *
     */
    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {

        LifecycleBeanPostProcessor processor = new LifecycleBeanPostProcessor() ;
        return processor ;
    }

    /**
     *  thymeleaf中使用shiro方言
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }


}

package org.live.config.dataComponent;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * shiro的数据载体
 *
 * Created by Mr.wang on 2016/11/22.
 */
@ConfigurationProperties(prefix = "shiro")
@Component
public class ShiroDataComponent {

    /**
     *  session会话验证周期
     */
    private long sessionValidationInterval ;

    /**
     * session存活时间
     */
    private long globalSessionTimeout ;

    /**
     *  sessionId的cookie名称
     */
    private String cookieName ;

    /**
     * 登录时，密码错误重试次数. 默认值：0.  即关闭，无限制
     */
    private int passwordRetryLimitCount ;

    /**
     * shiro的登录地址
     */
    private String loginUrl ;

    /**
     * shiro未授权页面
     */
    private String unauthorizedUrl ;

    /**
     * shiro登录成功之后的url
     */
    private String successUrl ;

    /**
     * shiro的访问控制配置中心,注意先后顺序
     */
    private String filterChainDefinitions ;


    public long getSessionValidationInterval() {
        return sessionValidationInterval;
    }

    public void setSessionValidationInterval(long sessionValidationInterval) {
        this.sessionValidationInterval = sessionValidationInterval;
    }

    public long getGlobalSessionTimeout() {
        return globalSessionTimeout;
    }

    public void setGlobalSessionTimeout(long globalSessionTimeout) {
        this.globalSessionTimeout = globalSessionTimeout;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public int getPasswordRetryLimitCount() {
        return passwordRetryLimitCount;
    }

    public void setPasswordRetryLimitCount(int passwordRetryLimitCount) {
        this.passwordRetryLimitCount = passwordRetryLimitCount;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getUnauthorizedUrl() {
        return unauthorizedUrl;
    }

    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getFilterChainDefinitions() {
        return filterChainDefinitions;
    }

    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }
}

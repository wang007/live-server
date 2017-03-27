package org.live.config.dataComponent;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * druid 连接池的数据载体
 *
 * Created by Mr.wang on 2016/11/22.
 */
@ConfigurationProperties(prefix = "druid")
@Component
public class DruidDataComponent {


    /**
     * druid 监控访问的账号
     */
    private String loginUsername ;

    /**
     * druid 监控访问的密码
     */
    private String loginPassword ;

    /**
     * druid 是否允许清空统计数据
     */
    private String resetEnable ;

    /**
     * 连接池的初始化大小
     */
    private int initialSize ;

    /**
     * 连接池的最小值
     */
    private int minIdle ;

    /**
     * 连接池的最大活跃数
     */
    private int maxActive ;

    /**
     * 连接等待超时的时间
     */
    private long maxWait ;

    /**
     * 间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
     */
    private long timeBetweenEvictionRunsMillis ;

    /**
     * 配置一个连接在池中最小生存的时间，单位是毫秒
     */
    private long minEvictableIdleTimeMillis ;

    /**
     * 测试数据库是否可以正常使用
     */
    private String validationQuery ;

    /**
     * 连接空闲时测试是否有效
     */
    private boolean testWhileIdle ;

    /**
     * 获取连接时测试是否有效
     */

    private boolean testOnBorrow ;

    /**
     * 归还连接时是否测试有效
     */
    private boolean testOnReturn ;

    /**
     * 是否开启Statement缓存,#是否开启Statement缓存,在mysql5.5以下的版本中没有PSCache功能，建议关闭掉
     */
    private boolean poolPreparedStatements ;

    /**
     * Statement（PSCache）缓存大小
     */
    private int maxPoolPreparedStatementPerConnectionSize ;

    /**
     * 配置监控统计拦截的filters. stat uri、sql监控. wall防止sql注入
     */
    private String filters ;

    public String getLoginUsername() {
        return loginUsername;
    }

    public void setLoginUsername(String loginUsername) {
        this.loginUsername = loginUsername;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getResetEnable() {
        return resetEnable;
    }

    public void setResetEnable(String resetEnable) {
        this.resetEnable = resetEnable;
    }


    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    public long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isPoolPreparedStatements() {
        return poolPreparedStatements;
    }

    public void setPoolPreparedStatements(boolean poolPreparedStatements) {
        this.poolPreparedStatements = poolPreparedStatements;
    }

    public int getMaxPoolPreparedStatementPerConnectionSize() {
        return maxPoolPreparedStatementPerConnectionSize;
    }

    public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
        this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

}

package org.live.config.dataComponent;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * jdbc的数据载体
 *
 * Created by Mr.wang on 2016/11/22.
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class JdbcDataComponent {

    /**
     * jdbc url
     */
    private String url ;

    /**
     * 用户名
     */
    private String username ;

    /**
     * 密码
     */
    private String password ;

    /**
     * driver
     */
    private String driverClassName ;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }
}

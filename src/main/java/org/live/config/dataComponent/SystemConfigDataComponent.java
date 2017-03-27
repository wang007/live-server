package org.live.config.dataComponent;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * systemConfig.properties 配置的信息
 *
 * Created by Mr.wang on 2016/11/27.
 */

@Component
@ConfigurationProperties(prefix = "system")
@PropertySource("classpath:systemConfig.properties")
public class SystemConfigDataComponent {

    /**
     * 项目中的title
     */
    private String title ;

    /**
     * 系统日志级别
     */
    private String logLevel ;

    /**
     * 登录密码重试次数
     */
    private int passwordRetryCount ;

    /**
     * 设置系统文件保存目录
     */
    private String projectFileDirectory ;

    /**
     * 设置上传文件保存目录
     */
    private String projectUploadFileDirectory ;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProjectFileDirectory() {
        return projectFileDirectory;
    }

    public void setProjectFileDirectory(String projectFileDirectory) {
        this.projectFileDirectory = projectFileDirectory;
    }

    public String getProjectUploadFileDirectory() {
        return projectUploadFileDirectory;
    }

    public void setProjectUploadFileDirectory(String projectUploadFileDirectory) {
        this.projectUploadFileDirectory = projectUploadFileDirectory;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public int getPasswordRetryCount() {
        return passwordRetryCount;
    }

    public void setPasswordRetryCount(int passwordRetryCount) {
        this.passwordRetryCount = passwordRetryCount;
    }

}

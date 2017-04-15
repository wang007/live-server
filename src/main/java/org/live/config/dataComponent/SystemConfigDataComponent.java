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
     * window系统上传文件根目录
     */
    private String windowsUploadFileRootPath ;

    /**
     * linux系统上传文件根目录
     */
    private String linuxUploadFileRootPath ;

    /**
     *  上传文件路径的前缀
     */
    private String uploadFilePathPrefix  ;

    /**
     *  申请成为主播的最大申请数
     */
    private int applyAnchorMaxCount ;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getWindowsUploadFileRootPath() {
        return windowsUploadFileRootPath;
    }

    public void setWindowsUploadFileRootPath(String windowsUploadFileRootPath) {
        this.windowsUploadFileRootPath = windowsUploadFileRootPath;
    }

    public String getLinuxUploadFileRootPath() {
        return linuxUploadFileRootPath;
    }

    public void setLinuxUploadFileRootPath(String linuxUploadFileRootPath) {
        this.linuxUploadFileRootPath = linuxUploadFileRootPath;
    }

    public String getUploadFilePathPrefix() {
        return uploadFilePathPrefix;
    }

    public void setUploadFilePathPrefix(String uploadFilePathPrefix) {
        this.uploadFilePathPrefix = uploadFilePathPrefix;
    }

    public int getApplyAnchorMaxCount() {
        return applyAnchorMaxCount;
    }

    public void setApplyAnchorMaxCount(int applyAnchorMaxCount) {
        this.applyAnchorMaxCount = applyAnchorMaxCount;
    }
}

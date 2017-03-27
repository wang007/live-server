package org.live.sys.vo;

/**
 * 系统参数设置的vo
 * Created by Mr.wang on 2016/12/15.
 */
public class SysConfigVo {

    private String systemTitle ;

    private int passwordRetryCount ;

    private int logLevel ;

    public String getSystemTitle() {
        return systemTitle;
    }

    public void setSystemTitle(String systemTitle) {
        this.systemTitle = systemTitle;
    }

    public int getPasswordRetryCount() {
        return passwordRetryCount;
    }

    public void setPasswordRetryCount(int passwordRetryCount) {
        this.passwordRetryCount = passwordRetryCount;
    }

    public int getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }
}

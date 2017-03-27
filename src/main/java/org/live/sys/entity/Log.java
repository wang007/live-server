package org.live.sys.entity;


import org.live.common.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 操作日志的实体
 *
 * Created by Mr.wang on 2016/12/13.
 */
@Entity
@Table(name="sys_log")
public class Log extends BaseEntity {

    /**
     * 操作人的ip
     */
    @Column
    private String ip ;

    /**
     * 操作人名称
     */
    @Column
    private String name ;

    /**
     * 操作人账号
     */
    @Column
    private String username ;

    /**
     * 操作时间
     */
    @Column
    private Date handleTime ;

    /**
     *  操作描述
     */
    @Column
    private String description ;

    /**
     * 日志级别
     */
    @Column
    private String logLevel ;

    /**
     * 操作方式
     */
    @Column
    private String operateType ;


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }
}

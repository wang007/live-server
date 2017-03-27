package org.live.sys.vo;

import java.util.Date;

/**
 * 用户vo
 *
 * Created by Mr.wang on 2016/11/29.
 */
public class UserVo {

    /**
     * 用户id
     */
    public String id ;

    /**
     * 账号
     */
    private String username ;

    /**
     * 名称
     */
    private String name ;

    /**
     * 用户类型
     */
    private String userType ;

    /**
     * 注册时间
     */
    private Date registerTime ;


    /**
     * 账号是否被锁定  1.锁定   0.未锁定(默认)
     */
    private int isLock ;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime ;

    /**
     * 最后登录的ip地址
     */
    private String lastLoginIp ;

    /**
     * 所属用户组名称
     */
    private String userGroupName ;

    /**
     * 用户组id
     */
    private String groupId ;

    public UserVo(){}

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public int getIsLock() {
        return isLock;
    }

    public void setIsLock(int isLock) {
        this.isLock = isLock;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getUserGroupName() {
        return userGroupName;
    }

    public void setUserGroupName(String userGroupName) {
        this.userGroupName = userGroupName;
    }
}

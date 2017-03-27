package org.live.sys.vo;

/**
 * Created by Mr.wang on 2016/12/7.
 */

public class FindGroupVo {

    /**
     * 用户组id
     */
    private String id ;

    /**
     * 用户组名
     */
    private String groupName ;

    /**
     * 序号 (用于排序)
     */
    private Integer serialNo ;

    /**
     * 用户组描述(对用户组进行简短的描述)
     */
    private String description ;

    /**
     * 是否启用  	1.启用  0.未启用
     */
    private int isEnable ;

    /**
     * 该用户下的角色
     */
    private String[] roleNames ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(int isEnable) {
        this.isEnable = isEnable;
    }

    public String[] getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String[] roleNames) {
        this.roleNames = roleNames;
    }
}

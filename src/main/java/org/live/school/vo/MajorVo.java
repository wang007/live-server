package org.live.school.vo;

import java.util.Date;

/**
 * Created by KAM on 2017/4/4.
 */
public class MajorVo {
    private String id;
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 系部名称
     */
    private String departmentName;
    /**
     * 启用标记
     */
    private boolean enableFlag;

    public MajorVo(String id, String code, String name, String description, Date createTime, String departmentName, boolean enableFlag) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.createTime = createTime;
        this.departmentName = departmentName;
        this.enableFlag = enableFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public boolean isEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(boolean enableFlag) {
        this.enableFlag = enableFlag;
    }
}

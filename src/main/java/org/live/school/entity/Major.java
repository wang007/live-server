package org.live.school.entity;

import org.live.common.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 专业
 * Created by KAM on 2017/4/4.
 */
@Entity
@Table(name = "school_major")
public class Major extends BaseEntity{
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public boolean isEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(boolean enableFlag) {
        this.enableFlag = enableFlag;
    }

    /**
     * 编码
     */
    @Column
    private String code;
    /**
     * 名称
     */
    @Column
    private String name;
    /**
     * 描述
     */
    @Column
    private String description;
    /**
     * 创建时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    /**
     * 所属系部
     */
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    /**
     * 是否启用，默认为true
     */
    @Column
    private boolean enableFlag = true;

}

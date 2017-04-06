package org.live.school.entity;

import org.live.common.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 系部
 * Created by KAM on 2017/4/4.
 */
@Entity
@Table(name = "school_department")
public class Department extends BaseEntity {
    /**
     * 编码
     */
    @Column
    private String code;
    /**
     * 系部名称
     */
    @Column
    private String name;
    /**
     * 描述
     */
    @Column
    private String description;
    /**
     * 是否启用,默认为true
     */
    @Column
    private boolean enableFlag;

    /**
     * 创建时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime ;

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

    public boolean isEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(boolean enableFlag) {
        this.enableFlag = enableFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

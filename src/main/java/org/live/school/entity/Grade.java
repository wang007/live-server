package org.live.school.entity;

import org.live.common.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 班级
 * Created by KAM on 2017/4/4.
 */
@Entity
@Table(name = "school_grade")
public class Grade extends BaseEntity {
    /**
     * 班号
     */
    @Column
    private Integer classNo;
    /**
     * 年级
     */
    @Column
    private Integer gradeNo;
    /**
     * 班级名称
     */
    @Column
    private String className;
    /**
     * 所属专业
     */
    @ManyToOne
    @JoinColumn(name = "major_id")
    private Major major;
    /**
     * 启用状态，默认为true
     */
    @Column
    private boolean enableFlag = true;
    /**
     * 创建时间
     */

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    public Integer getClassNo() {
        return classNo;
    }

    public void setClassNo(Integer classNo) {
        this.classNo = classNo;
    }

    public Integer getGradeNo() {
        return gradeNo;
    }

    public void setGradeNo(Integer gradeNo) {
        this.gradeNo = gradeNo;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
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

package org.live.school.vo;

import java.util.Date;

/**
 * Created by KAM on 2017/4/5.
 */
public class GradeVo {
    private String id;
    private Integer classNo;
    private Integer gradeNo;
    private String className;
    private String majorName;
    private Date createTime;
    private boolean enableFlag;

    public GradeVo(String id, Integer classNo, Integer gradeNo, String className, String majorName, Date createTime, boolean enableFlag){
        this.id = id;
        this.classNo = classNo;
        this.gradeNo = gradeNo;
        this.className = className;
        this.majorName = majorName;
        this.createTime = createTime;
        this.enableFlag = enableFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(boolean enableFlag) {
        this.enableFlag = enableFlag;
    }
}

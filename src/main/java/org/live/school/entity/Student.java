package org.live.school.entity;

import org.live.common.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 学生
 * Created by KAM on 2017/4/4.
 */
@Entity
@Table(name = "school_student")
public class Student extends BaseEntity {
    /**
     * 学号
     */
    @Column
    private String sNo;
    /**
     * 姓名
     */
    @Column
    private String realName;
    /**
     * 性别
     */
    @Column
    private String sex;
    /**
     * 所在班级
     */
    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade Grade;
    /**
     * 年龄
     */
    @Column
    private Integer age;
    /**
     * 生日
     */
    @Temporal(TemporalType.DATE)
    @Column
    private Date birthday;
    /**
     * 是否过期，默认为false
     */
    @Column
    private boolean outDate;
    /**
     * 注册时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date registerDate;

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public org.live.school.entity.Grade getGrade() {
        return Grade;
    }

    public void setGrade(org.live.school.entity.Grade grade) {
        Grade = grade;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isOutDate() {
        return outDate;
    }

    public void setOutDate(boolean outDate) {
        this.outDate = outDate;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }
}

package org.live.school.entity;

import org.live.common.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * 校园成员
 * Created by KAM on 2017/4/4.
 */
@Entity
@Table(name = "school_member")
public class Member extends BaseEntity {
    /**
     * 成员编号，教师或饭堂阿姨对应工号，学生对应学号
     */
    @Column
    private String memberNo;
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
    private Grade grade;

    /**
     * 成员类型,教师或学生 {stu|tea}
     */
    private String memberType;

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

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
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

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
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

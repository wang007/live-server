package org.live.school.vo;

import java.util.Date;

/**
 * Created by KAM on 2017/4/6.
 */
public class MemberVo {
    private String id;
    private String memberNo;
    private String realName;
    private String sex;
    private String className;
    private Integer age;
    private Date birthday;
    private String memberType;
    private boolean outDate;
    private Date registerDate;

    public MemberVo(String id, String memberNo, String realName, String sex, String className, Integer age, Date birthday, String memberType, boolean outDate, Date registerDate) {
        this.id = id;
        this.memberNo = memberNo;
        this.realName = realName;
        this.sex = sex;
        this.className = className;
        this.age = age;
        this.birthday = birthday;
        this.outDate = outDate;
        this.registerDate = registerDate;
        this.memberType = memberType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
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

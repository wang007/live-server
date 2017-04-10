package org.live.live.vo;

import java.util.Date;

/**
 * Created by KAM on 2017/4/6.
 */
public class MobileUserVo {
    private String id;
    /**
     * 头像
     */
    private String headImgUrl;
    /**
     * 账号
     */
    private String account;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String mobileNumber;
    /**
     * 注册时间
     */
    private Date registerTime;
    /**
     * 主播标记
     */
    private boolean anchorFlag;
    /**
     * 锁定标记
     */
    private boolean lockFlag;
    /**
     * 最后登录时间
     */
    private Date lastLoginTime;
    /**
     * 最后登陆ip
     */
    private String lastLoginIp;
    /**
     * 成员编号
     */
    private String memberNo;
    /**
     * 姓名
     */
    private String realName;
    /**
     * 性别
     */
    private String sex;
    /**
     * 成员类别
     */
    private String memberType;


    /**
     * 班级名称
     */
    private String className;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 成员id
     */
    private String memberId;


    public MobileUserVo(
            String id, String headImgUrl, String account, String nickname, String email, String mobileNumber, Date registerTime, boolean anchorFlag, boolean lockFlag,
            Date lastLoginTime, String lastLoginIp, String memberNo, String realName, String sex, String memberType, String className, Integer age, Date birthday, String memberId
    ) {
        this.id = id;
        this.headImgUrl = headImgUrl;
        this.account = account;
        this.nickname = nickname;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.registerTime = registerTime;
        this.anchorFlag = anchorFlag;
        this.lockFlag = lockFlag;
        this.lastLoginTime = lastLoginTime;
        this.lastLoginIp = lastLoginIp;
        this.memberNo = memberNo;
        this.realName = realName;
        this.sex = sex;
        this.memberType = memberType;
        this.className = className;
        this.age = age;
        this.birthday = birthday;
        this.memberId = memberId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public boolean isAnchorFlag() {
        return anchorFlag;
    }

    public void setAnchorFlag(boolean anchorFlag) {
        this.anchorFlag = anchorFlag;
    }

    public boolean isLockFlag() {
        return lockFlag;
    }

    public void setLockFlag(boolean lockFlag) {
        this.lockFlag = lockFlag;
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

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}

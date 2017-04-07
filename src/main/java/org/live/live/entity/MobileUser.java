package org.live.live.entity;

import org.live.common.base.BaseEntity;
import org.live.school.entity.Member;

import javax.persistence.*;
import java.util.Date;

/**
 * 移动端的用户
 * Created by Mr.wang on 2017/3/28.
 */
@Entity
@Table(name = "live_mobileuser")
public class MobileUser extends BaseEntity {

    /**
     * 账号
     */
    @Column(unique = true)  //账号唯一
    private String account;

    /**
     * 密码
     */
    @Column
    private String password;

    /**
     * 昵称
     */
    @Column
    private String nickname;

    /**
     * 邮箱
     */
    @Column
    private String email;

    /**
     * 手机号码
     */
    @Column
    private String mobileNumber;

    /**
     * 注册时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date registerTime;

/*    *//**
     * 性别
     *//*

    @Column
    private String sex;*/

    /**
     * 对应的学校成员身份
     */
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    /**
     * 头像url地址
     */
    @Column
    private String headImgUrl;

    /**
     * 是否是主播的标记,默认是false
     */
    @Column
    private boolean anchorFlag;

    /**
     * 是否锁定用户。默认是false
     */
    @Column
    private boolean lockFlag;

    /**
     * 最后登录时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginTime;

    /**
     * 最后登录ip
     */
    @Column
    private String lastLoginIp;

    /**
     * 过期标记
     */
    private boolean outDateFlag;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

 /*   public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }*/

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
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


    public boolean isOutDateFlag() {
        return outDateFlag;
    }

    public void setOutDateFlag(boolean outDateFlag) {
        this.outDateFlag = outDateFlag;
    }
}

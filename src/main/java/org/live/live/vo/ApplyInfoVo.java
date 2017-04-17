package org.live.live.vo;

import java.util.Date;

/**
 * Created by wang on 2017/4/16.
 */
public class ApplyInfoVo {

    /**
     *  申请表id
     */
    private String id ;

    /**
     * 账号
     */
    private String account ;

    /**
     * 昵称
     */
    private String nickname ;

    /**
     * 身份证号
     */
    private String idCard ;

    /**
     * 真实姓名
     */
    private String realName ;

    /**
     * 学校成员表中的真实姓名
     */
    private String realNameInMember ;

    /**
     * 性别
     */
    private String sex ;

    /**
     *  分类名称
     */
    private String categoryName ;

    /**
     * 是否审核通过， 0. 未审核， 1.审核通过， 2.审核不通过
     */
    private int passFlag ;

    /**
     * 审核不通过的原因
     */
    private String noPassReason ;

    /**
     * 创建时间
     */
    private Date createTime ;

    /**
     *   身份证正面照
     */
    private String idImgUrl ;

    /**
     * 邮箱
     */
    private String email ;

    /**
     * 手机号码
     */
    private String mobileNumber ;

    public ApplyInfoVo() {
    }

    public ApplyInfoVo(String id, String account, String nickname, String idCard, String realName, String realNameInMember, String sex, String categoryName, int passFlag, String noPassReason, Date createTime, String idImgUrl, String email, String mobileNumber) {
        this.id = id;
        this.account = account;
        this.nickname = nickname;
        this.idCard = idCard;
        this.realName = realName;
        this.realNameInMember = realNameInMember;
        this.sex = sex;
        this.categoryName = categoryName;
        this.passFlag = passFlag;
        this.noPassReason = noPassReason;
        this.createTime = createTime;
        this.idImgUrl = idImgUrl;
        this.email = email;
        this.mobileNumber = mobileNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealNameInMember() {
        return realNameInMember;
    }

    public void setRealNameInMember(String realNameInMember) {
        this.realNameInMember = realNameInMember;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getPassFlag() {
        return passFlag;
    }

    public void setPassFlag(int passFlag) {
        this.passFlag = passFlag;
    }

    public String getNoPassReason() {
        return noPassReason;
    }

    public void setNoPassReason(String noPassReason) {
        this.noPassReason = noPassReason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIdImgUrl() {
        return idImgUrl;
    }

    public void setIdImgUrl(String idImgUrl) {
        this.idImgUrl = idImgUrl;
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
}

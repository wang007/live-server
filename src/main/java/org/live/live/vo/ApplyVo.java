package org.live.live.vo;


import java.util.Date;

/**
 * 主播申请表的vo
 * Created by wang on 2017/4/15.
 */
public class ApplyVo {

    private String id;
    /**
     * 账号
     */
    private String account ;

    /**
     *  昵称
     */
    private String nickname ;

    /**
     *  姓名
     */
    private String realName ;

    /**
     * 分类名称
     */
    private String categoryName ;

    /**
     * 是否审核通过， 0. 未审核， 1.审核通过， 2.审核不通过
     */
    private int passFlag ;

    /**
     * 邮箱
     */
    private String email ;

    /**
     * 手机号码
     */
    private String mobileNumber ;

    /**
     * 申请时间
     */
    private Date createTime ;

    public ApplyVo(){}

    public ApplyVo(String id, String account, String nickname, String realName, String categoryName, int passFlag, String email, String mobileNumber, Date createTime) {
        this.id = id;
        this.account = account;
        this.nickname = nickname;
        this.realName = realName;
        this.categoryName = categoryName;
        this.passFlag = passFlag;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.createTime = createTime ;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

package org.live.live.vo;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by wang on 2017/4/11.
 */
public class AnchorInfoVo {

    /**
     * id
     */
    private String anchorId ;

    /**
     *  账号
     */
    private String account ;

    /**
     *  昵称
     */
    private String nickname ;

    /**
     * 姓名
     */
    private String realName ;

    /**
     *  头像地址
     */
    private String headImgUrl ;

    /**
     * 身份证号
     */
    private String idCard ;

    /**
     *  创建时间
     */
    private Date createTime ;

    /**
     *  个性签名
     */
    private String description ;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobileNumber;

    /**
     * 锁定状态
     */
    private boolean lockFlag ;

    public AnchorInfoVo() {
    }

    public AnchorInfoVo(String anchorId, String account, String nickname, String realName, String headImgUrl, String idCard, Date createTime, String description, String email, String mobileNumber, boolean lockFlag) {
        this.anchorId = anchorId;
        this.account = account ;
        this.nickname = nickname;
        this.realName = realName;
        this.headImgUrl = headImgUrl;
        this.idCard = idCard;
        this.createTime = createTime;
        this.description = description;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.lockFlag = lockFlag ;
    }

    public String getAnchorId() {
        return anchorId;
    }

    public void setAnchorId(String anchorId) {
        this.anchorId = anchorId;
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

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
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

    public boolean isLockFlag() {
        return lockFlag;
    }

    public void setLockFlag(boolean lockFlag) {
        this.lockFlag = lockFlag;
    }

}

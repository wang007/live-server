package org.live.live.vo;

import javax.persistence.Column;

/**
 * Created by wang on 2017/4/11.
 */
public class AnchorVo {

    /**
     *  主播id
     */
    private String anchorId ;

    /**
     * 昵称
     */
    private String nickname ;

    /**
     *  账号
     */
    private String account ;

    /**
     *  真实姓名
     */
    private String realName ;

    /**
     *  身份证号
     */
    private String idCard ;

    /**
     * 头像url地址
     */
    private String headImgUrl;

    private boolean lockFlag ;

    public AnchorVo(){}

    public AnchorVo(String anchorId, String nickname, String account, String realName, String idCard, String headImgUrl, boolean lockFlag) {
        this.anchorId = anchorId;
        this.nickname = nickname;
        this.account = account;
        this.realName = realName;
        this.idCard = idCard;
        this.headImgUrl = headImgUrl;
        this.lockFlag = lockFlag ;
    }

    public String getAnchorId() {
        return anchorId;
    }

    public void setAnchorId(String anchorId) {
        this.anchorId = anchorId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public boolean isLockFlag() {
        return lockFlag;
    }

    public void setLockFlag(boolean lockFlag) {
        this.lockFlag = lockFlag;
    }
}

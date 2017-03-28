package org.live.live.entity;

import org.live.common.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *  申请表，申请成为主播。
 * Created by Mr.wang on 2017/3/28.
 */
public class ApplyAnchor extends BaseEntity {

    /**
     *  用户
     */
    @OneToOne
    @JoinColumn(name="mobile_user_id")
    private MobileUser user ;

    /**
     *  身证份号
     */
    @Column
    private String idCard ;

    /**
     *  真实姓名
     */
    @Column
    private String realName ;

    /**
     *   身份证正面照
     */
    @Column
    private String idImgUrl ;

    public MobileUser getUser() {
        return user;
    }

    public void setUser(MobileUser user) {
        this.user = user;
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

    public String getIdImgUrl() {
        return idImgUrl;
    }

    public void setIdImgUrl(String idImgUrl) {
        this.idImgUrl = idImgUrl;
    }
}

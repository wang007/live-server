package org.live.live.entity;

import org.live.common.base.BaseEntity;

import javax.persistence.*;

/**
 *  申请表，申请成为主播。
 * Created by Mr.wang on 2017/3/28.
 */
@Entity
@Table(name="live_apply_anchor")
public class ApplyAnchor extends BaseEntity {

    /**
     * 未审核
     */
    public final static int NOT_AUDIT = 0 ;

    /**
     * 审核通过
     */
    public final static int ADUIT_PASS = 1 ;

    /**
     * 审核不通过
     */
    public final static int ADUIT_NOT_PASS = 2 ;

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

    /**
     *  是否已经审核. false: 未审核， true： 已审核
     */
    @Column
    private boolean auditFlag ;

    /**
     * 是否审核通过， 0. 未审核， 1.审核通过， 2.审核不通过
     */
    @Column
    private int passFlag ;

    /**
     * 审核不通过的原因
     */
    @Column
    private String noPassReason ;

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

    public boolean isAuditFlag() {
        return auditFlag;
    }

    public void setAuditFlag(boolean auditFlag) {
        this.auditFlag = auditFlag;
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
}

package org.live.live.entity;

import org.live.common.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

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
     * 审核被忽略
     */
    public final static int ADUIT_IGNORE = 3 ;

    /**
     *  用户
     */
    @ManyToOne
    @JoinColumn(name="mobile_user_id")
    private MobileUser user ;

    @ManyToOne
    @JoinColumn(name="category_id")
    private LiveCategory category ;

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
     * 是否审核通过， 0. 未审核， 1.审核通过， 2.审核不通过，3.忽略审核。（当存在审核通过就要忽略掉）
     */
    @Column
    private int passFlag ;

    /**
     * 审核不通过的原因
     */
    @Column
    private String noPassReason ;

    /**
     * 创建时间
     */
    @Column
    private Date createTime ;

    public MobileUser getUser() {
        return user;
    }

    public void setUser(MobileUser user) {
        this.user = user;
    }

    public LiveCategory getCategory() {
        return category;
    }

    public void setCategory(LiveCategory category) {
        this.category = category;
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
}

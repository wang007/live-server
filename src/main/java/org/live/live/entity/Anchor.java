package org.live.live.entity;

import org.live.common.base.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Mr.wang on 2017/3/28.
 */
@Entity
@Table(name="live_anchor")
public class Anchor extends BaseEntity {

    /**
     *  移动端的引用
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    private MobileUser user ;

    /**
     * 身份证号
     */
    @Column
    private String idCard ;

    /**
     *  真实姓名
     */
    @Column
    private String realName ;

    /**
     *  创建时间，即成为主播的时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime ;

    /**
     *  个性签名
     */
    @Column
    private String description ;


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
}

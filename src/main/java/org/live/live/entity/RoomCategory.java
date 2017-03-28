package org.live.live.entity;

import org.live.common.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *  房间分类
 * Created by Mr.wang on 2017/3/28.
 */
@Entity
@Table(name="live_room_category")
public class RoomCategory extends BaseEntity {

    /**
     *  分类名称
     */
    @Column
    private String categoryName ;

    /**
     * 分类图片的url
     */
    @Column
    private String categoryUrl ;

    /**
     *  分类简介
     */
    @Column
    private String description ;

    /**
     *  分类启用的标记, 默认是启用
     */
    @Column
    private boolean enabled = true ;

    /**
     * 序列号，用于排序
     */
    private int serailNo ;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryUrl() {
        return categoryUrl;
    }

    public void setCategoryUrl(String categoryUrl) {
        this.categoryUrl = categoryUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getSerailNo() {
        return serailNo;
    }

    public void setSerailNo(int serailNo) {
        this.serailNo = serailNo;
    }
}

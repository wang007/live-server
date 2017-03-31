package org.live.live.entity;

import org.live.common.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *  直播房间分类
 * Created by Mr.wang on 2017/3/28.
 */
@Entity
@Table(name="live_category")
public class LiveCategory extends BaseEntity {

    /**
     *  分类名称
     */
    @Column
    private String categoryName ;

    /**
     * 分类封面的url
     */
    @Column
    private String coverUrl ;

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

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
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

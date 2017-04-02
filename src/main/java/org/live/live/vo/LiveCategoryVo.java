package org.live.live.vo;

/**
 *
 * Created by Mr.wang on 2017/4/2.
 */
public class LiveCategoryVo {

    /**
     *  分类的id
     */
    private String id ;

    /**
     *  分类名称
     */
    private String categoryName ;

    /**
     *  分类简介
     */
    private String description ;

    /**
     *  分类启用的标记, 默认是启用
     */
    private boolean enabled = true ;

    /**
     * 序列号，用于排序
     */
    private int serailNo ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

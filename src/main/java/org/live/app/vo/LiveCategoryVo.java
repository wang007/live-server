package org.live.app.vo;

/**
 *  移动端 直播分类的vo
 * Created by Mr.wang on 2017/4/4.
 */
public class LiveCategoryVo {

    private String id ;

    private String categoryName ;

    private String coverUrl ;

    public LiveCategoryVo(){}

    public LiveCategoryVo(String id, String categoryName, String coverUrl) {
        this.id = id ;
        this.categoryName = categoryName ;
        this.coverUrl = coverUrl ;
    }


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

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
}

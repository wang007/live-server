package org.live.app.vo;


/**
 * 申请成为直播的申请表
 * Created by wang on 2017/4/15.
 */
public class ApplyAnchorVo {

    /**
     *  移动端用户id
     */
    private String userId ;

    /**
     * 身份证号
     */
    private String idCard ;

    /**
     *  真实姓名
     */
    private String realName ;

    /**
     *  身份证正面照
     */
    private String idImgUrl ;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

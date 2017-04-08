package org.live.school.vo;

/**
 * Created by KAM on 2017/4/8.
 */
public class SimpleMemberVo {
    private String id;
    private String memberNo;
    private String realName;

    public SimpleMemberVo(String id, String memberNo, String realName){
        this.id= id;
        this.memberNo = memberNo;
        this.realName = realName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }


}

package org.live.app.vo;

/**
 * 移动端登录返回的vo
 * Created by wang on 2017/4/12.
 */
public class LoginResponseVo {

    /**
     *  响应信息
     */
    private String message ;

    /**
     *  响应状态. 1.登录成功。 0. 登录失败
     */
    private int status ;

    private MobileUserVo data ;

    public void success() {
        this.status = 1 ;
    }

    public void error() {
        this.status = 0 ;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public MobileUserVo getData() {
        return data;
    }

    public void setData(MobileUserVo data) {
        this.data = data;
    }
}

package org.live.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 *  响应ajax的数据载体的实现
 *  响应复杂的数据类型. list集合+map集合
 *
 * Created by Mr.wang on 2016/11/24.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)	//值为null的属性不参与序列化
public class CompleteResponseModel extends BatchResponseModel {

    /**
     * map类型的数据载体
     */
    private Map<String,Object> body ;

    public CompleteResponseModel() {
        super() ;
    }

    public CompleteResponseModel(Map body) {
        super();
        this.body = body ;
    }

    public CompleteResponseModel(int status, String message, Map body) {
        super() ;
        this.setStatus(status) ;
        this.setMessage(message);
        this.setBody(body) ;

    }


    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

}

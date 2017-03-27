package org.live.common.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.live.common.constants.MessageConstants;

/**
 * 响应ajax的数据载体的实现
 * 响应单条数据时使用该类
 *
 *
 * Created by Mr.wang on 2016/11/24.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)	//值为null的属性不参与序列化
public class  SimpleResponseModel<T> implements ResponseModel<T> {

    /**
     *  响应状态
     */
    private int status ;

    /**
     * 响应信息
     */
    private String message ;

    /**
     * 响应数据载体，单条
     */
    private T data ;

    public SimpleResponseModel(){}

    public SimpleResponseModel(T data) {
        this.data = data ;
    }

    public SimpleResponseModel(int status, String message, T data) {

        this.setStatus(status);
        this.setMessage(message) ;
        this.setData(data) ;
    }


    @Override
    public int getStatus() {
        return status ;
    }

    @Override
    public SimpleResponseModel setStatus(int status) {
        this.status = status ;
        return this ;
    }

    @Override
    public String getMessage() {
        return this.message ;
    }

    @Override
    public SimpleResponseModel setMessage(String message) {
          this.message = message ;
          return this ;
    }

    @Override
    public T getData() {
        return this.data ;
    }

    @Override
    public SimpleResponseModel setData(T data) {
        this.data = data ;
        return this ;
    }

    @Override
    public ResponseModel error() {

        status = MessageConstants.OPERATION_ERROR_CODE ;
        message = MessageConstants.OPERATION_ERROR_MSG ;
        return this ;
    }

    @Override
    public SimpleResponseModel error(String message) {

        status = MessageConstants.OPERATION_ERROR_CODE ;
        this.message = message ;
        return this;
    }

    @Override
    public SimpleResponseModel success() {

        this.status = MessageConstants.OPERATION_SUCCESS_CODE ;
        this.message = MessageConstants.OPERATION_SUCCESS_MSG ;
        return this ;
    }

    @Override
    public SimpleResponseModel success(String message) {

        this.status = MessageConstants.OPERATION_SUCCESS_CODE ;
        this.message = message ;
        return this;
    }
}

package org.live.common.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.live.common.constants.MessageConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * 响应ajax的数据载体的实现
 * 响应多条数据时使用该类
 *
 * Created by Mr.wang on 2016/11/24.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)	//值为null的属性不参与序列化
public class BatchResponseModel implements ResponseModel<List> {

    private int status ;

    private String message ;

    /**
     * 响应数据载体，list集合
     */
    private List<?> data ;

    public BatchResponseModel() {
        data = new ArrayList<Object>() ;
    }

    public BatchResponseModel(List data) {
        this.data = data ;
    }

    public BatchResponseModel(int status, String message, List data) {
        this.setStatus(status) ;
        this.setMessage(message) ;
        this.data = data ;
    }


    @Override
    public int getStatus() {
        return this.status ;
    }

    @Override
    public BatchResponseModel setStatus(int status) {
        this.status = status ;
        return this ;
    }

    @Override
    public String getMessage() {
        return this.message ;
    }

    @Override
    public BatchResponseModel setMessage(String message) {
        this.message = message ;
        return this ;
    }

    @Override
    public List getData() {
        return this.data ;
    }

    @Override
    public BatchResponseModel setData(List data) {
        this.data = data ;
        return this ;
    }

    @Override
    public BatchResponseModel error() {

        this.status = MessageConstants.OPERATION_ERROR_CODE ;
        this.message = MessageConstants.OPERATION_ERROR_MSG ;
        return this;
    }

    @Override
    public BatchResponseModel error(String message) {

        this.status = MessageConstants.OPERATION_ERROR_CODE ;
        this.message = message ;
        return this ;
    }

    @Override
    public BatchResponseModel success() {

        this.status = MessageConstants.OPERATION_SUCCESS_CODE ;
        this.message = MessageConstants.OPERATION_SUCCESS_MSG ;
        return this;
    }

    @Override
    public BatchResponseModel success(String message) {

        this.status = MessageConstants.OPERATION_SUCCESS_CODE ;
        this.message = message ;
        return this;
    }
}

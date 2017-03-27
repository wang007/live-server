package org.live.common.response;

/**
 *  响应ajax的数据载体　接口
 *
 * Created by Mr.wang on 2016/11/24.
 */
public interface ResponseModel<T> {

    /**
     * 获取状态码
     * @return
     */
    public int getStatus() ;

    /**
     *  设置状态码
     * @param status
     */
    public ResponseModel setStatus(int status) ;

    /**
     *  获取响应信息
     * @return
     */
    public String getMessage() ;

    /**
     * 设置响应信息
     */
    public ResponseModel setMessage(String message) ;

    /**
     *  获取数据模型
     * @return
     */
    public T getData() ;

    /**
     * 设置数据模型
     * @param T
     */
    public ResponseModel setData(T data) ;

    /**
     *  响应错误
     * @return
     */
    public ResponseModel error() ;

    /**
     * 响应错误
     * @param message 错误信息
     * @return
     */
    public ResponseModel error(String message) ;

    /**
     *  响应成功
     * @return
     */
    public ResponseModel success() ;

    /**
     * 响应成功
     * @param message 成功信息
     * @return
     */
    public ResponseModel success(String message) ;
}

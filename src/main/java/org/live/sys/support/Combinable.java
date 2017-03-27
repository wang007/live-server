package org.live.sys.support;

import java.util.List;

/**
 * 可以用于拼接的基接口
 * 继承此接口可调用拼装方法
 * Created by Mr.wang on 2016/12/4.
 */
public interface Combinable {


    /**
     * 用于比较，确定传入的compareObj是不是它的父亲
     * @param compareObj
     * @return
     */
    public boolean compare(Object compareObj) ;

    /**
     * 设置子对象，用于保存数据
     * @param combinables
     */
    public  void setChilds(List<? extends Combinable> combinables) ;
}

package org.live.common.base;

import java.io.Serializable;
import java.util.List;

/**
 * service的基接口
 *
 * @author  Mr.wang
 */
public interface BaseService<T extends BaseEntity,ID extends Serializable>{
	
    /**
     * 查询单条数据 ， by id
     * @param id 实体id
     */
    public  T findOne(ID id) ;


    /**
     * 查询单条数据 ， by id
     * @param id 实体id
     */
    public T get(ID id) ;

    /**
     * 查询全部实体数据
     * @return
     */
    public List<T> findAll();


    /**
     * 保存实体. 保存create和update操作
     *
     * @param entity 实体
     * @return 实体
     */
    public T save(T entity) ;

    /**
     * 保存实体集合 .保存create和update操作
     * @param entities
     * @return 保存的实体集合
     */
    public List<T> save(Iterable<T> entities);

    /**
     * 删除单条数据,by id
     * @param id 实体id
     */
    public void delete(ID id) ;


    /**
     * 删除单条数据，by 实体
     * @param entity
     */
    public void delete(T entity) ;

    /**
     * 删除实体集合
     * @param entities
     */
    public void delete(Iterable<T> entities) ;



}

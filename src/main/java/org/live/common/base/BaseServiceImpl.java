package org.live.common.base;


import java.io.Serializable;
import java.util.List;


/**
 * 基类service的实现
 * @author Mr.wang
 */
public abstract class BaseServiceImpl<T extends BaseEntity,ID extends Serializable> implements BaseService<T,ID> {
	
	/**
	 *  获取repository层的接口
	 * @return
	 */
	protected abstract  BaseRepository<T,ID> getRepository() ;

    /**
     * 查询单条数据 ， by id
     * @param id 实体id
     */
	public  T findOne(ID id) {	
		if(id == null) return null ;
    	return this.getRepository().findOne(id) ;
	}

    /**
     * 查询单条数据 ， by id
     * @param id 实体id
     */
	@Override
	public T get(ID id) {
		
		return this.findOne(id) ;
	}

    /**
     * 查询全部实体数据
     * @return
     */
	@Override
	public List<T> findAll() {
		
		return this.getRepository().findAll() ;
	}

    /**
     * 保存实体. 保存create和update操作
     * 
     * @param entity 实体
     * @return 实体
     */
	@Override
	public T save(T entity) {
		if(entity == null) return null ;
    	return this.getRepository().save(entity) ;
	}

    /**
     * 保存实体集合 .保存create和update操作
     * @param entities
     * @return 保存的实体集合
     */
	@Override
	public List<T> save(Iterable<T> entities) {
		return this.getRepository().save(entities) ;
	}

    /**
     * 删除单条数据,by id
     * @param id 实体id
     */
	@Override
	public void delete(ID id) {
		if(id == null) return ;
    	this.getRepository().delete(id) ;
		
	}

    /**
     * 删除单条数据，by 实体
     * @param entity
     */
	@Override
	public void delete(T entity) {
		if(entity == null) return ;
    	this.getRepository().delete(entity); 		
	}

    /**
     * 删除实体集合
     * @param entities
     */
	@Override
	public void delete(Iterable<T> entities) {
		this.getRepository().delete(entities);	
	}


}

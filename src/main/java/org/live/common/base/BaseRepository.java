package org.live.common.base;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 *持久层的基接口
 *
 * 调用该接口自定义的方法时，是BaseRepositoryImpl的实现，必须在该目录下新建
 * xxxRepositoryImpl（不需要实现xxxRepository接口）并继承BaseRepositoryImpl <br/>
 * 
 * @see http://www.blogjava.net/badqiu/archive/2008/08/07/220569.html 	xsql教程
 *
 * @author Mr.wang
 *
 *
 */
@NoRepositoryBean
public interface  BaseRepository<T extends BaseEntity,ID extends Serializable> extends JpaRepository<T,ID>, JpaSpecificationExecutor<T> {
	
	/**
	 * 
	 * 将xsql转换成sql或hql语句
	 * @param xsql 
	 * @param filter 过滤的条件
	 * @return
	 */
	//public String xsqlConvertSql(String xsql, Map<String, Object> filter) ;
	
	/**
	 * 将xsql转换成sql或hql语句
	 * 
	 * @param xsql
	 * @param filter 过滤的条件
	 * @return
	 */
	//public String xsqlConvertHql(String xsql, Map<String, Object> filter) ;
	
	/**
	 * 通过hql语句查询从第from条开始，一共查size条数据 
	 * @param hql
	 * @param from
	 * @param size
	 * @return
	 */
	//public <E> List<E> find(String hql, int from, int size) ;
	
	//public <E> List<E> find(String hql, Object[] params, int from, int size) ;
	
	/**
	 * 
	 * @param sql
	 * @param params
	 * @param from
	 * @param size
	 * @param resultClass 结果集的类型
	 * @return
	 */
	//public <E> List<E> findBySql(String sql, Object[] params, int from, int size, Class<E> resultClass) ;
	
	/**
	 * 
	 * @param pageable
	 * @param hql
	 * @param params
	 * @return
	 */
	//public <E> Page<E> find(Pageable pageable, String hql, Object[] params) ;
	
	/**
	 * 
	 * @param pageable
	 * @param sql
	 * @param params
	 * @param resultClass 结果集的类型
	 * @return
	 */
	//public <E> Page<E> findBySql(Pageable pageable, String sql, Object[] params, Class<E> resultClass) ;
	

	/**
	 * 查询结果记录数
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	//public Long count(String hql, Object[] params) ;
	
	/**
	 * 查询结果记录数
	 * @param sql
	 * @param params
	 * @return
	 */
	//public Long countBySql(String sql, Object[] params) ;
	

}

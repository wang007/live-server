package org.live.common.base;

import com.mool.xsqlbuilder.XsqlBuilder;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

;

/**
 * repository的基层实现 当没有复杂的sql，hql查询时，并不需要实现RepositoryImpl
 * <p>
 * Created by Mr.wang on 2016/11/28.
 */
public abstract class BaseRepositoryImpl {

	@PersistenceContext
	private EntityManager em;

	private XsqlBuilder builder;

	public BaseRepositoryImpl() {
		this.builder = new XsqlBuilder();
	}

	/**
	 * 获取xsqlBuilder
	 *
	 * @return xsqlBuilder
	 */
	protected XsqlBuilder getBuilder() {
		return this.builder;
	}

	/**
	 * 获取entityManager，进行持久化操作
	 *
	 * @return
	 */
	protected EntityManager getEntityManager() {
		return this.em;
	}

	/**
	 * 获取ql统计总记录数ql语句
	 *
	 * @param ql
	 *            sql或hql等查询语句
	 * @return
	 */
	public String getCountQl(String ql) {
		int fromIndex = ql.indexOf("from ");
		fromIndex = fromIndex == -1 ? ql.indexOf("FROM ") : fromIndex;
		// 截取from后的语句
		String hqlFrom = ql.substring(fromIndex);
		return "select count(*) " + hqlFrom;
	}

	/**
	 * 将xsql转换成sql或hql语句
	 * <p>
	 * {}中的参数将会被替换成 ? ,xsqlConvertHql方法的唯一区别 <br/>
	 * []中的参数将被替换成filter中的实际值
	 *
	 * @param xsql
	 *            xsql语句
	 * @param filter
	 *            map类型替换xsql占位符中的值
	 * @return sql或hql语句
	 */
	public String xsqlConvertSql(String xsql, Map<String, Object> filter) {
		return getBuilder().generateSql(xsql, filter).getXsql();
	}

	/**
	 * 将xsql转换成sql或hql语句
	 * <p>
	 * {}中的参数将会被替换成 :占位符名称 ,xsqlConvertSql方法的唯一区别 <br/>
	 * []中的参数将被替换成filter中的实际值<br/>
	 *
	 * @param xsql
	 *            xsql语句
	 * @param filter
	 *            map类型替换xsql占位符中的值
	 * @return sql或hql语句
	 */
	public String xsqlConvertHql(String xsql, Map<String, Object> filter) {
		return getBuilder().generateHql(xsql, filter).getXsql();
	}

	public <T> List<T> find(String hql, int from, int size) {
		return find(hql, null, from, size);
	}

	/**
	 * 通过hql语句查询从第from条开始，一共查size条数据
	 *
	 * @param hql
	 * @param from
	 *            从第几条数据开始 index: 0开始
	 * @param size
	 *            查询多少条数据
	 * @param <T>
	 * @return 小于或等于size数据的list集合
	 * @parsm 查询的参数
	 */

	@SuppressWarnings("unchecked")
	public <T> List<T> find(String hql, Object[] params, int from, int size) {
		Query query = getEntityManager().createQuery(hql);
		if (params != null) {
			for (int i = 0, length = params.length; i < length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return (List<T>) query.setFirstResult(from).setMaxResults(size).getResultList();
	}

	/**
	 * 查询结果记录数
	 *
	 * @param hql
	 * @param params
	 * @return
	 */
	public long count(String hql, Object[] params) {
		Query query = getEntityManager().createQuery(this.getCountQl(hql));
		if (params != null) {
			for (int i = 0, length = params.length; i < length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return (long) query.getSingleResult();
	}

	/**
	 * @param pageable
	 * @param hql
	 * @param params
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> Page<T> find(Pageable pageable, String hql, Object[] params) {
		hql = this.createOrderQl(pageable, hql);
		Query query = getEntityManager().createQuery(hql);
		if (params != null) {
			for (int i = 0, length = params.length; i < length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		if (pageable != null) {
			query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
					.setMaxResults(pageable.getPageSize());
			return new PageImpl<T>(query.getResultList(), pageable, this.count(hql, params));
		} else {
			return new PageImpl<T>(query.getResultList());
		}
	}

	/**
	 * 查询结果记录数
	 *
	 * @param sql
	 * @param params
	 * @return
	 */
	public long countBySql(String sql, Object[] params) {
		Query query = getEntityManager().createNativeQuery(this.getCountQl(sql));
		if (params != null) {
			for (int i = 0, length = params.length; i < length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		BigInteger count = (BigInteger) query.getSingleResult();
		return count.longValue();
	}

	/**
	 * 通过sql语句查询从第from条开始，一共查size条数据
	 *
	 * @param sql
	 * @param params
	 * @param from
	 * @param size
	 * @param resultClass
	 *            结果集的类型
	 * @param <T>
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findBySql(String sql, Object[] params, int from, int size, Class<T> resultClass) {

		Query query = getEntityManager().createNativeQuery(sql);
		query.unwrap(org.hibernate.Query.class).setResultTransformer(Transformers.aliasToBean(resultClass));
		if (params != null) {
			for (int i = 0, length = params.length; i < length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return (List<T>) query.setFirstResult(from).setMaxResults(size).getResultList();
	}

	@SuppressWarnings("unchecked")
	public <T> Page<T> findBySql(Pageable pageable, String sql, Object[] params, Class<T> resultClass) {
		sql = this.createOrderQl(pageable, sql);
		Query query = getEntityManager().createNativeQuery(sql);
		query.unwrap(org.hibernate.Query.class).setResultTransformer(Transformers.aliasToBean(resultClass));
		if (params != null) {
			for (int i = 0, length = params.length; i < length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		if (pageable != null) {
			query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
					.setMaxResults(pageable.getPageSize());
			return new PageImpl<T>(query.getResultList(), pageable, this.countBySql(sql, params));
		} else {
			return new PageImpl<T>(query.getResultList());
		}
	}

	/**
	 * 根据pageable中的信息构建 排序查询语句.
	 * 
	 * @param pageable
	 *            分页信息
	 * @param qlString 查询语句
	 *
	 * @return
	 */
	public String createOrderQl(Pageable pageable, String qlString) {
		if (pageable != null && pageable.getSort() != null) {
			if (!StringUtils.containsIgnoreCase(qlString, "order by")) { // 查询中有排序的话，就不再构建
				StringBuilder qlStringSb = new StringBuilder(qlString);
				Sort sort = pageable.getSort();
				Iterator<Sort.Order> iter = sort.iterator();
				boolean flag = true;
				while (iter.hasNext()) {
					Sort.Order order = iter.next();
					String property = order.getProperty();
					String strDirection = order.getDirection().name();
					if (flag) {
						qlStringSb.append(" ORDER BY ");
						flag = false;
					}
					qlStringSb.append(property).append(" ").append(strDirection).append(",");
				}
				qlString = qlStringSb.substring(0, qlStringSb.length() - 1);
			}
		}
		return qlString;
	}

	/**
	 * 根据sql语句和参数查询记录
	 * 
	 * @param sql
	 * @param params
	 * @param resultClass
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> findBySql(String sql, Object[] params, Class<T> resultClass) {

		Query query = getEntityManager().createNativeQuery(sql);
		query.unwrap(org.hibernate.Query.class).setResultTransformer(Transformers.aliasToBean(resultClass));
		if (params != null) {
			for (int i = 0, length = params.length; i < length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return (List<T>) query.getResultList();
	}

	/**
	 * 根据hql语句和参数查询记录
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> find(String hql, Object[] params) {
		Query query = getEntityManager().createQuery(hql);
		if (params != null) {
			for (int i = 0, length = params.length; i < length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return (List<T>) query.getResultList();
	}

}

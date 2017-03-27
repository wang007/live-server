package org.live.common.page;

import java.util.List;

/**
 *  将分页bean转换成JqGridModel，然后再用json输出，
 *
 *  用于jqGrid插件
 * 
 * @author Mr.wang
 *
 */
public class JqGridModel<E> {
	
	/**
	 * {"page":"1","total":2,"records":"13", "rows":[]}
	 */
	
	/**
	 * 当前页
	 */
	private int page ;
	
	/**
	 * 总页数
	 */
	private int total ;
	
	/**
	 * 总记录数
	 */
	private long records ;
	
	/**
	 * 数据模型
	 */
	private List<E> rows ;
	

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public long getRecords() {
		return records;
	}

	public void setRecords(long records) {
		this.records = records;
	}

	public List<E> getRows() {
		return rows;
	}

	public void setRows(List<E> rows) {
		this.rows = rows;
	}

}

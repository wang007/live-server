package org.live.common.constants;

/**
 * 分页时所需要的一些常量
 * @author Mr.wang
 *
 */
public interface PageConstants {
	
	/**
	 * 默认每页为10条记录
	 */
	public static final int DEFAULT_PAGE_SIZE = 10;
	
	/**
	 * 当前页数
	 */
	public static final int DEFAULT_CURRENT_PAGE = 1 ;
	
	/**
	 * jqgrid中代表实际模型数据的入口
	 */
	public static final String JQGRID_ROWS = "rows";
	
	/**
	 * jqgrid中代表当前页码的数据
	 */
	public static final String JQGRID_PAGE = "page";
	
	/**
	 * jqgrid中代表页码总数的数据
	 */
	public static final String JQGRID_TOTAL = "total";
	
	/**
	 * jqgrid中代表数据行总数的数据
	 */
	public static final String JQGRID_RECORDS = "records";
	

}

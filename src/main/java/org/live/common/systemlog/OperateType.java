package org.live.common.systemlog;

/**
 * 用与systemLog注解中，标识用户的操作
 * 
 * @author Mr.wang
 *
 */
public enum OperateType {
	
	/**
	 * 添加操作
	 */
	ADD("添加") ,
	
	/**
	 * 更新操作
	 */
	UPDATE("更新") ,
	
	/**
	 * 删除操作
	 */
	DELETE("删除"),
	
	/**
	 * 查询操作
	 */
	QUERY("查询") ,
	
	/**
	 * 常规操作
	 */
	ROUTINE_OPERATE("常规操作") ;
	
	private String operateType ;
	
	private OperateType(String operateType) {
		
		this.setOperateType(operateType) ;
	}
	
	@Override
	public String toString() {
		
		return this.operateType ;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

}

package org.live.common.systemlog;

/**
 * <p> 系统日志级别，用于标识systemLog注解中的日志级别 </p>
 * <p> 实现日志输出可控制 </p>
 * 
 * @author Mr.wang
 *
 */
public enum LogLevel {
	
	/**
	 * info级别，
	 */
	INFO(1),
	
	/**
	 * warn
	 */
	WARN(2) ,
	
	/**
	 * error
	 */
	ERROR(3) ;

	/**
	 * 1. info级别
	 * 2. warn级别
	 * 3. error级别
	 */
	private int level ;
	
	private LogLevel(int level ) {
		
		this.setLevel(level) ;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}


}
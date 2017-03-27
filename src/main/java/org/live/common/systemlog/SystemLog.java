package org.live.common.systemlog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p> 系统日志，用于记录用户的操作。 </p>
 * 
 * <p> 该注解用于controller中，用于记录用户的操作。 </p>
 * <p>
 * description:简述是怎样的操作
 * operateType：操作类型：增删改查。默认值为OperateType.ROUTINE_OPERATE
 * logLevel:操作日志级别： 默认值为 LogLevel.INFO 
 * 
 * @author Mr.wang
 */
@Target(ElementType.METHOD)	//只能注解到方法上
@Retention(RetentionPolicy.RUNTIME)	
public @interface SystemLog {
	
	/**
	 * controller中这个方法的描述
	 * 简述该方法的作用
	 * @return
	 */
	public String description() ;
	
	/**
	 * 操作类型 例如：删除，查询，更新，添加等等操作，默认是常规操作。
	 * 但是需要设置，操作类型，最好不要是默认值，根据实际情况设置。
	 * 
	 * 
	 * @return
	 */
	public OperateType operateType() default OperateType.ROUTINE_OPERATE ;
	
	/**
	 * 系统日志级别 ，默认是INFO级别，但根据方法调节级别。
	 * 删除操作的话，一定ERROR级别。
	 * 修改操作的话，建议用WARN级别。
	 * @return
	 */
	public LogLevel logLevel() default LogLevel.INFO ;

}


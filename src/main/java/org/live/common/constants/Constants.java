package org.live.common.constants;

/**
 * 项目中用到的一些公共常量
 * 
 * 这些常量同步到数据字典表中
 * 
 * @author Mr.wang
 *
 */
public interface Constants {
	
	/**
	 * 当前登录用户
	 * session中用户对象的key
	 */
	public static final String CURRENT_LOGINUSER_KEY = "loginUser" ;
	
	/**
	 * 当前登录的用户类型
	 * session中用户类型的key
	 */
	public static final String CURRENT_USERTYPE_KEY = "userType";
	
	/**
	 * 初始化密码
	 */
	public static final String INITIAL_PASSWORD = "123456" ;	//md5+base64 : 4QrcOUm6Wau+VuBX8g+IPg==

	/**
	 * 默认字符集
	 */
	public static final String DEFAULT_CHARSET = "UTF-8";
	
	/**
	 * 数据字典：系统中判断是否：是
	 */
	public static final int DIC_YES = 1 ;
	
	/**
	 * 数据字典：系统中判断是否：否
	 */
	public static final int DIC_NO = 0 ;


	/**
	 * 数据字典：系统中默认值：零(int类型)
	 */
	public static final int DEFULT_VALUE_INTEGRL = 0 ;
	
	
	/**
	 * 数据字典：系统中默认值：零(string类型)
	 */
	public static final String DEFULT_VALUE_GRADE = "gradeOrdinary" ;
	


}

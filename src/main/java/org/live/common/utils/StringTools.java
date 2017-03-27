package org.live.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;


/**
 * String的工具类
 * 
 * @author Mr.wang
 *
 */
public class StringTools {

	/**
	 * 将javabean中本类的String的类型的值进行trim。不包括父类的
	 * 
	 * @param obj 
	 * @return String的类型的值进行trim之后的javabean
	 */
	public static Object trimObject(Object obj) {
		if(obj == null) {
			return null ;
		}
		 Class<? extends Object> clazz = obj.getClass() ;
		 Field[] fields = clazz.getDeclaredFields() ;
		 for(Field f:fields) {
			if(f.getType().equals(String.class)) {
				try {
					f.setAccessible(true);
					f.set(obj, StringUtils.trim((String)f.get(obj)));
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e) ;			
				}
			}
		 }	 
		 return obj ;
	}

	
}

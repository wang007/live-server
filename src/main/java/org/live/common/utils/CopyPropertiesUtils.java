package org.live.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * bean之间copy的工具，依赖spring <br/>
 * 更多bean的操作功能，操作，apache的BeanUtils和PropertyUtils,  spring的beanutils
 * 
 * @author Mr.wang
 *
 */
public class CopyPropertiesUtils {

	/**
	 * bean之间copy，忽略null值属性，即@param source中的null值属性不copy到dest属性
	 * 
	 * @param dest 源对象
	 * @param source 目标对象
	 */
	public static void copyPropertiesIgnoreNull(Object dest, Object source) {
		
		BeanUtils.copyProperties(source, dest, getNullPropertyNames(source)) ;
	}
	
	/**
	 * 获取source对象中null值的属性key
	 * 
	 * @param source
	 * @return source中null值的key 属性
	 */
	public static String[] getNullPropertyNames(Object source) {	
		final BeanWrapper wrapper = new BeanWrapperImpl(source) ;
		Set<String> nullPropertyNameSet = new HashSet<String>() ;	
		PropertyDescriptor[] pds = wrapper.getPropertyDescriptors() ;
		for(PropertyDescriptor pd : pds) {	
			if(wrapper.getPropertyValue(pd.getName()) == null) {
				nullPropertyNameSet.add(pd.getName()) ;	
			}
		}	
		String[] result = new String[nullPropertyNameSet.size()] ;
		return nullPropertyNameSet.toArray(result) ;

	}
	

}

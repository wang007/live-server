package org.live.common.support;

import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * properties文件数据的持有者
 * 
 * 获取properties配置文件的数据，可在非spring容器管理的bean中使用
 * 
 * @author Mr.wang
 *
 */
@Component
public class PropertiesDataHolder implements EmbeddedValueResolverAware {
	
	private static final String PROPERTIES_KEY_PREFIX = "${" ;
	
	private static final String PROPERTIES_KEY_POSTFIX = "}" ;
	
	private static StringValueResolver resolver ;
	
	private PropertiesDataHolder(){}

	public void setEmbeddedValueResolver(StringValueResolver resolver) {
		
		PropertiesDataHolder.resolver = resolver ;
	}
	
	/**
	 * 获取value
	 * 
	 * @param key 配置文件定义的key
	 * @return 配置文件定义的value
	 */
	public static String getPropertiesValue(String key) {
		
		return PropertiesDataHolder.resolver.resolveStringValue(PROPERTIES_KEY_PREFIX+key+PROPERTIES_KEY_POSTFIX) ;	
	}

}

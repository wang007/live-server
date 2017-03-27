package org.live.common.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * spring容器的持有者,可以在非spring容器管理的bean
 * 
 * @author Mr.wang
 *
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

	private SpringContextHolder(){}
	
	private static ApplicationContext springContext ;
	
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		
		SpringContextHolder.springContext = context ;
	}
	
	/**
	 * 获取springcontext
	 * @return
	 */
	public static ApplicationContext getSpringContext() {
		
		return SpringContextHolder.springContext ;
	}
	
	/**
	 * 获取bean
	 * @param beanName bean的名称
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		
		return (T) springContext.getBean(beanName) ;
	}
	
	/**
	 * 获取bean
	 * @param requiredType bean的类型
	 * @return
	 */
	public static <T> T getBean(Class<T> requiredType) {

		return springContext.getBean(requiredType) ;
	}
	
	/**
	 * 获取spring容器中bean的集合
	 * @param type
	 * @return
	 */
	public static <T> Map<String, T> getBeansOfType(Class<T> type){
		
		return springContext.getBeansOfType(type) ;
	}
	
	
	

}

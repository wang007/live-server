package org.live.common.utils;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * json工具，基于jackson的实现
 * 
 * @author Mr.wang
 *
 */
public final class JsonUtils {

	/**
	 * jackson是安全线程的，直接将JACKSON共有出去，供外界调用
	 */
	public final static ObjectMapper JACKSON = new ObjectMapper();

	/**
	 * 加载类时对jackson做初始化工作
	 */
	static {
		JACKSON.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")) // 格式化日期
				.enable(Feature.ALLOW_SINGLE_QUOTES) // 设置允许单引号或者无引号的属性
				.enable(Feature.ALLOW_UNQUOTED_FIELD_NAMES);

		// 反序列化忽略未知属性
		JACKSON.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * 将java对象转换成json串
	 * 
	 * @param obj
	 * @return json字符串
	 */
	public static String toJson(Object obj) {
		try {
			return JACKSON.writeValueAsString(obj);

		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将json串转成java对象 jackson把json转换java对象时，要求json的格式非常严格
	 * 
	 * @param src
	 *            json串
	 * @param srcType
	 *            目标源对象
	 * @return T 指定的目标源对象
	 */
	public static <T> T fromJson(String src, Class<T> srcType) {
		try {
			return JACKSON.readValue(src, srcType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将map集合转换成java对象
	 * 
	 * @see 更多关于对象的转换可以直接调用JsonUtils中的JACKSON
	 * 
	 * @param map
	 * @param clazz
	 *            目标类
	 * @return 目标类对象
	 */
	public static <T> T convertMap2Object(Map<String, Object> map, Class<T> clazz) {

		return JACKSON.convertValue(map, clazz);
	}

	/**
	 * java对象转换成map集合
	 * 
	 * @param bean java对象
	 * @return 目标map集合
	 * @throws Exception
	 */
	public static Map<String, Object> convertBean(Object bean) throws Exception {
		Class<?> type = bean.getClass();
		Map<String, Object> returnMap = new HashMap<String, Object>();

		/**
		 * 内省
		 */
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

}

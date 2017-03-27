package org.live.dictionary.cache;


import org.live.dictionary.entity.DictType;
import org.live.dictionary.entity.Dictionary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 缓存数据字典
 * 
 * @author KAM
 *
 */
public class CacheDict {
	/**
	 * 所有的数据字典类型
	 */
	public static Map<String, DictType> allTypes = new HashMap<String, DictType>();
	/**
	 * 所有类型，对应的数据字典项
	 */
	public static Map<String, List<Dictionary>> dictList = new HashMap<String, List<Dictionary>>();

	/**
	 * 类型与字典项 由List 转成Map（key,value）
	 */
	public static Map<String, Map<String, String>> dictMap = new HashMap<String, Map<String, String>>();
	
	/**
	 * 存放所有字典记录的标识和对应的值
	 */
	public static Map<String, String> allDictMap = new HashMap<String, String>();

}

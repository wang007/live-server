package org.live.common.web.listenter;


import org.live.dictionary.service.DictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


/**
 * 数据字典缓存启动监听器
 * 
 * @author KAM
 *
 */
@WebListener
public class DictCacheListener implements ServletContextListener {

	@Resource
	private DictService dictService;

	private static final Logger LOGGER = LoggerFactory.getLogger(DictCacheListener.class) ;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {

		dictService.getCacheDic(); // 缓存数据字典

		LOGGER.info("执行数据字典读取");

		// ServletContext context = event.getServletContext();

		/**
		 * 将缓存字典存入servlet域中，方便页面使用字典值
		 * 
		 * "allTypes",以字典类型的标识符作为key,以类型实体作为value
		 * 
		 * "dictList",以字典类型标识符作为一组相同类型字典集合的key，以一组相同类型的字典集合作为value
		 * 
		 * "dictMap",以字典类型标识符作为一组相同类型的map字典集合的key,以一组相同类型的map字典集合作为value。
		 * 页面中以字典类型标识符+字典标识符进入检索。
		 * 
		 */
		/*
		 * if (CacheDict.allTypes.size() != 0) context.setAttribute("allTypes",
		 * CacheDict.allTypes); if (CacheDict.dictList.size() != 0)
		 * context.setAttribute("dictList", CacheDict.dictList); if
		 * (CacheDict.dictMap.size() != 0) context.setAttribute("dictList",
		 * CacheDict.dictMap);
		 */
		
	}

}

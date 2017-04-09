package org.live.common.web.listenter;


import org.live.dictionary.service.DictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


/**
 * 数据字典缓存启动监听器
 *
 * @author KAM
 */
@WebListener
public class DictCacheListener implements ServletContextListener {

    @Autowired
    private DictService dictService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DictCacheListener.class);


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        dictService.cacheDic(); // 缓存字典
        LOGGER.info("ServletContex初始化");
        LOGGER.info("数据字典初始化");
        LOGGER.info(sce.getServletContext().getServerInfo());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("ServletContex销毁");
    }


}

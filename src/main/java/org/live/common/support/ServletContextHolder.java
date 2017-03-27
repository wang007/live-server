package org.live.common.support;


import org.live.common.constants.SystemConfigConstants;
import org.live.config.dataComponent.SystemConfigDataComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

/**
 *  获取serlvet容器
 *  并完成一些变量初始化的操作
 *
 * Created by Mr.wang on 2016/11/25.
 */
@Component
public class ServletContextHolder implements ServletContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServletContextHolder.class) ;

    /**
     *  systemConfig.properties的数据载体
     */
    @Resource
    private SystemConfigDataComponent systemConfig ;

    private static ServletContext servletContext ;

    @Override
    public void setServletContext(ServletContext servletContext) {

        initProperties(servletContext) ;
        ServletContextHolder.servletContext = servletContext ;
    }

    public static ServletContext getServletContext() {
        return servletContext ;
    }

    /**
     *  从servlet容器中获取变量，并完成类型转换
     * @param name
     * @param <T>
     * @return
     */
    public static <T> T getAttribute(String name) {
       return (T) servletContext.getAttribute(name);
    }

    public static void setAttribute(String name, Object value) {
        servletContext.setAttribute(name,value);
    }


    /**
     *  获取项目的文件上传路径
     * @return
     */
    public static String getRealUploadFilePath() {

       return getAttribute(SystemConfigConstants.REAL_UPLOAD_FILE_DIR_KEY) ;
    }


    /**
     *  初始化一些属性到servlet容器中
     *  并初始化SystemConfig.properties中的 系统文件保存目录、上传文件保存的目录、项目中前端页面的title
     *
     */
    private void initProperties(ServletContext servletContext) {

        LOGGER.trace("执行常量的serlvetContext属性的初始化操作") ;
        String realProjectDirPath = servletContext.getRealPath(systemConfig.getProjectFileDirectory()) ;    //projectDir真实路径
        String realUploadFileDirPath = servletContext.getRealPath(systemConfig.getProjectUploadFileDirectory()) ;   //文件上传的真实路径

        servletContext.setAttribute(SystemConfigConstants.REAL_PROJECT_DIR_KEY, realProjectDirPath) ;   //保存到servlet容器中
        servletContext.setAttribute(SystemConfigConstants.REAL_UPLOAD_FILE_DIR_KEY, realUploadFileDirPath) ;
        servletContext.setAttribute(SystemConfigConstants.SYSTEM_TITLE_KEY, systemConfig.getTitle()) ;

        LOGGER.info(" real project Directory --> {}",realProjectDirPath) ;
        LOGGER.info(" real uploadFile Directory --> {}",realUploadFileDirPath) ;
        LOGGER.info(" system title --> {}", systemConfig.getTitle()) ;
    }


}

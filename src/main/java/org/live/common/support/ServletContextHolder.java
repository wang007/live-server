package org.live.common.support;


import org.live.common.constants.SystemConfigConstants;
import org.live.config.dataComponent.SystemConfigDataComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

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

    @Resource
    private UploadFilePathConfig pathConfig ;

    @Resource
    private Environment env ;

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
     *  初始化一些属性到servlet容器中
     *  并初始化SystemConfig.properties中的 系统文件保存目录、上传文件保存的目录、项目中前端页面的title
     *
     */
    private void initProperties(ServletContext servletContext) {

        LOGGER.info("执行常量的serlvetContext属性的初始化操作") ;

        String currentOS = System.getProperty("os.name").toLowerCase() ;
        LOGGER.info("current os ---> {}", currentOS) ;
        String uploadFileRootPath = null ;  //文件上传的根路径
        if(currentOS.contains("windows")) {
            uploadFileRootPath = systemConfig.getWindowsUploadFileRootPath();
        } else {
            uploadFileRootPath = systemConfig.getLinuxUploadFileRootPath() ;
        }
        String uploadFilePrefix = "/" + systemConfig.getUploadFilePathPrefix();   //文件上传的路径前缀
        String uploadFilePath = uploadFileRootPath + File.separator + systemConfig.getUploadFilePathPrefix() ;    //文件上传路径

        pathConfig.setUploadFileRootPath(uploadFileRootPath) ;
        pathConfig.setUploadFilePathPrefix(uploadFilePrefix) ;
        pathConfig.setUploadFilePath(uploadFilePath) ;

        //系统标题
        servletContext.setAttribute(SystemConfigConstants.SYSTEM_TITLE_KEY, systemConfig.getTitle()) ;

        try {
            InetAddress localHost = InetAddress.getLocalHost() ;
            servletContext.setAttribute(SystemConfigConstants.SYSTEM_IP_KEY, localHost.getHostAddress()) ;
            LOGGER.info("本地ip地址---> "+localHost.getHostAddress()) ;
        } catch (UnknownHostException e) {
            throw new RuntimeException("获取本地的ip地址异常", e) ;
        }

        String rtmpAddrPrefix = env.getProperty("system.rtmpAddrPrefix") ;
        servletContext.setAttribute(SystemConfigConstants.RTMP_ADDR_PREFIX_KEY, rtmpAddrPrefix) ;
        LOGGER.info("rtmp地址前缀——>" + rtmpAddrPrefix) ;

        servletContext.setAttribute(UploadFilePathConfig.UPLOAD_FILE_ROOT_PATH_KEY, uploadFileRootPath) ;
        servletContext.setAttribute(UploadFilePathConfig.UPLOAD_FILE_PATH_PREFIX_KEY, uploadFilePrefix) ;
        servletContext.setAttribute(UploadFilePathConfig.UPLOAD_FILE_PATH_KEY, uploadFilePath) ;

        LOGGER.info("system title ---> {}", systemConfig.getTitle());
        LOGGER.info("uplaodFileRootPath ---> {}", uploadFileRootPath) ;
        LOGGER.info("uploadFilePathPrefix ---> {}", uploadFilePrefix) ;
        LOGGER.info("uploadFilePath ---> {}", uploadFilePath) ;
    }


}

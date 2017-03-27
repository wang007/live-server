package org.live.common.constants;

/**
 * 项目设置的一些常量，和key、
 * 通过这些key在serlvetContext中获取value
 * @see { cn.school.common.support.ServletContextHolder }
 *
 * Created by Mr.wang on 2016/11/27.
 */
public interface SystemConfigConstants {

    /**
     *  相对于项目的目录的key，
     *  这个目录存放一些上传文件，日志文件，等
     */
    public static final String REAL_PROJECT_DIR_KEY = "projectDir" ;

    /**
     *  文件上传目录的key
     *  上传文件时，在该目录下添加一个自己的模块目录，然后上传文件保存到这个新添加的目录中
     *
     */
    public static final String REAL_UPLOAD_FILE_DIR_KEY = "uploadFileDir" ;

    /**
     *  系统的title的key
     */
    public static final String SYSTEM_TITLE_KEY = "systemTitle" ;



}

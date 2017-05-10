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
     *  系统的title的key
     */
    public static final String SYSTEM_TITLE_KEY = "systemTitle" ;

    /**
     * 系统的ip地址
     */
    public static final String SYSTEM_IP_KEY = "systemIp" ;

    /**
     * rtmp地址前缀
     */
    public static final String RTMP_ADDR_PREFIX_KEY = "rtmpAddrPrefix" ;

}

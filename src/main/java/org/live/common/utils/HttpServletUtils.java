package org.live.common.utils;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 *  封装httpservlet的工具
 * @author Mr.wang
 *
 */
public class HttpServletUtils {
	
	/**
	 * 将构造方法私有，防止被new
	 */
	private HttpServletUtils(){}

	
	/**
	 * 判断是否是ajax
	 * 
	 * 通过请求头判断，但这个请求是可以伪造的。但是不影响判断
	 * 
	 * @param request 
	 * @return  boolean true为ajax请求
	 */
    public static boolean isAjax(HttpServletRequest request) {
    	
    	//requestType的值为 null 则是普通的页面请求，值为 XMLHttpRequest则是ajax请求
    	String requestType = request.getHeader("X-Requested-With") ;
    	if(StringUtils.equals(requestType, "XMLHttpRequest")) {	
    		return true ;
    	}
    	return false ;
    }

    /**
     * 通过HttpServletRequest返回IP地址
     * 
     * @param request HttpServletRequest
     * @return ip String
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("Cdn-Src-Ip");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr() ;
            if (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1")) {
                try {
                    //根据网卡取本机配置的IP
                    InetAddress inet = InetAddress.getLocalHost();
                    ip = inet.getHostAddress();
                } catch (UnknownHostException e) {
                	throw new RuntimeException("本地ip获取异常",e) ;
                }
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
        if (ip != null && ip.length() > 15) { //"***.***.***.***".length() = 15  
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

	
}
    
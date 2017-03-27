package org.live.common.shiro;

import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * <p>并发登录人数控制的filter</p>
 * 
 * <p>默认情况下，一个账号只允许并发登录一个用户</p>
 * 
 * @author Mr.wang
 */
public class KickoutSessionControlFilter extends AccessControlFilter {
	

	/**
	 * @return boolean true,允许访问. false,访问拒绝，走onAccessDenied方法继续判断
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		return false;
	}

	/**
	 *  这里实现并发登录用户的控制
	 *  
	 *  
	 *  
	 *  @return boolean 表示访问拒绝时是否自己处理，如果返回true表示自己不处理且继续拦截器链执行，
	 *  		返回false表示自己已经处理了（比如重定向到另一个页面）。
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}

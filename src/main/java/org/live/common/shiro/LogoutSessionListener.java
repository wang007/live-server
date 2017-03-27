package org.live.common.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;


/**
 * <p> shiro的session监听器，处理过期的session </p>
 * 
 * @author Mr.wang
 *
 */
public class LogoutSessionListener extends SessionListenerAdapter {

	private final static Logger logger = LoggerFactory.getLogger(LogoutSessionListener.class) ;
	
	@Resource
	private LoginRealm loginRealm ;
	
	/**
	 * session过期执行
	 * 
	 *  session会话过期shiro自动清除session缓存，但不清除用户认证与授权缓存。
	 * 用户认证成功：
	 *      缓存用户认证信息
	 *      session存PrincipalCollection
	 */
	@Override
	public void onExpiration(Session session) {
	
		try {
			//获取身份信息
			PrincipalCollection principals = (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) ;
				if(!CollectionUtils.isEmpty(principals)) {
					logger.debug("清除session并同时清除 [{}] 认证和授权缓存",principals.getPrimaryPrincipal());
					loginRealm.clearCache(principals) ;		//清楚用户认证和授权缓存。
				}
			super.onExpiration(session);
		} catch(Exception e) {
			logger.error("session过期操作异常！",e) ;
		}
		
	}

}

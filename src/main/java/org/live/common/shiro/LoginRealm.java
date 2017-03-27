package org.live.common.shiro;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.live.common.constants.Constants;
import org.live.common.exception.ServiceException;
import org.live.sys.entity.User;
import org.live.sys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>shiro自定义认证realm</p>
 *
 * <p>shiro中的认证器和授权器将分别调用doGetAuthenticationInfo和doGetAuthorizationInfo方法</p>
 *
 * @author Mr.wang
 *
 */
public class LoginRealm extends AuthorizingRealm {

	private final static Logger LOGGER = LoggerFactory.getLogger(LoginRealm.class) ;

	@Resource
	private UserService userService ;


	/**
	 *  当认证成功之后，调用该方法进行授权
	 *
	 *  授权完成之后，将以principal为key,进行缓存。
	 *
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo() ;
		String username = (String) principals.getPrimaryPrincipal(); //账号
		try {
			info.addRoles(userService.getRoleValuesByUsername(username));    //设置角色值
			info.addStringPermissions(userService.getPermissionValuesByUsername(username)) ;    //设置权限值
		} catch(Exception e) {
			LOGGER.error("用户:{},授权异常",username,e) ;
		}
		return info ;
	}

	/**
	 * 当用户登录时，调用此方法进行认证。
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		String username = (String) token.getPrincipal();	//账号
		User user = null ;

		try {
			List<User> users = userService.findByUsername(username) ;
			int length = users == null ? 0 : users.size() ; //用户数量
			user = length > 0 ? users.get(0) : null ;
			if(length == 0) {
				throw new UnknownAccountException("用户不存在") ;
			} else if(length != 1) {
				new AccountException("用户信息重复") ;
			} else if(user.getIsDelete() == Constants.DIC_YES) {
				throw new UnknownAccountException("用户不存在") ;
			} else if(user.getIsLock() == Constants.DIC_YES) {
				throw new LockedAccountException("用户被锁定") ;
			} else {
				Session session = SecurityUtils.getSubject().getSession() ;
				session.setAttribute(Constants.CURRENT_LOGINUSER_KEY, user) ;
				session.setAttribute(Constants.CURRENT_USERTYPE_KEY, user.getUserType());
			}
		} catch (ServiceException e) {
			throw new AccountException("用户登录验证异常");
		}
		//使用加盐
		return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),this.getName()) ;
	}


	/**
	 * 清除当前用户认证与授权缓存
	 */
	@Override
	public void clearCache(PrincipalCollection principals) {

		super.clearCache(principals);
	}


	/**
	 * 清除当前用户的授权缓存
	 */
	@Override
	protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {

		super.clearCachedAuthorizationInfo(principals) ;
	}

	/**
	 * 清除当前用户的认证缓存
	 */
	@Override
	protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {

		super.clearCachedAuthenticationInfo(principals) ;
	}

}

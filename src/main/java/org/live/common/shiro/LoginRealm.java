package org.live.common.shiro;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



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

	/**
	 *  当认证成功之后，调用该方法进行授权
	 *  
	 *  授权完成之后，将以principal为key,进行缓存。
	 *  
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        return null ;
	}

	/**
	 * 当用户登录时，调用此方法进行认证。
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

	    return null ;
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

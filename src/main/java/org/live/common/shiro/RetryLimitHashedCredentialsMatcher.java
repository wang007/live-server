package org.live.common.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * <p>重写密码匹配器 </p>
 * 
 * <p>可以进行一些拓展。例如：登录次数限制 </p>
 * 
 * @author Mr.wang
 *
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

	
	private static final int DEFAULT_RETRY_LIMIT_COUNT = 5 ;

	/**
	 * ehcache中配置的密码重试的key
	 */
	public static final String PASSWORDRETRY_CACHE_KEY = "passwordRetryCache" ;

	/**
	 * 重试次数限制
     * 0.关闭限制
	 */
	private int retryLimitCount = DEFAULT_RETRY_LIMIT_COUNT ;
	
	/**
	 * cache
	 */
	private Cache<String,AtomicInteger> passwordRetryCache ;
	
	/**
	 * 初始化一些算法
	 * 
	 * @param cacheManager shiro缓存管理器
	 */
	public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {

		passwordRetryCache = cacheManager.getCache(PASSWORDRETRY_CACHE_KEY) ;
		if(passwordRetryCache == null) {
			throw new RuntimeException("passwordtryCache is null") ;
		}
		
		//该加密方法对应EncryptUtils.encryptToBase64
		this.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME) ;		//md5加密
		this.setHashIterations(1) ;	 //md5散列一次
		this.setStoredCredentialsHexEncoded(false) ;	//设置凭证编码方式：true为十六进制，false为Base64，默认为true
	}

	/**
	 * 密码匹配方法
	 */
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		
		//这里实现重试密码的限制。
		String username = (String) token.getPrincipal();	//得到账号。
        if(retryLimitCount != 0 ) {
            AtomicInteger retryCount = passwordRetryCache.get(username) ;   //得到统计的重试次数
            if(retryCount == null) {
                retryCount = new AtomicInteger(0) ;
                passwordRetryCache.put(username, retryCount) ;
            }
            if(retryCount.incrementAndGet() > retryLimitCount ) {   //超过重试次数，
                throw new ExcessiveAttemptsException("密码错误次数超过限制，账户锁定1小时！") ;
            }
        }
        boolean matches = super.doCredentialsMatch(token, info) ;   //执行匹配
        if(retryLimitCount != 0) {
            if(matches) {
                passwordRetryCache.remove(username) ;
            }
        }

        return matches ;

	}
	

	public int getRetryLimitCount() {
		return retryLimitCount;
	}

	public void setRetryLimitCount(int retryLimitCount) {
	    if(retryLimitCount == 0) {  //等于0时，清理缓存
	        passwordRetryCache.clear() ;
        }
		this.retryLimitCount = retryLimitCount;
	}
	
	
	
	
	

}

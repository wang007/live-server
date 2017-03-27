package org.live.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 *  spring缓存配置
 * Created by Mr.wang on 2016/11/25.
 */
@Configuration
@EnableCaching
public class CacheConfig {


    /*
     * ehcache 主要的管理器
     */
    @Bean(name = "cacheCacheManager")
    public EhCacheCacheManager cacheCacheManager(){
        EhCacheCacheManager cacheManager =  new EhCacheCacheManager() ;
        cacheManager.setCacheManager(ehCacheManagerFactoryBean().getObject()) ;
        return cacheManager ;
    }

    /*
     * 据shared与否的设置,Spring分别通过CacheManager.create()或new CacheManager()方式来创建一个ehcache基地.
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setConfigLocation (new ClassPathResource("ehcache.xml")) ;
       //由于hibernate也使用了Ehcache, 保证双方都使用同一个缓存管理器
        cacheManagerFactoryBean.setShared (true) ;
        return cacheManagerFactoryBean ;
    }


}

package org.live.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Properties;

/**
 *
 * 事务切面的配置,未配置成功
 * Created by Mr.wang on 2016/12/3.
 */
@Configuration
public class TransactionConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionConfig.class) ;

    /**
     * 事务拦截器
     * @param transactionManager
     * @return
     */
    //@Bean
    public TransactionInterceptor transactionInterceptor(PlatformTransactionManager transactionManager) {

        LOGGER.info("事务管理器-----> {}",transactionManager.getClass().getName()) ;
        System.out.println("事务管理器-----> "+transactionManager.getClass().getName());
        TransactionInterceptor ti = new TransactionInterceptor() ;
        ti.setTransactionManager(transactionManager) ;
        Properties properties = new Properties() ;

        properties.setProperty("get*", "PROPAGATION_REQUIRED,readOnly");
        properties.setProperty("find*", "PROPAGATION_REQUIRED,readOnly");
        properties.setProperty("query*", "PROPAGATION_REQUIRED,readOnly");

        properties.setProperty("insert*", "PROPAGATION_REQUIRED");
        properties.setProperty("new*", "PROPAGATION_REQUIRED");
        properties.setProperty("add*", "PROPAGATION_REQUIRED");
        properties.setProperty("save*", "PROPAGATION_REQUIRED");
        properties.setProperty("set*", "PROPAGATION_REQUIRED");

        properties.setProperty("modify*", "PROPAGATION_REQUIRED");
        properties.setProperty("upadte*", "PROPAGATION_REQUIRED");
        properties.setProperty("edit*", "PROPAGATION_REQUIRED");
        properties.setProperty("change*", "PROPAGATION_REQUIRED");

        properties.setProperty("remove*", "PROPAGATION_REQUIRED");
        properties.setProperty("delete*", "PROPAGATION_REQUIRED");

        properties.setProperty("*", "PROPAGATION_REQUIRED,readOnly");

        ti.setTransactionAttributes(properties) ;
        return ti ;
    }


}

package org.live.config;

import org.live.common.constants.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Mr.wang on 2016/11/23.
 */
@Configuration
   //开启servlet扫描，即扫描servlet，filter，listenter注解的serlvet容器相关的类
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Value("${spring.web.uploadfile-maxsize}")
    private long maxUploadSize ;

    /**
     *  文件上传解析器
     * @return
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver() ;
        multipartResolver.setDefaultEncoding(Constants.DEFAULT_CHARSET) ;   //设置编码
        multipartResolver.setResolveLazily(true);//resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
        multipartResolver.setMaxUploadSize(maxUploadSize) ;     //设置文件上传的最大内存
        return multipartResolver ;
    }


}

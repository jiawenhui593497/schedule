package com.syj.schedule.config;

import com.syj.schedule.common.interceptor.RequestInterceptor;
import com.syj.schedule.common.zk.ZookeeperClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;


@Configuration
public class WebAppConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/css")
                .addResourceLocations("classpath:/static/fonts")
                .addResourceLocations("classpath:/static/images")
                .addResourceLocations("classpath:/static/js")
        ;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //传递req，res
        registry.addInterceptor(new RequestInterceptor()).addPathPatterns("/**");

        super.addInterceptors(registry);
    }


    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize("20MB");
        factory.setMaxRequestSize("20MB");
        return factory.createMultipartConfig();
    }


    @Bean(
            initMethod = "init",
            destroyMethod = "close"
    )
    public ZookeeperClient zookeeperClient(@Value("${dubbo.register.address}") String conntionStr) {
        ZookeeperClient zookeeperClient = new ZookeeperClient(conntionStr);
        return zookeeperClient;
    }


}

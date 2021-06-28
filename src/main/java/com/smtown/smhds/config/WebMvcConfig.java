package com.smtown.smhds.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;


/**
 * <pre>
 *	WebMvcConfig.java - 파일 경로 수정하는 클래스
 * </pre>
 *
 * @author	최해림
 * @since	2019.06.25
 * @version	1.0
 *
 * <pre>
 * == Modification Information ==
 * Date		Modifier		Comment
 * ====================================================
 * 2019.06.25	최해림		Initial Created.
 * ====================================================
 * </pre>
 *
 * Copyright SM Entertainment.(C) All right reserved.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file_location}")
    private String resourcesLocation;  //실제 파일 경로
//    private String resourcesLocation = "/Users/cocoa1149/upload/";  //실제 파일 경로

    @Value("${uri_path}")
    private String resourcesUriPath ;          //페이지에서 요청하는 경로
//    private String resourcesUriPath = "/resourcses/upload";          //페이지에서 요청하는 경로

    /*
     * resourcesUriPath로 접근 시 resourcesLocation 경로의 파일 제공
     * @param
     * @return
     * @exception
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcesUriPath+"/**")
                .addResourceLocations("file://"+resourcesLocation);
    }
    
    /*
     * XSS 공격 차단 설정
     * @param
     * @return
     * @exception
     */
    @Bean
    public FilterRegistrationBean<XssEscapeServletFilter> filterRegistrationBean() {
        FilterRegistrationBean<XssEscapeServletFilter> filterRegistration = new FilterRegistrationBean<>();
        filterRegistration.setFilter(new XssEscapeServletFilter());
        filterRegistration.setOrder(1);
        filterRegistration.addUrlPatterns("/*");

        return filterRegistration;
    }
}

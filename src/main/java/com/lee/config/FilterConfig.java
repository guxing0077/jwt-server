package com.lee.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lee.filters.JwtAuTokenFilter;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(jwtAuTokenFilter());
        registration.addUrlPatterns("/user/*");
        registration.setName("jwtAuTokenFilter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public JwtAuTokenFilter jwtAuTokenFilter(){
        return new JwtAuTokenFilter();
    }
}

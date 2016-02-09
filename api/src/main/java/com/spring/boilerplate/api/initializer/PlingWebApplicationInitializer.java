package com.spring.boilerplate.api.initializer;

import com.spring.boilerplate.api.config.AppSecurityConfig;
import com.spring.boilerplate.api.config.ApiConfig;
import com.spring.boilerplate.api.config.ServletContextConfig;
import com.spring.boilerplate.common.config.CommonConfig;
import com.spring.boilerplate.common.config.PersistenceConfig;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;

@Order(2)
public class PlingWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { AppSecurityConfig.class, CommonConfig.class, PersistenceConfig.class, ApiConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { ServletContextConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");

        return new Filter[] {
                characterEncodingFilter
        };
    }
}

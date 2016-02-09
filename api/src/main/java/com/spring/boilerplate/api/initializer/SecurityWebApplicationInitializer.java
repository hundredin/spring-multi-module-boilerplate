package com.spring.boilerplate.api.initializer;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Sets up the Spring Security filter chain
 * http://docs.spring.io/spring-security/site/docs/4.0.2.RELEASE/reference/htmlsingle/#abstractsecuritywebapplicationinitializer-with-spring-mvc
 */
@Order(1)
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

}


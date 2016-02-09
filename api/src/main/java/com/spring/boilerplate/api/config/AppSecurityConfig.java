package com.spring.boilerplate.api.config;


import com.spring.boilerplate.api.security.AjaxAuthenticationFailureHandler;
import com.spring.boilerplate.api.security.AjaxAuthenticationSuccessHandler;
import com.spring.boilerplate.api.security.RestAuthenticationEntryPoint;
import com.spring.boilerplate.api.security.SecurityUserDetailsService;
import com.spring.boilerplate.api.security.filter.AuthenticationTokenProcessingFilter;
import com.spring.boilerplate.api.security.filter.CORSFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.spring.boilerplate.security")
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppSecurityConfig.class);

    @Autowired
    public void setUserDetailsService(SecurityUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    private SecurityUserDetailsService userDetailsService;

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.eraseCredentials(false);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class)   // http://stackoverflow.com/a/22923806
                .addFilterBefore(new AuthenticationTokenProcessingFilter(userDetailsService), UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
            .and()
            .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/*/**").permitAll()
                .anyRequest()
                .authenticated()
            .and()
                .formLogin()
                .loginProcessingUrl("/authenticate")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(new AjaxAuthenticationSuccessHandler(new SavedRequestAwareAuthenticationSuccessHandler()))
                .failureHandler(new AjaxAuthenticationFailureHandler())
            .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll();


        if ("true".equals(System.getProperty("httpsOnly"))) {
            LOGGER.info("launching the application in HTTPS-only mode");
            http.requiresChannel().anyRequest().requiresSecure();
        }
    }
}

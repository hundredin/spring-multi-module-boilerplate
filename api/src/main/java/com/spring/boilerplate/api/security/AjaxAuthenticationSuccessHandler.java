package com.spring.boilerplate.api.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boilerplate.api.util.TokenUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Authentication success handler for integration with SPA applications that need to login using Ajax instead of
 * a form post.
 *
 * Detects if its a ajax login request, and if so sends a customized response in the body, otherwise defaults
 * to the existing behaviour for none-ajax login attempts.
 *
 */
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private AuthenticationSuccessHandler defaultHandler;

    public AjaxAuthenticationSuccessHandler(AuthenticationSuccessHandler defaultHandler) {
        this.defaultHandler = defaultHandler;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        if ("true".equals(request.getHeader("X-Login-Ajax-call"))) {
            User user = (User) authentication.getPrincipal();
            WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
            String token = TokenUtils.createToken(user);
            String sessionId = details.getSessionId();

            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("sessionId", sessionId);

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonAuthInfo = objectMapper.writeValueAsString(map);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(jsonAuthInfo);
        } else {
            defaultHandler.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
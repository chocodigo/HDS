package com.smtown.smhds.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
 
/**
 * 로그인 성공 핸들러
 * 
 * @author wedul
 *
 */
@Component
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private static final Logger log = LoggerFactory.getLogger(AuthSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
    	
    	
    	log.debug("##################@@@@@@@@@@@@@@@@@@@@@");
    }
 
}
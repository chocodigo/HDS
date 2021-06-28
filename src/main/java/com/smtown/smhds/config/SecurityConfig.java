package com.smtown.smhds.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.smtown.smhds.account.AccountService;
import com.smtown.smhds.util.AuthSuccessHandler;

/**
 * <pre>
 *	SecurityConfig.java - Spring Security 설정
 * </pre>
 *
 * @author	최해림
 * @since	2019.05.28
 * @version	1.0
 *
 * <pre>
 * == Modification Information ==
 * Date		Modifier		Comment
 * ====================================================
 * 2019.05.28	최해림		Initial Created.
 * ====================================================
 * </pre>
 *
 * Copyright SM Entertainment.(C) All right reserved.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired AccountService accountService;
	
	@Resource AuthSuccessHandler authSuccessHandler;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(accountService);
	}

	
	
	//http 설정
	//001 admin
	//002 user
	protected void configure(HttpSecurity http) throws Exception{
		http.headers().contentTypeOptions();
		http.headers().httpStrictTransportSecurity().disable();
		http.csrf().ignoringAntMatchers("/forgotProc");
		http.csrf().ignoringAntMatchers("/resetPWProc");
		http.csrf().ignoringAntMatchers("/isPwNull");

		http.authorizeRequests().antMatchers("/admin/**").hasAuthority("001")
								.antMatchers("/forgot").permitAll()
                                .antMatchers("/forgotProc").permitAll()
                                .antMatchers("/resetPWProc").permitAll()
								.antMatchers("/isPwNull").permitAll()
								.antMatchers("/notice_insert/**").hasAuthority("001")
								.antMatchers("/noticeInsertProc").hasAuthority("001")
								.antMatchers("/notice_update/**").hasAuthority("001")
								.antMatchers("/notice_updateProc").hasAuthority("001")
								.antMatchers("/notice_delete/**").hasAuthority("001")
								.antMatchers("/updateStatProc").hasAuthority("001")
								.antMatchers("/faq_insert").hasAuthority("001")
								.antMatchers("/faqInsertProc").hasAuthority("001")
								.antMatchers("/faq_delete/**").hasAuthority("001")
								.antMatchers("/faq_update/**").hasAuthority("001")
								.antMatchers("/faq_updateProc").hasAuthority("001")
								.antMatchers("/comment/insert").hasAuthority("001")
								.antMatchers("/comment/update").hasAuthority("001");
		
		http.authorizeRequests().antMatchers("/resources/**").permitAll()
								.antMatchers("/**").hasAnyAuthority("001","002");



		http.formLogin()
			.loginPage("/login") 				//로그인 페이지
			.failureUrl("/login?error")			//로그인실패 에러페이지
			.defaultSuccessUrl("/main")			//로그인 성공 페이지
			.usernameParameter("USER_NAME")		//유저 아이디
			.passwordParameter("PASS_WORD")		//유저 비밀번호
			.permitAll();
		
		
		http.logout()
			.logoutSuccessUrl("/login")			//로그아웃 시 이동 페이지
			.invalidateHttpSession(true)		//인증정보 삭제
			.permitAll();
		
		//rememberMe() : return RememberMeConfigurer
		//key(String key) : 
		//rememberMeCookieName(String rememberMeCookieName) : 이름
		//tokenValiditySeconds(int tokenValiditySeconds) : 유효기간
		http.rememberMe().key("remember_key")
						.rememberMeParameter("remember-me")
						.rememberMeCookieName("HDS-login-rememberMe")
						//60 * 60 * 24 * 14 (2주일)
						.tokenValiditySeconds(60*60*24*14);
	}
}

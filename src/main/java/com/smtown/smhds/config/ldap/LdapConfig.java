package com.smtown.smhds.config.ldap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
/**
 * <pre>
 *	LdapConfig.java - LDAP 설정 Configuration
 * </pre>
 *
 * @author	방재훈
 * @since	2019.09.03
 * @version	1.0
 *
 * <pre>
 * == Modification Information ==
 * Date		    Modifier	Comment
 * ====================================================
 * 2019.09.03	방재훈		Initial Created.
 * ====================================================
 * </pre>
 *
 * Copyright SM Entertainment.(C) All right reserved.
 */
@Configuration
public class LdapConfig {

	/*
	 * LDAP - Active Directory 연결 정보 설정
	 * @param		filter
	 * @return		List<ADUserVO>
	 * @exception	NamingException
	 */
	@Bean
	public LdapContextSource getContextSource() throws Exception{
		LdapContextSource ldapContextSource = new LdapContextSource();
		ldapContextSource.setUrl("ldap://10.10.12.6:389");
		ldapContextSource.setBase("OU=SMTown_users,OU=SMTown,DC=smtown,DC=com");
		ldapContextSource.setUserDn("Administrator@smtown.com");
		ldapContextSource.setPassword("dlsvmfk!!^^@)!&");
		return ldapContextSource;
	}
	
	/*
	 * Active Directory에서 가져온 유저 정보를 ADUserVO Parsing
	 * @param		filter
	 * @return		List<ADUserVO>
	 * @exception	NamingException
	 */
	@Bean
	public LdapTemplate ldapTemplate() throws Exception{
		LdapTemplate ldapTemplate = new LdapTemplate(getContextSource());
		
		/* 검색에서 PartialResultException을 무시해야하는지 여부 */
		ldapTemplate.setIgnorePartialResultException(true);
		ldapTemplate.setContextSource(getContextSource());
		
		return ldapTemplate;
	}
}

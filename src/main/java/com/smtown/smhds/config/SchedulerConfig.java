package com.smtown.smhds.config;
/**
 * <pre>
 *	SchedulerConfig.java - 스케줄러
 * </pre>
 *
 * @author	방재훈
 * @since	2019.09.10
 * @version	1.0
 *
 * <pre>
 * == Modification Information ==
 * Date		Modifier		Comment
 * ====================================================
 * 2019.09.20	방재훈		Initial Created.
 * ====================================================
 * </pre>
 *
 * Copyright SM Entertainment.(C) All right reserved.
 */
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.filter.PresentFilter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.smtown.smhds.account.ADUserVO;
import com.smtown.smhds.account.AccountService;
import com.smtown.smhds.account.AuthVO;

@Transactional
@Component
public class SchedulerConfig {

	private static final Logger log = LoggerFactory.getLogger(SchedulerConfig.class);

	@Autowired
	private AccountService accountService;

	@SuppressWarnings("unchecked")
	@Scheduled(cron = "10 * * * * *")
	public void cronJobSch() throws Exception {
		// TODO

		/* ADUser 테이블 data 유무확인 */
		int adUserCnt = accountService.selADUserCnt();

		/* ADUser 테이블 데이타 있을 시 전체 삭제 */
		if (adUserCnt > 0) {
			accountService.delADUser();
		}

		/* LDAP context 설정 */
		LdapContextSource ldapContextSource = new LdapContextSource();
		ldapContextSource.setUrl("ldap://10.10.12.6:389");
		ldapContextSource.setBase("OU=SMTown_users,OU=SMTown,DC=smtown,DC=com");
		ldapContextSource.setUserDn("Administrator@smtown.com");
		ldapContextSource.setPassword("dlsvmfk!!^^@)!&");
		/* 새로작성하는 context 이기 때문에 설정 후 적용시커주는 함수 */
		ldapContextSource.afterPropertiesSet();

		/* LDAP Template 설정 */
		LdapTemplate ldapTemplate = new LdapTemplate();
		/* 검색에서 PartialResultException을 무시해야하는지 여부 */
		ldapTemplate.setIgnorePartialResultException(true);
		ldapTemplate.setContextSource(ldapContextSource);
		ldapTemplate.setDefaultCountLimit(10000);
		ldapTemplate.afterPropertiesSet();
		
		/* 검색 filter */
		AndFilter andFilter = new AndFilter();
		//andFilter.and(new EqualsFilter("department", "IT Infra Team(인프라팀)"));
		/*
		 * andFilter.and(new PresentFilter("mail")); andFilter.and(new
		 * PresentFilter("mobile"));
		 */
		andFilter.and(new PresentFilter("company"));
		andFilter.and(new PresentFilter("department"));
	 	//andFilter.and(new PresentFilter("title"));

		Filter filter = andFilter;
		/* Active Directory로 부터 가져온 정보를 VO에 파싱하는 로직 */
		@SuppressWarnings("rawtypes")
		List<ADUserVO> ADUserList = ldapTemplate.search("", filter.encode(), new AttributesMapper() {
			@Override
			public ADUserVO mapFromAttributes(Attributes attr) throws NamingException {
				
				ADUserVO adUserVO = new ADUserVO();
				/* 계정 */
				adUserVO.setUser_name(String.valueOf(attr.get("SamAccountName").get()));
				/* 이름 */
				adUserVO.setReal_name(String.valueOf(attr.get("cn").get()));
				/* 휴대번호 */
				if(attr.get("mobile") != null)
					adUserVO.setPhon_enum(String.valueOf(attr.get("mobile").get()));
				/* 이메일 */
				if(attr.get("mail") != null)
					adUserVO.setUser_mail(String.valueOf(attr.get("mail").get()));
				/* 회사 */
				adUserVO.setComm_pany(String.valueOf(attr.get("company").get()));
				/* 부서 */
				adUserVO.setDept_ment(String.valueOf(attr.get("department").get()));
				/* 직책 */
				//adUserVO.setPosi_tion(String.valueOf(attr.get("title").get()));

				return adUserVO;
			}
		});
		
		  
		log.debug("=============================>"+ADUserList.size());
		
		/*
		 * for(ADUserVO adUserVo : ADUserList) { //초기 비밀번호 String defaultPassword = new
		 * BCryptPasswordEncoder().encode("1111");
		 * 
		 * adUserVo.setPass_word("{bcrypt}"+defaultPassword);
		 * 
		 * log.debug("===> {}", adUserVo.getUser_name());
		 * 
		 * //HD_ADUSER 테이블에 저장 accountService.insAdUser(adUserVo); }
		 * 
		 * //ADUser User Merge accountService.mergADUserToUser(); //Auth User Merge
		 * accountService.mergAuth();
		 */
	}
}

package com.smtown.smhds.account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * <pre>
 *	AccountService.java - 계정 정 서비스
 * </pre>
 *
 * @author	방재훈
 * @since	2019.09.09
 * @version	2.0
 *
 * <pre>
 * == Modification Information ==
 * Date		Modifier		Comment
 * ====================================================
 * 2019.05.21	최해림		Initial Created.
 * 2019.09.20	방재훈
 * ====================================================
 * </pre>
 *
 * Copyright SM Entertainment.(C) All right reserved.
 */
@Service
public class AccountService implements UserDetailsService{

	//계정정보 매퍼(Mapper)
	private final AccountMapper accountMapper;

	@Autowired
	public AccountService(AccountMapper accountMapper){
		this.accountMapper=accountMapper;
	}

	/*
	 * Database에 접근해서 사용자 정보를 가져오는 역할
	 * @param	user_name
	 * @return Account
	 * @exception Exception - 조회시 발생한 예외
	 */
	@Override
	public UserDetails loadUserByUsername(String user_name) throws UsernameNotFoundException {
		try {
			Account account = accountMapper.accountDetail(user_name);		//user_name을 통해 계정 정보 가져오기
			account.setAuthorities(getAuthorities(user_name));			//user_name을 통해 권한 설정
			return account;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * user_name의 권한 리스트
	 * @param user_name
	 * @return "/main"
	 * @exception Exception - 조회시 발생한 예외
	 */
	public Collection<GrantedAuthority> getAuthorities(String user_name) throws Exception{
		List<String> str_authorities = accountMapper.accountAuthority(user_name);	//user_name을 통해 사용자의 권한 리스트 받아오기
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String authority : str_authorities) {
			authorities.add(new SimpleGrantedAuthority(authority));					//권한 리스트 만들기
		}
		return authorities;
	}

	/*
	 * 계정 정보
	 * @param	user_name
	 * @return account
	 * @exception Exception - 조회시 발생한 예외
	 */
	public Account accountDetailService(String user_name) throws Exception{
		return accountMapper.accountDetail(user_name);
	}
	
	/*
	 * ADUSER TABLE data 유무 확인
	 * @param
	 * @return int
	 * @exception Exception - 조회시 발생한 예외
	 */
	public int selADUserCnt() throws Exception{
		return accountMapper.selADUserCnt();
	}
	
	/*
	 * AD 유저 정보 저장
	 * @param	ADUserVO
	 * @return int
	 * @exception Exception
	 */
	public int insAdUser(ADUserVO adUserVo) throws Exception{
		return accountMapper.insAdUser(adUserVo);
	}
	
	/*
	 * User 권한 부여
	 * @param	ADUserVO
	 * @return int
	 * @exception Exception
	 */
	public int mergAuth() throws Exception{
		return accountMapper.mergAuth();
	}
	
	/*
	 * ADUser Table Data 전체 삭제
	 * @param 
	 * @return int
	 * @exception Exception
	 */
	public int delADUser() throws Exception{
		return accountMapper.delADUser();
	}
	
	public int mergADUserToUser() throws Exception{
		return accountMapper.mergADUserToUser();
	}
}

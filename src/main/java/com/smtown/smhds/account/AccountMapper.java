package com.smtown.smhds.account;

import java.util.List;

/**
 * <pre>
 *	AccountMapper.java - 계정 매퍼
 * </pre>
 *
 * @author	최해림
 * @since	2019.05.21
 * @version	1.0
 *
 * <pre>
 * == Modification Information ==
 * Date		Modifier		Comment
 * ====================================================
 * 2019.05.21	최해림		Initial Created.
 * 2019.09.20	방재훈		Modify
 * ====================================================
 * </pre>
 *
 * Copyright SM Entertainment.(C) All right reserved.
 */
public interface AccountMapper {
	//사용자정보 읽어오기
	public Account accountDetail(String user_name) throws Exception;
	//권한받아오기
	public List<String> accountAuthority(String user_name) throws Exception;
	//ADUser Table 데이터 유무 확인
	public int selADUserCnt() throws Exception;
	//ADUser Insert 
	public int insAdUser(ADUserVO adUserVO) throws Exception;
	//ADUser delete 
	public int delADUser() throws Exception;
	//ADUser -> User merge
	public int mergADUserToUser() throws Exception;
	//User 권한 부여
	public int mergAuth() throws Exception;
}

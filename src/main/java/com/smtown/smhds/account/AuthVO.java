package com.smtown.smhds.account;
/**
 * <pre>
 *	AuthVO.java - Auth Table VO
 * </pre>
 *
 * @author	방재훈
 * @since	2019.09.19
 * @version	1.0
 *
 * <pre>
 * == Modification Information ==
 * Date		Modifier		Comment
 * ====================================================
 * 2019.09.19	방재훈		Initial Created.
 * ====================================================
 * </pre>
 *
 * Copyright SM Entertainment.(C) All right reserved.
 */
public class AuthVO {
	
	private String user_name;		//사용자이름
	private String user_auth;		//사용자권한

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_auth() {
		return user_auth;
	}

	public void setUser_auth(String user_auth) {
		this.user_auth = user_auth;
	}
	
	
}

package com.smtown.smhds.config.ldap;
/**
 * <pre>
 *	ADUserVO.java - ADUser 정보 VO
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
public class ADUserVO {
	
	private String cn; 				/* 이름 */
	private String mobile;			/* 휴대번호 */
	private String mail;			/* 메일 */
	private String company;			/* 회사 */
	private String department;		/* 부서 */
	private String title;			/* 직책 */

	
	/************************
	*	  getter/setter  	*
	************************/
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getCn() {
		return cn;
	}

	public void setCn(String cn) {
		this.cn = cn;
	}
}

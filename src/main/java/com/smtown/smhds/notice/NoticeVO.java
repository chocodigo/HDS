package com.smtown.smhds.notice;

import java.util.Date;

/**
 * <pre>
 *	NoticeVO.java - 공지사항 VO
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
public class NoticeVO {
	//순번
	private String idxx_numb;
	//제목
	private String titl_name;
	//이름
	private String user_name;
	//내용
	private String cont_ents;
	//입력자
	private String crea_user;
	//입력일자
	private Date crea_date;
	//순번
	private int numb_keyx;
	
	
	public int getNumb_keyx() {
		return numb_keyx;
	}
	public void setNumb_keyx(int nub_keyx) {
		this.numb_keyx = nub_keyx;
	}
	public String getIdxx_numb() {
		return idxx_numb;
	}
	public void setIdxx_numb(String idxx_numb) {
		this.idxx_numb = idxx_numb;
	}
	public String getTitl_name() {
		return titl_name;
	}
	public void setTitl_name(String titl_name) {
		this.titl_name = titl_name;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getCont_ents() {
		return cont_ents;
	}
	public void setCont_ents(String cont_ents) {
		this.cont_ents = cont_ents;
	}
	public String getCrea_user() {
		return crea_user;
	}
	public void setCrea_user(String crea_user) {
		this.crea_user = crea_user;
	}
	public Date getCrea_date() {
		return crea_date;
	}
	public void setCrea_date(Date crea_date) {
		this.crea_date = crea_date;
	}
}

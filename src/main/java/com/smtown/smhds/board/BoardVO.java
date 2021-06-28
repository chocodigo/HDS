package com.smtown.smhds.board;

import java.util.Date;

/**
 * <pre>
 *	BoardVO.java - 요청사항 게시판 VO
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
 * ====================================================
 * </pre>
 *
 * Copyright SM Entertainment.(C) All right reserved.
 */

public class BoardVO {
	//순번
	private String idxx_numb;
	//제목
	private String titl_name;
	//이름
	private String user_name;
	//내용
	private String cont_ents;
	//완료일자
	private Date succ_date;
	//수정일자
	private Date upda_date;
	//상태
	// "1" : 신규
	// "2" : 담당자 접수
	// "3" : 진행
	// "4" : 완료
	private String stat_flag;
	//입력자
	private String crea_user;
	//입력일자
	private Date crea_date;
	//수정자
	private String upda_user;
	
	private int numb_keyx;	//게시물 순번
	
	private String cate_gory;	//카테고리 코드
	
	private String cate_name;	//카테고리 이름
	
	public String getCate_name() {
		return cate_name;
	}
	public void setCate_name(String cate_name) {
		this.cate_name = cate_name;
	}
	public String getCate_gory() {
		return cate_gory;
	}
	public void setCate_gory(String cate_gory) {
		this.cate_gory = cate_gory;
	}
	public int getnumb_keyx() {
		return numb_keyx;
	}
	public void setnumb_keyx(int numb_keyx) {
		this.numb_keyx = numb_keyx;
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
	public Date getSucc_date() {
		return succ_date;
	}
	public void setSucc_date(Date succ_date) {
		this.succ_date = succ_date;
	}
	public Date getUpda_date() {
		return upda_date;
	}
	public void setUpda_date(Date upda_date) {
		this.upda_date = upda_date;
	}
	public String getStat_flag() {
		return stat_flag;
	}
	public void setStat_flag(String stat_flag) {
		this.stat_flag = stat_flag;
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
	public String getUpda_user() {
		return upda_user;
	}
	public void setUpda_user(String upda_user) {
		this.upda_user = upda_user;
	}
}

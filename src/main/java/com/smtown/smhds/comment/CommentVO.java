package com.smtown.smhds.comment;

import java.util.Date;

/**
 * <pre>
 *	CommentVO.java - 댓글 VO
 * </pre>
 *
 * @author	최해림
 * @since	2019.06.10
 * @version	1.0
 *
 * <pre>
 * == Modification Information ==
 * Date		Modifier		Comment
 * ====================================================
 * 2019.06.10	최해림		Initial Created.
 * ====================================================
 * </pre>
 *
 * Copyright SM Entertainment.(C) All right reserved.
 */

public class CommentVO {
	private String comm_idxx;	//댓글 ID
	private String idxx_numb;	//댓글을 등록할 게시물 ID
	private String comm_cont;	//댓글 내용
	private String user_name;	//댓글을 등록한 사용자 ID
	private Date comm_date;		//댓글을 등록한 날짜
	private String comm_titl;	//답글 제목
	private String crea_user;	//작성한 사용자의 이름

	public String getComm_titl() {
		return comm_titl;
	}

	public void setComm_titl(String comm_titl) {
		this.comm_titl = comm_titl;
	}
	public String getComm_idxx() {
		return comm_idxx;
	}
	public void setComm_idxx(String comm_idxx) {
		this.comm_idxx = comm_idxx;
	}
	public String getIdxx_numb() {
		return idxx_numb;
	}
	public void setIdxx_numb(String idxx_numb) {
		this.idxx_numb = idxx_numb;
	}
	public String getComm_cont() {
		return comm_cont;
	}
	public void setComm_cont(String comm_cont) {
		this.comm_cont = comm_cont;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public Date getComm_date() {
		return comm_date;
	}
	public void setComm_date(Date comm_date) {
		this.comm_date = comm_date;
	}

	public String getCrea_user() {
		return crea_user;
	}

	public void setCrea_user(String crea_user) {
		this.crea_user = crea_user;
	}
}

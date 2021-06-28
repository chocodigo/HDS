package com.smtown.smhds.util;

/**
 * <pre>
 *	FileVO.java - 파일 VO
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
public class FileVO {
	
	//파일번호
	private int file_numb;
	//게시글 번호
	private String idxx_numb;
	//저장될 파일명
	private String file_name;
	//원래 파일명
	private String file_orig;
	//파일 위치
	private String file_urll;
	
	public int getFile_numb() {
		return file_numb;
	}
	public void setFile_numb(int file_numb) {
		this.file_numb = file_numb;
	}
	public String getIdxx_numb() {
		return idxx_numb;
	}
	public void setIdxx_numb(String idxx_numb) {
		this.idxx_numb = idxx_numb;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public String getFile_orig() {
		return file_orig;
	}
	public void setFile_orig(String file_orig) {
		this.file_orig = file_orig;
	}
	public String getFile_urll() {
		return file_urll;
	}
	public void setFile_urll(String file_urll) {
		this.file_urll = file_urll;
	}
}

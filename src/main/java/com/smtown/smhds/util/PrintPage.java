package com.smtown.smhds.util;

/**
 * <pre>
 *	PrintPage.java - 특정 페이지 출력
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
public class PrintPage {
	//보여줄 페이지 번호
	private int page;
	//페이지 당 보여줄 게시글의 개수
	private int perPageNum;
	//검색값 - 검색어
	private String search;
	//검색값 - 시작날짜
	private String start_day;
	//검색값 - 마지막날짜
	private String end_day;
	//카테고리 값
	private String cate_gory;
	//진행상황 상태 값
	private String stat_flag;

	//기본값 설정
	public PrintPage() {
		this.page=1;
		this.perPageNum=10;
		this.search="";
		this.start_day="";
		this.end_day="";
		this.cate_gory="01";
	}

	public String getCate_gory() {
		return cate_gory;
	}

	public void setCate_gory(String cate_gory) {
		this.cate_gory = cate_gory;
	}


	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		if(page <=0) {
			this.page = 1;
			return;
		}

		this.page = page;
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(int perPageNum) {
		if(perPageNum <= 0) {
			this.perPageNum=10;
			return;
		}

		this.perPageNum = perPageNum;
	}

	//limit 구문에서 시작위치를 지정할 때 사용
	public int getPageStart() {
		return (this.page-1) * perPageNum;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getStart_day() {
		return start_day;
	}

	public void setStart_day(String start_day) {
		this.start_day = start_day;
	}

	public String getEnd_day() {
		return end_day;
	}

	public void setEnd_day(String end_day) {
		this.end_day = end_day;
	}

	public String getStat_flag() {
		return stat_flag;
	}

	public void setStat_flag(String stat_flag) {
		this.stat_flag = stat_flag;
	}

	@Override
	public String toString() {
		return "PrintPage [page=" + page +", perPageNum=" + perPageNum + "]";
	}
}

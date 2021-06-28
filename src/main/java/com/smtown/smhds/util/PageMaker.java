package com.smtown.smhds.util;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * <pre>
 *	PageMaker.java - 게시판 페이징
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
public class PageMaker {
	
	//게시판 전체 데이터 갯수
	private int totalCount;
	//게시판 화면에서 한번에 보여질 페이지 번호의 갯수
	private int displayPageNum = 5;
	
	//현재 화면에서 보이는 startPage 번호
	private int startPage;
	//현재 화면에 보이는 endPage 번호
	private int endPage;
	//페이징 이전 버튼 활성화 여부
	private boolean prev;
	//페이징 다음 버튼 활성화 여부
	private boolean next;
	
	@Autowired
	private PrintPage printPage;
	
	public int getDisplayPageNum() {
		return displayPageNum;
	}

	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public PrintPage getprintPage() {
		return printPage;
	}

	public void setprintPage(PrintPage printPage) {
		this.printPage = printPage;
	}
	
	public int getTotalcount() {
		return totalCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		
		calcData();
	}
	
	//시작페이지, 마지막 페이지, 이전버튼 생성 여부, 다음버튼 생성 여부 계산 
	private void calcData() {
		//마지막 페이지 = 올림 (현재 페이지 / 한 번에 화면에 보여줄 페이지) * 한 번에 화면에 보여줄 페이지
		endPage = (int) (Math.ceil(printPage.getPage() / (double) displayPageNum) * displayPageNum);
		//시작 페이지 = (마지막 페이지 - 한 번에 화면에 보여줄 페이지) + 1
		startPage = (endPage - displayPageNum) + 1;                     
		
		//tempEndPage(가장 마지막 페이지 = 올림 (총 게시글 갯수 / 페이지 당 보여줄 게시글 갯수)
		int tempEndPage = (int) (Math.ceil(totalCount / (double) printPage.getPerPageNum()));
		if(endPage > tempEndPage) {
			endPage = tempEndPage;
		}
		
		//이전 버튼 생성 여부 = 시작 페이지 번호 == 1 ? false : true
		prev = startPage == 1 ? false : true;
				
		//다음 버튼 생성 여부 = 끝 페이지 번호 * 한 페이지당 보여줄 게시글의 갯수 >= 총 게시글의 수 ? false : true
		next = endPage * printPage.getPerPageNum() >= totalCount ? false : true;
	}
}

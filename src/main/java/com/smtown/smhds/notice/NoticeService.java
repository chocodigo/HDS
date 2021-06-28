package com.smtown.smhds.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smtown.smhds.util.PrintPage;

/**
 * <pre>
 *	NoticeService.java - 공지사항 게시판 서비스
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

@Service
public class NoticeService {
	
	@Autowired
	NoticeMapper noticeMapper;
	
	//게시글 목록
	public List<NoticeVO> noticeListService(PrintPage printPage) throws Exception {
		
		return noticeMapper.noticeList(printPage);
	}
	
	//메인화면에 보여줄 게시글 목록
	public List<NoticeVO> noticeMainListService() throws Exception{
		return noticeMapper.noticeMainList();
	}
	
	//자동완성
	public List<String> noticeSuggestionService(PrintPage printPage) throws Exception{
		return noticeMapper.noticeSuggestion(printPage);
	}
	
	//게시글 목록
	public List<NoticeVO> noticeListMainService(int limit) throws Exception {
		
		return noticeMapper.noticeListMain(limit);
	}
	
	
	
	//게시글 갯수
	public int noticecount(PrintPage printPage) throws Exception{
		return noticeMapper.noticeCount(printPage);
	}
	
	//게시글 상세
	public NoticeVO noticeDetailService(String idxx_numb) throws Exception {

		return noticeMapper.noticeDetail(idxx_numb);
	}
	
	//게시글 작성 시 idxx_numb 생성
	public String noticeSelectIdxxService() throws Exception{
		return noticeMapper.noticeSelectIdxx();
	}
	
	//게시글 작성
	public int noticeInsertService(NoticeVO board) throws Exception {

		return noticeMapper.noticeInsert(board);
	}
	
	//게시글 삭제
	public int noticeDeleteService(String idxx_numb) throws Exception {

		return noticeMapper.noticeDelete(idxx_numb);
	}
	
	//게시글 수정
	public int noticeUpdateService(NoticeVO notice) throws Exception {

		return noticeMapper.noticeUpdate(notice);
	}
}

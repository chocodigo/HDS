package com.smtown.smhds.faq;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smtown.smhds.util.PrintPage;

/**
 * <pre>
 *	faqService.java - 공지사항 게시판 서비스
 * </pre>
 *
 * @author	최해림
 * @since	2019.06.17
 * @version	1.0
 *
 * <pre>
 * == Modification Information ==
 * Date		Modifier		Comment
 * ====================================================
 * 2019.06.17	최해림		Initial Created.
 * ====================================================
 * </pre>
 *
 * Copyright SM Entertainment.(C) All right reserved.
 */

@Service
public class FaqService {
	/*
	 * Faq 게시판 매퍼(Mapper) 클래스
	 */
	@Autowired
	FaqMapper faqMapper;
	
	//게시글 목록
	public List<FaqVO> faqListService(PrintPage printPage) throws Exception {
		
		return faqMapper.faqList(printPage);
	}
	
	//메인화면에 보여줄 게시글 목록
	public List<FaqVO> faqMainListService() throws Exception{
		return faqMapper.faqMainList();
	}
	
	//자동완성
	public List<String> faqSuggestionService(Map<String,String> searchMap) throws Exception{
		return faqMapper.faqSuggestion(searchMap);
	}
	
	//게시글 목록
	public List<FaqVO> faqListMainService(int limit) throws Exception {
		
		return faqMapper.faqListMain(limit);
	}
	
	
	
	//게시글 갯수
	public int faqcount(PrintPage printPage) throws Exception{
		return faqMapper.faqCount(printPage);
	}
	
	//게시글 상세
	public FaqVO faqDetailService(String idxx_numb) throws Exception {

		return faqMapper.faqDetail(idxx_numb);
	}
	
	//게시글 작성 시 idxx_numb 생성
	public String faqSelectIdxxService() throws Exception{
		return faqMapper.faqSelectIdxx();
	}
	
	//게시글 작성
	public int faqInsertService(FaqVO board) throws Exception {

		return faqMapper.faqInsert(board);
	}
	
	//게시글 삭제
	public int faqDeleteService(String idxx_numb) throws Exception {

		return faqMapper.faqDelete(idxx_numb);
	}
	
	//게시글 수정
	public int faqUpdateService(FaqVO faq) throws Exception {

		return faqMapper.faqUpdate(faq);
	}
}

package com.smtown.smhds.faq;

import java.util.List;
import java.util.Map;

import com.smtown.smhds.util.PrintPage;

/**
 * <pre>
 *	FaqMapper.java - FAQ 매퍼
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
public interface FaqMapper {
	public int faqCount(PrintPage printPage) throws Exception;	//게시글 갯수
	
	public List<FaqVO> faqList(PrintPage printPage) throws Exception;	//게시글 목록
	
	public List<FaqVO> faqMainList() throws Exception;	//메인화면에 보여줄 faq 목록
	
	public List<FaqVO> faqListMain(int limit) throws Exception;		//메인 게시글 

	public List<String> faqSuggestion(Map<String,String> searchMap) throws Exception;	//자동완성 기능

	public FaqVO faqDetail(String idxx_numb) throws Exception;	//게시글 상세
	
	public int faqInsert(FaqVO faq) throws Exception;	//게시글 작성
	
	public int faqUpdate(FaqVO faq) throws Exception;	//게시글 수정
	
	public int faqDelete(String idxx_numb) throws Exception;	//게시글 삭제
	
	public String faqSelectIdxx() throws Exception; //idxx_numb 생성	
}

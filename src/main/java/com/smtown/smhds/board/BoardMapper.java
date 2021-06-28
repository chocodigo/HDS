package com.smtown.smhds.board;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.smtown.smhds.account.Account;
import com.smtown.smhds.util.FileVO;
import com.smtown.smhds.util.PrintPage;

/**
 * <pre>
 *	BoardMapper.java - 요청사항 게시판 매퍼
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
@Repository
public interface BoardMapper {
	
	public int boardCompareBirth(Account account) throws Exception;	//생일인증

	public int boardResetPassword(Account account) throws Exception; //비밀번호 재설정

	public int isPasswordNull(String user_name) throws Exception;	//비밀번호 null 확인

	public StatInfo boardStatInfo() throws Exception;	//진행상황 정보

	public int boardCount(PrintPage printPage) throws Exception;	//게시글 갯수

	public List<BoardVO> boardList(PrintPage printPage) throws Exception;	//게시글 목록

	public List<String> boardSuggestion(Map<String,String> SearchMap) throws Exception;	//자동완성 기능

	public BoardVO boardDetail(String idxx_numb) throws Exception;	//게시글 상세

	public int boardHasReply(String idxx_numb) throws Exception;	//게시글 답변 달렸는지 확인

	public int boardInsert(BoardVO board) throws Exception;	//게시글 작성

	public String boardSelectIdxx() throws Exception; //idxx_numb 생성

	public int fileInsert(FileVO file) throws Exception;	//파일 업로드

	public FileVO fileDetail(String idxx_numb) throws Exception;	//파일 상세

	public int boardUpdate(BoardVO board) throws Exception;	//게시글 수정

	public int boardUpdateStat(BoardVO board) throws Exception; //게시글 상태 수정

	public String selectCodeName(String stat_flag) throws Exception;	//게시글 상태 이름

	public int boardDelete(String idxx_numb) throws Exception;	//게시글 삭제

	public int fileDelete(String idxx_numb) throws Exception;	//파일 삭제

	public List<BoardVO> boardFindStat(PrintPage printPage) throws Exception;	//상태에 따른 조회

	public String getAdminEmail(String cate_gory) throws Exception;		//카테고리 관리자 이메일 조회
}

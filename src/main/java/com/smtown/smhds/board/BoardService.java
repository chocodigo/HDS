package com.smtown.smhds.board;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.smtown.smhds.account.Account;
import com.smtown.smhds.util.FileVO;
import com.smtown.smhds.util.PrintPage;

/**
 * <pre>
 *	BoardService.java - 요청사항 게시판 서비스
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

@Service
public class BoardService{
	private final BoardMapper boardMapper;

	public BoardService(BoardMapper boardMapper){
		this.boardMapper = boardMapper;
	}

	//로그인 생일인증
	public int boardCompareBirthService(Account account) throws Exception{
		return boardMapper.boardCompareBirth(account);
	}

	//비밀번호 변경
	public int boardResetPasswordService(Account account) throws Exception{
		return boardMapper.boardResetPassword(account);
	}

	//비밀번호 null 확인
	public int isPasswordNullService(String user_name) throws Exception{
		return boardMapper.isPasswordNull(user_name);
	}
	//전체진행상황 정보
	public StatInfo boardStatInfoService() throws Exception{
		return boardMapper.boardStatInfo();
	}

	//게시글 목록
	public List<BoardVO> boardListService(PrintPage printPage) throws Exception {

		return boardMapper.boardList(printPage);
	}

	//자동완성 기능
	public List<String> boardSuggestionService(Map<String,String> SearchMap) throws Exception{
		return boardMapper.boardSuggestion(SearchMap);
	}

	//게시글 상세
	public BoardVO boardDetailService(String idxx_numb) throws Exception {

		return boardMapper.boardDetail(idxx_numb);
	}

	//게시글이 답글을 가지고 있는지 확인
	public int boardHasReplyService(String idxx_numb) throws Exception{
		return boardMapper.boardHasReply(idxx_numb);
	}

	//게시글 작성
	public int boardInsertService(BoardVO board) throws Exception {

		return boardMapper.boardInsert(board);
	}

	//게시글 작성 시 idxx_numb 생성
	public String boardSelectIdxxService() throws Exception{
		return boardMapper.boardSelectIdxx();
	}

	//파일 업로드
	public int fileInsertService(FileVO file) throws Exception{
		return boardMapper.fileInsert(file);
	}

	//파일 상세
	public FileVO fileDetailService(String idxx_numb) throws Exception{
		return boardMapper.fileDetail(idxx_numb);
	}

	//게시글 수정
	public int boardUpdateService(BoardVO board) throws Exception {

		return boardMapper.boardUpdate(board);
	}

	//게시글 상태 수정
	public int boardUpdateStatService(BoardVO board)throws Exception{
		return boardMapper.boardUpdateStat(board);
	}

	public String selectCodeNameService(String stat_flag) throws Exception{
		return boardMapper.selectCodeName(stat_flag);
	}

	//게시글 삭제
	public int boardDeleteService(String idxx_numb) throws Exception {

		return boardMapper.boardDelete(idxx_numb);
	}

	//파일 삭제
	public int fileDeleteService(String idxx_numb) throws Exception{

		return boardMapper.fileDelete(idxx_numb);
	}

	//게시글 갯수
	public int boardcount(PrintPage printPage) throws Exception{
		return boardMapper.boardCount(printPage);
	}

	//상태에 따른 조회
	public List<BoardVO> boardFindStatService(PrintPage printPage) throws Exception{
		return boardMapper.boardFindStat(printPage);
	}

	//카테고리 관리자 이메일 조회
	public String getAdminEmailService(String cate_gory) throws Exception{
		return boardMapper.getAdminEmail(cate_gory);
	}
}
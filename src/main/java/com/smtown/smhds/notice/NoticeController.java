package com.smtown.smhds.notice;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.smtown.smhds.account.Account;
import com.smtown.smhds.board.BoardService;
import com.smtown.smhds.util.FileUtil;
import com.smtown.smhds.util.FileVO;
import com.smtown.smhds.util.PageMaker;
import com.smtown.smhds.util.PrintPage;

/**
 * <pre>
 *	NoticeController.java - 공지사항 게시판 컨트롤러
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
 * 2019.09.20	방재훈
 * ====================================================
 * </pre>
 *
 * Copyright SM Entertainment.(C) All right reserved.
 */

@Controller
public class NoticeController {
	
	private static final Logger log = LoggerFactory.getLogger(NoticeController.class);
	
	/*
	 * 공지사항 게시판 서비스(Service) 클래스
	 */
	private final NoticeService noticeService;

	/*
	 * 요청사항 게시판 서비스(Service) 클래스
	 */
	private final BoardService boardService;

	public NoticeController(NoticeService noticeService, BoardService boardService){
		this.noticeService = noticeService;
		this.boardService = boardService;
	}
	/*
	 * 공지사항 목록
	 * @param model - 게시글 정보,페이징 정보, 현재 페이지 ,
	 *		  printPage - 조회하려는 페이징 정보 printPageteria에 파라미터 없을 시에 생성자에서 기본 값으로 셋팅(page = 1 , perPageNum=10)
	 * @return "/notice/NoticeList"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping(value="/notice_list", method=RequestMethod.POST)
	public String noticeList(@ModelAttribute("printPage") PrintPage printPage, Model model) throws Exception{

		int listCnt = noticeService.noticecount(printPage); 		//전체 게시글 수

		List<NoticeVO> noticeList = noticeService.noticeListService(printPage);

		model.addAttribute("list",noticeList);	//전체 게시글 List

		PageMaker pageMaker = new PageMaker();	//페이징 처리 객체 생성
		pageMaker.setprintPage(printPage);		//현재 페이지 정보 
		pageMaker.setTotalCount(listCnt);		//전체 게시글 수

		model.addAttribute("pageMaker",pageMaker);
		model.addAttribute("curpage",printPage.getPage());

		return "notice/NoticeList";
	}

	/*
	 * 자동완성
	 * @param printPage - 출력할 페이지 정보
	 * 		  model - 자동완성 리스트
	 * @return "/common/AutoComplete"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping(value="/notice/autoComplete")
	public String suggestion(@ModelAttribute("printPage") PrintPage printPage, Model model) throws Exception{

		List<String> autoComplete = noticeService.noticeSuggestionService(printPage);
		model.addAttribute("list", autoComplete);

		return "common/AutoComplete";

	}

	/*
	 * 공지사항 상세
	 * @param idxx_numb - 글 번호
	 * 		  model - 글 정보
	 * @return "/notice/NoticeDetail"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping(value="/notice_detail",method=RequestMethod.POST)
	public String noticeDetail(@ModelAttribute("idxx_numb") String idxx_numb, Model model) throws Exception{

		NoticeVO noticeVO = noticeService.noticeDetailService(idxx_numb);
		FileVO fileVO = boardService.fileDetailService(idxx_numb);

		model.addAttribute("detail", noticeVO);
		model.addAttribute("files", fileVO);

		return "notice/NoticeDetail";
	}

	/*
	 * 공지사항 작성
	 * @param
	 * @return "/notice/noticeInsert"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/notice_insert")
	public String noticeInsertForm(Model model) {
		Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String real_name = account.getReal_name();		//실제 이름 받아오기
		model.addAttribute("real_name",real_name);

		return "notice/NoticeInsert";
	}

	/*
	 * 게시글 DB에 등록
	 * @param request - 요청
	 * 		  files - 파일
	 * @return "/list"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@Transactional
	@RequestMapping("/noticeInsertProc")
	public String boardInsertProc(HttpServletRequest request, @RequestPart MultipartFile files) throws Exception{
		try {
			NoticeVO board = new NoticeVO();
			FileVO file;
	
			Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
			//idxx_numb 만들기
			String idxx_numb = "";
	
			idxx_numb = noticeService.noticeSelectIdxxService(); //게시글 주키 생성 로직
	
			String titl_name = request.getParameter("titl_name");
			String cont_ents = request.getParameter("cont_ents");
			String user_name = account.getUsername();
			String crea_user = account.getReal_name();
	
			board.setTitl_name(titl_name);	//게시글 제목 설정
			board.setCont_ents(cont_ents);	//게시글 내용 설정
			board.setUser_name(user_name);	//게시글 작성자 설정
			board.setCrea_user(crea_user);	//게시글 작성자 설정
			board.setIdxx_numb(idxx_numb);
	
			if(!files.isEmpty()) {
				FileUtil fileUtil = new FileUtil();
				file = fileUtil.uploadFile(idxx_numb, files);	//업로드
	
				if(file!=null)
					boardService.fileInsertService(file);		//파일 등록 쿼리 실행
	
			}
	
			noticeService.noticeInsertService(board);
		} catch(Exception e) {
			log.error("boardInsertProc에서 에러발생...");
			throw new RuntimeException(e);
		}


		return "redirect:/main";
	}

	/*
	 * 게시글 삭제
	 * @param idxx_numb - 글 번호
	 * @return "/list"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/notice_delete")
	public String noticeDelete(HttpServletRequest request) throws Exception{
		try {
			String idxx_numb = request.getParameter("idxx_numb");
			FileVO befoFileVO= boardService.fileDetailService(idxx_numb);	//등록돼있는 파일 정보 가져오기
			noticeService.noticeDeleteService(idxx_numb);
			if(befoFileVO != null) {
	
				File file = new File(befoFileVO.getFile_urll()+befoFileVO.getFile_name());	//파일이 등록돼있는 URL에서 파일 가져오기
				file.delete();									//파일 삭제
				boardService.fileDeleteService(idxx_numb);		//DB에서 파일정보 삭제
			}
		} catch(Exception e) {
			log.error("noticeDelete에서 에러발생...");
			throw new RuntimeException(e);
		}
		return "redirect:/main";
	}

	/*
	 * 게시글 수정
	 * @param idxx_numb - 글 번호
	 * 		  model - 게시글 정보
	 * @return "/notice/NoticeUpdate"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/notice_update")
	public String boardUpdateForm(HttpServletRequest request, Model model) throws Exception{
		String idxx_numb = request.getParameter("idxx_numb");
		model.addAttribute("detail", noticeService.noticeDetailService(idxx_numb));
		if(boardService.fileDetailService(idxx_numb) != null) {
			model.addAttribute("files",boardService.fileDetailService(idxx_numb));
		}
		return "notice/NoticeUpdate";
	}

	/*
	 * 게시글 DB 수정
	 * @param request - 요청
	 * @return "/notice_detail/{idxx_numb}"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/notice_updateProc")
	public String boardUpdateProc(HttpServletRequest request, @RequestPart MultipartFile files) throws Exception{
		try {
			NoticeVO notice = new NoticeVO();
			FileUtil fileUtil = new FileUtil();
	
			String titl_name = request.getParameter("titl_name");
			String cont_ents = request.getParameter("cont_ents");
			String idxx_numb = request.getParameter("idxx_numb");
	
			notice.setTitl_name(titl_name);
			notice.setCont_ents(cont_ents);
			notice.setIdxx_numb(idxx_numb);
	
			FileVO befoFileVO = boardService.fileDetailService(idxx_numb);	//idxx_numb가 가지고 있는 파일 정보 가져오기
			FileVO fileVO;	//새로 등록할 파일 정보
	
			if(befoFileVO!=null && !files.isEmpty()) {//게시글에 파일이 등록되어 있고 등록한 파일이 있을 경우
				boardService.fileDeleteService(idxx_numb);		//DB에 등록돼 있는 파일 정보 삭제
				File file = new File(befoFileVO.getFile_urll()+befoFileVO.getFile_name());	//파일이 등록돼있는 URL에서 파일 가져오기
				file.delete();									//파일 삭제
				fileVO= fileUtil.uploadFile(idxx_numb, files);    //새로운 파일 업로드
				boardService.fileInsertService(fileVO);			//새로운 파일 정보 DB에 입력
			} else if(befoFileVO==null && !files.isEmpty()) {		//파일이 등록돼 있지 않을 경우
				fileVO = fileUtil.uploadFile(idxx_numb, files);    //업로드
				boardService.fileInsertService(fileVO);			//파일 정보 DB에 입력
			}
	
			noticeService.noticeUpdateService(notice);
		} catch(Exception e) {
			log.error("boardUpdateProc에서 에러발생...");
			throw new RuntimeException(e);
		}
		return "redirect:/main";
	}
}

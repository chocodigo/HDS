package com.smtown.smhds.faq;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smtown.smhds.account.Account;
import com.smtown.smhds.board.BoardService;
import com.smtown.smhds.util.FileUtil;
import com.smtown.smhds.util.FileVO;
import com.smtown.smhds.util.PageMaker;
import com.smtown.smhds.util.PrintPage;

/**
 * <pre>
 *	faqController.java - FAQ 게시판 컨트롤러
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
 * 2019.09.20	방재훈		
 * ====================================================
 * </pre>
 *
 * Copyright SM Entertainment.(C) All right reserved.
 */

@Controller
public class FaqController {
	
	
	private static final Logger log = LoggerFactory.getLogger(FaqController.class);

	
	/*
	 * faq 게시판 서비스(Service) 클래스
	 */
	private final FaqService faqService;

	/*
	 * 요청사항 게시판 서비스(Service) 클래스
	 */
	private final BoardService boardService;

	@Autowired
	public FaqController(FaqService faqService, BoardService boardService){
		this.faqService = faqService;
		this.boardService = boardService;
	}

	/*
	 * faq 목록
	 * @param model - 게시글 정보,페이징 정보, 현재 페이지 ,
	 *		  printPage - 조회하려는 페이징 정보 printPageteria에 파라미터 없을 시에 생성자에서 기본 값으로 셋팅(page = 1 , perPageNum=10)
	 * @return "/faq/faqList"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping(value="/faq_list", method=RequestMethod.POST)
	public String faqList(@ModelAttribute("printPage") PrintPage printPage, Model model) throws Exception{

		int listCnt = faqService.faqcount(printPage); 		//전체 게시글 수

		List<FaqVO> faqList = faqService.faqListService(printPage);		//전체 게시글 조회

		model.addAttribute("list",faqList);	//전체 게시글 List

		PageMaker pageMaker = new PageMaker();	//페이징 처리 객체 생성
		pageMaker.setprintPage(printPage);		//현재 페이지 정보
		pageMaker.setTotalCount(listCnt);		//전체 게시글 수

		model.addAttribute("pageMaker",pageMaker);
		model.addAttribute("curpage",printPage.getPage());

		return "faq/FaqList";
	}

	/*
	 * 자동완성
	 * @param printPage - 출력할 페이지 정보
	 * 		  model - 자동완성 리스트
	 * @return "/common/AutoComplete"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping(value="/faq/autoComplete")
	public String suggestion(HttpServletRequest request, Model model) throws Exception{
		String search=request.getParameter("search");	//search값 가져오기
		Map<String,String> searchMap = new HashMap<>();
		searchMap.put("search",search);

		List<String> autoComplete = faqService.faqSuggestionService(searchMap);		//자동완성 Query 실행
		model.addAttribute("list", autoComplete);								//listdp 결과값 넘기기

		return "common/AutoComplete";

	}

	/*
	 * faq 상세
	 * @param idxx_numb - 글 번호
	 * 		  model - 글 정보
	 * @return "/faq/faqDetail"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping(value="/faq_detail",method=RequestMethod.POST)
	public String faqDetail(@ModelAttribute("idxx_numb") String idxx_numb, Model model) throws Exception{

		FaqVO faqVO = faqService.faqDetailService(idxx_numb);	//글 상세정보 조회
		FileVO fileVO = boardService.fileDetailService(idxx_numb);	//파일 정보 조회

		model.addAttribute("detail", faqVO);		//글 상세정보 넘기기
		model.addAttribute("files", fileVO);		//글 파일 넘기기

		return "faq/FaqDetail";
	}

	/*
	 * faq 작성
	 * @param
	 * @return "/faq/faqInsert"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/faq_insert")
	public String faqInsertForm(Model model) {
		Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String real_name = account.getReal_name();		//실제 이름 받아오기
		model.addAttribute("real_name",real_name);

		return "faq/FaqInsert";
	}

	/*
	 * 게시글 DB에 등록
	 * @param request - 요청
	 * 		  files - 파일
	 * @return "/list"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@Transactional
	@RequestMapping("/faqInsertProc")
	public String boardInsertProc(HttpServletRequest request, @RequestPart MultipartFile files) throws Exception{
		try {
			FaqVO board = new FaqVO();		//FaqVO 생성
			PrintWriter out = null;
	
			Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();	//로그인된 계정 정보 받아오기
	
			String idxx_numb = faqService.faqSelectIdxxService(); 		//게시글 주키 생성 로직
	
			String titl_name = request.getParameter("titl_name");
			String cont_ents = request.getParameter("cont_ents");
			String user_name = account.getUsername();
			String crea_user = account.getReal_name();
	
			board.setTitl_name(titl_name);	//게시글 제목 설정
			board.setCont_ents(cont_ents);	//게시글 내용 설정
			board.setUser_name(user_name);	//게시글 작성자 설정
			board.setCrea_user(crea_user);	//게시글 작성자 설정
			board.setIdxx_numb(idxx_numb);	//게시글 주키 설정
	
			//파일이 존재할 경우
			if(!files.isEmpty()) {
				FileUtil fileUtil = new FileUtil();
				FileVO file = fileUtil.uploadFile(idxx_numb, files);	//업로드
	
				if(file!=null)
					boardService.fileInsertService(file);		//파일 등록 쿼리 실행
	
			}
			faqService.faqInsertService(board);		//게시글 등록 Query 실행
		} catch(Exception e) {
			log.error("boardInsertProc에서 에러발생...");
			throw new RuntimeException(e);
		}

		return "redirect:/main?tab_menu=faq";
	}

	/*
	 * 게시글 삭제
	 * @param request - 글 번호
	 * @return
	 * @exception Exception - 조회시 발생한 예외
	 */
	@Transactional
	@RequestMapping("/faq_delete")
	@ResponseBody
	public int faqDelete(HttpServletRequest request) throws Exception{
		String idxx_numb = request.getParameter("idxx_numb");
		try {
			FileVO befoFileVO= boardService.fileDetailService(idxx_numb);	//등록돼있는 파일 정보 가져오기
			//파일이 존재할 경우
			if(befoFileVO != null) {
	
				File file = new File(befoFileVO.getFile_urll()+befoFileVO.getFile_name());	//파일이 등록돼있는 URL에서 파일 가져오기
				file.delete();									//파일 삭제
				boardService.fileDeleteService(idxx_numb);		//DB에서 파일정보 삭제
			}
		} catch(Exception e) {
			log.error("faqDelete에서 에러발생...");
			throw new RuntimeException(e);
		}
		
		return faqService.faqDeleteService(idxx_numb);
	}

	/*
	 * 게시글 수정
	 * @param request - 글번호
	 * @return "/faq/faqUpdate"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/faq_update")
	public String boardUpdateForm(HttpServletRequest request, Model model) throws Exception{
		String idxx_numb = request.getParameter("idxx_numb");
		FaqVO faqVO = faqService.faqDetailService(idxx_numb);
		model.addAttribute("detail", faqVO);		//게시글 정보 넘기기

		FileVO fileVO = boardService.fileDetailService(idxx_numb);
		//파일이 존재할 경우
		if( fileVO != null) {
			model.addAttribute("files",fileVO);	//파일 정보 넘기기
		}
		return "faq/FaqUpdate";
	}

	/*
	 * 게시글 DB 수정
	 * @param request - 게시글 정보 받아오기
	 * 		  response - 지원하지 않는 첨부파일 등록 시 오류 알람
	 * 		  files - 첨부파일
	 * @return "/faq_detail/{idxx_numb}"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@Transactional
	@RequestMapping("/faq_updateProc")
	public String boardUpdateProc(HttpServletRequest request, @RequestPart MultipartFile files) throws Exception{
		try {
			FaqVO faq = new FaqVO();
			FileUtil fileUtil = new FileUtil();
	
			String titl_name = request.getParameter("titl_name");	//게시글 제목 받아오기
			String cont_ents = request.getParameter("cont_ents");	//게시글 내용 받아오기
			String idxx_numb = request.getParameter("idxx_numb");	//게시글 주키 받아오기
	
			faq.setTitl_name(titl_name);	//게시글 제목 등록
			faq.setCont_ents(cont_ents);	//게시글 내용 등록
			faq.setIdxx_numb(idxx_numb);	//게시글 주키 등록
	
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
	
	
			faqService.faqUpdateService(faq);	//게시글 수정 Query 실행
		} catch(Exception e) {
			log.error("boardUpdateProc에서 에러발생...");
			throw new RuntimeException(e);
		}
		return "redirect:/main?tab_menu=faq";
	}
}

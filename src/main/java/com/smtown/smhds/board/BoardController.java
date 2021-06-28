package com.smtown.smhds.board;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smtown.smhds.account.Account;
import com.smtown.smhds.account.AccountService;
import com.smtown.smhds.admin.AdminService;
import com.smtown.smhds.admin.CategoryVO;
import com.smtown.smhds.comment.CommentService;
import com.smtown.smhds.util.FileUtil;
import com.smtown.smhds.util.FileVO;
import com.smtown.smhds.util.MailUtil;
import com.smtown.smhds.util.PageMaker;
import com.smtown.smhds.util.PrintPage;


/**
 * <pre>
 *	BoardController.java - 요청사항 게시판 컨트롤러
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
 * 2019.09.20	방재훈		
 * ====================================================
 * </pre>
 *
 * Copyright SM Entertainment.(C) All right reserved.
 */
@Controller
public class BoardController{
	
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);

	
	// 요청사항 게시판 서비스(Service) 클래스
	private final BoardService boardService;

	// 카테고리 관리 서비스(Service 클래스
	private final AdminService adminService;

	//답글 서비스(Service) 클래스
	private final CommentService commentService;

	//계정 서비스(Service) 클래스
	private final AccountService accountService;

	private final MailSender sender;  //메일 발송 기능을 위한 sender 객체


	@Autowired
	public BoardController(BoardService boardService, CommentService commentService,
						   AccountService accountService, AdminService adminService,
						   MailSender sender){
		this.boardService = boardService;
		this.commentService = commentService;
		this.accountService = accountService;
		this.adminService = adminService;
		this.sender = sender;
	}

	/*
	 * 최초 프로젝트 접근 시 목록페이지로 넘김
	 * @param
	 * @return "/main"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping(value="/")
	public String root() {
		return "redirect:/main";
	}

	/*
	 * 로그인 페이지
	 * @param
	 * @return "/main/login"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/login")
	public String login() {
		return "main/login";
	}

	/*
	 * 비밀번호 초기화 페이지
	 * @param
	 * @return "/main/login"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/forgot")
	public String forgot() {
		return "main/forgot";
	}

	/*
	 * 생일 인증
	 * @param request : 입력된 ID, 생일
	 * @return returnMap : 조회성공 여부(성공 1, 실패 0)
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping(value = "/forgotProc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> forgotProc(HttpServletRequest request) throws Exception{
		Map<String,String> returnMap = new HashMap<>();	//ID, 생일 추가할 맵 생성

		String user_name = request.getParameter("user_name");	//입력된 ID 받아오기
		String phon_enum = request.getParameter("phon_enum");	//입력된 생일 받아오기

		Account account = new Account();								//account 객체 생성
		account.setUser_name(user_name);								//user_name 설정
		account.setPhon_enum(phon_enum);								//phon_enum 설정

		int isItPermit = boardService.boardCompareBirthService(account);		//user_name, phon_enum 비교 서비스

		returnMap.put("isItPermit",String.valueOf(isItPermit));					//조회성공 1, 조회실패 0을 isItPermit값에 저장후 리턴

		return returnMap;
	}

	/*
	 * 비밀번호 재설정
	 * @param request : 입력된 ID, 비밀번호
	 * @return 로그인 페이지로 이동
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping(value = "/resetPWProc", method = RequestMethod.POST)
	@ResponseBody
	public int resetPWProc(HttpServletRequest request) throws Exception{
		//bycrpt encoder 설정
		Map encoders = new HashMap<>();
		encoders.put("bcrypt",new BCryptPasswordEncoder());
		PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("bcrypt",encoders);

		String user_name = request.getParameter("user_name");	//입력된 ID 받아오기
		String pass_word = request.getParameter("pass_word");	//입력된 비밀번호 받아오기
		String enc_pass_word = passwordEncoder.encode(pass_word);	//패스워드 암호화

		Account account = new Account();	//account 객체 생성
		account.setUser_name(user_name);	//user_name 설정
		account.setPass_word(enc_pass_word);	//pass_word 설정

		return boardService.boardResetPasswordService(account);	//비밀번호 변경 서비스

	}

	/*
	 * 비밀번호 null 확인
	 * @param request : 입력된 ID
	 * @return : 패스워드 존재시 1, 없을 시 0
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/isPwNull")
	@ResponseBody
	public int isPwNull(HttpServletRequest request) throws Exception{
		String user_name = request.getParameter("user_name");	//user_name 받아오기

		return boardService.isPasswordNullService(user_name);		//패스워드 존재하는지 확인하는 Query 실행

	}
	/*
	 * 게시판 목록
	 * @param model - 게시글 정보,페이징 정보, 현재 페이지 ,
	 *		  printPage - 조회하려는 페이징 정보 printPage에 파라미터 없을 시에 생성자에서 기본 값으로 셋팅(page = 1 , perPageNum=5)
	 * @return "/board/BoardList"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping(value="/list")
	public String boardList(HttpServletRequest request, @ModelAttribute("printPage") PrintPage printPage, Model model) throws Exception{

		String cate_gory = request.getParameter("cate_gory");			//카테고리받아오기
		printPage.setCate_gory(cate_gory);									//카테고리 설정

		int listCnt = boardService.boardcount(printPage); 					//전체 게시글 수
		List<BoardVO> list = boardService.boardListService(printPage);		//전체 게시글 list
		List<CategoryVO> category_list = adminService.getCategoryListService();	//카테고리 list



		model.addAttribute("list", list);	//전체 게시글 List
		model.addAttribute("category_list", category_list);//카테고리 이름 넘기기

		PageMaker pageMaker = new PageMaker();	//페이징 처리 객체 생성
		pageMaker.setprintPage(printPage);		//현재 페이지 정보 
		pageMaker.setTotalCount(listCnt);		//전체 게시글 수

		model.addAttribute("pageMaker",pageMaker);
		model.addAttribute("curpage",printPage.getPage());
		model.addAttribute("cate_gory",cate_gory);

		return "board/BoardList";
	}

	/*
	 * 게시판 상세
	 * @param idxx_numb - 글 번호
	 * 		  model - 글 정보
	 * @return "/board/BoardDetail"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping(value="/detail",method=RequestMethod.POST)
	public String boardDetail(@ModelAttribute("idxx_numb") String idxx_numb, Model model) throws Exception{
		BoardVO detail = boardService.boardDetailService(idxx_numb);	//게시글 정보
		FileVO files = boardService.fileDetailService(idxx_numb);		//게시글의 첨부파일 정보
		int hasReply = boardService.boardHasReplyService(idxx_numb);	//게시글에 답글이 등록돼있는지 갯수 확인

		model.addAttribute("detail", detail); 		//글정보를 넘기기
		model.addAttribute("files", files);			//파일 정보 넘기기
		model.addAttribute("hasReply",hasReply);	//답글갯수 넘기기

		//답글 갯수가 1이면 답글 ID 넘기기
		if(hasReply == 1) {
			String comm_idxx = commentService.commentSelectCommIdxxService(idxx_numb);
			model.addAttribute("comm_idxx",comm_idxx);
		}

		return "board/BoardDetail";
	}

	/*
	 * 파일 다운로드
	 * @param idxx_numb - 글 번호
	 * 		  request - 요청
	 * 		  response - 응답
	 * @return
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping(value="/fileDown/{idxx_numb}")
	public void fileDown(@PathVariable String idxx_numb, HttpServletRequest request, HttpServletResponse response) throws Exception{

		request.setCharacterEncoding("UTF-8");
		FileVO fileVO = boardService.fileDetailService(idxx_numb);

		try {
			//파일 업로드 된 경로
			String fileUrl = fileVO.getFile_urll();			//fileUrl : 업로드된 파일 경로
			fileUrl += "/";
			String fileName = fileVO.getFile_name();		 //fileName : 파일 원본 이름

			//실제 내보낼 파일명
			String oriFileName = fileVO.getFile_orig();		//oriFileName : 원래 파일명
			InputStream in = null;
			OutputStream os = null;
			File file = null;
			boolean skip = false;							//skip : 파일 읽지못하면 false
			String browser = "";

			//파일을 읽어 스트림에 담기
			try {
				file = new File(fileUrl, fileName);
				in = new FileInputStream(file);
			}catch(FileNotFoundException fe) {
				skip = true;
			}
			browser = request.getHeader("User-Agent");

			//파일 다운로드 헤더 지정
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-DesprintPageption", "JSP Generated Data");

			//파일을 읽었을 경우
			if(!skip) {
				//IE
				if(browser.contains("MSIE")) {
					response.setHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ")+"\"");

				}
				//IE 11 이상
				else if(browser.contains("Trident")) {
					response.setHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(oriFileName, "UTF-8").replaceAll("\\+", "\\ ")+"\"");
				}
				else {
					response.setHeader("Content-Disposition", "attachment; filename=\""
							+ new String(oriFileName.getBytes("UTF-8"), "ISO8859_1") + "\"");
					response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
				}
				response.setHeader("Content-Length", "" + file.length());
				os = response.getOutputStream();
				byte b[] = new byte[(int)file.length()];
				int leng = 0;
				while((leng = in.read(b)) > 0) {
					os.write(b, 0 ,leng);
				}
			}else {
				response.setContentType("text/html;charset=UTF-8");

				PrintWriter out = response.getWriter();
				out.println("<script>alert('파일을 찾을 수 없습니다.');history.back();</script>");
				out.flush();
			}
			in.close();
			os.close();
		} catch (Exception e) {
			System.out.println("Error : " + e.getMessage());
		}
	}

	/*
	 * 게시글 작성
	 * @param
	 * @return "/board/BoardInsert"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/insert")
	public String boardInsertForm(Model model) throws Exception {
		model.addAttribute("category_list",adminService.getCategoryListService());//카테고리 이름 넘기기
		Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String user_name = account.getUsername();	//로그인한 사용자의 user_name받아오기
		Account user_account = accountService.accountDetailService(user_name);		//user_name을 통해 계정정보 받아오기
		String real_name = user_account.getReal_name();		//사용자의 실제 이름 받아오기
		model.addAttribute("real_name",real_name);

		return "board/BoardInsert";
	}

	/*
	 * 게시글 DB에 등록
	 * @param request - 요청
	 * 		  files - 파일
	 * @return "/list"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@Transactional
	@RequestMapping("/insertProc")
	public String boardInsertProc(HttpServletRequest request,  @RequestPart MultipartFile files) throws Exception{
		
		try {
		
			BoardVO board = new BoardVO();
			FileVO file;
			String idxx_numb = boardService.boardSelectIdxxService(); //게시글 주키 생성
	
			Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();	//acoount 정보 받아오기
			String user_name = account.getUsername();	//로그인한 사용자의 user_name받아오기
			Account user_account = accountService.accountDetailService(user_name);		//user_name을 통해 계정정보 받아오기
	
			String titl_name = request.getParameter("titl_name");
			String cont_ents = request.getParameter("cont_ents");
			String crea_user = user_account.getReal_name();
			String stat_flag = "01";
			String cate_gory = request.getParameter("cate_gory");
	
			board.setTitl_name(titl_name);	//게시글 제목
			board.setCont_ents(cont_ents);	//게시글 내용
			board.setUser_name(user_name);	//작성자 이름
			board.setCrea_user(crea_user);	//작성자 이름
			board.setStat_flag(stat_flag);	//첫 글 작성시 신규상태로 초기화( 01 - 신규/ 02 - 담당자 접수/ 03 - 진행/ 04 - 완료)
			board.setIdxx_numb(idxx_numb);	//idxx_numb 생성
			board.setCate_gory(cate_gory);	//카테고리 설정
	
			//파일첨부시
			if(!files.isEmpty()) {
				FileUtil fileUtil = new FileUtil();
				file = fileUtil.uploadFile(idxx_numb, files);	//업로드
	
				if(file!=null)
					boardService.fileInsertService(file);		//파일 등록 쿼리 실행
	
			}
	
			boardService.boardInsertService(board);		//게시글 등록 쿼리 실행
		
		} catch(Exception e) {
			log.error("boardInsertProc에서 에러발생...");
			throw new RuntimeException(e);
		}
		return "redirect:/main?tab_menu=list";
	}

	/*
	 * 게시글 수정
	 * @param idxx_numb - 글 번호
	 * 		  model - 게시글 정보
	 * @return "/board/BoardUpdate"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/update")
	public String boardUpdateForm(HttpServletRequest request, Model model) throws Exception{
		String idxx_numb = request.getParameter("idxx_numb");

		List category_list =  adminService.getCategoryListService(); //카테고리 이름 넘기기
		BoardVO boardVO = boardService.boardDetailService(idxx_numb);	//idxx_numb를 가진 글 상세정보 넘기기

		model.addAttribute("category_list",category_list);
		model.addAttribute("detail", boardVO);
		if(boardService.fileDetailService(idxx_numb) != null) {									//idxx_numb를 가진 파일 존재 시
			model.addAttribute("files",boardVO);	//idxx_numb를 가진 파일 상세정보 넘기기

		}
		return "board/BoardUpdate";
	}

	/*
	 * 게시글 DB 수정
	 * @param request - 수정할 글 정보 받아오기
	 * 		  response - 지원하지 않는 첨부파일 등록 시 오류 알람
	 * 		  files - 첨부파일
	 * @return "/detail/idxx_numb"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/updateProc")
	public String boardUpdateProc(HttpServletRequest request, HttpServletResponse response , @RequestPart MultipartFile files) throws Exception{
		BoardVO board = new BoardVO();

		FileUtil fileUtil = new FileUtil();

		String titl_name = request.getParameter("titl_name");	//글 제목 받아오기
		String cont_ents = request.getParameter("cont_ents");	//글 내용 받아오기
		String idxx_numb = request.getParameter("idxx_numb");	//글 ID 받아오기
		String cate_gory = request.getParameter("cate_gory");	//글 카테고리 받아오기

		board.setTitl_name(titl_name);		//글 제목 설정
		board.setCont_ents(cont_ents);		//글 내용 설정
		board.setIdxx_numb(idxx_numb);		//글 ID 설정
		board.setCate_gory(cate_gory);		//글 카테고리 설정

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

		boardService.boardUpdateService(board);		//글 update Query 실행

		return "redirect:/main?tab_menu=list";
	}

	/*
	 * 게시글 DB 파일 삭제
	 * @param request - idxx_numb 요청
	 * @return boardService.fileDeleteService(idxx_numb)
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/fileDelete")
	@ResponseBody
	public int boardFileDelete(HttpServletRequest request) throws Exception{
		String idxx_numb = request.getParameter("idxx_numb");		//글 ID 받아오기
		FileVO befoFileVO= boardService.fileDetailService(idxx_numb);	//등록돼있는 파일 정보 가져오기

		File file = new File(befoFileVO.getFile_urll()+befoFileVO.getFile_name());	//파일이 등록돼있는 URL에서 파일 가져오기
		file.delete();									//파일 삭제

		return boardService.fileDeleteService(idxx_numb);		//게시글 삭제 Query 실행

	}

	/*
	 * 게시글 진행상황 DB 수정
	 * @param request - idxx_numb, stat_flag
	 * @return "/detail/idxx_numb"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@Transactional
	@RequestMapping("/updateStatProc")
	public String boardUpdateStatProc(HttpServletRequest request) throws Exception{
		try {
			BoardVO board = new BoardVO();
			Account boardAccount = new Account();	//글 작성자 계정
	
			String stat_flag = request.getParameter("stat_flag");
			String idxx_numb = request.getParameter("idxx_numb");
			String titl_name = request.getParameter("titl_name");
			String user_name = request.getParameter("user_name");
				
			board.setStat_flag(stat_flag);	//변화 상태 받아오기
			board.setIdxx_numb(idxx_numb);	//idxx_numb 받아오기
			board.setTitl_name(titl_name);	//게시글 제목 받아오기
			board.setUser_name(user_name);	//글 작성자 받아오기
	
			boardService.boardUpdateStatService(board);					//게시글 상태 변화 Query 실행
			boardAccount = accountService.accountDetailService(user_name);		//글작성자 계정정보 받아오기
	
			//글 상태 수정 시 글 작성자에게 메일 보내기
			MailUtil mailUtil=new MailUtil(sender);
	
			//메일 내용 설정
			String from="cocoa1149@smtown.com";
			String to =boardAccount.getUser_mail();
			String subject = "smHDS : 등록된 글의 진행상황이 변경되었습니다.";
			String mailMsg = "등록하신 요청사항 '"+board.getTitl_name()+"' 의 진행상황이 '"+
					boardService.selectCodeNameService(stat_flag)+
					"'으로 변경되었습니다.";
	
			mailUtil.sendMail(from,to,subject,mailMsg);				//메일 내용 보내기
		} catch(Exception e) {
			log.error("boardUpdateStatProc에서 에러발생...");
			throw new RuntimeException(e);
		}
		return "redirect:/main?tab_menu=list";
	}

	/*
	 * 게시글 삭제
	 * @param
	 * @return "/list/"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@Transactional
	@RequestMapping("/delete")
	public String boardDelete(HttpServletRequest request) throws Exception{
		try {
			String idxx_numb = request.getParameter("idxx_numb");		//삭제할 게시글 ID 가져오기
			FileVO befoFileVO= boardService.fileDetailService(idxx_numb);	//게시글에 등록돼있는 파일 정보 가져오기
			boardService.boardDeleteService(idxx_numb);						//게시글 삭제 Query 실행
	
			//파일이 등록돼있으면
			if(befoFileVO != null) {
	
				File file = new File(befoFileVO.getFile_urll()+befoFileVO.getFile_name());	//파일이 등록돼있는 URL에서 파일 가져오기
				file.delete();									//파일 삭제
				boardService.fileDeleteService(idxx_numb);		//DB에서 파일정보 삭제
			}
		} catch(Exception e) {
			log.error("boardDelete에서 에러발생...");
			throw new RuntimeException(e);
		}
		return "redirect:/main?tab_menu=list";
	}

	/*
	 * 자동완성
	 * @param printPage - 출력할 페이지 정보
	 * 		  model - 자동완성 리스트
	 * @return "/common/AutoComplete"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping(value="/board/autoComplete")
	public String suggestion(HttpServletRequest request, Model model) throws Exception{
		String search = request.getParameter("search");								//search 값 가져오기
		Map<String,String> searchMap = new HashMap<>();
		searchMap.put("search",search);


		List<String> autoComplete = boardService.boardSuggestionService(searchMap);		//자동완성 Query 실행
		model.addAttribute("list", autoComplete);									//list에 결과값 넘기기

		return "common/AutoComplete";

	}

	/*
	 * 메인화면
	 * @param model - 공지사항 5개 출력 , FAQ 5개 출력
	 * @return "/main/boardMain"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping(value="/main")
	public String boardMain(@ModelAttribute("tab_menu") String tabMenu, Model model) throws Exception{
		Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();	//로그인한 계정 정보
		String user_name = account.getUsername();	//로그인한 계정의 user_name
		Account accountDetail = accountService.accountDetailService(user_name);	//사용자 상세정보 가져오기

		model.addAttribute("tab_menu",tabMenu);
		model.addAttribute("accountDetail",accountDetail);

		StatInfo statInfo = boardService.boardStatInfoService();			//전체 진행상황 정보

		model.addAttribute("statInfo",statInfo);

		return "main/boardMain";
	}

	/*
	 * 메인화면 상태에 따른 분류
	 * @param model - 공지사항 5개 출력 , FAQ 5개 출력
	 * @return "/main/boardMain"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping(value="/findByStat")
	public String boardFindStat(HttpServletRequest request, @ModelAttribute("printPage") PrintPage printPage, Model model) throws Exception{
		String stat_flag = request.getParameter("stat_flag");	//상태코드 받아오

		String cate_gory = request.getParameter("cate_gory");	//카테고리받아오기

		printPage.setCate_gory(cate_gory);
		printPage.setStat_flag(stat_flag);

		StatInfo statInfo = boardService.boardStatInfoService();			//전체 진행상황 정보

		int listCnt = 0; 		//전체 게시글 수

		switch (stat_flag){
			case "01":
				listCnt = statInfo.getStat_neww();
				break;
			case "02":
				listCnt = statInfo.getStat_chek();
				break;
			case "03":
				listCnt = statInfo.getStat_load();
				break;
			case "04":
				listCnt = statInfo.getStat_comp();
				break;
		}


		model.addAttribute("list",boardService.boardFindStatService(printPage));	//전체 게시글 List
		model.addAttribute("category_list",adminService.getCategoryListService());//카테고리 이름 넘기기

		PageMaker pageMaker = new PageMaker();	//페이징 처리 객체 생성
		pageMaker.setprintPage(printPage);		//현재 페이지 정보
		pageMaker.setTotalCount(listCnt);		//전체 게시글 수

		model.addAttribute("pageMaker",pageMaker);
		model.addAttribute("curpage",printPage.getPage());
		model.addAttribute("cate_gory",cate_gory);

		return "board/BoardList";
	}

}
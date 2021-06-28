package com.smtown.smhds.comment;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.smtown.smhds.account.Account;
import com.smtown.smhds.account.AccountService;
import com.smtown.smhds.board.BoardService;
import com.smtown.smhds.board.BoardVO;
import com.smtown.smhds.util.FileUtil;
import com.smtown.smhds.util.FileVO;
import com.smtown.smhds.util.MailUtil;

/**
 * <pre>
 *	CommentController.java - 요청사항 게시판 댓글 컨트롤러
 * </pre>
 *
 * @author	최해림
 * @since	2019.06.10
 * @version	1.0
 *
 * <pre>
 * == Modification Information ==
 * Date		Modifier		Comment
 * ====================================================
 * 2019.06.10	최해림		Initial Created.
 * ====================================================
 * </pre>
 *
 * Copyright SM Entertainment.(C) All right reserved.
 */

@Controller
@RequestMapping("/comment")
public class CommentController {

	/*
	 * 요청사항 게시판 답글 서비스(Service) 클래스
	 */
	private final CommentService commentService;


	/*
	 * 요청사항 게시판 서비스(Service) 클래스
	 */
	private final BoardService boardService;

	private final AccountService accountService;	//계정 정보 서비스(Service) 클래스

	private final MailSender sender;  //메일 발송 기능을 위한 sender 객체

	@Autowired
	public CommentController(CommentService commentService, BoardService boardService,
							 AccountService accountService, MailSender sender){
		this.commentService=commentService;
		this.boardService=boardService;
		this.accountService=accountService;
		this.sender=sender;
	}
	/*
	 * 답글 등록
	 * @param idxx_numb - 댓글 등록할 게시글 ID
	 * 		  model - idxx_numb 넘기기
	 * @return 댓글 작성 Query 실행
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/insert")
	public String commentInsert(HttpServletRequest request, Model model) throws Exception{

		String idxx_numb = request.getParameter("idxx_numb");
		BoardVO boardVO = boardService.boardDetailService(idxx_numb);	//답글을 등록할 글의 정보 받아오기

		String user_name = boardVO.getUser_name();				//글의 정보 중 작성자의 ID 받아오기
		Account account = accountService.accountDetailService(user_name);		//작성자정보 받아오기
		String user_mail = account.getUser_mail();				//작성자의 메일 주소 받아오기

		model.addAttribute("idxx_numb",idxx_numb);			//글의 idxx_numb 넘기기
		model.addAttribute("user_mail", user_mail);			//글 작성자의 user_mail 넘기기

		Account crea_account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String crea_user_name = crea_account.getUsername();	//로그인한 사용자의 user_name받아오기
		Account user_account = accountService.accountDetailService(crea_user_name);		//user_name을 통해 계정정보 받아오기
		String real_name = user_account.getReal_name();		//사용자의 실제 이름 받아오기
		model.addAttribute("real_name",real_name);

		return "comment/commentInsert";				//댓글 작성 Query 실행
	}

	/*
	 * 답글 등록 과정
	 * @param request - 글 등록에 필요한 정보 받아오기
	 * @return 댓글 작성 Query 실행
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/insertProc")
	public String commentInsertProc(HttpServletRequest request, HttpServletResponse response , @RequestPart MultipartFile files) throws Exception{
		Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();	//계정정보 받아오기
		String user_name = account.getUsername();	//로그인한 사용자의 user_name받아오기
		Account user_account = accountService.accountDetailService(user_name);		//user_name을 통해 계정정보 받아오기

		String idxx_numb = request.getParameter("idxx_numb");	//idxx_numb 받아오기
		String real_name = user_account.getReal_name();
		String comm_cont = request.getParameter("comm_cont");	//답글 내용 받아오기
		String comm_idxx = commentService.commentSelectIdxxService();	//comm_idxx만들기
		String comm_titl = request.getParameter("comm_titl");		//답글 제목 받아오기
		String user_mail = request.getParameter("user_mail");		//요청 글 작성자 메일 받아오기


		CommentVO comment = new CommentVO();								//댓글 VO 생성
		comment.setIdxx_numb(idxx_numb);									//글의 idxx_numb 등록
		System.out.println("idxx_numb : "+ idxx_numb);

		comment.setComm_idxx(comm_idxx); 									//comm_idxx 생성
		comment.setComm_cont(comm_cont);									//댓글의 내용 등록
		comment.setUser_name(account.getUsername());						//사용자 ID 등록
		comment.setComm_titl(comm_titl);									//답글 제목 등록
		comment.setCrea_user(real_name);									//사용자 이름 등록

		if(!files.isEmpty()) {
			FileUtil fileUtil = new FileUtil();
			FileVO file = fileUtil.uploadFile(comm_idxx, files);	//업로드

			if(file!=null)
				boardService.fileInsertService(file);		//파일 등록 쿼리 실행
		}

		commentService.commentInsertService(comment);						//insert 쿼리 실행

		//답글 등록시 글작성자에게 메일 전송
		MailUtil mailUtil=new MailUtil(sender);

		String from="cocoa1149@smtown.com";
		String to =user_mail;
		String subject = "smHDS : 답글이 등록되었습니다.";
		String mailMsg = "제목 : "+comment.getComm_titl()+"\n\n" +
				"내용 : \n\n" +comment.getComm_cont();

		mailUtil.sendMail(from,to,subject,mailMsg);

		return "redirect:/main?tab_menu=list";
	}

	/*
	 * 답글 수정
	 * @param 	idxx_numb - 수정할 답글이 등록된 글의 idxx_numb
	 * 			model - 수정할 내용 폼에 출력
	 * @return 댓글 수정 Query 실행
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/update")
	public String commentUpdate(HttpServletRequest request, Model model) throws Exception{
		String idxx_numb = request.getParameter("idxx_numb");

		CommentVO commentVO = commentService.commentDetailService(idxx_numb);	//idxx_numb를 가지고 있는 답글상세정보 조회 Query
		model.addAttribute("detail_comment", commentVO); 	//답글정보를 넘기기

		String comm_idxx = commentVO.getComm_idxx();			//답글 ID받아오기
		FileVO fileVO = boardService.fileDetailService(comm_idxx);		//comm_idxx를 가진 댓글 조회
		if(fileVO != null) {						//idxx_numb를 가진 파일 존재 시
			model.addAttribute("files",fileVO);	//idxx_numb를 가진 파일 상세정보 넘기기

		}

		return "comment/commentUpdate";
	}

	/*
	 * 답글 DB 수정
	 * @param request - 수정할 글 정보 받아오기
	 * 		  response - 지원하지 않는 첨부파일 등록 시 오류 알람
	 * 		  files - 첨부파일
	 * @return "/detail/idxx_numb"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/updateProc")
	public String commentUpdateProc(HttpServletRequest request, @RequestPart MultipartFile files) throws Exception{
		CommentVO comment = new CommentVO();	//답글 VO 생성
		FileUtil fileUtil = new FileUtil();		//파일 업로드를 위한 fileUtil 클래스 생성

		String comm_titl=request.getParameter("comm_titl");	//수정할 답글 제목 받아오기
		String comm_cont=request.getParameter("comm_cont");	//수정할 답글 내용 받아오기
		String idxx_numb=request.getParameter("idxx_numb");	//수정할 답글이 등록된 글의 ID 받아오기
		System.out.println("updateProc_idxx_numb : "+idxx_numb);
		String comm_idxx=request.getParameter("comm_idxx");	//수정할 답글의 ID 받아오기

		comment.setComm_titl(comm_titl);	//제목 설정
		comment.setComm_cont(comm_cont);	//내용 설정
		comment.setIdxx_numb(idxx_numb);	//답글이 등록된 글 ID설정
		comment.setComm_idxx(comm_idxx);	//수정할 답글 ID 설정

		FileVO befoFileVO = boardService.fileDetailService(comm_idxx);	//idxx_numb가 가지고 있는 파일 정보 가져오기
		FileVO fileVO;	//새로 등록할 파일 정보

		if(befoFileVO!=null && !files.isEmpty()) {//게시글에 파일이 등록되어 있고 등록한 파일이 있을 경우
			boardService.fileDeleteService(comm_idxx);		//DB에 등록돼 있는 파일 정보 삭제
			File file = new File(befoFileVO.getFile_urll()+befoFileVO.getFile_name());	//파일이 등록돼있는 URL에서 파일 가져오기
			file.delete();									//파일 삭제
			fileVO= fileUtil.uploadFile(comm_idxx, files);    //새로운 파일 업로드
			boardService.fileInsertService(fileVO);			//새로운 파일 정보 DB에 입력
		} else if(befoFileVO==null && !files.isEmpty()) {		//파일이 등록돼 있지 않을 경우
			fileVO = fileUtil.uploadFile(comm_idxx, files);    //업로드
			boardService.fileInsertService(fileVO);			//파일 정보 DB에 입력
		}

		commentService.commentUpdateService(comment);	//답글 수정 Query 실행


		return "redirect:/main?tab_menu=list";
	}

	/*
	 * 답글 삭제
	 * @param comm_idxx - 수정할 댓글 ID
	 * 		  comm_cont - 수정할 댓글 내용
	 * @return 댓글 수정 Query 실행
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public int commentDelete(HttpServletRequest request) throws Exception{
		String comm_idxx = request.getParameter("comm_idxx");		//수정할 답글 ID받아오기
		FileVO befoFileVO= boardService.fileDetailService(comm_idxx);	//등록돼있는 파일 정보 가져오기

		//파일이 등록돼있을 경우
		if(befoFileVO != null) {

			File file = new File(befoFileVO.getFile_urll()+befoFileVO.getFile_name());	//파일이 등록돼있는 URL에서 파일 가져오기
			file.delete();									//파일 삭제
			boardService.fileDeleteService(comm_idxx);		//DB에서 파일정보 삭제
		}

		return commentService.commentDeleteService(comm_idxx);	//답글 삭제 Query 실행
	}

	/*
	 * 답글 출력
	 * @param 	request - 답글 정보 받아오기
	 * 			model - 답글 상세 출력
	 * @return - 답글 페이지 리턴
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/detail")
	public String commentDetail(HttpServletRequest request, Model model) throws Exception{
		String idxx_numb = request.getParameter("idxx_numb");	//답글이 등록된 ID 받아오기
		String comm_idxx = request.getParameter("comm_idxx");	//답글 ID 받아오기

		CommentVO commentVO = commentService.commentDetailService(idxx_numb);			//글 정보 조회
		FileVO fileVO = boardService.fileDetailService(comm_idxx);						//파일정보 조회

		model.addAttribute("detail_comment", commentVO); 	//글정보를 넘기기
		model.addAttribute("files", fileVO);		//파일 정보 넘기기


		return "comment/commentDetail";
	}
}

package com.smtown.smhds.util;

import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <pre>
 *	HdsErrorController.java - 에러 컨트롤러
 * </pre>
 *
 * @author	최해림
 * @since	2019.06.18
 * @version	1.0
 *
 * <pre>
 * == Modification Information ==
 * Date		Modifier		Comment
 * ====================================================
 * 2019.06.18	최해림		Initial Created.
 * ====================================================
 * </pre>
 *
 * Copyright SM Entertainment.(C) All right reserved.
 */


@Controller
public class HdsErrorController implements ErrorController{

	private static final String ERROR_PATH = "/error";	//error경로명 지정
	
	/*
	 * error 경로 리턴
	 */
	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return ERROR_PATH; //error 경로 리턴
	}
	
	/*
	 * 에러 페이지 접근 시 출력 
	 * @param request : 에러코드
	 * 		  model : 에러 정보
	 * @return "/main"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, Model model) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);	//에러 코드 저장
		HttpStatus httpStatus = HttpStatus.valueOf(Integer.valueOf(status.toString()));	//http상태코드
		model.addAttribute("code",status.toString());	//model에  에러코드 넘기기
		model.addAttribute("msg",httpStatus.getReasonPhrase());	//model에 상태코드의 이유 넘기기
		model.addAttribute("timestamp",new Date());	//model에 에러가 발생한 날짜 넘기기
		
		return "common/error";
	}

}

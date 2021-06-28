package com.smtown.smhds.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <pre>
 *	BoardController.java - 요청사항 게시판 컨트롤러
 * </pre>
 *
 * @author	최해림
 * @since	2019.06.04
 * @version	1.0
 *
 * <pre>
 * == Modification Information ==
 * Date		Modifier		Comment
 * ====================================================
 * 2019.06.04	최해림		Initial Created.
 * ====================================================
 * </pre>
 *
 * Copyright SM Entertainment.(C) All right reserved.
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

	// 요청사항 게시판 서비스(Service) 클래스
	private final AdminService adminService;

	@Autowired
	public AdminController(AdminService adminService){
		this.adminService = adminService;
	}
	/*
	 * 관리자 페이지로 접근시 페이지 넘김 
	 * @param 
	 * @return "/admin/admin"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/")
	public String admin() throws Exception {

		return "admin/admin";
	}

	/*
	 * 카테고리 관리 페이지
	 * @param
	 * @return "/admin/categoryList"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/categoryList")
	public String categoryList(Model model) throws Exception {
		model.addAttribute("list",adminService.getCategoryListService());	//전체 카테고리 List
		return "admin/categoryList";
	}

	/*
	 * 카테고리 등록 페이지
	 * @param
	 * @return "/admin/categoryInsert"
	 * @exception Exception - 등록시 발생한 예외
	 */
	@RequestMapping("/categoryInsert")
	public String categoryInsert() throws Exception{
		return "admin/categoryInsert";
	}

	/*
	 * 카테고리 중복확인
	 * @param
	 * @return "adminService.checkCommCodeService(comm_code)"
	 * @exception Exception - 등록시 발생한 예외
	 */
	@RequestMapping("/checkCommCode")
	@ResponseBody
	public int checkCommCode(HttpServletRequest request) throws Exception{
		String comm_code = request.getParameter("comm_code");
		return adminService.checkCommCodeService(comm_code);
	}

	/*
	 * 카테고리 등록
	 * @param
	 * @return "redirect:/admin/admin"
	 * @exception Exception - 등록시 발생한 예외
	 */
	@RequestMapping("/categoryInsertProc")
	public String categoryInsertProc(HttpServletRequest request) throws Exception{
		CategoryVO categoryVO = new CategoryVO();
		String comm_code = request.getParameter("comm_code");
		String code_name = request.getParameter("code_name");
		String admi_mail = request.getParameter("admi_mail");

		categoryVO.setComm_code(comm_code);
		categoryVO.setCode_name(code_name);
		categoryVO.setAdmi_mail(admi_mail);

		adminService.insertCategory(categoryVO);

		return "redirect:/admin/";
	}

	/*
	 * 카테고리 상세
	 * @param
	 * @return "/admin/categoryDetail"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/categoryDetail")
	public String categoryDetail(HttpServletRequest request, Model model) throws Exception{
		String comm_code = request.getParameter("comm_code");
		CategoryVO categoryVO = adminService.detailCategoryService(comm_code);
		model.addAttribute("detail",categoryVO);

		return "/admin/categoryDetail";
	}

	/*
	 * 카테고리 수정 페이지
	 * @param
	 * @return "/admin/categoryUpdate"
	 * @exception Exception - 조회시 발생한 예외
	 */
	@RequestMapping("/categoryUpdate")
	public String categoryUpdate(HttpServletRequest request, Model model) throws Exception{
		String comm_code = request.getParameter("comm_code");
		CategoryVO categoryVO = adminService.detailCategoryService(comm_code);
		model.addAttribute("detail",categoryVO);

		return "/admin/categoryUpdate";
	}

	/*
	 * 카테고리 수정
	 * @param
	 * @return "redirect:/admin/admin"
	 * @exception Exception - 수정시 발생한 예외
	 */
	@RequestMapping("/categoryUpdateProc")
	public String categoryUpdateProc(HttpServletRequest request) throws Exception{
		String orig_comm_code = request.getParameter("orig_comm_code");
		CategoryVO categoryVO = new CategoryVO();
		String comm_code = request.getParameter("comm_code");
		String code_name = request.getParameter("code_name");
		String admi_mail = request.getParameter("admi_mail");

		categoryVO.setComm_code(comm_code);
		categoryVO.setCode_name(code_name);
		categoryVO.setAdmi_mail(admi_mail);

		adminService.updateCategoryService(categoryVO,orig_comm_code);

		return "redirect:/admin/";
	}

	/*
	 * 카테고리 삭제
	 * @param
	 * @return "redirect:/admin/admin"
	 * @exception Exception - 수정시 발생한 예외
	 */
	@RequestMapping("/categoryDelete")
	public String categoryDelete(HttpServletRequest request) throws Exception{
		String comm_code = request.getParameter("comm_code");
		adminService.deleteCategory(comm_code);

		return "redirect:/admin/";
	}
}

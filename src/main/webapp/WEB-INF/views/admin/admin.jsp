<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 페이지</title>
</head>
<body>
<script type="text/javascript">
/***************************************************************************************************
 * # Program: 	admin.jsp 관리자 페이지
 *
 * # Modification Information
 * -------------------------------------------------------------------------------------------------
 *   Date			Modifier	Comment
 * -------------------------------------------------------------------------------------------------
 *   2019.07.24		최해림		Initial Created.
 *
 * -------------------------------------------------------------------------------------------------
 * Copyright 2019-2019 By SM Entertainment Co,Ltd. All rights reserved.
 ****************************************************************************************************
 *   Form Common Script
 *      1) 공통 스크립트 위치 및 명칭은 수정 불가 입니다.
 *      2) 각 함수에서 필요한 부분의 소스를 수정 하세요.
 *      3) 해당 프로그램의 기능이 없는 경우 해당 함수의 이름만 놓고 공백 처리
 *
 *  ★ 위 사항의 변경이 있거나 추가 사항이 있을 경우 반드시 개발 PL과 상의 후 수정 요망
 *     현재 총 100 컬럼임 되도록 100 컬럼 안으로 코딩을 하세요
 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 1234567890
 ********************************************************************************SMTOWN*CO*KR********/
 /************************
  *  공통 Script Include  *
  ************************/
   /*-----------------------------------+
    |  01.최초 화면 Load時 처리 할 사항  |
    +------------------------------------*/
   	$(document).ready(function(){
   	    var category_url = "/admin/categoryList"
   	    data={};
        ajaxJsonCallSync(category_url, data, listSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");     //카테고리 리스트 화면 출력
   	});

    /*----------------------+
    |  02. 카테고리 리스트 통신 성공시 처리  |
    +-----------------------*/
    function listSuccessCallBack(data){
       $("#category_list").html(data);     //카테고리 리스트 출력
    }
</script>
<div class="admin_page_div">
    <div class="category_list" id="category_list"></div>
</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="<%= request.getContextPath()%>"></c:set>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>Login</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script type="text/javascript" src="/resources/script/jquery-3.4.1.js"></script>
   	<script type="text/javascript" src="/resources/script/common.js"></script>
    <!-- bootstrap-4.3.1-dist -->
    <script type="text/javascript" src="/resources/bootstrap-4.3.1-dist/js/bootstrap.js"></script>
    <link rel="stylesheet" href="/resources/bootstrap-4.3.1-dist/css/bootstrap.css">
    <!-- swiper-4.5.0 -->
    <script type="text/javascript" src="/resources/swiper-4.5.0/dist/js/swiper.js"></script>
    <link rel="stylesheet" href="/resources/swiper-4.5.0/dist/css/swiper.css">
    <!-- core CSS -->
    <link rel="stylesheet" href="/resources/css/commons.css">
    <link rel="stylesheet" href="/resources/css/font.css">
    <!-- User Define CSS -->
    <link rel="stylesheet" href="/resources/css/user_define.css">
    <!-- Device Width CSS -->
    <link rel="stylesheet" href="/resources/css/device-width.css">
</head>
<body>
<script type="text/javascript">
/***************************************************************************************************
 * # Program: 	forgot.jsp 비밀번호 초기화 페이지 
 * 
 * # Modification Information
 * -------------------------------------------------------------------------------------------------
 *   Date			Modifier	Comment
 * -------------------------------------------------------------------------------------------------
 *   2019.06.17		최해림		Initial Created.
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

 });
 
 /*----------------------+
 |  02.생일 인증 처리 	 |
 +-----------------------*/
 function permit(){

		/* 
		필수 입력사항 처리
		 */
		var USER_NAME = $("#USER_NAME").val();
		var PHON_ENUM = $("#PHON_ENUM").val();

		/* 
		입력하지 않았을 때 처리
		 */
		if (USER_NAME == "" || PHON_ENUM == "") {
			alert('아이디 또는 생년원일을 입력하지 않으셨습니다.');
			$("#USER_NAME").focus();
			return false;
		}
		
		/* 
		전송할 데이터(ID, 생일)
		 */
		var data = {"user_name" : USER_NAME,
					"phon_enum" : PHON_ENUM};
		
		ajaxJsonCallSync("/forgotProc", data, birthSuccessCallBack);	//ajax sync 통신
	}
 

 /*----------------------+
 |  03. 생일인증 success call back |
 +-----------------------*/
 function birthSuccessCallBack(data){
	 if(data.isItPermit == 1){
		 alert("인증 완료");
		 $("#div_password").css("display","block");
		 $("#div_confirm").css("display","block");
		 $("#reset_btn").css("display","flex");
		 $("#permit_btn").css("display","none");
	 }else{
		 alert("아이디나 생년월일이 잘못되었습니다.");
	 }
 }
 
 /*----------------------+
 |  04. 비밀번호 재설정 처리 |
 +-----------------------*/
 function resetPassword(){
	 
		/* 
		필수 입력사항 처리
		 */
		var USER_NAME = $("#USER_NAME").val();
		var PASS_WORD = $("#PASS_WORD").val();
		var CONF_PSWD = $("#CONF_PSWD").val();

		/* 
		입력하지 않았을 때 처리
		 */
		if (USER_NAME == ""  || PASS_WORD == "" || CONF_PSWD == "") {
			alert('입력하지 않은 값이 있습니다.');
			$("#USER_NAME").focus();
			return false;
		}

		//비밀번호 같은지 확인
		if(PASS_WORD === CONF_PSWD){
			/* 
			전송할 데이터(ID, 비밀번호, 비밀번호 확인)
			 */
			var data = {
				"user_name" : USER_NAME,
				"pass_word" : PASS_WORD,
			};
			ajaxJsonCallSync("/resetPWProc", data, resetSuccessCallBack); //ajax sync 통신
		}else{
			alert("비밀번호가 일치하지 않습니다.");
		}
		
	}
 
 /*----------------------+
 |  05. 비밀번호 재설정 SuccessCallback |
 +-----------------------*/
 
 function resetSuccessCallBack(data){
	 alert("변경 완료되었습니다.");
	 location.href="/login";
 }
</script>
    <div class="wrap">
        <div class="login_box">
        	<div class="img_box">
        		<img src="${contextPath}/resources/images/SM_logo.png">
        	</div>
        	<div class="input_box">
                <input class="" type="text" id="USER_NAME" name="USER_NAME" placeholder="ID">
                <span class=""><i></i></span>
            </div>
            <div class="input_box">
                <input class="" type="text" id="PHON_ENUM" name="PHON_ENUM" placeholder="생년월일 여섯자리">
                <span class=""><i></i></span>
            </div>
            <!-- 비밀번호 변경 칸 -->
            <div id="div_password" class="input_box" style="display: none;">
                <input class="" type="password" id="PASS_WORD" name="PASS_WORD" placeholder="휸대폰번호">
            	<span class=""></span>
            </div>

            <!-- 비밀번호 확인 칸 -->
            <div id="div_confirm" class="input_box" style="display: none;">
            	<input class="" type="password" id="CONF_PSWD" name="CONF_PSWD" placeholder="비밀번호 확인 ">
            	<span class=""></span>
            </div>
            <!-- csrf : 웹 사이트의 취약점을 이용하여 이용자가 의도하지 않은 요청을 통한 공격  -->
            <!-- 해결책 : CSRF Token 정보를 Header 정보에 포함하여 서버 요청을 시도함 -->

            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

            <div class="btn">

                <a id="permit_btn" href="javascript:permit();" class="login100-form-btn" style="text-decoration: none;">인증</a>
                <a id="reset_btn" href="javascript:resetPassword();" class="login100-form-btn" style="text-decoration: none; display: none;">비밀번호 변경</a>

            </div>
        </div>
    </div>
</body>
</html>
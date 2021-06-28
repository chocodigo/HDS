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
 * # Program: 	Login.jsp 최초 로그인 페이지 생성
 *
 * # Modification Information
 * -------------------------------------------------------------------------------------------------
 *   Date			Modifier	Comment
 * -------------------------------------------------------------------------------------------------
 *   2019.05.21		최해림		Initial Created.
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

	 $('#loginButton').click(function(){
		login();
	 });

	 //iD 또는 패스워드가 다를 경우
	 <c:if test="${param.error != null}">
		loginFail();
	</c:if>


 });


 /*----------------------+
 |  02.로그인 이벤트 처리 	 |
 +-----------------------*/
 function login(){
	/*
		필수 입력사항 처리
	*/
	var USER_NAME = $("#USER_NAME").val();
    var PASS_WORD = $("#PASS_WORD").val();

    if(USER_NAME == "" || PASS_WORD == "" ){
        alert('아이디 또는 패스워드를 입력하지 않으셨습니다.');
        $("#USER_NAME").focus();
        return false;
    }
    isPwNull(USER_NAME);
}

 /*----------------------+
 |  02.로그인 이벤트 처리 	 |
 +-----------------------*/

 function loginFail(){
	 alert('아이디 또는 패스워드를 다시 확인하세요.');
	 return false;
 }


 /*----------------------+
 |  03. 로그인 페이지 Enter 처리 |
 +-----------------------*/
 function meEnter(){
	 var keyCode = event.keyCode;	//User, password input 박스에서 발생한 키 코드를 불러온다.

	 //Enter 키 처리시 로그인 이벤트 발생
	if(keyCode == 13){
		login();
	}

 }

 /*----------------------+
 |  04. 비밀번호 NULL 체크 |
 +-----------------------*/
 function isPwNull(USER_NAME){
    data={'user_name' : USER_NAME};
    ajaxJsonCallSync("/isPwNull", data, isPwNullSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");
 }

 /*----------------------+
 |  05. 비밀번호 NULL 체크 통신 성공 시 처리 |
 +-----------------------*/
 function isPwNullSuccessCallBack(data){
    if(data==1){
        alert("초기 로그인 시 비밀번호를 재설정하여야 합니다.");
        location.href="/forgot";
    }
    else{
        $('#login_form').submit();
    }
 }
</script>
    <div class="wrap">
    	<div class="login_box">
    		<div class="img_box">
    			<img src="${contextPath}/resources/images/SM_logo.png">
    		</div>
   			<c:url value="/login" var="loginUrl"/>
               <form id="login_form" action="${loginUrl}" method="post">
                   <div class="input_box">
                       <input class="" type="text" id="USER_NAME" name="USER_NAME" placeholder="ID" onkeyup="javascript:meEnter();">
                       <span class=""><i></i></span>
                   </div>
                   <div class="input_box">
                       <input class="" type="password" id="PASS_WORD" name="PASS_WORD" placeholder="Password" onkeyup="javascript:meEnter();">
                       <span class=""><i></i></span>
                   </div>
                   <div class="remember_box">
                       <div class="checkbox_box">
                            <input class="" id="remember_me" type="checkbox" name="remember-me">
                            <label class="" for="remember_me">저장하기</label>
                       </div>
                       <a class="pw_reset" href="/forgot">비밀번호 재설정</a>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value = "${_csrf.token}"/>
                </form>
    			<div class="btn">
    			    <a href="javascript:login();">로그인</a>
    			</div>
    			<div class="problem_info">
    				<img src="${contextPath}/resources/images/email.png">
					관리자이메일(<a style="color:blue;" href="mailto:cocoa1149@smtown.com">cocoa1149@smtown.com</a>)    					
    			</div>
    	</div>
    </div>
</body>
</html>
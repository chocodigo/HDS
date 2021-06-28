<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert Form</title>
</head>
<body>
<script type="text/javascript">
/***************************************************************************************************
 * # Program: 	BoardInsert.jsp 게시글 입력 페이지
 * 
 * # Modification Information
 * -------------------------------------------------------------------------------------------------
 *   Date			Modifier	Comment
 * -------------------------------------------------------------------------------------------------
 *	2019.05.21		최해림		Initial Created.
 *	2019.09.20		방재훈
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
	
  /*-----------------------------------+
  |  02. 작성 버튼 클릭 시 진행되는 함수|
  +------------------------------------*/
function insert(){
	var titl_name = $("#titl_name").val();	//글 제목
	var cont_ents = $("#cont_ents").val();	//글 내용
	var select_code =  $("#select_category option:selected").val();	//선택한 값의 code 가져오기
	 
	if(titl_name == ""){
		alert("제목을 입력하세요");
		$("#titl_name").focus();
	}else if(cont_ents ==""){
		alert("내용을 입력하세요");
		$("#cont_ents").focus();
	}else if (select_code =="cate_all"){
		 alert("카테고리를 선택하세요.");
	}else if(confirm("게시 글을 등록하시겠습니까?")){
		$("#insert_form").submit();
	}else{
	}
	
/* 	var params = new Object(); 		 //파라미터를 담을 Object 선언 
	var submitFlag = true;			 //validation Flag
	var objKeys = []; 				 //object key 이름이 담길 배열
	var thisId;						 //태그 속성 중 name으로 $("#name") 설정
	var tagName;					 //태그네임 변수

	//파라미터 세팅
	params = {
			titl_name : $("#titl_name").val(),
			select_category :  $("#select_category option:selected").val(),
			cont_ents : $("#cont_ents").val()
			}

	//파라미터 key 조회
	objKeys = Object.keys(params);

	//key와 input, select, textarea의 name 매칭으로 validation check
	$.each(objKeys, function(idx, obj){
				  
		thisId = $("#"+obj);
		tagName = $thisObj.prop("tagName");
		
		var cmt = "";
		
		//TagName에 따른 문구 분기처리 
		if($tagName == "INPUT" || $tagName == "TEXTAREA"){
			cmt = "을(를) 입력해주세요.";
		} else if($tagName == "SELECT"){
			cmt = "을(를) 선택해주세요.";
		}
		
		//해당 값 없을 시 alert, focus 처리 
		if($thisObj.val() == ""){
			alert($thisObj.prop("title") + cmt);
			$thisObj.focus();
			flag = false;
			return false;
		}
	});
	if(submitFlag)
	$("#insert_form").submit(); */
}
 /*-----------------------------------+
 |  03.셀렉트박스 카테고리 값 처리  |
 +------------------------------------*/
 function changePlaceholder(){
	 var select_code =  $("#select_category option:selected").val();	//선택한 값의 code 가져오기
	 $("#cate_gory").val(select_code);
 }
</script>

<sec:authentication var="principal" property="principal"/>

<div class="table_box">
    <form id="insert_form" action="/insertProc" method="post" enctype="multipart/form-data">
        <table>
            <colgroup>
                <col width="15%">
                <col width="85%" >
            </colgroup>

            <tbody>
                <tr>
                    <td>제목</td>
                    <td><input type="text" class="form-control" id="titl_name" name="titl_name" title="제목"></td>
                </tr>
                <tr>
                    <td>작성자</td>
                    <td>${real_name}</td>
                </tr>
                <tr>
                    <td>문제상황</td>
                    <td>
						<select id="select_category" style="border:0" onchange="javascript:changePlaceholder();" title="카테고리">
							<option value="cate_all">카테고리</option>
							<c:forEach var="item" items="${category_list }">
								<option value="${item.comm_code }">${item.code_name }</option>
							</c:forEach>
						</select>
                    </td>
                </tr>
                <tr>
                    <td>내용</td>
                    <td><textarea class="form-control" id="cont_ents" name="cont_ents" rows="10" title="내용"></textarea></td>
                </tr>
                <tr>
                    <td>첨부파일</td>
                    <td><input type="file" name="files "onchange="javascript:document.getElementById('files').value=this.value"></td>
                </tr>
                <input type="hidden" name="${_csrf.parameterName}" value = "${_csrf.token}"/>
                <input type="hidden" id="cate_gory"name="cate_gory"/>
            </tbody>
        </table>
    </form>
    <div class="btn_group">
        <button type="button" class="btn" onclick="insert();">등록</button>
        <button type="button" class="btn" onclick="back('list');">목록</button>
    </div>
    <div class="clear_fix"></div>
</div>

</body>
</html>
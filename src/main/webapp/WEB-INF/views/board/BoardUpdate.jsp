<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Board Update</title>
<meta charset="UTF-8">
<script type="text/javascript" src="/resources/script/common.js"></script>
</head>
<body>
<script type="text/javascript">
/***************************************************************************************************
 * # Program: 	BoardUpdate.jsp 게시글 수정 및 업데이트 페이지
 * 
 * # Modification Information
 * -------------------------------------------------------------------------------------------------
 *   Date			Modifier	Comment
 * -------------------------------------------------------------------------------------------------
 *   2019.05.21		최해림		Initial Created.
 *   2019.09.20.	방재훈
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
	 $("#form-update").submit(
			 function(){
				 
				 if($("#user_name").val() != $("#data_name").val()){
					 alert('작성자만 수정할 수 있습니다.');
					 return false;
				 }
				 else{
					 return true;
				 }
			 }
		);	
		
 });

 /*-----------------------------------+
 |  02. 수정 버튼 클릭 시 진행되는 함수|
 +------------------------------------*/
 function list_update(){
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
	  }else if(confirm("게시 글을 수정하시겠습니까?")){
		  
		  $("#update_form").submit();
	  }
	  else{
	  }
 }
/*-----------------------------------+
|  03.셀렉트박스 변경 시 카테고리 값 처리  |
+------------------------------------*/
function changePlaceholder(){
	 var select_code =  $("#select_category option:selected").val();	//선택한 값의 code 가져오기
	 $("#cate_gory").val(select_code);
}

/*-----------------------------------+
|  04.첨부파일 삭제  버튼 클릭시|
+------------------------------------*/
function fileDelete(){
	if(confirm("파일을 삭제하시겠습니까? 수정버튼을 클릭하지 않아도 파일이 삭제가 됩니다.")){
		var idxx_numb = '${detail.idxx_numb}';
		data={'idxx_numb' : idxx_numb};
		ajaxJsonCallSync("/fileDelete", data, deleteFileSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");	//삭제하기 위한 통신
	}else{
		
	}
	
}

/*-----------------------------------+
|  05.파일 삭제 성공 시 콜백 함수|
+------------------------------------*/
function deleteFileSuccessCallBack(data){
	$("#file_info").css("display","none");
}
</script>

<sec:authentication var="principal" property="principal"/>

<div class="table_box">
    <form id="update_form" action="/updateProc" method="post" enctype="multipart/form-data">
        <table>
            <colgroup>
                <col width="15%">
                <col width="85%" >
            </colgroup>

            <tbody>
                <tr>
                    <td>제목</td>
                    <td><input type="text" class="form-control" id="titl_name" name="titl_name" value="${detail.titl_name}"></td>
                </tr>
                <tr>
                    <td>작성자</td>
                    <td>${detail.crea_user}</td>
                </tr>
                <tr>
                    <td>문제상황</td>
                    <td>
						<select id="select_category" style="border:0" onchange="changePlaceholder()">
							<option value="cate_all">카테고리</option>
							<c:forEach var="item" items="${category_list }">
								<option value="${item.comm_code }" <c:if test="${item.comm_code eq detail.cate_gory}">selected</c:if>>${item.code_name }</option>
							</c:forEach>
						</select>
                    </td>
                </tr>
                <tr>
                    <td>내용</td>
                    <td><textarea class="form-control" id="cont_ents" name="cont_ents" rows="10">${detail.cont_ents}</textarea></td>
                </tr>
                    <c:if test="${files ne null}">
                        <tr id="file_info">
                            <td>첨부한 파일</th>
                            <td>
                            ${files.file_orig }  <a onclick="javascript:fileDelete();" style="cursor: pointer;">삭제</a>
                            </td>
                        </tr>
                    </c:if>
                <tr>
                    <td>첨부파일</td>
                    <td><input type="file" name="files "onchange="javascript:document.getElementById('files').value=this.value"></td>
                </tr>
                <input type="hidden" name="${_csrf.parameterName}" value = "${_csrf.token}"/>
                <input type="hidden" name="idxx_numb" value = "${detail.idxx_numb}"/>
                <input type="hidden" id="cate_gory"name="cate_gory"/>
            </tbody>
        </table>
    </form>
    <div class="btn_group">
        <button type="button" class="btn" onclick="list_update();">수정</button>
        <button type="button" class="btn" onclick="back('list');">목록</button>
    </div>
    <div class="clear_fix"></div>
</div>
</body>
</html>
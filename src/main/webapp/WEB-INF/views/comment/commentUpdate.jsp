<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript" src="/resources/script/common.js"></script>
</head>
<body>
<script type="text/javascript">
/***************************************************************************************************
 * # Program: 	commentUpdate.jsp  답글 수정 페이지
 *
 * # Modification Information
 * -------------------------------------------------------------------------------------------------
 *   Date			Modifier	Comment
 * -------------------------------------------------------------------------------------------------
 *   2019.06.20		최해림		Initial Created.
 *	 2019.09.20 	방재훈
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
 |  02. 수정 버튼 클릭 시 진행되는 함수|
 +------------------------------------*/
 function comment_update(){
	 var titl_name = $("#comm_titl").val();	//글 제목
	  var cont_ents = $("#comm_cont").val();	//글 내용

	  if(titl_name == ""){
		  alert("제목을 입력하세요");
		  $("#comm_titl").focus();
	  }else if(cont_ents ==""){
		  alert("내용을 입력하세요");
		  $("#comm_cont").focus();
	  }else if(confirm("게시 글을 수정하시겠습니까?")){

    	  $("#update_form").submit();
      }
      else{
      }
}
 
 /*-----------------------------------+
 |  03.첨부파일 삭제  버튼 클릭시|
 +------------------------------------*/
 function fileDelete(){
 	if(confirm("파일을 삭제하시겠습니까? 수정버튼을 클릭하지 않아도 파일이 삭제가 됩니다.")){
 		var comm_idxx = '${detail_comment.comm_idxx}';
 		data={'idxx_numb' : comm_idxx};
 		ajaxJsonCallSync("/fileDelete", data, deleteFileSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");	//파일삭제하기 위한 통신
 	}else{
 		
 	}
 }

 /*-----------------------------------+
 |  04.파일 삭제 성공 시 콜백 함수|
 +------------------------------------*/
 function deleteFileSuccessCallBack(data){
 	$("#file_info").css("display","none");
 }
</script>

<div class="table_box">
    <form id="update_form" action="/comment/updateProc" method="post" enctype="multipart/form-data">
        <table>
            <colgroup>
                <col width="15%">
                <col width="85%" >
            </colgroup>

            <tbody>
                <tr>
                    <td>제목</td>
                    <td><input type="text" class="form-control" id="comm_titl" name="comm_titl" value="${detail_comment.comm_titl}"></td>
                </tr>
                <tr>
                    <td>작성자</td>
                    <td>${detail_comment.crea_user}</td>
                </tr>
                <tr>
                    <td>내용</td>
                    <td><textarea class="form-control" id="comm_cont" name="comm_cont" rows="10">${detail_comment.comm_cont}</textarea></td>
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
                <input type="hidden" id="cate_gory"name="cate_gory"/>
                <input type="hidden" id="idxx_numb" name="idxx_numb"value="${detail_comment.idxx_numb}"/>
                <input type="hidden" id="comm_idxx" name="comm_idxx"value="${detail_comment.comm_idxx }"/>
            </tbody>
        </table>
    </form>
    <div class="btn_group">
        <button type="button" class="btn" onclick="comment_update();">수정</button>
        <button type="button" class="btn" onclick="back('list');">목록</button>
    </div>
    <div class="clear_fix"></div>
</div>
</body>
</html>
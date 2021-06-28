<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% pageContext.setAttribute("LF", "\n"); %>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta charset="UTF-8">
<script type="text/javascript" src="/resources/script/common.js"></script>
</head>
<script>
/***************************************************************************************************
 * # Program: 	commentDetail.jsp 답글 상세 페이지
 *
 * # Modification Information
 * -------------------------------------------------------------------------------------------------
 *   Date			Modifier	Comment
 * -------------------------------------------------------------------------------------------------
 *   2019.06.20		최해림		Initial Created.
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
 /*-----------------------------------+
 |  02.삭제버튼 클릭 시 처리 할 사항  |
 +------------------------------------*/
 function comment_delete(){
    if(confirm("게시 글을 삭제하시겠습니까?")){
	     data={'comm_idxx' : '${detail_comment.comm_idxx}'}
	    ajaxJsonCallSync("/comment/delete", data, commentDeleteSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");	//답글 삭제를 하기 위한 통신
    }else{
    }
 }
 /*-----------------------------------+
 |  03.수정 버튼 클릭 時 처리 할 사항  |
 +------------------------------------*/
 function comment_update(idxx_numb){
    data={'idxx_numb':idxx_numb};
  	ajaxJsonCallSync("/comment/update", data, commentSuccessCallBack,"${_csrf.headerName}", "${_csrf.token}");     //수정 페이지로 이동
 }
 /*-----------------------------------+
 |  04.답글 수정 클릭시 callback 함수  |
 +------------------------------------*/
 function commentSuccessCallBack(data){
	  $("#box").html(data);     //box에 필요한 페이지 출력
 }
 /*-----------------------------------+
 |  05.답글 삭제 성공시 callback 함수  |
 +------------------------------------*/
 function commentDeleteSuccessCallBack(data){
      location.href="/main?tab_menu=list";
 }
</script>
<body>
<div class="table_box">
    <div class="answer_div">
        <img src="${contextPath}/resources/images/answer.png">
        <h2>답변</h2>
    </div>
    <table>
        <colgroup>
            <col width="10%">
            <col width="40%" >
            <col width="10%" >
            <col width="20%">
        </colgroup>
        <tbody>
            <tr>
                <td>제목</td>
                <td>${detail_comment.comm_titl}</td>
                <td>작성자</td>
                <td>${detail_comment.crea_user}</td>
            </tr>
            <tr>
                <td>작성일</td>
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${detail_comment.comm_date}" /></td>
            </tr>
            <tr>
                <td>내용</td>
                <td colspan="3"><c:out value="${fn:replace(detail_comment.comm_cont,LF,'<br>')}" escapeXml="false"/></td>
            </tr>
            <tr>
                <td>첨부파일</td>
                <td colspan="3"><a href="/fileDown/${files.idxx_numb}">${files.file_orig}</a></td>
            </tr>
            <c:forTokens var="token" items="${files.file_orig }" delims="." varStatus="status">
				<c:if test="${status.last }">
					<c:choose>
						<c:when test="${token eq 'png' || token eq 'jpg' || token eq 'jpeg' || token eq 'gif' || token eq 'bmp'}">
							<tr class="detail_th">
								<td>첨부파일 이미지</td>
								<td colspan="3">
									<img class="file_img" src="/resources/upload/${files.file_name}">
								</td>
							</tr>
						</c:when>
					</c:choose>
				</c:if>
			</c:forTokens>
        </tbody>
    </table>
    <div class="btn_group">
        <sec:authorize access="hasAuthority('001')">
            <button type="button" class="btn" onclick="comment_update('${detail_comment.idxx_numb}');">수정</button>
            <button type="button" class="btn" onclick="comment_delete('${detail_comment.idxx_numb}');">삭제</button>
        </sec:authorize>
    </div>
    <div class="clear_fix"></div>
</div>
</body>
</html>
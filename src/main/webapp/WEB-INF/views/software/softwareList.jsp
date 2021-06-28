<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>faq List</title>
<script src="https://code.getmdl.io/1.3.0/material.min.js"></script>
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>
</head>
<body>
<script type="text/javascript">
/***************************************************************************************************
 * # Program: 	softwareList.jsp 자료실 목록 페이지
 *
 * # Modification Information
 * -------------------------------------------------------------------------------------------------
 *   Date			Modifier	Comment
 * -------------------------------------------------------------------------------------------------
 *   2019.07.23		최해림		Initial Created.
 *	 2019.09.20		방재훈
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
    |  02. 페이지 버튼 클릭시 처리할 사항|
    +------------------------------------*/
    function click_page(page){
        if(isItSearching == 0){
            data={ 'page' : page}
        }
        //검색 중일때
        else if(isItSearching ==1){
            var search_input = $("#search").val();
            var start_day = $("#start_day").val();
            var end_day = $("#end_day").val();

            data={ 'page' : page,
                   'search':search_input,
                   'start_day':start_day,
                   'end_day':end_day
                 }
        }
        ajaxJsonCallSync("/software_list", data, softwareSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");     //page 번호를 통해 리스트 통신
    }

    /*-----------------------------------+
    |  03. 게시글 클릭시 처리할 사항|
    +------------------------------------*/
    function software_detail(idxx_numb){
        data={'idxx_numb':idxx_numb};
        ajaxJsonCallSync("/software_detail", data, softwareSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");   //idxx_numb를 통해 게시글 상세내역 통신
    }

    /*-----------------------------------+
    |  04. 글쓰기 버튼 클릭시 처리할 사항|
    +------------------------------------*/
    function software_insert(){
        data={};
        ajaxJsonCallSync("/software_insert", data, softwareSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");   //글쓰기 게시판으로 이동
    }

    /*-----------------------------------+
    |  00. 통신 성공 시 처리할 사항|
    +------------------------------------*/
    function softwareSuccessCallBack(data){
        $("#box").html(data);     //box에 필요한 페이지 출력
    }
</script>
<div class="table_box">
    <table>
        <colgroup>
            <col width="10%">
            <col width="60%" >
            <col width="15%" >
            <col width="15%">
        </colgroup>
        <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>작성일</th>
            </tr>
        </thead>
        <tbody>
			<tr align="center">
				<c:choose>
				<c:when test="${list gt 0 }">
					<c:forEach var="item" items="${list}">
				     <td>${item.numb_keyx}</td>
				     <td class="text_c">
				         <a href='javascript: software_detail("${item.idxx_numb}")'>${item.titl_name}</a>
				     </td>
				     <td>${item.crea_user}</td>
				     <td>
				         <fmt:formatDate pattern="yyyy-MM-dd" value="${item.crea_date}" />
				     </td>
				    </c:forEach>
			    </c:when>
			    <c:otherwise>
			    	<td colspan="4">등록된 글이 없습니다.</td>
			    </c:otherwise>
			    </c:choose>
			</tr>
        </tbody>
    </table>

    <!-- 페이징 하단 -->
    <div class="paging_box">
        <ul class="">
            <!-- 이전 버튼 생성되면 시작 페이지의 전 페이지로 링크 걸기 -->
            <c:if test="${pageMaker.prev}">
                <li>
                    <a href='javascript: click_page(${pageMaker.startPage-1})' title="이전" class="prev">
                        <span><i class="material-icons">chevron_left</i></span>
                    </a>
                </li>
            </c:if>
            <!-- 시작 페이지부터 마지막 페이지 링크 걸기 -->
            <li>
                <c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx" varStatus="status">
                    <c:if test="${curpage == status.index}">
                        <a href='javascript: click_page(${idx})'><span class="active">${idx}</span></a>
                    </c:if>

                    <c:if test="${curpage != status.index}">
                        <a href='javascript: click_page(${idx})'><span>${idx}</span></a>
                    </c:if>
                </c:forEach>
            </li>
            <!-- 다음 버튼 생성되면  마지막 페이지의 다음 페이지로 링크 걸기 -->
            <c:if test="${pageMaker.next && pageMaker.endPage > 0 }">
                <li>
                    <a href='javascript: click_page(${pageMaker.endPage+1})' title="다음" class="next">
                        <span><i class="material-icons">chevron_right</i></span>
                    </a>
                </li>
            </c:if>
        </ul>
        <sec:authorize access="hasAuthority('001')">
            <div class="insert_btn">
                <button class="btn" onclick="software_insert();">글쓰기</button>
            </div>
        </sec:authorize>
    </div>

</div>
</body>
</html>
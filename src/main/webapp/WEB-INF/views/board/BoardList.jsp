<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Board List</title>
<script type="text/javascript" src="/resources/script/common.js"></script>
</head>
<body>
<script>
/***************************************************************************************************
 * # Program: 	BoardList.jsp 게시글 리스트 조회 페이지
 *
 * # Modification Information
 * -------------------------------------------------------------------------------------------------
 *   Date			Modifier	Comment
 * -------------------------------------------------------------------------------------------------
 *   2019.05.21		최해림		Initial Created.
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
	    var cate_gory = "${cate_gory}";     //parameter cate_gory
	    if(cate_gory != "00" && cate_gory != null){
	        $("#select_category option:eq(${cate_gory})").attr("selected","selected");
	    }
	});

 /*-----------------------------------+
 |  02.셀렉트박스 변경 시 게시판 변경 처리  |
 +------------------------------------*/
 function changeList(){
	 var select_code =  $("#select_category option:selected").val();	//선택한 값의 code 가져오기
	 data={'cate_gory':select_code};
	 ajaxJsonCallSync("/list", data, listSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");     //카테고리를 통해 리스트 통신
 }
  /*-----------------------------------+
  |  03.자동완성 통신 성공시 처리  |
  +------------------------------------*/
  function autoCompleteSuccessCallBack(data){
        $("#autoCompleteBox").html(data);
  }

  /*-----------------------------------+
  |  04. 페이지 버튼 클릭시 처리할 사항|
  +------------------------------------*/
  function click_page(page){
        var select_code =  $("#select_category option:selected").val();	//선택한 값의 code 가져오기
        var stat_flag = $("#stat_flag").val();
        //검색중이 아닐 때
        if(isItSearching == 0){
            data={ 'page' : page,
                    'cate_gory' : select_code,
                    'stat_flag' : stat_flag
                    }
        }
        //검색 중일때
        else if(isItSearching ==1){
            var search_input = $("#search").val();
            var start_day = $("#start_day").val();
            var end_day = $("#end_day").val();

            data={ 'page' : page,
                   'cate_gory' : select_code,
                   'stat_flag' : stat_flag,
                   'search':search_input,
                   'start_day':start_day,
                   'end_day':end_day
                  }
        }
        ajaxJsonCallSync("/list", data, listSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");     //page 번호를 통해 리스트 통신
  }

  /*-----------------------------------+
  |  05. 게시글 클릭시 처리할 사항|
  +------------------------------------*/
  function list_detail(idxx_numb){
      data={'idxx_numb':idxx_numb};
      ajaxJsonCallSync("/detail", data, listSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");   //idxx_numb를 통해 게시글 상세내역 통신
  }

    /*-----------------------------------+
    |  06. 글쓰기 버튼 클릭시 처리할 사항|
    +------------------------------------*/
    function list_insert(){
        data={};
        ajaxJsonCallSync("/insert", data, listSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");   //글쓰기 게시판으로 이동
    }
 /*-----------------------------------+
 |  00. 통신 성공 시 처리할 사항|
 +------------------------------------*/
 function listSuccessCallBack(data){
       $("#box").html(data);     //box에 필요한 페이지 출력
 }
</script>

<div class="table_box">
    <table>
        <colgroup>
            <col width="10%">
            <col width="15%">
            <col width="10%">
            <col width="35%">
            <col width="15%">
            <col width="15%">
        </colgroup>
        <thead>
            <tr>
                <th>번호</th>
               	<th style="padding:0;">
					<select id="select_category" onchange="changeList()">
						<option value="00">카테고리</option>
						<c:forEach var="item" items="${category_list }">
							<option value="${item.comm_code}">${item.code_name }</option>
						</c:forEach>
					</select>
                </th>
                <th>상태</th>
                <th>제목</th>
                <th>작성자</th>
                <th>작성일</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${list}">
                <tr align="center">
                    <td>${item.numb_keyx}</td>
                    <td class="text_c">${item.cate_name}</td>
                                        <td>
                        <c:choose>
                            <c:when test="${item.stat_flag eq '01' }">
                                신규
                            </c:when>
                            <c:when test="${item.stat_flag eq '02' }">
                                접수
                            </c:when>
                            <c:when test="${item.stat_flag eq '03' }">
                                진행
                            </c:when>
                            <c:when test="${item.stat_flag eq '04' }">
                                완료
                            </c:when>
                        </c:choose>
                    </td>
                    <td class="text_l">
                        <a href='javascript: list_detail("${item.idxx_numb}")'>${item.titl_name}</a>
                    </td>
                    <td>${item.crea_user}</td>
                    <td>
                        <fmt:formatDate pattern="yyyy-MM-dd" value="${item.crea_date}" />
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- 페이징 하단 -->
    <div class="paging_box" >
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
        <div class="insert_btn">
            <button class="btn" onclick="list_insert();">글쓰기</button>
        </div>
    </div>
</div>

</body>
</html>
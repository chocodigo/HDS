<%@ page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
</head>
<body>
<script type="text/javascript">
/***************************************************************************************************
 * # Program: 	categoryList.jsp 카테고리 관리 페이지
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

   	});
   /*-----------------------------------+
    |  02.카테고리 추가 버튼 클릭 時 처리 할 사항  |
    +------------------------------------*/
    function category_insert(){
        insert_url = "/admin/categoryInsert";
        data={};
        ajaxJsonCallSync(insert_url, data, insertSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");
    }

    /*----------------------+
    |  03. 카테고리 추가 버튼 통신 성공시 처리  |
    +-----------------------*/
    function insertSuccessCallBack(data){
       $("#insert_div").html(data);     //카테고리 리스트 출력
       $("#insert_div").css("display","block");
    }

    /*----------------------+
    |  04. 카테고리 제목 클릭 시 처리  |
    +-----------------------*/
    function category_detail(comm_code){
        detail_url = "/admin/categoryDetail";
        data={'comm_code':comm_code};
        ajaxJsonCallSync(detail_url, data, detailSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");
    }

    /*----------------------+
    |  05. 카테고리 제목 클릭 통신 성공시 처리  |
    +-----------------------*/
    function detailSuccessCallBack(data){
        $("#detail_div").html(data);
        $("#detail_div").css("display","block");
    }
</script>
    <div class="table_box">
        <div class="answer_div">
            <img src="${contextPath}/resources/images/category.png">
            <h2>카테고리 리스트</h2>
        </div>
        <table>
            <colgroup>
                <col width="10%">
                <col width="50%" >
                <col width="40%" >
            </colgroup>
            <thead>
                <tr>
                    <th>코드</th>
                    <th>카테고리 이름</th>
                    <th>담당자 메일</th>
               </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${list}">
                    <tr align="center">
                        <td>${item.comm_code}</td>
                        <td class="text_c">
                            <a href='javascript: category_detail("${item.comm_code}")'>${item.code_name}</a>
                        </td>
                        <td>${item.admi_mail}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div class="paging_box margin_t30">
            <div class="insert_btn">
                <button class="btn" onclick="category_insert();">카테고리 추가</button>
            </div>
        </div>
        <div id="detail_div" class="paging_box margin_t30"></div>
        <div id="insert_div" class="paging_box margin_t30"></div>
    </div>
</body>
</html>
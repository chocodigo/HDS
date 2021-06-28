<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<head>
<meta charset="UTF-8">
<title>faq insert</title>
</head>
<body>
<script type="text/javascript">
/***************************************************************************************************
 * # Program: 	softwareInsert.jsp 자료실 입력 페이지
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
	$(document).ready(function() {

	});
	/*-----------------------------------+
	|  02. 작성 버튼 클릭 시 진행되는 함수|
	+------------------------------------*/
	function insert() {
		var titl_name = $("#titl_name").val();
		var cont_ents = $("#cont_ents").val();

		if (titl_name == "") {
			alert("제목을 입력하세요");
			$("#titl_name").focus();
		} else if (cont_ents == "") {
			alert("내용을 입력하세요");
			$("#cont_ents").focus();
		} else if(confirm("게시 글을 등록하시겠습니까?")){
            $("#insert_form").submit();
        }else{}
	}

</script>

<div class="table_box">
    <form id="insert_form" action="/softwareInsertProc" method="post" enctype="multipart/form-data">
        <table>
            <colgroup>
                <col width="15%">
                <col width="85%" >
            </colgroup>

            <tbody>
                <tr>
                    <td>제목</td>
                    <td><input type="text" class="form-control" id="titl_name" name="titl_name"></td>
                </tr>
                <tr>
                    <td>작성자</td>
                    <td>${real_name}</td>
                </tr>
                <tr>
                    <td>내용</td>
                    <td><textarea class="form-control" id="cont_ents" name="cont_ents" rows="10"></textarea></td>
                </tr>
                <tr>
                    <td>첨부파일</td>
                    <td><input type="file" name="files "onchange="javascript:document.getElementById('files').value=this.value"></td>
                </tr>
                <input type="hidden" name="${_csrf.parameterName}" value = "${_csrf.token}"/>

            </tbody>
        </table>
    </form>
    <div class="btn_group">
        <button type="button" class="btn" onclick="insert();">등록</button>
        <button type="button" class="btn" onclick="back('software');">목록</button>
    </div>
    <div class="clear_fix"></div>
</div>

</body>
</html>
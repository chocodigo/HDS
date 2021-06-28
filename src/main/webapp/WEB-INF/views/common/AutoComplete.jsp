<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<meta charset="UTF-8">
<script type="text/javascript" src="/resources/vendor/jquery/jquery-1.9.1.min.js"></script>
</head>
<script>
/***************************************************************************************************
 * # Program: 	AutoComplete.jsp 자동완성 페이지를 위한 itemlist
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
		
 });
 
 
 /*-------------------------------------------------+
 |  자동완성 기능을 위한 List 조회 선택시 해당 자료 input 박스 입력 |
 +--------------------------------------------------*/
function autoComplete(inputText){
	$('#search-field').val(inputText); 
	$('#autoCompleteBox').empty();
}
</script>
<body>
<div id="search_table_div">
	<table id="search_table" class="mdl-data-table mdl-js-data-table mdl-shadow--2dp">
		<tbody>
			<c:forEach var="item" items="${list}">
				<tr align = "center">
					<td class="mdl-data-table__cell--non-numeric" onclick="autoComplete($(this).text());">${item}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
</body>
</html>
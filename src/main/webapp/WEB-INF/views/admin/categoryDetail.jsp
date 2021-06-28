<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

</head>
<body>
<script type="text/javascript">
/***************************************************************************************************
 * # Program: 	categoryDetail.jsp 카테고리 상세 내용페이지
 *
 * # Modification Information
 * -------------------------------------------------------------------------------------------------
 *   Date			Modifier	Comment
 * -------------------------------------------------------------------------------------------------
 *   2019.07.25		최해림		Initial Created.
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
  |  02.수정 버튼 클릭 時 처리 할 사항  |
  +------------------------------------*/
    function category_update(comm_code){
        update_url = "/admin/categoryUpdate";
        data= {'comm_code' : comm_code};
        ajaxJsonCallSync(update_url, data, updateSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");
    }

  /*-----------------------------------+
  |  03.수정 버튼 클릭 통신 성공 時 처리 할 사항  |
  +------------------------------------*/
    function updateSuccessCallBack(data){
        $("#detail_div").html(data);
    }

  /*-----------------------------------+
  |  04.삭제 버튼 클릭 時 처리 할 사항  |
  +------------------------------------*/
    function category_delete(comm_code){
        if(confirm("카테고리를 삭제하시겠습니까?")){
            delete_url = "/admin/categoryDelete";
            data = {'comm_code' : comm_code};
            ajaxJsonCallSync(delete_url, data, deleteSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");
        }else{}
    }
  /*-----------------------------------+
  |  05.삭제 버튼 클릭 통신 성공 時 처리 할 사항  |
  +------------------------------------*/
    function deleteSuccessCallBack(data){
        alert("삭제 완료");
        location.href="/admin/";
    }
 	</script>

 	<div class="table_box">
        <table>
            <colgroup>
                <col width="25%">
                <col width="25%">
                <col width="25%">
                <col width="25%">
            </colgroup>

            <tbody>
                <tr>
                    <td>코드</td>
                    <td>${detail.comm_code}</td>
                    <td>카테고리</td>
                    <td>${detail.code_name}</td>
                </tr>
                <tr>
                    <td>담당자 이메일</td>
                    <td>${detail.admi_mail}</td>
                    <td colspan="2"></td>
                </tr>
                <input type="hidden" name="${_csrf.parameterName}" value = "${_csrf.token}"/>
            </tbody>
        </table>
        <div class="btn_group">
            <button type="button" class="btn" onclick="category_update('${detail.comm_code}');">수정</button>
            <button type="button" class="btn" onclick="category_delete('${detail.comm_code}');">삭제</button>
        </div>
        <div class="clear_fix"></div>
 	</div>
</body>
</html>
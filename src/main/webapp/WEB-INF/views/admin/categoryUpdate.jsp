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

    var code_check = 0;     //코드 중복확인 0 : 중복되는 값 존재
                            //           1 : 중복되는 값 없음

   /*-----------------------------------+
    |  01.최초 화면 Load時 처리 할 사항  |
    +------------------------------------*/
   	$(document).ready(function(){
        $("#check_code_btn").click(function(){
            code_check = 1;
            alert("완료");
        });
   	});

   /*-----------------------------------+
    |  02.등록 버튼 클릭時 처리 할 사항  |
    +------------------------------------*/
    function update(){
        var comm_code = $("#comm_code").val();
        var code_name = $("#code_name").val();
        var admi_mail = $("#admi_mail").val();

        if(comm_code == "${detail.comm_code}"){         //수정하던 코드이면 중복체크 하지않음
            code_check = 1;
        }else{  check_code(); }                         //중복체크

        if(!$.isNumeric(comm_code)){
            alert("코드는 숫자만 입력해야 합니다.");
        }else if(comm_code.length != 2){
            alert("코드는 두 자리의 숫자로 입력해 주세요. 예) 01");
        }else if(comm_code =="" || code_name == "" || admi_mail == ""){
            alert("입력하지 않은 값이 있습니다.");
        }else if(code_check == 0){
            alert("중복된 코드가 있습니다.");
        }else{
            if(confirm("수정하시겠습니까?")){
                $("#detail_div").css("display","none");
                $("#update_form").submit();
                alert("수정 완료");
            }else{ }
        }
    }

   /*-----------------------------------+
    |  03.중복확인      |
    +------------------------------------*/
    function check_code(){
        var comm_code = $("#comm_code").val();

        var check_code_url = "/admin/checkCommCode";
        data = {'comm_code' : comm_code};
        ajaxJsonCallSync(check_code_url, data, checkCodeSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");
    }

   /*-----------------------------------+
    |  04.중복확인 통신 성공 時 처리 할 사항  |
    +------------------------------------*/
    function checkCodeSuccessCallBack(data){
        if(data == 1){
            code_check = 0;
        }else{
            code_check = 1;
        }
    }
</script>
<div class="table_box">
    <form id="update_form" action="/admin/categoryUpdateProc" method="post" enctype="multipart/form-data">
        <table>
            <colgroup>
                <col width="15%">
                <col width="20%">
                <col width="65%">
            </colgroup>

            <tbody>
                <tr>
                    <td>코드</td>
                    <td><input type="text" class="form-control" id="comm_code" name="comm_code" value="${detail.comm_code}" ></td>
                    <td>코드는 두 자리의 숫자로만 입력해주세요. 예) 01</td>
                </tr>
                <tr>
                    <td>카테고리 이름</td>
                    <td colspan="2"><input type="text" class="form-control" id="code_name" name="code_name" value="${detail.code_name}"></td>
                </tr>
                <tr>
                    <td>담당자 이메일</td>
                    <td colspan="2"><input type="text" class="form-control" id="admi_mail" name="admi_mail" value="${detail.admi_mail}"></td>
                </tr>
                <input type="hidden" name="${_csrf.parameterName}" value = "${_csrf.token}"/>
            </tbody>
        </table>
        <input type="hidden" id="orig_comm_code" name="orig_comm_code" value="${detail.comm_code}">
    </form>
    <div class="insert_btn">
        <button type="button" class="btn" onclick="update();">수정</button>
    </div>
    <div class="clear_fix"></div>
</div>

</body>
</html>
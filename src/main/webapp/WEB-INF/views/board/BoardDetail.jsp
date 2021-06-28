<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% pageContext.setAttribute("LF", "\n"); %>
<html>
<head>
<meta charset="UTF-8">
<title>Board Detail</title>
<script type="text/javascript" src="/resources/script/common.js"></script>

</head>
<body>

<script type="text/javascript">

/***************************************************************************************************
 * # Program: 	BoardDetail.jsp 게시글 상세 내용페이지
 *
 * # Modification Information
 * -------------------------------------------------------------------------------------------------
 *   Date			Modifier	Comment
 * -------------------------------------------------------------------------------------------------
 *	2019.05.21		최해림		Initial Created.
 *	2019.09.20		방재훈	
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
		console.log("####"+'${principal}');

 	 	
 		var idxx_numb = '${detail.idxx_numb}';			//게시글 번호
		var user_name = $("#user_name").val();			//로그인된 사용자 이름
 		var data_name = '${detail.user_name }';			//게시글 작성자 이름
 		var cate_gory = '${cate_gory }';				//게시글 카테고리



 		if("${hasReply}"==1){						//답글 갯수가 1이면 답글 정보 받아오기
 			$("#comment_div").css("display","block");	//답글 갯수가 1이면 답글 부분 보이게 하기
 			var comm_idxx = '${comm_idxx}';
 		    var data = {'idxx_numb' : idxx_numb,
 		    			'comm_idxx' : comm_idxx};
 		    ajaxJsonCallSync("/comment/detail", data, detailSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");	//답글을 받아오기 위한 통신
 		    }
 		else if("${hasReply}"==0){
 			$("#comment_div").css("display","none");	//답글 갯수가 0이면 답글 부분 보이지 않게 하기
 		}


 		//수정 버튼 클릭 시
 		$('#update_btn').click(
 			function(){
 				if(user_name != data_name){
 					alert("user_name : "+user_name);
 					alert("data_name : "+data_name);
 					alert('작성자만 수정할 수 있습니다.');
 				 }
 				else{
 					location.href="/update/"+idxx_numb;
 				}

 			}
 		);
 	});

 /*-----------------------------------+
 |  02. 상태 수정 아이콘 클릭 시 처리  |
 +------------------------------------*/
 	function check_stat(stat_flag,idxx_numb,titl_name,user_name){
	 	var stat_str="";
	 	switch(stat_flag){
	 	case '01':
	 		stat_str = "신규";
	 		break;
	 	case '02':
	 		stat_str = "접수";
	 		break;
	 	case '03':
	 		stat_str = "진행";
	 		break;
	 	case '04':
	 		stat_str = "완료";
	 		break;
	 	}
		if(confirm("상태를 "+stat_str+"(으)로 바꾸시겠습니까?")){
			update_stat(stat_flag,idxx_numb,titl_name,user_name);
		}else{

		}
	}
 /*-----------------------------------+
 |  03. 상태 변경을 위한 ajax통신  |
 +------------------------------------*/
 	function update_stat(stat_flag, idxx_numb, titl_name,user_name){

		data = {
			'idxx_numb' : idxx_numb,
			'stat_flag' : stat_flag,
			'titl_name' : titl_name,
			'user_name' : user_name
		};

		ajaxJsonCallSync("/updateStatProc", data, listReSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");  //상태변경을 위한 통신
 	}

/*-----------------------------------+
 |  04. 답글 통신 성공 시 화면에 추가  |
 +------------------------------------*/

    function detailSuccessCallBack(data){
        $("#comment_div").html(data);
    }

/*-----------------------------------+
 |  05. 삭제 버튼 클릭시 실행  |
 +------------------------------------*/
    function list_delete(){
        var user_name = $("#user_name").val();		//로그인된 사용자 이름
     	var data_name = '${detail.user_name }';			//게시글 작성자 이름

        if(confirm("게시 글을 삭제하시겠습니까?")){
			var idxx_numb = '${detail.idxx_numb}';			//게시글 번호
			data = {'idxx_numb' : idxx_numb}
			ajaxJsonCallSync("/delete", data, listReSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");	//삭제하기 위한 통신
        }
    }
/*-----------------------------------+
 |  06. 통신 성공 시 화면 새로고침 |
 +------------------------------------*/
    function listReSuccessCallBack(data){
        location.href="/main?tab_menu=list";
    }

 /*-----------------------------------+
  |  08.수정 버튼 클릭 時 처리 할 사항  |
  +------------------------------------*/
 	function list_update(idxx_numb){
 	    data={'idxx_numb':idxx_numb};
 	    ajaxJsonCallSync("/update", data, listSuccessCallBack,"${_csrf.headerName}", "${_csrf.token}");     //수정 페이지로 이동
 	}

/*-----------------------------------+
|  09. 통신 성공 시 처리할 사항|
+------------------------------------*/
    function listSuccessCallBack(data){
        $("#box").html(data);     //box에 필요한 페이지 출력
    }

/*-----------------------------------+
|  10. 답글 등록 클릭 시 처리할 사항|
+------------------------------------*/
    function comment_insert(idxx_numb){
        data={'idxx_numb':idxx_numb};
        ajaxJsonCallSync("/comment/insert", data, listSuccessCallBack,"${_csrf.headerName}", "${_csrf.token}");     //faq 수정 페이지로 이동
    }
</script>

<sec:authentication var="principal" property="principal"/>



<div class="table_box">
		<!-- 진행상황 수정 버튼 (권한이 001 admin인 경우만 출력)-->
		<div class="admin_btn">
			<sec:authorize access="hasAuthority('001')">
			    <!-- 답글등록 부분 -->
			    <c:if test="${hasReply == 0}">
				    <button class="btn" onclick="comment_insert('${idxx_numb}')">답글등록</button>
				</c:if>
				<button class="btn" data-toggle="modal" data-target="#progress_Modal">진행상황 수정</button>
				<!-- Modal -->
				<div class="modal fade" id="progress_Modal" role="dialog">
				    <div class="modal-dialog" >

				      <!-- Modal content-->
				      <div class="modal-content">
				        <div class="modal-header">
				          <h4 class="modal-title">수정할 진행상황을 선택해주세요.</h4>
				          <button type="button" class="close" data-dismiss="modal">&times;</button>
				        </div>
				        <div class="modal-body">

							<figure class="snip1141">
								<img id="stat_img" width="100" src="${contextPath}/resources/images/stat/001-new.png"
								onclick="check_stat('01','${detail.idxx_numb}','${detail.titl_name}','${detail.user_name}');">
								<figcaption>
									<h3>신규</h3>
								</figcaption>
							</figure>

							<figure class="snip1141">
								<img class="stat_img" width="100" src="${contextPath}/resources/images/stat/002-checkmark.png"
								onclick="check_stat('02','${detail.idxx_numb}','${detail.titl_name}','${detail.user_name}');">
								<figcaption>
									<h3>접수</h3>
								</figcaption>
							</figure>

							<figure class="snip1141">
								<img class="stat_img" width="100" src="${contextPath}/resources/images/stat/003-loading.png"
								onclick="check_stat('03','${detail.idxx_numb}','${detail.titl_name}','${detail.user_name}');">
								<figcaption>
									<h3>진행</h3>
								</figcaption>
							</figure>

	 						<figure class="snip1141">
								<img class="stat_img" width="100" src="${contextPath}/resources/images/stat/004-complete.png"
								onclick="check_stat('04','${detail.idxx_numb}','${detail.titl_name}','${detail.user_name}');">
								<figcaption>
									<h3>완료</h3>
								</figcaption>
							</figure>

						 </div>
				        </div>

				      </div>

				    </div>


			</sec:authorize>
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
                <td>${detail.titl_name}</td>
                <td>작성자</td>
                <td>${detail.crea_user}</td>
            </tr>
            <tr>
                <td>문제상황</td>
                <td>${detail.cate_name}</td>
                <td>작성일</td>
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${detail.crea_date}" /></td>

            </tr>
            <tr>
                <td>진행상태</td>
                <td>
                    <c:choose>
                        <c:when test="${detail.stat_flag eq '01'}">
                            신규
                        </c:when>
                        <c:when test="${detail.stat_flag eq '02'}">
                            접수
                        </c:when>
                        <c:when test="${detail.stat_flag eq '03'}">
                            진행
                        </c:when>
                        <c:when test="${detail.stat_flag eq '04'}">
                            완료
                        </c:when>
                    </c:choose>
                </td>
                <td>완료일</td>
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${detail.succ_date}" /></td>
            </tr>
            <tr>
                <td>내용</td>
                <td colspan="3"><c:out value="${fn:replace(detail.cont_ents,LF,'<br>')}" escapeXml="false"/></td>
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
									<img class="file_img" src="${pageContext.request.contextPath}/resources/img/${files.file_name}">
								</td>
							</tr>
						</c:when>
					</c:choose>
				</c:if>
			</c:forTokens>
        </tbody>
    </table>

    <!-- user_name : 로그인된 사용자 이름 -->
    <input type="hidden" id="user_name" value="${principal.username}"/>

    <div class="btn_group">
	    <sec:authorize access="hasAuthority('001')">
	    	<button type="button" class="btn" onclick="list_update('${detail.idxx_numb}');">수정</button>
            <button type="button" class="btn" onclick="list_delete('${detail.idxx_numb}');">삭제</button>
        </sec:authorize>
    	<sec:authorize access="hasAuthority('002')">
	        <c:if test="${principal.username eq detail.user_name }">
	            <button type="button" class="btn" onclick="list_update('${detail.idxx_numb}');">수정</button>
	            <button type="button" class="btn" onclick="list_delete('${detail.idxx_numb}');">삭제</button>
	        </c:if>
        </sec:authorize>
        <button type="button" class="btn" onclick="back('list');">목록</button>
    </div>
    <div class="clear_fix"></div>

    <div id="comment_div"></div>
</div>

</body>
</html>
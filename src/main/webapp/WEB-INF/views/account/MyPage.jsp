<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<%@ include file="../common/bootstrap.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My page</title>

</head>
<body>
<script>
	/***************************************************************************************************
     * # Program: 	MyPage.jsp 마이 페이지
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
		$(document).ready(function() {
			var progressbar = $('#progress_bar');
			max = progressbar.attr('max');

			if(max !=0){
				time = (1000 / 100) * 5;

				value = progressbar.val();


				var loading = function() {
					value += 1;
					addValue = progressbar.val(value);

					$('.progress-value').html(value + '%');
					var $ppc = $('.progress-pie-chart'),
					deg = 360 * value / 100;
					if (value > 50) {
					$ppc.addClass('gt-50');
					}

					$('.ppc-progress-fill').css('transform', 'rotate(' + deg + 'deg)');
					$('.ppc-percents span').html(value + '%');

					if (value == max) {
					clearInterval(animate);
					}
				};

				var animate = setInterval(function() {
				loading();
				}, time);
			}else{
				$('.ppc-progress-fill').css('transform', 'rotate(0deg)');
			}
		});

		 /*-----------------------------------+
         |  02.셀렉트박스 변경 시 게시판 변경 처리  |
         +------------------------------------*/
         function changeList(){
        	 var select_code =  $("#select_category option:selected").val();	//선택한 값의 code 가져오기

        	 switch(select_code){
        	 case 'cate_all':
        		 location.href="/mypage";
        		 break;
        	 default:
        		 location.href="/mypage?cate_gory="+select_code;
        		 break;
        	 }
         }

          /*-----------------------------------+
          |  03. 검색 버튼 클릭 시 진행 |
          +------------------------------------*/
          function search(){
         	 location.href="/mypage?search="+$("#search_box").val();		//검색 창에 있는 값으로 검색
          }

          /*----------------------+
          |  04. 검색 Enter 처리  	 |
          +-----------------------*/
          function meEnter(){
         	 var keyCode = event.keyCode;	//검색 input 박스에서 발생한 키 코드를 불러온다.

         	 //Enter 키 처리시 검색 이벤트 발생
         	if(keyCode == 13){
         		search();
         	}

          }
	</script>
	<!-- 최근 요청사항 -->
	<div class="mdl-color--white mdl-shadow--2dp mdl-cell mdl-cell--12-col mdl-grid" 
		style="padding-top: 40px; padding-left: 40px; margin-top:100px; margin-bottom: 40px; ">
		<div style="width: 100%">
		    <div style="margin-bottom: 30px; display: inline-block;">
            	<div class="mdl-card__title" style="height:60px;">
            		<img class="main-image" style="margin-top:0px;" src="${contextPath}/resources/images/target.png"/>
            		<h2 class="mdl-card__title-text" style="display: inline-block;">최근 요청사항</h2>
            	</div>
            	<div class="mdl-card__supporting-text" style="font-size : 15px; margin-left: 50px;">
            		<c:choose>
            		    <c:when test="${stat_flag eq '0' }">
            		  		최근 요청사항이 없습니다.
            		  	</c:when>
            		  	<c:otherwise>
            		  		<p>- 요청 내역 : ${detail.titl_name}</p>
            		  		<p>- 작성일 : <fmt:formatDate pattern="yyyy-MM-dd" value="${detail.crea_date}"/></p>

            		  	</c:otherwise>
            		</c:choose>
            	</div>

            </div>
			<div id="pbar" class="progress-pie-chart" data-percent="0" style="display: inline-block; float:right; margin-right: 40px;">
				<div class="ppc-progress">
					<div class="ppc-progress-fill"></div>
				</div>
				<div class="ppc-percents">
					<div class="pcc-percents-wrapper">
						<span>${stat_flag }%
		  				</span>
		  				<c:if test="${detail.stat_flag eq '0'}"></c:if>
		  				<c:if test="${detail.stat_flag eq '01'}"><p style="font-size: 15px; color: #E8C1DF;">신규작성</p></c:if>
		  				<c:if test="${detail.stat_flag eq '02'}"><p style="font-size: 15px; color: #E8C1DF;">담당자 접수</p></c:if>
		  				<c:if test="${detail.stat_flag eq '03'}"><p style="font-size: 15px; color: #E8C1DF;">진행중</p></c:if>
		  				<c:if test="${detail.stat_flag eq '04'}"><p style="font-size: 15px; color: #E8C1DF;">완료</p></c:if>
					</div>
					
				</div>
			</div>
							
			<progress style="display: none;" id="progress_bar" value="0" max="${stat_flag }"></progress>
		</div>
		

		<c:if test="${stat_flag ne '0' }">
			<div class="mdl-card__actions mdl-card--border" style="margin-top: 20px;">
				
				<a class="mdl-button mdl-button--colored mdl-js-button mdl-js-ripple-effect" style="float:right; text-decoration:none;" href="/detail/${detail.idxx_numb}">
					MORE
				</a>
				
			</div>
		</c:if>
	</div>
	
	<!-- 작성한 글 목록 -->
	<div class="mdl-color--white mdl-shadow--2dp mdl-cell mdl-cell--12-col mdl-grid">
		
		<div>
			<div class="mdl-card__title" style="height:60px; display: inline-block;">
			  	<img class="main-image" style="margin-top:-20px;" src="${contextPath}/resources/images/database.png"/>
			    <h2 class="mdl-card__title-text" style="display: inline-block;">작성한 글</h2>
		  	</div>
		  	<!-- 검색창 -->
			<div style="float: right;">
				<input id="search_box" type="text" placeholder="검색 값을 입력하세요." style="height: 60px; border: 0;font-size: 18px;" onkeyup="javascript:meEnter();">
			</div>
		  	<div class="mdl-card__supporting-text" style="width: 100%;">
		  		<!-- 게시글 목록 -->
				<table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp">
					<thead>
						<tr align = "center">
							<th class="mdl-data-table__cell--non-numeric" width="5%">순번</th>
							<th class="mdl-data-table__cell--non-numeric" width="5%">상태</th>
                            <th class="mdl-data-table__cell--non-numeric" width="10%">
                                <select id="select_category" style="border:0" onchange="changeList()">
                               		<option value="cate_all">카테고리</option>
                               		<c:forEach var="item" items="${category_list }">
                               			<option value="${item.cate_gory }">${item.cate_name }</option>
                               		</c:forEach>
                               	</select>
                            </th>
							<th class="mdl-data-table__cell--non-numeric" width="40%">제목</th>
							<th class="mdl-data-table__cell--non-numeric" width="10%">이름</th>
							<th class="mdl-data-table__cell--non-numeric" width="10%">입력일자</th>
							<th class="mdl-data-table__cell--non-numeric" width="10%">완료일자</th>

						</tr>
					</thead>
					<tbody>
						<c:forEach var="item" items="${list}">
							<tr align = "center">
								<td class="mdl-data-table__cell--non-numeric">${item.numb_keyx}</td>
								<c:choose>
                                	<c:when test="${item.stat_flag eq '01' }">
                                		<td class="mdl-data-table__cell--non-numeric">신규</td>
                                	</c:when>
                                	<c:when test="${item.stat_flag eq '02' }">
                                		<td class="mdl-data-table__cell--non-numeric">접수</td>
                                	</c:when>
                                	<c:when test="${item.stat_flag eq '03' }">
                                		<td class="mdl-data-table__cell--non-numeric">진행</td>
                                	</c:when>
                                	<c:when test="${item.stat_flag eq '04' }">
                                		<td class="mdl-data-table__cell--non-numeric">완료</td>
                                	</c:when>
                                </c:choose>
                                <td class="mdl-data-table__cell--non-numeric">
                                	${item.cate_name }
                                </td>
								<td class="mdl-data-table__cell--non-numeric"><a class="boardlist_title" style="color: #223055;" href='<c:url value="/detail/${item.idxx_numb}"/>'>${item.titl_name}</a></td>
								<td class="mdl-data-table__cell--non-numeric">${item.user_name}</td>
								<td class="mdl-data-table__cell--non-numeric"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${item.crea_date}"/></td>
								<td class="mdl-data-table__cell--non-numeric"><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${item.succ_date}"/></td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
				
				
				<!-- 페이징 하단 -->
				<div class="board_pagination">
					<ul class="pagination">
						<!-- 이전 버튼 생성되면 시작 페이지의 전 페이지로 링크 걸기 -->
						<c:if test="${pageMaker.prev}">
							<li class="page-item">
								<a class="page-link" aria-label="Previous" href='<c:url value="/mypage?page=${pageMaker.startPage-1}"/>'>
									<span aria-hidden="true">&laquo;</span>
									<span class="sr-only">Previous</span>
								</a>
							</li>
						</c:if>
						<!-- 시작 페이지부터 마지막 페이지 링크 걸기 -->
						<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx" varStatus="status">
							<c:if test="${curpage == status.index}">
								<li class="page-item active">	
									<a class="page-link" href='<c:url value="/list?page=${idx}"/>'><i class="fa">${idx}</i></a>
								</li>
							</c:if>
							
							<c:if test="${curpage != status.index}">
								<li class="page-item">
									<a href='<c:url value="/mypage?page=${idx}"/>'><i class="fa">${idx}</i></a>
								</li>
							</c:if>
						</c:forEach>
						<!-- 다음 버튼 생성되면  마지막 페이지의 다음 페이지로 링크 걸기 -->
						<c:if test="${pageMaker.next && pageMaker.endPage > 0 }">
							<li class="page-item">
								<a class="page-link" aria-label="Next" href='<c:url value="/mypage?page=${pageMaker.endPage+1}"/>'>
									<span aria-hidden="true">&raquo;</span>
				       				<span class="sr-only">Next</span>
								</a>
							</li>
						</c:if>
					</ul>
				</div>
		  	</div>
		  	
		</div>
		
	</div>
	<%@ include file="/WEB-INF/views/include/footer.jsp" %>   
</body>
</html>
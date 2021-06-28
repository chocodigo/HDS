<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<!DOCTYPE html >
<html style="height:969px;" xmlns:th="http://www.thymeleaf.org" xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
<title></title>
<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
<script type="text/javascript">
/***************************************************************************************************
 * # Program: 	Login.jsp 최초 로그인 페이지 생성
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
 
 
 var isItSearching = 0;      //검색 중인지 체크하는 값 초기 - 0
 /*-----------------------------------+
 |  01.최초 화면 Load時 처리 할 사항  |
 +------------------------------------*/
 $(document).ready(function(){

	getCookie();							//쿠키 상태를 통해 팝업 출력
	data={};

    //tab_menu 파라미터 통해 첫 화면 결정
    if("${tab_menu}"!=""){
        var page = "${tab_menu}";
        tab_menu(page);
    }else{
        ajaxJsonCallSync("/notice_list", data, listSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");     //첫화면 공지사항 리스트 출력
    }

    //검색어 입력 후 엔터할 경우 처리
	$("#search").keydown(function(key){
	    if(key.keyCode==13){
		    search();
		}
	});
	var end_day = new Date().toISOString().substring(0,10);
    var arr = end_day.split('-');
    var today = new Date(arr[0],arr[1],arr[2]);
    var start_month = today.getMonth() - 3;
    var start_day = new Date(arr[0],start_month,arr[2]).toISOString().substring(0,10);

    $("#end_day").val(end_day);
    $("#start_day").val(start_day);

	//날짜 입력칸 클릭했을 시 달력 팝업
	$("#start_day").datepicker();
	$("#end_day").datepicker();

	//이용방법 버튼 클릭 시
	$("#howto_btn").click(function(){
	     $("#howto_popup").modal('show');
	});

	//관리자 페이지 버튼 클릭 시
	$("#popup_admin_btn").on("click", function(){
        window.open("/admin/","관리자 페이지","width=400, height=300, left=300, top=150");
	});
 });
 
 /*-----------------------------------+
 |  02.일주일동안 열지 않음 처리  |
 +------------------------------------*/
 function week_close(){
	 
	 setCookie("todayCookie","done", 7);			//파라미터: 쿠키 이름, 7일 경과시 done, 7일동안 열지 않기
	 												//7일간 열지 않음 설정
	 //$("#howto_popup").css("display","none");		//팝업 사라짐
	 $("#closeBtn").click();	//팝업 사라짐
 }
 
 /*-----------------------------------+
 |  03. 7일간 열지 않음 설정 |
 +------------------------------------*/
 function setCookie(name, value, expiredays){	//파라미터: 쿠키 이름, 7일 경과시 done, 7일동안 열지 않기

	var today = new Date();						//오늘 날짜
 	today.setDate(today.getDate()+expiredays);	//팝업을 다시 띄울 날짜 설정
 	document.cookie = name + "=" + escape(value) + "; path:/; expires=" + today.toGMTString() + ";";	//todayCookie 설정
 	
 }	
 
 /*-----------------------------------+
 |  04. todayCookie를 통해 받아와서 팝업 유무 설정 |
 +------------------------------------*/
 function getCookie(){
	 var cookiedata = document.cookie;
	 if(cookiedata.indexOf("todayCookie=done")<0){	//todayCookie가 설정돼있지 않으면 팝업 출력
		 $("#howto_popup").modal('show');
	 }else{
		 $("#howto_popup").modal('hide');	//todayCookie가 설정돼있으면 팝업 사라짐
	 }
 }
 
 /*----------------------+
 |  05. 검색 Enter 처리  	 |
 +-----------------------*/
 function meEnter(){
	 var keyCode = event.keyCode;	//검색 input 박스에서 발생한 키 코드를 불러온다. 

	 //Enter 키 처리시 검색 이벤트 발생 
	if(keyCode == 13){
		search();
	}
	 
 }

 /*----------------------+
 |  06. 탭 통신 성공시 처리  |
 +-----------------------*/
 function listSuccessCallBack(data){

    $("#box").html(data);     //table_box에 리스트 출력
 }

 /*----------------------+
 |  07. 탭 클릭시 처리  |
 +-----------------------*/
 function tab_menu(menu){
    var url="";
    isItSearching = 0;      //검색중이 아님으로 변경
    $("#search").val('');
    switch(menu){
        case 'notice':     //공지사항 탭
            url="/notice_list"
            $('#tab_notice').addClass("active");
            $('#tab_faq').removeClass("active");
            $('#tab_list').removeClass("active");
            $('#tab_software').removeClass("active");
            break;
        case 'faq':     //FAQ 탭
            url="/faq_list"
            $('#tab_notice').removeClass("active");
            $('#tab_faq').addClass("active");
            $('#tab_list').removeClass("active");
            $('#tab_software').removeClass("active");
            break;
        case 'list':     //요청사항 탭
            url="/list"
            $('#tab_notice').removeClass("active");
            $('#tab_faq').removeClass("active");
            $('#tab_list').addClass("active");
            $('#tab_software').removeClass("active");
            $("#stat_flag").val('00');      //다시 클릭 시 진행상황 전체보기로 초기화
            break;
        case 'software':    //자료실 탭
            url="/software_list"
            $('#tab_notice').removeClass("active");
            $('#tab_faq').removeClass("active");
            $('#tab_list').removeClass("active");
            $('#tab_software').addClass("active");
            break;
    }

    data={};
    ajaxJsonCallSync(url, data, listSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");     //클릭한 리스트 화면 출력

 }

 /*----------------------+
 |  08. 상태 탭 클릭시 처리  |
 +-----------------------*/
 function tab_stat(stat){
    var stat_flag="";
    isItSearching = 0;      //검색중이 아님으로 변경
    $("#search").val('');

    $('#tab_notice').removeClass("active");
    $('#tab_faq').removeClass("active");
    $('#tab_list').addClass("active");
    $('#tab_software').removeClass("active");
    switch(stat){
        case 'new':
            stat_flag = '01';
            break;
        case 'reciept':
            stat_flag = '02';
            break;
        case 'progress':
            stat_flag = '03';
            break;
        case 'complete':
            stat_flag = '04';
            break;
    }
    $("#stat_flag").val(stat_flag);
    data={'stat_flag' : stat_flag};
    ajaxJsonCallSync("/findByStat", data, listSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");
 }

 /*----------------------+
 |  09. 검색 버튼 클릭시 처리  |
 +-----------------------*/
 function search(){
    isItSearching = 1;          //검색중으로 값 변경
    var select_code =  $("#select_menu option:selected").val();	    //선택한 값의 code 가져오기
    var search_input = $("#search").val();
    var start_day = $("#start_day").val();
    var end_day = $("#end_day").val();
    var data={'search':search_input,
              'start_day':start_day,
              'end_day':end_day};

    //시작일이나 마지막일중 하나만 입력돼있을 경우
    if((start_day != "" && end_day == "") || (start_day == "" && end_day !="")){
        alert("시작일이나 마지막일을 입력해주세요. ");
    }
    //마지막일이 시작일보다 앞선 경우
    else if(start_day > end_day){
        alert("마지막일은 시작일보다 빠를 수 없습니다.");
    }
    else{
        switch(select_code){
            case '공지사항':
                $('#tab_notice').addClass("active");
                $('#tab_faq').removeClass("active");
                $('#tab_list').removeClass("active");
                $('#tab_software').removeClass("active");
                ajaxJsonCallSync("/notice_list",data, listSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");
            break;
            case 'FAQ':
                $('#tab_notice').removeClass("active");
                $('#tab_faq').addClass("active");
                $('#tab_list').removeClass("active");
                $('#tab_software').removeClass("active");
                ajaxJsonCallSync("/faq_list",data, listSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");
            break;
            case '문의 게시판':
                $('#tab_notice').removeClass("active");
                $('#tab_faq').removeClass("active");
                $('#tab_list').addClass("active");
                $('#tab_software').removeClass("active");
                ajaxJsonCallSync("/list",data, listSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");
            break;
            case '자료실':
                $('#tab_notice').removeClass("active");
                $('#tab_faq').removeClass("active");
                $('#tab_list').removeClass("active");
                $('#tab_software').addClass("active");
                ajaxJsonCallSync("/software_list",data, listSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");
            break;
        }
    }
 }

/*-----------------------------------+
|  10. 뒤로가기 이미지 버튼 클릭 時 처리 할 사항  |
+------------------------------------*/
function back(menu){
    data={};

    switch(menu)
    {
    case 'notice':
        ajaxJsonCallSync("/notice_list", data, listSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");     //공지사항 목록으로 이동
    break;
    case 'faq':
        ajaxJsonCallSync("/faq_list", data, listSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");      //FAQ 목록으로 이동
    break;
    case 'list':
        ajaxJsonCallSync("/list", data, listSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");          //문의 게시판 목록으로 이동
    break;
    case 'software':
        ajaxJsonCallSync("/software_list", data, listSuccessCallBack, "${_csrf.headerName}", "${_csrf.token}");     //자료실 게시판으로 이동
    break;
    default:
        location.href="/main";      //첫 화면으로 이동
    break;

    }
}
</script>


<body>
<sec:authentication var="principal" property="principal"/>
<div id="myModal" class="modal" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Modal title</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <p>Modal body text goes here.</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary">Save changes</button>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
    <!-- 진행과정 안내 팝업 -->
    <div id="howto_popup" class="modal" tabindex="-1" role="dialog">
	    <div class="modal-dialog" role="document">
	      <div class="modal-content">
	        <div class="modal-header">
	          <h5 class="modal-title">장애 접수 처리 과정</h5>
	          <button id="closeBtn" type="button" class="close" data-dismiss="modal" aria-label="Close">
	            <span aria-hidden="true">&times;</span>
	          </button>
	        </div>
	        <div class="modal-body">
	          <%@ include file="/WEB-INF/views/main/howToUse.jsp" %>
	        </div>
	        <div class="modal-footer">
	          <a href="javascript: week_close();">[일주일 동안 열지 않음]</a>
	        </div>
	      </div>
	    </div>
    </div>
	<!--header-->
	<div class="wrap main_bg">
		<div class="head_box">

			<div class="bottom">
				<div class="datum_point">
					<h1><img src="${contextPath}/resources/images/SM_logo_white.png" onclick="back();" ></h1>
				</div>
			</div>
		</div>
		<div class="content_box">
    		<div class="datum_point">
				<div class="content1">
					<div class="left">
						<div class="user_info">
						    <sec:authorize access="hasAuthority('001')">
							    <i class="fas fa-key"></i>
							</sec:authorize>
						    <sec:authorize access="hasAuthority('002')">
							    <i class="far fa-user"></i>
							</sec:authorize>
							<ul>
							    <sec:authorize access="hasAuthority('001')">
							        <li>관리자</li>
							    </sec:authorize>
								<li>${accountDetail.comm_pany}</li>
								<li>${accountDetail.dept_ment}</li>
								<li>${accountDetail.real_name}<span>&nbsp;${accountDetail.posi_tion}</span></li>
							</ul>
							<form action="/logout" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							    <button class="btn">로그아웃</button>
							</form>
                            <button class="btn" id="howto_btn">이용방법</button>
                            <sec:authorize access="hasAuthority('001')">
                                <button class="btn" id="popup_admin_btn">카테고리 관리</button>
                            </sec:authorize>
						</div>
					</div>
					<div class="right">
					    <!--탭 메뉴-->
						<div class="tab">
							<ul>
								<li id="tab_notice" class="active" onclick="tab_menu('notice')"><a>공지사항</a></li>
								<li id="tab_faq" onclick="tab_menu('faq')"><a>FAQ</a></li>
								<li id="tab_list" onclick="tab_menu('list')"><a>문의 게시판</a></li>
								<li id="tab_software" onclick="tab_menu('software')"><a>자료실</a></li>
							</ul>
						</div>
						<!--dashboard-->
						<div class="count_box">
							<ul>
								<li>
								    <label id="new_list" onclick="tab_stat('new')">
									    <p>신규</p>
									    <i class="far fa-bell"></i>
									    <span>${statInfo.stat_neww}</span>
									</label>
								</li>
								<li>
								    <label id="reciept_list" onclick="tab_stat('reciept')">
									    <p>접수</p>
									    <i class="far fa-comment-dots"></i>
									    <span>${statInfo.stat_chek}</span>
									</label>
								</li>
								<li>
								    <label id="progress_list" onclick="tab_stat('progress')">
                                        <p>진행중</p>
                                        <i class="fas fa-hourglass-half"></i>
                                        <span>${statInfo.stat_load}</span>
                                    </label>
							    </li>
								<li>
								    <label id="complete_list" onclick="tab_stat('complete')">
                                        <p>요청완료</p>
                                        <i class="far fa-check-circle"></i>
                                        <span>${statInfo.stat_comp}</span>
									</label>
								</li>
							</ul>
						</div>
						<!--검색 부분-->
						<div class="search_box">
							<ul>
								<li class="day_box">
									<div class="input_box">
										<input id="start_day" type="text" readonly>
									</div>
									<span class="wave">~</span>
									<div class="input_box">
										<input id="end_day" type="text" readonly>
									</div>
								</li>
								<li class="class_box">
									<select id="select_menu">
										<option>공지사항</option>
										<option>FAQ</option>
										<option>문의 게시판</option>
										<option>자료실</option>
									</select>
								</li>
								<li class="input_box_search">
									<input id="search" type="text" placeholder="검색어를 입력해주세요">
									<a href="javascript:search();"><i class="fas fa-search"></i></a>
								</li>
							</ul>
					   </div>
					   <!--게시판 목록-->
					   <div id="box" class="box">
					   </div>
					</div>
				</div>
			</div>
	</div>
    <input id="stat_flag" type="hidden" value="00">
    <%@ include file="/WEB-INF/views/include/footer.jsp" %>
	</div>
</body>
</html>

</body>

</html>
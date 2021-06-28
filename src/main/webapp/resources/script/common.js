
/******************************************************************************************
 2019.05.24 최해림 (최초 개발)
본 스크립트는 HDS 전체서 사용될 공통 함수입니다.

* 만약 특정 함수의 Parameter나 Return Value 변경 시 기존 함수를 수정하시 마시고
새로운 함수를 만들어 사용하세요!

* 되도록 변경 없이 표준을 준수하세요~~!!!
꼬~~오~~옥 수정, 추가, 삭제가 필요하시면 연락바람(협의 후 반영 예정).
* 함수 중간에 새로운 함수 추가 하시 마세요
현재 함수 순서대로 공통함수 정의서가 작성되어 있습니다. 
추가 시 가장 마지막에 추가 합니다.
******************************************************************************************/

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//기능 Ajax 통신 ////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * ajax post Sync통신
 */
var ajaxJsonCallSync = function(url, param, successCallback, csrf_headerName, csrf_token, errorCallback, isProgress) {

    var data;
    var dataType;


    if (typeof param == "string") {

        data = param;
    } else if (typeof param == "object") {

        data = param;
    } else {
        
    }

    console.log( "data = "+ data);
    console.log( "url = "+ url);
    console.log( "csrf_headerName = "+ csrf_headerName);
    console.log( "csrf_token = "+ csrf_token);

    $.ajax({
        type : 'POST'
        ,url : url
        ,data : data
        ,async: false
        ,beforeSend : function(xhr){
            if(csrf_headerName != null && csrf_token != null)
			    xhr.setRequestHeader(csrf_headerName, csrf_token);
		}
        ,success : function(data) {
            successCallback(data);
        }
        ,error : function(xhr, status, error) {
            // console.log("xhr=" + JSON.stringify( xhr));
            // console.log("status=" + JSON.stringify( status));
            // console.log("error=" +  JSON.stringify(error));
            if (401 === xhr.status) {
                alert("1");
                //location.href = getContextPath() + "URL정의 필요.";
            } else {
                alert("error: " + error);
            }
        }
    });
};




//기능 Ajax 통신 ////////////////////////////////////////////////////////////////////////////////////////

/**
 * ajax post 통신
 */
var ajaxJsonCall = function(url, param, successCallback, errorCallback, isProgress) {
	var contentType;
	var data;
	var dataType;


	if (typeof param == "string") {
		contentType = "application/json;charset=UTF-8";
		data = param;
	} else if (typeof param == "object") {
		contentType = "application/json;charset=UTF-8";
		data = param;
	} else {

	}

	//console.log( "data = "+ data);


	$.ajax({
		type : 'POST'
			,url : url
			,contentType : contentType
			,data : data
			,async: false
			,beforeSend : function(xhr){
				xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
			}
	,success : function(data) {
		successCallback(data);
	}
	,error : function(xhr, status, error) {
//		console.log("xhr=" + JSON.stringify( xhr));
//		console.log("status=" + JSON.stringify( status));
//		console.log("error=" +  JSON.stringify(error));
		if (401 === xhr.status) {
			alert("1");
//			location.href = getContextPath() + "URL정의 필요.";
		} else {
			alert("error: " + error);
		}
	}
	});
};


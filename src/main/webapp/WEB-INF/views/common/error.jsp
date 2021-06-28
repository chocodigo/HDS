<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link rel="stylesheet" href="${contextPath}/resources/css/styles.css">
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error</title>
</head>
<body>
	<div class="error">
		<img class="error-img" src="${contextPath }/resources/images/warning.png">
		<br><br>
		error code : ${code }</span>
		<br>error msg : ${msg }</span>
		<br>timestamp : ${timestamp }</span>
		<br><br>
		관리자 (cocoa1149@smtown.com)에게 연락 바랍니다.
	</div>
</body>
</html>
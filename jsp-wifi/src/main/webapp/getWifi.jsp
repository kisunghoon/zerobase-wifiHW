<%@ page import="wifi.WifiService" %>
<%@ page import="wifi.WifiApi" %>
<%@ page import="wifi.UTIL_DATA" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<style>
	h2 {text-align : center;}
	.home  {text-align : center;}
</style>
</head>
<body>

<div>
<%
	WifiService service = new WifiService();
	WifiApi api = new WifiApi();
	int totalCount = service.listTotalCount();
	
	if(totalCount ==0){
		api.execute();
		totalCount = UTIL_DATA.listTotalCount;
	}
	
%>
	<h2><%=totalCount %> WIFI 정보를 정상적으로 저장하였습니다.</h2>
</div>

<div class ="home">
	<a href="index.jsp">홈 으로 가기</a>
</div>
</body>
</html>
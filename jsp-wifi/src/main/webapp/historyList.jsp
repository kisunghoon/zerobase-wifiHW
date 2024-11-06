<%@ page import ="java.util.List"%>
<%@page import="wifi.WifiInfo"%>
<%@page import="wifi.WifiService"%>
<%@page import="wifi.LocationHistory"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<style>
	table {
		width:100%;
		margin-left : auto;
		margin-right: auto;
		overflow-x:auto;
		border:solid 1px #fff;
		border-collapse : collapse;
	}
	th {
		border:solid 1px #fff;
		text-align: center;
		background-color:#00AB6F;
		color:white;
		border-collapse : collapse;
		height:30px;
	}
	td{
		border:solid 1px #e9ecef;
		text-align: center;
		border-collapse : collapse;
	}
	tr:nth-child(even)
	{
			background-color:#e9ecef;
	}
</style>
<script>
	function deleteHistory(id){
		window.location.href="deleteHistory.jsp?id="+id;
	}
</script>
</head>
<body>
	<h2>위치 히스토리 목록</h2>
	
	<div>
	<a href ="index.jsp">홈&nbsp;</a>|&nbsp;<a href="historyList.jsp">위치 히스토리 목록</a>|&nbsp;|&nbsp;<a href="getWifi.jsp">Open API 와이파이 정보 가져오기</a>
	
	<br/><br/>
	
	<table>
		<thead>
			<th>ID</th>
			<th>X좌표</th>
			<th>Y좌표</th>
			<th>조회일자</th>
			<th>비고</th>
		</thead>
		
		<tbody>
			<%
				WifiService service = new WifiService();
				List<LocationHistory> historyList = service.selectLocationHistory();
				
				for(LocationHistory history : historyList){
				
			%>
			
			<tr>
				<td><%=history.getID() %></td>
				<td><%=history.getX_coordinate() %></td>
				<td><%=history.getY_coordinate() %></td>
				<td><%=history.getViewDate() %></td>
				<td><button type="button" onclick="deleteHistory(<%=history.getID()  %>)"> 삭제 </button></td>
			</tr>
			
		<%
		}
						
				
		%>
		</tbody>
	</table>

</div>
</body>
</html>
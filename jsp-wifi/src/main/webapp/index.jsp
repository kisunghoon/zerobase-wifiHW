<%@ page import ="java.util.List"%>
<%@page import="wifi.WifiInfo"%>
<%@page import="wifi.WifiService"%>
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
		height:50px;
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
	function getMyLocation(){
		if(navigator.geolocation){
			navigator.geolocation.getCurrentPosition(
					pos => {
	                document.getElementById("LAT").value = pos.coords.latitude.toFixed(6);
	                document.getElementById("LNT").value = pos.coords.longitude.toFixed(6);
            }, 
            error => alert("error "+err.message)
          );
		}
    }
	
	function validateForm(){
		const lat = document.getElementById("LAT").value;
		const lnt = document.getElementById("LNT").value;
		
		if(!lat || !lnt){
			alert("위도, 경도 값을 다시 입력해 주세요.");
			return false;
		}
		return true;
	}
	
</script>
</head>
<body>

<h2>와이파이 정보 구하기</h2><br/>

<div>
	<a href ="index.jsp">홈&nbsp;</a>|&nbsp;<a href="historyList.jsp">위치 히스토리 목록</a>|&nbsp;|&nbsp;<a href="getWifi.jsp">Open API 와이파이 정보 가져오기</a>
	
	<br/><br/>
	<form method ="get" action="index.jsp" onsubmit="return validateForm()">
		<label for="LAT">LAT: </label>
		<input type="text" id="LAT" name="LAT"></input>,
		<label for="LNT">LNT:</label>
		<input type="text" id="LNT" name="LNT"></input>
		<button type="button" onclick="getMyLocation()"> 내 위치 가져오기 </button>
		<button type="submit"> 근처 WIPI 정보 보기</button><br/>
	</form>

</div>
<br/>
<table>
	<thead>
		<tr>
			<th>거리(KM)</th>
			<th>관리 번호</th>
			<th>자치구</th>
			<th>와이파이명</th>
			<th>도로명 주소</th>
			<th>상세 주소 </th>
			<th>설치 위치 (층)</th>
			<th>설치기관</th>
			<th>서비스 구분</th>
			<th>망종류</th>
			<th>설치년도</th>
			<th>실내외구분</th>
			<th>WIFI접속환경</th>
			<th>X좌표</th>
			<th>Y좌표</th>
			<th>작업일자</th>
		</tr>
	</thead>
	<tbody>
		<%
			String templat = request.getParameter("LAT");
			String templnt = request.getParameter("LNT");
			
			if(templat != null && templnt != null){
				double lat = Double.parseDouble(templat);
				double lnt = Double.parseDouble(templnt);
				
				WifiService service = new WifiService();
				List<WifiInfo> wifiList = service.nearWifiList(lat, lnt);
				
				for(WifiInfo wifi : wifiList){
			
		%>
	
                    <tr>
                        <td><%= wifi.getDistance() %></td>
                        <td><%= wifi.getManage_no() %></td>
                        <td><%= wifi.getAutonomous_district() %></td>
                        <td>
                        <a href="detail.jsp?manage_no=<%=wifi.getManage_no()%>&LAT=<%=wifi.getX_coordinate() %>&LNT=<%=wifi.getY_coordinate() %>">
                        <%= wifi.getWifi_name() %>
                        </td>
                        <td><%= wifi.getRoad_name_address() %></td>
                        <td><%= wifi.getDetail_address() %></td>
                        <td><%= wifi.getInstall_location() %></td>
                        <td><%= wifi.getInstall_agency() %></td>
                        <td><%= wifi.getService_category() %></td>
                        <td><%= wifi.getNet_type() %></td>
                        <td><%= wifi.getInstall_year() %></td>
                        <td><%= wifi.getInOutDoor() %></td>
                        <td><%= wifi.getWifi_conn() %></td>
                        <td><%= wifi.getX_coordinate() %></td>
                        <td><%= wifi.getY_coordinate() %></td>
                        <td><%= wifi.getWorkDate() %></td>
                    </tr>
                    
           <%
				}
			}
           %>
		
	</tbody>
	
</table>

</body>
</html>
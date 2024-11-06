<%@ page import ="java.util.List"%>
<%@ page import ="wifi.WifiService" %>
<%@ page import ="wifi.WifiInfo" %>
<%@page import ="bookmark.BookmarkInfo" %>
<%@page import ="bookmark.BookmarkService" %>
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
		height:40px;
		width:30%
	}
	td{
		border:solid 1px #e9ecef;
		text-align: center;
		border-collapse : collapse;
		width:70%
	}
	tr:nth-child(even)
	{
			background-color:#e9ecef;
	}
</style>
</head>
<body>
<h2>와이파이 정보 구하기</h2><br/>
    <%
    	String manage_no = request.getParameter("manage_no");
    	double distance;
    	double LAT = Double.parseDouble(request.getParameter("LAT"));
    	double LNT = Double.parseDouble(request.getParameter("LNT"));

    	WifiInfo wifi = new WifiInfo();
    	List<BookmarkInfo> bookmarkList = null;
    	
    	if(manage_no != null){
    		
    		WifiService service = new WifiService();
    		BookmarkService bookmarkService = new BookmarkService();
    		wifi = service.detailWifiInfo(manage_no);
    		bookmarkList = bookmarkService.selectBookmark();
    	}
    	
    	
    %>
<div>
	<a href ="index.jsp">홈&nbsp;</a>|&nbsp;<a href="historyList.jsp">위치 히스토리 목록</a>
	|&nbsp;|&nbsp;<a href="getWifi.jsp">Open API 와이파이 정보 가져오기</a>
	|&nbsp;<a href="bookmark-list.jsp">북마크 보기</a>|&nbsp;<a href="bookmark-group.jsp">북마크 그룹 관리</a>
	<br/><br/>
	<form method ="post" action="bookmark-list-add.jsp" onsubmit="#">
		<input type="hidden" name="manage_no" value="<%= wifi.getManage_no() %>">
		
		<select name="bookmark" id="bookmark">
	    <option value="" disabled selected>북마크 그룹 이름 선택</option>
		    <%
		        for (BookmarkInfo bookmark : bookmarkList) {
		    %>
		        <option value="<%= bookmark.getBookmark_id() %>"><%= bookmark.getBookmark_name() %></option>
		    <%
		        }
		    %>
		</select>
		<button type="submit"> 북마크 추가하기</button><br/>
	</form>

	<table>
		<thead>
			<tr>
				<th>거리(KM)</th>
				<td><%=wifi.getDistance() %>
			</tr>
			<tr>
				<th>관리번호</th>
				<td>
                        <%= wifi.getManage_no() %>
				</td>
			</tr>
			<tr>
				<th>자치구</th>
				<td><%=wifi.getAutonomous_district() %></td>
			</tr>
			<tr>
				<th>와이파이명</th>
				<td>
				<a href="index.jsp?manage_no=<%=wifi.getManage_no()%>&LAT=<%=wifi.getX_coordinate() %>&LNT=<%=wifi.getY_coordinate() %>">
				<%=wifi.getWifi_name() %></td>
			</tr>
			<tr>
				<th>도로명주소</th>
				<td><%=wifi.getRoad_name_address() %></td>
			</tr>
			<tr>
				<th>상세주소</th>
				<td><%=wifi.getDetail_address() %></td>
			</tr>
			<tr>
				<th>설치위치(층)</th>
				<td><%=wifi.getInstall_location() %></td>
			</tr>
			<tr>
				<th>설치유형</th>
				<td><%=wifi.getInstall_type() %></td>
			</tr>
			<tr>
				<th>설치기관</th>
				<td><%=wifi.getInstall_agency() %></td>
			</tr>
			<tr>
				<th>서비스구분</th>
				<td><%=wifi.getService_category() %></td>
			</tr>
			<tr>
				<th>망종류</th>
				<td><%=wifi.getNet_type() %></td>
			</tr>
			<tr>
				<th>설치년도</th>
				<td><%=wifi.getInstall_year() %></td>
			</tr>
			<tr>
				<th>실내외구분</th>
				<td><%=wifi.getInOutDoor() %></td>
			</tr>
			<tr>
				<th>WIFI접속환경</th>
				<td><%=wifi.getWifi_conn() %></td>
			</tr>			
			<tr>
				<th>X좌표</th>
				<td><%=wifi.getX_coordinate() %></td>
			</tr>
			<tr>
				<th>Y좌표</th>
				<td><%=wifi.getY_coordinate() %></td>
			</tr>
			<tr>
				<th>작업일자</th>
				<td><%=wifi.getWorkDate() %></td>
			</tr>						
		</thead>
	</table>

</div>
</body>
</html>
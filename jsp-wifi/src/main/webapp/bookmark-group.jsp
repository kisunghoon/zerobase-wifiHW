<%@ page import ="java.util.List"%>
<%@page import ="bookmark.BookmarkInfo" %>
<%@page import ="bookmark.BookmarkService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.Timestamp" %>

<%
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%>
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
</head>
<body>
<h2>북마크 그룹</h2><br/>
<div>
	<a href ="index.jsp">홈&nbsp;</a>|&nbsp;<a href="historyList.jsp">위치 히스토리 목록</a>
	|&nbsp;|&nbsp;<a href="getWifi.jsp">Open API 와이파이 정보 가져오기</a>
	|&nbsp;<a href="bookmark-list.jsp">북마크 보기</a>|&nbsp;<a href="bookmark-group.jsp">북마크 그룹 관리</a>
	<br/><br/>
	
		<form method ="get" action="bookmark-group-add.jsp" onsubmit="#">

		
		<button type="submit"> 북마크 그룹 이름 추가</button><br/>
	</form>
</div>
<br/>
<table>
	<thead>
		<tr>
			<th>ID</th>
			<th>북마크 이름</th>
			<th>순서</th>
			<th>등록일자</th>
			<th>수정일자</th>
			<th>비고</th>
		</tr>
	</thead>
	<tbody>
	

			<%
				BookmarkService service = new BookmarkService();
				List<BookmarkInfo> bookmarkList = service.selectBookmark();
				
				for(BookmarkInfo bookmark : bookmarkList){
				 
			%>
			  
			<tr> 
				<td><%=bookmark.getBookmark_id() %></td>
				<td><%=bookmark.getBookmark_name() %></td>
				<td><%=bookmark.getBookmark_order() %></td>
				<td><%=bookmark.getRegist_date() %></td>
				<td><%=bookmark.getUpdate_date() != null ? bookmark.getUpdate_date().toString() : "" %></td>
				<td><a href="bookmark-group-update.jsp?bookmark_id=<%=bookmark.getBookmark_id()%>">수정</a>&nbsp;
				<a href="deleteBookmark.jsp?bookmark_id=<%=bookmark.getBookmark_id()%>">삭제</a></td>
			</tr>
			
			<%
			}
				
			%>
			
	</tbody>
</body>
</html>
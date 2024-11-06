<%@ page import ="java.util.List"%>
<%@page import ="bookmark.BookmarkListInfo" %>
<%@page import ="bookmark.BookmarkService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>북마크 목록</title>
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

<h2>북마크 목록</h2><br/>

<div>
	<a href ="index.jsp">홈&nbsp;</a>|&nbsp;<a href="historyList.jsp">위치 히스토리 목록</a>
	|&nbsp;|&nbsp;<a href="getWifi.jsp">Open API 와이파이 정보 가져오기</a>
	|&nbsp;<a href="bookmark-list.jsp">북마크 보기</a>|&nbsp;<a href="bookmark-group.jsp">북마크 그룹 관리</a>
	<br/><br/>

</div>
<br/>
<table>
	<thead>
		<tr>
			<th>북마크 ID</th>
			<th>북마크 이름</th>
			<th>와이파이명</th>
			<th>등록일자</th>
			<th>비고</th>
		</tr>
	</thead>
	
	<tbody>
	<%
		BookmarkService service = new BookmarkService();
	
		List<BookmarkListInfo> bookmarkList = service.listBookmarkInfo();
		
		for(BookmarkListInfo bookmark : bookmarkList) {
	%>
	
	<tr>
		<td><%=bookmark.getId() %></td>
		<td><%=bookmark.getBookmark_name() %></td>
		<td><%=bookmark.getWifi_name() %></td>
		<td><%=bookmark.getRegist_date() %></td>
		<td><a href="detailBookmarkPage.jsp?manage_no=<%=bookmark.getManage_no() %>">삭제</a></td>
	</tr>
	
	<%
	
		}
		
	%>
	</tbody>
</body>
</html>
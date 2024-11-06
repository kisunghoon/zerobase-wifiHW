<%@ page import ="bookmark.BookmarkService" %>
<%@ page import ="bookmark.BookmarkInfo" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <meta charset="UTF-8">
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
		text-align: left;
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
		<%
			BookmarkInfo bookmark= new BookmarkInfo();
			String bookmark_id = request.getParameter("bookmark_id");
			
			int id = Integer.parseInt(bookmark_id);
			System.out.println("bookmark_id "+bookmark_id);
			
			BookmarkService service = new BookmarkService();
			
			bookmark = service.selectBookmark(id);
			
		%>
		
<h2>북마크 그룹 수정</h2><br/>

	<a href ="index.jsp">홈&nbsp;</a>|&nbsp;<a href="historyList.jsp">위치 히스토리 목록</a>
	|&nbsp;|&nbsp;<a href="getWifi.jsp">Open API 와이파이 정보 가져오기</a>
	|&nbsp;<a href="#">북마크 보기</a>|&nbsp;<a href="bookmark-group.jsp">북마크그룹 관리</a>
	<br/><br/>
	<form method ="post" action="updateBookmark.jsp" onsubmit="#">
	<input type="hidden" id="bookmark_id" name="bookmark_id" value="<%= bookmark.getBookmark_id() %>">
		<table>
			<thead>
				<tr>
					<th>북마크 이름</th>
					<td><input type="text" id="bookmark_name" name="bookmark_name" value="<%=bookmark.getBookmark_name() %>" required></input></td>
				</tr>
				<tr>
					<th>순서</th>
					<td>
						<input type="text" id="order" name="order" value="<%=bookmark.getBookmark_order() %>"  required></input>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:center">
						<a href="bookmark-group.jsp">돌아가기</a>
						<button type="submit">수정</button>
					</td>
				</tr>
			</thead>
		</table>
	</form>
</body>
</html>
<%@page import="bookmark.BookmarkInfo"%>
<%@page import="bookmark.BookmarkService"%>
<%@page import="bookmark.BookmarkListInfo"%>
<%@ page import ="java.util.List"%>
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
<script>
	function deletedDetailBookmark(manage_no){
		window.location.href="deletedDetailBookmark.jsp?manage_no="+manage_no;
	}
</script>
</head>
<body>
<h2>북마크 삭제</h2><br/>
<%
	String manage_no = request.getParameter("manage_no");
	BookmarkService service = new BookmarkService();
	
	BookmarkListInfo bookmark = new BookmarkListInfo();
	bookmark = service.selectByDeletedBookmarkInfo(manage_no);
	


%>

	<a href ="index.jsp">홈&nbsp;</a>|&nbsp;<a href="historyList.jsp">위치 히스토리 목록</a>
	|&nbsp;|&nbsp;<a href="getWifi.jsp">Open API 와이파이 정보 가져오기</a>
	|&nbsp;<a href="bookmark-list.jsp">북마크 보기</a>|&nbsp;<a href="bookmark-group.jsp">북마크 그룹 관리</a>
	<br/><br/>
	<div>북마크를 삭제하시겠습니까?</div><br/>
	
	
	<table>
		<thead>
			<tr>
				<th>북마크 이름</th>
				<td><%=bookmark.getBookmark_name() %></td>
			</tr>
			<tr>
				<th>와이파이명</th>
				<td><%=bookmark.getWifi_name() %></td>
			</tr>
			<tr>
				<th>등록일자</th>
				<td><%=bookmark.getRegist_date() %></td>
			</tr>
			<tr>
					<td colspan="2" style="text-align:center">
						<a href="bookmark-list.jsp">돌아가기</a><b>&nbsp;|&nbsp;</b>
						<button type="button" onclick="deletedDetailBookmark('<%= bookmark.getManage_no() %>')">삭제</button>
					</td>
			</tr>
		</thead>
	</table>
</body>
</html>
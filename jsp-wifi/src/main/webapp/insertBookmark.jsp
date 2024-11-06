<%@ page import ="bookmark.BookmarkService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>북마크 추가 처리</title>
</head>
<script>
    alert("북마크가 성공적으로 추가되었습니다!");
    window.location.href = "bookmark-group.jsp";
</script>
<body>
<%
	request.setCharacterEncoding("UTF-8");
	String bookmarkName = request.getParameter("bookmark_name");
	String order = request.getParameter("order");
	int intOrder = Integer.parseInt(order);

	
	BookmarkService service = new BookmarkService();
	
	service.insertBookmark(bookmarkName, intOrder);
%>

</body>
</html>
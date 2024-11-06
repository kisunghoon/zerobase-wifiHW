<%@ page import ="bookmark.BookmarkService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>북마크 삭제 처리</title>
</head>
<script>
    alert("북마크가 성공적으로 삭제되었습니다!");
    window.location.href = "bookmark-list.jsp";
</script>
<body>
<%
	String manage_no = request.getParameter("manage_no");
	System.out.println("deletedBookmark "+manage_no);
	if(manage_no != null){
		BookmarkService service = new BookmarkService();
		
		service.deleteBookmarkInfo(manage_no);
	}
%>
</body>
</html>
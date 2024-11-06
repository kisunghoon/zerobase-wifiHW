<%@page import="bookmark.BookmarkService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>북마크 추가 처리</title>
</head>
<script>
    alert("북마크 정보가 성공적으로 추가되었습니다!");
    window.location.href = "bookmark-list.jsp";
</script>
<body>

    
    <%
    	request.setCharacterEncoding("UTF-8");
    	String manage_no = request.getParameter("manage_no");
    	String bookmark_id = request.getParameter("bookmark");
    	
    	
    	if(bookmark_id != null){
    		int id = Integer.parseInt(bookmark_id);
    		
    		BookmarkService service = new BookmarkService();
    		
    		service.upsertBookmarkWifi(manage_no, id);
    	}
    
    %>
    
</body>
</html>
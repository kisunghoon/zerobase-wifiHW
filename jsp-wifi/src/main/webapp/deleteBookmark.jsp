<%@ page import ="bookmark.BookmarkService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
    	String id = request.getParameter("bookmark_id");
    	
    	if(id != null){
    		int bookmark_id = Integer.parseInt(id);
    		
    		BookmarkService service = new BookmarkService();
    		service.deleteBookmark(bookmark_id);
    		
    	}
    	
    %>
<script>
    alert("북마크가 성공적으로 삭제되었습니다.");
    window.location.href = "bookmark-group.jsp";
</script>
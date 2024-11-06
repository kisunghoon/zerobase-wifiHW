<%@ page import ="wifi.WifiService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%
    	String id = request.getParameter("id");
    	
    	if(id != null){
    		int history_id = Integer.parseInt(id);
    		
    		WifiService service = new WifiService();
    		service.deleteLocationHistory(history_id);
    		
    	}
    	
    	response.sendRedirect("historyList.jsp");
    %>
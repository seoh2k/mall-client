<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="mall.client.vo.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>clientOne</title>
</head>
<body>
	<!-- mainMenu -->
	<jsp:include page="/WEB-INF/view/inc/mainMenu.jsp"></jsp:include>
	
	<h1>clientOne</h1>
	<%
		// 형변환
		Client clientOne = (Client)request.getAttribute("clientOne");
	%>
		<table border="1">
			<tr>
				<th>clientNo</th>
				<td><%=clientOne.getClientNo() %></td>
			</tr>
			<tr>
				<th>clientMail</th>
				<td><%=clientOne.getClientMail() %></td>
			</tr>
			<tr>
				<th>clientDate</th>
				<td><%=clientOne.getClientDate() %></td>
			</tr>
		</table>
</body>
</html>
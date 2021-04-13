<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- mainMenu -->
	<jsp:include page="/WEB-INF/view/inc/mainMenu.jsp"></jsp:include>
	
	<!-- cartList -->
	<h1>장바구니</h1>
	
	<!-- request 객체에 담아둔 list 가져오기 -->
	<%
		List<Map<String, Object>> cartList = (List<Map<String, Object>>)(request.getAttribute("cartList"));
	%>
	<table border="1">
		<thead>
			<tr>
				<th>cartNo</th>
				<th>ebookNo</th>
				<th>ebookTitle</th>
				<th>cartDate</th>
			</tr>
		</thead>
		<tbody>
		<%
			for(Map<String, Object> map : cartList){
				int cartNo = (int)map.get("cartNo");
				String ebookTitle = (String)map.get("ebookTitle");
				int ebookNo = (int)map.get("ebookNo");
				String cartDate = (String)map.get("cartDate");
		%>
				<tr>
					<td><%=cartNo %></td>
					<td><%=ebookNo %></td>
					<td><%=ebookTitle %></td>
					<td><%=cartDate.substring(0,11) %></td>
				</tr>
		<%
				}
		%>
		</tbody>
	</table>
</body>
</html>
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
	
	<h1>ebookOne</h1>
	<%
		// 형변환
		Ebook ebook = (Ebook)request.getAttribute("ebook");
	%>
		<table border="1">
			<tr>
				<th>Title</th>
            	<td><%=ebook.getEbookTitle() %></td>
			</tr>
			<tr>
				<th>ISBN</th>
            	<td><%=ebook.getEbookISBN() %></td>
			</tr>
			<tr>
				<th>Name</th>
				<td><%=ebook.getCategoryName() %></td>
			</tr>
			<tr>
				<th>Author</th>
				<td><%=ebook.getEbookAuthor() %></td>
			</tr>
			<tr>
				<th>Company</th>
            	<td><%=ebook.getEbookCompany() %></td>
			</tr>
			<tr>
				<th>Page Count</th>
				<td><%=ebook.getEbookPageCount() %></td>
			</tr>
			<tr>
				<th>Price</th>
				<td><%=ebook.getEbookPrice() %></td>
			</tr>
			<tr>
				<th>Summary</th>
				<td><%=ebook.getEbookSummary() %></td>
			</tr>
			<tr>
				<th>Image</th>
				<td><img src="<%=request.getContextPath()%>/img/<%=ebook.getEbookImg()%>" width="240" height="300"></td>
			</tr>
			<tr>
				<th>Date</th>
				<td><%=ebook.getEbookDate().substring(0,11)%></td>
			</tr>
			<tr>
				<th>State</th>
				<td><%=ebook.getEbookState() %></td>
			</tr>
		</table>
		
		<!-- InsertCartController?ebookNo - CartDao.insertCart() - redirect: CartListController -->
		<a href="<%=request.getContextPath()%>/InsertCartController?ebookNo=<%=ebook.getEbookNo()%>">
			<% 
				if(session.getAttribute("loginClient") == null 
					|| ebook.getEbookState().equals("품절")
					|| ebook.getEbookState().equals("절판")
					|| ebook.getEbookState().equals("구편절판")){
			%>
					<button type="submit" disabled="disabled">장바구니 추가</button>
			<%
				} else {
			%>
					<button type="submit">장바구니 추가</button>
			<%
				}
			%>
		</a>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<table border="1">
			<tr>
				<th>Title</th>
            	<td>${ebook.ebookTitle}</td>
			</tr>
			<tr>
				<th>ISBN</th>
				<td>${ebook.ebookISBN}</td>
			</tr>
			<tr>
				<th>Name</th>
				<td>${ebook.categoryName}</td>
			</tr>
			<tr>
				<th>Author</th>
				<td>${ebook.ebookAuthor}</td>
			</tr>
			<tr>
				<th>Company</th>
				<td>${ebook.ebookCompany}</td>
			</tr>
			<tr>
				<th>Page Count</th>
				<td>${ebook.ebookPageCount}</td>
			</tr>
			<tr>
				<th>Price</th>
				<td>${ebook.ebookPrice}</td>
			</tr>
			<tr>
				<th>Summary</th>
				<td>${ebook.ebookSummary}</td>
			</tr>
			<tr>
				<th>Image</th>
				<td><img src="${pageContext.request.contextPath}/img/${ebook.ebookImg}" width="240" height="300"></td>
			</tr>
			<tr>
				<th>Date</th>
				<td>${ebook.ebookDate}</td>
			</tr>
			<tr>
				<th>State</th>
				<td>${ebook.ebookState}</td>
			</tr>
		</table>
		
		<!-- InsertCartController?ebookNo - CartDao.insertCart() - redirect: CartListController -->
		<a href="${pageContext.request.contextPath}/InsertCartController?ebookNo=${ebook.ebookNo}">
			<c:if test="${loginClient == null || ebook.ebookState.equals('품절') || ebook.ebookState.equals('절판') || ebook.ebookState.equals('구편절판')}">
				<button type="submit" disabled="disabled">장바구니 추가</button>
			</c:if>
			<c:if test="${loginClient != null || ebook.ebookState.equals('판매중')}">
				<button type="submit">장바구니 추가</button>
			</c:if>
		</a>
</body>
</html>
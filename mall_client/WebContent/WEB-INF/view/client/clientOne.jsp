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
	
	<h1>clientOne</h1>
		<table border="1">
			<tr>
				<th>clientNo</th>
				<td>${client.clientNo}</td>
			</tr>
			<tr>
				<th>clientMail</th>
				<td>${client.clienMail}</td>
			</tr>
			<tr>
				<th>clientDate</th>
				<td>${client.clientDate}</td>
			</tr>
		</table>
		
		<!-- UpdateClientPwController.doGet() - updateClientPw.jsp -->
		<!-- UpdateClientPwController.doPost() - ClientDao.updateClientPw() - session.invalidate() - redirect: /IndexController -->
		<a href="${pageContext.request.contextPath}/UpdateClientPwController"><button>비밀번호 수정</button></a>
		<!-- DeleteClientController - ClientDao.deleteCartByClient(mail), ClientDao.deleteClient() - session.invalidate() - redirect: /IndexController -->
		<a href="${pageContext.request.contextPath}/DeleteClientController"><button>회원탈퇴</button></a>
</body>
</html>
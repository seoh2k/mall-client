<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>insertClient</title>
</head>
<body>
	<h1>회원가입</h1>
	<form method="post" action="${pageContext.request.contextPath}/InsertClientController"> <!-- 포스트방식으로 넘어간다 -->
		<table border="1">
			<tr>
				<th>clientMail</th>
				<td><input type="text" name="clientMail"></td>
			</tr>
			<tr>
				<th>clientPw</th>
				<td><input type="password" name="clientPw"></td>
			</tr>
		</table>
		<button type="submit">회원가입</button>
	</form>
</body>
</html>
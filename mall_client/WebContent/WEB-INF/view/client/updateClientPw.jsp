<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>updateClientPw</title>
</head>
<body>
	<h1>updateClientPw</h1>
	<form method="post" action="<%=request.getContextPath()%>/UpdateClientPwController" method="post">
		<table border="1">
			<tr>
				<th>새 비밀번호</th>
				<td><input type="password" name="clientPw"></td>
			</tr>
		</table>
		<button type="submit">비밀번호 수정</button>
	</form>
</body>
</html>
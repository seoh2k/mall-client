<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "mall.client.vo.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="/WEB-INF/view/inc/mainMenu.jsp"></jsp:include>
	<!-- 메뉴1 로그인/회원가입/내정보 -->
	<!-- 장바구니(세션) -->
	<!-- 캘린더(이번달에 나온 책들) -->
	<!-- 베스트셀러(주문량) -->
	<!-- 메뉴2 카테고리 -->
	<h1>index</h1> <!-- jsp 말고 서블릿을 실행해야한다 -->
	<%
		List<Ebook> ebookList = (List<Ebook>)(request.getAttribute("ebookList"));
	%>
	<table border="1">
		<tr>
		<%
			int i = 0;
			for(Ebook ebook : ebookList){
				i += 1;
		%>
				<td>
					<div><img src="<%=request.getContextPath() %>/img/default.jpg"></div>
					<!-- ebookOneController - EbookDao.selectEbookOne() - ebookOne.jsp -->
					<div>
						<a href="<%=request.getContextPath() %>/ebookOneController?ebookNo=<%=ebook.getEbookNo()%>"><%=ebook.getEbookTitle() %></a>
					</div>
					<div>￦<%=ebook.getEbookPrice() %></div>
				</td>
		<%	
				if(i%5==0){
		%>
					</tr><tr>
		<%		
				}
			}
		%>
		</tr>
	</table>
</body>
</html>
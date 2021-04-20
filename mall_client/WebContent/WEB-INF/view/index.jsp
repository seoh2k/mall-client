<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	
	<!-- 카테고리별 목록을 볼 수 있는 메뉴(네비게이션) -->
	<ul>
			<li><a href = "${pageContext.request.contextPath}/IndexController">전체보기</a></li>
		<c:forEach var="c" items="${categoryList}">
			<li><a href = "${pageContext.request.contextPath}/IndexController?categoryName=${c.categoryName}">${c.categoryName}</a></li>	
		</c:forEach>
	</ul>
		
	<!-- best ebook 상품 5개 출력 -->
	<h3>Best Ebook</h3>
	<table border="1">
		<tr>
			<c:forEach var="m" items="${bestOrdersList}">
				<td>
					<div><img src="${pageContext.request.contextPath}/img/default.jpg"></div>
					<!-- EbookOneController - EbookDao.selectEbookOne() - ebookOne.jsp -->
					<div>
						<a href="${pageContext.request.contextPath}/EbookOneController?ebookNo=${m.ebookNo}">
							${m.ebookTitle}
						</a>
					</div>

					<div>￦${m.ebookPrice}</div>
				</td>
			</c:forEach>
		</tr>
	</table>
	
	<!-- ebook 상품 출력 -->
	<table border="1">
		<tr>
			<c:set var="count" value="0"/>
			<c:forEach var="ebook" items="${ebookList}">
				<c:set var="count" value="${count + 1}"/>
					<td>
						<div><img src="${pageContext.request.contextPath}/img/default.jpg"></div>
						<!-- ebookOneController - EbookDao.selectEbookOne() - ebookOne.jsp -->
						<div>
							<a href="${pageContext.request.contextPath}/EbookOneController?ebookNo=${ebook.ebookNo}">${ebook.ebookTitle}</a>
						</div>
						<div>￦${ebook.ebookPrice}</div>
					</td>
				<c:if test="${count % 5 == 0}">
					</tr><tr>
				</c:if>
			</c:forEach>
		</tr>
	</table>
	
	<!-- 페이징 -->
	<c:if test="${currentPage != 1}">
		<a href="${pageContext.request.contextPath}/IndexController?currentPage=${currentPage-1}&rowPerPage=${rowPerPage}&categoryName=${categoryName}&searchWord=${searchWord}"><button type="button">Previous</button></a>
	</c:if>

	<c:if test="${currentPage < (totalCount/rowPerPage) + 1}">
		<a href="${pageContext.request.contextPath}/IndexController?currentPage=${currentPage+1}&rowPerPage=${rowPerPage}&categoryName=${categoryName}&searchWord=${searchWord}"><button type="button">Next</button></a>
	</c:if>
	<!-- 페이징 끝 -->
	
	<!-- 이북 검색 -->
	<form action="${pageContext.request.contextPath}/IndexController" method="get">
		EbookTitle : 
		<input type="text" name="searchWord">
		<button type="submit">검색</button>
	</form>
</body>
</html>
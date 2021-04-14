package mall.client.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mall.client.model.CartDao;
import mall.client.vo.*;

@WebServlet("/CartListController")
public class CartListController extends HttpServlet {
	private CartDao cartDao;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 아니면 redirect
		HttpSession session = request.getSession();
		if(session.getAttribute("loginClient") == null) { //로그인 정보가 없으면 IndexController로 이동
			response.sendRedirect(request.getContextPath()+"/IndexController");
			return;
		}
		
		// 인코딩 설정
		request.setCharacterEncoding("utf-8");
		
		// 세션에 있는 로그인 정보(clientMail)을 가져온다.
		// 메소드 에서 사용하는 매개변수를 넘겨주기 위해 필요한 값을 가져온다.
		Client client = new Client();
		client = (Client)session.getAttribute("loginClient");
		String clientMail = client.getClientMail();
		
		// dao 호출
		this.cartDao = new CartDao();
		List<Map<String, Object>> cartList = this.cartDao.selectCartList(clientMail);
		
		// forword
		request.setAttribute("cartList", cartList);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/cart/cartList.jsp");
		rd.forward(request, response);
	}

}

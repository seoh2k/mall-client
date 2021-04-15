package mall.client.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mall.client.model.CartDao;
import mall.client.vo.*;

@WebServlet("/DeleteCartController")
public class DeleteCartController extends HttpServlet {
	private CartDao cartDao; 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 비회원 못 들어오게 막기
		HttpSession session = request.getSession();
		if(session.getAttribute("loginClient") == null) {
			response.sendRedirect(request.getContextPath()+"/IndexController");
			return;
		}
		
		// 전처리
		int ebookNo = Integer.parseInt(request.getParameter("ebookNo"));
		Cart cart = new Cart();
		cart.setEbookNo(ebookNo);
		cart.setClientMail(((Client)session.getAttribute("loginClient")).getClientMail());
		
		// dao 호출
		this.cartDao = new CartDao();
		this.cartDao.deleteCart(cart);
		
		// redirect
		response.sendRedirect(request.getContextPath()+"/CartListController");
	}

}

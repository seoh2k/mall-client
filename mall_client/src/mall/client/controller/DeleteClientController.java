package mall.client.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mall.client.model.CartDao;
import mall.client.model.ClientDao;
import mall.client.vo.Cart;
import mall.client.vo.Client;

@WebServlet("/DeleteClientController")
public class DeleteClientController extends HttpServlet {
	private ClientDao clientDao;
	private CartDao cartDao;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginClient") == null) {
			response.sendRedirect(request.getContextPath()+"/IndexController");
			return;
		}
		// session에서 회원 메일 받음
		String clientMail = ((Client)session.getAttribute("loginClient")).getClientMail();
		
		// 회원 카트 정보 삭제
		this.cartDao = new CartDao();
		this.cartDao.deleteCartByClient(clientMail);
		
		this.clientDao = new ClientDao();
		this.clientDao.deleteClient(clientMail);
		
		session.invalidate();
		response.sendRedirect(request.getContextPath()+"/IndexController");
	}

}

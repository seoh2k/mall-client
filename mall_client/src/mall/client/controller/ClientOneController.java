package mall.client.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mall.client.model.ClientDao;
import mall.client.vo.Client;

@WebServlet("/ClientOneController")
public class ClientOneController extends HttpServlet {
	private ClientDao clientDao;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 유효성 검사, redirect
		HttpSession s = request.getSession();
		if(s.getAttribute("loginClient") == null) {
				response.sendRedirect(request.getContextPath()+"/IndexController");
				return;
		}

		// Dao 호출
		this.clientDao = new ClientDao();
		Client clientOne = clientDao.selectClientOne(((Client)(s.getAttribute("loginClient"))).getClientMail());

		// View forward
		request.setAttribute("clientOne", clientOne);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/client/clientOne.jsp");
		rd.forward(request, response);
	}
}

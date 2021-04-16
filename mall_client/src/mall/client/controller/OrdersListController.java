package mall.client.controller;

import java.io.IOException;

import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import mall.client.model.OrdersDao;
import mall.client.vo.Client;


@WebServlet("/OrdersListController")
public class OrdersListController extends HttpServlet {
	private OrdersDao ordersDao;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 유효성 검사, redirect
		HttpSession session = request.getSession();
		if(session.getAttribute("loginClient") == null) {
				response.sendRedirect(request.getContextPath()+"/IndexController");
				return;
		}

		this.ordersDao = new OrdersDao();

		Client client = (Client)session.getAttribute("loginClient");
		List<Map<String, Object>> ordersList = this.ordersDao.selectOrderListByClient(client.getClientNo()); 
		request.setAttribute("ordersList", ordersList);

		request.getRequestDispatcher("/WEB-INF/view/orders/ordersList.jsp").forward(request, response);

	}
}
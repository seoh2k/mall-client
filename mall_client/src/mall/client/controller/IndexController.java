package mall.client.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// C -> V (모두 컨트롤러를 지나간다) // Controller -> View | IndexController에서 요청하면 Index.jsp를 보여준다.
@WebServlet("/IndexController")
public class IndexController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/index.jsp"); // WEB-INF안에 있어서 jsp로 바로 보여줄 수 없다
		rd.forward(request, response);
	}

}

package mall.client.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mall.client.model.EbookDao;
import mall.client.vo.Ebook;

@WebServlet("/ebookOneController")
public class ebookOneController extends HttpServlet {
	private EbookDao ebookDao;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ebookNo =Integer.parseInt(request.getParameter("ebookNo"));
		this.ebookDao = new EbookDao();
		Ebook ebook = this.ebookDao.selectEbookOne(ebookNo);
		  
		request.setAttribute("ebook", ebook);
		request.getRequestDispatcher("/WEB-INF/view/ebook/ebookOne.jsp").forward(request, response);

	}

}

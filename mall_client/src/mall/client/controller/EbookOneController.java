package mall.client.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mall.client.model.EbookDao;
import mall.client.vo.Ebook;

@WebServlet("/EbookOneController")
public class EbookOneController extends HttpServlet {
	private EbookDao ebookDao;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get으로 받은 정보 받기 정보가 없다면 IndexController로
		if(request.getParameter("ebookNo") == null) {
			response.sendRedirect(request.getContextPath()+"/IndexController");
			return;
		}
		int ebookNo = Integer.parseInt(request.getParameter("ebookNo"));
		//받은정보 디버깅
		System.out.println(ebookNo + "<-------------EbookOneController 에서 ebookNo");
		
		//dao객체 생성 후 책정보를 담을 ebook 객체에 책정보 담기
		this.ebookDao = new EbookDao();
		Ebook ebook = this.ebookDao.selectEbookOne(ebookNo);
		
		//request객체에 ebook정보 저장
		request.setAttribute("ebook", ebook);
		//ebookOne으로 포워딩
		request.getRequestDispatcher("/WEB-INF/view/ebook/ebookOne.jsp").forward(request, response);

	}

}

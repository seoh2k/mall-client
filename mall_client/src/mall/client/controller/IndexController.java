package mall.client.controller;

import java.io.IOException;

import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mall.client.model.*;
import mall.client.vo.*;

// C -> M -> V (모두 컨트롤러를 지나간다) // Controller -> View | IndexController에서 요청하면 Index.jsp를 보여준다.
@WebServlet("/IndexController")
public class IndexController extends HttpServlet {
	private EbookDao ebookDao;
	private CategoryDao categoryDao;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request 분석
		String searchWord = "";
		if(request.getParameter("searchWord") != null) {
			searchWord = request.getParameter("searchWord");
		}
		
		int currentPage = 1;
		if(request.getParameter("currentPage")!=null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		int rowPerPage = 15;
		int beginRow = (currentPage-1)*rowPerPage;
		
		int totalRow = 0;
		
		String categoryName = null;
		if(request.getParameter("categoryName") != null) {
			categoryName = request.getParameter("categoryName");
			totalRow = this.ebookDao.totalRowOfCategory(categoryName);
			System.out.println("totalRow: "+totalRow);
		} else {
			totalRow = this.ebookDao.totalRow();
			System.out.println("totalRow: "+totalRow);
		}
		
		int lastPage = totalRow/rowPerPage;
		if(totalRow % rowPerPage != 0) {
			searchWord = request.getParameter("searchWord");
		}
		
		// model 호출
		this.ebookDao = new EbookDao();
		List<Ebook> ebookList = new ArrayList<>();
		if(categoryName != null) {
			ebookList = this.ebookDao.ebookListByCategory(beginRow, rowPerPage, searchWord, categoryName);
		} else {
			ebookList = this.ebookDao.selectEbookListByPage(beginRow, rowPerPage, searchWord);
		}
		
		this.categoryDao = new CategoryDao();
		List<Category> categoryList = this.categoryDao.categoryList();
		
		// View forward
		request.setAttribute("ebookList", ebookList);
		request.setAttribute("categoryList", categoryList);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/index.jsp"); // WEB-INF안에 있어서 jsp로 바로 보여줄 수 없다
		rd.forward(request, response);
	}

}

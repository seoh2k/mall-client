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
	private OrdersDao ordersDao;
	private StatsDao statsDao;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("/IndexController 시작");
		
		// 페이징
		int currentPage = 1;
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		int rowPerPage = 15;
		int beginRow = (currentPage-1)*rowPerPage;

		// 카테고리 선택
		String categoryName = null;
		if(request.getParameter("categoryName") != null ) {
			categoryName = request.getParameter("categoryName");
			
			if(request.getParameter("categoryName").equals("null") || request.getParameter("categoryName").equals("")) { // 문자열 null이 넘어올 경우 진짜 null로 변경
				categoryName = null;
			}
		}
		
		// 검색
		String searchWord = null;
		if(request.getParameter("searchWord") != null) {
			searchWord = request.getParameter("searchWord");
			
			if(request.getParameter("searchWord").equals("null")) { // 문자열 null이 넘어올 경우 진짜 null로 변경
				searchWord = null;
			}
		}

		// BestEbookList
		this.ordersDao = new OrdersDao();
		List<Map<String, Object>> bestOrdersList = this.ordersDao.selectBestOrdersList();
		
		// categoryList
		this.categoryDao = new CategoryDao();
		List<Category> categoryList = this.categoryDao.selectCategoryList();
		
		// EbookList
		this.ebookDao = new EbookDao();
		List<Ebook> ebookList = this.ebookDao.selectEbookListByPage(beginRow, rowPerPage, categoryName, searchWord);
		
		// EbookList totalCount
		int totalCount = this.ebookDao.totalCount(categoryName, searchWord); // EbookDao 객체 생성 후 선언해줘야 한다.
		System.out.println(totalCount +"<---IndexController에서 totalRow");
			
		// 접속자 관련 데이터
		this.statsDao = new StatsDao();
		long total = this.statsDao.selectStatsTotal();
		Stats stats = this.statsDao.selectStatsByToday();
		long statsCount = 0;
		if(stats != null) {
			statsCount = stats.getStatsCount();
		}
		
		// request객체에 리스트 저장 후 View forward
		request.setAttribute("total", total);
		request.setAttribute("statsCount", statsCount);
		request.setAttribute("bestOrdersList", bestOrdersList);
		request.setAttribute("searchWord", searchWord);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("rowPerPage", rowPerPage);
		request.setAttribute("totalCount",totalCount);
		request.setAttribute("categoryName", categoryName);
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("ebookList", ebookList);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/index.jsp");
		rd.forward(request, response);
	}

}

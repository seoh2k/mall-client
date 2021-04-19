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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.ebookDao = new EbookDao();
		this.categoryDao = new CategoryDao();

		// request 분석
		int currentPage = 1;
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}

		//카테고리별 정렬을 하기위해서 변수 선언후 기본값은 null 만약 view에서 특정 카테고리를 선택하면 설정
		String categoryName = null;
		if(request.getParameter("categoryName") != null) {
			categoryName = request.getParameter("categoryName");
		}

		//ebook검색기능
		String searchWord = null;
		if(request.getParameter("searchWord") != null) {
			searchWord = request.getParameter("searchWord");
		}

		//한페이지에 보여줄 책의 수 (추후에 선택가능하게 만들기)
		int rowPerPage = 15;

		//시작기준
		int beginRow = (currentPage-1)*rowPerPage;
		System.out.println(beginRow +"<---IndexController에서 beginRow");
		
		//총 책의 수
		int totalRow = this.ebookDao.totalCount(searchWord, categoryName);
		//totalRow디버깅
		System.out.println(totalRow +"<---IndexController에서 totalRow");

		//마지막 페이지(나머지가 있으면 다 보여주기 위해서 올림계산)
		int lastPage = (int)Math.ceil((double)totalRow /rowPerPage); 

		////페이징하기위한 범위 (1~10, 11~20, ... 처럼 나누기위한변수)
		int pageRange = (currentPage - 1) / 10;

		// model 호출
		this.ordersDao = new OrdersDao();
		List<Map<String, Object>> bestOrdersList = this.ordersDao.selectBestOrdersList();
		
		//모델호출 카테고리 이름들은 리스트로 받아온다. 리스트안의 하나의 카테고리 객체에 카테고리이름과 가중치가 담겨있음. 
		List<Category> categoryList = this.categoryDao.selectCategoryList();
		// model 호출 category가 null이면 모든 책을 가져오고 카테고리가 null이 아니면 특정 카테고리만 가져온다. 검색에 유무에따라 나눔.
		List<Ebook> ebookList = null;
		//검색어가 없을때
		if(searchWord == null) {
			ebookList = this.ebookDao.selectEbookListByPageAndCategory(beginRow, rowPerPage, categoryName);
		} else {//검색어가 있을때
			ebookList = this.ebookDao.selectEbookListByPageAndSearchword(beginRow, rowPerPage, searchWord);
		}

		// request객체에 리스트 저장 후 View forward
		request.setAttribute("bestOrdersList", bestOrdersList);
		request.setAttribute("searchWord", searchWord);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("rowPerPage", rowPerPage);
		request.setAttribute("lastPage", lastPage);
		request.setAttribute("pageRange", pageRange);
		request.setAttribute("categoryName", categoryName);
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("ebookList", ebookList);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/index.jsp");
		rd.forward(request, response);
	}

}

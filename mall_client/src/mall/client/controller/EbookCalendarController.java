package mall.client.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mall.client.model.EbookDao;
import mall.client.vo.Ebook;

@WebServlet("/EbookCalendarController")
public class EbookCalendarController extends HttpServlet {
	private EbookDao ebookDao;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.ebookDao = new EbookDao();
		
		// 년/월이 매개값으로 전달되지 않으면
		Calendar dday = Calendar.getInstance();
	
		if(request.getParameter("currentYear") != null) {
			dday.set(Calendar.YEAR, Integer.parseInt(request.getParameter("currentYear")));
		}
		if(request.getParameter("currentMonth") != null) {
			dday.set(Calendar.MONTH, Integer.parseInt(request.getParameter("currentMonth"))-1);
		}
		
		int currentYear = dday.get(Calendar.YEAR);
		int currentMonth = dday.get(Calendar.MONTH) + 1;
		
		// 마지막 일(ex: 31, 30, 29, 28, ...)
		int endDay = dday.getActualMaximum(Calendar.DAY_OF_MONTH); 
		
		// 현재 달의 1일의 요일
		Calendar firstDay = Calendar.getInstance();
		firstDay.set(Calendar.YEAR, currentYear);
		firstDay.set(Calendar.MONTH, currentMonth-1);
		firstDay.set(Calendar.DATE, 1);
		int firstDayOfWeek = firstDay.get(Calendar.DAY_OF_WEEK); //get(Calendar.DAY_OF_WEEK): 해당 날짜의 요일을 숫자로 출력
		
		System.out.println("currentYear: "+currentYear);
		System.out.println("currentMonth: "+currentMonth);
		System.out.println("endDay: "+endDay);
		System.out.println(firstDayOfWeek + "<--4/1 목요일(5)");
		
		List<Map<String, Object>> ebookListByMonth = this.ebookDao.selectEbookListByMonth(currentYear, currentMonth);
		
		request.setAttribute("ebookListByMonth", ebookListByMonth);
		request.setAttribute("currentYear", currentYear);
		request.setAttribute("currentMonth", currentMonth);
		request.setAttribute("endDay", endDay);
		request.setAttribute("firstDayOfWeek", firstDayOfWeek);
		request.getRequestDispatcher("/WEB-INF/view/ebook/ebookCalendar.jsp").forward(request, response);
	}
}

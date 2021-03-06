package mall.client.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mall.client.model.ClientDao;
import mall.client.vo.Client;

@WebServlet("/InsertClientController")
public class InsertClientController extends HttpServlet {
	private ClientDao clientDao;
	// 폼: C -> V
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginClient") != null) { // 로그인 되었으면
			response.sendRedirect("/IndexController");
		}
		request.getRequestDispatcher("/WEB-INF/view/client/insertClient.jsp").forward(request, response);
	}
	// action : C -> M -> redirect
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.clientDao = new ClientDao();
		
		request.setCharacterEncoding("utf-8");
		
		// 입력 form에서 받은 clientMail, clientPw 수집
		String clientMail = request.getParameter("clientMail");
		String clientPw = request.getParameter("clientPw");
		// 디버깅
		System.out.println("clientMail : " + clientMail);
		System.out.println("clientPw : " + clientPw);
		
		// 메일 중복처리 메서드 호출
		String returnClientMail = clientDao.selectClientMail(clientMail);
		// 메일 중복 시 콘솔 메세지 출력 후 회원가입 페이지로 이동
		if(returnClientMail != null) { // 메일이 존재한다면
			System.out.println("메일 중복");
			response.sendRedirect(request.getContextPath()+"/InsertClientController");
		}
		
		// 전처리: 회원정보 client vo 객체에 저장
		Client client = new Client();
		client.setClientMail(clientMail);
		client.setClientPw(clientPw);
		
		// Dao에서 insert 메서드 호출
		clientDao.insertClient(client);
		
		// redirect
		response.sendRedirect(request.getContextPath()+"/IndexController");
	}

}

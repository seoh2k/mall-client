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

@WebServlet("/UpdateClientPwController")
public class UpdateClientPwController extends HttpServlet {
	private ClientDao clientDao;
	
	// pw 변경 form으로 연결
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("loginClient") == null) { // 로그인 되었으면
			response.sendRedirect("/IndexController");
		}
		request.getRequestDispatcher("/WEB-INF/view/client/updateClientPw.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//회원 확인
		HttpSession session = request.getSession();
		if(session.getAttribute("loginClient") == null) {
			response.sendRedirect(request.getContextPath()+"/IndexController");
			return;
		}
		
		// 입력 form에서 받은 clientMail, clientPw 수집
		String clientMail = (((Client)session.getAttribute("loginClient")).getClientMail());
		String clientPw = request.getParameter("clientPw");
		// 디버깅
		System.out.println("clientMail : " + clientMail);
		System.out.println("clientPw : " + clientPw);
		
		// 전처리: 회원정보 client vo 객체에 저장
		Client client = new Client();
		client.setClientMail(clientMail);
		client.setClientPw(clientPw);
		
		// Dao에서 insert 메서드 호출
		this.clientDao = new ClientDao();
		clientDao.updateClientPw(client);
		
		// logout 세션 끊기
		session.invalidate();
		
		// redirect
		response.sendRedirect(request.getContextPath()+"/IndexController");
	}

}

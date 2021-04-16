package mall.client.model;

import java.sql.*;

import mall.client.commons.DBUtil;
import mall.client.vo.Client;

public class ClientDao {
	private DBUtil dbUtil;
	// 비밀번호 수정
	public int updateClientPw(Client client) {
		this.dbUtil = new DBUtil();
		int rowCnt = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			// db 연결
			conn = this.dbUtil.getConnection();
			String sql = "UPDATE client SET client_pw = PASSWORD(?) WHERE client_mail=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, client.getClientPw());
			stmt.setString(2, client.getClientMail());
			// 디버깅
			System.out.println("updateClientPw stmt: "+ stmt);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.dbUtil.close(null, stmt, conn);
		}
		return rowCnt;	
	}
	
	// 회원탈퇴
	public void deleteClient(String clientMail) {
		this.dbUtil = new DBUtil();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			// db 연결
			conn = this.dbUtil.getConnection();
			String sql = "DELETE FROM client WHERE client_mail=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, clientMail);
			// 디버깅
			System.out.println("deleteClient stmt: "+ stmt);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.dbUtil.close(null, stmt, conn);
		}
		
	}
	
	public Client selectClientOne(String clientMail) {
		// 전처리
		this.dbUtil= new DBUtil();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Client client = null;
		
		try {
			// db 연결		
			String sql = "SELECT client_no, client_mail, client_date FROM client WHERE client_mail=?";
			conn = this.dbUtil.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, clientMail);
			// 디버깅
			System.out.println("selectClientOne stmt: "+stmt);
			rs = stmt.executeQuery();
			if(rs.next()) { 
				client = new Client();
				client.setClientNo(rs.getInt("client_no"));
				client.setClientMail(rs.getString("client_mail"));
				client.setClientDate(rs.getString("client_date"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			this.dbUtil.close(rs, stmt, conn); // 쿼리가 INSERT문 메서드는 rs가 없다
		}
		return client;
	}
	
	// 회원가입
	public int insertClient(Client client) {
		// 전처리(dbutil, rowCnt, conn, stmt 객체 초기화)
		this.dbUtil= new DBUtil();
		int rowCnt = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			// db 연결
			String sql = "INSERT INTO client(client_mail, client_pw, client_date) VALUES(?,PASSWORD(?),now())";
			conn = this.dbUtil.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, client.getClientMail());
			stmt.setString(2, client.getClientPw());
			// 디버깅
			System.out.println("insertClient stmt: "+stmt);
			stmt.executeUpdate();
		} catch(Exception e) { // 예외 처리
			e.printStackTrace();
		} finally { // 할당값 해제
			this.dbUtil.close(null, stmt, conn); // 쿼리가 INSERT문 메서드는 rs가 없다
		}
		return rowCnt;
	}
	
	// 이메일 중복검사
	public String selectClientMail(String clientMail) {
		// 전처리 (dbutil, rowCnt, conn, stmt 객체, return타입 초기화)
		String returnClientMail = null;
		this.dbUtil = new DBUtil();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// db 연결
			String sql = "SELECT client_mail FROM client WHERE client_mail=?";
			conn = this.dbUtil.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, clientMail);
			
			// 디버깅
			System.out.println("selectClientMail stmt: "+stmt);
			
			rs = stmt.executeQuery();
			if(rs.next()) {
				returnClientMail = rs.getString("client_mail");
			}
		} catch(Exception e) { // 예외 처리
			e.printStackTrace();
		} finally { // 할당값 해제
			this.dbUtil.close(rs, stmt, conn);
		}
		return returnClientMail;
	}
	
	// 로그인 메서드
	public Client login(Client client) {
		this.dbUtil = new DBUtil();
		Client returnClient = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = this.dbUtil.getConnection();
			String sql = "SELECT client_no clientNo, client_mail clientMail FROM client WHERE client_mail=? AND client_pw=PASSWORD(?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, client.getClientMail());
			stmt.setString(2, client.getClientPw());
			rs = stmt.executeQuery();
			if(rs.next()) {
				returnClient = new Client();
				returnClient.setClientNo(rs.getInt("clientNo"));
				returnClient.setClientMail(rs.getString("clientMail"));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			this.dbUtil.close(rs, stmt, conn);
		}
		return returnClient;
	}
	
}

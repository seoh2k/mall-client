package mall.client.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import mall.client.commons.DBUtil;
import mall.client.vo.Cart;
import mall.client.vo.Client;

public class CartDao {
	private DBUtil dbUtil;
	
	public void deleteCartByClient(String clientMail) {
		this.dbUtil = new DBUtil();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = this.dbUtil.getConnection();
			String sql = "DELETE FROM cart WHERE client_mail=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, clientMail);
			System.out.println("deleteCartByClient stmt: "+ stmt);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.dbUtil.close(null, stmt, conn);
		}
		
	}
	
	public void deleteCart(Cart cart) {
		this.dbUtil = new DBUtil();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = this.dbUtil.getConnection();
			String sql = "DELETE from cart WHERE client_mail=? AND ebook_no=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, cart.getClientMail());
			stmt.setInt(2, cart.getEbookNo());
			// 디버깅
			System.out.println("deleteCart stmt: "+ stmt);
			stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.dbUtil.close(null, stmt, conn);
		}
		
	}
	
	public boolean selectClientMail(Cart cart) {
		boolean flag = true; // 중복 없음
		this.dbUtil = new DBUtil();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// DB 처리
		try {
			conn = this.dbUtil.getConnection();
			String sql = "SELECT * FROM cart WHERE client_mail=? AND ebook_no=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, cart.getClientMail());
			stmt.setInt(2, cart.getEbookNo());
			System.out.println("selectClientMail stmt: "+ stmt);
			
			rs = stmt.executeQuery();
			if(rs.next()) {
				flag = false; // 중복 있음
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.dbUtil.close(rs, stmt, conn);
		}
		return flag;
	}
	
	public int insertCart(Cart cart) {
		this.dbUtil = new DBUtil();
		int rowCnt = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = this.dbUtil.getConnection();
			String sql = "INSERT INTO cart(client_mail, ebook_no, cart_date) VALUES(?, ?, NOW())";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, cart.getClientMail());
			stmt.setInt(2, cart.getEbookNo());
			System.out.println("insertCart stmt: "+ stmt);
			rowCnt = stmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.dbUtil.close(null, stmt, conn);
		}
		return rowCnt;
	}
	
	public List<Map<String, Object>> selectCartList(String clientMail) {
		this.dbUtil = new DBUtil();
		List<Map<String, Object>> list = new ArrayList();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// DB 처리
		try {
			conn = this.dbUtil.getConnection();
			String sql = "SELECT c.cart_no, e.ebook_no, e.ebook_title, c.cart_date FROM cart c INNER JOIN ebook e ON c.ebook_no = e.ebook_no WHERE client_mail=? ORDER BY cart_date DESC";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, clientMail);
			System.out.println("selectCartList stmt: "+ stmt);
			
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String, Object> map = new HashMap<>();
				map.put("cartNo", rs.getInt("c.cart_no"));
				map.put("ebookNo", rs.getInt("e.ebook_no"));
				map.put("ebookTitle", rs.getString("e.ebook_title"));
				map.put("cartDate", rs.getString("c.cart_date"));
				list.add(map);
			}	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.dbUtil.close(rs, stmt, conn);
		}
		return list;
	}
}

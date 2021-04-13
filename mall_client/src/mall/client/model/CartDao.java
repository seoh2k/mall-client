package mall.client.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import mall.client.commons.DBUtil;
import mall.client.vo.Cart;

public class CartDao {
	private DBUtil dbUtil;
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

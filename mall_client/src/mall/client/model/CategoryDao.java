package mall.client.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import mall.client.commons.*;
import mall.client.vo.*;

public class CategoryDao {
	private DBUtil dbUtil;
	public List<Category> selectCategoryList() {
		this.dbUtil = new DBUtil();
		List<Category> list = new ArrayList<>();
		Category category = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = this.dbUtil.getConnection();
			String sql = "SELECT * FROM category ORDER BY category_weight DESC";
			stmt = conn.prepareStatement(sql);
			System.out.println("selectCategory " + stmt);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				category = new Category();
				category.setCategoryName(rs.getString("category_name"));
				// category.setCategoryWeight(rs.getInt("category_weight")); 가중치 사용하는 곳 없음
				list.add(category);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			this.dbUtil.close(rs, stmt, conn);
		}
		
		return list;
	}

}

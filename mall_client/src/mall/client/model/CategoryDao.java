package mall.client.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import mall.client.commons.*;
import mall.client.vo.*;

public class CategoryDao {
	private DBUtil dbUtil;
	public ArrayList<Category> selectCategoryList() {
		this.dbUtil = new DBUtil();
		ArrayList<Category> returnCategoryList = new ArrayList<Category>(); 
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			//db연결
			conn = this.dbUtil.getConnection();
			//sql 혹시나 나중에 가중치를 사용할지도 모르기때문에 weight도 category객체에 넣어둔다.
			String sql = "SELECT category_name categoryName, category_weight categoryWeight FROM category";

			//쿼리실행
			stmt = conn.prepareStatement(sql);
			System.out.println(stmt + "<-- CategoryDao.java에서 selectCategoryList()에서 stmt");
			rs = stmt.executeQuery();
			while(rs.next()) {
				Category category = new Category();
				category.setCategoryName(rs.getString("categoryName"));
				category.setCategoryWeight(rs.getInt("categoryWeight"));
				returnCategoryList.add(category);
			}

		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			this.dbUtil.close(rs, stmt, conn);
		}

		return returnCategoryList;
	}

}

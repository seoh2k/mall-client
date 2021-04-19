package mall.client.model;

import java.sql.*;
import java.util.*;

import mall.client.commons.DBUtil;
import mall.client.vo.Ebook;

public class EbookDao {
	private DBUtil dbUtil;
	
	public List<Map<String, Object>> selectEbookListByMonth(int year, int month){
		List<Map<String, Object>> list = new ArrayList<>();
		this.dbUtil = new DBUtil();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = this.dbUtil.getConnection();
			//sql
			String sql = "SELECT ebook_no ebookNo, ebook_title ebookTitle, day(ebook_date) d FROM ebook WHERE YEAR(ebook_date) = ? AND MONTH(ebook_date) = ? ORDER BY day(ebook_date) asc";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, year);
			stmt.setInt(2, month);
			//디버깅
			System.out.println(stmt + "<---selectEbookListByDay stmt");

			rs = stmt.executeQuery();
			//반복문 돌면서 리스트에 ebook객체 정보담기
			while(rs.next()) {
				Map<String, Object> map = new HashMap<>();
				map.put("ebookNo", rs.getInt("ebookNo"));
				map.put("ebookTitle", rs.getString("ebookTitle"));
				map.put("d", rs.getInt("d"));
				list.add(map);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			this.dbUtil.close(rs, stmt, conn);
		}
		return list;
	}
	
	//전체 책 가져오기 (검색어 여부, 카테고리정렬 여부, 검색기능을 이용했을때는 카테고리 정렬을 할수없음.)
	public int totalCount(String searchWord, String categoryName ) {
		this.dbUtil = new DBUtil();
		int totalRow = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//검색어와 카테고리 정렬이 없을때 모든 책의 수
		if(searchWord == null && categoryName == null) {
			try {
				conn = this.dbUtil.getConnection();
				//sql
				String sql = "SELECT COUNT(*) cnt FROM ebook";
				stmt = conn.prepareStatement(sql);
				rs = stmt.executeQuery();

				if(rs.next()) {
					totalRow = rs.getInt("cnt");
				}
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				this.dbUtil.close(rs, stmt, conn);
			}
		} else if(searchWord != null) { //검색어가 있을때
			try {
				conn = this.dbUtil.getConnection();
				//sql
				String sql = "SELECT COUNT(*) cnt FROM ebook WHERE ebook_title LIKE ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, "%"+searchWord+"%");
				System.out.println(sql + "<---EbookDao 에서 totalCount의 stmt");
				rs = stmt.executeQuery();

				if(rs.next()) {
					totalRow = rs.getInt("cnt");
				}
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				this.dbUtil.close(rs, stmt, conn);
			}
		} else if (categoryName != null) {
			try {
				conn = this.dbUtil.getConnection();
				//sql
				String sql = "SELECT COUNT(*) cnt FROM ebook WHERE category_name = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, categoryName);
				System.out.println(sql + "<---EbookDao 에서 totalCount의 stmt");
				rs = stmt.executeQuery();

				if(rs.next()) {
					totalRow = rs.getInt("cnt");
				}
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				this.dbUtil.close(rs, stmt, conn);
			}
		}

		//리턴
		return totalRow;
	}
	
	//책 상세보기 제공을 위한 메서드
	public Ebook selectEbookOne(int ebookNo) {
		this.dbUtil = new DBUtil();
		Ebook returnEbook = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = this.dbUtil.getConnection();
			//sql
			String sql = "SELECT ebook_no ebookNo, ebook_isbn ebookISBN, category_name categoryName, ebook_title ebookTitle, ebook_author ebookAuthor, ebook_company ebookCompany, ebook_page_count ebookPageCount, ebook_price ebookPrice, ebook_img ebookImg, ebook_summary ebookSummary, ebook_date ebookDate,ebook_state ebookState FROM ebook WHERE ebook_no = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, ebookNo);
			//디버깅
			System.out.println(sql + "<---EbookDao 에서 selectEbookOne의 stmt");
			rs = stmt.executeQuery();
			
			//리턴 할 ebook객체에 정보 저장
			if(rs.next()) {
				returnEbook = new Ebook();
				returnEbook.setEbookNo(rs.getInt("ebookNo"));
				returnEbook.setCategoryName(rs.getString("categoryName"));
				returnEbook.setEbookISBN(rs.getString("ebookISBN"));
				returnEbook.setEbookTitle(rs.getString("ebookTitle"));
				returnEbook.setEbookAuthor(rs.getString("ebookAuthor"));
				returnEbook.setEbookCompany(rs.getString("ebookCompany"));
				returnEbook.setEbookPageCount(rs.getInt("ebookPageCount"));
				returnEbook.setEbookPrice(rs.getInt("ebookPrice"));
				returnEbook.setEbookSummary(rs.getString("ebookSummary"));
				returnEbook.setEbookImg(rs.getString("ebookImg"));
				returnEbook.setEbookDate(rs.getString("ebookDate"));
				returnEbook.setEbookState(rs.getString("ebookState"));
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			this.dbUtil.close(rs, stmt, conn);
		}
		//리턴
		return returnEbook;
		
	}
	
	//ebook찾아보기 selectEbookListByPageAndCategory와 합칠수도 있지만 가독성을 위해서 분리함.
	public List<Ebook> selectEbookListByPageAndSearchword(int beginRow, int rowPerPage, String searchWord) {
		List<Ebook> returnList = new ArrayList<>();
		this.dbUtil = new DBUtil();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		//선택된 카테고리가 없다면 모든 책 셀렉트
		try {
			conn = this.dbUtil.getConnection();
			//sql
			String sql = "SELECT ebook_no ebookNo, ebook_title ebookTitle, ebook_price ebookPrice FROM ebook WHERE ebook_title LIKE ? ORDER BY ebook_date DESC LIMIT ?, ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%"+ searchWord +"%");
			stmt.setInt(2, beginRow);
			stmt.setInt(3, rowPerPage);
			//디버깅
			System.out.println(stmt + "<---EbookDao 에서 selectEbookListByPageAndSearchword의 stmt");

			rs = stmt.executeQuery();
			//반복문 돌면서 리스트에 ebook객체 정보담기
			while(rs.next()) {
				Ebook ebook = new Ebook();
				ebook.setEbookNo(rs.getInt("ebookNo"));
				ebook.setEbookTitle(rs.getString("ebookTitle"));
				ebook.setEbookPrice(rs.getInt("ebookPrice"));
				returnList.add(ebook);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			this.dbUtil.close(rs, stmt, conn);
		}

		return returnList;
	}

	//카테고리별 ebook가져오기
	public List<Ebook> selectEbookListByPageAndCategory(int beginRow, int rowPerPage, String categoryName) {
		List<Ebook> list = new ArrayList<>();
		this.dbUtil = new DBUtil();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		//선택된 카테고리가 없다면 모든 책 셀렉트
		if(categoryName == null) {
			try {
				conn = this.dbUtil.getConnection();
				//sql
				String sql = "SELECT ebook_no ebookNo, ebook_title ebookTitle, ebook_price ebookPrice FROM ebook ORDER BY ebook_date DESC LIMIT ?, ?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, beginRow);
				stmt.setInt(2, rowPerPage);
				//디버깅
				System.out.println(sql + "<---EbookDao 에서 selectEbookListByPage의 stmt");

				rs = stmt.executeQuery();
				//반복문 돌면서 리스트에 ebook객체 정보담기
				while(rs.next()) {
					Ebook ebook = new Ebook();
					ebook.setEbookNo(rs.getInt("ebookNo"));
					ebook.setEbookTitle(rs.getString("ebookTitle"));
					ebook.setEbookPrice(rs.getInt("ebookPrice"));
					list.add(ebook);
				}
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				this.dbUtil.close(rs, stmt, conn);
			}
		} else {//선택된 카테고리가 있다면 그 카테고리만 셀렉트
			try {
				conn = this.dbUtil.getConnection();
				//sql
				String sql = "SELECT ebook_no ebookNo, ebook_title ebookTitle, ebook_price ebookPrice FROM ebook WHERE category_name = ? ORDER BY ebook_date DESC LIMIT ?, ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, categoryName);
				stmt.setInt(2, beginRow);
				stmt.setInt(3, rowPerPage);
				//디버깅
				System.out.println(sql + "<---EbookDao 에서 selectEbookListByPage의 stmt");

				rs = stmt.executeQuery();
				//반복문 돌면서 리스트에 ebook객체 정보담기
				while(rs.next()) {
					Ebook ebook = new Ebook();
					ebook.setEbookNo(rs.getInt("ebookNo"));
					ebook.setEbookTitle(rs.getString("ebookTitle"));
					ebook.setEbookPrice(rs.getInt("ebookPrice"));
					list.add(ebook);
				}
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				this.dbUtil.close(rs, stmt, conn);
			}
		}

		//리스트 리턴
		return list;
	}
					
}

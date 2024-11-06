package bookmark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import wifi.UTIL_DATA;

public class BookmarkService {
	
	public void loginDB() {

	    try {
	        Class.forName(UTIL_DATA.driverName);
	    } catch (ClassNotFoundException e) {
	        System.out.println(e.getMessage());
	    }
	}
	
	public void insertBookmark(String bookmark_name,int order) {
		loginDB();
		
		String sql = "INSERT INTO bookmark (bookmark_id, bookmark_name, `order`, regist_date, manage_no) "
				+ "VALUES (?, ?, ?, ?,?)";
		
		try(Connection connection = DriverManager.getConnection(UTIL_DATA.url,UTIL_DATA.dbUserId,UTIL_DATA.dbPassword);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			
			
			preparedStatement.setInt(1, order);
			preparedStatement.setString(2,bookmark_name);
			preparedStatement.setInt(3, order);
			preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			preparedStatement.setString(5," ");
			int affectedRows = preparedStatement.executeUpdate();
			
			if(affectedRows>0) {
				System.out.println("북마크가 성공적으로 저장되었습니다.");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<BookmarkInfo> selectBookmark() {
		loginDB();
		List<BookmarkInfo> bookmarkList = new ArrayList<>();
		
		String sql = "select bookmark_id,bookmark_name,`order`,regist_date,update_date from bookmark order by `order` asc;";
		
		try(Connection connection = DriverManager.getConnection(UTIL_DATA.url, UTIL_DATA.dbUserId, UTIL_DATA.dbPassword);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				
				BookmarkInfo bookmark = new BookmarkInfo();
				bookmark.setBookmark_id(rs.getInt("bookmark_id"));
				bookmark.setBookmark_name(rs.getString("bookmark_name"));
				bookmark.setBookmark_order(rs.getInt("order"));
				bookmark.setRegist_date(rs.getTimestamp("regist_date"));
				bookmark.setUpdate_date(rs.getTimestamp("update_date"));
				bookmarkList.add(bookmark);
				
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return bookmarkList;
		
	}

	
	public BookmarkInfo selectBookmark(int bookmark_id) {
		
		
		loginDB();
		BookmarkInfo bookmark = new BookmarkInfo();
		
		String sql = "select bookmark_id,bookmark_name,`order`,regist_date,update_date "
				+ " from bookmark "
				+ " where bookmark_id = ? "
				+ " order by `order` asc";
		
		try(Connection connection = DriverManager.getConnection(UTIL_DATA.url, UTIL_DATA.dbUserId, UTIL_DATA.dbPassword);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			
			preparedStatement.setInt(1, bookmark_id);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				
				bookmark.setBookmark_name(rs.getString("bookmark_name"));
				bookmark.setBookmark_order(rs.getInt("order"));
				bookmark.setBookmark_id(rs.getInt("bookmark_id"));

				
				
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return bookmark;
		
	}

	public void updateBookmark(int bookmark_id,String bookmark_name,int order) {
		
		loginDB();

		String sql = "UPDATE bookmark SET bookmark_name = ?, `order` = ?, update_date = ? WHERE bookmark_id = ?";
		
		try(Connection connection = DriverManager.getConnection(UTIL_DATA.url,UTIL_DATA.dbUserId,UTIL_DATA.dbPassword);
				PreparedStatement prearedStatement = connection.prepareStatement(sql)){
			
			
			prearedStatement.setString(1, bookmark_name);
			prearedStatement.setInt(2, order);
			prearedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			prearedStatement.setInt(4, bookmark_id);
			
			int affectedRows = prearedStatement.executeUpdate();
			
			if(affectedRows > 0) {
				System.out.println("북마크가 성공적으로 업데이트 되었습니다.");
			}else {
				System.out.println("북마크를 찾지 못하였습니다.");
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}


	public void deleteBookmark(int bookmark_id) {
		loginDB();
		
		String sql = "DELETE FROM bookmark WHERE bookmark_id = ?";
		
		
		try(Connection connection = DriverManager.getConnection(UTIL_DATA.url, UTIL_DATA.dbUserId, UTIL_DATA.dbPassword);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			
			preparedStatement.setInt(1, bookmark_id);
			
			int deleteRow = preparedStatement.executeUpdate();
			
			if(deleteRow > 0) {
				System.out.println("행이 삭제되었습니다.");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	
	public void updateDetailBookmark(String manage_no,int id) {
		
		loginDB();
		
		String sql = "UPDATE bookmark SET manage_no = ? WHERE bookmark_id = ?";
		
		try(Connection connection = DriverManager.getConnection(UTIL_DATA.url,UTIL_DATA.dbUserId,UTIL_DATA.dbPassword);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			
			preparedStatement.setString(1,manage_no);
			preparedStatement.setInt(2,id);
			
			int affectedRows = preparedStatement.executeUpdate();
			
			if(affectedRows > 0) {
				System.out.println("updateDetailBookmark ");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void upsertBookmarkWifi(String manage_no,int id) {
		loginDB();
		
		try(Connection connection = DriverManager.getConnection(UTIL_DATA.url,UTIL_DATA.dbUserId,UTIL_DATA.dbPassword)){
			
			connection.setAutoCommit(false);
			
			String updateQuery = "UPDATE bookmark SET manage_no = ? WHERE bookmark_id = ?";
			
			try(PreparedStatement updateStmt = connection.prepareStatement(updateQuery)){
				updateStmt.setString(1, manage_no);
				updateStmt.setInt(2, id);
				updateStmt.executeUpdate();
			}
			
			String insertQuery = "INSERT INTO bookmark_wifi (manage_no,bookmark_id,regist_date) VALUES (?, ?, NOW())";
			
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                insertStmt.setString(1, manage_no);
                insertStmt.setInt(2, id);
                insertStmt.executeUpdate();
            }

            connection.commit();
            System.out.println("성공 upsertBookmarkWifi");
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	
	public List<BookmarkListInfo> listBookmarkInfo(){
		
		loginDB();
		List<BookmarkListInfo> bookmarkListInfo = new ArrayList<>();
		
		String sql = "SELECT "
				+ "    bw.bookmark_id AS id, "
				+ "    b.bookmark_name AS bookmark_name, "
				+ "    w.wifi_name AS wifi_name, "
				+ "    bw.regist_date AS regist_date, "
				+ "	   bw.manage_no AS manage_no	"
				+ " FROM "
				+ "    bookmark_wifi bw "
				+ " JOIN "
				+ "    bookmark b ON bw.bookmark_id = b.bookmark_id "
				+ " JOIN "
				+ "    wifi_info w ON bw.manage_no = w.manage_no "
				+ " ORDER BY "
				+ "    bw.regist_date DESC";
		
		try(Connection connection = DriverManager.getConnection(UTIL_DATA.url,UTIL_DATA.dbUserId,UTIL_DATA.dbPassword);
			PreparedStatement prearedStatement = connection.prepareStatement(sql)){
			
			ResultSet rs = prearedStatement.executeQuery();
			
			while(rs.next()) {
				BookmarkListInfo bookmark = new BookmarkListInfo();
				bookmark.setId(rs.getInt("id"));
				bookmark.setBookmark_name(rs.getString("bookmark_name"));
				bookmark.setWifi_name(rs.getString("wifi_name"));
				bookmark.setRegist_date(rs.getTimestamp("regist_date"));
				bookmark.setManage_no(rs.getString("manage_no"));
				bookmarkListInfo.add(bookmark);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return bookmarkListInfo;
	}
	
	
	public BookmarkListInfo selectByDeletedBookmarkInfo(String manage_no) {
		
		loginDB();
		BookmarkListInfo bookmark = new BookmarkListInfo();
		
		String sql = "SELECT"
				+ "    bw.bookmark_id AS id, "
				+ "    b.bookmark_name AS bookmark_name, "
				+ "    w.wifi_name AS wifi_name, "
				+ "    bw.regist_date AS regist_date, "
				+ "    bw.manage_no AS manage_no "
				+ " FROM "
				+ "    bookmark_wifi bw "
				+ " JOIN"
				+ "    bookmark b ON bw.bookmark_id = b.bookmark_id "
				+ " JOIN "
				+ "    wifi_info w ON bw.manage_no = w.manage_no "
				+ " where bw.manage_no = ? "
				+ " ORDER BY "
				+ "    bw.regist_date DESC";
		
		try(Connection connection = DriverManager.getConnection(UTIL_DATA.url, UTIL_DATA.dbUserId, UTIL_DATA.dbPassword);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			
			preparedStatement.setString(1, manage_no);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {			
				bookmark.setId(rs.getInt("id"));
				bookmark.setBookmark_name(rs.getString("bookmark_name"));
				bookmark.setWifi_name(rs.getString("wifi_name"));
				bookmark.setRegist_date(rs.getTimestamp("regist_date"));
				bookmark.setManage_no(rs.getString("manage_no"));
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return bookmark;
	}


	public void deleteBookmarkInfo(String manage_no) {
		loginDB();
		
		String sql = "DELETE FROM bookmark_wifi WHERE manage_no = ?";
		
		
		try(Connection connection = DriverManager.getConnection(UTIL_DATA.url, UTIL_DATA.dbUserId, UTIL_DATA.dbPassword);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			
			preparedStatement.setString(1, manage_no);
			
			int deleteRow = preparedStatement.executeUpdate();
			
			if(deleteRow > 0) {
				System.out.println("행이 삭제되었습니다.");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

}

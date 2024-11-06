package wifi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WifiService {
	
	static Map<String,WifiInfo> wifiInfoMapping = new HashMap<>();

	public void loginDB() {

	    try {
	        Class.forName(UTIL_DATA.driverName);
	    } catch (ClassNotFoundException e) {
	        System.out.println(e.getMessage());
	    }
	    
	}
	
	public void BulkifyInsert(List<WifiInfo> wifiList) {
		loginDB();
		
		String sql = "INSERT INTO wifi_info (manage_no, autonomous_district, wifi_name, road_name_address, detail_address, install_location, install_type, install_agency, service_category, net_type, install_year, wifi_conn, inOutDoor, x_coordinate, y_coordinate, work_date) " +
	             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
	             "ON DUPLICATE KEY UPDATE " +
	             "autonomous_district = VALUES(autonomous_district), " +
	             "wifi_name = VALUES(wifi_name), " +
	             "road_name_address = VALUES(road_name_address), " +
	             "detail_address = VALUES(detail_address), " +
	             "install_location = VALUES(install_location), " +
	             "install_type = VALUES(install_type), " +
	             "install_agency = VALUES(install_agency), " +
	             "service_category = VALUES(service_category), " +
	             "net_type = VALUES(net_type), " +
	             "install_year = VALUES(install_year), " +
	             "wifi_conn = VALUES(wifi_conn), " +
	             "inOutDoor = VALUES(inOutDoor), " +
	             "x_coordinate = VALUES(x_coordinate), " +
	             "y_coordinate = VALUES(y_coordinate), " +
	             "work_date = VALUES(work_date)";
	    
	  
	    try (Connection connection = DriverManager.getConnection(UTIL_DATA.url, UTIL_DATA.dbUserId, UTIL_DATA.dbPassword);
	            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

	           connection.setAutoCommit(false);
	           
	            for (WifiInfo wifi : wifiList) {
	                preparedStatement.setString(1, wifi.getManage_no());
	                preparedStatement.setString(2, wifi.getAutonomous_district());
	                preparedStatement.setString(3, wifi.getWifi_name());
	                preparedStatement.setString(4, wifi.getRoad_name_address());
	                preparedStatement.setString(5, wifi.getDetail_address());
	                preparedStatement.setString(6, wifi.getInstall_location());
	                preparedStatement.setString(7, wifi.getInstall_type());
	                preparedStatement.setString(8, wifi.getInstall_agency());
	                preparedStatement.setString(9, wifi.getService_category());
	                preparedStatement.setString(10, wifi.getNet_type());
	                preparedStatement.setString(11, wifi.getInstall_year());
	                preparedStatement.setString(12, wifi.getWifi_conn());
	                preparedStatement.setString(13, wifi.getInOutDoor());
	                preparedStatement.setDouble(14, wifi.getX_coordinate());
	                preparedStatement.setDouble(15, wifi.getY_coordinate());
	                preparedStatement.setString(16, wifi.getWorkDate());

	                preparedStatement.addBatch();
	            }
	            
	            preparedStatement.executeBatch();
	            connection.commit();
	            System.out.println("데이터 Insert 성공하였습니다.");

	           
	       } catch (SQLException e) {
	           e.printStackTrace();
	       }
	}
	
	
	public List<WifiInfo> nearWifiList(double x_coordinate , double y_coordinate) {
		
		
		loginDB();
		
		List<WifiInfo> wifiList = new ArrayList<>();
		
		String sql ="SELECT "
				+ "       ROUND(SQRT(POW(x_coordinate - ?, 2) + POW(y_coordinate - ?, 2)),4) AS distance "
				+ "       ,manage_no,autonomous_district,wifi_name,road_name_address,detail_address,install_location,install_agency,install_type,service_category,net_type,install_year,inOutDoor,wifi_conn,x_coordinate,y_coordinate,work_date "
				+ " FROM WIFI_INFO "
				+ " ORDER BY distance asc "
				+ " limit 20";
		
		try (Connection connection = DriverManager.getConnection(UTIL_DATA.url, UTIL_DATA.dbUserId, UTIL_DATA.dbPassword);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			
			preparedStatement.setDouble(1, x_coordinate);
			preparedStatement.setDouble(2, y_coordinate);
			
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
                WifiInfo wifiInfo = new WifiInfo();
                wifiInfo.setManage_no(rs.getString("manage_no"));
                wifiInfo.setAutonomous_district(rs.getString("autonomous_district"));
                wifiInfo.setWifi_name(rs.getString("wifi_name"));
                wifiInfo.setRoad_name_address(rs.getString("road_name_address"));
                wifiInfo.setDetail_address(rs.getString("detail_address"));
                wifiInfo.setInstall_location(rs.getString("install_location"));
                wifiInfo.setInstall_agency(rs.getString("install_agency"));
                wifiInfo.setInstall_type(rs.getString("install_type"));
                wifiInfo.setService_category(rs.getString("service_category"));
                wifiInfo.setNet_type(rs.getString("net_type"));
                wifiInfo.setInstall_year(rs.getString("install_year"));
                wifiInfo.setInOutDoor(rs.getString("inOutDoor"));
                wifiInfo.setWifi_conn(rs.getString("wifi_conn"));
                wifiInfo.setX_coordinate(rs.getDouble("x_coordinate"));
                wifiInfo.setY_coordinate(rs.getDouble("y_coordinate"));
                wifiInfo.setDistance(rs.getDouble("distance"));
                wifiInfo.setWorkDate(rs.getString("work_date"));
                
                wifiInfoMapping.put(rs.getString("manage_no"), wifiInfo);
                
                wifiList.add(wifiInfo);
			}
			
			insertLocationHistory(x_coordinate, y_coordinate);
		} catch(Exception e) {
			e.printStackTrace();
		}
				
		
		
		return wifiList;
	}
	
	public int listTotalCount() {
		
		int count = 0;
		
		String sql = "select count(*) from wifi_info";
		
		try(Connection connection = DriverManager.getConnection(UTIL_DATA.url,UTIL_DATA.dbUserId,UTIL_DATA.dbPassword);
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				ResultSet resultSet = preparedStatement.executeQuery()){

			if(resultSet.next()) {
				count = resultSet.getInt(1);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	
		return count;
	}

	public void insertLocationHistory(double x_coordinate , double y_coordinate) {
		
		loginDB();
		
		String sql = "insert into location_history (x_coordinate,y_coordinate,view_date) value (?,?,?);";
		
		try(Connection connection = DriverManager.getConnection(UTIL_DATA.url, UTIL_DATA.dbUserId,UTIL_DATA.dbPassword);
				PreparedStatement prepatedStatement = connection.prepareStatement(sql)){
			
			prepatedStatement.setDouble(1, x_coordinate);
			prepatedStatement.setDouble(2, y_coordinate);
			prepatedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			
			
			int afftectedRows = prepatedStatement.executeUpdate();
			
			if(afftectedRows > 0) {
				System.out.println("Location History가 성공적으로 저장되었습니다.");
			}
		
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public List<LocationHistory> selectLocationHistory(){
		
		List<LocationHistory> historyList = new ArrayList<>();
		
		loginDB();
		
		String sql = "select history_id,x_coordinate,y_coordinate,view_date from location_history order by history_id desc";
		
		try(Connection connection = DriverManager.getConnection(UTIL_DATA.url,UTIL_DATA.dbUserId,UTIL_DATA.dbPassword);
				PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				LocationHistory lh = new LocationHistory();
				
				lh.setID(rs.getInt("history_id"));
				lh.setX_coordinate(rs.getDouble("x_coordinate"));
				lh.setY_coordinate(rs.getDouble("y_coordinate"));
				lh.setViewDate(rs.getTimestamp("view_date"));
				
				historyList.add(lh);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return historyList;
	}

	public WifiInfo detailWifiInfo(String manage_no) {
	
		

		return wifiInfoMapping.get(manage_no);
	}
	
	public void deleteLocationHistory(int id) {
		
		loginDB();
		
		String sql = "DELETE FROM location_history WHERE history_id = ? ";
		
		try(Connection connection = DriverManager.getConnection(UTIL_DATA.url,UTIL_DATA.dbUserId,UTIL_DATA.dbPassword)
				;PreparedStatement preparedStatement = connection.prepareStatement(sql)){
			
			preparedStatement.setInt(1, id);
			
			int deletedRow = preparedStatement.executeUpdate();
			
			if(deletedRow > 0) {
				System.out.println("행이 삭제 되었습니다.");
				
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

}

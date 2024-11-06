package wifi;
import java.text.DecimalFormat;
import java.util.Date;

import com.google.gson.JsonObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data

public class WifiInfo {
	
	private double distance;
	private String manage_no;
	private String autonomous_district;
	private String wifi_name;
	private String road_name_address;
	private String detail_address;
	private String install_location;
	private String install_type;
	private String install_agency;
	private String service_category;
	private String net_type;
	private String install_year;
	private String wifi_conn;
	private String inOutDoor;
	private double x_coordinate;
	private double y_coordinate;
	private String workDate;
	
	public WifiInfo(JsonObject json) {
	    this.manage_no = json.has("X_SWIFI_MGR_NO") ? json.get("X_SWIFI_MGR_NO").getAsString() : "";
        this.autonomous_district = json.has("X_SWIFI_WRDOFC") ? json.get("X_SWIFI_WRDOFC").getAsString() : "";
        this.wifi_name = json.has("X_SWIFI_MAIN_NM") ? json.get("X_SWIFI_MAIN_NM").getAsString() : "";
        this.road_name_address = json.has("X_SWIFI_ADRES1") ? json.get("X_SWIFI_ADRES1").getAsString() : "";
        this.detail_address = json.has("X_SWIFI_ADRES2") ? json.get("X_SWIFI_ADRES2").getAsString() : "";
        this.install_location = json.has("X_SWIFI_INSTL_FLOOR") ? json.get("X_SWIFI_INSTL_FLOOR").getAsString() : "";
        this.install_type = json.has("X_SWIFI_INSTL_TY") ? json.get("X_SWIFI_INSTL_TY").getAsString() : "";
        this.install_agency = json.has("X_SWIFI_INSTL_MBY") ? json.get("X_SWIFI_INSTL_MBY").getAsString() : "";
        this.service_category = json.has("X_SWIFI_SVC_SE") ? json.get("X_SWIFI_SVC_SE").getAsString() : "";
        this.net_type = json.has("X_SWIFI_CMCWR") ? json.get("X_SWIFI_CMCWR").getAsString() : "";
        this.install_year = json.has("X_SWIFI_CNSTC_YEAR") ? json.get("X_SWIFI_CNSTC_YEAR").getAsString() : "";
        this.inOutDoor = json.has("X_SWIFI_INOUT_DOOR") ? json.get("X_SWIFI_INOUT_DOOR").getAsString() : "";
        this.wifi_conn = json.has("X_SWIFI_REMARS3") ? json.get("X_SWIFI_REMARS3").getAsString() : "";
        this.x_coordinate = json.has("LAT") ? json.get("LAT").getAsDouble() : 0;
        this.y_coordinate = json.has("LNT") ? json.get("LNT").getAsDouble() : 0;
        this.workDate = json.has("WORK_DTTM") ? json.get("WORK_DTTM").getAsString() : "";
	}

}

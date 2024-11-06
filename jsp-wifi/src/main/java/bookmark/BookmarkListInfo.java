package bookmark;

import java.sql.Timestamp;

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


public class BookmarkListInfo {
	
	private int id;
	private String bookmark_name;
	private String wifi_name;
	private Timestamp regist_date; 
	private String manage_no;

}

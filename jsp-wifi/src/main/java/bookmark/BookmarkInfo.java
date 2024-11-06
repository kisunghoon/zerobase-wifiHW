package bookmark;

import java.sql.Date;
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

public class BookmarkInfo {

	private int bookmark_id;
	private String bookmark_name;
	private int bookmark_order;
	private String manage_no;
	private Timestamp  regist_date;
	private Timestamp  update_date;
}

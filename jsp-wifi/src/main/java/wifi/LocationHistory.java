package wifi;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocationHistory {
	
	private int ID;
	private double x_coordinate;
	private double y_coordinate; 
	private Date viewDate;

}

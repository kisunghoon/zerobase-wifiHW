package wifi;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WifiApi {
	
	
	OkHttpClient http = new OkHttpClient();
	Gson gson = new Gson();
	List<WifiInfo> wifiDataList = new ArrayList<>();
	
	public int getTotal() {
		
		StringBuilder urlBuilder;
		urlBuilder = new StringBuilder(UTIL_DATA.BaseUrl);
		urlBuilder.append("/");
		urlBuilder.append(UTIL_DATA.AuthenticationKey);
		urlBuilder.append("/json/TbPublicWifiInfo/1/1");
		
		Request req = new Request.Builder().url(urlBuilder.toString()).get().build();
		try (Response res = http.newCall(req).execute()) {
			if (res.isSuccessful()) {
				String resBody = res.body().string();
				JsonObject jsonObject = JsonParser.parseString(resBody).getAsJsonObject();
				JsonObject wifiInfo = jsonObject.getAsJsonObject("TbPublicWifiInfo");
				
				return wifiInfo.get("list_total_count").getAsInt();
			} else {
				System.out.println("HTTP Error Code: " + res.code());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
		
	}
	
	public void getWifiInfo(int start, int end) {

		
		StringBuilder urlBulider;
		StringBuilder pageIndex = new StringBuilder();
		

		urlBulider = new StringBuilder(UTIL_DATA.BaseUrl);
		urlBulider.append("/");
		urlBulider.append(UTIL_DATA.AuthenticationKey);
		urlBulider.append("/json/TbPublicWifiInfo");
		
		
		pageIndex.append("/").append(start).append("/").append(end);
		urlBulider.append(pageIndex.toString());
		
		Request request = new Request.Builder().url(urlBulider.toString()).get().build();
		Response response = null;
		
		try {
			
			response = http.newCall(request).execute();
			
			if(response.isSuccessful()) {
				String resBody = response.body().string();
				
				JsonObject jsonObject = JsonParser.parseString(resBody).getAsJsonObject();
				JsonObject wifiInfo = jsonObject.getAsJsonObject("TbPublicWifiInfo");
				
				UTIL_DATA.listTotalCount = wifiInfo.get("list_total_count").getAsInt();
				
				JsonArray rows = wifiInfo.getAsJsonArray("row");
				
				for(int i=0;i<rows.size();i++) {
					JsonObject rowObject = rows.get(i).getAsJsonObject();
					WifiInfo wifiData = new WifiInfo(rowObject);
					wifiDataList.add(wifiData);
		
				}
				
				WifiService service = new WifiService();
				service.BulkifyInsert(wifiDataList);
				
				
			}else {
				System.out.println("HTTP Error Code "+response.code());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		} 
		finally {
			if(response != null) {
				response.close();
			}
		}
	}
	
	public void execute() {
		
		int totalCnt = getTotal();
		
		if(totalCnt > 0) {
			int start = 1;
			int windowSize = 1000;
			int end = windowSize;
			
			while(start <= totalCnt) {
				getWifiInfo(start, Math.min(end, totalCnt));
				
				start += windowSize;
				end +=windowSize;
					
			}
		}
	}

	

	public static void main(String[] args){
		//WifiApi api = new WifiApi();
		//api.execute();
		//System.out.println("총 갯수 :"+UTIL_DATA.listTotalCount);
		//WifiService service = new WifiService();
		//service.insertLocationHistory(37.596369, 37.596369);
		
		
	}

}

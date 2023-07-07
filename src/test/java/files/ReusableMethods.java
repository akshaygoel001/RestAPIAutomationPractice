package files;

import io.restassured.path.json.JsonPath;


public class ReusableMethods {
	
	public static String rawToJSON(String response,String path) {
		JsonPath json=new JsonPath(response);
		return json.getString(path);
	}

}

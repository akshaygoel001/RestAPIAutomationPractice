import io.restassured.RestAssured;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SerializeTest {
	
	public static void main(String[] args) {
		
		AddPlace payload=new AddPlace();
		Location l=new Location();
		List<String> list=new ArrayList<String>();
		list.add("shoe park");list.add("shop");
		l.setLat(-38.383494);
		l.setLng(33.427362);
		
		payload.setAccuracy(50);
		payload.setAddress("29, side layout, cohen 09");
		payload.setLanguage("French-IN");
		payload.setName("Frontline house");
		payload.setPhone_number("(+91) 983 893 3937");
		payload.setTypes(list);
		payload.setLocation(l);
		payload.setWebsite("http://google.com");
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		String response=given().log().all().
			queryParam("key", "qaclick123")
		.body(payload)
		.when()
		.post("/maps/api/place/add/json")
		.then().log().all()
			.assertThat()
				.statusCode(200)
		.extract().response().asString();
		
		
	}
	

}

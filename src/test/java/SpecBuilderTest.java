import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SpecBuilderTest {
	
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
		
		RequestSpecification reqSpec=new RequestSpecBuilder()
			.setBaseUri("https://rahulshettyacademy.com")
			.addQueryParam("key", "qaclick123")
			.setContentType(ContentType.JSON)
			.build();
		
		ResponseSpecification respSpec=new ResponseSpecBuilder()
			.expectStatusCode(200)
			.expectContentType(ContentType.JSON)
			.build();
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		
		String response=given().log().all().
			spec(reqSpec)
		.body(payload)
		.when()
		.post("/maps/api/place/add/json")
		.then().log().all()
			.spec(respSpec)
		.extract().response().asString();
		JsonPath js=new JsonPath(response);
		String placeId=js.getString("place_id");
		given().log().all()
			.spec(reqSpec)
			.queryParam("place_id", placeId)
		.when()
		.get("/maps/api/place/get/json")
		.then().log().all()
			.assertThat()
				.statusCode(200);
		
		
	}
	

}

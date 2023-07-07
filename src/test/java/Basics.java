import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;

public class Basics {
	
	public static void main(String[] args) throws IOException {
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		//Add Place
		String addPlaceResponse=given().log().all()
			.queryParam("key","qaclick123")
		.header("Content-Type","application/json")
		.body(new String(Files.readAllBytes(Paths.get(".\\src\\test\\resources\\data.json"))))
		.when()
		.post("maps/api/place/add/json")
		.then()
			.assertThat()
				.statusCode(200)
				.body("scope",equalTo("APP"))
				.header("Server", "Apache/2.4.52 (Ubuntu)")
		.extract().response().asString();
		System.out.println("----------------------");
		//System.out.println(addPlaceResponse);
		String placeId=ReusableMethods.rawToJSON(addPlaceResponse, "place_id");
		System.out.println("----------------------");
		System.out.println(placeId);
		
		//Update Place
		String newAddress="Roosevelt, Germany";
		given().log().all()
			.queryParam("key", "qaclick123")
			.header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when()
		.put("maps/api/place/update/json")
		.then().log().all()
			.assertThat()
				.statusCode(200)
				.body("msg", equalTo("Address successfully updated"));
		
		//Get Place
		String getResponse=given().log().all()
			.queryParam("key", "qaclick123")
			.queryParam("place_id", placeId)
		.when()
		.get("maps/api/place/get/json")
		.then().log().all()
			.assertThat()
				.statusCode(200)
		.extract().response().asString();
		System.out.println("----------------------");
		String actualAddress=ReusableMethods.rawToJSON(getResponse, "address");
		
		Assert.assertEquals(actualAddress, newAddress);
		
	}

}

import static io.restassured.RestAssured.given;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.GetCourse;

//Deserialize
public class OAuth2Test {
	
	public static void main(String[] args) {
		
		
		String code=
				"4%2F0AZEOvhXHxS3UqQe4e4W-Fqh4SR2I8usNZTDEhKZ8fvsDKPgkEpqBlR0-pZ2aJuMcF9mwrw";
		
		String accessTokenResponse=given().urlEncodingEnabled(false)
			.queryParam("code", code)
			.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
			.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
			.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
			.queryParam("grant_type", "authorization_code")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token")
		.asString();
		JsonPath js=new JsonPath(accessTokenResponse);
		String accessToken=js.getString("access_token");
			
		GetCourse response=given().
			queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
		.when()
		.get("https://rahulshettyacademy.com/getCourse.php")
		.then()
			.assertThat()
				.statusCode(200)
		.extract().response().as(GetCourse.class);
		System.out.println(response);
		System.out.println(response.getLinkedIn());
		System.out.println(response.getInstructor());
		
		
	}

}

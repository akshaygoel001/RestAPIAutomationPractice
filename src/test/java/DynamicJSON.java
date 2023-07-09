import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ReusableMethods;
import io.restassured.RestAssured;

public class DynamicJSON {

	@Test
	public void addBook() {
		Map<String,Object> jsonAsMap=new HashMap<>();
		jsonAsMap.put("name", "Learn Selenium Automation with Javascript");
		jsonAsMap.put("isbn", "uyt");
		jsonAsMap.put("aisle", 1223);
		jsonAsMap.put("author", "Ana Adams");
		
		RestAssured.baseURI="http://216.10.245.166";
		String response=given().log().all()
			.header("Content-Type","application/json")
		.body(jsonAsMap)
		.when()
		.post("Library/Addbook.php")
		.then().log().all()
			.assertThat()
				.statusCode(200)
		.extract().response().asString();
		String id=ReusableMethods.rawToJSON(response, "ID");
		System.out.println(response);
		
		given().log().all()
			.queryParam("ID", id)
		.when()
		.get("/Library/GetBook.php")
		.then().log().all()
			.assertThat()
				.statusCode(200);
		
		/*String delRresponse=given().
				header("Content-Type","application/json")
			.body(Payload.deleteBook(id))
			.when()
			.post("Library/DeleteBook.php")
			.then()
				.assertThat()
					.statusCode(200)
			.extract().response().asString();
			System.out.println(delRresponse); */
	}
	
	
	
	@DataProvider(name="BooksData")
	public Object[][] getData() {
		//return new Object[][] {{"aaa",123},{"sss",234},{"qaw",554}};
		return new Object[][] {{"aaa",1200}};
	}
}

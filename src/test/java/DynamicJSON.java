import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

public class DynamicJSON {

	@Test(dataProvider = "BooksData")
	public void addBook(String isbn,int aisle) {
		RestAssured.baseURI="http://216.10.245.166";
		String response=given().
			header("Content-Type","application/json")
		.body(Payload.addBook(isbn,aisle))
		.when()
		.post("Library/Addbook.php")
		.then()
			.assertThat()
				.statusCode(200)
		.extract().response().asString();
		String id=ReusableMethods.rawToJSON(response, "ID");
		System.out.println(response);
		
		String delRresponse=given().
				header("Content-Type","application/json")
			.body(Payload.deleteBook(id))
			.when()
			.post("Library/DeleteBook.php")
			.then()
				.assertThat()
					.statusCode(200)
			.extract().response().asString();
			System.out.println(delRresponse);
	}
	
	
	
	@DataProvider(name="BooksData")
	public Object[][] getData() {
		return new Object[][] {{"aaa",123},{"sss",234},{"qaw",554}};
	}
}

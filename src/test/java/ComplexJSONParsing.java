import org.testng.Assert;

import files.Payload;
import files.ReusableMethods;
import io.restassured.path.json.JsonPath;

public class ComplexJSONParsing {

	public static void main(String[] args) {
		JsonPath json=new JsonPath(Payload.coursePrices());
		System.out.println(json.getInt("courses.size()"));
		System.out.println(json.getString("dashboard.purchaseAmount"));
		System.out.println(json.getString("courses[0].title"));
		
		for(int i=0;i<json.getInt("courses.size()");i++) {
			System.out.println(json.getString("courses["+i+"].title")+" -> "+json.getString("courses["+i+"].price"));
		}
		System.out.println(json.getString("courses[2].copies"));
		int purchaseAmount=json.getInt("dashboard.purchaseAmount");
		int sum=0;
		for(int i=0;i<json.getInt("courses.size()");i++) {
			int price=json.getInt("courses["+i+"].price");
			int copies=json.getInt("courses["+i+"].copies");
			sum=sum+(price*copies);
		}
		Assert.assertEquals(sum, purchaseAmount);
	}
}

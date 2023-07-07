import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.LoginReq;
import pojo.LoginResponse;
import pojo.OrderDetails;
import pojo.Orders;
import pojo.Product;

//Test1234 - test5895@gmail.com
public class ECommerce {
	
	public static void main(String[] args) {
		
		LoginReq loginReq=new LoginReq();
		loginReq.setUserEmail("test5895@gmail.com");
		loginReq.setUserPassword("Test1234");
		
		RequestSpecification reqSpec=new RequestSpecBuilder()
										.setBaseUri("https://rahulshettyacademy.com")
										.setContentType(ContentType.JSON)
										.build();
		ResponseSpecification respSpec=new ResponseSpecBuilder()
				.expectStatusCode(200)
				.expectContentType(ContentType.JSON)
				.build();
		
		LoginResponse resp=given().spec(reqSpec).log().all()
		.body(loginReq)
		.when().post("/api/ecom/auth/login")
		.then().log().all()
		.spec(respSpec)
		.extract().response().as(LoginResponse.class);
		System.out.println(resp.getToken());
		
		//Add Product
		RequestSpecification addProductReqSpec=new RequestSpecBuilder()
													.setBaseUri("https://rahulshettyacademy.com")
													.addHeader("Authorization", resp.getToken())
													.build();
		
		Product product=given().spec(addProductReqSpec).log().all()
			.param("productName", "News")
			.param("productAddedBy", resp.getUserId())
			.param("productCategory", "faishon")
			.param("productSubCategory", "shirts")
			.param("productPrice", "11200")
			.param("productDescription", "Luois Phillipe")
			.param("productFor", "women")
			.multiPart("productImage", new File("D:\\Projects\\Bench\\Screenshot_1.png"))
		.when()
		.post("/api/ecom/product/add-product")
		.then().log().all()
			.extract().response().as(Product.class);
		System.out.println(product.getProductId());
		
		//Create Order
		RequestSpecification createProductReqSpec=new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", resp.getToken())
				.setContentType(ContentType.JSON)
				.build();
		OrderDetails o=new OrderDetails();
		o.setCountry("India");o.setProductOrderedId(product.getProductId());
		List<OrderDetails> list=new ArrayList<OrderDetails>();
		list.add(o);
		Orders order=new Orders();
		order.setOrders(list);
		given().spec(createProductReqSpec).log().all()
			.body(order)
		.when()
		.post("/api/ecom/order/create-order")
		.then().log().all()
			.extract().response();
		
		//Delete Product
		RequestSpecification deleteProductReqSpec=new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", resp.getToken())
				.setContentType(ContentType.JSON)
				.build();
		String deleteResp=given().spec(deleteProductReqSpec).log().all()
			.pathParam("productId", product.getProductId())
		.when()
		.delete("/api/ecom/product/delete-product/{productId}")
		.then().log().all()
			.extract().response().asString();
		JsonPath js=new JsonPath(deleteResp);
		String message=js.getString("message");
		Assert.assertEquals(message, "Product Deleted Successfully");
		
		
	}
	

}

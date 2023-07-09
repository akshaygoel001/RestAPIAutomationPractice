package files;

import static io.restassured.RestAssured.given;

import io.restassured.path.json.JsonPath;

public class GraphQLScript {
	
	public static void main(String[] args) {
		
		String response=given().log().all()
			.header("Content-Type","application/json")
		.body("{\"query\":\"query ($characterId: Int!, $episodeId: Int!) {\\n  character(characterId: $characterId) {\\n    name\\n    status\\n  }\\n  location(locationId: 4) {\\n    name\\n  }\\n  episode(episodeId: $episodeId) {\\n    name\\n    episode\\n    air_date\\n  }\\n  characters {\\n    info {\\n      count\\n    }\\n    result {\\n      id\\n      type\\n    }\\n  }\\n}\\n\",\"variables\":{\"characterId\":1,\"episodeId\":1}}")
		.when()
		.post("https://rahulshettyacademy.com/gq/graphql")
		.then().
			assertThat().
				statusCode(200)
		.extract().response().asString();
		JsonPath js=new JsonPath(response);
		
		System.out.println(js.getString("data.character.name"));
	}
	

}

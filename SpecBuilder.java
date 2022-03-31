import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.File;
public class SpecBuilder {
    RequestSpecification requestSpecification;
    RequestSpecification requestSpecification2;
    ResponseSpecification responseSpecification;
    @BeforeClass
    public void setup() {
        RequestSpecBuilder reqBuilder1 = new RequestSpecBuilder();
        RequestSpecBuilder reqBuilder2 = new RequestSpecBuilder();
        reqBuilder1.setBaseUri("https://jsonplaceholder.typicode.com")
                .addHeader("Content-Type", "application/json");
        reqBuilder2
                .setBaseUri("https://reqres.in/api")
                .addHeader("Content-Type", "application/json");
        //Request Specification
        requestSpecification = RestAssured.with().spec(reqBuilder1.build());
        requestSpecification2 = RestAssured.with().spec(reqBuilder2.build());
        //Response Spec Builder
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectContentType(ContentType.JSON)
                .expectStatusCode(200);
        //Response Specification
        responseSpecification = responseSpecBuilder.build();
    }
    @Test(priority = 1)
    public void get() {
        Response response = requestSpecification.get("/posts")
                .then().spec(responseSpecification)
                .log()
                .ifError()
                .extract()
                .response();
        JSONArray arr = new JSONArray(response.asString());
        for (int i = 0; i < arr.length(); i++) {
            if (arr.getJSONObject(i).has("title")) {
                Assert.assertTrue(true);
            }
            assert (arr.getJSONObject(i).get("title") instanceof String);

            if (arr.getJSONObject(i).get("id").equals(40)) {
                assert (arr.getJSONObject(i).get("userId").equals(4));
            }
        }
    }
    @Test(priority = 2)
    public void put() {
        File jsonData = new File("\"src\\test\\resources\\postdata.json");
        Response response = requestSpecification2.body(jsonData)
                .put("/users")
                .then().spec(responseSpecification)
                .log().ifError()
                .extract()
                .response();
        JSONObject object = new JSONObject(response.asString());
        assert (object.get("name").equals("Arun") && object.get("job").equals("Manager"));
    }
}
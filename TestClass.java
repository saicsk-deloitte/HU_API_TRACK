import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.*;
public class TestClass {
    static String url1 = "https://jsonplaceholder.typicode.com/posts";
    static String url2 = "https://reqres.in/api";
    @BeforeTest
    void init() {
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    void get_call() {
        Response response = given().when().get(url1).then().extract().response();
        assert (response.getStatusCode() == 200);
        assert (response.getContentType().contains("json"));
        JSONArray arr = new JSONArray(response.asString());
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            assert obj.getInt("userId") != 40 || (obj.getInt("id") == 4);
            assert obj.get("title") != null;
            assert obj.get("title") instanceof String;
        }
    }
    @Test
    void put_cell() {
        File jsonData = new File("\\src\\test\\resources\\postdata.json");
        Response response = given().
                baseUri(url2).
                body(jsonData).
                header("Content-Type", "application/json").
                when().
                put("/users").
                then().extract().response();
        assert response.statusCode() == 200;
        JSONObject obj = new JSONObject(response.asString());
        assert obj.get("name").equals("Arun") && obj.get("job").equals("Manager");
    }
}
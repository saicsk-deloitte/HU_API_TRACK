import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
public class RestAssuredTest {
    public static final String GET_URI = "https://jsonplaceholder.typicode.com";
        public static final String PUT_URI = "https://reqres.in/api";
        @Test(priority = 1)
        public void testGet() {
            given()
                    .baseUri(GET_URI)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("posts")
                    .then()
                    .statusCode(200)
                    .contentType("application/json")
                    .body("userId[39]", equalTo(4))
                    .body(containsString("title"));
        }
        @Test(priority = 2)
        public void testPut() {
            File jsonData = new File("src\\test\\java\\resources\\postdata.json");
            given()
                    .baseUri(PUT_URI)
                    .body(jsonData)
                    .header("Content-Type", "application/json")
                    .when()
                    .put("/users")
                    .then()
                    .statusCode(200)
                    .contentType("application/json")
                    .body("name", equalTo("Arun"))
                    .body("job", equalTo("Manager"));

        }
    }


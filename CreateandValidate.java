package MAIN_ASSIGNMENT.Functionalities;
import Excel.ExcelUtils;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;
public class CreateandValidate {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    @BeforeTest
    void init() {
        RestAssured.useRelaxedHTTPSValidation();
    }
    @BeforeClass
    public void setUp() {
        //baseUrl TO_DO_LIST
        String baseUrl = "https://api-nodejs-todolist.herokuapp.com";
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setBaseUri(baseUrl).addHeader("Content-Type", "application/json");
        ////Specifying the request
        requestSpecification = RestAssured.with().spec(reqBuilder.build());
        //Specifying the response
        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder().expectContentType(ContentType.JSON);
        responseSpecification = resBuilder.build();
    }
    @Test
    // Registration using Collections (by reading from Excel )
    public void registerUser() throws Exception {
        ArrayList al;
        ExcelUtils re = new ExcelUtils();
        al = re.readCells();
        System.out.println(al);
        HashMap hash = new HashMap();
        hash.put("name", al.get(0));
        hash.put("email", al.get(1));
        hash.put("password", al.get(2));
        hash.put("age", al.get(3));
        //request Specification
        Response response = requestSpecification
                .body(hash).
                when().
                post("/user/register").
                then().spec(responseSpecification).extract().response();
        //Checking the status code(verifying successful creation) using AssertEquals
        Assert.assertEquals(response.statusCode(), 201);
    }
    @Test
    //Logging-in of User with registered details
    public void loginUser() throws Exception {
        ArrayList al;
        ExcelUtils re = new ExcelUtils();
        al = re.readCells();
        HashMap hash = new HashMap();
        hash.put("email", al.get(1));
        hash.put("password", al.get(2));
        //System.out.println(hash);
        Response response = requestSpecification.body(hash).
                when().post("/user/login").
                then().spec(responseSpecification).extract().response();
        //Verifying HTTP 200 OK success status
        Assert.assertEquals(response.statusCode(), 200);

        JSONObject object = new JSONObject(response.asString());
        //Generating token after successful login
        String generateToken = object.getString("token");
        re.registerToken(generateToken);
    }
    @Test
    //Validating the generated token
    public void validToken() throws Exception {
        ExcelUtils re = new ExcelUtils();
        ArrayList al;
        al = re.readCells();
        String token=re.token;
        //specification and token authorization
        Response response = requestSpecification.auth().oauth2(token).when().get("/user/me").
                then().spec(responseSpecification).extract().response();
        JSONObject object = new JSONObject(response.asString());
        //verifying name
        String verifyName = object.getString("name");
        //verifying email
        String verifyEmail = object.getString("email");
        //verifying age
        String verifyAge = String.valueOf(object.get("age"));
        //Using AssertEquals for verifying the above details
        Assert.assertEquals(verifyName, al.get(0));
        Assert.assertEquals(verifyEmail, al.get(1));
        Assert.assertEquals(verifyAge, al.get(3));
        System.out.println(token);
    }
}




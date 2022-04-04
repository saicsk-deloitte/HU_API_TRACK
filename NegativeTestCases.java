package MAIN_ASSIGNMENT;

import Excel.ExcelUtils;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.HashMap;
public class NegativeTestCases {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    @BeforeTest
    void init() {
        RestAssured.useRelaxedHTTPSValidation();
    }
    @BeforeClass
    public void setUp() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.
                setBaseUri("https://api-nodejs-todolist.herokuapp.com").
                addHeader("Content-Type", "application/json");
        requestSpecification = RestAssured.with().spec(requestSpecBuilder.build());
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().expectContentType(ContentType.JSON);
        responseSpecification = responseSpecBuilder.build();
    }
    @Test(priority = 1)
    public void InvalidRegister() throws Exception {
        ExcelUtils re = new ExcelUtils();
        ArrayList al = re.readCells();
        requestSpecification.
                body(al).
                when().
                post("/user/register").
                then().
                spec(responseSpecification).statusCode(400).extract().response();
                System.out.println("Error!!! Creating User With Same Details");
    }
    @Test(priority = 2)
    public void InvalidLogin() throws Exception {
        ExcelUtils re = new ExcelUtils();
        ArrayList al = re.readCells();
        String token = re.token;
        HashMap hashMap = new HashMap();
        hashMap.put("email", "errrrorr@user.com");
        hashMap.put("password", "password@111");
        requestSpecification.body(hashMap).auth().oauth2(token).
                when().
                post("/task").
                then().
                spec(responseSpecification).statusCode(400).extract().response();
        System.out.println("Error!!!! Logging with invalid registered Details");
    }
}
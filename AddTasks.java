package MAIN_ASSIGNMENT.Functionalities;
import Excel.ExcelUtils;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
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
public class AddTasks {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    @BeforeTest
    void init() {
        RestAssured.useRelaxedHTTPSValidation();
    }
    @BeforeClass
    public void setUp() {
        //setting-up baseurl
        String baseUrl = "https://api-nodejs-todolist.herokuapp.com";
        //specifying the request
        RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
        reqBuilder.setBaseUri(baseUrl).addHeader("Content-Type", "application/json");
        requestSpecification = RestAssured.with().spec(reqBuilder.build());
        //specifying the expected reponse
        ResponseSpecBuilder resBuilder = new ResponseSpecBuilder().expectContentType(ContentType.JSON);
        responseSpecification = resBuilder.build();
    }
    @Test
    public void addTask() throws Exception {
        ExcelUtils re = new ExcelUtils();
        re.readCells();
        TasksDB DB = new TasksDB();
        ArrayList al;
        HashMap hash = new HashMap();
        String checkToken = String.valueOf(re.readCells().get(4));
        al = DB.addTasks();
        for (int i = 0; i < al.size(); i++) {
            hash.put("description", al.get(i));
            Response response = requestSpecification.body(hash).auth().oauth2(checkToken).
                    request(Method.POST, "/task");
            Assert.assertEquals(response.statusCode(), 201);
            JSONObject obj = new JSONObject(response.asString());
            JSONObject obj1 = obj.getJSONObject("data");
            String own = (String) obj1.get("owner");
            System.out.println(own);
            TasksDB tasksDB = new TasksDB();
            tasksDB.getOwner(own, i + 1);
        }
    }
}







    /*@Test
    public void addPagination() throws Exception{
            int limit_2,limit_5,limit_10;
            limit_2 = page.pagination("/task?limit=2");
            limit_5 = page.pagination("/task?limit=5");
            limit_10 = page.pagination("/task?limit=10");
            Assert.assertEquals(limit_2,2);
            Assert.assertEquals(limit_5,5);
            Assert.assertEquals(limit_10,10);
        }

    }
}*/



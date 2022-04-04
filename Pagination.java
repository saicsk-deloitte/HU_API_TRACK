package MAIN_ASSIGNMENT;

import Excel.ExcelUtils;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import static io.restassured.RestAssured.given;
public class Pagination {
    public int paginationImp(String sub_uri) throws Exception
    {
        int len=0;
        ExcelUtils re = new ExcelUtils();
        re.readCells();
        String token=re.token;
        Response response =
                given().
                        baseUri("https://api-nodejs-todolist.herokuapp.com").header("Content-Type", "application/json").auth().oauth2(token).
                        when().
                        get(sub_uri).
                        then().
                        extract().response();
        JSONObject object = new JSONObject(response.asString());
        JSONArray array = (JSONArray) object.get("data");
        len = array.length();
        return len;
    }
}

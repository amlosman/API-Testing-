package RestSpec;

import io.qameta.allure.Description;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RestSpecTest {
    String restPath = "api/users";
    RequestSpecification reqSpec ;
    ResponseSpecification resSpec;
    @Description("Set up Specification ")
    @BeforeClass
    public void setSpac()
    {
        reqSpec = new RequestSpecBuilder().setBaseUri("https://reqres.in").build();
        resSpec = new ResponseSpecBuilder().expectStatusCode(200).build();

    }
    @DataProvider(name = "dataReader")
    public static Object[][]dataReader()
    {
        return new Object[][]
                {
                        {1,"george.bluth@reqres.in","George","Bluth"},
                        {2,"janet.weaver@reqres.in","Janet","Weaver"},
                        {3,"emma.wong@reqres.in","Emma","Wong"},

                };
    }
    @Description("Testes based on DataProvider")
    @Test(dataProvider =  "dataReader")
    public  void getBody(int id,String email ,String first_name , String last_name)
    {
        Response res = given().spec(reqSpec).
                and().pathParam("Id",id).
                when().get(restPath+"/{Id}").
                then().extract().response();

        JsonPath jsonPath = res.jsonPath();
        Assert.assertEquals(jsonPath.get("data.email"),email);
        Assert.assertEquals(jsonPath.get("data.first_name"),first_name);
        Assert.assertEquals(jsonPath.get("data.last_name"),last_name);




    }
    @Description("Test by Specification")

    @Test
    public  void requestResponseSpec()
    {
        given().
                spec(reqSpec).
                log().all().
                when().
                get("api/users/2").then().spec(resSpec);

    }

}

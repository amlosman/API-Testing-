package Rest;

import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

@Test
public class RestTest {
    String restURL = "https://reqres.in/";
    String restPath = "api/users";

    @Description("Test has priority high ")
    @Test(priority = 1)
    public void testSanityCheck()
    {
        given().baseUri(restURL).when().get(restPath).then().log().all();
        given().baseUri(restURL).when().get(restPath).then().assertThat().
                statusCode(200).and().
                contentType("application/json; charset=utf-8");

    }
    @Description("Example for Get method ")

    @Test (dependsOnMethods = {"testSanityCheck"})
    public  void TC1_GET (){

        given().log().all().
                baseUri(restURL)
                .header("Connection","keep-alive").
                and().
                header("ContentType" , ContentType.JSON).
                and().queryParam("page" , 1)
                .and()
                .queryParam("per_page" , 3)
                .and()
                .pathParam("UserNumber" , 2)
                .when().get(restPath+"/{UserNumber}").
                then().
                log().all();

    }
    @Description("Example for Post method ")
    @Test (dependsOnMethods = {"testSanityCheck"})
    public  void TC2_Post (){

        given().log().all().
                baseUri(restURL).
                header("ContentType" , ContentType.JSON)
                .and()
                .body("{\n" +
                        "\"name\": \"ro\",\n" +
                        "\"job\": \"zion resident\"\n" +
                        "}").when().post(restPath).then().log().all();

    }

}

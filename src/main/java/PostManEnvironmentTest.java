import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Test;


public class PostManEnvironmentTest {


    private static final String URL = "https://api.getpostman.com/environments";

    private static final String APIKEY = "PMAK-62963136476527565c88f5f3-9d04294bd4dcfaf22c1d9dd83672dbdaf3";

    @Test
    public void newEnvironmentSuccess() {

        String bodyContent = "{\n" +
                "    \"environment\": {\n" +
                "        \"name\": \"Test " + System.currentTimeMillis() + "\",\n" +
                "        \"values\": [\n" +
                "            {\"key\": \"variable_name_1\", \"value\": \"hello there\"},\n" +
                "            {\"key\": \"variable_name_2\", \"value\": \"\"}\n" +
                "        ]\n" +
                "    }\n" +
                "}";
        
        RestAssured.given()
                .contentType("application/json")
                .header("x-api-key", APIKEY)
                .body(bodyContent)
                .when()
                .post(URL)
                .then()
                .statusCode(200)
                .assertThat()
                .body("uid", Matchers.not(Matchers.emptyString()));

    }

    @Test
    public void newEnvironmentFailure() {
        RestAssured.given()
                .contentType("application/json")
                .header("x-api-key", APIKEY)
                .body("")
                .when()
                .post(URL)
                .then()
                .statusCode(400);
    }

    @Test
    public void getAllEnvironments() {
        //Test A and Test B are existing Environments
        RestAssured.given()
                .contentType("application/json")
                .header("x-api-key", APIKEY)
                .when()
                .get(URL)
                .then()
                .statusCode(200)
                .assertThat()
                .body("environments.name", Matchers.hasItems("Test A", "Test B"));
    }

}

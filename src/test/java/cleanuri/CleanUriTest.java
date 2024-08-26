package cleanuri;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.HttpURLConnection;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CleanUriTest {
    File json = new File("src/test/resources/link.json");

    @BeforeEach
    public void setUp(){
        RestAssured.baseURI = "https://cleanuri.com";
    }

    @Test
    public void createShortLink(){
        String result_url = given().log().all()
                //.contentType(ContentType.fromContentType("application/x-www-form-urlencoded"))
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("/api/v1/shorten")
                .then()
                .statusCode(HttpURLConnection.HTTP_OK)
                .assertThat().body("result_url", notNullValue())
                .extract()
                .body()
                .path("result_url");
                System.out.println(result_url);
    }
    @Test
    public void createShortLinkNegativeBodyTest(){
        given().log().all()
                .header("Content-type", "application/json")
                .body("")
                .when()
                .post("/api/v1/shorten")
                .then()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .assertThat().body("error", equalTo("API Error: URL is empty"));
    }
    @Test
    public void createShortLinkNegativeMethodTest(){
        given().log().all()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .get("/api/v1/shorten")
                .then()
                .statusCode(HttpURLConnection.HTTP_BAD_METHOD)
                .assertThat().body(notNullValue());
    }
}

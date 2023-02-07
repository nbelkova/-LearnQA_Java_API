package testFromLessons;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class HeadersTest {
    @Test
    public void testHeaders(){
        Map<String, String> headers = new HashMap<>();
        headers.put("myHeader1", "myValue1");
        headers.put("myHeader2", "myValue2");

        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .when()
                .get("https://playground.learnqa.ru/api/get_303")
                .andReturn();
        response.prettyPrint();

//        Headers responseHeaders = response.getHeaders();
//        System.out.println(responseHeaders);
        String locationHeader = response.getHeader("location");
        System.out.println(locationHeader);
    }
}

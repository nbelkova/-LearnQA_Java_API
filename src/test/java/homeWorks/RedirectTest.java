package homeWorks;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class RedirectTest {
    @Test
    public void testRedirect(){

        int statusCode = 0;
        Response response;
        String locationHeader = null;
//        Headers responseHeaders = null;
        String url = "https://playground.learnqa.ru/api/long_redirect";
        while (statusCode != 200){
//            System.out.println("------------------------------------------");
//            System.out.println("url: " + url);
            response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .when()
                    .get(url)
                    .andReturn();
            statusCode = response.statusCode();
            locationHeader = response.getHeader("location");
            if (locationHeader != null){
                url=locationHeader;
            }
//             System.out.println("statusCode: " + statusCode);
//             System.out.println("locationHeader: " + locationHeader);
//            responseHeaders = response.getHeaders();
//             System.out.println("responseHeaders: " + responseHeaders);
        }
        System.out.println("Адрес на который редиректит этот запрос: " + url);

    }
}

package homeWorks;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class LongRedirectTest {
    @Test
    public void testLongRedirect(){
        int statusCode = 0;
        int iteration=1;
        Response response;
        String locationHeader = null;
        String url = "https://playground.learnqa.ru/api/long_redirect";
        while (statusCode != 200){
            System.out.println("-----------------Итерация "+iteration+"-----------------");
            System.out.println("Запрос на url: " + url);
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
             System.out.println("Статус запроса: " + statusCode);
             System.out.println("Запрос редиректит на: " + locationHeader);
            iteration = iteration + 1;
        }
    }
}

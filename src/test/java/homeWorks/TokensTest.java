package homeWorks;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

public class TokensTest {
    @Test
    public void testTokens() throws InterruptedException {
//        Map<String, String> headers = new HashMap<>();
//        headers.put("token", "myToken");

        JsonPath response = RestAssured
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        response.prettyPrint();
        String token = response.get("token");
        int seconds = response.get("seconds");
        Long long_mills = (long) (seconds * 1000);
        response = RestAssured
                .given()
                .queryParams("token", token)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        response.prettyPrint();
        String status = response.get("status");
        String statusRight = "Job is NOT ready";
        System.out.println("Статус " + status);

        if (status.equals(statusRight)){
            System.out.println("Статус правильный в промежуточной точке");
        }
        Thread.sleep(long_mills);
        response = RestAssured
                .given()
                .queryParams("token", token)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        response.prettyPrint();

        status = response.get("status");
        String result = response.get("result");
        statusRight = "Job is ready";
        if (status.equals(statusRight)){
            System.out.println("Статус правильный в конечной точке - задача завершена");
            if (result == null){
                System.out.println("Результат задачи не получен");
            }else {
                System.out.println("Результат задачи: " + result);
            }
        }else {
            System.out.println("Статус не правильный в конечной точке - задача не завершена");
        }
    }
}

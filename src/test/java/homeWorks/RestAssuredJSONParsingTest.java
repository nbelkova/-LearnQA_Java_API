package homeWorks;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

public class RestAssuredJSONParsingTest {
    @Test
    public void testRestAssuredJSONParsing(){
        JsonPath response = RestAssured
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();
        System.out.println("Первая часть задания - полученный JSON распечатать:");
        response.prettyPrint();

        System.out.println("Вторая часть задания - вывести текст второго сообщения:");
//        Object messages = response.get("messages");

        ArrayList<Map<String,String>> messages = response.get("messages");
        Map<String,String> secondMessage = messages.get(1);
        System.out.println(secondMessage.get("message"));

    }
}

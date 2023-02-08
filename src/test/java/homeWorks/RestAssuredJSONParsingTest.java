package homeWorks;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

public class RestAssuredJSONParsingTest {
    @Test
    public void testRestAssuredJSONParsing(){
        JsonPath response = RestAssured
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();
        System.out.println("Первая часть задания - полученный JSON распечатать:");
        response.prettyPrint();

        System.out.println("Вторая часть задания - вывести текст второго сообщения");
        Object messages = response.get("messages");



//        JsonPath[] messages = new JsonPath[2];
//        String messages = response.get("messages").toString();
//        for (int i=0; i<1; i++){
//            int start = messages.indexOf("{");
//            messages = messages.substring(start);
//        }
//        int end = messages.indexOf("\"");
//        char[] message2 = new char[30];
//        messages.getChars(12, end, message2, 0);
//        JsonPath message2T = messages[1];

            System.out.println(messages);
//        }

    }
}

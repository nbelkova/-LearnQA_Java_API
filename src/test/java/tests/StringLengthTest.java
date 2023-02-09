package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringLengthTest {
    @Test
    public void testStringLength(){

            JsonPath response = RestAssured
                    .get("https://playground.learnqa.ru/api/get_json_homework")
                    .jsonPath();
            String messages = response.get("messages").toString();
            assertTrue(messages.length() > 15,"");
    }
}

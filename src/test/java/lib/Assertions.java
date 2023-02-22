package lib;

import io.restassured.response.Response;

import java.util.Map;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Assertions {
    public static void assertJsonByName(Response Response, String name, int expectedValue) {
        Response.then().assertThat().body("$", hasKey(name));

        int value = Response.jsonPath().getInt(name);
        assertEquals(expectedValue, value, "JSON value is not equal to expected value");
    }

    public static void assertJsonByNameString(Response Response, String name, String expectedValue) {
        Response.then().assertThat().body("$", hasKey(name));

        String value = Response.jsonPath().getString(name);
        assertEquals(expectedValue, value, "JSON value is not equal to expected value");
    }

    public static void assertHeaderByNameString(Response Response, String name, String expectedValue) {
        Response.then().assertThat().header("$", hasKey(name));

        String value = Response.jsonPath().getString(name);
        assertEquals(expectedValue, value, "JSON value is not equal to expected value");
    }
    public static void assertResponseTextEquals(Response Response, String expectedAnswer){
        assertEquals(
                expectedAnswer,
                Response.asString(),
                "Response text isn't as expected"
        );
    }

    public static void assertResponseCodeEquals(Response Response, int expectedStatusCode){
        assertEquals(
                expectedStatusCode,
                Response.statusCode(),
                "Response status code isn't as expected"
        );
    }

    public static void assertJsonHasField(Response Response, String expectedFieldName){
        Response.then().assertThat().body("$", hasKey(expectedFieldName));
    }

    public static void assertJsonHasNotField(Response Response, String unexpectedFieldName){
        Response.then().assertThat().body("$", not(hasKey(unexpectedFieldName)));
    }

    public static void assertJsonHasFields(Response Response, String[] expectedFieldNames){
        for (String expectedFieldName : expectedFieldNames){
            Assertions.assertJsonHasField(Response,expectedFieldName);
        }
    }

    public static void userAgentsRightParams(String userAgent, Map<String,String> userAgentResponseParams) {
        if (userAgent.equals("Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30")){
            assertEquals("Mobile", userAgentResponseParams.get("platform"), "Не правильно указана платформа для UA: " + userAgent);
            assertEquals("No", userAgentResponseParams.get("browser"), "Не правильно указан браузер для UA: " + userAgent);
            assertEquals("Android", userAgentResponseParams.get("device"), "Не правильно указан девайс для UA: " + userAgent);
        }
        if (userAgent.equals("Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1")){
            assertEquals("Mobile", userAgentResponseParams.get("platform"), "Не правильно указана платформа для UA: " + userAgent);
            assertEquals("Chrome", userAgentResponseParams.get("browser"), "Не правильно указан браузер для UA: " + userAgent);
            assertEquals("iOS", userAgentResponseParams.get("device"), "Не правильно указан девайс для UA: " + userAgent);
        }
        if (userAgent.equals("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")){
            assertEquals("Googlebot", userAgentResponseParams.get("platform"), "Не правильно указана платформа для UA: " + userAgent);
            assertEquals("Unknown", userAgentResponseParams.get("browser"), "Не правильно указан браузер для UA: " + userAgent);
            assertEquals("Unknown", userAgentResponseParams.get("device"),"Не правильно указан девайс для UA: " + userAgent);
        }
        if (userAgent.equals("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0")){
            assertEquals(userAgentResponseParams.get("platform"), "Web", "Не правильно указана платформа для UA: " + userAgent);
            assertEquals(userAgentResponseParams.get("browser"), "Chrome", "Не правильно указан браузер для UA: " + userAgent);
            assertEquals(userAgentResponseParams.get("device"), "No", "Не правильно указан девайс для UA: " + userAgent);
        }
        if (userAgent.equals("Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1")){
            assertEquals("Mobile", userAgentResponseParams.get("platform"), "Не правильно указана платформа для UA: " + userAgent);
            assertEquals("No", userAgentResponseParams.get("browser"), "Не правильно указан браузер для UA: " + userAgent);
            assertEquals("iPhone", userAgentResponseParams.get("device"), "Не правильно указан девайс для UA: " + userAgent);
        }
    }
}

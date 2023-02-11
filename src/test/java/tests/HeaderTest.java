package tests;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import lib.BaseTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HeaderTest extends BaseTestCase {

    String header;
    Headers headers;

    @BeforeEach
    public void getHomeworkHeader(){
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();
        this.header = response.getHeaders().getValue("x-secret-homework-header");
        this.headers = response.getHeaders();
//        System.out.println(headers);

    }

    @Test
    public void testHomeworkHeaderValue() {

        assertEquals("Some secret value", this.header, "Header isn't equal to expected value 'Some secret value'");
    }
    @Test
    public void testOtherHeaders() {
        assertEquals("application/json", this.headers.getValue("Content-Type"), "Content-Type header isn't equal to expected value 'application/json'");
        assertEquals("15", this.headers.getValue("Content-Length"), "Content-Length header isn't equal to expected value '15'");
        assertEquals("keep-alive", this.headers.getValue("Connection"), "Connection header isn't equal to expected value 'keep-alive'");
        assertEquals("timeout=10", this.headers.getValue("Keep-Alive"), "Keep-Alive timeout header isn't equal to expected value '10'");
        assertEquals("Apache", this.headers.getValue("Server"), "Server header isn't equal to expected value 'Apache'");
        assertEquals("Apache", this.headers.getValue("Server"), "Server header isn't equal to expected value 'Apache'");
    }
}

package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.BaseTestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CookieTest extends BaseTestCase {

    String cookie;
    String cookieWithParams;
    String[] cookie_params;

    @BeforeEach
    public void getCookie(){
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();
        this.cookie = response.getCookies().toString();
        this.cookieWithParams = response.getHeaders().getValue("Set-Cookie");
        this.cookie_params = cookieWithParams.split("; ");
    }

    @Test
    public void testCookieValue() {

        assertEquals("{HomeWork=hw_value}", this.cookie, "Cookie isn't equal to expected value 'hw_value'");
    }
    @Test
    public void testCookieParams() {

        // в предположении, что места параметров не меняются, сравнила так. Если они могу менять места, то наверное можно попоробовать убрать из массива expires и сравнить массивы  Arrays.equals(cookie_params, cookie_params_expected)
        assertEquals("Max-Age=2678400", cookie_params[2], "Cookie parameter Max-Age isn't equal to expected value '2678400'");
        assertEquals("path=/", cookie_params[3], "Cookie parameter path isn't equal to expected value '/'");
        assertEquals("domain=playground.learnqa.ru", cookie_params[4], "Cookie parameter domain isn't equal to expected value 'playground.learnqa.ru'");
        assertEquals("HttpOnly", cookie_params[5], "There isn't cookie parameter HttpOnly");
    }
    @Test
    public void testCookieExpireEmptyTest() throws InterruptedException {

        // expires скачет, поэтому проверить его не получится, нужно четкое понимание алгоритма его скачков, чтобы проверить, что оно соответсвует ТЗ

//        Date now = new Date();
//        long l = now.getTime()- 2678428*1000+17277*60*1000;
//        Date expireDate = new Date(l);
//        System.out.println("Сейчас " + now);
//        System.out.println(expireDate);

        // проверка протухающей куки - не получится проверить, потому что метод постоянно отдает одну и ту же куку и не меняет ее

//        long millis = (2678400*1000);
//        Thread.sleep(millis);
//        Response responseForCheck = RestAssured
//                .get("https://playground.learnqa.ru/api/homework_cookie")
//                .andReturn();
//        assertFalse(response.getHeaders().getValue("Set-Cookie").equals(responseForCheck.getHeaders().getValue("Set-Cookie")), "Cookie didn't expire");

        //вариант проверить куку через другой метод - даже при действующей куке говорит, что я не авторизована. Нужна документация и понимание как проверить действвие куки

//        Response responseForCheck = RestAssured
//                .post("https://playground.learnqa.ru/api/check_auth_cookie")
//                .andReturn();
//        responseForCheck.prettyPrint();
//        String result = responseForCheck.print();
//        assertEquals("You are NOT authorized", result, "Cookie Homework isn't expired");
    }
}

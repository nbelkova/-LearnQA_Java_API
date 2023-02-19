package lib;

import io.restassured.http.Headers;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.hasKey;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseTestCase {
    protected String getHeader (Response Response, String name){
        Headers headers = Response.getHeaders();

        assertTrue(headers.hasHeaderWithName(name), "Response doesn't have header with name " + name);
        return headers.getValue(name);
    }
    protected String getCookie (Response Response, String name){
        Map<String,String> cookies = Response.getCookies();

        assertTrue(cookies.containsKey(name),"Response doesn't have cookie with name " + name);
        return cookies.get(name);
    }
    protected int getIntFromJson(Response Response, String name) {
        Response.then().assertThat().body("$",hasKey(name));
        return Response.jsonPath().getInt(name);
    }

    protected Map<String,String> getUserAgentResponseParams(Response Response) {
        Map<String,String> userAgentResponseParams = new HashMap<>();
        userAgentResponseParams.put("platform", Response.jsonPath().get("platform"));
        userAgentResponseParams.put("browser", Response.jsonPath().get("browser"));
        userAgentResponseParams.put("device", Response.jsonPath().get("device"));

        return userAgentResponseParams;
    }
}

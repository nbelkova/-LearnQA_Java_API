package tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserDeleteTest extends BaseTestCase{
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    @Description("This test checks delete user with id = 2")
    @DisplayName("Test login like user with id = 2, delete user, check result")
    public void testDeleteUserId2(){
        //LOGIN
        Map<String,String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login",authData);

        //DELETE USER WITH ID = 2
        int userId = 2;
        Response responseDeleteUser = apiCoreRequests.makeDeleteRequest("https://playground.learnqa.ru/api/user/" + userId,
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));

        Assertions.assertDeleteResponse(responseDeleteUser, false);

        //CHECK DELETE
        Response responseUserData = apiCoreRequests.makeGetRequest("https://playground.learnqa.ru/api/user/" + userId,
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));
//        System.out.println("Ответ на запрос данных пользователя: " + responseUserData.prettyPrint());

        Assertions.assertJsonHasField(responseUserData, "id");
    }

    @Test
    @Description("This positive test checks delete user")
    @DisplayName("Test generate new user, get his id, login, delete user, check it")
    public void testDeleteUser(){
        // GENERATE USER
        Map<String,String> userData = DataGenerator.getRegistrationData();
        int userId = apiCoreRequests.createAndTakeIDForNewUser();

        //LOGIN
        Map<String,String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login",authData);

        //CHECK USER EXIST
        Response responseUserData = apiCoreRequests.makeGetRequest("https://playground.learnqa.ru/api/user/" + userId,
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));
        Assertions.assertJsonHasField(responseUserData, "id");

        //DELETE
        apiCoreRequests.makeDeleteRequest("https://playground.learnqa.ru/api/user/" + userId,
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));

        //CHECK DELETE
        Response responseUserDataAfterDelete = apiCoreRequests.makeGetRequest("https://playground.learnqa.ru/api/user/" + userId,
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));

        Assertions.assertDeleteResponse(responseUserDataAfterDelete, true);
    }

    @Test
    @Description("This negative test checks delete user by other user")
    @DisplayName("Test generate two  user, get his id, login, delete other user, check it")
    public void testDeleteOtherUser(){
        // GENERATE USERS
        Map<String,String> userData1 = DataGenerator.getRegistrationData();
        apiCoreRequests.createAndTakeIDForNewUser();
        int userId2 = apiCoreRequests.createAndTakeIDForNewUser();

        //LOGIN BY USER1
        Map<String,String> authData = new HashMap<>();
        authData.put("email", userData1.get("email"));
        authData.put("password", userData1.get("password"));

        Response responseGetAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login",authData);

        //CHECK USER2 EXIST
        Response responseUserData = apiCoreRequests.makeGetRequest("https://playground.learnqa.ru/api/user/" + userId2,
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));
        Assertions.assertJsonHasNotField(responseUserData, "id");

        //DELETE USER2 WITH AUTH USER1
        apiCoreRequests.makeDeleteRequest("https://playground.learnqa.ru/api/user/" + userId2,
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));

        //CHECK AFTER DELETE
        apiCoreRequests.makeGetRequest("https://playground.learnqa.ru/api/user/" + userId2,
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));

        Assertions.assertJsonHasNotField(responseUserData, "id");
    }
}

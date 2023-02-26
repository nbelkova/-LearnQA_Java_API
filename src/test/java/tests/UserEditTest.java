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

public class UserEditTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Test
    @Description("This test checks edit user")
    @DisplayName("Test generate new user, get his id, login, edit firstName, check it")
    public void testEditJustCreatedUser(){
        // GENERATE USER
        Map<String,String> userData = DataGenerator.getRegistrationData();
        int userId = apiCoreRequests.createAndTakeIDForNewUser();

        //LOGIN
        Map<String,String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login",authData);

        //EDIT
        String newName = "Changed name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        apiCoreRequests.makePutRequest("https://playground.learnqa.ru/api/user/" + userId,
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"),
                editData);

        //GET
        Response responseUserData = apiCoreRequests.makeGetRequest("https://playground.learnqa.ru/api/user/" + userId,
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));

        Assertions.assertJsonByNameString(responseUserData, "firstName", newName);
    }
    @Test
    @Description("This test checks edit user without auth)")
    @DisplayName("Test generate new user, get his id, edit firstName, check it")
    public void testEditUserWithoutAuth(){
        // GENERATE USER
        Map<String,String> userData = DataGenerator.getRegistrationData();
        int userId = apiCoreRequests.createAndTakeIDForNewUser();

        //EDIT
        String newName = "Changed name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUser = apiCoreRequests.makePutRequest("https://playground.learnqa.ru/api/user/" + userId,
                "anyToken",
                "anyCookie",
                editData);
//        System.out.println("Ответ на попытку редактирования: " + responseEditUser.asString());
        Assertions.assertResponseTextEquals(responseEditUser, "Auth token not supplied");

        //GET
        Response responseUserData = apiCoreRequests.makeGetRequest("https://playground.learnqa.ru/api/user/" + userId,
                "anyToken",
                "anyCookie");
//        System.out.println("Запрос после попытки редактирования: " + responseUserData.asString());

        Assertions.assertJsonByNameString(responseUserData, "username", userData.get("username"));
    }

    @Test
    @Description("This test checks edit user by other auth user")
    @DisplayName("Test generate new users, get id 2d user, login by 1st, edit 2d user, check it")
    public void testEditUserByOtherAuthUser(){
        // GENERATE USERS
        Map<String,String> userData1 = DataGenerator.getRegistrationData();
        apiCoreRequests.createAndTakeIDForNewUser();
        int userId2 = apiCoreRequests.createAndTakeIDForNewUser();

        //LOGIN BY FIRST USER
        Map<String,String> authData = new HashMap<>();
        authData.put("email", userData1.get("email"));
        authData.put("password", userData1.get("password"));

        Response responseGetAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login",authData);

        //EDIT BY FIRST USER SECOND USER
        String newName = "Changed name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUser = apiCoreRequests.makePutRequest("https://playground.learnqa.ru/api/user/" + userId2,
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"),
                editData);
//        System.out.println("Ответ на попытку редактирования: " + responseEditUser.prettyPrint());

        //GET
        Response responseUserData = apiCoreRequests.makeGetRequest("https://playground.learnqa.ru/api/user/" + userId2,
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));
//        System.out.println("Запрос после попытки редактирования: " + responseUserData.asString());

        Assertions.assertJsonByNameString(responseUserData, "username", userData1.get("username"));
    }

    @Test
    @Description("This test checks edit user by incorrect email without @")
    @DisplayName("Test generate new user, get his id, login, edit email, check it")
    public void testEditUserByIncorrectEmail(){
        // GENERATE USER
        Map<String,String> userData = DataGenerator.getRegistrationData();
        int userId = apiCoreRequests.createAndTakeIDForNewUser();

        //LOGIN
        Map<String,String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login",authData);

        //EDIT
        String newEmail = "namegmail.com";
        Map<String, String> editData = new HashMap<>();
        editData.put("email", newEmail);

        Response responseEditUser = apiCoreRequests.makePutRequest("https://playground.learnqa.ru/api/user/" + userId,
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"),
                editData);
//        System.out.println("Ответ на попытку редактирования: " + responseEditUser.asString());
        Assertions.assertResponseTextEquals(responseEditUser, "Invalid email format");

        //GET
        Response responseUserData = apiCoreRequests.makeGetRequest("https://playground.learnqa.ru/api/user/" + userId,
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));
//        System.out.println("Запрос после попытки редактирования: " + responseUserData.asString());

        Assertions.assertJsonByNameString(responseUserData, "email", userData.get("email"));
    }

    @Test
    @Description("This test checks edit user by too short firstname")
    @DisplayName("Test generate new user, get his id, login, edit firstName, check it")
    public void testEditUserByTooShortName(){
        // GENERATE USER
        Map<String,String> userData = DataGenerator.getRegistrationData();
        int userId = apiCoreRequests.createAndTakeIDForNewUser();

        //LOGIN
        Map<String,String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests.makePostRequest("https://playground.learnqa.ru/api/user/login",authData);

        //EDIT
        String newName = "b";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUser = apiCoreRequests.makePutRequest("https://playground.learnqa.ru/api/user/" + userId,
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"),
                editData);
//        System.out.println("Ответ на попытку редактирования: " + responseEditUser.asString());
        Assertions.assertJsonByNameString(responseEditUser,"error", "Too short value for field firstName");

        //GET
        Response responseUserData = apiCoreRequests.makeGetRequest("https://playground.learnqa.ru/api/user/" + userId,
                this.getHeader(responseGetAuth, "x-csrf-token"),
                this.getCookie(responseGetAuth, "auth_sid"));
//        System.out.println("Запрос после попытки редактирования: " + responseUserData.asString());

        Assertions.assertJsonByNameString(responseUserData, "firstName", userData.get("firstName"));
    }
}

package tests;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

public class UserRegistrationTest extends BaseTestCase {
    @Test
    @Description("This test checks positive case of registration")
    @DisplayName("Test positive user registration")
    public void testCreateUserSuccessfully(){

        Map<String,String> userData =  DataGenerator.getRegistrationData();

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/ajax/api/user")
                .andReturn();

//        System.out.println(responseCreateAuth.asString());
        Assertions.assertResponseCodeEquals(responseCreateAuth,200);
        Assertions.assertJsonHasField(responseCreateAuth, "id");
    }
    @Test
    @Description("This test checks registration with existing in db email")
    @DisplayName("Test negative user registration existing email")
    public void testCreateUserWithExistingEmail(){

        String email = "vinkotov@example.com";
        Map<String,String> userData =  new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/ajax/api/user")
                .andReturn();

        Assertions.assertResponseTextEquals(responseCreateAuth,"Users with email '" + email + "' already exists");
        Assertions.assertResponseCodeEquals(responseCreateAuth,400);
    }
    @Test
    @Description("This test checks registration with email without @")
    @DisplayName("Test negative user registration with email without @")
    public void testCreateUserWithoutAtInEmail(){

        String email = "vinkotovexample.com";
        Map<String,String> userData =  new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/ajax/api/user")
                .andReturn();

//        System.out.println(responseCreateAuth.asString());
        Assertions.assertResponseTextEquals(responseCreateAuth,"Invalid email format");
        Assertions.assertResponseCodeEquals(responseCreateAuth,400);
    }

    @Test
    @Description("This test checks registration with too short username")
    @DisplayName("Test negative user registration with too short username")
    public void testCreateUserWithShortName(){

        String shortName = "a";
        Map<String,String> userData =  new HashMap<>();
        userData.put("username", shortName);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/ajax/api/user")
                .andReturn();

//        System.out.println(responseCreateAuth.asString());
        Assertions.assertResponseTextEquals(responseCreateAuth,"The value of 'username' field is too short");
        Assertions.assertResponseCodeEquals(responseCreateAuth,400);
    }

    @Test
    @Description("This test checks registration with too long username")
    @DisplayName("Test negative user registration with too long username")
    public void testCreateUserWithLongName(){

        String longName = DataGenerator.getLongString();
        Map<String,String> userData =  new HashMap<>();
        userData.put("username", longName);
        userData = DataGenerator.getRegistrationData(userData);

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/ajax/api/user")
                .andReturn();

//        System.out.println(responseCreateAuth.asString());
        Assertions.assertResponseTextEquals(responseCreateAuth,"The value of 'username' field is too long");
        Assertions.assertResponseCodeEquals(responseCreateAuth,400);
    }
    @Description("This test checks registration without sending some of required field")
    @DisplayName("Test negative user registration user without some field")
    @ParameterizedTest
    @ValueSource(strings = {"email", "password", "username", "firstName", "lastName"})
    public void testCreateUserWithoutAnyField(String field){

        Map<String,String> userData = DataGenerator.getRegistrationDataWithoutField(field);

        Response responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/ajax/api/user")
                .andReturn();

//        System.out.println(responseCreateAuth.asString());
//        System.out.println(DataGenerator.getErrorMessageWithoutField(field));
        Assertions.assertResponseTextEquals(responseCreateAuth,DataGenerator.getErrorMessageWithoutField(field));
        Assertions.assertResponseCodeEquals(responseCreateAuth,400);
    }
}



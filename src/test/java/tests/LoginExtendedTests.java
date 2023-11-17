package tests;

import models.lombok.LoginBodyLombokModel;
import models.lombok.LoginResponseLombokModel;
import models.pojo.LoginBodyPojoModel;
import models.pojo.LoginResponsePojoModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginExtendedTests {

    @Test
    void successfulLoginBadPracticeTest() {
        String authBody = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }"; // BAD PRACTICE

        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(authBody)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void successfulLoginWithPojoTest() {
        LoginBodyPojoModel authBody = new LoginBodyPojoModel();
        authBody.setEmail("eve.holt@reqres.in");
        authBody.setPassword("cityslicka");

        LoginResponsePojoModel response =  given()
                .log().uri()
                .log().method()
                .log().body()
                .body(authBody)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponsePojoModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

    @Test
    void successfulLoginWithLombokTest() {
        LoginBodyLombokModel authBody = new LoginBodyLombokModel();
        authBody.setEmail("eve.holt@reqres.in");
        authBody.setPassword("cityslicka");

        LoginResponseLombokModel response =  given()
                .log().uri()
                .log().method()
                .log().body()
                .body(authBody)
                .contentType(JSON)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }


}
